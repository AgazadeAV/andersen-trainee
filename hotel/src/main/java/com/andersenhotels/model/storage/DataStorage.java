package com.andersenhotels.model.storage;

import com.andersenhotels.model.Hotel;

import java.io.IOException;

public interface DataStorage {

    void saveState(Hotel hotel) throws IOException;

    Hotel loadState() throws IOException;

    void saveStateForTests(Hotel hotel) throws IOException;

    Hotel loadStateForTests() throws IOException;
}
