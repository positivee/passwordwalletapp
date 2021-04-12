package pl.biernacki.passwordwalletapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.biernacki.passwordwalletapp.entity.Actions;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.PasswordHistory;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.PasswordHistoryRepository;
import pl.biernacki.passwordwalletapp.service.ActionService;
import pl.biernacki.passwordwalletapp.service.PasswordHistoryService;
import pl.biernacki.passwordwalletapp.service.PasswordService;
import pl.biernacki.passwordwalletapp.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
@RequiredArgsConstructor
public class ActionHistoryController {
    private final ActionService actionService;
    private final UserService userService;
    private final PasswordHistoryService passwordHistoryService;

    private final PasswordService passwordService;
    private final PasswordHistoryRepository passwordHistoryRepository;
    @GetMapping("/login/{id}/actions")
    public String getActionTable(@PathVariable(name = "id") Long id, Model model, HttpSession httpSession){
        List<Actions> actionsList = actionService.getActionsForUser(httpSession.getAttribute("user").toString());
        User user = userService.findByLogin(httpSession.getAttribute("user").toString());
        model.addAttribute("actions", actionsList);
        model.addAttribute("user", user);
        return "userAction";
    }

    @GetMapping("/login/{id}/{passId}/history")
    public String showPasswordHistory(@PathVariable(name = "id") Long id, @PathVariable(name = "passId") Long password_id, Model model, HttpSession httpSession){
        List<PasswordHistory> historyList = passwordHistoryService.findAllByPasswordId(password_id);
        User user = userService.findByLogin(httpSession.getAttribute("user").toString());
        model.addAttribute("historyList", historyList);
        model.addAttribute("user", user);
        model.addAttribute("password_id", password_id);
        return "passwordHistory";
    }



    @GetMapping("/login/{id}/{password_id}/history/{passwordhistory_id}/goback")
    public String restorePassword(@PathVariable Long id,
                                  @PathVariable Long password_id,
                                  @PathVariable Long passwordhistory_id,
                                  Model model,
                                  HttpSession httpSession){
        Password restoredPassword = passwordService.restorePassword(password_id, passwordhistory_id);
        actionService.saveAction("Restored password", httpSession.getAttribute("user").toString());
        return "redirect:/login/" + id ;
    }
}
