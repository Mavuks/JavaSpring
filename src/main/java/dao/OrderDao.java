package dao;

import model.Installment;
import model.Order;
import model.Orderrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
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

        if (order.getOrderRows() != null) {
            for (int i = 0; i < order.getOrderRows().size(); i++) {

                String itemName = order.getOrderRows().get(i).getItemName();
                int quantity = order.getOrderRows().get(i).getQuantity();
                Integer price = order.getOrderRows().get(i).getPrice();

                insertOrderRow(itemName, quantity, price, id.longValue());

            }
        }

        return new Order(id.longValue(), order.getOrderNumber(), order.getOrderRows());

    }

    public void insertOrderRow(String itemname, int quantity, Integer price, long orderId) {


        String sql = "insert into \"orderrows\" (itemname, quantity, price, order_id) values (?, ? ,?, ?)";

        template.update(sql, itemname, quantity, price, orderId);


    }

    public Order findOrdersById(long id) {

        String sql = "select * from \"orders\"  left join \"orderrows\"  on \"orders\".id= \"orderrows\".order_id where \"orders\".id = ?";

        List<Map<String, Object>> list = template.queryForList(sql, id);
        List<Orderrows> orderrows = new ArrayList<>();

        Order order = null;


        for (Map<String, Object> map : list) {


            Orderrows row = new Orderrows((String) map.get("itemName"), (Integer) map.get("quantity"), (Integer) map.get("price"));

            orderrows.add(row);

            Integer idV = (Integer) map.get("id");
            Long idL = Long.valueOf(idV);


            order = new Order(idL, (String) map.get("orderNumber"), orderrows);


        }


        return order;
    }

    public void deleteRowById(Long id) {


        String sql = "delete from \"orders\" where id =?";

        template.update(sql, id);

    }

    public List<Installment> installments(Long id, LocalDate start, LocalDate end) {

        Order order = findOrdersById(id);
        int sum = 0;

        for (int i = 0; i < order.getOrderRows().size(); i++) {

            int quantity = order.getOrderRows().get(i).getQuantity();
            Integer price = order.getOrderRows().get(i).getPrice();

            sum += quantity * price;
        }

        YearMonth m1 = YearMonth.from(start);
        YearMonth m2 = YearMonth.from(end);

        long months = m1.until(m2, ChronoUnit.MONTHS) + 1;

        if((sum / months) < 3 & (sum / (months - 1 )) >= 3){
            months = months - 1;
        }



        List<Installment> list = new ArrayList<>();
        Installment installment = null;

        LocalDate date = start;
        for (int i = 1; i <= months; i++) {

            if ((sum % months) == 1 & i == months) {

                installment = new Installment((int) (sum / months + sum % months), date);
                list.add(installment);
                date = date.withDayOfMonth(1).plusMonths(1);

            }else if((sum % months) == 2 & (i == months || i == months - 1) ){
                installment = new Installment((int) (sum / months + sum % months / 2), date);
                list.add(installment);
                date = date.withDayOfMonth(1).plusMonths(1);
            }
            else {

                installment = new Installment((int) (sum / months), date);
                list.add(installment);

                date = date.withDayOfMonth(1).plusMonths(1);

            }

        }

        return list;
    }


}
