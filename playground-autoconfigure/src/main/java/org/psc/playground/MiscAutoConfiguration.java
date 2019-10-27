package org.psc.playground;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "playground", name = "enable-misc", havingValue = "true", matchIfMissing = true)
public class MiscAutoConfiguration {}
