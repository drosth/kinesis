package nl.dpes.kinesis

import scala.collection.JavaConverters._

import nl.dpes.kinesis.config.{AmazonKinesisConfig, Configuration, Logging}

import com.amazonaws.services.kinesis.AmazonKinesis
import rx.lang.scala.Observable

object AmazonKinesisUtils extends Configuration with AmazonKinesisConfig with Logging {

  def listStreamNames()(implicit kinesis: AmazonKinesis): Observable[String] = {
    Observable.from(kinesis.listStreams().getStreamNames.asScala.toList)
  }

  def validateStream(streamName: String)(implicit kinesis: AmazonKinesis): Observable[Boolean] = {
    Observable.just(kinesis.describeStream(streamName))
        .map(result => !"ACTIVE".equals(result.getStreamDescription.getStreamStatus))
        .onErrorReturn(ResourceNotFoundException => {
          logger.warn(s"Stream '$streamName' does not exist. Please create it in the console.")
          false
        })
        .onErrorReturn(Exception => {
          logger.warn(s"Error found while describing the stream '$streamName'")
          false
        })
  }
}
