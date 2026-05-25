package com.qrcodegen;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class SampleDataGeneratorTest {

    private static final String[][] STUDENTS = {
        {"Alice Smith",     "Positive Behaviour"},
        {"Bob Johnson",     "Outstanding Achievement"},
        {"Charlie Brown",   "Needs Improvement"},
        {"Diana Prince",    "Excellent Participation"},
        {"Edward Norton",   "Disruptive Behaviour"},
        {"Fiona Green",     "Positive Behaviour"},
        {"George Miller",   "Outstanding Achievement"},
        {"Hannah White",    "Excellent Participation"},
        {"Ian Black",       "Needs Improvement"},
        {"Julia Roberts",   "Positive Behaviour"},
    };

    @Test
    void generateSampleStudentExcel() throws IOException {
        Path outputPath = Paths.get("sample-data/students.xlsx");
        Files.createDirectories(outputPath.getParent());

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");

            // Header row with bold styling
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row header = sheet.createRow(0);
            Cell h1 = header.createCell(0);
            h1.setCellValue("Student Name");
            h1.setCellStyle(headerStyle);

            Cell h2 = header.createCell(1);
            h2.setCellValue("BehaviourType");
            h2.setCellStyle(headerStyle);

            // Data rows
            for (int i = 0; i < STUDENTS.length; i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(STUDENTS[i][0]);
                row.createCell(1).setCellValue(STUDENTS[i][1]);
            }

            // Auto-size columns
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                workbook.write(fos);
            }
        }

        assertThat(outputPath.toFile()).exists();
        assertThat(outputPath.toFile().length()).isGreaterThan(0);
    }
}
