package lesson24.springboot.hibernate;

import java.math.BigDecimal;
import java.util.Set;

import lesson24.springboot.entity.Orders;

/**
 * @author spasko
 */
public interface OrderDao {

	Set<Orders> getAllOrders();

	Orders findOrderById(BigDecimal id);

	boolean insertOrder(Orders order);

	boolean updateOrder(Orders order);

	boolean deleteOrder(BigDecimal id);
}
