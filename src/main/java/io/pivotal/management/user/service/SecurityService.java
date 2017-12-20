package io.pivotal.management.user.service;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

@Service
public class SecurityService {

    public static final String DEFAULT_PASSWORD = "p@ssword";

    //private String salt = "iehqfienvnfeiu9455695rbiutren9";
    private TextEncryptor encryptor;
    String salt;

    public String securePassword(String password) {
        this.salt = getSalt();
        if(this.salt == null) {
            this.salt = KeyGenerators.string().generateKey();
        }
        encryptor = getEncryptor(password, this.salt);
        String encryptedPassword = encryptor.encrypt(password);
        return encryptedPassword;
    }

    public String decryptPassword(String ePassword) {
        String password = encryptor.decrypt(ePassword);
        return password;
    }

    public String getSalt() {
        return KeyGenerators.string().generateKey();
    }

    protected TextEncryptor getEncryptor(String password, String storedSalt) {
        TextEncryptor encryptor = Encryptors.queryableText(password, storedSalt);
        return encryptor;
    }
}
