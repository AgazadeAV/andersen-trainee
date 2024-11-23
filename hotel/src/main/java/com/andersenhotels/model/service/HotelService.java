package com.andersenhotels.model.service;

import com.andersenhotels.model.Hotel;

public class HotelService extends AbstractCrudService<Hotel, Integer> {
    public HotelService() {
        super(Hotel.class);
    }
}
