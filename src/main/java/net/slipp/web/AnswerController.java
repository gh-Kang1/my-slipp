package net.slipp.web;

import net.slipp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AnswerController {

    private Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/{questionId}/answers")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        Question question = questionRepository.findById(questionId).get();
        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = new Answer(loginUser,question,contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d",questionId);
    }
}
