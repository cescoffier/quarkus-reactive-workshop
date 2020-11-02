package io.quarkus.workshop.superheroes.banner.runtime;

import io.quarkus.runtime.annotations.Recorder;


// Recorder
// Will execute that "executed" code at runtime (during the startup)
// Add print method, printing the banner

@Recorder
public class BannerRecorder {

    public void print(String banner) {
        System.err.println(banner);
    }
}