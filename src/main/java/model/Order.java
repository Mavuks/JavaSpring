package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

public class Order {

    private Integer id;

    @NonNull
    private String orderNumber;

    private List<Orderrows> orderRows;




    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {

            return null;
        }
    }
//
//    public long getId() {
//        return id;
//    }
//
//    public String getOrderNumber() {
//        return orderNumber;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public void setOrderNumber(String orderNumber) {
//        this.orderNumber = orderNumber;
//    }
//
//
//    public List<Orderrows> getOrderRows() {
//        return orderRows;
//    }
//
//    public void setOrderRows(List<Orderrows> orderRows) {
//        this.orderRows = orderRows;
//    }


}



