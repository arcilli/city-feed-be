// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package cityfeed.application.grpc

object FeedProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    com.google.protobuf.timestamp.TimestampProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      cityfeed.application.grpc.PostRequest,
      cityfeed.application.grpc.PostResponse,
      cityfeed.application.grpc.FetchRequest,
      cityfeed.application.grpc.FetchedPosts
    )
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """ChNjaXR5ZmVlZC9mZWVkLnByb3RvEgRmZWVkGh9nb29nbGUvcHJvdG9idWYvdGltZXN0YW1wLnByb3RvIuIBCgtQb3N0UmVxd
  WVzdBImCgdtZXNzYWdlGAEgASgJQgziPwkSB21lc3NhZ2VSB21lc3NhZ2USMgoLYmFzZTY0SW1hZ2UYAiABKAlCEOI/DRILYmFzZ
  TY0SW1hZ2VSC2Jhc2U2NEltYWdlEikKCGxvY2F0aW9uGAMgASgJQg3iPwoSCGxvY2F0aW9uUghsb2NhdGlvbhIdCgR0YWdzGAQgA
  SgJQgniPwYSBHRhZ3NSBHRhZ3MSLQoKdXNlcl90b2tlbhgFIAEoCUIO4j8LEgl1c2VyVG9rZW5SCXVzZXJUb2tlbiJCCgxQb3N0U
  mVzcG9uc2USMgoLcG9zdENyZWF0ZWQYASABKAhCEOI/DRILcG9zdENyZWF0ZWRSC3Bvc3RDcmVhdGVkIoYBCgxGZXRjaFJlcXVlc
  3QSIwoGYW1vdW50GAEgASgFQgviPwgSBmFtb3VudFIGYW1vdW50EiMKBnVzZXJJZBgCIAEoCUIL4j8IEgZ1c2VySWRSBnVzZXJJZ
  BIsCglzZWVuUG9zdHMYAyADKAVCDuI/CxIJc2VlblBvc3RzUglzZWVuUG9zdHMi7AIKDEZldGNoZWRQb3N0cxIsCglvd25lclVzZ
  XIYASABKAlCDuI/CxIJb3duZXJVc2VyUglvd25lclVzZXISKQoIdXNlcm5hbWUYAiABKAlCDeI/ChIIdXNlcm5hbWVSCHVzZXJuY
  W1lEiYKB21lc3NhZ2UYAyABKAlCDOI/CRIHbWVzc2FnZVIHbWVzc2FnZRIyCgtiYXNlNjRJbWFnZRgEIAEoCUIQ4j8NEgtiYXNlN
  jRJbWFnZVILYmFzZTY0SW1hZ2USNQoMbmVpZ2hib3Job29kGAUgASgJQhHiPw4SDG5laWdoYm9yaG9vZFIMbmVpZ2hib3Job29kE
  h0KBHRhZ3MYBiADKAlCCeI/BhIEdGFnc1IEdGFncxIsCgl0aW1lc3RhbXAYByABKAlCDuI/CxIJdGltZXN0YW1wUgl0aW1lc3Rhb
  XASIwoGZWRpdGVkGAggASgIQgviPwgSBmVkaXRlZFIGZWRpdGVkMkcKDlBvc3RpbmdTZXJ2aWNlEjUKCkNyZWF0ZVBvc3QSES5mZ
  WVkLlBvc3RSZXF1ZXN0GhIuZmVlZC5Qb3N0UmVzcG9uc2UiADJICgxGZXRjaFNlcnZpY2USOAoKZmV0Y2hQb3N0cxISLmZlZWQuR
  mV0Y2hSZXF1ZXN0GhIuZmVlZC5GZXRjaGVkUG9zdHMiADABQigKGWNpdHlmZWVkLmFwcGxpY2F0aW9uLmdycGNCCUZlZWRQcm90b
  1ABYgZwcm90bzM="""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
      com.google.protobuf.timestamp.TimestampProto.javaDescriptor
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}