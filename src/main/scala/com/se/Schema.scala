package com.se

import org.apache.spark.sql.types._

object Schema {

  val transactionSchema = StructType(
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
