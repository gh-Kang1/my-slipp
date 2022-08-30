package net.slipp.web;

import net.slipp.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser,question,contents);
        return answerRepository.save(answer);
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> delete(@PathVariable Long questionId , @PathVariable Long id , HttpSession session) {
        if(!HttpSessionUtils.isLoginUser(session)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.fail("로그인 사용자만 이용 가능합니다."));
        }

        Answer answer = answerRepository.findById(id).get();
        Users loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(loginUser)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Result.fail("로그인 사용자만 이용 가능합니다."));
        }

        answerRepository.deleteById(id);
        return ResponseEntity.ok(Result.ok("/questions/"+id));
    }
}
