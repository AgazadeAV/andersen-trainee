package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

public class StateManager {

    @Getter
    private static String PATH = ConfigManager.getStateFilePath();

    public static void setPATH(String PATH) {
        StateManager.PATH = PATH;
    }

    public static void saveState(HotelService hotelService) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(PATH), hotelService);
    }

    public static HotelService loadState() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(PATH), HotelService.class);
    }
}
