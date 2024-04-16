package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.SubMenu;
import _com6.Parcial.Corte2.repository.SubMenuRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubMenuService {
    private final SubMenuRepository subMenuRepository;

    public SubMenuService(SubMenuRepository subMenuRepository) {
        this.subMenuRepository = subMenuRepository;
    }

    @Transactional
    public SubMenu createUpdate(SubMenu entity){
        if(entity.getId() == null)
            entity.setCreationDate(LocalDateTime.now());
        return subMenuRepository.save(entity);
    }

    public Page<SubMenu> findAll(Pageable pageable){
        return subMenuRepository.findAll(pageable);
    }

    public SubMenu findById(Long id){
        return subMenuRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        subMenuRepository.deleteById(id);
    }

    public List<SubMenu> findByMenuId(Long menuId) {
        return subMenuRepository.findByMenuId(menuId);
    }
}
