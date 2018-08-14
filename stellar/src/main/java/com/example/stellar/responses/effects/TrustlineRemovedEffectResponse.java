package com.example.stellar.responses.effects;

/**
 * Represents trustline_removed effect response.
 * @see <a href="https://www.stellar.org/developers/horizon/reference/resources/effect.html" target="_blank">Effect documentation</a>
 * @see com.example.stellar.requests.EffectsRequestBuilder
 * @see com.example.stellar.Server#effects()
 */
public class TrustlineRemovedEffectResponse extends TrustlineCUDResponse {
  TrustlineRemovedEffectResponse(String limit, String assetType, String assetCode, String assetIssuer) {
    super(limit, assetType, assetCode, assetIssuer);
  }
}
