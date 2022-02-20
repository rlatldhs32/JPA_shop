package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository{
    private final EntityManager em;

    public void save(Item item){ //item은 jpa 저장하기전까진 id가없음. 따라서 null
        if(item.getId() ==null){
            em.persist(item); //신규 등록
        }else{
            em.merge(item); //db 등록된거 가져온거기때문에 업데이트같은느낌임.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }

}
