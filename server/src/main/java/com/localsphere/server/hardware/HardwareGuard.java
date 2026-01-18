package com.localsphere.server.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class HardwareGuard {

    private static final Logger logger = LoggerFactory.getLogger(HardwareGuard.class);
    private static final String TARGET_GPU = "GTX 1060";

    public void initialize() {
        if (isGpuPresent()) {
            logger.info("Target GPU ({}) detected. Applying settings...", TARGET_GPU);
            setGpuPersistenceMode();
            setGpuClockSpeeds();
        } else {
            logger.info("Target GPU ({}) not found. Skipping GPU-specific hardware management.", TARGET_GPU);
        }
    }

    private boolean isGpuPresent() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("nvidia-smi", "-L");
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(TARGET_GPU)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Failed to execute 'nvidia-smi -L'. Make sure nvidia-smi is installed and in the system's PATH.", e);
        }
        return false;
    }

    private void setGpuPersistenceMode() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("nvidia-smi", "-pm", "1");
            Process process = processBuilder.start();
            process.waitFor();
            logger.info("Set GPU persistence mode.");
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to set GPU persistence mode.", e);
        }
    }

    private void setGpuClockSpeeds() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("nvidia-smi", "-lgc", "800,1200");
            Process process = processBuilder.start();
            process.waitFor();
            logger.info("Set GPU clock speeds.");
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to set GPU clock speeds.", e);
        }
    }
}
