package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long Id;

    private String name;
    private int price;
    private int stackQuantity;

    @ManyToMany(mappedBy = "items", fetch = LAZY)
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    public void addStcok(int quantity) {
        this.stackQuantity += quantity;
    }

    /**
     *
     * stack 감소
     */
    public void removeStock(int quantity) {
        int restStcok = this.stackQuantity -= quantity;
        if (restStcok < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stackQuantity = restStcok;
    }

}
