// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten

package com.example.stellar.xdr;


import java.io.IOException;

// === xdr source ============================================================

//  typedef unsigned int uint32;

//  ===========================================================================
public class Uint32  {
  private Integer uint32;
  public Integer getUint32() {
    return this.uint32;
  }
  public void setUint32(Integer value) {
    this.uint32 = value;
  }
  public static void encode(XdrDataOutputStream stream, Uint32  encodedUint32) throws IOException {
  stream.writeInt(encodedUint32.uint32);
  }
  public static Uint32 decode(XdrDataInputStream stream) throws IOException {
    Uint32 decodedUint32 = new Uint32();
  decodedUint32.uint32 = stream.readInt();
    return decodedUint32;
  }
}
