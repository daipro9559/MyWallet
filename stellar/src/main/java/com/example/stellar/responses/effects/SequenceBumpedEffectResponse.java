package com.example.stellar.responses.effects;

import com.google.gson.annotations.SerializedName;

/**
 * Represents sequence_bumped effect response.
 * @see <a href="https://www.stellar.org/developers/horizon/reference/resources/effect.html" target="_blank">Effect documentation</a>
 * @see com.example.stellar.requests.EffectsRequestBuilder
 * @see com.example.stellar.Server#effects()
 */
public class SequenceBumpedEffectResponse extends EffectResponse {
    @SerializedName("new_seq")
    protected final Long newSequence;

    public SequenceBumpedEffectResponse(Long newSequence) {
        this.newSequence = newSequence;
    }

    public Long getNewSequence() {
        return newSequence;
    }
}
