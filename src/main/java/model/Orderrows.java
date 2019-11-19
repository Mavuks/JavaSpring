package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Embeddable
@Table(name = "order_rows")
public class Orderrows {


    @Size(min = 2, max = 255)
    @Column(name = "item_name")
    private String itemName;


    @NotNull
    @Column(name = "quantity")
    @Min(1L)
    private Integer quantity;


    @NotNull
    @Column(name = "price")
    @Min(1L)
    private Integer price;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "{" +
                "\"itemName\":\"" + itemName + '\"' +
                ", \"quantity\":" + quantity +
                ", \"price\":" + price +
                '}';
    }


}