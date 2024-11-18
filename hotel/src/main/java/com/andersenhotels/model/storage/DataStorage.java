package com.andersenhotels.model.storage;

import com.andersenhotels.model.service.HotelService;

import java.io.IOException;

public interface DataStorage {

    void saveState(HotelService hotelService) throws IOException;

    HotelService loadState() throws IOException;

    void saveStateForTests(HotelService hotelService) throws IOException;

    HotelService loadStateForTests() throws IOException;
}
