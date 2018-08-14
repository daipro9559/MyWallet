package com.example.stellar.responses.effects;

import com.google.gson.annotations.SerializedName;

/**
 * Represents account_flags_updated effect response.
 * @see <a href="https://www.stellar.org/developers/horizon/reference/resources/effect.html" target="_blank">Effect documentation</a>
 * @see com.example.stellar.requests.EffectsRequestBuilder
 * @see com.example.stellar.Server#effects()
 */
public class AccountFlagsUpdatedEffectResponse extends EffectResponse {
  @SerializedName("auth_required_flag")
  protected final Boolean authRequiredFlag;
  @SerializedName("auth_revokable_flag")
  protected final Boolean authRevokableFlag;

  AccountFlagsUpdatedEffectResponse(Boolean authRequiredFlag, Boolean authRevokableFlag) {
    this.authRequiredFlag = authRequiredFlag;
    this.authRevokableFlag = authRevokableFlag;
  }

  public Boolean getAuthRequiredFlag() {
    return authRequiredFlag;
  }

  public Boolean getAuthRevokableFlag() {
    return authRevokableFlag;
  }
}
