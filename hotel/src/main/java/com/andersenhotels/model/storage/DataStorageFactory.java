package com.andersenhotels.model.storage;

import com.andersenhotels.model.storage.db_storage.DatabaseStorage;
import com.andersenhotels.model.storage.json_storage.JsonStorage;

public class DataStorageFactory {

    public static DataStorage getStorage(DataStorageType type) {
        switch (type) {
            case DATABASE:
                return new DatabaseStorage();
            case JSON:
            default:
                return new JsonStorage();
        }
    }
}
