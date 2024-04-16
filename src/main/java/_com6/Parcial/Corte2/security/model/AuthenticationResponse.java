package _com6.Parcial.Corte2.security.model;

import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.model.UserAccount;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record AuthenticationResponse(
        String accessToken,
        Date accessExpirationTime,
        UserAccount user
) {
}
