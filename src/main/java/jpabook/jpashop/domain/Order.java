package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //생성자 막기 위함.
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //다대일
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems =  new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //--연관관계 매서드--// 깜빡할수도있으니까 그냥 다같이 묶어버림. 양방향 연관관계
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){ //order <-> orderItem 사이
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //--생성 매서드--// 생성할떄부터 crateOrder을 호출하게.
    public static Order createOrder(Member member,Delivery delivery,OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //-비지니스 로직-// 재고 올려주는거 로직이 엔티티 안에있음!!
    /**
     * 주문 취소
     */

    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for( OrderItem orderItem : orderItems){
            orderItem.cancel(); // 각각 다 cancel 날려줌
        }
    }

    //-조회 로직--/
    public int getTotalPrice(){
        int totalPrice=0;
        for (OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
