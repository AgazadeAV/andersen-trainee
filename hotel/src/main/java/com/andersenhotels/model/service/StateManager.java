package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class StateManager {
    public static void saveState(HotelService hotelService) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(ConfigManager.getStateFilePath()), hotelService);
    }

    public static HotelService loadState() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(ConfigManager.getStateFilePath()), HotelService.class);
    }

    public static void saveStateForTests(HotelService hotelService, String testPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(testPath), hotelService);
    }

    public static HotelService loadStateForTests(String testPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(testPath), HotelService.class);
    }
}
