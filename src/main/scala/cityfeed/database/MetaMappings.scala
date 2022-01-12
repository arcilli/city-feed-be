package cityfeed.database


import com.google.protobuf.timestamp.Timestamp
import doobie.{Get, Meta}

import java.sql.Timestamp
import java.time.{Instant, ZoneId}
import java.util.Calendar
import cats._, cats.data._, cats.implicits._
import doobie._, doobie.implicits._

object MetaMappings {

//  implicit val timestampMeta: Get[com.google.protobuf.timestamp.Timestamp] =
//    Get[Long].tmap(timestamp => {
//        val nanos = new  java.sql.Timestamp(timestamp).getNanos
//        val seconds =  new java.sql.Timestamp(timestamp).getSeconds
//        val googleTimestamp = new com.google.protobuf.timestamp.Timestamp(nanos, seconds)
//        googleTimestamp})

}
