package nl.dpes.kinesis

import scala.concurrent.{Await, ExecutionContext}

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.{Region, Regions, RegionUtils}
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.gilt.gfc.aws.kinesis.client.{KCLConfiguration, KinesisClientEndpoints, KinesisPublisher}
import scala.concurrent.duration._

object Main extends App {
  val region: Region = RegionUtils.getRegion(Regions.EU_WEST_1.getName)

  val endpointConfiguration: EndpointConfiguration = new EndpointConfiguration("arn:aws:kinesis:eu-west-1:674201978047:stream", "eu-west-1")

  val publisher: KinesisPublisher = KinesisPublisher(
    maxErrorRetryCount = 10
    , threadPoolSize = 8
    , awsCredentialsProvider = new DefaultAWSCredentialsProviderChain()
    , awsRegion = Some(region)
  )

  lazy val kinesisClientEndpoints: KinesisClientEndpoints = KinesisClientEndpoints("", "arn:aws:kinesis:eu-west-1:674201978047:stream")

  lazy val config: KinesisClientLibConfiguration = KCLConfiguration(
    applicationName = "consumer-name",
    streamName = "acc-domain",
    endpointConfiguration = Some(kinesisClientEndpoints)
  )


  val consumer: ErasureRequestEventConsumer = new ErasureRequestEventConsumerImpl(config)

  val producer: ErasureRequestEventProducer = new ErasureRequestEventProducerImpl(publisher)
  Await.result(producer.writeToKinesis("acc-domain", Seq("Hello", "World")), 10 seconds)
}
