// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: App.proto

package uc.mei.is;

public interface ProtoProfessorOrBuilder extends
    // @@protoc_insertion_point(interface_extends:tutorial.ProtoProfessor)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional string uuid = 1;</code>
   * @return Whether the uuid field is set.
   */
  boolean hasUuid();
  /**
   * <code>optional string uuid = 1;</code>
   * @return The uuid.
   */
  java.lang.String getUuid();
  /**
   * <code>optional string uuid = 1;</code>
   * @return The bytes for uuid.
   */
  com.google.protobuf.ByteString
      getUuidBytes();

  /**
   * <code>optional string name = 2;</code>
   * @return Whether the name field is set.
   */
  boolean hasName();
  /**
   * <code>optional string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>optional string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>optional string phoneNum = 3;</code>
   * @return Whether the phoneNum field is set.
   */
  boolean hasPhoneNum();
  /**
   * <code>optional string phoneNum = 3;</code>
   * @return The phoneNum.
   */
  java.lang.String getPhoneNum();
  /**
   * <code>optional string phoneNum = 3;</code>
   * @return The bytes for phoneNum.
   */
  com.google.protobuf.ByteString
      getPhoneNumBytes();

  /**
   * <code>optional string addr = 4;</code>
   * @return Whether the addr field is set.
   */
  boolean hasAddr();
  /**
   * <code>optional string addr = 4;</code>
   * @return The addr.
   */
  java.lang.String getAddr();
  /**
   * <code>optional string addr = 4;</code>
   * @return The bytes for addr.
   */
  com.google.protobuf.ByteString
      getAddrBytes();

  /**
   * <code>optional .google.protobuf.Timestamp bday = 5;</code>
   * @return Whether the bday field is set.
   */
  boolean hasBday();
  /**
   * <code>optional .google.protobuf.Timestamp bday = 5;</code>
   * @return The bday.
   */
  com.google.protobuf.Timestamp getBday();
  /**
   * <code>optional .google.protobuf.Timestamp bday = 5;</code>
   */
  com.google.protobuf.TimestampOrBuilder getBdayOrBuilder();

  /**
   * <code>repeated .tutorial.ProtoStudent student = 6;</code>
   */
  java.util.List<uc.mei.is.ProtoStudent> 
      getStudentList();
  /**
   * <code>repeated .tutorial.ProtoStudent student = 6;</code>
   */
  uc.mei.is.ProtoStudent getStudent(int index);
  /**
   * <code>repeated .tutorial.ProtoStudent student = 6;</code>
   */
  int getStudentCount();
  /**
   * <code>repeated .tutorial.ProtoStudent student = 6;</code>
   */
  java.util.List<? extends uc.mei.is.ProtoStudentOrBuilder> 
      getStudentOrBuilderList();
  /**
   * <code>repeated .tutorial.ProtoStudent student = 6;</code>
   */
  uc.mei.is.ProtoStudentOrBuilder getStudentOrBuilder(
      int index);
}
