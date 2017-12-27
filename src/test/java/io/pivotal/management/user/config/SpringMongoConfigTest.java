package io.pivotal.management.user.config;

import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SpringMongoConfigTest {

    @Autowired
    private SpringMongoConfig config;

    @Before
    public void setUp() {
        this.config = new SpringMongoConfig();
    }

    @After
    public void tearDown() {
        this.config = null;
    }

    @Test
    public void configurationTest() {
        fail("Test not implemented yet!");
    }

    @Test
    public void SpringMongoConfigTest() {
        MongoClient actual = config.mongoClient();
        assertNotNull(actual);
    }

    @Test
    public void getDatabaseNameTest() {
        String actual = config.getDatabaseName();
        String expected = "testdb";
        assertEquals(expected, actual);
    }
}
