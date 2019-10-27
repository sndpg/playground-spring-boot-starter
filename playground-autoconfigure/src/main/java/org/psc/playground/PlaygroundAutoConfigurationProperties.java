package org.psc.playground;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("playground")
public class PlaygroundAutoConfigurationProperties {

    /**
     * Whether or not to enable {@link MiscAutoConfiguration}
     */
    private boolean enableMisc;

    /**
     * Whether or not to enable {@link DefaultExceptionHandlerAutoConfiguration}
     */
    private boolean enableExceptionHandler;
}
