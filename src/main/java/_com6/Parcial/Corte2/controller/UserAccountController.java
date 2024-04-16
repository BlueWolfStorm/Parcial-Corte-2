package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.GrantPermission;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.security.model.AuthenticationRequest;
import _com6.Parcial.Corte2.security.model.AuthenticationResponse;
import _com6.Parcial.Corte2.service.AuthenticationService;
import _com6.Parcial.Corte2.service.TokenService;
import _com6.Parcial.Corte2.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-account")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public UserAccountController(UserAccountService userAccountService, AuthenticationService authenticationService, TokenService tokenService) {
        this.userAccountService = userAccountService;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create-update")
    public UserAccount createUserAccount(@RequestBody UserAccount entity, HttpServletRequest request) {
        if(entity.getId() != null && !entity.getId().equals(tokenService.extractId(tokenService.extractToken(request))))
            tokenService.hasEnoughPermissions(request);

        return userAccountService.createUpdate(entity);
    }

    @GetMapping("/get-all")
    public List<UserAccount> getUserAccount(@PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return userAccountService.findAll(pageable).getContent();
    }

    @GetMapping("/get-my-profile")
    public UserAccount getMyProfile(HttpServletRequest request) {
        Long id = tokenService.extractId(tokenService.extractToken(request));
        return userAccountService.getMyProfile(id);
    }

    @PostMapping("/grant-permission")
    public void grantPermission(@RequestBody GrantPermission grant, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        userAccountService.grantPermission(grant);
    }

    @PostMapping("/revoke-permission")
    public void revokePermission(@RequestBody GrantPermission grant, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        userAccountService.revokePermission(grant);
    }

    @GetMapping("/get-by/{id}")
    public UserAccount getById(@PathVariable Long id, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return userAccountService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        userAccountService.delete(id);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authenticationService.login(request);
    }

}
