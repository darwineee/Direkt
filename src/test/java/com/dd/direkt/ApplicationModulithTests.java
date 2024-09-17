package com.dd.direkt;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;

@ApplicationModuleTest
@ActiveProfiles("staging")
class ApplicationModulithTests {

    @Test
    void contextLoads() {
        var modules = ApplicationModules.of(DirektApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }

}
