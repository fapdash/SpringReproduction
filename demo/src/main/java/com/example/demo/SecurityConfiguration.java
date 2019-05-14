package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

@Configuration
public class SecurityConfiguration<S extends Session> extends WebSecurityConfigurerAdapter {

	@Autowired
	private FindByIndexNameSessionRepository<S> sessionRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			// other config goes here...
			.sessionManagement()
				.maximumSessions(-1)
				.sessionRegistry(sessionRegistry());
		// @formatter:on
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public SessionEventHttpSessionListenerAdapter sessionListeners(SessionDestroyedListener sessionEndedListener) {
		List<HttpSessionListener> listeners = new ArrayList<>();
		listeners.add(sessionEndedListener);
		return new SessionEventHttpSessionListenerAdapter(listeners);
	}

	@Bean
	public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
		return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
	}
}