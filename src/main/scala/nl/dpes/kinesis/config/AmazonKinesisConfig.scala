package nl.dpes.kinesis.config


import com.amazonaws.{ClientConfiguration, ClientConfigurationFactory}
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.services.kinesis.{AmazonKinesis, AmazonKinesisClientBuilder}

trait AmazonKinesisConfig {
  this: Configuration =>

  def clientBuilder(region: Region): AmazonKinesisClientBuilder = {
    val clientConfiguration: ClientConfiguration = new ClientConfigurationFactory().getConfig
    val clientBuilder = AmazonKinesisClientBuilder.standard()
    clientBuilder.setRegion(region.getName)
    clientBuilder.setCredentials(new DefaultAWSCredentialsProviderChain())
    clientBuilder.setClientConfiguration(clientConfiguration)
    clientBuilder
  }

  implicit val kinesis: AmazonKinesis = clientBuilder(region).build()
}
