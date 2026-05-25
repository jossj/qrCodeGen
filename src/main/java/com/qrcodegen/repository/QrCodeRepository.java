package com.qrcodegen.repository;

import com.qrcodegen.model.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, String> {

    List<QrCode> findByLabel(String label);
}
