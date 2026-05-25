package com.qrcodegen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String content;
    private String label;
    private int width;
    private int height;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
