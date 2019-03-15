package lesson24.springboot.hibernate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import lesson24.springboot.entity.Orders;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class OrderDaoImplTest {

	private static final Orders NOT_EXIST_ORDER_INSERT = new Orders(BigDecimal.valueOf(333333));
	private static final Orders ALREADY_EXIST_ORDER_UPDATE = new Orders(BigDecimal.valueOf(111111));
	private static final Orders ALREADY_EXIST_ORDER_DELETE = new Orders(BigDecimal.valueOf(222222));
	private static final BigDecimal NOT_EXIST_ORDER_ID = BigDecimal.valueOf(-1);

	@Autowired
	private OrderDaoImpl orderDaoImpl;
	@Autowired
	private DataSource dataSource;

	@Before
	public void initDb() {
		Resource initSchema = new ClassPathResource("script\\schema.sql");
		Resource data = new ClassPathResource("script\\data.sql");
		DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	}

	@Test
	public void testGetAllOrders() {
		Set<Orders> orders = orderDaoImpl.getAllOrders();
		assertTrue(orders.size() >= 2);
	}

	@Test
	public void testInsertOrder() {
		orderDaoImpl.insertOrder(NOT_EXIST_ORDER_INSERT);
	}

	@Test
	public void testUpdateOrder() {
		ALREADY_EXIST_ORDER_UPDATE.setAmount(BigDecimal.valueOf(-333));
		ALREADY_EXIST_ORDER_UPDATE.setQty(BigDecimal.valueOf(-111));
		orderDaoImpl.updateOrder(ALREADY_EXIST_ORDER_UPDATE);
	}

	@Test
	public void testDeleteOrder() {
		orderDaoImpl.deleteOrder(ALREADY_EXIST_ORDER_DELETE.getOrderNum());
	}

	@Test
	public void testFindOrderByIdNotPresent() {
		Orders order = orderDaoImpl.findOrderById(NOT_EXIST_ORDER_ID);
		assertNull(order);
	}

	@Test
	public void testFindOrderById() {
		Orders order = orderDaoImpl.findOrderById(ALREADY_EXIST_ORDER_UPDATE.getOrderNum());
		assertNotNull(order);
	}

}
