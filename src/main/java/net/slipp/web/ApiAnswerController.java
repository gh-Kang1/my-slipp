package net.slipp.web;

import net.slipp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        logger.info("@@@question Id {} " ,questionId);
        logger.info("@@@quecontentsstion Id {} " ,contents);
        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser,question,contents);
        return answerRepository.save(answer);
    }

    @Autowired
    private QuestionRepository questionRepository;
}
