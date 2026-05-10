package com.fleettracking.fleet_tracker.service.geo;

import org.springframework.stereotype.Service;

@Service
public class GeoServiceImpl implements GeoService {

	private static final double EARTH_RADIUS_KM = 6371.0;

    @Override
    public double calculateDistance(double lat1, double lon1,
                                    double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1))
                 * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    @Override
    public double calculateHeading(double lat1, double lon1,
                                   double lat2, double lon2) {
        double dLon = Math.toRadians(lon2 - lon1);
        double y = Math.sin(dLon) * Math.cos(Math.toRadians(lat2));
        double x = Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                 - Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.cos(dLon);
        double bearing = Math.toDegrees(Math.atan2(y, x));
        return (bearing + 360) % 360;
    }

    @Override
    public double[] getBoundingBox(double lat, double lon, double radiusKm) {
        double latDelta = Math.toDegrees(radiusKm / EARTH_RADIUS_KM);
        double lonDelta = Math.toDegrees(radiusKm / EARTH_RADIUS_KM
                        / Math.cos(Math.toRadians(lat)));
        return new double[]{
            lat - latDelta, lat + latDelta,
            lon - lonDelta, lon + lonDelta
        };
    }
}
