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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.RequestContextFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(2)
@Slf4j
public class AuthorizationRequestFilter extends RequestContextFilter {
    private final List<String> WHITE_LIST = Arrays.asList(
    		"swagger-ui",
    		"swagger-resources",
    		"v2/api-docs",
    		"oauth/token",
    		"resources/images",
    		"hotfix"
    );

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
            	log.error("ACCESS DENIED: {}", path);
                resp.reset();
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (Exception e) {
        	log.error("ERR ", e);
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
    
    /**
     * Need this to use request scope in web application thread
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
