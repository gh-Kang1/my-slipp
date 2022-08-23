package net.slipp.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_question_writer")
    private Users writerId;

    private String writer;
    private String title;
    private String contents;

    private LocalDateTime createDate;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateDate() {
        if (this.createDate == null) {
            return "";
        }
        return this.createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public Question() {}

    public Question(Users user,String writer, String title, String contents) {
        this.writerId = user;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
