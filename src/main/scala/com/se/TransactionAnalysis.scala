package com.se

import org.apache.spark.sql.SparkSession

import com.se.ExecuteOperations.{ readData, transformData, writeData }

object TransactionAnalysis extends App {
  val spark = SparkSession.builder()
    //.master("local") // uncomment this to run from local IDE for demo purpose
    .appName("TransactionAnalysis")
    .getOrCreate()

  val inputFile = args(0) // comment this while running in local IDE for demo purpose
  val outputPath = args(1) // comment this while running in local IDE for demo purpose
  //val inputFile = "src/main/resources/transaction_data.csv" // uncomment this while running from local IDE for demo purpose
  val inputDf = readData(spark, inputFile)
  val finalDf = transformData(inputDf)
  finalDf.show()
  writeData(finalDf, outputPath) // comment this while running from local IDE for demo purpose

  spark.close()

}