package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.LoginHistory;
import _com6.Parcial.Corte2.service.LoginHistoryService;
import _com6.Parcial.Corte2.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login-history")
public class LoginHistoryController {
    private final LoginHistoryService loginHistoryService;
    private final TokenService tokenService;

    @Autowired
    public LoginHistoryController(LoginHistoryService loginHistoryService, TokenService tokenService) {
        this.loginHistoryService = loginHistoryService;
        this.tokenService = tokenService;
    }

    @GetMapping("/get-all")
    public List<LoginHistory> getLoginHistory(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return loginHistoryService.findAll(pageable).getContent();
    }

    @GetMapping("/get-by/{id}")
    public LoginHistory getById(@PathVariable Long id) {
        return loginHistoryService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        loginHistoryService.delete(id);
    }
}
