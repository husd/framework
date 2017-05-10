package com.husd.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

    /**
     * 从excel中读取数据。
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static List<String[]> readFromExcel(InputStream in) throws IOException {
        return readAll(in, 0);
    }

    /**
     * 生成一个excel文件。
     * 
     * @param data
     * @param targetFile
     * @return
     */
    public static boolean createExcel2003(List<Object[]> data, File targetFile) {
        // 不设置必填项（必填项会增加红色） ，不设置列toPage的宽度

        return createExcel2003(data, targetFile, null, 10, -1, -1);
    }

    /**
     * 读取整个excel文件到list中去 注意： 由于excel中的数字格式内容如20，会被POI解析为double型20.0，
     * 读取到的列的宽度需要指定，默认第一行的宽度为整个excel列读取的宽度，多余的列将被忽略。
     *
     * @method: ExcelUtil readAll
     * @param in
     * @param startRow 从哪行开始读数据，默认为0。
     * @return
     * @throws IOException List<String[]>
     * @create date： 2015年11月4日 @2015, by hushengdong.
     */
    private static List<String[]> readAll(InputStream in, int startRow) throws IOException {
        List<String[]> allData = new ArrayList<String[]>();
        if (in == null)
            return allData;
        Workbook wb = new HSSFWorkbook(in);
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(0);
            int rowSize = sheet.getLastRowNum();
            if (rowSize == 0) {
                return allData;
            }
            int columnNumStandard = 0;
            short columnSize = sheet.getRow(columnNumStandard).getLastCellNum();
            for (int i = startRow; i <= rowSize; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                String[] temp = new String[columnSize];
                for (int j = 0; j < columnSize; j++) {
                    temp[j] = getStringValueFromCell(row.getCell(j));
                }
                allData.add(temp);
            }
        }
        return allData;
    }

    private static String getStringValueFromCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue = getCellStringValue(cell);
        // 如果从excel读取到的数字是一个科学计数法表示的字符串，需要把它再转换回来数字。
        if (NumberUtil.isENum(cellValue)) {
            BigDecimal bd = new BigDecimal(cellValue);
            cellValue = bd.toPlainString();
        }
        return cellValue;
    }



    /**
     * 这个方法是创建一个excel，支持设置表头第一行必填列为红色，支持设置任意一个范围内列的宽度
     *
     * @method: ExcelUtil createExcel2003
     * @param data 要写入excel的数据
     * @param targetFile 要写入的文件对象
     * @param requiredColumn 必填列，会标红，只有第一行会标示红色。
     * @param columnWidth 要设置列的宽度
     * @param startIndex 从哪一列开始设置宽度
     * @param endIndex 从那一列结束设置宽度
     * @return boolean 是否创建成功
     * @create date： 2016年4月12日 @2016, by hushengdong.
     */
    private static boolean createExcel2003(List<Object[]> data, File targetFile,
            int[] requiredColumn, int columnWidth, int startIndex, int endIndex) {
        if (data == null || data.size() == 0 || targetFile == null) {
            return false;
        }
        Workbook wb = new HSSFWorkbook();// 创建excel工作簿
        Sheet sheet = wb.createSheet();
        int currentRowNum = 0;
        HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.RED.index);
        for (Object[] rowData : data) {
            Row row = sheet.createRow(currentRowNum);
            int currentCellNum = 0;
            for (Object cellValue : rowData) {
                Cell cell = row.createCell(currentCellNum);
                cell.setCellValue(String.valueOf(cellValue));
                if (currentRowNum == 0 && requiredColumn != null) {
                    if (isColumnRequired(currentCellNum, requiredColumn)) {
                        cell.setCellStyle(style);
                    }
                }
                if (currentCellNum >= startIndex && currentCellNum <= endIndex) {
                    sheet.setColumnWidth(currentCellNum, columnWidth * 256);
                }
                currentCellNum++;
            }
            currentRowNum++;
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(targetFile);
            wb.write(out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return true;
    }

    private static boolean isColumnRequired(int cellNum, int[] requiredColumn) {
        for (int temp : requiredColumn) {
            if (cellNum == temp) {
                return true;
            }
        }
        return false;
    }

    private static String getCellStringValue(Cell cell) {
        String value = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                value = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                value = StringUtils.EMPTY;
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                value = cell.getStringCellValue();
                break;
        }
        return StringUtils.trim(value);
    }
}
