package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class Item {

    @NotNull
    private Long id;

    @NotBlank(message = "공백은 입력할 수 없습니다.")
    private String itemName;

    @NotNull
    @Range(min =1000, max = 1000000)
    private Integer price; // price 가 null 일 가능성이 존재한다.

    @NotNull
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
