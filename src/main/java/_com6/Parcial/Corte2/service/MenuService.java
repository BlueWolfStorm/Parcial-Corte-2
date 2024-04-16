package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.Menu;
import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.model.SubMenu;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final SubMenuService subMenuService;
    private final UserAccountService userAccountService;

    @Autowired
    public MenuService(MenuRepository menuRepository, UserAccountService userAccountService, SubMenuService subMenuService){
        this.menuRepository = menuRepository;
        this.userAccountService = userAccountService;
        this.subMenuService = subMenuService;
    }

    @Transactional
    public Menu createUpdate(Menu entity){
        if(entity.getId() == null)
            entity.setCreationDate(LocalDateTime.now());


        return menuRepository.save(entity);
    }

    public Page<Menu> findAll(Pageable pageable){
        return menuRepository.findAll(pageable);
    }

    public Menu findById(Long id){
        return menuRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        menuRepository.deleteById(id);
    }

    public List<SubMenu> getSubMenuByMenu(Long menuId, Long userId, boolean isAdmin){
        Menu menu = menuRepository.findById(menuId).orElse(null);
        if(isAdmin)
            return subMenuService.findByMenuId(menuId);

        UserAccount user = userAccountService.findById(userId);

        for(Role role : user.getRoleList()){
            for(Menu roleMenu : role.getMenusAvailable()){
                if(roleMenu.getId().equals(menuId))
                    return subMenuService.findByMenuId(menuId);
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to make this action!");
    }
}
