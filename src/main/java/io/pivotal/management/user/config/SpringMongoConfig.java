package io.pivotal.management.user.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

public class SpringMongoConfig {


    private String profileActive;


    private String proAppName;

    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDB;


    public MongoClient mongoClient() {
        return new MongoClient(this.mongoHost);
    }


    protected String getDatabaseName() {
        return this.mongoDB;
    }
}
