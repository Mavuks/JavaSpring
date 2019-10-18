package dao;

import model.Order;
import model.Orderrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao {


    private JdbcTemplate template;

    @Autowired
    public OrderDao(JdbcTemplate template) {

        this.template = template;
    }


    public List<Order> findOrders() {

        String sql = "select id, ordernumber from \"orders\"";

        var mapper2 = new BeanPropertyRowMapper<>(Order.class);

        return template.query(sql, mapper2);

    }

    public Order insertOrder(Order order) {

        var data = new BeanPropertySqlParameterSource(order);

        Number id = new SimpleJdbcInsert(template)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(data);

        return new Order(id.intValue(), order.getOrderNumber(), order.getOrderRows());


    }

    public int insertOrderRow(String itemname, int quantity, Integer price, int orderId) {


        String sql = "insert into \"orderrows\" (itemname, quantity, price, order_id) values (?, ? ,?, ?)";

        return template.update(sql, itemname, quantity, price, orderId);


    }

    public Order findOrdersById(int id) {

        String sql = "select * from \"orders\"  left join \"orderrows\"  on \"orders\".id= \"orderrows\".order_id where \"orders\".id = ?";

        List<Map<String, Object>> list = template.queryForList(sql, id);
        List<Orderrows> orderrows = new ArrayList<>();

        Order order = null;

    
        for (Map<String, Object> map : list) {


            Orderrows row = new Orderrows((String) map.get("itemName"), (Integer) map.get("quantity"), (Integer) map.get("price"));

            orderrows.add(row);

            order = new Order((Integer) map.get("id"), (String) map.get("orderNumber"), orderrows);

        }

        return order;
    }

    public void deleteRowById(Integer id) {

        String sql = "delete from \"orders\" where id =?";

        template.update(sql, id);

    }


}
