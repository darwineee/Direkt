package com.dd.direkt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.ActiveProfiles;

@ApplicationModuleTest
@ActiveProfiles("staging")
@SpringBootTest(classes = ApplicationModuleTest.class)
class ApplicationModulithTests {

    @Test
    void contextLoads() {
        var modules = ApplicationModules.of(DirektApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }

}
