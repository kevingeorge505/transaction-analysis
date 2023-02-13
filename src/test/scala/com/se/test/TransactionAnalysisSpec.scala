package com.se.test

import org.apache.spark.sql.{ DataFrame, SparkSession }
import org.apache.spark.sql.functions.col

import com.se.ExecuteOperations.transformData
import com.se.test.TestSchema.{ ActualSchema, expectedTestSchema }

class TransactionAnalysisSpec extends TestBase {

  val spark = SparkSession.builder()
    .master("local")
    .appName("TransactionAnalysisSpec")
    .getOrCreate()

  it should "check data and schema are equal for actual and expected data" in {
    val expectedDf = spark.read.schema(expectedTestSchema).option("header", true).option("delimiter", ";").csv("src/test/resources/expected_output.csv")
    val inputTestDf = spark.read.schema(ActualSchema).option("header", true).option("delimiter", ";").csv("src/test/resources/input_test_data.csv")
    val actualDf = transformData(inputTestDf)
    assert(actualDf.schema.equals(expectedDf.schema))
    checkData(actualDf).collect().mkString shouldEqual checkData(expectedDf).collect().mkString
  }

  /**
   * Function to sort the dataframe based on all columns so that it can be compared
   *
   * @param df dataframe to sort
   * @return sorted dataframe
   */
  def checkData(df: DataFrame): DataFrame = {
    val colNames = df.columns.sorted
    val cols = colNames.map(col)
    df.sort(cols: _*)
  }

}
