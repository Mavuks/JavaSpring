package exservlet.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor

public class Order {

    private long id;
    private String orderNumber;
    private List<Orderrows> orderRows;


    private static Map<Long, String> saveMap = new HashMap<>();


    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {

            return null;
        }
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

        saveMap.put(id, orderNumber);


    }


}



