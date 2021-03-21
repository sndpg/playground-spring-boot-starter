package org.psc.playground;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.annotation.Bean;

@ConditionalOnJava(range = ConditionalOnJava.Range.OLDER_THAN, value = JavaVersion.NINE)
public class Java8AutoConfiguration {

    @Bean
    public Java8Bean java8Bean(){
        return new Java8Bean();
    }

    public static class Java8Bean {
        private static final String JAVA_VERSION = "8";

        public String getVersion() {
            return JAVA_VERSION;
        }
    }
}
