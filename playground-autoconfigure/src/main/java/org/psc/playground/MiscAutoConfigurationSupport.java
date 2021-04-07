package org.psc.playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Map;

@Slf4j
@ConditionalOnBean({MiscConfigurationBase.class})
public class MiscAutoConfigurationSupport implements ApplicationContextAware {

    private ConfigurationProvider configurationProvider;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        GenericApplicationContext genericApplicationContext =
                (GenericApplicationContext) applicationContext;
        Map<String, MiscConfigurationBase> miscConfigurationBases =
                genericApplicationContext.getBeansOfType(MiscConfigurationBase.class);
        miscConfigurationBases.forEach((k, v) -> log.info("{} -> {}", k, v));

        configurationProvider = new ConfigurationProvider();
        configurationProvider.setDescription("TEST");

        genericApplicationContext.registerBean("configurationProvider", ConfigurationProvider.class, configurationProvider);

        miscConfigurationBases.forEach(
                (name, miscConfigurationBase) -> miscConfigurationBase.configure(configurationProvider));
    }

    @Bean
    public ConfiguredResult configuredResult() {
        return new ConfiguredResult(configurationProvider);
    }
}
