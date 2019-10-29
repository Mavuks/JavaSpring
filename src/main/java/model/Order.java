package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String orderNumber;

    @Valid
    private List<Orderrows> orderRows;




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


}



