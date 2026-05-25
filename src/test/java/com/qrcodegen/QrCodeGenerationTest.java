package com.qrcodegen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class QrCodeGenerationTest {

    private static final int SIZE = 250;
    private static final Path OUTPUT_DIR = Paths.get("qrcodes");

    @BeforeAll
    static void createOutputDir() {
        OUTPUT_DIR.toFile().mkdirs();
    }

    private BitMatrix encode(String content) throws WriterException {
        return new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, SIZE, SIZE);
    }

    @Test
    void generateQrCodeToFile() throws WriterException, IOException {
        // File file = QRCode.from("Hello World").file();
        Path dest = OUTPUT_DIR.resolve("hello-world.png");
        MatrixToImageWriter.writeToPath(encode("Hello World"), "PNG", dest);

        File file = dest.toFile();
        assertThat(file).exists();
        assertThat(file.length()).isGreaterThan(0);
    }

    @Test
    void generateQrCodeToStream() throws WriterException, IOException {
        // ByteArrayOutputStream stream = QRCode.from("Hello World").stream();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(encode("Hello World"), "PNG", stream);

        byte[] bytes = stream.toByteArray();
        assertThat(bytes).isNotEmpty();

        try (FileOutputStream fos = new FileOutputStream(OUTPUT_DIR.resolve("hello-world-stream.png").toFile())) {
            fos.write(bytes);
        }
    }

    @Test
    void generateQrCodeToJpgFile() throws WriterException, IOException {
        // QRCode.from("Hello World").to(ImageType.JPG).file();
        Path dest = OUTPUT_DIR.resolve("hello-world.jpg");
        MatrixToImageWriter.writeToPath(encode("Hello World"), "JPEG", dest);

        File file = dest.toFile();
        assertThat(file).exists();
        assertThat(file.length()).isGreaterThan(0);
    }

    @Test
    void generateQrCodeToJpgStream() throws WriterException, IOException {
        // QRCode.from("Hello World").to(ImageType.JPG).stream();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(encode("Hello World"), "JPEG", stream);

        byte[] bytes = stream.toByteArray();
        assertThat(bytes).isNotEmpty();

        try (FileOutputStream fos = new FileOutputStream(OUTPUT_DIR.resolve("hello-world-stream.jpg").toFile())) {
            fos.write(bytes);
        }
    }
}
