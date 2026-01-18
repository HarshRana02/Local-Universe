package com.localsphere.server;

import com.localsphere.server.hardware.HardwareGuard;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner {

    private final HardwareGuard hardwareGuard;

    public ApplicationStartupRunner(HardwareGuard hardwareGuard) {
        this.hardwareGuard = hardwareGuard;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        hardwareGuard.initialize();
    }
}
