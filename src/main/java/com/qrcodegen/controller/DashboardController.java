package com.qrcodegen.controller;

import com.qrcodegen.model.QrCode;
import com.qrcodegen.model.StudentQrCodeDTO;
import com.qrcodegen.service.QrCodeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final QrCodeService qrCodeService;

    public DashboardController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<StudentQrCodeDTO>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        List<StudentQrCodeDTO> results = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return ResponseEntity.badRequest().build();
            }

            int nameCol = -1, behaviourCol = -1;
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim().toLowerCase().replaceAll("\\s+", " ");
                if (header.equals("child name") || header.equals("student name")) {
                    nameCol = cell.getColumnIndex();
                } else if (header.equals("behaviour") || header.equals("behaviourtype") || header.equals("behaviour type")) {
                    behaviourCol = cell.getColumnIndex();
                }
            }

            if (nameCol == -1 || behaviourCol == -1) {
                return ResponseEntity.badRequest().build();
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String childName  = getCellValue(row.getCell(nameCol));
                String behaviour  = getCellValue(row.getCell(behaviourCol));
                if (childName.isBlank() && behaviour.isBlank()) continue;

                String qrContent = "Child Name: " + childName + "\nBehaviour: " + behaviour;
                byte[] imageBytes = qrCodeService.generateQrCodeImage(qrContent, 200, 200);
                String base64 = Base64.getEncoder().encodeToString(imageBytes);

                QrCode record = QrCode.builder()
                        .childName(childName)
                        .behaviour(behaviour)
                        .content(qrContent)
                        .width(200)
                        .height(200)
                        .build();
                qrCodeService.save(record);

                results.add(new StudentQrCodeDTO(childName, behaviour, base64));
            }
        }

        return ResponseEntity.ok(results);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default      -> "";
        };
    }
}
