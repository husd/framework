package com.husd.framework.excel;

import com.husd.framework.util.CollectionUtil;
import com.husd.framework.util.DateUtil;
import com.husd.framework.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * <pre>
 *  读：ExcelUtility.{@link #newImportBuilder(File, Class, String[])}.build()构造， 然后调用{@link #readExcel()}返回数据集合。
 *  写: ExcelUtility.{@link #newExportBuilder(String, String[], String[], List)}.build()构造， 然后调用{@link #writeExcel()}写入文件。
 * </pre>
 * <p>
 * 非线程安全
 */
@SuppressWarnings("unchecked")
public final class ExcelUtility<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtility.class);

    private static final String LINE_NUM_NAME = "excelLineNum";
    /**
     * 导入imp,导出exp
     */
    private final Tag tag;
    /**
     * Excel版本
     */
    private final Excel version;
    /**
     * 组装对象属性列
     */
    private final String[] properties;
    /**
     * 表头列
     */
    private final String[] headers;
    /**
     * 数据的文件全路径
     */
    private final String fullPathName;
    /**
     * 最大上传限制
     */
    private final int maxOperateSum;
    /**
     * workbook
     */
    private Workbook wookbook;
    /**
     * 第几个sheet
     */
    private int sheet;
    /**
     * 对象class
     */
    private Class<T> clazz;
    /**
     * 导出数据的内容集
     */
    private List<T> contentList;
    /**
     * 此动态列
     */
    private String dynamicProperty;
    /**
     * 动态列导出表头
     */
    private String[] dynamicHeaders;
    /**
     * 导出文件中的必填项
     */
    private Set<String> requiredColumns;
    /**
     * 动态属性KV形式的自定义关系，例如：K:北京,V：6；代表将 T 中dynamicProperty属性的Key(北京)替换成用户自定义的值6
     */
    private Map<String, Object> dynamicColumnParameters;

    private ExcelUtility(Export<T> exp) {

        this.tag = exp.tag;
        this.fullPathName = exp.fullPathName;
        this.contentList = exp.contents;
        this.headers = exp.headers;
        this.properties = exp.properties;
        this.version = exp.version;
        this.maxOperateSum = exp.maxExportNum;
        this.dynamicProperty = exp.dynamicProperty;
        this.dynamicHeaders = exp.dynamicHeaders;
        this.requiredColumns = exp.requiredColumns;
    }

    private ExcelUtility(Import<T> imp) {

        this.tag = imp.tag;
        this.wookbook = imp.wookbook;
        this.sheet = imp.sheet;
        this.version = imp.version;
        this.clazz = imp.clazz;
        this.properties = imp.properties;
        this.headers = imp.headers;
        this.maxOperateSum = imp.maxUploadSum;
        this.dynamicProperty = imp.dynamicProperty;
        this.dynamicColumnParameters = imp.dynamicColumnParameters;
        this.fullPathName = imp.fullPathName;
    }

    /**
     * 构造导入Excel对象<br/> ExcelUtility.newImportBuilder(file,clazz,properties)
     *
     * @param file       导入的文件实体
     * @param clazz      实体class
     * @param properties 实体class对应的填充属性
     * @return
     * @throws IOException
     * @throws POIException
     */
    public static <T> Import<T> newImportBuilder(File file, Class<T> clazz, String[] properties)
            throws IOException,
            POIException {

        return new Import<T>(file, clazz, properties);
    }

    /**
     * 构造导入Excel对象<br/> ExcelUtility.newImportBuilder(file,clazz,properties)
     *
     * @param file       导入的文件实体
     * @param clazz      实体class
     * @param properties 实体class对应的填充属性
     * @return
     * @throws IOException
     * @throws POIException
     */
    public static <T> Import<T> newImportBuilder(File file, Class<T> clazz, String[] properties,
                                                 int sheetIndex) throws IOException, POIException {

        return new Import<T>(file, clazz, properties, sheetIndex);
    }

    /**
     * 构造导出Excel对象<br/> ExcelUtility.newExportBuilder(fullPathName,titles,properties, contentList)
     *
     * @param fullPathName 目标文件全路径
     * @param titles       导出的数据表头数组
     * @param properties   对应的对象属性数组
     * @param contentList  数据内容集合
     * @return
     * @throws POIException
     * @throws IOException
     */
    public static <T> Export<T> newExportBuilder(String fullPathName, String[] titles,
                                                 String[] properties,
                                                 List<T> contentList) throws POIException, IOException {

        return new Export<T>(fullPathName, titles, properties, contentList);
    }

    private static boolean isNotBlank(String str) {

        return !isBlank(str);
    }

    private static boolean isBlank(String str) {

        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取Excel文件数据
     *
     * @return
     * @throws POIException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public List<T> readExcel() throws POIException, InstantiationException, IllegalAccessException,
            ClassNotFoundException, SecurityException, NoSuchFieldException, NoSuchMethodException,
            IllegalArgumentException, InvocationTargetException, ParseException {

        List<T> ls = new ArrayList<T>();
        if (Tag.EXPORT == tag) {
            LOGGER.warn("Unsupport operation! please invoke [ExcelUtility.newImportBuilder()]");
            return ls;
        }

        Sheet sheet = wookbook.getSheetAt(this.sheet);
        if (sheet == null) {
            return ls;
        } else {
            for (int i = 1; i <= sheet.getLastRowNum() && i <= maxOperateSum; i++) {
                Row row = sheet.getRow(i);
                // if the row is empty, skip this row
                if (this.isEmptyRow(row)) {
                    continue;
                }
                T t = (T) Class.forName(clazz.getCanonicalName()).newInstance();
                int col = 0;
                for (String property : properties) {
                    Method method = getSetterMethod(t, property);
                    Class<?>[] typeClasses = method.getParameterTypes();
                    if (typeClasses.length == 1) {
                        Cell cell = row.getCell(col);
                        // setter 参数 207 method.invoke(t,
                        // this.getParams(typeClasses[0].getName(), cell));
                        if (LINE_NUM_NAME.equals(property)) {
                            method.invoke(t, row.getRowNum());
                        } else {
                            Object[] params = this.getParams(typeClasses[0].getName(), cell);
                            if (params != null && params.length > 0) {
                                method.invoke(t, params);
                            }
                        }
                    }
                    col++;
                }

                // 动态列数据生成
                if (isNotBlank(dynamicProperty)) {
                    Method method = getSetterMethod(t, dynamicProperty);
                    Class<?>[] typeClasses = method.getParameterTypes();
                    if (typeClasses.length == 1) {
                        if (!typeClasses[0].isAssignableFrom(Map.class)) {
                            LOGGER.error(String.format(
                                    "Dynamic property [%s] should implement interface java.util.Map",
                                    dynamicProperty));
                            throw new POIException("动态列属性[%s]必须是实现 java.util.Map 的子类。",
                                    dynamicProperty);
                        }

                        // 有序Map做动态列存储
                        Map<String, String> map = new LinkedHashMap<String, String>(headers.length,
                                1);
                        while (col < headers.length) {
                            map.put(getUserDefineKey(headers[col]),
                                    getRightCellValue(row.getCell(col)));
                            col++;
                        }

                        method.invoke(t, map);
                    }
                }

                ls.add(t);
            }
            return ls;
        }
    }

    /**
     * 集合数据写入Excel
     *
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws POIException
     */
    public File writeExcel() throws SecurityException, NoSuchFieldException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException,
            POIException {

        if (Tag.IMPORT == tag) {
            LOGGER.warn("Unsupport operation! please invoke [ExcelUtility.newExportBuilder()]");
            return null;
        }

        Workbook workbook = this.getWorkbook();
        Sheet sheet = workbook.createSheet();

        // 表头列
        Row header = sheet.createRow(0);
        if (this.headers != null) {
            String[] newHeaders = headers;
            // 动态列表头
            if (null != dynamicHeaders) {
                newHeaders = concat(headers, dynamicHeaders);
            }

            CellStyle headerStyle = createHeaderCellStyle(workbook);
            CellStyle requiredStyle = createRequiredCellStyle(workbook);
            for (int i = 0; i < newHeaders.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(newHeaders[i] != null ? newHeaders[i] : "");
                // 如果是必填列
                if (requiredColumns.contains(newHeaders[i])) {
                    cell.setCellStyle(requiredStyle);
                } else {
                    cell.setCellStyle(headerStyle);
                }
                // 列长度
                sheet.setColumnWidth(i, 15 * 256);
            }
        }

        if (this.contentList != null) {
            CellStyle bodyStyle = createBodyCellStyle(workbook);
            for (int i = 0; i < this.contentList.size(); i++) {

                if (maxOperateSum != -1 && i >= maxOperateSum) {// 超出限制
                    break;
                }

                Row content = sheet.createRow(i + 1);
                int col = 0;
                T t = this.contentList.get(i);
                for (String property : properties) {
                    Method method = getGetterMethod(t, property);
                    Object val = method.invoke(t);
                    Cell cell = content.createCell(col);
                    this.setCellValue(cell, val);
                    cell.setCellStyle(bodyStyle);
                    col++;
                }

                // 动态列数据生成
                if (isNotBlank(dynamicProperty)) {
                    Method method = getGetterMethod(t, dynamicProperty);
                    Object list = method.invoke(t);
                    if (list == null) {
                        LOGGER.error(String
                                .format("Row [%s], dynamic property [%s] is null.", i + 1,
                                        dynamicProperty));
                        continue;
                    }

                    if (list instanceof List) {
                        for (Object val : (List<?>) list) {
                            Cell cell = content.createCell(col);
                            this.setCellValue(cell, val);
                            cell.setCellStyle(bodyStyle);
                            col++;
                        }
                    } else {
                        LOGGER.error(String.format(
                                "Dynamic property [%s] should implement interface java.util.List.",
                                dynamicProperty));
                        throw new POIException("动态列属性[%s]必须是实现 java.util.List 的子类。",
                                dynamicProperty);
                    }
                }
            }
        }

        return workookWrite(workbook, this.fullPathName);// 写入
    }

    public File appendExcelColumn2AllSheet(String columnHeader,
                                           List<Map<Integer, String>> columnContentsList,
                                           String excelPrefix) throws FileNotFoundException, IOException {

        if (Tag.EXPORT == tag) {
            LOGGER.warn("Unsupport operation! please invoke [ExcelUtility.newImportBuilder()]");
            return null;
        }

        // 直接返回原文件
        if (CollectionUtil.isEmpty(columnContentsList)) {
            return new File(fullPathName);
        }
        Workbook wb = null;
        String suffix = ".xlsx";
        FileInputStream in = null;
        try {
            in = new FileInputStream(fullPathName);
            switch (version) {
                case EXCEL_2003:
                    suffix = ".xls";
                    wb = new HSSFWorkbook(in);
                    break;
                case EXCEL_2007:
                    suffix = ".xlsx";
                    wb = new XSSFWorkbook(in);
                    break;
                default: // never happen
                    break;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        for (int j = 0; j < columnContentsList.size(); j++) {
            Map<Integer, String> columnContents = columnContentsList.get(j);

            Sheet sheet = wb.getSheetAt(j);
            Row headerRow = sheet.getRow(0);
            headerRow = null != headerRow ? headerRow : sheet.createRow(0);
            // 最后一行写入
            int targetCellIndex = headerRow.getPhysicalNumberOfCells();
            sheet.setColumnWidth(targetCellIndex, 80 * 256);

            // 头
            Cell headerCell = headerRow.createCell(targetCellIndex);
            headerCell.setCellStyle(createAppendHeaderCellStyle(wb));
            headerCell.setCellValue(columnHeader);

            // 体

            Iterator<Map.Entry<Integer, String>> iterator = columnContents.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, String> entry = iterator.next();
                Integer rowKey = entry.getKey();
                String errInfo = entry.getValue();
                if (isNotBlank(errInfo)) {
                    Row row = sheet.getRow(rowKey);
                    if (row == null) {
                        row = sheet.createRow(rowKey);
                    }
                    Cell cell = row.createCell(targetCellIndex);
                    cell.setCellStyle(createAppendColumnCellStyle(wb));
                    cell.setCellValue(errInfo);
                }
            }

        }

        return workookWrite(wb, getAppendFileFullName(excelPrefix, suffix));// 写入
    }

    /**
     * 读取Excel操作后，追加一列信息到读取的数据尾列
     *
     * @param columnHeader   列头名称，自定义列头名称
     * @param columnContents 数据内容，K-V形式，K是列号，从1开始，V是列值。
     * @param excelPrefix    用于定义返回的文件名称前缀，例如：上传更新错误信息_xxxx.xls
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File appendExcelColumn(String columnHeader, Map<Integer, String> columnContents,
                                  String excelPrefix) throws FileNotFoundException, IOException {

        if (Tag.EXPORT == tag) {
            LOGGER.warn("Unsupport operation! please invoke [ExcelUtility.newImportBuilder()]");
            return null;
        }

        // 直接返回原文件
        if (null == columnContents || columnContents.isEmpty()) {
            return new File(fullPathName);
        }

        Workbook wb = null;
        String suffix = ".xlsx";
        FileInputStream in = null;
        try {
            in = new FileInputStream(fullPathName);
            switch (version) {
                case EXCEL_2003:
                    suffix = ".xls";
                    wb = new HSSFWorkbook(in);
                    break;
                case EXCEL_2007:
                    suffix = ".xlsx";
                    wb = new XSSFWorkbook(in);
                    break;
                default: // never happen
                    break;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        Sheet sheet = wb.getSheetAt(this.sheet);
        Row headerRow = sheet.getRow(0);
        headerRow = null != headerRow ? headerRow : sheet.createRow(0);
        // 最后一行写入
        int targetCellIndex = headerRow.getPhysicalNumberOfCells();
        sheet.setColumnWidth(targetCellIndex, 80 * 256);

        // 头
        Cell headerCell = headerRow.createCell(targetCellIndex);
        headerCell.setCellStyle(createAppendHeaderCellStyle(wb));
        headerCell.setCellValue(columnHeader);

        // 体
        //CellStyle如果在循环中创建数量超过4000会抛异常
        CellStyle cellStyle = createAppendColumnCellStyle(wb);
        for (int i = 1; sheet.getRow(i) != null; i++) {
            String val = columnContents.get(i);
            if (isNotBlank(val)) {
                Row row = sheet.getRow(i);
                Cell cell = row.createCell(targetCellIndex);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(val);
            }
        }

        return workookWrite(wb, getAppendFileFullName(excelPrefix, suffix));// 写入

    }

    /**
     * workbook写入
     *
     * @param workbook
     * @param path
     * @throws IOException
     */
    private File workookWrite(Workbook workbook, String path) throws IOException {

        File file = new File(path);

        // 写流
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

        return file;
    }

    /**
     * 合并两个数组
     *
     * @param first
     * @param second
     * @return
     */
    private <A> A[] concat(A[] first, A[] second) {

        A[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 普通表头样式
     *
     * @param workbook
     * @return
     */
    private CellStyle createHeaderCellStyle(Workbook workbook) {

        Font headFont = createFont(workbook, IndexedColors.BLACK.getIndex(),
                Font.BOLDWEIGHT_BOLD);// 创建字体
        return createCellStyle(workbook, headFont, CellStyle.ALIGN_CENTER);
    }

    /**
     * 必填项样式
     *
     * @param workbook
     * @return
     */
    private CellStyle createRequiredCellStyle(Workbook workbook) {

        CellStyle style = createHeaderCellStyle(workbook);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        return style;
    }

    /**
     * 普通表内容样式
     *
     * @param workbook
     * @return
     */
    private CellStyle createBodyCellStyle(Workbook workbook) {

        Font bodyFont = createFont(workbook, IndexedColors.BLACK.getIndex());// 创建字体
        return createCellStyle(workbook, bodyFont, CellStyle.ALIGN_LEFT);
    }

    /**
     * 追加列表头样式
     *
     * @param wb
     * @return
     */
    private CellStyle createAppendHeaderCellStyle(Workbook wb) {

        Font f = wb.createFont();
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle cs = wb.createCellStyle();
        cs.setFont(f);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        return cs;
    }

    /**
     * 追加列内容样式
     *
     * @param wb
     * @return
     */
    private CellStyle createAppendColumnCellStyle(Workbook wb) {

        Font f = wb.createFont();
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_NORMAL);

        CellStyle cs = wb.createCellStyle();
        cs.setFont(f);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        cs.setAlignment(CellStyle.ALIGN_LEFT);
        return cs;
    }

    private CellStyle createCellStyle(Workbook workbook, Font font, Short alignment) {

        CellStyle cs = workbook.createCellStyle();
        alignment = null == alignment ? CellStyle.ALIGN_LEFT : alignment;
        cs.setFont(font);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(alignment);
        return cs;
    }

    private Font createFont(Workbook wb, Short color, Short bold) {

        Font f = createFont(wb, color);
        if (null != bold) {
            f.setBoldweight(bold);
        }
        return f;
    }

    private Font createFont(Workbook wb, Short color) {

        Font f = wb.createFont();
        color = null == color ? IndexedColors.BLACK.getIndex() : color;
        f.setFontHeightInPoints((short) 10);// 创建第一种字体样式（用于列名）
        f.setColor(color);
        return f;
    }

    /**
     * 针对时间作特殊处理
     *
     * @param cell
     * @param val
     */
    private void setCellValue(Cell cell, Object val) {

        if (val instanceof Date) {
            cell.setCellValue(DateUtil.date2String((Date) val, DateUtil.FORMAT_1));
        } else {
            if (val == null) {
                cell.setCellValue(StringUtils.EMPTY);
            } else {
                cell.setCellValue(val.toString());
            }
        }
    }

    private Workbook getWorkbook() {

        switch (this.version) {
            case EXCEL_2003:
                return new HSSFWorkbook();
            case EXCEL_2007:
                return new XSSFWorkbook();
            default:
                // NEVER HAPPEN
                return null;
        }
    }

    /**
     * 获取用户自定义的列名<br/> 例如：Excel中列叫北京，存放仅对象的时候改成6
     *
     * @param key
     * @return
     */
    private String getUserDefineKey(String key) {

        if (null == dynamicColumnParameters) {
            return key;
        }

        if (null == dynamicColumnParameters.get(key)) {
            LOGGER.warn(
                    String.format("The key [%s] does not exist in dynamicColumnParameters(Map)", key));
            return key;
        }

        return dynamicColumnParameters.get(key).toString();

    }

    private Method getGetterMethod(T t, String property)
            throws SecurityException, NoSuchFieldException,
            NoSuchMethodException {

        Type type = t.getClass().getDeclaredField(property.trim()).getType();
        return t.getClass().getMethod(this.getPropertyMethodName(type, property.trim(), false));

    }

    private Method getSetterMethod(T t, String property)
            throws SecurityException, NoSuchFieldException,
            NoSuchMethodException {

        Type type = t.getClass().getDeclaredField(property).getGenericType();
        return t.getClass().getMethod(this.getPropertyMethodName(type, property, true),
                t.getClass().getDeclaredField(property).getType());
    }

    /**
     * 得到属性GET,SET方法
     *
     * @param type
     * @param propertyName
     * @return
     */
    private String getPropertyMethodName(Type type, String propertyName, boolean isSet) {

        StringBuilder sb = new StringBuilder();
        if (!isSet) {
            // 判断是否是布尔类型
            if ("boolean".equals(type.toString())) {
                if (propertyName.indexOf("is") > 0) {
                    sb.append("is");
                }
                sb.append(propertyName);
                return sb.toString();
            }

            sb.append("get");
        } else {

            if (propertyName.indexOf("is") == 0 && "boolean".equals(type.toString())) {
                propertyName = propertyName.substring(2);
            }

            sb.append("set");
        }

        sb.append(propertyName.replaceFirst(propertyName.substring(0, 1),
                propertyName.substring(0, 1).toUpperCase()));
        return sb.toString();
    }

    /**
     * 688 /** 判断是否是空行,empty rows，仅删除了内容，行依然存在
     *
     * @param row
     * @return
     */
    private boolean isEmptyRow(Row row) {

        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK && isNotBlank(
                    cell.toString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据参数类型,获得参数对象数组
     *
     * @param className
     * @param cell
     * @return
     * @throws POIException
     */
    private Object[] getParams(String className, Cell cell) throws ParseException {

        String cellValue = this.getRightCellValue(cell);

        if (className.equals("java.lang.String")) {
            return new Object[]{cellValue};
        } else if (className.equals("java.lang.Number")) {
            return new Object[]{NumberFormat.getInstance().parse(cellValue)};
        } else if (className.equals("char") || className.equals("java.lang.Character")) {
            return new Object[]{cellValue.toCharArray()[0]};
        } else if (className.equals("int") || className.equals("java.lang.Integer")) {
            return new Object[]{new Double(cellValue).intValue()};
        } else if (className.equals("byte") || className.equals("java.lang.Byte")) {
            return new Object[]{new Double(cellValue).byteValue()};
        } else if (className.equals("short") || className.equals("java.lang.Short")) {
            return new Object[]{new Double(cellValue).shortValue()};
        } else if (className.equals("float") || className.equals("java.lang.Float")) {
            return new Object[]{new Double(cellValue).shortValue()};
        } else if (className.equals("double") || className.equals("java.lang.Double")) {
            return new Object[]{new Double(cellValue)};
        } else if (className.equals("long") || className.equals("java.lang.Long")) {
            return new Object[]{new Double(cellValue).longValue()};
        } else if (className.equals("boolean") || className.equals("java.lang.Boolean")) {
            return new Object[]{Boolean.valueOf(cellValue)};
        } else if (className.equals("java.util.Date")) {
            return new Object[]{new Date(cell.getDateCellValue().getTime())};
        } else {
            return new Object[]{};
        }
    }

    /**
     * 处理特殊格式的数据，公式则保存公式统计的结果
     *
     * @param cell
     * @return
     */
    private String getRightCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }
        String cellValue;
        //解决读取的数字加了个.0的问题
        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        cellValue = cell.toString();
        cellValue = this.filterNonBreakSpace(cellValue);

        if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
            cellValue = String.valueOf(getFormulaResult(cell));
        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            //处理工具类中的 日期格式，日期格式会根据vo的属性类型改变 liuwenjie
            short format = cell.getCellStyle().getDataFormat();
            // 这几个数字表示的意思是excel里面日期的不同格式。
            if (format == 14 || format == 31 || format == 57
                    || format == 58 || format == 20 || format == 32) {
                Date date = cell.getDateCellValue();
                cellValue = DateUtil.date2String(date, DateUtil.FORMAT_1);
            }
        }
        return cellValue.trim();
    }

    /**
     * 过滤特殊空格 ‘\u00A0’, ‘\u2007’, ‘\u202F’
     *
     * @return
     */
    private String filterNonBreakSpace(String str) {
        str = str.replaceAll("\\u00A0", "");
        str = str.replaceAll("\\u2007", "");
        str = str.replaceAll("\\u202F", "");
        return str;
    }

    /**
     * 返回公式计算的结果
     *
     * @param cell
     * @return
     */
    private Object getFormulaResult(Cell cell) {

        FormulaEvaluator evaluator = wookbook.getCreationHelper().createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);

        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return cellValue.getBooleanValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cellValue.getNumberValue();
            case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_ERROR:
                break;

            // CELL_TYPE_FORMULA will never happen
            case Cell.CELL_TYPE_FORMULA:
                break;
        }

        return "";
    }

    private String getAppendFileFullName(String bizTag, String fileSuffix) {

        return fullPathName.substring(0, fullPathName.lastIndexOf('\\') + 1) + bizTag + "_"
                + DateUtil.date2String(new Date()) + fileSuffix;
    }

    public Excel getVersion() {

        return version;
    }

    public Class<T> getClazz() {

        return clazz;
    }

    public Workbook getWookbook() {

        return wookbook;
    }

    public String[] getHeaders() {

        return headers;
    }

    /**
     * 支持的类型
     *
     * @author hurong
     */
    public enum Excel {
        EXCEL_2003, EXCEL_2007
    }

    /**
     * 操作类型
     *
     * @author hurong
     */
    public enum Tag {
        IMPORT, EXPORT
    }

    /**
     * 导入Excel文件，内部构造器
     *
     * @param <T>
     * @author hurong
     */
    public static class Export<T> {

        private final Tag tag;
        private final String fullPathName;

        private final List<T> contents;
        /**
         * 组装对象属性列
         */
        private final String[] properties;
        /**
         * 表头列
         */
        private final String[] headers;
        /**
         * Excel版本
         */
        private final Excel version;
        /**
         * 最大写入限制，默认-1 不限制
         */
        private int maxExportNum = -1;
        /**
         * 此列是动态导出列
         */
        private String dynamicProperty;
        /**
         * 动态列导出表头
         */
        private String[] dynamicHeaders;
        /**
         * 必填项集合
         */
        private Set<String> requiredColumns = new HashSet<String>();
        ;

        /**
         * 导出
         *
         * @param fullPathName 全路径名称 path+fileName
         * @param columnTitles Excel头
         * @param properties   对应的实体属性
         * @param contentList  实体对象集合
         * @throws POIException Excel异常
         * @throws IOException
         */
        public Export(String fullPathName, String[] columnTitles, String[] properties,
                      List<T> contentList) throws POIException, IOException {

            if (fullPathName.endsWith(".xls")) {
                version = Excel.EXCEL_2003;
            } else if (fullPathName.endsWith(".xlsx")) {
                version = Excel.EXCEL_2007;
            } else {
                LOGGER.warn("Unsupported file type, only 'xls' or '.xlsx' allowed ");
                throw new POIException("不支持的文件后缀，仅支持导出.xls或.xlsx结尾的Excel文件。");
            }

            if (columnTitles.length != 0 && columnTitles.length != properties.length) {
                LOGGER.warn("titles &  properties length mismatch, please check");
                throw new POIException("参数错误，请检查titles和properties的长度是否匹配。");
            }
            this.tag = Tag.EXPORT;
            this.headers = columnTitles;
            this.properties = properties;
            this.contents = contentList;
            this.fullPathName = fullPathName;
            // 生成目录
            this.createPath();
        }

        /**
         * 设置必填项，链式编程，可设置多个
         *
         * @param columnTitle
         * @return
         */
        public Export<T> requiredColumn(String columnTitle) {

            if (isNotBlank(columnTitle)) {
                requiredColumns.add(columnTitle);
            }
            return this;
        }

        /**
         * 动态列，导出使用,针对T对象中某个属性是List，需要动态追加到最后的情况
         * <p>
         * <pre>
         *      T 中的此动态property必须java.util.List的子类
         *      正常情况下dynamicHeaders的长度和property的List长度是一致的。
         * </pre>
         *
         * @param property       动态列的属性名
         * @param dynamicHeaders 动态列的行头 not null
         * @return
         * @throws POIException
         */
        public Export<T> dynamicProperty4List(String property, String[] dynamicHeaders)
                throws POIException {

            if (isBlank(property)) {
                throw new POIException("property 不能为空");
            }

            if (null == dynamicHeaders || (null != dynamicHeaders && dynamicHeaders.length < 1)) {
                throw new POIException("dynamicHeaders 不能为空");
            }

            this.dynamicProperty = property.trim();
            this.dynamicHeaders = dynamicHeaders;

            return this;
        }

        /**
         * 最大导出数量限制(感觉并没有什么用，万一有人有这样的需求呢。)
         *
         * @param num
         * @return
         * @throws POIException
         */
        public Export<T> maxExportNum(int num) throws POIException {

            if (num <= 0) {
                throw new POIException("最大导出数量必须是个大于0的整数。");
            }

            this.maxExportNum = num;
            return this;
        }

        /**
         * 创建文件建构目录
         *
         * @throws IOException
         */
        private File createPath() throws IOException {

            File file = new File(this.fullPathName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            return file;
        }

        public ExcelUtility<T> build() {

            return new ExcelUtility<T>(this);
        }
    }

    /**
     * 导入Excel文件，内部构造器
     *
     * @param <T>
     * @author hurong
     */
    public static class Import<T> {

        private final Tag tag;
        /**
         * workbook
         */
        private final Workbook wookbook;
        /**
         * Excel版本
         */
        private final Excel version;
        /**
         * 对象class
         */
        private final Class<T> clazz;
        /**
         * 组装对象属性列
         */
        private final String[] properties;
        /**
         * 文件全路径
         */
        private final String fullPathName;
        /**
         * 表头列
         */
        private String[] headers;
        /**
         * 动态属性
         */
        private String dynamicProperty;
        /**
         * 动态属性KV形式的自定义关系，例如：K:北京,V：6；代表将 T 中dynamicProperty属性的Key(北京)替换成用户自定义的值6
         */
        private Map<String, Object> dynamicColumnParameters;
        /**
         * 第几个sheet
         */
        private int sheet = 0;
        /**
         * 最大上传限制，默认1000
         */
        private int maxUploadSum = 1000;

        public Import(File file, Class<T> clazz, String[] properties)
                throws IOException, POIException {

            if (!FileUtil.isExists(file)) {
                LOGGER.warn("File not exists");
                throw new POIException("对不起，上传的文件不存在");
            }

            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                if (file.getName().endsWith(".xls")) {
                    version = Excel.EXCEL_2003;
                    this.wookbook = new HSSFWorkbook(in);
                } else if (file.getName().endsWith(".xlsx")) {
                    version = Excel.EXCEL_2007;
                    this.wookbook = new XSSFWorkbook(in);
                } else {
                    LOGGER.warn("Unsupported file type, only 'xls' or '.xlsx' allowed ");
                    throw new POIException("不支持的文件后缀，仅支持上传.xls或.xlsx结尾的Excel文件。");
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }

            this.tag = Tag.IMPORT;
            this.fullPathName = file.getCanonicalPath();
            // 实际文件表头
            this.headers = this.getColumnNameList().toArray(new String[0]);
            // 初始化各成员变量
            this.clazz = clazz;
            this.properties = properties;
        }

        public Import(File file, Class<T> clazz, String[] properties, int sheetIndex)
                throws IOException, POIException {

            this(file, clazz, properties);
            this.sheet = sheetIndex;
            this.headers = this.getColumnNameList().toArray(new String[0]);
        }

        /**
         * 动态属性列
         * <p>
         * <p>
         * 将动态属性列Map的Key按照parameters格式进行替换
         * </p>
         *
         * @return
         * @throws POIException
         */
        public Import<T> dynamicColumnParameters(Map<String, Object> parameters)
                throws POIException {

            if (null == parameters) {
                LOGGER.warn("Input parameters map is null.");
                throw new POIException("动态属性 parameters 不能为空");
            }

            this.dynamicColumnParameters = parameters;

            return this;
        }

        /**
         * 动态属性列
         * <p>
         * <p>
         * 动态属性列请使用Map结构的数据存储，最终的结果将会存储在 java.util.LinkedHashMap<String,String>的有序Map结构中
         * </p>
         *
         * @param property
         * @return
         * @throws POIException
         */
        public Import<T> dynamicProperty(String property) throws POIException {

            if (isBlank(property)) {
                throw new POIException("property 不能为空");
            }

            this.dynamicProperty = property.trim();

            return this;
        }

        /**
         * 修改sheet号,从0开始
         *
         * @param sheetNo
         * @return
         * @throws POIException
         */
        public Import<T> sheet(int sheetNo) throws POIException {

            if (sheetNo < 0) {
                throw new POIException("sheetNo必须不小0");
            }

            if (sheetNo > this.wookbook.getNumberOfSheets() - 1) {
                LOGGER.warn("Out of the number of sheets! check the sheet parameter" + sheet);
                throw new POIException("Excel错误，文件中第[%s]个sheet文件不存在。", sheetNo);
            }
            this.sheet = sheetNo;

            return this;
        }

        /**
         * 最大上传数量，默认1000
         *
         * @param num
         * @return
         * @throws POIException
         */
        public Import<T> maxUploadSum(int num) throws POIException {

            if (num <= 0) {
                throw new POIException("最大上传数量必须是个大于0的整数。");
            }

            this.maxUploadSum = num;
            return this;
        }

        /**
         * 检测表头是否一致
         *
         * @return
         * @throws POIException
         */
        public Import<T> headers(String[] expectedHeaders) throws POIException {

            if (!Arrays.equals(headers, expectedHeaders)) {
                LOGGER.warn(String.format("Excel header mismatch, actual:%s , and expected: %s",
                        Arrays.toString(headers), Arrays.toString(expectedHeaders)));
                throw new POIException("不支持的Excel头栏，请上传指定模板的Excel");
            }
            return this;
        }

        public ExcelUtility<T> build() {

            return new ExcelUtility<T>(this);
        }

        /**
         * 得到sheet中第一行的值，存在List中
         *
         * @return
         * @throws POIException
         */
        private List<String> getColumnNameList() {

            List<String> ls = new ArrayList<String>();
            if (wookbook.getSheetAt(this.sheet) == null) {
                return ls;
            }

            if (wookbook.getSheetAt(this.sheet).getRow(0) == null) {
                return ls;
            }

            Sheet sheet = wookbook.getSheetAt(this.sheet);
            Row row = sheet.getRow(0);// 第一行
            for (int j = 0; row.getCell(j) != null && isNotBlank(row.getCell(j).toString()); j++) {
                Cell cell = row.getCell(j);
                ls.add(cell.toString().trim());
            }
            return ls;
        }

    }

}
