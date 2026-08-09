package br.com.danilodps.coordinator.domain;

import java.time.Instant;

public record SecretEntry(String version, String secret, Instant createdAt) {}
