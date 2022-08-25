package net.slipp.web;

import net.slipp.domain.UserRepository;
import net.slipp.domain.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CommunityController {

    private Logger logger = LoggerFactory.getLogger(CommunityController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/community")
    public String form(Model model) {
        logger.info("@chatController , chat get() ");
        List<Users> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "/community/form";
    }
}
