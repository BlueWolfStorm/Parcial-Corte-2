package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.GrantPermission;
import _com6.Parcial.Corte2.model.Menu;
import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.service.RoleService;
import _com6.Parcial.Corte2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    private final TokenService tokenService;

    @Autowired
    public RoleController(RoleService roleService, TokenService tokenService) {
        this.roleService = roleService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create-update")
    public Role createRole(@RequestBody Role entity, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return roleService.createUpdate(entity);
    }


    @GetMapping("/get-all")
    public List<Role> getRole(@PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return roleService.findAll(pageable).getContent();
    }

    @GetMapping("/get-all-my-menus")
    public List<Menu> getMenus(@PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        Long userId = tokenService.extractId(tokenService.extractToken(request));
        return roleService.findMenus(pageable, userId).getContent();
    }

    @GetMapping("/get-by/{id}")
    public Role getById(@PathVariable Long id, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return roleService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        roleService.delete(id);
    }
}
