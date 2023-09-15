package com.artemishealth.reference.table.months

import dao.ReferenceTableProcessor
import org.apache.spark.sql.{DataFrame, SparkSession}

class MonthsReferenceTable(spark: SparkSession) extends ReferenceTableProcessor {
  override def process(): DataFrame = {

    val months = spark.read.csv("")
    months
  }

}
