package io.craigmiller160.emailservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record EmailRequest(
    @JsonProperty("toAddresses") List<String> toAddresses,
    @JsonProperty("ccAddresses") List<String> ccAddresses,
    @JsonProperty("bccAddresses") List<String> bccAddresses,
    @JsonProperty("subject") String subject,
    @JsonProperty("text") String text) {}
