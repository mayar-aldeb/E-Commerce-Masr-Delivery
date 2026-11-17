package org.route.model;

import org.route.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Rider {
    private static int count=0;
    private int id;
    private String name;
    private VehicleType vehicleType;
    private String district;
    private boolean available;
    private int completedOrders;
    private Order activeOrder;
    private final List<Order> completedDeliveries=new LinkedList<>();


    public Rider(String name, VehicleType vehicleType) {
        this.name = name;
        this.vehicleType = vehicleType;
    }


    public static int createId(){
        return ++count;
    }
}
