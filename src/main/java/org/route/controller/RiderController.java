package org.route.controller;

import lombok.RequiredArgsConstructor;
import org.route.dto.order.OrderResponse;
import org.route.platform.RiderPlatform;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rider")
public class RiderController {
//    ========================================
//              Rider Platform
//    ========================================
    private final RiderPlatform riderPlatform;
    //1.Go on duty.
    @PatchMapping("/onDuty{riderId}")
    public void onDuty(@PathVariable int riderId) {
        riderPlatform.goOnDuty(riderId);
    }

    //2.Go off duty.
    @PatchMapping("/offDuty{riderId}")
    public void offDuty(@PathVariable int riderId) {
        riderPlatform.goOffDuty(riderId);
    }

    //3.assign order.
    @PatchMapping("/newOrder{riderId}")
    public void assignOrder(@PathVariable int riderId) {
        riderPlatform.assignOrder(riderId);
    }

    //4.View the currently assigned order.
    @GetMapping("/assignedOrder{riderId}")
    public OrderResponse assignedOrder(@PathVariable int riderId) {
        return riderPlatform.viewAssignedOrder(riderId);
    }

    //5.Mark order picked up.
    @PatchMapping("/pickUp{riderId}")
    public void markPickedUp(@PathVariable int riderId) {
        riderPlatform.markPickedUp(riderId);
    }

    //6.Mark order delivered.
    @PatchMapping("/deliverOrder{riderId}")
    public void deliverOrder(@PathVariable int riderId) {
        riderPlatform.deliverOrder(riderId);
    }

    //7.View personal delivery statistics.
    @GetMapping("/personalDeliveryStatistics{riderId}")
    public List<OrderResponse> personalDeliveryStatistics(@PathVariable int riderId) {
        return riderPlatform.personalDeliveryStatistics(riderId);
    }




}
