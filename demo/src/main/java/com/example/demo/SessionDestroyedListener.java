package com.example.demo;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class SessionDestroyedListener implements HttpSessionListener {

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("### DESTROYED");
	}


}