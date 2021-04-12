package pl.biernacki.passwordwalletapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.biernacki.passwordwalletapp.Encryption.Aes;
import pl.biernacki.passwordwalletapp.Encryption.Hmac;
import pl.biernacki.passwordwalletapp.Encryption.Sha512;
import pl.biernacki.passwordwalletapp.entity.*;
import pl.biernacki.passwordwalletapp.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AppController {


    private final UserService userService;

    private final PasswordService passwordService;
    private final IpAddressAttemptService addressService;
    private final LoginAttemptService loginService;
    private final SharePasswordService sharePasswordService;
    private final ActionService actionService;

    private final String pepper = "test";

    Hmac hmac = new Hmac();

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
    /*Get mapping for register geting register form*/
    @GetMapping("/register")
    public String showRegister(Model model){
        model.addAttribute("user", new User());
        return "registerForm";
    }
    /*Post mapping for register saving new account to database*/
    @PostMapping("/process_register")
    public String processRegistration(User user,Model model)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException{

        //checking if user exists
        if(userService.existByLogin(user.getLogin())) {
            model.addAttribute("error", true);
            return "registerForm";
        }

        model.addAttribute("user", user);
        //saving user
     /*   userRepository.save(user);*/
        userService.saveUser(user, Sha512.generateSalt().toString());
        return "registerSuccessful";
    }
    /*Get mapping for login giving form*/
    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new User());
        return "loginForm";
    }
        /*   Processing login saving to database   */
    @RequestMapping("/process_login")
    public String processLogin(User user, Model model,HttpSession session, HttpServletRequest httpServletRequest, HttpSession httpSession) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        IpAddressAttempt addressAttempt;
        session.setAttribute("mode", "read");

        if(userService.existByLogin(user.getLogin())){
            Optional<User> checkUser = userService.checkPassword(user);
            if(!addressService.addressExists(httpServletRequest.getRemoteAddr()))
                addressAttempt = addressService.saveAddressAttempt(httpServletRequest.getRemoteAddr());
            else
                addressAttempt = addressService.getAddressAttempt(httpServletRequest.getRemoteAddr());

            if(!addressService.verifyAddressAttempts(httpServletRequest.getRemoteAddr())){
                model.addAttribute("errorAddress", true);
                model.addAttribute("addressAttempt", addressAttempt);


            }
            else{
                if(!loginService.existsByUserLogin(user.getLogin())){
                    loginService.saveUserAttempt(user);
                }
                if(!loginService.verifyLoginAttempts(user)){
                    model.addAttribute("errorUser", true);
                    model.addAttribute("userAttempt", loginService.getLoginAttempt(user.getLogin()));
                }
                else{
                    if(checkUser.isPresent()) { //check password match
                        model.addAttribute("user", checkUser.get());
                        model.addAttribute("userAttempt", checkUser.get().getLoginAttempt());
                        loginService.resetAttempts(user);
                        addressService.addressAddCorrectAttempts(httpServletRequest.getRemoteAddr());
                        httpSession.setAttribute("user", user.getLogin());
                        actionService.saveAction("Logged In", httpSession.getAttribute("user").toString());

                        return "redirect:/login/" + checkUser.get().getId();
                    }
                    loginService.addAttempts(user);
                    addressService.addressAddIncorrectAttempts(httpServletRequest.getRemoteAddr());
                    model.addAttribute("error", true);
                }
            }
        }
        else {
            model.addAttribute("error", true);
        }

        return "loginForm";



    }

    /* Get mapping for changing password, it gives form*/
    @GetMapping("/login/{id}/changePassword")
    public String showChangePasswordForm(@PathVariable(name = "id") Long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("tempUser", new TempUser());
        model.addAttribute("user", user);
        return "changePassword";
    }
    /* Post mapping for changing password*/
    @PostMapping("/login/{id}/changePassword")
    public String changeUserPassword(@PathVariable(name = "id") Long id, TempUser tempUser, Model model, HttpSession session)
            throws Exception {

        User user = userService.getUser(id);

        userService.changePassword(tempUser,user);
        session.setAttribute("password", tempUser.getNewPass());
        model.addAttribute("tempUser", tempUser);
        model.addAttribute("user", user);
        return "redirect:/login/" + user.getId();
    }

    /*mapping for main page getting all password hashed*/
    @GetMapping("/login/{id}")
    public String loginScreen(@PathVariable(name = "id") Long id, Model model,Password password,HttpSession session) throws Exception {
        User user = userService.getUser(id);

        for(SharePassword temp : sharePasswordService.getSharedPassword(user.getLogin())){
            user.getPasswordList().add(temp.getPassword());
        }

        model.addAttribute("mode", session.getAttribute("mode"));
        model.addAttribute("user", user);
        model.addAttribute("passwords",  user.getPasswordList());
        model.addAttribute("userAttempt", user.getLoginAttempt());
        return "afterLogin";
        }

    /*   table with decrypt on of the passwords */
    @GetMapping("/login/{id}/{passId}")
    public String loginScreen(@PathVariable(name = "id") Long id, @PathVariable(name = "passId",required = false) Long passId, Model model,HttpSession session) throws Exception {
        User user = userService.getUser(id);

        Password password = passwordService.getPassword(passId);

        for(SharePassword temp : sharePasswordService.getSharedPassword(user.getLogin())){
            user.getPasswordList().add(temp.getPassword());
        }
        actionService.saveAction("Decrypted password", session.getAttribute("user").toString());

        int i = 0;
        for(Password temp : user.getPasswordList()){
            if(temp == password)
                if(!temp.getSharePasswords().isEmpty()) {
                    temp.setPasswordWeb(Aes.decrypt(password.getPasswordWeb(), userService.findByLogin(temp.getSharePasswords().get(i).getOwnerLogin()).getPasswordHash()));//user.getPasswordHash()
                    i++;
                }else{
                    temp.setPasswordWeb(Aes.decrypt(password.getPasswordWeb(), user.getPasswordHash()));
                }
        }
        model.addAttribute("mode", session.getAttribute("mode"));
        model.addAttribute("userAttempt",user.getLoginAttempt());
        model.addAttribute("user", user);
        model.addAttribute("passwords",  user.getPasswordList());
        return "afterLogin";
    }
    /* mapping for addding password to you wallet*/
    @GetMapping("/login/{id}/addPassword")
    public String addPasswordShowForm(@PathVariable(name = "id") Long id, Model model, HttpSession session){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("password", new Password());


        return "addPassword";
    }
    /*Post maping for procesing post request password*/
    @PostMapping("/login/{id}/processSavedPassword")
    public String processSavedPassword(@PathVariable(name = "id") Long id, Password password, HttpSession session) throws Exception {
        User user = userService.getUser(id);
        passwordService.savePassword(password,user);
        actionService.saveAction("Added password", session.getAttribute("user").toString());
        return "redirect:/login/{id}";

    }
    /* unlock adress ip*/
    @GetMapping("/unlock")
    public String unblockAddress(HttpServletRequest request) {

        addressService.deleteByAddress(request.getRemoteAddr());
        return "redirect:/login";
    }


    //change user mode -- modes: "modify, read"
    @GetMapping("/login/{id}/change-mode")
    public String changeMode(@PathVariable(name = "id") Long id, HttpSession httpSession){
        if(httpSession.getAttribute("mode").equals("read"))
            httpSession.setAttribute("mode", "modify");
        else
            httpSession.setAttribute("mode", "read");

        return "redirect:/login/" + id ;
    }
    //showing form
    @GetMapping("/login/{id}/{passId}/edit")
    public String editPassword(@PathVariable Long id, @PathVariable Long passId, Model model,HttpSession session){
        User user = userService.getUser(id);
        Password password = passwordService.getPassword(passId);
        model.addAttribute("mode", session.getAttribute("mode"));
        model.addAttribute("password", password);
        model.addAttribute("user", user);
        return "editPassword";
    }
    //post on edit form
    @PostMapping("/login/{id}/{passId}/edit")
    public String editPasswordForm(@PathVariable Long id, @PathVariable Long passId, Password password, Model model,HttpSession session) throws Exception {
        User user = userService.getUser(id);
        Password savedPassword = passwordService.editPasswordById(passwordService.getPassword(passId), password, user);
        actionService.saveAction("Edited password", session.getAttribute("user").toString());

        model.addAttribute("password", savedPassword);
        model.addAttribute("user", user);
        model.addAttribute("mode", session.getAttribute("mode"));

        return "redirect:/login/" + id ;
    }
    //delete password
    @GetMapping("/login/{id}/{passId}/delete")
    public String deletePasswordById(@PathVariable Long id, @PathVariable Long passId, HttpSession httpSession){
        passwordService.deletePasswordById(passId);
        actionService.saveAction("Deleted password", httpSession.getAttribute("user").toString());
        return "redirect:/login/" + id ;
    }


    /* Sharing pasword*/
    @GetMapping("/login/{id}/{passId}/share-password")
    public String sharePassword(@PathVariable Long id, @PathVariable Long passId, Model model){
        model.addAttribute("user", new TempUser());
        model.addAttribute("userOwner", id);
        model.addAttribute("userOwnerPass", passId);
        return "sharingPassword";
    }
    /*Post of sharing password*/
    @PostMapping("/login/{id}/{passId}/share-password")
    public String sharePasswordForUser(@PathVariable Long id, @PathVariable Long passId, TempUser tempUser, Model model,HttpSession httpSession){
        if(userService.existByLogin(tempUser.getLogin())){
            User owner = userService.getUser(id);
            if(sharePasswordService.findByLoginAndOwner(tempUser.getLogin(), owner.getLogin()) &&  sharePasswordService.existsByPasswordId(passId)  ){
                model.addAttribute("error2", true);
                model.addAttribute("user", tempUser);
                model.addAttribute("userOwner", id);
                model.addAttribute("userOwnerPass", passId);
                return "sharingPassword";
            }
            if(!owner.getLogin().equals(tempUser.getLogin())) {
                if (passwordService.existById(passId)) {
                    Password password = passwordService.getPassword(passId);
                    if (owner.getPasswordList().stream().anyMatch(c -> c == password)) {
                        User userToShare = userService.findByLogin(tempUser.getLogin());
                        sharePasswordService.saveSharePassword(owner, userToShare, password);
                        actionService.saveAction("Shared password", httpSession.getAttribute("user").toString());

                        return "redirect:/login/" + id + "/" + passId;
                    }
                }
            }
            else{
                model.addAttribute("error", true);
                model.addAttribute("user", tempUser);
                model.addAttribute("userOwner", id);
                model.addAttribute("userOwnerPass", passId);
            }
        }
        else{
            model.addAttribute("error", true);
            model.addAttribute("user", tempUser);
            model.addAttribute("userOwner", id);
            model.addAttribute("userOwnerPass", passId);
        }
        return "sharingPassword";
    }

}
