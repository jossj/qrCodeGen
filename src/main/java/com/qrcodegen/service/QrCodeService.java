package com.qrcodegen.service;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrCodeService {

    public byte[] generateQrCodeImage(String content, int width, int height) {
        ByteArrayOutputStream stream = QRCode.from(content)
                .withSize(width, height)
                .stream();
        return stream.toByteArray();
    }
}
