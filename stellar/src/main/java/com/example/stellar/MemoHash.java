package com.example.stellar;

import com.example.stellar.xdr.MemoType;

/**
 * Represents MEMO_HASH.
 */
public class MemoHash extends MemoHashAbstract {
  public MemoHash(byte[] bytes) {
    super(bytes);
  }

  public MemoHash(String hexString) {
    super(hexString);
  }

  @Override
  com.example.stellar.xdr.Memo toXdr() {
    com.example.stellar.xdr.Memo memo = new com.example.stellar.xdr.Memo();
    memo.setDiscriminant(MemoType.MEMO_HASH);

    com.example.stellar.xdr.Hash hash = new com.example.stellar.xdr.Hash();
    hash.setHash(bytes);

    memo.setHash(hash);
    return memo;
  }
}
