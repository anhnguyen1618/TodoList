package com.zozo.todolist.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @JoinColumn(name="assignee")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType
            .REFRESH})
    private User assignee;

    @JoinColumn(name="assignor")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private User assignor;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignee(String assigneeName) {
        User assignee = new User();
        assignee.setUsername(assigneeName);
        this.assignee = assignee;
    }

    public void setAssignor(String assignorName) {
        User assignor = new User();
        assignor.setUsername(assignorName);
        this.assignor = assignor;
    }

    public String getAssignee() {
        return assignee.getUsername();
    }

    public String getAssignor() {
        return assignor.getUsername();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comment.setTask(this);
    }
}
