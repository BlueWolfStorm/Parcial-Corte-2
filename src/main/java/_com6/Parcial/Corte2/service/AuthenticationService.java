package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.security.model.AuthenticationRequest;
import _com6.Parcial.Corte2.security.model.AuthenticationResponse;
import _com6.Parcial.Corte2.security.model.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;
    private final UserAccountService userAccountService;
    private final NotificationHistoryService notificationHistoryService;
    private final LoginHistoryService loginHistoryService;
    private final EmailSenderService emailSenderService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService, TokenService tokenService, JwtProperties jwtProperties, UserAccountService userAccountService, NotificationHistoryService notificationHistoryService, LoginHistoryService loginHistoryService, EmailSenderService emailSenderService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
        this.tokenService = tokenService;
        this.jwtProperties = jwtProperties;
        this.userAccountService = userAccountService;
        this.notificationHistoryService = notificationHistoryService;
        this.loginHistoryService = loginHistoryService;
        this.emailSenderService = emailSenderService;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest){
        AuthenticationResponse response = authenticate(authRequest);

        if(response != null){
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            executorService.submit(() -> notificationHistoryService.send(response.user()));
            executorService.submit(() -> loginHistoryService.register(response.user()));
            executorService.submit(() -> emailSenderService.send(response.user()));
            executorService.shutdown();
            return response;
        }

        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );

        UserAccount userAccount = userAccountService.findByEmail(authRequest.email());

        UserDetails user = customUserDetailService.mapToUserDetails(userAccount);

        boolean isAdmin = userAccount.getRoleList().stream().anyMatch(role -> role.getId() == 1);

        String accessToken = generateAccessToken(user, userAccount.getId(), isAdmin);

        return new AuthenticationResponse(
                accessToken,
                new Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration()),
                userAccount
        );

    }

    private String generateAccessToken(UserDetails user, Long id, boolean isAdmin) {
        return tokenService.generate(
                user,
                new Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration()),
                Map.of("userId", id, "isAdmin", isAdmin)
        );
    }
}
