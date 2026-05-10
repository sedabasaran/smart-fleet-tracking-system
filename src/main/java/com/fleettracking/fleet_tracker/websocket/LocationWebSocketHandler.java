package com.fleettracking.fleet_tracker.websocket;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.CopyOnWriteArrayList;
import com.fleettracking.fleet_tracker.entity.Location;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class LocationWebSocketHandler extends TextWebSocketHandler {

	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		sessions.add(session);
		log.info("WebSocket bağlandı: {}", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		sessions.remove(session);
		log.info("WebSocket kapandı: {}", session.getId());
	}

	public void broadcastLocation(Location location) {
		try {
			String message = objectMapper.writeValueAsString(
					new LocationMessage(location.getDevice().getDeviceCode(), location.getLatitude(),
							location.getLongitude(), location.getSpeed(), location.getRecordedAt().toString()));
			for (WebSocketSession session : sessions) {
				if (session.isOpen()) {
					session.sendMessage(new TextMessage(message));
				}
			}
		} catch (Exception e) {
			log.error("WebSocket broadcast hatası: {}", e.getMessage());
		}
	}

	record LocationMessage(String deviceCode, Double lat, Double lon, Double speed, String recordedAt) {
	}

}
