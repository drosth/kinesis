package nl.dpes.kinesis.config

import com.amazonaws.regions.{Region, Regions, RegionUtils}

trait Configuration {
  lazy val region: Region = RegionUtils.getRegion(Regions.EU_WEST_1.getName)

  lazy val streamName: String = "test-domain"
  lazy val applicationName: String = "kinesis-poc"
}
