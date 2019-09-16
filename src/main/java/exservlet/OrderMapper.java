package exservlet;

import exservlet.model.Order;

public class OrderMapper {



    public Order parse(String input){

        input = input.replace("}","");
        input = input.replace("{","");
        input = input.trim();


        if(input.contains("id")){
            String[] keyAndValue1 = input.split(",");
            String[] value = keyAndValue1[1].split(":");
            String orderNumber = value[1].replace("\"", "").trim();
            Order order = new Order();
            order.setOrderNumber(orderNumber);
            return order;

        }
        String[] keyAndValue = input.split(":");
        String orderNumber = keyAndValue[1].replace("\"", "").trim();

        Order order = new Order();
        order.setOrderNumber(orderNumber);

        return order;
    }

    public String stringify(Order order){
        String result = "{ ";
        result += "\"id\": " + order.getId();
        result += ", \"orderNumber\": \"" + order.getOrderNumber() + "\"";
        result += "}";

        return result;
        }

}
