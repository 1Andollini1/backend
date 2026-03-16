package com.invitation.backend.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "rsvps")
public class Rsvp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(length = 200)
    private String email;

    @Column(length = 50)
    private String phone;

    @Column(nullable = false)
    private Boolean willCome;

    @Column(length = 500)
    private String reason;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(length = 500)
    private String questions;

    @Column(name = "is_not_single")
    private Boolean isNotSingle;

    @Column(length = 500)
    private String secondGuestName;

    @Column(length = 500)
    private String allergic;

    public Rsvp() { } // JPA требует конструктор без параметров

    @PrePersist
    void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    // getters/setters

    public Long getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Boolean getWillCome() { return willCome; }
    public void setWillCome(Boolean willCome) { this.willCome = willCome; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public OffsetDateTime getCreatedAt() { return createdAt; }

    public String getQuestions() {
        return questions;
    }

    public Boolean getIsNotSingle() {
        return isNotSingle;
    }

    public void setIsNotSingle(Boolean isNotSingle) {
        this.isNotSingle = isNotSingle;
    }

    public String getSecondGuestName() {return secondGuestName;}
    public void setSecondGuestName(String secondGuestName) {this.secondGuestName = secondGuestName;}

    public String getAllergic() {return allergic;}
    public void setAllergic(String allergic) {this.allergic = allergic;}

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}