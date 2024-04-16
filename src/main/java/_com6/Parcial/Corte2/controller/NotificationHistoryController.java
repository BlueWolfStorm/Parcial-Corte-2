package _com6.Parcial.Corte2.controller;

import _com6.Parcial.Corte2.model.NotificationHistory;
import _com6.Parcial.Corte2.service.NotificationHistoryService;
import _com6.Parcial.Corte2.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-history")
public class NotificationHistoryController {
    private final NotificationHistoryService notificationHistoryService;
    private final TokenService tokenService;

    @Autowired
    public NotificationHistoryController(NotificationHistoryService notificationHistoryService, TokenService tokenService) {
        this.notificationHistoryService = notificationHistoryService;
        this.tokenService = tokenService;
    }

    @GetMapping("/get-all")
    public List<NotificationHistory> getNotificationHistory(@PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return notificationHistoryService.findAll(pageable).getContent();
    }

    @GetMapping("/get-by/{id}")
    public List<NotificationHistory> getById(@PathVariable Long id, @PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        return notificationHistoryService.findByUserId(pageable, id).getContent();
    }

    @GetMapping("/get-by-myself")
    public List<NotificationHistory> getById(@PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
        Long id = tokenService.extractId(tokenService.extractToken(request));
        return notificationHistoryService.findByUserId(pageable, id).getContent();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        tokenService.hasEnoughPermissions(request);
        notificationHistoryService.delete(id);
    }
}
