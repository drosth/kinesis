package nl.dpes.kinesis.config

import nl.dpes.kinesis.{ErasureRequestEventProducer, ErasureRequestEventProducerImpl}
import nl.dpes.kinesis.Kinesis.region

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.gilt.gfc.aws.kinesis.client.KinesisPublisher

trait KinesisProducerConfig {
  this: Configuration =>

  val publisher = KinesisPublisher(
    awsRegion = Some(region)
  )

  val producer: ErasureRequestEventProducer = new ErasureRequestEventProducerImpl(publisher)
}
