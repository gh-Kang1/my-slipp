package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.h2.engine.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "USERS")
public class Users {
    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

    @OneToMany(mappedBy = "writer")
    private List<Question> question;

    @Column(unique = true)
    @JsonProperty
    private String userId;
    @JsonIgnore
    private String password;
    @JsonProperty
    private String name;
    @JsonProperty
    private String email;

    public Users() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean matchId(Long id) {
        if (id == null) {
            return false;
        }

        return this.id.equals(id);
    }

    public boolean matchPassword(String newPassword) {
        if (newPassword == null) {
            return false;
        }

        return newPassword.equals(password);
    }

    public void update(Users newUser) {
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
