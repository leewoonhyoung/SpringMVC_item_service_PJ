package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // price 가 null 일 가능성이 존재한다.
    private Integer quantity;

    private Boolean open; // 판매여부
    private List<String> regions; // 등록지역
    private ItemType itemType; // 상품종 류
    private String deliveryCode;


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {
    }
}
