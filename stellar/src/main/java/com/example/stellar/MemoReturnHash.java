package com.example.stellar;

import com.example.stellar.xdr.Memo;
import com.example.stellar.xdr.MemoType;

/**
 * Represents MEMO_RETURN.
 */
public class MemoReturnHash extends MemoHashAbstract {
  public MemoReturnHash(byte[] bytes) {
    super(bytes);
  }

  public MemoReturnHash(String hexString) {
    super(hexString);
  }

  @Override
  Memo toXdr() {
    com.example.stellar.xdr.Memo memo = new com.example.stellar.xdr.Memo();
    memo.setDiscriminant(MemoType.MEMO_RETURN);

    com.example.stellar.xdr.Hash hash = new com.example.stellar.xdr.Hash();
    hash.setHash(bytes);

    memo.setRetHash(hash);
    return memo;
  }
}
