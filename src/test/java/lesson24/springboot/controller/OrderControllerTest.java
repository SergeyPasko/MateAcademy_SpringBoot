package lesson24.springboot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lesson24.springboot.entity.Orders;

/**
 * @author spasko
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class OrderControllerTest {
	private static final int STATUS_OK = 200;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private DataSource dataSource;

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		Resource initSchema = new ClassPathResource("script\\schema.sql");
		Resource data = new ClassPathResource("script\\data.sql");
		DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetOrders() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/order")).andDo(print()).andReturn();

		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
		assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
		List<Orders> orders = getListOrdersFromResult(mvcResult);
		assertEquals(2, orders.size());
	}

	@Test
	public void testGetOrderByIdExist() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/order/{id}", "111111")).andDo(print()).andReturn();
		Orders order = mapper.readValue(mvcResult.getResponse().getContentAsString(), Orders.class);
		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
		assertNotNull(order);
	}

	@Test
	public void testGetOrderByIdNotExist() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/order/{id}", "-11")).andDo(print()).andReturn();
		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
		assertTrue(mvcResult.getResponse().getContentAsString().length() == 0);
	}

	@Test
	public void testAddOrder() throws Exception {
		String json = mapper.writeValueAsString(new Orders());
		MvcResult mvcResult = mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON).content(json))
				.andDo(print()).andReturn();

		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testDeleteOrder() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete("/order/{id}", "111111")).andDo(print()).andReturn();
		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testUpdateOrder() throws Exception {
		MvcResult mvcResult = mockMvc.perform(put("/order/{id}", "111111").param("qty", "777")).andDo(print())
				.andReturn();
		assertEquals(STATUS_OK, mvcResult.getResponse().getStatus());
	}

	private List<Orders> getListOrdersFromResult(MvcResult mvcResult)
			throws IOException, JsonParseException, JsonMappingException, UnsupportedEncodingException {
		return mapper.readValue(mvcResult.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(List.class, Orders.class));
	}

}
