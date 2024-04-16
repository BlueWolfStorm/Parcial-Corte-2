package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.Menu;
import _com6.Parcial.Corte2.model.SubMenu;
import _com6.Parcial.Corte2.service.MenuService;
import _com6.Parcial.Corte2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;
    private final TokenService tokenService;

    @Autowired
    public MenuController(MenuService menuService, TokenService tokenService) {
        this.menuService = menuService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create-update")
    public Menu createMenu(@RequestBody Menu entity) {
        return menuService.createUpdate(entity);
    }

    @GetMapping("/get-all")
    public List<Menu> getMenu(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return menuService.findAll(pageable).getContent();
    }

    @GetMapping("/get-submenu/{menuId}")
    public List<SubMenu> getSubMenu(@PathVariable Long menuId, HttpServletRequest request) {
        Long userId = tokenService.extractId(tokenService.extractToken(request));
        boolean isAdmin = tokenService.isAdministrator(tokenService.extractToken(request));
        return menuService.getSubMenuByMenu(menuId, userId, isAdmin);
    }

    @GetMapping("/get-by/{id}")
    public Menu getById(@PathVariable Long id) {
        return menuService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }
}
