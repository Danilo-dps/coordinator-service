package br.com.danilodps.coordinator.controller;

import br.com.danilodps.coordinator.application.SecretStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secrets")
public class SecretController {

    private final SecretStoreService store;

    public SecretController(SecretStoreService store) {
        this.store = store;
    }

    @GetMapping("/current")
    public ResponseEntity<SecretResponse> getCurrent() {
        var current = store.getCurrent();
        return ResponseEntity.ok(new SecretResponse(current.version(), current.secret()));
    }

    @GetMapping("/{version}")
    public ResponseEntity<SecretResponse> getByVersion(@PathVariable String version) {
        var entry = store.getByVersion(version);
        if (entry == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new SecretResponse(entry.version(), entry.secret()));
    }

    @PostMapping("/rotate")
    public ResponseEntity<SecretResponse> rotate() {
        var rotated = store.rotate();
        return ResponseEntity.ok(new SecretResponse(rotated.version(), rotated.secret()));
    }

}