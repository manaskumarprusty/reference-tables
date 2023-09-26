package utils

import org.slf4j.{Logger, LoggerFactory}

trait ReferenceLogging {

  @transient
  protected lazy val logger: Logger =
    LoggerFactory.getLogger(getClass.getName)

}
