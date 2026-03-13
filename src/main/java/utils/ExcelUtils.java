package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static Object[][] getSheetData(String filePath, String sheetName) {

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }

            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[totalRows - 1][totalCols];

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < totalRows; i++) {

                Row row = sheet.getRow(i);

                for (int j = 0; j < totalCols; j++) {

                    String cellValue = "";

                    if (row != null && row.getCell(j) != null) {
                        cellValue = formatter.formatCellValue(row.getCell(j));
                    }

                    data[i - 1][j] = cellValue;
                }
            }

            return data;

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + filePath, e);
        }
    }

    public static int findColumnIndex(Sheet sheet, String columnName) {

        Row headerRow = sheet.getRow(0);

        if (headerRow == null) {
            throw new RuntimeException("Header row not found");
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {

            Cell cell = headerRow.getCell(i);

            if (cell != null &&
                    columnName.equalsIgnoreCase(cell.getStringCellValue().trim())) {

                return i;
            }
        }

        throw new RuntimeException("Column not found: " + columnName);
    }
}