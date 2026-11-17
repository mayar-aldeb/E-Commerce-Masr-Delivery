package org.route.validation;

import org.route.exception.RiderException;
import org.route.model.Rider;
import org.springframework.http.HttpStatus;

public class RiderValidation {

    public static void validateRider(Rider rider){
        if(!rider.isAvailable())
            throw new RiderException(HttpStatus.NOT_FOUND,"Rider is not available.");
    }
}
