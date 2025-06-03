package fr.diginamic.hello.services;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public final class ResponseEntityService {

    private ResponseEntityService() {}

    public static <T> ResponseEntity<?> returnResponse(T entity, String label) {
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.status(404).body(label + " introuvable");
        }
    }

    public static <T> ResponseEntity<?> returnResponse(Collection<T> entities, String label) {
        if (entities != null && !entities.isEmpty()) {
            return ResponseEntity.ok(entities);
        } else {
            return ResponseEntity.status(404).body(label + " introuvables avec ces critères");
        }
    }
}
