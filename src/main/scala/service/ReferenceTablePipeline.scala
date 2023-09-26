package service

import org.apache.spark.sql.{DataFrame, SaveMode}
import scala.util.{Failure, Success, Try}
import utils.ReferenceLogging

object ReferenceTablePipeline extends ReferenceLogging {

  def persistReferenceTable(refDataFrame: DataFrame,
                            referenceTableName: String,
                            databricksDbName: String,
                            writeMode: SaveMode = SaveMode.Overwrite
                           ): Unit = {

    Try {
      logger.info(s"loading $referenceTableName table on $databricksDbName database")

      refDataFrame.write
        .mode(writeMode)
        .format("delta")
        .saveAsTable(s"$databricksDbName.$referenceTableName")

    } match {
      case Success(_) => logger.info(s"Successfully Managed Table Created for $referenceTableName")
      case Failure(f) => logger.error(s"Failed to write data for $referenceTableName", f)
    }
  }
}
