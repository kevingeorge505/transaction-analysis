package com.se

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{ col, dense_rank, sum }
import org.apache.spark.sql.{ DataFrame, SaveMode, SparkSession }
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{ FileSystem, Path }

import org.slf4j.LoggerFactory

import com.se.Schema.transactionSchema

object ExecuteOperations {
  val logger = LoggerFactory.getLogger(getClass.getName)

  /**
   * This function is to read csv data from the local path and return a dataframe
   *
   * @param spark spark session variable
   * @param path  csv file path
   * @return dataframe
   */
  def readData(spark: SparkSession, path: String): DataFrame = {
    logger.info(s"Reading csv input data from hdfs path $path")
    spark.read.schema(transactionSchema).option("header", true).option("delimiter", ";").csv(s"$path")
  }

  /**
   * This function transforms the input data as per the requirement and returns dataframe
   *
   * @param inputDf dataframe with input csv data
   * @return dataframe
   */
  def transformData(inputDf: DataFrame): DataFrame = {
    logger.info("Transforming input data")
    // selecting the required columns and finding the sum of amount for each user_id and counterparty_id
    val df = inputDf.select(col("user_id"), col("counterparty_id"), col("amount"))
      .groupBy("user_id", "counterparty_id").agg(sum("amount").as("amount"))

    val windowSpec = Window.partitionBy("user_id").orderBy(col("amount").desc)
    /* applying the window function and selecting the highest sum of amount per user_id to find the
       counterparty with which the user had done highest amount transfer */
    df.withColumn("rank", dense_rank().over(windowSpec))
      .where(col("rank") === 1)
      .drop("rank")
  }

  /**
   * Function to write data as single csv file to hdfs output path
   *
   * @param df   dataframe to write
   * @param path hdfs output path
   */
  def writeData(df: DataFrame, path: String): Unit = {
    logger.info(s"Writing output dataframe as csv file to hdfs path: $path")
    df.coalesce(1).write.mode(SaveMode.Overwrite).option("header", true).option("delimiter", ";").csv(path)
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    val partFileName = fs.globStatus(new Path(s"$path/part*"))(0).getPath().getName()
    fs.rename(new Path(s"$path/$partFileName"), new Path(s"$path/output.csv"))
  }

}
