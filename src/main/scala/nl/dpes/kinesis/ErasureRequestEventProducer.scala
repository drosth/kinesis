package nl.dpes.kinesis

import scala.concurrent.Future

import com.gilt.gfc.aws.kinesis.client.{KinesisPublisher, KinesisPublisherBatchResult}

trait ErasureRequestEventProducer {

  def writeToKinesis(streamName: String, payloads: Seq[String]): Future[KinesisPublisherBatchResult]
}

class ErasureRequestEventProducerImpl(publisher: KinesisPublisher) extends ErasureRequestEventProducer {
  import Implicits._

  override def writeToKinesis(streamName: String, payloads: Seq[String]): Future[KinesisPublisherBatchResult] = {

    publisher.publishBatch(streamName, payloads)
  }
}
