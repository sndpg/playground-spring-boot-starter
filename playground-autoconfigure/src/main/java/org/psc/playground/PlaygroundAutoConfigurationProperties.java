package org.psc.playground;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Setter
@Getter
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

    /**
     * Whether or not to enable {@link org.psc.playground.security.JwtSecurityAutoConfiguration}
     */
    private boolean enableJwtSecurity;

    private Autoconfigure autoconfigure;

    @Setter
    @Getter
    public static class Autoconfigure {

        /**
         * Whether or not to enable the MiscAutoConfiguration
         */
        private boolean misc;

        /**
         * Whether or not to enable the DefaultExceptionHandlerAutoConfiguration
         */
        private boolean defaultExceptionHandler;

        /**
         * Whether or not to enable the JwtSecurityAutoConfiguration
         */
        private boolean jwtSecurity;

        /**
         * Whether or not to enable the DefaultSecurityAutoConfiguration
         */
        private boolean defaultSecurity;
    }

}
