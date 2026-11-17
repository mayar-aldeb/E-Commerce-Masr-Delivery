package org.route.service;

import lombok.RequiredArgsConstructor;
import org.route.dto.rider.RiderRequest;
import org.route.exception.RiderException;
import org.route.mapper.RiderMapper;
import org.route.model.Order;
import org.route.model.Rider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.RiderRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepository;
    private final RiderMapper riderMapper;

    public void addRider(RiderRequest riderRequest){
        Rider rider=riderMapper.toEntity(riderRequest);
        rider.setId(Rider.createId());
        rider.setAvailable(true);
        riderRepository.addRider(rider);
    }


    public List<Rider> displayRiders(){
        return riderRepository.findAllRiders();
    }

    public Rider findRiderById(int riderId){
        return riderRepository.findRiderById(riderId).orElseThrow(()->new RiderException(HttpStatus.NOT_FOUND,"Rider not found"));
    }

    public void assignOrderToRider(Rider rider,Order order){
        rider.setDistrict(order.getDeliveryAddress().getDistrict());
        rider.setActiveOrder(order);
    }

    public void deliverOrder(Rider rider,Order order){
        rider.setDistrict(null);
        rider.setCompletedOrders(rider.getCompletedOrders()+1);
        rider.setActiveOrder(null);
        rider.getCompletedDeliveries().addFirst(order);
    }

}
