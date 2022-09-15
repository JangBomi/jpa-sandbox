package com.example.jpasandbox.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eager_member_id")
    private EagerMember eagerMember;

    protected Order() {
    }

    public Order(final Long id, final String orderStatus, final EagerMember eagerMember) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.eagerMember = eagerMember;
    }

    public Order(final String orderStatus, final EagerMember eagerMember) {
        this.orderStatus = orderStatus;
        this.eagerMember = eagerMember;
    }
}
