/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.diviso.graeshoppe.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class RefundDetails extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -8112252011095735175L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"RefundDetails\",\"namespace\":\"com.diviso.graeshoppe.avro\",\"fields\":[{\"name\":\"status\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"orderId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]},{\"name\":\"refundId\",\"type\":[\"null\",{\"type\":\"string\",\"avro.java.string\":\"String\"}]}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<RefundDetails> ENCODER =
      new BinaryMessageEncoder<RefundDetails>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<RefundDetails> DECODER =
      new BinaryMessageDecoder<RefundDetails>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<RefundDetails> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<RefundDetails> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<RefundDetails>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this RefundDetails to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a RefundDetails from a ByteBuffer. */
  public static RefundDetails fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String status;
  @Deprecated public java.lang.String orderId;
  @Deprecated public java.lang.String refundId;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public RefundDetails() {}

  /**
   * All-args constructor.
   * @param status The new value for status
   * @param orderId The new value for orderId
   * @param refundId The new value for refundId
   */
  public RefundDetails(java.lang.String status, java.lang.String orderId, java.lang.String refundId) {
    this.status = status;
    this.orderId = orderId;
    this.refundId = refundId;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return status;
    case 1: return orderId;
    case 2: return refundId;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: status = (java.lang.String)value$; break;
    case 1: orderId = (java.lang.String)value$; break;
    case 2: refundId = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.String value) {
    this.status = value;
  }

  /**
   * Gets the value of the 'orderId' field.
   * @return The value of the 'orderId' field.
   */
  public java.lang.String getOrderId() {
    return orderId;
  }

  /**
   * Sets the value of the 'orderId' field.
   * @param value the value to set.
   */
  public void setOrderId(java.lang.String value) {
    this.orderId = value;
  }

  /**
   * Gets the value of the 'refundId' field.
   * @return The value of the 'refundId' field.
   */
  public java.lang.String getRefundId() {
    return refundId;
  }

  /**
   * Sets the value of the 'refundId' field.
   * @param value the value to set.
   */
  public void setRefundId(java.lang.String value) {
    this.refundId = value;
  }

  /**
   * Creates a new RefundDetails RecordBuilder.
   * @return A new RefundDetails RecordBuilder
   */
  public static com.diviso.graeshoppe.avro.RefundDetails.Builder newBuilder() {
    return new com.diviso.graeshoppe.avro.RefundDetails.Builder();
  }

  /**
   * Creates a new RefundDetails RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new RefundDetails RecordBuilder
   */
  public static com.diviso.graeshoppe.avro.RefundDetails.Builder newBuilder(com.diviso.graeshoppe.avro.RefundDetails.Builder other) {
    return new com.diviso.graeshoppe.avro.RefundDetails.Builder(other);
  }

  /**
   * Creates a new RefundDetails RecordBuilder by copying an existing RefundDetails instance.
   * @param other The existing instance to copy.
   * @return A new RefundDetails RecordBuilder
   */
  public static com.diviso.graeshoppe.avro.RefundDetails.Builder newBuilder(com.diviso.graeshoppe.avro.RefundDetails other) {
    return new com.diviso.graeshoppe.avro.RefundDetails.Builder(other);
  }

  /**
   * RecordBuilder for RefundDetails instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<RefundDetails>
    implements org.apache.avro.data.RecordBuilder<RefundDetails> {

    private java.lang.String status;
    private java.lang.String orderId;
    private java.lang.String refundId;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.diviso.graeshoppe.avro.RefundDetails.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.orderId)) {
        this.orderId = data().deepCopy(fields()[1].schema(), other.orderId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.refundId)) {
        this.refundId = data().deepCopy(fields()[2].schema(), other.refundId);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing RefundDetails instance
     * @param other The existing instance to copy.
     */
    private Builder(com.diviso.graeshoppe.avro.RefundDetails other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.orderId)) {
        this.orderId = data().deepCopy(fields()[1].schema(), other.orderId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.refundId)) {
        this.refundId = data().deepCopy(fields()[2].schema(), other.refundId);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public java.lang.String getStatus() {
      return status;
    }

    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder setStatus(java.lang.String value) {
      validate(fields()[0], value);
      this.status = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder clearStatus() {
      status = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'orderId' field.
      * @return The value.
      */
    public java.lang.String getOrderId() {
      return orderId;
    }

    /**
      * Sets the value of the 'orderId' field.
      * @param value The value of 'orderId'.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder setOrderId(java.lang.String value) {
      validate(fields()[1], value);
      this.orderId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'orderId' field has been set.
      * @return True if the 'orderId' field has been set, false otherwise.
      */
    public boolean hasOrderId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'orderId' field.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder clearOrderId() {
      orderId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'refundId' field.
      * @return The value.
      */
    public java.lang.String getRefundId() {
      return refundId;
    }

    /**
      * Sets the value of the 'refundId' field.
      * @param value The value of 'refundId'.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder setRefundId(java.lang.String value) {
      validate(fields()[2], value);
      this.refundId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'refundId' field has been set.
      * @return True if the 'refundId' field has been set, false otherwise.
      */
    public boolean hasRefundId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'refundId' field.
      * @return This builder.
      */
    public com.diviso.graeshoppe.avro.RefundDetails.Builder clearRefundId() {
      refundId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RefundDetails build() {
      try {
        RefundDetails record = new RefundDetails();
        record.status = fieldSetFlags()[0] ? this.status : (java.lang.String) defaultValue(fields()[0]);
        record.orderId = fieldSetFlags()[1] ? this.orderId : (java.lang.String) defaultValue(fields()[1]);
        record.refundId = fieldSetFlags()[2] ? this.refundId : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<RefundDetails>
    WRITER$ = (org.apache.avro.io.DatumWriter<RefundDetails>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<RefundDetails>
    READER$ = (org.apache.avro.io.DatumReader<RefundDetails>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
