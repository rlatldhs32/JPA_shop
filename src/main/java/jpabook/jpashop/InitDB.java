package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;


    @PostConstruct // bean 다 등록하고 나서 이런식으로. 코드 다 붙이지말고 이렇게함.
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1(){
            Member member = getMember("userA", "서울", "1", "111");
            em.persist(member);

            Book book1 = getBook("JPA1 Book", 10000, 100);

            Book book2 = getBook("JPA2 Book", 20000, 200);

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        public void dbInit2(){
            Member member = getMember("userB", "부산", "2", "222");
            em.persist(member);

            Book book1 = getBook("SPRING1 Book", 20000, 200);

            Book book2 = getBook("SPRING2 Book", 40000, 300);

            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            em.persist(order);

        }

        private Book getBook(String JPA1_Book, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(JPA1_Book);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member getMember(String userB, String ss, String street, String zipcode) {
            Member member = new Member();
            member.setName(userB);
            member.setAddress(new Address(ss, street, zipcode));
            return member;
        }
    }
}
