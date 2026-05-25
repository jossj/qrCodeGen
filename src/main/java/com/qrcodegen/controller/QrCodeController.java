package com.qrcodegen.controller;

import com.qrcodegen.model.QrCode;
import com.qrcodegen.service.QrCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService service;

    public QrCodeController(QrCodeService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestBody QrCode qrCode) {
        int width  = qrCode.getWidth()  > 0 ? qrCode.getWidth()  : 250;
        int height = qrCode.getHeight() > 0 ? qrCode.getHeight() : 250;

        byte[] image = service.generateQrCodeImage(qrCode.getContent(), width, height);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
