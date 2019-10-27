package org.psc.playground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@SpringBootApplication
@EnableConfigurationProperties
@SpringBootTest
public class MiscAutoConfigurationTest {

    @Autowired
    private PlaygroundAutoConfigurationProperties playgroundAutoConfigurationProperties;

    @Test
    void contextLoads() {
        assertThat(playgroundAutoConfigurationProperties, is(not(nullValue())));
    }
}
