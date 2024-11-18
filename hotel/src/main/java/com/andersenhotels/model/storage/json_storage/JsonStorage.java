package com.andersenhotels.model.storage.json_storage;

import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.storage.DataStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class JsonStorage implements DataStorage {

    @Getter
    private static final String TEST_PATH = "src/main/resources/hotel_service_state_test.json";

    private final ObjectMapper mapper;

    public JsonStorage() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveState(HotelService hotelService) throws IOException {
        File file = new File(ConfigManager.getStateFilePath());
        System.out.println("Saving state to: " + file.getAbsolutePath());

        mapper.writeValue(file, hotelService);
    }

    public HotelService loadState() throws IOException {
        File file = new File(ConfigManager.getStateFilePath());
        System.out.println("Loading state from: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("State file not found. Creating a new instance.");
            return new HotelService();
        }

        return mapper.readValue(file, HotelService.class);
    }

    public void saveStateForTests(HotelService hotelService) throws IOException {
        File file = new File(TEST_PATH);
        mapper.writeValue(file, hotelService);
    }

    public HotelService loadStateForTests() throws IOException {
        File file = new File(TEST_PATH);

        if (!file.exists()) {
            System.out.println("Test state file not found. Creating a new instance.");
            return new HotelService();
        }

        return mapper.readValue(file, HotelService.class);
    }
}
