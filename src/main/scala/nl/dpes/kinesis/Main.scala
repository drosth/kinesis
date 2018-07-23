package nl.dpes.kinesis

import scala.concurrent.Await

import nl.dpes.kinesis.config.{AmazonKinesisConfig, Configuration, KinesisConsumerConfig, KinesisProducerConfig}

import com.gilt.gfc.aws.kinesis.client.KinesisPublisher
import scala.concurrent.duration._

object Kinesis extends App with Configuration
    with KinesisConsumerConfig
    with KinesisProducerConfig
    with AmazonKinesisConfig
{

  AmazonKinesisUtils.listStreamNames().foreach(name=>println(s"Known stream name: '$name'"))
  AmazonKinesisUtils.validateStream(streamName)

  // ------------------------

  Await.result(producer.writeToKinesis(streamName, Seq("Foo", "Bear")), 20 seconds)

  consumer.readFromKinesis()
}
