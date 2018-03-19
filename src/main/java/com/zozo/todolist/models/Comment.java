package com.zozo.todolist.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private String id;

    @Column(name="content")
    private String content;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="author")
    private User author;

    @ManyToOne()
    @JoinColumn(name="task_id")
    private Task task;


    @Column(name="date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public int getTask() {
        return task.getId();
    }

    public void setTask(Task task) {
       this.task = task;
    }

    public void setTask(int taskID) {
        Task task = new Task();
        task.setId(taskID);
        task.addComment(this);
    }

    public void setAuthor(String authorName) {
        User author = new User();
        author.setUsername(authorName);
        this.author = author;
    }

    public String getAuthor() {
        return author.getUsername();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
