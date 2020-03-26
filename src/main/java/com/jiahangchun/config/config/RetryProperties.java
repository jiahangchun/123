package com.jiahangchun.config.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.cloud.config.retry")
public class RetryProperties {

    /**
     * Initial retry interval in milliseconds.
     */
    long initialInterval = 1000;

    /**
     * Multiplier for next interval.
     */
    double multiplier = 1.1;

    /**
     * Maximum interval for backoff.
     */
    long maxInterval = 2000;

    /**
     * Maximum number of attempts.
     */
    int maxAttempts = 6;

}
