package com.bssm.bumaview.domain.subscription.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mail_subscriptions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","category"}))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String category;

    @Column(nullable=false)
    private boolean active;

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }
}
