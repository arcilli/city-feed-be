// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package cityfeed.application.grpc

@SerialVersionUID(0L)
final case class PostResponse(
    postCreated: _root_.scala.Boolean = false,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[PostResponse] {
    @transient
    private[this] var __serializedSizeMemoized: _root_.scala.Int = 0
    private[this] def __computeSerializedSize(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = postCreated
        if (__value != false) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(1, __value)
        }
      };
      __size += unknownFields.serializedSize
      __size
    }
    override def serializedSize: _root_.scala.Int = {
      var __size = __serializedSizeMemoized
      if (__size == 0) {
        __size = __computeSerializedSize() + 1
        __serializedSizeMemoized = __size
      }
      __size - 1
      
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      {
        val __v = postCreated
        if (__v != false) {
          _output__.writeBool(1, __v)
        }
      };
      unknownFields.writeTo(_output__)
    }
    def withPostCreated(__v: _root_.scala.Boolean): PostResponse = copy(postCreated = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = postCreated
          if (__t != false) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PBoolean(postCreated)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion: cityfeed.application.grpc.PostResponse.type = cityfeed.application.grpc.PostResponse
    // @@protoc_insertion_point(GeneratedMessage[feed.PostResponse])
}

object PostResponse extends scalapb.GeneratedMessageCompanion[cityfeed.application.grpc.PostResponse] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[cityfeed.application.grpc.PostResponse] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): cityfeed.application.grpc.PostResponse = {
    var __postCreated: _root_.scala.Boolean = false
    var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder = null
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 8 =>
          __postCreated = _input__.readBool()
        case tag =>
          if (_unknownFields__ == null) {
            _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder()
          }
          _unknownFields__.parseField(tag, _input__)
      }
    }
    cityfeed.application.grpc.PostResponse(
        postCreated = __postCreated,
        unknownFields = if (_unknownFields__ == null) _root_.scalapb.UnknownFieldSet.empty else _unknownFields__.result()
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[cityfeed.application.grpc.PostResponse] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      cityfeed.application.grpc.PostResponse(
        postCreated = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Boolean]).getOrElse(false)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = FeedProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = FeedProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = cityfeed.application.grpc.PostResponse(
    postCreated = false
  )
  implicit class PostResponseLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, cityfeed.application.grpc.PostResponse]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, cityfeed.application.grpc.PostResponse](_l) {
    def postCreated: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.postCreated)((c_, f_) => c_.copy(postCreated = f_))
  }
  final val POSTCREATED_FIELD_NUMBER = 1
  def of(
    postCreated: _root_.scala.Boolean
  ): _root_.cityfeed.application.grpc.PostResponse = _root_.cityfeed.application.grpc.PostResponse(
    postCreated
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[feed.PostResponse])
}
