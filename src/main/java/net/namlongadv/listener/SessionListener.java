package net.namlongadv.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.debug("Session created");
		event.getSession().setMaxInactiveInterval(900);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		log.debug("Session destroyed");
	}

}
