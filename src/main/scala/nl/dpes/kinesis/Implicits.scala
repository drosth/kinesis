package nl.dpes.kinesis

import com.amazonaws.services.kinesis.model.Record
import com.gilt.gfc.aws.kinesis.client.{KinesisRecord, KinesisRecordReader, KinesisRecordWriter}

object Implicits {

  implicit object StringRecordReader extends KinesisRecordReader[String] {
    override def apply(r: Record): String = new String(r.getData.array(), "UTF-8")
  }

  implicit object StringRecordWriter extends KinesisRecordWriter[String] {
    override def toKinesisRecord(item: String): KinesisRecord = KinesisRecord("partition-key", item.getBytes("UTF-8"))
  }
}
