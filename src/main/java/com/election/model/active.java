package com.election.model;

import org.springframework.stereotype.Component;

@Component
public class active {
  private  boolean active;

public boolean isActive() {
	return active;
}

public void setActive(boolean active) {
	this.active = active;
}
}
