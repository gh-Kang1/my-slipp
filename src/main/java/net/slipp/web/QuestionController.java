package net.slipp.web;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Users;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping
    public String create(String title, String contents,HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "/users//loginForm";
        }

        Users sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser,sessionUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question",questionRepository.findById(id).orElseThrow(RuntimeException::new));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id , Model model) {
        model.addAttribute("question",questionRepository.findById(id).orElseThrow(RuntimeException::new));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title , String contents) {
        Question question = questionRepository.findById(id).orElseThrow(RuntimeException::new);
        question.update(title,contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d",id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
