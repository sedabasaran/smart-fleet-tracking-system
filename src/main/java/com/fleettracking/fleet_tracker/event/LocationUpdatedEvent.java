package com.fleettracking.fleet_tracker.event;

import org.springframework.context.ApplicationEvent;

import com.fleettracking.fleet_tracker.entity.Location;

public class LocationUpdatedEvent extends ApplicationEvent {
	
	private final Location location;

	public LocationUpdatedEvent(Object source, Location location) {
		super(source);
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}
}
