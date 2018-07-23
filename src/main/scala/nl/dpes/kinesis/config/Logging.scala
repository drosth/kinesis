package nl.dpes.kinesis.config

import org.slf4j.{Logger, LoggerFactory}

trait Logging {
  implicit lazy val logger: Logger = LoggerFactory.getLogger("nl.dpes.kinesis")

}
