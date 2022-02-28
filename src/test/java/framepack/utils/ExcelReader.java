package framepack.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import reports.ReportTrail;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelReader {

    public static String getCellData(String fileName, String sheetName, String rowKeyName, String columnHeading) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();

        int cellIndex = 0;
        Row rowHeading = excelWorkBookSheet.getRow(0);
        for (int cellNum = 0; cellNum < rowHeading.getLastCellNum(); cellNum++) {
            if (formatter.formatCellValue(rowHeading.getCell(cellNum)).equalsIgnoreCase(columnHeading)) {
                cellIndex = cellNum;
                break;
            }
        }

        String value = null;

        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            Row row = excelWorkBookSheet.getRow(rowNum);
            if (formatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowKeyName)) {
                value = formatter.formatCellValue(row.getCell(cellIndex));
                break;
            }

        }
        excelWorkBook.close();

        return value;
    }

    public static ArrayList<String> getRowOfData(String fileName, String sheetName, String rowKeyName) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();
        ArrayList<String> rowData = new ArrayList<>();
        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            Row row = excelWorkBookSheet.getRow(rowNum);
            if (formatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowKeyName)) {
                for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                    rowData.add(formatter.formatCellValue(row.getCell(cellNum)));
                }
                break;
            }

        }
        excelWorkBook.close();
        return rowData;
    }

    public static boolean updateCellData(String fileName, String sheetName, String rowKeyName, String columnHeading, String valueToSet) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();

        int cellIndex = 0;
        Row rowHeading = excelWorkBookSheet.getRow(0);
        for (int cellNum = 0; cellNum < rowHeading.getLastCellNum(); cellNum++) {
            if (formatter.formatCellValue(rowHeading.getCell(cellNum)).equalsIgnoreCase(columnHeading)) {
                cellIndex = cellNum;
                break;
            }
        }


        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            Row row = excelWorkBookSheet.getRow(rowNum);
            if (formatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowKeyName)) {
                row.getCell(cellIndex).setCellValue(valueToSet);
                break;
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            excelWorkBook.write(outputStream);
            excelWorkBook.close();
        }

        return true;
    }

    public static boolean appendCellDataEndOfRow(String fileName, String sheetName, String rowKeyName,  String valueToSet) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();


        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            Row row = excelWorkBookSheet.getRow(rowNum);
            if (formatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowKeyName)) {
                int colCount = excelWorkBookSheet.getRow(0).getLastCellNum();
                Cell cell = row.createCell(colCount++);
                cell.setCellValue(valueToSet);
                break;
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            excelWorkBook.write(outputStream);
            excelWorkBook.close();
        }

        return true;
    }

    public static boolean addDataAsNewRow(String fileName, String sheetName, ArrayList<String> rowOfData) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        Row row = excelWorkBookSheet.createRow(++rowCount);
        int columnCount = 0;
        for (String field : rowOfData) {
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(field);
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            excelWorkBook.write(outputStream);
            excelWorkBook.close();
        }

        return true;
    }

    public static boolean updateAllDataOfTheRow(String fileName, String sheetName, String rowKeyName,  ArrayList<String> rowOfData) throws IOException {

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();

        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            Row row = excelWorkBookSheet.getRow(rowNum);
            if (formatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowKeyName)) {
                int currentColCount = excelWorkBookSheet.getRow(rowNum).getLastCellNum();
                int columnToDelete = 1;
                while (columnToDelete<=currentColCount){
                    Cell cell = row.getCell(columnToDelete);
                    if(cell !=null){
                        row.removeCell(cell);
                    }
                    columnToDelete++;
                }

                for(int col=1; col<=rowOfData.size(); col++){
                    Cell cell = row.getCell(col);
                    if(cell ==null){
                        cell = row.createCell(col);
                    }
                    cell.setCellValue(rowOfData.get(col-1));
                }
                break;
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            excelWorkBook.write(outputStream);
            excelWorkBook.close();
        }

        return true;

    }

    public static ArrayList<ArrayList<String>> readExcelArrayList(String fileName, String sheetName)
            throws IOException {

        ArrayList<ArrayList<String>> eData = new ArrayList<>();

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        DataFormatter formatter = new DataFormatter();

        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            ArrayList<String> excelData = new ArrayList<>();
            Row row = excelWorkBookSheet.getRow(rowNum);
            for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                excelData.add(formatter.formatCellValue(row.getCell(cellNum)));
            }
            eData.add(excelData);
        }
        excelWorkBook.close();
        return eData;
    }

    /**
     * Read the excel data and return it as ArrayList<HashMap<String, String>>
     *
     * @param fileName  The file name with path
     * @param sheetName The sheet name
     * @return ArrayList<HashMap < String, String>> with the excel data
     * @throws IOException Throws the IOException if unable to get the data from the excel sheet
     */
    public static ArrayList<HashMap<String, String>> readExcelHashMap(
            String fileName, String sheetName) throws IOException {
        ArrayList<HashMap<String, String>> sheetData = new ArrayList<>();

        Workbook excelWorkBook = getWorkBookData(fileName);

        if (excelWorkBook == null) {
            throw new IOException("Invalid file at location " + fileName);
        }

        Sheet excelWorkBookSheet = getSheetData(excelWorkBook, sheetName);

        int colCount = excelWorkBookSheet.getRow(0).getLastCellNum();

        int rowCount = excelWorkBookSheet.getLastRowNum() - excelWorkBookSheet.getFirstRowNum();

        HashMap<Integer, String> keySet = new HashMap<>();
        DataFormatter formatter = new DataFormatter();

        for (int rowNum = 0; rowNum < rowCount + 1; rowNum++) {
            keySet.put(rowNum, formatter.formatCellValue(excelWorkBookSheet.getRow(rowNum).getCell(0)));
        }

        for (int col = 1; col < colCount; col++) {
            HashMap<String, String> data = new HashMap<>();
            for (int rowNum = 1; rowNum < rowCount + 1; rowNum++) {
                data.put(
                        keySet.get(rowNum),
                        formatter.formatCellValue(excelWorkBookSheet.getRow(rowNum).getCell(col)));
            }
            sheetData.add(data);
        }
        return sheetData;
    }

    /**
     * Get the work book from the excel
     *
     * @param fileName The file path
     * @return The workbook from the excel file.
     */
    private static Workbook getWorkBookData(String fileName) {
        Workbook excelWorkBook = null;
        try {
            File excelFile = new File(fileName);
            FileInputStream inputStream = new FileInputStream(excelFile);
            if (fileName.endsWith("xlsx")) {
                excelWorkBook = new XSSFWorkbook(inputStream);
            } else {
                excelWorkBook = new HSSFWorkbook(inputStream);
            }
        } catch (NullPointerException e) {
            ReportTrail.error(e.getMessage());
            ReportTrail.error("File path was a null");
        } catch (FileNotFoundException e) {
            ReportTrail.error(e.getMessage());
            ReportTrail.error("File not found at location " + fileName);
        } catch (IOException e) {
            ReportTrail.error(e.getMessage());
            ReportTrail.error("The file at location " + fileName + " is not a valid excel file.");
        }
        return excelWorkBook;
    }

    /**
     * Get the sheet name from the workbook; if the sheet name is not provided returns the first sheet
     *
     * @param excelWorkBook The excel work book
     * @param sheetName     The name of the sheet
     * @return The Sheet with the provided name
     */
    private static Sheet getSheetData(Workbook excelWorkBook, String sheetName) {

        Sheet excelWorkBookSheet;
    /*
    Read the excel sheet
    If the sheet name is not provided takes the first sheet
     */
        if (sheetName == null) {
            excelWorkBookSheet = excelWorkBook.getSheetAt(0);
        } else {
            excelWorkBookSheet = excelWorkBook.getSheet(sheetName);
        }

        return excelWorkBookSheet;
    }
}
