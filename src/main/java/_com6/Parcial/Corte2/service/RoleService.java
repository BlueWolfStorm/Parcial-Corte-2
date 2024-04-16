package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.GrantPermission;
import _com6.Parcial.Corte2.model.Menu;
import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.repository.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role createUpdate(Role entity) {
        if (entity.getId() == null)
            entity.setCreationDate(LocalDateTime.now());

        return roleRepository.save(entity);
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Page<Menu> findMenus(Pageable pageable, Long id) {
        List<Role> roleList = roleRepository.findByUserId(id);
        List<Long> roleListId = roleList.stream().map(Role::getId).toList();

        return roleRepository.findMenus(roleListId, pageable);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        if (id == 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot delete the administrator role!");
        roleRepository.deleteById(id);
    }
}
