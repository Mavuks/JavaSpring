package exservlet.model;

public class Orderrows {


    private String itemName;
    private Integer quantity;
    private Long price;


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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }



}
