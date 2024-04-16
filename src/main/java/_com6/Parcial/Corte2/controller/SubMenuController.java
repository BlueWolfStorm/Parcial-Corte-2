package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.SubMenu;
import _com6.Parcial.Corte2.service.SubMenuService;
import _com6.Parcial.Corte2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-menu")
public class SubMenuController {
    private final SubMenuService subMenuService;
    private final TokenService tokenService;

    public SubMenuController(SubMenuService subMenuService, TokenService tokenService) {
        this.subMenuService = subMenuService;
        this.tokenService = tokenService;
    }

    @PostMapping("/create-update")
    public SubMenu createSubMenu(@RequestBody SubMenu entity, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return subMenuService.createUpdate(entity);
    }

    @GetMapping("/get-all")
    public List<SubMenu> getSubMenu(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return subMenuService.findAll(pageable).getContent();
    }

    @GetMapping("/get-by/{id}")
    public SubMenu getById(@PathVariable Long id) {
        return subMenuService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        subMenuService.delete(id);
    }
}
