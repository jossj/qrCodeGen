package com.qrcodegen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQrCodeDTO {
    private String childName;
    private String behaviour;
    private String qrCodeBase64;
}
