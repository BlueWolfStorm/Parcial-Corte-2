package _com6.Parcial.Corte2.security.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JwtProperties(String key, Long accessTokenExpiration) {
}
