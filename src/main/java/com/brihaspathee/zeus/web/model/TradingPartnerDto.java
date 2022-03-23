package com.brihaspathee.zeus.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 19, January 2022
 * Time: 3:09 PM
 * Project: Zeus
 * Package Name: com.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Trading Partner Object")
@Validated
public class TradingPartnerDto {

    @JsonProperty(required = false)
    @Schema(description = "Trading Partner SK - This is a UUID value", example = "657cfd75-634e-49f1-9556-4d79f79848ec", required = false, accessMode = Schema.AccessMode.READ_ONLY)
    @Null
    private UUID tradingPartnerSK;

    @JsonProperty(required = true)
    @Schema(description = "Trading Partner ID", example = "683234GHD42")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 100)
    private String tradingPartnerId;

    @JsonProperty(required = true)
    @Schema(description = "Trading Partner Name", example = "FL FFM")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @JsonProperty(required = false)
    @Schema(description = "A short description of the trading partner", example = "FL State FFM TP")
    private String description;

    @JsonProperty(required = true)
    @Schema(description = "Sender Id configured for the TP", example = "CMSFFM")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 100)
    private String senderId;

    @JsonProperty(required = true)
    @Schema(description = "Receiver Id configured for the TP", example = "96633")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 100)
    private String receiverId;

    @JsonProperty(required = true)
    @Schema(description = "LOB associated with the trading partner", example = "HIX")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 100)
    private String lineOfBusinessTypeCode;

    @JsonProperty(required = true)
    @Schema(description = "Marketplace Type Code of the trading partner", example = "FFM")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 10)
    private String marketplaceTypeCode;

    @JsonProperty(required = true)
    @Schema(description = "State of the trading partner", example = "FL")
    //@NotNull
    //@NotBlank
    @Size(min = 2, max = 2)
    private String stateTypeCode;

    @Override
    public String toString() {
        return "TradingPartnerDto{" +
                "tradingPartnerSK=" + tradingPartnerSK +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradingPartnerDto that = (TradingPartnerDto) o;
        return tradingPartnerSK.equals(that.tradingPartnerSK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradingPartnerSK);
    }
}
