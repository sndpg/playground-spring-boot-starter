package org.psc.playground;

import lombok.Data;

@Data
public class ConfiguredResult {
    private String description;

    public ConfiguredResult(ConfigurationProvider configurationProvider) {
        this.description = configurationProvider.getDescription();
    }

}
