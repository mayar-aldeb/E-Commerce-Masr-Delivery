package org.route.service;

import org.route.dto.Coordinates;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class RoutingService {

    public double calculateDistance(Coordinates origin, Coordinates destination)throws Exception  {
        String url = String.format("%s%f,%f;%f,%f?overview=false",
                "https://router.project-osrm.org/route/v1/driving/", origin.lon(), origin.lat(), destination.lon(), destination.lat());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        double distanceInMeters = root
                .path("routes")
                .get(0)
                .path("distance")
                .asDouble();
        return distanceInMeters / 1000;
    }

}
