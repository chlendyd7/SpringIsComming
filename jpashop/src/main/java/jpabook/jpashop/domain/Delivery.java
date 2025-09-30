package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery") //fetch = Lazy x 비소유 측이라 지연 로딩이 불가능
    private Order order;


    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINARY 시 순서가 밀릴 수 있음
    private DeliveryStatus status; //READY, COMP
}
