package com.example.webproject.List.Entity;

import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,length = 10000)
    private String content;

    @CreationTimestamp
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserInfo userInfo;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @Builder(builderClassName = "UserRegister", builderMethodName = "userRegister")
    public Post(String title, String content, Date createTime,UserInfo userInfo){

        this.title = title;

        this.content = content;

        this.createTime = createTime;

        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                "}";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }
}
