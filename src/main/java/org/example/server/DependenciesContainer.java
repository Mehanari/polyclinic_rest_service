package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.InMemoryPolyclinicRepository;
import org.example.repository.PolyclinicRepository;

/**
 * This class is used to store dependencies of the application that are used in the controller layer (in those classes that implement HTTP methods).
 * For example, it can store repository objects, service objects, etc.
 * If you want classes in your controller layer to access same instances of objects, you can store those objects here.
 */
public class DependenciesContainer {
    private static final PolyclinicRepository POLYCLINIC_REPOSITORY;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        try {
            POLYCLINIC_REPOSITORY = new InMemoryPolyclinicRepository();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create repository", e);
        }
    }

    public static PolyclinicRepository getPolyclinicRepository() {
        return POLYCLINIC_REPOSITORY;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
