package com.andersenhotels.model.storage;

import com.andersenhotels.model.storage.db_storage.DatabaseStorage;
import com.andersenhotels.model.storage.json_storage.JsonStorage;

public class DataStorageFactory {

    public static DataStorage getStorage(DataStorageType type) {
        return switch (type) {
            case DATABASE -> new DatabaseStorage();
            default -> new JsonStorage();
        };
    }
}
