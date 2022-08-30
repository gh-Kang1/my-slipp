package net.slipp.web;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.Users;
import org.h2.engine.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        return "/qna/form";
    }

    @PostMapping
    public String create(String title, String contents,HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }

        Users sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser,sessionUser.getUserId(), title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id , Model model , HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session,question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage",result.getErrorMessage());
            return "/user/login";
        }

        model.addAttribute("question",question);
        return "/qna/updateForm";
    }

    private Result valid(HttpSession session,Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신의 쓴 글만 수정, 삭제가 가능합니다.");
        }

        return Result.ok();
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        if(!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("자신의 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return false;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title , String contents, HttpSession session,Model model) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session,question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage",result.getErrorMessage());
            return "/user/login";
        }

        question.update(title,contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d",id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,HttpSession session,Model model) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session,question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage",result.getErrorMessage());
            return "/user/login";
        }

        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
