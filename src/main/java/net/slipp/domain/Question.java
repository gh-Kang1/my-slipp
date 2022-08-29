package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private Users writer;

    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC ")
    private List<Answer> answers;

    @Column(name = "name")
    private String writerId;
    @JsonProperty
    private String title;
    @Lob
    @JsonProperty
    private String contents;

    private LocalDateTime createDate;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Users getWriter() {
        return writer;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getContents() {
        return contents;
    }

    public String getCreateDate() {
        if (this.createDate == null) {
            return "";
        }
        return this.createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public Question() {}

    public Question(Users user,String writer, String title, String contents) {
        this.writer = user;
        this.writerId = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isSameWriter(Users loginUser) {
        return this.writer.equals(loginUser);
    }

}
