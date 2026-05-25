package com.qrcodegen.config;

import com.qrcodegen.repository.InMemoryQrCodeRepository;
import com.qrcodegen.repository.QrCodeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("no-mongo")
public class NoMongoConfig {

    @Bean
    @Primary
    public QrCodeRepository qrCodeRepository() {
        return new InMemoryQrCodeRepository();
    }
}
