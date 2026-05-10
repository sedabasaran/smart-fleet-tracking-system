package com.fleettracking.fleet_tracker.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.event.LocationUpdatedEvent;
import com.fleettracking.fleet_tracker.websocket.LocationWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationEventListener {

	private final LocationWebSocketHandler webSocketHandler;

	@EventListener
	public void handleLocationUpdated(LocationUpdatedEvent event) {
		log.info("Location event: device={}, lat={}, lon={}", event.getLocation().getDevice().getDeviceCode(),
				event.getLocation().getLatitude(), event.getLocation().getLongitude());
		webSocketHandler.broadcastLocation(event.getLocation());
	}

}
