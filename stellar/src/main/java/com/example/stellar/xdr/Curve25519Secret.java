// Automatically generated by xdrgen 
// DO NOT EDIT or your changes may be overwritten

package com.example.stellar.xdr;


import java.io.IOException;

// === xdr source ============================================================

//  struct Curve25519Secret
//  {
//          opaque key[32];
//  };

//  ===========================================================================
public class Curve25519Secret  {
  public Curve25519Secret () {}
  private byte[] key;
  public byte[] getKey() {
    return this.key;
  }
  public void setKey(byte[] value) {
    this.key = value;
  }
  public static void encode(XdrDataOutputStream stream, Curve25519Secret encodedCurve25519Secret) throws IOException{
    int keysize = encodedCurve25519Secret.key.length;
    stream.write(encodedCurve25519Secret.getKey(), 0, keysize);
  }
  public static Curve25519Secret decode(XdrDataInputStream stream) throws IOException {
    Curve25519Secret decodedCurve25519Secret = new Curve25519Secret();
    int keysize = 32;
    decodedCurve25519Secret.key = new byte[keysize];
    stream.read(decodedCurve25519Secret.key, 0, keysize);
    return decodedCurve25519Secret;
  }
}
