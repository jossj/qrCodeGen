package com.qrcodegen.repository;

import com.qrcodegen.model.QrCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QrCodeRepository extends MongoRepository<QrCode, String> {

    List<QrCode> findByLabel(String label);
}
