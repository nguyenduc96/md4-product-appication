package com.nguyenduc.custom;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            // should be /<path-to-projectBasedir>/build/classes/main/
            File pwd = new File(getClass().getResource("/").toURI());
            String projectDir = pwd.getParentFile().getParentFile().getParent();
            String conf = new File(projectDir, "db/init").getAbsolutePath();
            Map<String, Object> props = new HashMap<>();
            props.put("spring.datasource.url", conf);
            MapPropertySource mapPropertySource = new MapPropertySource("db-props", props);
            applicationContext.getEnvironment().getPropertySources().addFirst(mapPropertySource);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
