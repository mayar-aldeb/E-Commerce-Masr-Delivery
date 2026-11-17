package org.route.service;

import org.route.dto.Coordinates;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {
    private final RestClient restClient;

    public GeocodingService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(
                        "User-Agent",
                        "MyDeliveryApplication/1.0"
                )
                .build();
    }

    public Coordinates getCoordinates(String district) {
        String address=district + ", Cairo, Egypt";
        List<Map<String, Object>> response =
                restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search")
                                .queryParam("q", address)
                                .queryParam("format", "json")
                                .queryParam("limit", 1)
                                .build())
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {});

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Address not found: " + address);
        }
        Map<String, Object> location = response.getFirst();
        double latitude = Double.parseDouble(location.get("lat").toString());
        double longitude =Double.parseDouble(location.get("lon").toString());
        return new Coordinates(longitude,latitude);
    }

}
