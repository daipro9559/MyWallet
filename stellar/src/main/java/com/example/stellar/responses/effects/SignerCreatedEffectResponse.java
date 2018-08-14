package com.example.stellar.responses.effects;

/**
 * Represents signer_created effect response.
 * @see <a href="https://www.stellar.org/developers/horizon/reference/resources/effect.html" target="_blank">Effect documentation</a>
 * @see com.example.stellar.requests.EffectsRequestBuilder
 * @see com.example.stellar.Server#effects()
 */
public class SignerCreatedEffectResponse extends SignerEffectResponse {
  SignerCreatedEffectResponse(Integer weight, String publicKey) {
    super(weight, publicKey);
  }
}
