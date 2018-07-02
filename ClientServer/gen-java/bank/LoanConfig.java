/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package bank;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-07-02")
public class LoanConfig implements org.apache.thrift.TBase<LoanConfig, LoanConfig._Fields>, java.io.Serializable, Cloneable, Comparable<LoanConfig> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LoanConfig");

  private static final org.apache.thrift.protocol.TField CURRENCY_FIELD_DESC = new org.apache.thrift.protocol.TField("currency", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField MONEY_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("moneyAmount", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField DAYS_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("daysCount", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LoanConfigStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LoanConfigTupleSchemeFactory();

  public java.lang.String currency; // required
  public double moneyAmount; // required
  public int daysCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CURRENCY((short)1, "currency"),
    MONEY_AMOUNT((short)2, "moneyAmount"),
    DAYS_COUNT((short)3, "daysCount");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CURRENCY
          return CURRENCY;
        case 2: // MONEY_AMOUNT
          return MONEY_AMOUNT;
        case 3: // DAYS_COUNT
          return DAYS_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __MONEYAMOUNT_ISSET_ID = 0;
  private static final int __DAYSCOUNT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CURRENCY, new org.apache.thrift.meta_data.FieldMetaData("currency", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MONEY_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("moneyAmount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.DAYS_COUNT, new org.apache.thrift.meta_data.FieldMetaData("daysCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LoanConfig.class, metaDataMap);
  }

  public LoanConfig() {
  }

  public LoanConfig(
    java.lang.String currency,
    double moneyAmount,
    int daysCount)
  {
    this();
    this.currency = currency;
    this.moneyAmount = moneyAmount;
    setMoneyAmountIsSet(true);
    this.daysCount = daysCount;
    setDaysCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LoanConfig(LoanConfig other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetCurrency()) {
      this.currency = other.currency;
    }
    this.moneyAmount = other.moneyAmount;
    this.daysCount = other.daysCount;
  }

  public LoanConfig deepCopy() {
    return new LoanConfig(this);
  }

  @Override
  public void clear() {
    this.currency = null;
    setMoneyAmountIsSet(false);
    this.moneyAmount = 0.0;
    setDaysCountIsSet(false);
    this.daysCount = 0;
  }

  public java.lang.String getCurrency() {
    return this.currency;
  }

  public LoanConfig setCurrency(java.lang.String currency) {
    this.currency = currency;
    return this;
  }

  public void unsetCurrency() {
    this.currency = null;
  }

  /** Returns true if field currency is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrency() {
    return this.currency != null;
  }

  public void setCurrencyIsSet(boolean value) {
    if (!value) {
      this.currency = null;
    }
  }

  public double getMoneyAmount() {
    return this.moneyAmount;
  }

  public LoanConfig setMoneyAmount(double moneyAmount) {
    this.moneyAmount = moneyAmount;
    setMoneyAmountIsSet(true);
    return this;
  }

  public void unsetMoneyAmount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MONEYAMOUNT_ISSET_ID);
  }

  /** Returns true if field moneyAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetMoneyAmount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MONEYAMOUNT_ISSET_ID);
  }

  public void setMoneyAmountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MONEYAMOUNT_ISSET_ID, value);
  }

  public int getDaysCount() {
    return this.daysCount;
  }

  public LoanConfig setDaysCount(int daysCount) {
    this.daysCount = daysCount;
    setDaysCountIsSet(true);
    return this;
  }

  public void unsetDaysCount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DAYSCOUNT_ISSET_ID);
  }

  /** Returns true if field daysCount is set (has been assigned a value) and false otherwise */
  public boolean isSetDaysCount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DAYSCOUNT_ISSET_ID);
  }

  public void setDaysCountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DAYSCOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case CURRENCY:
      if (value == null) {
        unsetCurrency();
      } else {
        setCurrency((java.lang.String)value);
      }
      break;

    case MONEY_AMOUNT:
      if (value == null) {
        unsetMoneyAmount();
      } else {
        setMoneyAmount((java.lang.Double)value);
      }
      break;

    case DAYS_COUNT:
      if (value == null) {
        unsetDaysCount();
      } else {
        setDaysCount((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CURRENCY:
      return getCurrency();

    case MONEY_AMOUNT:
      return getMoneyAmount();

    case DAYS_COUNT:
      return getDaysCount();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CURRENCY:
      return isSetCurrency();
    case MONEY_AMOUNT:
      return isSetMoneyAmount();
    case DAYS_COUNT:
      return isSetDaysCount();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LoanConfig)
      return this.equals((LoanConfig)that);
    return false;
  }

  public boolean equals(LoanConfig that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_currency = true && this.isSetCurrency();
    boolean that_present_currency = true && that.isSetCurrency();
    if (this_present_currency || that_present_currency) {
      if (!(this_present_currency && that_present_currency))
        return false;
      if (!this.currency.equals(that.currency))
        return false;
    }

    boolean this_present_moneyAmount = true;
    boolean that_present_moneyAmount = true;
    if (this_present_moneyAmount || that_present_moneyAmount) {
      if (!(this_present_moneyAmount && that_present_moneyAmount))
        return false;
      if (this.moneyAmount != that.moneyAmount)
        return false;
    }

    boolean this_present_daysCount = true;
    boolean that_present_daysCount = true;
    if (this_present_daysCount || that_present_daysCount) {
      if (!(this_present_daysCount && that_present_daysCount))
        return false;
      if (this.daysCount != that.daysCount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetCurrency()) ? 131071 : 524287);
    if (isSetCurrency())
      hashCode = hashCode * 8191 + currency.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(moneyAmount);

    hashCode = hashCode * 8191 + daysCount;

    return hashCode;
  }

  @Override
  public int compareTo(LoanConfig other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetCurrency()).compareTo(other.isSetCurrency());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrency()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currency, other.currency);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMoneyAmount()).compareTo(other.isSetMoneyAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMoneyAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.moneyAmount, other.moneyAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDaysCount()).compareTo(other.isSetDaysCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDaysCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.daysCount, other.daysCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LoanConfig(");
    boolean first = true;

    sb.append("currency:");
    if (this.currency == null) {
      sb.append("null");
    } else {
      sb.append(this.currency);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("moneyAmount:");
    sb.append(this.moneyAmount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("daysCount:");
    sb.append(this.daysCount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class LoanConfigStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LoanConfigStandardScheme getScheme() {
      return new LoanConfigStandardScheme();
    }
  }

  private static class LoanConfigStandardScheme extends org.apache.thrift.scheme.StandardScheme<LoanConfig> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LoanConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CURRENCY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currency = iprot.readString();
              struct.setCurrencyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MONEY_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.moneyAmount = iprot.readDouble();
              struct.setMoneyAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DAYS_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.daysCount = iprot.readI32();
              struct.setDaysCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, LoanConfig struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.currency != null) {
        oprot.writeFieldBegin(CURRENCY_FIELD_DESC);
        oprot.writeString(struct.currency);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MONEY_AMOUNT_FIELD_DESC);
      oprot.writeDouble(struct.moneyAmount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DAYS_COUNT_FIELD_DESC);
      oprot.writeI32(struct.daysCount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LoanConfigTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LoanConfigTupleScheme getScheme() {
      return new LoanConfigTupleScheme();
    }
  }

  private static class LoanConfigTupleScheme extends org.apache.thrift.scheme.TupleScheme<LoanConfig> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LoanConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetCurrency()) {
        optionals.set(0);
      }
      if (struct.isSetMoneyAmount()) {
        optionals.set(1);
      }
      if (struct.isSetDaysCount()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetCurrency()) {
        oprot.writeString(struct.currency);
      }
      if (struct.isSetMoneyAmount()) {
        oprot.writeDouble(struct.moneyAmount);
      }
      if (struct.isSetDaysCount()) {
        oprot.writeI32(struct.daysCount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LoanConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.currency = iprot.readString();
        struct.setCurrencyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.moneyAmount = iprot.readDouble();
        struct.setMoneyAmountIsSet(true);
      }
      if (incoming.get(2)) {
        struct.daysCount = iprot.readI32();
        struct.setDaysCountIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
