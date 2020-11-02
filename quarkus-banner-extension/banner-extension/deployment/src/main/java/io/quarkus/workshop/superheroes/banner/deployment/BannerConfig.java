package io.quarkus.workshop.superheroes.banner.deployment;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;


// Banner Config -> Build Time
// ConfigRoot -> my-banner, phase= build time
// 1 ConfigItem : the path

@ConfigRoot(name = "my-banner", phase = ConfigPhase.BUILD_TIME)
public class BannerConfig {

    /**
     * The path of the banner.
     */
    @ConfigItem public String path;
}