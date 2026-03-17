package framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    /**
     * Đọc toàn bộ data từ một sheet Excel [cite: 361]
     * Dòng đầu tiên (index 0) được coi là header bị bỏ qua [cite: 362]
     * Trả về Object[][] để dùng với @DataProvider [cite: 362]
     */
    public static Object[][] getData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet: " + sheetName);
            }

            int lastRow = sheet.getLastRowNum();
            int lastCol = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[lastRow][lastCol];

            for (int r = 1; r <= lastRow; r++) { // Bắt đầu từ dòng 1: bỏ header [cite: 380]
                Row row = sheet.getRow(r);
                if (row == null) continue;
                for (int c = 0; c < lastCol; c++) {
                    Cell cell = row.getCell(c);
                    data[r - 1][c] = getCellValue(cell); // [cite: 385]
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc Excel: " + filePath, e);
        }
    }

    /** Xử lý từng kiểu dữ liệu trong cell [cite: 393] */
    /** Xử lý từng kiểu dữ liệu trong cell (Dùng cú pháp Java 8 cho tương thích) */
    private static String getCellValue(Cell cell) {
        if (cell == null) return ""; // Xử lý cell null
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCachedFormulaResultType() == CellType.NUMERIC
                        ? String.valueOf((long) cell.getNumericCellValue())
                        : cell.getStringCellValue();
            default:
                return "";
        }
    }
}