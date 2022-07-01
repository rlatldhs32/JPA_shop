package jpabook.jpashop.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQuery>
    }

    public List<OrderQueryDto> findOrderQueryDtos(){
        em.createQuery(
                "select new jpabook.jpashop.repository.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address) from Order o"+
                        " join o.member m"+
                        " join o.delivery d",OrderQueryDto.class)
                .getResultList();
        )
    }

}
