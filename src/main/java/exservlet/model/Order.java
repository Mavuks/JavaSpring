package exservlet.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    private static int idCount = 0;
    private long id;
    private String orderNumber;
    private List<Orderrows> orderRows;


    private static Map<Long,String> saveMap = new HashMap<>();



    public Order(){


        this.id = idCount++;



    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderrows=" + orderRows +
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


    public List<Orderrows> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<Orderrows> orderRows) {
        this.orderRows = orderRows;
    }

    public static Map<Long, String> getAllSaveMap() {
        return saveMap;
    }


    public static String getSaveMap(Long id) {
        return saveMap.get(id);
    }

    public static void setSaveMap(Long id, String orderNumber) {

        saveMap.put(id,orderNumber);


    }




}



