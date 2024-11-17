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

        File file = new File(ConfigManager.getStateFilePath());
        System.out.println("Saving state to: " + file.getAbsolutePath());

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        mapper.writeValue(file, hotelService);
    }

    public static HotelService loadState() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(ConfigManager.getStateFilePath());
        System.out.println("Loading state from: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("State file not found. Creating a new one.");
            return new HotelService();
        }

        return mapper.readValue(file, HotelService.class);
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
