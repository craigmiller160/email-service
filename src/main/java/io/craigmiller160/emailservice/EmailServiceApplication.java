package io.craigmiller160.emailservice;

import io.craigmiller160.webutils.tls.TlsConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailServiceApplication {

    private static final String TRUST_STORE_TYPE = "JKS";
    private static final String TRUST_STORE_PATH = "truststore.jks";
    private static final String TRUST_STORE_PASSWORD = "changeit";

    public static void main(String[] args) {
        TlsConfigurer.INSTANCE.configureTlsTrustStore(TRUST_STORE_PATH, TRUST_STORE_TYPE, TRUST_STORE_PASSWORD);
        SpringApplication.run(EmailServiceApplication.class, args);
    }

}
