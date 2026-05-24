package com.qrcodegen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "qr_codes")
public class QrCode {

    @Id
    private String id;

    private String content;
    private String label;
    private int width;
    private int height;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
