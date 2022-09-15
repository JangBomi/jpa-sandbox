package com.example.jpasandbox.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "eager_member")
public class EagerMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected EagerMember() {
    }

    public EagerMember(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public EagerMember(final String name) {
        this.name = name;
    }
}
