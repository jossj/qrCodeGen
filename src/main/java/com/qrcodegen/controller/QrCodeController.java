package com.qrcodegen.controller;

import com.qrcodegen.model.QrCode;
import com.qrcodegen.service.QrCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService service;

    public QrCodeController(QrCodeService service) {
        this.service = service;
    }

    @GetMapping
    public List<QrCode> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QrCode> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<QrCode> getByLabel(@RequestParam String label) {
        return service.findByLabel(label);
    }

    @PostMapping
    public ResponseEntity<QrCode> create(@RequestBody QrCode qrCode) {
        QrCode saved = service.save(qrCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Generates a QR code PNG image for the given content and saves the record.
     * Returns the image bytes directly so callers can display or download it.
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestBody QrCode qrCode) {
        int width  = qrCode.getWidth()  > 0 ? qrCode.getWidth()  : 250;
        int height = qrCode.getHeight() > 0 ? qrCode.getHeight() : 250;

        qrCode.setWidth(width);
        qrCode.setHeight(height);
        service.save(qrCode);

        byte[] image = service.generateQrCodeImage(qrCode.getContent(), width, height);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
