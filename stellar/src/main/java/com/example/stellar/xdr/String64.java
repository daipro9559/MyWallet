// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten

package com.example.stellar.xdr;


import java.io.IOException;

// === xdr source ============================================================

//  typedef string string64<64>;

//  ===========================================================================
public class String64  {
  private String string64;
  public String getString64() {
    return this.string64;
  }
  public void setString64(String value) {
    this.string64 = value;
  }
  public static void encode(XdrDataOutputStream stream, String64  encodedString64) throws IOException {
  stream.writeString(encodedString64.string64);
  }
  public static String64 decode(XdrDataInputStream stream) throws IOException {
    String64 decodedString64 = new String64();
  decodedString64.string64 = stream.readString();
    return decodedString64;
  }
}
