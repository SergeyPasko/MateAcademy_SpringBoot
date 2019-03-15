package lesson24.springboot.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

/**
 * @author spasko
 */
@Component
public class ThreadContextFilter implements Filter {
	private static final String UUID_FIELD = "uuid";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			ThreadContext.put(UUID_FIELD, UUID.randomUUID().toString());
			chain.doFilter(request, response);
		} finally {
			ThreadContext.remove(UUID_FIELD);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
