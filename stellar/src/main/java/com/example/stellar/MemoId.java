package com.example.stellar;

import com.example.stellar.xdr.MemoType;
import com.example.stellar.xdr.Uint64;

/**
 * Represents MEMO_ID.
 */
public class MemoId extends Memo {
  private long id;

  public MemoId(long id) {
    if (id < 0) {
      throw new IllegalArgumentException("id must be a positive number");
    }
    this.id = id;
  }

  public long getId() {
    return id;
  }

  @Override
  com.example.stellar.xdr.Memo toXdr() {
    com.example.stellar.xdr.Memo memo = new com.example.stellar.xdr.Memo();
    memo.setDiscriminant(MemoType.MEMO_ID);
    Uint64 idXdr = new Uint64();
    idXdr.setUint64(id);
    memo.setId(idXdr);
    return memo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MemoId memoId = (MemoId) o;
    return id == memoId.id;
  }
}
