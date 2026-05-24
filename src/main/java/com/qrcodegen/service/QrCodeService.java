package com.qrcodegen.service;

import com.qrcodegen.model.QrCode;
import com.qrcodegen.repository.QrCodeRepository;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    /**
     * Generates a QR code image as a PNG byte array for the given content.
     */
    public byte[] generateQrCodeImage(String content, int width, int height) {
        ByteArrayOutputStream stream = QRCode.from(content)
                .withSize(width, height)
                .stream();
        return stream.toByteArray();
    }
}
