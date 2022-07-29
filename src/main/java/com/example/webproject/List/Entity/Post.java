package com.example.webproject.List.Entity;

import com.example.webproject.UserHandle.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Post")
@Table
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserInfo userInfo;

    @Builder
    public Post(String title,String content,Date createTime,UserInfo userInfo){

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
