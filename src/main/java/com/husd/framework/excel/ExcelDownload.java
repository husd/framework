package com.husd.framework.excel;

import org.apache.commons.lang3.StringUtils;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author hushengdong
 */
public class ExcelDownload {

    public static void download(HttpServletRequest request, HttpServletResponse response,
                                File file) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setContentType("application/octet-stream");
            String userAgent = request.getHeader("User-Agent");

            String filePath = file.getAbsolutePath();
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            String rtn = encodeByBrowseType(userAgent, fileName);
            response.setHeader("Content-disposition", "attachment; " + rtn);
            response.setHeader("Content-Length", String.valueOf(file.length()));

            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            // ignore this exception
        } finally {
            try {
                bis.close();
            } catch (IOException e1) {
            }
            try {
                bos.close();
            } catch (IOException e1) {
            }
            file.delete();
        }
    }

    private static String encodeByBrowseType(String userAgent, String fileName)
            throws UnsupportedEncodingException {

        String newName = URLEncoder.encode(fileName, "UTF-8");
        // 默认按IE的方式来编码
        if (StringUtils.isBlank(userAgent)) {
            return "filename=\"" + newName + "\"";
        }
        String rtn = "";
        userAgent = userAgent.toLowerCase();
        // IE浏览器
        if (userAgent.indexOf("msie") != -1) {
            rtn = "filename=\"" + newName + "\"";
        }
        // Opera浏览器只能采用filename*
        else if (userAgent.indexOf("opera") != -1) {
            rtn = "filename*=UTF-8''" + newName;
        }
        // Safari浏览器，只能采用ISO编码的中文输出
        else if (userAgent.indexOf("safari") != -1) {
            rtn = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
        }
        // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
        else if (userAgent.indexOf("applewebkit") != -1) {
            newName = MimeUtility.encodeText(fileName, "UTF8", "B");
            rtn = "filename=\"" + newName + "\"";
        }
        // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
        else if (userAgent.indexOf("mozilla") != -1) {
            rtn = "filename*=UTF-8''" + newName;
        }
        return rtn;
    }

//    List<Object[]> data = new ArrayList<Object[]>();
//        data.add(Supply2ProcureConstant.EXCEL_TIP_TITLE_S);
//        data.add(Supply2ProcureConstant.EXCEL_TITLE_S);
//    int columnNum = data.get(0).length;
//    boolean status = ExcelUtil.createSupply2PureTemplate(data, targetFile,
//            Supply2ProcureConstant.REQUIRED_CELL_INDEX_S, Supply2ProcureConstant.DC_NARROW_WIDTH, 3, columnNum);
//    String info = status ? "生成excel模板成功" : "生成excel模板失败";
//        return new BooleanMessage(status, info);
//
//    String value = null;
//    int cellType = cell.getCellType();
//        switch (cellType) {
//        case Cell.CELL_TYPE_NUMERIC:
//            value = String.valueOf(cell.getNumericCellValue());
//            break;
//        case Cell.CELL_TYPE_STRING:
//            value = cell.getStringCellValue().trim();
//            break;
//        case Cell.CELL_TYPE_FORMULA:
//            switch (cell.getCachedFormulaResultType()) {
//                case Cell.CELL_TYPE_NUMERIC:
//                    value = String.valueOf(cell.getNumericCellValue());
//                    break;
//                case Cell.CELL_TYPE_STRING:
//                    value = cell.getStringCellValue().trim();
//                    break;
//            }
//            break;
//        case Cell.CELL_TYPE_BLANK:
//            value = StringUtils.EMPTY;
//            break;
//        case Cell.CELL_TYPE_BOOLEAN:
//            value = String.valueOf(cell.getBooleanCellValue());
//            break;
//        case Cell.CELL_TYPE_ERROR:
//            value = String.valueOf(cell.getErrorCellValue());
//            break;
//        default:
//            value = cell.getStringCellValue().trim();
//            break;
//    }
//        return StringUtils.strip(value, "\u00A0");
}
