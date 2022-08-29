package net.slipp.web;

import net.slipp.domain.UserRepository;
import net.slipp.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Users show(@PathVariable Long id) {
        return userRepository.findById(id).get();
    }
}
