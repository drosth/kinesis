package nl.dpes.kinesis

import scala.collection.JavaConverters._

import nl.dpes.kinesis.config.{AmazonKinesisConfig, Configuration}

import com.amazonaws.services.kinesis.AmazonKinesis
import com.amazonaws.services.kinesis.model.ResourceNotFoundException

object AmazonKinesisUtils extends Configuration with AmazonKinesisConfig {

  def listStreamNames()(implicit kinesis: AmazonKinesis): List[String] = {
    kinesis.listStreams().getStreamNames.asScala.toList
  }

  def validateStream(streamName: String)(implicit kinesis: AmazonKinesis): Boolean = {
    try {
      val result = kinesis.describeStream(streamName)
      !"ACTIVE".equals(result.getStreamDescription.getStreamStatus)
    }
    catch {
      case e: ResourceNotFoundException => {
        System.err.println(s"Stream '$streamName' does not exist. Please create it in the console.")
        false
      }
      case e: Exception => {
        System.err.println(s"Error found while describing the stream '$streamName'")
        false
      }
    }
  }
}
