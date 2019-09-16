package exservlet.model;

public class Order {

    private static int idCount = 0;
    private long id;
    private String orderNumber;

    public Order(){
        this.id = idCount++;

    }


    @Override
    public String toString() {
        return "order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}



