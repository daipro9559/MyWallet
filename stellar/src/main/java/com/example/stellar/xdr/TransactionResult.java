// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten

package com.example.stellar.xdr;


import java.io.IOException;

// === xdr source ============================================================

//  struct TransactionResult
//  {
//      int64 feeCharged; // actual fee charged for the transaction
//  
//      union switch (TransactionResultCode code)
//      {
//      case txSUCCESS:
//      case txFAILED:
//          OperationResult results<>;
//      default:
//          void;
//      }
//      result;
//  
//      // reserved for future use
//      union switch (int v)
//      {
//      case 0:
//          void;
//      }
//      ext;
//  };

//  ===========================================================================
public class TransactionResult  {
  public TransactionResult () {}
  private Int64 feeCharged;
  public Int64 getFeeCharged() {
    return this.feeCharged;
  }
  public void setFeeCharged(Int64 value) {
    this.feeCharged = value;
  }
  private TransactionResultResult result;
  public TransactionResultResult getResult() {
    return this.result;
  }
  public void setResult(TransactionResultResult value) {
    this.result = value;
  }
  private TransactionResultExt ext;
  public TransactionResultExt getExt() {
    return this.ext;
  }
  public void setExt(TransactionResultExt value) {
    this.ext = value;
  }
  public static void encode(XdrDataOutputStream stream, TransactionResult encodedTransactionResult) throws IOException{
    Int64.encode(stream, encodedTransactionResult.feeCharged);
    TransactionResultResult.encode(stream, encodedTransactionResult.result);
    TransactionResultExt.encode(stream, encodedTransactionResult.ext);
  }
  public static TransactionResult decode(XdrDataInputStream stream) throws IOException {
    TransactionResult decodedTransactionResult = new TransactionResult();
    decodedTransactionResult.feeCharged = Int64.decode(stream);
    decodedTransactionResult.result = TransactionResultResult.decode(stream);
    decodedTransactionResult.ext = TransactionResultExt.decode(stream);
    return decodedTransactionResult;
  }

  public static class TransactionResultResult {
    public TransactionResultResult () {}
    TransactionResultCode code;
    public TransactionResultCode getDiscriminant() {
      return this.code;
    }
    public void setDiscriminant(TransactionResultCode value) {
      this.code = value;
    }
    private OperationResult[] results;
    public OperationResult[] getResults() {
      return this.results;
    }
    public void setResults(OperationResult[] value) {
      this.results = value;
    }
    public static void encode(XdrDataOutputStream stream, TransactionResultResult encodedTransactionResultResult) throws IOException {
    stream.writeInt(encodedTransactionResultResult.getDiscriminant().getValue());
    switch (encodedTransactionResultResult.getDiscriminant()) {
    case txSUCCESS:
    case txFAILED:
    int resultssize = encodedTransactionResultResult.getResults().length;
    stream.writeInt(resultssize);
    for (int i = 0; i < resultssize; i++) {
      OperationResult.encode(stream, encodedTransactionResultResult.results[i]);
    }
    break;
    default:
    break;
    }
    }
    public static TransactionResultResult decode(XdrDataInputStream stream) throws IOException {
    TransactionResultResult decodedTransactionResultResult = new TransactionResultResult();
    TransactionResultCode discriminant = TransactionResultCode.decode(stream);
    decodedTransactionResultResult.setDiscriminant(discriminant);
    switch (decodedTransactionResultResult.getDiscriminant()) {
    case txSUCCESS:
    case txFAILED:
    int resultssize = stream.readInt();
    decodedTransactionResultResult.results = new OperationResult[resultssize];
    for (int i = 0; i < resultssize; i++) {
      decodedTransactionResultResult.results[i] = OperationResult.decode(stream);
    }
    break;
    default:
    break;
    }
      return decodedTransactionResultResult;
    }

  }
  public static class TransactionResultExt {
    public TransactionResultExt () {}
    Integer v;
    public Integer getDiscriminant() {
      return this.v;
    }
    public void setDiscriminant(Integer value) {
      this.v = value;
    }
    public static void encode(XdrDataOutputStream stream, TransactionResultExt encodedTransactionResultExt) throws IOException {
    stream.writeInt(encodedTransactionResultExt.getDiscriminant().intValue());
    switch (encodedTransactionResultExt.getDiscriminant()) {
    case 0:
    break;
    }
    }
    public static TransactionResultExt decode(XdrDataInputStream stream) throws IOException {
    TransactionResultExt decodedTransactionResultExt = new TransactionResultExt();
    Integer discriminant = stream.readInt();
    decodedTransactionResultExt.setDiscriminant(discriminant);
    switch (decodedTransactionResultExt.getDiscriminant()) {
    case 0:
    break;
    }
      return decodedTransactionResultExt;
    }

  }
}
