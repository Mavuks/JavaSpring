package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orderrows {

    @Size(min = 2, max = 50)
    private String itemName;

    @NotNull
    @Min(1L)
    private Integer quantity;

    @NotNull
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