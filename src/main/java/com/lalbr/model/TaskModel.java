package com.lalbr.model;

import com.lalbr.util.Priority;
import com.lalbr.util.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "task_category_id")
    private CategoryModel category;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private Status status;

    @Column(name = "task_deadline")
    private LocalDateTime deadline;

    //in days
    @Column(name = "task_notificationleadtime")
    private int notificationLeadTime;

    //in days
    @Column(name = "task_repetitiondelay")
    private int repetitionDelay;


    @Column(name = "task_note")
    private String note;

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getNotificationLeadTime() {
        return notificationLeadTime;
    }

    public void setNotificationLeadTime(int notificationLeadTime) {
        this.notificationLeadTime = notificationLeadTime;
    }

    public int getRepetitionDelay() {
        return repetitionDelay;
    }

    public void setRepetitionDelay(int repetitionDelay) {
        this.repetitionDelay = repetitionDelay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
