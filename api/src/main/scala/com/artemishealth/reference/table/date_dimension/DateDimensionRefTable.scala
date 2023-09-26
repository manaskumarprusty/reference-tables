package com.artemishealth.reference.table.date_dimension

import com.artemishealth.reference.table.date_dimension.DateDimensionRefTable.DateDimensionSchema
import dao.ReferenceTableProcessor
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

import java.sql.Date
import java.time.LocalDate


class DateDimensionRefTable (spark: SparkSession) extends ReferenceTableProcessor {
  override def process(): DataFrame = {

    import spark.implicits._

    val years = (2008 to 2030).toDF("year")
    val months = (1 to 12).toDF("month")
    val fiscalYearStartMonth = 7
    val yearMonthDF = years.crossJoin(months)
    val daysDF = yearMonthDF
      .withColumn("days", sequence(lit(1),
      when($"month" === 2 && ($"year" % 4 === 0 && $"year" % 100 != 0 || $"year" % 400 === 0), 29)
        .when($"month" === 2, 28)
        .when($"month" === 4 || $"month" === 6 || $"month" === 9 || $"month" === 11, 30)
        .otherwise(31)
    ))
    val yearMonthDateDF = daysDF.select($"year", $"month", explode($"days").as("day"))
      .withColumn("full_date", to_date(concat($"year", lit("-"), $"month", lit("-"), $"day")))

    val dateDimensionRefTable = yearMonthDateDF
      .withColumn("date_key", date_format($"full_date", "yyyyMMdd"))
      .withColumn("month_key", date_format($"full_date", "yyyy-MM"))
      .withColumn("day_of_week", (dayofweek($"full_date") + 5) % 7 + 1)
      .withColumn("day_num_in_month", $"day")
      .withColumn("day_num_overall", row_number().over(Window.orderBy($"full_date")))
      .withColumn("day_name", date_format($"full_date", "EEEE"))
      .withColumn("day_abbrev", date_format($"full_date", "E"))
      .withColumn("weekday_flag", when(((dayofweek($"full_date") + 5) % 7 + 1)
        .isin(6,7), "y").otherwise("n"))
      .withColumn("week_num_in_year", weekofyear($"full_date")-1)
      .withColumn("week_num_overall", lit(0))
      .withColumn("week_begin_date", date_sub($"full_date", expr("dayofweek(full_date) - 1")))
      .withColumn("week_begin_date_key", date_format(date_sub($"full_date", expr("dayofweek(full_date) - 1")), "yyyyMMdd"))
      .withColumn("month_num_overall", row_number().over(Window.orderBy($"year", $"month")))
      .withColumn("month_name", date_format($"full_date", "MMMM"))
      .withColumn("month_abbrev", date_format($"full_date", "MMM"))
      .withColumn("quarter", quarter($"full_date"))
      .withColumn("yearmo", date_format($"full_date", "yyyyMM"))
      .withColumn("fiscal_month", when(month($"full_date") >= fiscalYearStartMonth, year($"full_date") + 1).otherwise(year($"full_date")))
      .withColumn("last_day_in_month_flag", when(dayofmonth($"full_date") === dayofmonth(last_day($"full_date")), "y").otherwise("n"))
      .withColumn("same_day_year_ago_date", add_months($"full_date", -12))

    dateDimensionRefTable.as[DateDimensionSchema].toDF()

  }

}

object DateDimensionRefTable {
  case class DateDimensionSchema(date_key: Int,
                                month_key: String,
                                full_date: Date,
                                day_of_week: Int,
                                day_num_in_month: Int,
                                day_num_overall: Int,
                                day_name: String,
                                day_abbrev: String,
                                weekday_flag: String,
                                week_num_in_year: Int,
                                week_num_overall: Int,
                                week_begin_date: Date,
                                week_begin_date_key: Long,
                                month: Int,
                                month_num_overall: Int,
                                month_name: String,
                                month_abbrev: String,
                                quarter: Int,
                                year: Int,
                                yearmo: Long,
                                fiscal_month: Int,
                                fiscal_quarter: Int,
                                fiscal_year: Int,
                                last_day_in_month_flag: String,
                                same_day_year_ago_date: Date)
}