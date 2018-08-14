// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten

package com.example.stellar.xdr;


import java.io.IOException;

// === xdr source ============================================================

//  union CreateAccountResult switch (CreateAccountResultCode code)
//  {
//  case CREATE_ACCOUNT_SUCCESS:
//      void;
//  default:
//      void;
//  };

//  ===========================================================================
public class CreateAccountResult  {
  public CreateAccountResult () {}
  CreateAccountResultCode code;
  public CreateAccountResultCode getDiscriminant() {
    return this.code;
  }
  public void setDiscriminant(CreateAccountResultCode value) {
    this.code = value;
  }
  public static void encode(XdrDataOutputStream stream, CreateAccountResult encodedCreateAccountResult) throws IOException {
  stream.writeInt(encodedCreateAccountResult.getDiscriminant().getValue());
  switch (encodedCreateAccountResult.getDiscriminant()) {
  case CREATE_ACCOUNT_SUCCESS:
  break;
  default:
  break;
  }
  }
  public static CreateAccountResult decode(XdrDataInputStream stream) throws IOException {
  CreateAccountResult decodedCreateAccountResult = new CreateAccountResult();
  CreateAccountResultCode discriminant = CreateAccountResultCode.decode(stream);
  decodedCreateAccountResult.setDiscriminant(discriminant);
  switch (decodedCreateAccountResult.getDiscriminant()) {
  case CREATE_ACCOUNT_SUCCESS:
  break;
  default:
  break;
  }
    return decodedCreateAccountResult;
  }
}
