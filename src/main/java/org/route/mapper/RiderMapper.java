package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.rider.RiderRequest;
import org.route.dto.rider.RiderResponse;
import org.route.model.Rider;

@Mapper(componentModel = "spring")
public interface RiderMapper {
    RiderResponse toResponse(Rider rider);
    Rider  toEntity(RiderRequest riderRequest);
}
