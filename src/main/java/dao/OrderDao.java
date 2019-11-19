package dao;

import model.Installment;
import model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao {


    @PersistenceContext
    private EntityManager em;


    public List<Order> findOrders() {

        return em.createQuery("select p from Order p", Order.class).getResultList();

    }
    @Transactional
    public Order insertOrder(Order order) {

        if(order.getId() == null){
            em.persist(order);
        }else{
            em.merge(order);
        }
        return order;
    }

    public Order findOrdersById(long id) {

        TypedQuery<Order> query = em.createQuery("select p from Order p where p.id = :id", Order.class);

        query.setParameter("id", id);


            return query.getSingleResult();
    }

    public void deleteRowById(Long id) {

        Query query = em.createQuery("delete from Order p where p.id = :id");
            query.setParameter("id", id);
            query.executeUpdate();


    }


    public List<Installment> installments(Long id, LocalDate start, LocalDate end) {

        Order order = null;
        int sum = 0;

        for (int i = 0; i < order.getOrderRows().size(); i++) {

            int quantity = order.getOrderRows().get(i).getQuantity();
            Integer price = order.getOrderRows().get(i).getPrice();

            sum += quantity * price;
        }

        YearMonth m1 = YearMonth.from(start);
        YearMonth m2 = YearMonth.from(end);

        long months = m1.until(m2, ChronoUnit.MONTHS) + 1;

        if ((sum / months) < 3 & (sum / (months - 1)) >= 3) {
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

            } else if ((sum % months) == 2 & (i == months || i == months - 1)) {
                installment = new Installment((int) (sum / months + sum % months / 2), date);
                list.add(installment);
                date = date.withDayOfMonth(1).plusMonths(1);
            } else {

                installment = new Installment((int) (sum / months), date);
                list.add(installment);

                date = date.withDayOfMonth(1).plusMonths(1);

            }

        }

        return list;
    }


}
