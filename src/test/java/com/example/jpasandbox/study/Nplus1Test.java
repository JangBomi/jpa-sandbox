package com.example.jpasandbox.study;

import com.example.jpasandbox.domain.EagerMember;
import com.example.jpasandbox.domain.Order;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class Nplus1Test {

    @PersistenceContext
    EntityManager em;

    @DisplayName("Order와 Member 사이의 연관관계 쿼리를 확인한다. - 1차 캐시에서 확인하는 경우")
    @Test
    void checkQuery() {
        EagerMember bom = new EagerMember("봄");
        Order order = new Order("주문완료", bom);

        em.persist(bom);
        em.persist(order);
        em.flush();

        Order foundOrder = em.find(Order.class, order.getId());
    }
}
