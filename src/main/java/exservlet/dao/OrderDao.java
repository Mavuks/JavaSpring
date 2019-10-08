package exservlet.dao;

import exservlet.model.Order;
import exservlet.model.Orderrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {


    private DataSource dataSource;


    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Order> findOrders() {

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select id, ordernumber from \"order\"");

            List<Order> orders = new ArrayList<>();


            while (rs.next()) {
                Order order = new Order(rs.getLong("id"), rs.getString("orderNumber"), null);

                orders.add(order);
            }
            return orders;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public Order insertOrder(Order order) {

        String sql = "insert into \"order\" (orderNumber) values (?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, order.getOrderNumber());


            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (!keys.next()) {
                throw new RuntimeException("Unexpected");
            }

            return new Order(keys.getLong("id"), order.getOrderNumber(), order.getOrderRows());


        } catch (SQLException e) {
            throw new RuntimeException(e);


        }

    }

    public long insertOrderRow(String itemname, int quantity, Long price, Long orderId) {


        String sql = "insert into \"orderrows\" (itemname, quantity, price, order_id) values (?, ? ,?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, itemname);
            ps.setInt(2, quantity);
            ps.setLong(3, price);
            ps.setLong(4, orderId);

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (!keys.next()) {
                throw new RuntimeException("Unexpected");
            }

            return keys.getLong("id");


        } catch (SQLException e) {
            throw new RuntimeException(e);


        }


    }

    public Order findOrdersById(Long id) {

        String sql = "select * from \"order\"  left join \"orderrows\"  on \"order\".id= \"orderrows\".order_id where \"order\".id = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            List<Orderrows> orderRows = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            Order order = null;

            while (rs.next()) {


                Orderrows row = new Orderrows(rs.getString("itemName"), rs.getInt("quantity"), rs.getLong("price"));

                orderRows.add(row);


                order = new Order(rs.getLong("id"), rs.getString("orderNumber"), orderRows);
            }


            return order;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void deleteRowById(Long id) {

        String sql = "delete from \"order\" where id =?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);


        }

    }


}
