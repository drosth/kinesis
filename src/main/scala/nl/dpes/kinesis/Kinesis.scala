package nl.dpes.kinesis

import scala.concurrent.Await
import scala.concurrent.duration._

import nl.dpes.kinesis.config._

object Kinesis extends App with Configuration
    with KinesisConsumerConfig
    with KinesisProducerConfig
    with AmazonKinesisConfig
    with Logging {

  AmazonKinesisUtils.listStreamNames().foreach(name => println(s"Known stream name: '$name'"))
  AmazonKinesisUtils.validateStream(streamName).subscribe(
    isValid => logger.info(s"'$streamName' is known"),
    t => logger.error(t.getMessage)
  )

  // ------------------------

  Await.result(producer.writeToKinesis(streamName, Seq("Foo", "Bear")), 20 seconds)

  //  consumer.readFromKinesis()
}
