package com.taoche.alertmanage.config;

import com.taoche.alertmanage.dto.RestrainItemDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "alert.restrain")
public class RestrainInfoConfig {
    private Map<String, RestrainItemDto> accountMap;
}