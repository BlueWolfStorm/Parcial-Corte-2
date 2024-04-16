package _com6.Parcial.Corte2.repository;

import _com6.Parcial.Corte2.model.SubMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    @Query("SELECT s FROM SubMenu s WHERE s.menu.id = ?1")
    List<SubMenu> findByMenuId(Long menuId);
}
