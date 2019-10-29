package app;

import dao.OrderDao;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {


    @Autowired
    private OrderDao dao;


    @GetMapping("orders")
    public List<Order> getOrder() {
        return dao.findOrders();
    }


    @GetMapping("orders/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return dao.findOrdersById(id);
    }

    @PostMapping("orders")
    public Order saveOrder(@RequestBody @Valid Order order) {

        return dao.insertOrder(order);
    }

    @DeleteMapping("orders/{id}")
    public void deleteOrder(@PathVariable Long id) {

        dao.deleteRowById(id);
    }

}
