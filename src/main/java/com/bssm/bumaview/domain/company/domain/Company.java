package com.bssm.bumaview.domain.company.domain;

import com.bssm.bumaview.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companys")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String logoUrl;

    public Company(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public static Company of(String name, String logoUrl) {
        return new Company(name, logoUrl);
    }
}
