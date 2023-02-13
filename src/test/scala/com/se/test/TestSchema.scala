package com.se.test

import org.apache.spark.sql.types._

object TestSchema {

  val expectedTestSchema = StructType(
    List(
      StructField("user_id", StringType, true),
      StructField("counterparty_id", StringType, true),
      StructField("amount", LongType, true)
    )
  )

  val ActualSchema = StructType(
    List(
      StructField("user_id", StringType, true),
      StructField("account_id", StringType, true),
      StructField("counterparty_id", StringType, true),
      StructField("transaction_type", StringType, true),
      StructField("date", DateType, true),
      StructField("amount", LongType, true)
    )
  )

}
