package com.qrcodegen.service;

import com.qrcodegen.repository.QrCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class QrCodeServiceTest {

    // PNG file signature — first 8 bytes of every valid PNG
    private static final byte[] PNG_MAGIC = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    @Mock
    private QrCodeRepository repository;

    private QrCodeService service;

    @BeforeEach
    void setUp() {
        service = new QrCodeService(repository);
    }

    @Test
    void generateQrCodeImage_returnsNonEmptyByteArray() {
        byte[] result = service.generateQrCodeImage("https://example.com", 250, 250);

        assertThat(result).isNotNull().isNotEmpty();
    }

    @Test
    void generateQrCodeImage_returnsPngImage() {
        byte[] result = service.generateQrCodeImage("https://example.com", 250, 250);

        assertThat(result).hasSizeGreaterThanOrEqualTo(PNG_MAGIC.length);
        assertThat(result).startsWith(PNG_MAGIC);
    }

    @Test
    void generateQrCodeImage_largerSizeProducesLargerImage() {
        byte[] small = service.generateQrCodeImage("test", 100, 100);
        byte[] large = service.generateQrCodeImage("test", 500, 500);

        assertThat(large.length).isGreaterThan(small.length);
    }

    @Test
    void generateQrCodeImage_worksWithPlainText() {
        byte[] result = service.generateQrCodeImage("Hello, World!", 300, 300);

        assertThat(result).isNotEmpty();
        assertThat(result).startsWith(PNG_MAGIC);
    }

    @Test
    void generateQrCodeImage_throwsOnBlankContent() {
        assertThatThrownBy(() -> service.generateQrCodeImage("", 250, 250))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to generate QR code");
    }
}
