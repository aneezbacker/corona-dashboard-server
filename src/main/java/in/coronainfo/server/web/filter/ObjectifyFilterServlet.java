package in.coronainfo.server.web.filter;

import com.googlecode.objectify.ObjectifyFilter;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;

@Component
@WebFilter(urlPatterns = {"/*"})
public class ObjectifyFilterServlet extends ObjectifyFilter {
}