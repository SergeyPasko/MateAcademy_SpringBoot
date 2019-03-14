package lesson24.springboot.controller;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lesson24.springboot.entity.Orders;
import lesson24.springboot.service.OrdersService;

/**
 * @author spasko
 */

@RestController
@RequestMapping("/order")
public class OrderController {
	private static final Logger LOG = LogManager.getLogger(OrderController.class);

	@Autowired
	private OrdersService ordersService;

	@GetMapping
	public @ResponseBody Set<Orders> getOrdersQtyBetween() {
		LOG.debug("getOrdersQtyBetween use getAllOrders");
		return ordersService.getAllOrders();
	}

	@PostMapping
	public void addOrder(@Valid @RequestBody Orders orderRequest) {
		LOG.info("addOrder start, orderRequest={}", orderRequest);
		ordersService.insertOrder(orderRequest);
		LOG.info("addOrder end");
	}

	@GetMapping("/{id}")
	public @ResponseBody Orders getOrderById(@PathVariable("id") int id) {
		LOG.info("getOrderById start, id={}", id);
		Orders result = ordersService.findOrderById(BigDecimal.valueOf(id));
		LOG.info("getOrderById end");
		return result;
	}

	@DeleteMapping("/{id}")
	public void deleteOrderById(@PathVariable("id") int id) {
		LOG.info("deleteOrderById start, id={}", id);
		ordersService.deleteOrder(BigDecimal.valueOf(id));
		LOG.info("deleteOrderById end");
	}

	@PutMapping("/{id}")
	public void updateOrderById(@PathVariable("id") int id, @RequestParam("qty") Integer qty) {
		LOG.info("updateOrderById start, id={}, qty={}", id, qty);
		Orders order = ordersService.findOrderById(BigDecimal.valueOf(id));
		if (Objects.isNull(order)) {
			LOG.warn("updateOrderById cannot update not existing order");
		} else {
			order.setQty(BigDecimal.valueOf(qty));
			ordersService.updateOrder(order);
		}
		LOG.info("updateOrderById end");
	}

}
