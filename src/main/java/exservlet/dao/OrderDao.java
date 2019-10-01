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


    public  List<Order> findOrders(){

        try(Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery("select id, ordernumber, orderrows from \"order\"");

            List<Order> orders = new ArrayList<>();

            while (rs.next()){
                Order order = new Order(rs.getLong("id"), rs.getString("orderNumber"), (List<Orderrows>) rs.getArray("orderRows"));


                orders.add(order);
            }
                return orders;


        }catch (SQLException  e){
            throw new RuntimeException(e);

        }
    }

    public Order insertOrder(Order order){

        String sql = "insert into \"order\" (orderNumber, orderRows) values (?,?)";

        try(Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, order.getOrderNumber());
            ps.setArray(2, (Array) order.getOrderRows());

            ps.executeUpdate();


        }catch (SQLException e){
            throw new RuntimeException(e);


        }
        return order;

    }

//    public void  insertOrderProov(){
//
//
//
//        try(Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()){
//
//           stmt.executeUpdate("insert into \"order\"(ordernumber, orderrows) values ('A125',null);");
//
//
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//
//
//        }
//
//
//    }


}
