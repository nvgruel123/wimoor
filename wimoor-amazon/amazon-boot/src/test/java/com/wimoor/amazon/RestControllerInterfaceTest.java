package com.wimoor.amazon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class RestControllerInterfaceTest {

    @Test
    void allRestControllersExposeRequestMappings() throws IOException {
        Path sourceRoot = Paths.get("src/main/java");
        assertTrue(Files.exists(sourceRoot), "Source directory not found: " + sourceRoot.toAbsolutePath());

        try (Stream<Path> pathStream = Files.walk(sourceRoot)) {
            List<SourceFile> restControllers = pathStream
                .filter(path -> path.toString().endsWith(".java"))
                .map(path -> new SourceFile(path, readContent(path)))
                .filter(SourceFile::isRestController)
                .collect(Collectors.toList());

            List<String> missingMappings = new ArrayList<>();
            for (SourceFile controller : restControllers) {
                if (!containsAnyMappingAnnotation(controller.content)) {
                    missingMappings.add(sourceRoot.relativize(controller.path).toString());
                }
            }

            assertTrue(missingMappings.isEmpty(), "Controllers missing request mapping annotations: " + missingMappings);
        }
    }

    private static String readContent(Path file) {
        try {
            return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file " + file, e);
        }
    }

    private static boolean containsAnyMappingAnnotation(String content) {
        return content.contains("@RequestMapping")
            || content.contains("@GetMapping")
            || content.contains("@PostMapping")
            || content.contains("@PutMapping")
            || content.contains("@DeleteMapping")
            || content.contains("@PatchMapping");
    }

    private static class SourceFile {
        private final Path path;
        private final String content;

        private SourceFile(Path path, String content) {
            this.path = path;
            this.content = content;
        }

        private boolean isRestController() {
            return content.contains("@RestController") && !content.contains("@RestControllerAdvice");
        }
    }
}
