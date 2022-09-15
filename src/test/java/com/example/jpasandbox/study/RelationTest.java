package com.example.jpasandbox.study;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpasandbox.domain.EagerMember;
import com.example.jpasandbox.domain.Order;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RelationTest {

    @PersistenceContext
    EntityManager em;

    @DisplayName("Order와 Member 사이의 연관관계 쿼리를 확인한다. - flush를 하지 않는 경우(1차 캐시 확인)")
    @Test
    void checkQueryAtCacheWithoutFlush() {
        EagerMember bom = new EagerMember("봄");
        Order order = new Order("주문완료", bom);

        em.persist(bom);
        em.persist(order);

        Order foundOrder = em.find(Order.class, order.getId());

        /*
        flush를 하지 않아도 이미 em.persist()를 통해서 1차 캐시에 저장되어 있기 때문에, 1차 캐시에서 값을 조회해온다.
        따라서 select 쿼리가 없다.
         */
        assertThat(foundOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());
    }

    @DisplayName("Order와 Member 사이의 연관관계 쿼리를 확인한다. - flush 한 경우(1차 캐시 확인)")
    @Test
    void checkQueryAtCacheWithFlush() {
        EagerMember bom = new EagerMember("봄");
        Order order = new Order("주문완료", bom);

        em.persist(bom);
        em.persist(order);
        em.flush(); // flush를 통해 DB에 실제 데이터를 날린다.

        /*
        flush를 통해 DB에 실제 데이터를 저장했더라도, 아직 clear이 없기 때문에 영속성 컨텍스트에서 1차 캐시로 객체를 관리한다.
        따라서, 1차 캐시에서 값을 조회해오기 때문에 select 쿼리가 없다.
         */
        Order foundOrder = em.find(Order.class, order.getId());
    }

    @DisplayName("Order와 Member 사이의 연관관계 쿼리를 확인한다. - DB에서 확인하는 경우")
    @Test
    void checkQueryAtDBWithFlushAndClear() {
        EagerMember bom = new EagerMember("봄");
        Order order = new Order("주문완료", bom);

        em.persist(bom);
        em.persist(order);
        em.flush(); // flush를 통해 DB에 실제 데이터를 날린다.
        em.clear(); // 영속성 컨텍스트를 비워준다.

        /*
        flush를 통해 DB에 실제 데이터를 저장한 후
        clear를 통해 영속성 컨텍스트를 비워줬기 때문에, 현재 1차 캐시에 값 존재하지 않음
        따라서, Select 쿼리를 통해 조회
         */

        Order foundOrder = em.find(Order.class, order.getId());
    }

    @DisplayName("Order와 Member 사이의 연관관계 쿼리를 확인한다. - clear만 한 경우")
    @Test
    void checkQueryWithClear() {
        EagerMember bom = new EagerMember("봄");
        Order order = new Order("주문완료", bom);

        em.persist(bom);
        em.persist(order);
        em.clear();

        Order foundOrder = em.find(Order.class, order.getId());
        assertThat(foundOrder.getOrderStatus()).isEqualTo("주문완료");
    }
}
