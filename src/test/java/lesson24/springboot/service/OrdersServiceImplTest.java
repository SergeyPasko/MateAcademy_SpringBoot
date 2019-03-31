package lesson24.springboot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import lesson24.springboot.dao.OrderDaoImpl;
import lesson24.springboot.entity.Orders;

/**
 * @author spasko
 */
@RunWith(MockitoJUnitRunner.class)
public class OrdersServiceImplTest {
	@Spy
	@InjectMocks
	private OrdersService ordersService = new OrdersServiceImpl();
	@Mock
	private OrderDaoImpl orderDaoImpl;

	private Orders order1 = new Orders();
	private Orders order2 = new Orders();

	@Test
	public void testGetAllOrders() {
		Set<Orders> orders = new HashSet<>(Arrays.asList(new Orders[] { order1, order2 }));
		doReturn(orders).when(orderDaoImpl).getAllOrders();
		Set<Orders> result = ordersService.getAllOrders();
		assertTrue(orders.containsAll(result) && result.containsAll(orders));
	}

	@Test
	public void testFindOrderById() {
		doReturn(order1).when(orderDaoImpl).findOrderById(any());
		Orders result = ordersService.findOrderById(BigDecimal.TEN);
		assertEquals(order1, result);
	}

	@Test
	public void testInsertOrder() {
		doNothing().when(orderDaoImpl).insertOrder(any());
		ordersService.insertOrder(order1);
		verify(orderDaoImpl, times(1)).insertOrder(any());
	}

	@Test
	public void testUpdateOrder() {
		doNothing().when(orderDaoImpl).updateOrder(any());
		ordersService.updateOrder(order1);
		verify(orderDaoImpl, times(1)).updateOrder(any());
	}

	@Test
	public void testDeleteOrder() {
		doNothing().when(orderDaoImpl).deleteOrder(any());
		ordersService.deleteOrder(order1.getOrderNum());
		verify(orderDaoImpl, times(1)).deleteOrder(any());
	}
}
