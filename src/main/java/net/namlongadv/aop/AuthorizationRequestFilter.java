package net.namlongadv.aop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(2)
public class AuthorizationRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationRequestFilter.class);

    private final List<String> WHITE_LIST = Arrays.asList("swagger-ui", "swagger-resources", "v2/api-docs", "oauth/token");

    @Autowired
    private UserSession userSession;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = req.getServletPath();
            /**
             * Check the token
             */
            if (isWhiteList(path) || userSession.isValid()) {
                filterChain.doFilter((ServletRequest) req, (ServletResponse) resp);
            } else {
            	LOG.error("ACCESS DENIED: {}", path);
                resp.reset();
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (Exception e) {
            resp.reset();
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * This method is used to check white list for each request.
     * 
     * @param path
     * @return TRUE/FALSE
     */
    private boolean isWhiteList(String path) {
        return WHITE_LIST.stream().anyMatch(w -> path.toLowerCase().contains(w));
    }
}
