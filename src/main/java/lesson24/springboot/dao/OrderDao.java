package lesson24.springboot.dao;

import java.math.BigDecimal;
import java.util.Set;

import lesson24.springboot.entity.Orders;

/**
 * @author spasko
 */
public interface OrderDao {

	Set<Orders> getAllOrders();

	Orders findOrderById(BigDecimal id);

	void insertOrder(Orders order);

	void updateOrder(Orders order);

	void deleteOrder(BigDecimal id);
}
