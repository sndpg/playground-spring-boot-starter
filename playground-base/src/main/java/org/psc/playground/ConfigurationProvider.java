package org.psc.playground;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationProvider {
    private String description;
    private Map<String, Object> properties;
}
