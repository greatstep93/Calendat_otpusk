package ru.greatstep.spring_sqlite.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.greatstep.spring_sqlite.models.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter {
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private final List<User> userList;

    public UserExcelExporter(List<User> userList) {
        this.userList = userList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Users");

    }

    void setAllBorder(CellStyle style) {
        BorderStyle borderStyle = BorderStyle.THIN;

        style.setBorderBottom(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderTop(borderStyle);
        style.setBorderRight(borderStyle);
    }

    private void writeHeaderRow() {

        // создаем лист

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(15);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        setAllBorder(style);


        // создаем колонки

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(0);

        cell = row.createCell(1);
        cell.setCellValue("ФИО");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(1);

        cell = row.createCell(2);
        cell.setCellValue("Должность");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(2);

        cell = row.createCell(3);
        cell.setCellValue("Дата начала отпуска");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(3);

        cell = row.createCell(4);
        cell.setCellValue("Дата окончания отпуска");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(4);

        cell = row.createCell(5);
        cell.setCellValue("Количество дней отпуска");
        cell.setCellStyle(style);
        sheet.autoSizeColumn(5);

    }

    private void writeDataRows() {
        int rowCont = 1;

        for (User user : userList) {
            Row row = sheet.createRow(rowCont++);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(13);
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            setAllBorder(style);

            Cell cell = row.createCell(0);
            cell.setCellValue(user.getId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(user.getFullName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(user.getPosition());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(user.getVacationStart());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(user.getVacationEnd());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(user.getVacationDaysCount());
            sheet.autoSizeColumn(5);
            cell.setCellStyle(style);

        }

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();


    }
}
