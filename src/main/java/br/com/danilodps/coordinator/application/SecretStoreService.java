package br.com.danilodps.coordinator.application;

import br.com.danilodps.coordinator.domain.SecretEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class SecretStoreService {

    private final AtomicInteger versionCounter = new AtomicInteger(1);
    private final AtomicReference<SecretEntry> current = new AtomicReference<>();
    private final Map<String, SecretEntry> history = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    public SecretStoreService() {
        String initial = generateSecret();
        SecretEntry entry = new SecretEntry("v1", initial, Instant.now());
        current.set(entry);
        history.put("v1", entry);
        log.info("[Coordinator] Inicializado com {}", entry.version());
    }

    public synchronized SecretEntry rotate() {
        String newVersion = "v" + versionCounter.incrementAndGet();
        String newSecret = generateSecret();
        SecretEntry entry = new SecretEntry(newVersion, newSecret, Instant.now());

        current.set(entry);
        history.put(newVersion, entry);

        log.info("[Coordinator] Rotacionado: {}", newVersion);
        return entry;
    }

    public SecretEntry getCurrent() {
        return current.get();
    }

    public SecretEntry getByVersion(String version) {
        return history.get(version);
    }

    private String generateSecret() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

}