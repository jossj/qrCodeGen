package com.qrcodegen.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrcodegen.model.QrCode;
import com.qrcodegen.repository.QrCodeRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class QrCodeService {

    private final QrCodeRepository repository;

    public QrCodeService(QrCodeRepository repository) {
        this.repository = repository;
    }

    public List<QrCode> findAll() {
        return repository.findAll();
    }

    public Optional<QrCode> findById(String id) {
        return repository.findById(id);
    }

    public List<QrCode> findByLabel(String label) {
        return repository.findByLabel(label);
    }

    public QrCode save(QrCode qrCode) {
        return repository.save(qrCode);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public byte[] generateQrCodeImage(String content, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
            return stream.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}
