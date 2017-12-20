package io.pivotal.management.user.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SecurityServiceTest {

    @Autowired
    private SecurityService service;

    @Before
    public void setUp() {
        this.service = new SecurityService();
    }

    @After
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
