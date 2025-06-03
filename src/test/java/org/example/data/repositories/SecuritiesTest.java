package org.example.data.repositories;
import org.example.data.models.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
public class SecuritiesTest {

    @Autowired
    private Securities securities;

    @Test
    void testSaveSecurity() {
        Security security = new Security();
        security.setName("Security");
        security.setEmail("security.@gmail.com");
        security.setPassword("password");

        Security savedSecurity = securities.save(security);
        assertNotNull(savedSecurity.getId());
        assertEquals("Security", savedSecurity.getName());
    }
}
