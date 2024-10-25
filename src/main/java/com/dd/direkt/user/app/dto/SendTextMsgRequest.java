package com.dd.direkt.user.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendTextMsgRequest {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long from;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long to;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senderEmail;

//    @NotNull
    private LocalDateTime createdAt;

    @NotBlank
    private String data;
}
