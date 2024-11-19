package com.andersenhotels.model.storage.json_storage;

import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.storage.DataStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonStorage implements DataStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonStorage.class);

    @Getter
    private static final String TEST_PATH = "src/main/resources/hotel_service_state_test.json";

    private final ObjectMapper mapper;

    public JsonStorage() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        LOGGER.info("JsonStorage initialized with ObjectMapper.");
    }

    @Override
    public void saveState(HotelService hotelService) throws IOException {
        File file = new File(ConfigManager.getStateFilePath());
        LOGGER.info("Saving state to: {}", file.getAbsolutePath());

        try {
            mapper.writeValue(file, hotelService);
            LOGGER.info("State saved successfully to: {}", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Failed to save state to file: {}", file.getAbsolutePath(), e);
            throw e;
        }
    }

    @Override
    public HotelService loadState() throws IOException {
        File file = new File(ConfigManager.getStateFilePath());
        LOGGER.info("Loading state from: {}", file.getAbsolutePath());

        if (!file.exists()) {
            LOGGER.warn("State file not found: {}. Creating a new instance.", file.getAbsolutePath());
            return new HotelService();
        }

        try {
            HotelService hotelService = mapper.readValue(file, HotelService.class);
            LOGGER.info("State loaded successfully from: {}", file.getAbsolutePath());
            return hotelService;
        } catch (IOException e) {
            LOGGER.error("Failed to load state from file: {}", file.getAbsolutePath(), e);
            throw e;
        }
    }

    @Override
    public void saveStateForTests(HotelService hotelService) throws IOException {
        File file = new File(TEST_PATH);
        LOGGER.info("Saving test state to: {}", file.getAbsolutePath());

        try {
            mapper.writeValue(file, hotelService);
            LOGGER.info("Test state saved successfully to: {}", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Failed to save test state to file: {}", file.getAbsolutePath(), e);
            throw e;
        }
    }

    @Override
    public HotelService loadStateForTests() throws IOException {
        File file = new File(TEST_PATH);
        LOGGER.info("Loading test state from: {}", file.getAbsolutePath());

        if (!file.exists()) {
            LOGGER.warn("Test state file not found: {}. Creating a new instance.", file.getAbsolutePath());
            return new HotelService();
        }

        try {
            HotelService hotelService = mapper.readValue(file, HotelService.class);
            LOGGER.info("Test state loaded successfully from: {}", file.getAbsolutePath());
            return hotelService;
        } catch (IOException e) {
            LOGGER.error("Failed to load test state from file: {}", file.getAbsolutePath(), e);
            throw e;
        }
    }
}
