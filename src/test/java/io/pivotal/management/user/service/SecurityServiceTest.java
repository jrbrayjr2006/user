package io.pivotal.management.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityServiceTest {

    @Autowired
    private SecurityService service;

    @BeforeEach
    public void setUp() {
        this.service = new SecurityService();
    }

    @AfterEach
    public void tearDown() {
        this.service = null;
    }

    @Test
    public void securePasswordTest() {
        String password = "p@ssword123";
        String encryptedPassword = this.service.securePassword(password);
        System.out.println(encryptedPassword);
        String decryptedPassword = this.service.decryptPassword(encryptedPassword);
        assertEquals(password, decryptedPassword);
    }

    @Test
    public void getSaltTest() {
        String salt = this.service.getSalt();
        assertNotNull(salt);
    }
}
