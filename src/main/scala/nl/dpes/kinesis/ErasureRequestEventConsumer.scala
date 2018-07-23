package nl.dpes.kinesis

import scala.concurrent.Future
import scala.concurrent.duration._

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.gilt.gfc.aws.kinesis.client.KCLWorkerRunner

trait ErasureRequestEventConsumer {

  def readFromKinesis(): Unit
}

class ErasureRequestEventConsumerImpl(config: KinesisClientLibConfiguration) extends ErasureRequestEventConsumer {

  import scala.concurrent.ExecutionContext.Implicits.global

  import Implicits._

  override def readFromKinesis(): Unit = {

    KCLWorkerRunner(config).runAsyncSingleRecordProcessor[String](1 minute) { e: String =>

      println(s"Received: '$e'")

      Future.unit
    }
  }
}
