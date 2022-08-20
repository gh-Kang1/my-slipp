package net.slipp.web;

import net.slipp.domain.UserRepository;
import net.slipp.domain.Users;
import org.h2.engine.Mode;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    private List<Users> users = new ArrayList<Users>();

    @Autowired
    UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Users user = userRepository.findByUserId(userId);
        if(user == null) {
            log.info("LoginFail");
            return "redirect:/users/loginForm";
        }

        if(!password.equals(user.getPassword())) {
            log.info("LoginFail");
            return "redirect:/users/loginForm";
        }

        session.setAttribute("sessionedUser",user);
        log.info("LoginSuccess Session : {} ", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @PostMapping
    public String create(Users users, Model model) {
        userRepository.save(users);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users",userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model , HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if(tempUser == null) {
            return "redirect:/users/loginForm";
        }

        Users sessionedUser = (Users) tempUser;
        if(!sessionedUser.getId().equals(id)){
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, Users updatedUser, HttpSession session) {
        Object tempUser = session.getAttribute("sessionedUser");
        if(tempUser == null) {
            return "redirect:/users/loginForm";
        }

        Users sessionedUser = (Users) tempUser;
        if(!sessionedUser.getId().equals(id)){
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        Users user = userRepository.findOne(id);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
