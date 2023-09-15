package dao

import org.apache.spark.sql.DataFrame

trait ReferenceTableProcessor {
  def process(): DataFrame
}
