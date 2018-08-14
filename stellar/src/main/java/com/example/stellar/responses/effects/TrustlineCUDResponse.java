package com.example.stellar.responses.effects;

import com.google.gson.annotations.SerializedName;

import com.example.stellar.Asset;
import com.example.stellar.AssetTypeNative;
import com.example.stellar.KeyPair;

abstract class TrustlineCUDResponse extends EffectResponse {
  @SerializedName("limit")
  protected final String limit;
  @SerializedName("asset_type")
  protected final String assetType;
  @SerializedName("asset_code")
  protected final String assetCode;
  @SerializedName("asset_issuer")
  protected final String assetIssuer;

  public TrustlineCUDResponse(String limit, String assetType, String assetCode, String assetIssuer) {
    this.limit = limit;
    this.assetType = assetType;
    this.assetCode = assetCode;
    this.assetIssuer = assetIssuer;
  }

  public String getLimit() {
    return limit;
  }

  public Asset getAsset() {
    if (assetType.equals("native")) {
      return new AssetTypeNative();
    } else {
      KeyPair issuer = KeyPair.fromAccountId(assetIssuer);
      return Asset.createNonNativeAsset(assetCode, issuer);
    }
  }
}
