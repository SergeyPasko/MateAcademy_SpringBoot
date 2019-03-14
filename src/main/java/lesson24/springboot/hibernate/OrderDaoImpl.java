package lesson24.springboot.hibernate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lesson24.springboot.entity.Orders;

/**
 * @author spasko
 */
@Repository
public class OrderDaoImpl implements OrderDao {
	private Session sessionObj;

	private SessionFactory sessionFactoryObj;

	private static final Logger LOG = LogManager.getLogger(OrderDaoImpl.class);

	@Autowired
	public OrderDaoImpl(EntityManagerFactory factory) {
		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.sessionFactoryObj = factory.unwrap(SessionFactory.class);
	}

	@Override
	public Set<Orders> getAllOrders() {
		Set<Orders> orders = new HashSet<>();
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();
			orders = new HashSet<>(sessionObj.createQuery("FROM Orders", Orders.class).list());
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return orders;
	}

	@Override
	public Orders findOrderById(BigDecimal id) {
		Orders order = null;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();

			order = sessionObj.get(Orders.class, id);
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return order;
	}

	@Override
	public boolean insertOrder(Orders order) {
		boolean result = false;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			sessionObj.save(order);

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			result = true;
			LOG.info("\nSuccessfully Created Records In The Database!\n");
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				LOG.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return result;
	}

	@Override
	public boolean updateOrder(Orders order) {
		boolean result = false;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();
			sessionObj.update(order);
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			result = true;
			LOG.info("\nOrder With Id={} Is Successfully Updated In The Database!\n", order.getOrderNum());
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				LOG.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return result;
	}

	@Override
	public boolean deleteOrder(BigDecimal id) {
		boolean result = false;
		try {
			// Getting Session Object From SessionFactory
			sessionObj = sessionFactoryObj.openSession();
			// Getting Transaction Object From Session Object
			sessionObj.beginTransaction();

			sessionObj.delete(sessionObj.get(Orders.class, id));

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
			result = true;
			LOG.info("\nOrder With Id={} Is Successfully deleted from The Database!\n", id);
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				LOG.info("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return result;
	}

}
