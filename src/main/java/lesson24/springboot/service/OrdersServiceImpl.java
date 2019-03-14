package lesson24.springboot.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lesson24.springboot.entity.Orders;
import lesson24.springboot.hibernate.OrderDao;

/**
 * @author spasko
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
	private static final Logger LOG = LogManager.getLogger(OrdersServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Override
	public Set<Orders> getAllOrders() {
		LOG.debug("getAllOrders");
		HashSet<Orders> result = new HashSet<>(orderDao.getAllOrders());
		LOG.debug("getAllOrders return {} records", result.size());
		return result;
	}

	@Override
	public Orders findOrderById(BigDecimal id) {
		LOG.debug("findOrderById, id={}", id);
		Orders result = orderDao.findOrderById(id);
		LOG.debug("findOrderById, result={}", result);
		return result;
	}

	@Override
	public void insertOrder(Orders order) {
		LOG.debug("insertOrder, order={}", order);
		orderDao.insertOrder(order);
		LOG.debug("insertOrder completed");
	}

	@Override
	public void updateOrder(Orders order) {
		LOG.debug("updateOrder, order={}", order);
		orderDao.updateOrder(order);
		LOG.debug("updateOrder completed");
	}

	@Override
	public void deleteOrder(BigDecimal id) {
		LOG.debug("deleteOrder, id={}", id);
		orderDao.deleteOrder(id);
		LOG.debug("deleteOrder completed");
	}

}
