package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScenarioStore {
    private final Path file = Path.of("data", "scenarios.txt");

    public void save(ShareScenario s, RiskResult r) {
        try {
            Files.createDirectories(file.getParent());
            String line = String.join(" | ",
                    s.name(), s.fileType(), String.valueOf(r.score()), r.level().name());
            Files.writeString(file, line + System.lineSeparator(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save scenario.", e);
        }
    }

    public List<String> readAll() {
        try {
            if (!Files.exists(file)) return new ArrayList<>();
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read saved scenarios.", e);
        }
    }
}
