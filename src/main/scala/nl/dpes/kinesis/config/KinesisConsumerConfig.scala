package nl.dpes.kinesis.config

import nl.dpes.kinesis.{ErasureRequestEventConsumer, ErasureRequestEventConsumerImpl}
import nl.dpes.kinesis.Kinesis.{kinesisClientLibConfiguration, region}

import com.amazonaws.{ClientConfiguration, ClientConfigurationFactory}
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.services.kinesis.{AmazonKinesis, AmazonKinesisClientBuilder}
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.gilt.gfc.aws.kinesis.client.KCLConfiguration

trait KinesisConsumerConfig {
  this: Configuration =>

  lazy val kinesisClientLibConfiguration: KinesisClientLibConfiguration = KCLConfiguration(
    applicationName = applicationName,
    streamName = streamName,
    regionName = Some(region.getName)
  )

  val consumer: ErasureRequestEventConsumer = new ErasureRequestEventConsumerImpl(kinesisClientLibConfiguration)
}
