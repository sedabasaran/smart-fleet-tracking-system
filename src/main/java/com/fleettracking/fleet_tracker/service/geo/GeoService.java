package com.fleettracking.fleet_tracker.service.geo;

public interface GeoService {
	
	double calculateDistance(double lat1, double lon1, double lat2, double lon2);

	double calculateHeading(double lat1, double lon1, double lat2, double lon2);

	double[] getBoundingBox(double lat, double lon, double radiusKm);

}
