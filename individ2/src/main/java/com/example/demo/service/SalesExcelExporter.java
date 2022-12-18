package com.example.demo.service;

import com.example.demo.models.Tovaradd;

import com.example.demo.models.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SalesExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Tovaradd> listSales;

    public SalesExcelExporter(List<Tovaradd> listSales) {
        this.listSales = listSales;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Sales");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Изображение в бд", style);
        createCell(row, 1, "Цена товара", style);
        createCell(row, 2, "Количество", style);
        createCell(row, 3, "Товар", style);
        createCell(row, 4, "номер партии", style);
        createCell(row, 5, "поставщик", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Tovaradd sale : listSales) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;




            String ClientFIO = sale.getFilename();
            String EmployeeFIO = "";

            String ClientInfoData = sale.getCena();
            String EmployeeInfoData = "";

            String Kolichestvo = sale.getKolichestvo();
            String test1 = "";

            String Opisanie = sale.getOpisanie();
            String nomerZakaza = "48892";




            createCell(row, columnCount++, ClientFIO, style);
            createCell(row, columnCount++, ClientInfoData, style);
            createCell(row, columnCount++, Kolichestvo, style);
            createCell(row, columnCount++, Opisanie, style);
            createCell(row, columnCount++, nomerZakaza, style);


        }
    }

    public void export(String currentDateTime) throws IOException {
        writeHeaderLine();
        writeDataLines();

        String path = "load/sales_" + currentDateTime + ".xlsx";

        File excelFile = new File(path);

        OutputStream outputStream = Files.newOutputStream(Paths.get(path));
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
