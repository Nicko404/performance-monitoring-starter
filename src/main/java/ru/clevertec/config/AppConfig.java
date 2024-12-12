package ru.clevertec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("ru.clevertec")
@PropertySource(value = "classpath:application.yml", factory = ApplicationPropertiesFactory.class)
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public PerformanceMonitorProperties monitorPerformanceConfigurationProperties(Environment env) {
        return PerformanceMonitorProperties.builder()
                .enabled(env.getProperty("performance-monitor.enabled", Boolean.class, false))
                .minTime(env.getProperty("performance-monitor.min-time", Integer.class, 100))
                .build();
    }
}
