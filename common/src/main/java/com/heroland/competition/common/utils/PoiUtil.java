package com.heroland.competition.common.utils;

import com.heroland.competition.common.constant.DataQualityConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

/**
 * Excle表处理工具
 *
 * @author Eason@bianque
 * @date 2018/11/13
 **/
public class PoiUtil {

    public static Workbook getWorkbook(InputStream in, String name) throws Exception {
        Workbook workbook = null;
        /*Excel 2003*/
        if (StringUtils.endsWith(name, DataQualityConstants.EXCEL_XLS)) {
            workbook = new HSSFWorkbook(in);
        }
        /*Excel 2007/2010*/
        if (StringUtils.endsWith(name, DataQualityConstants.EXCEL_XLSX)) {
            workbook = new XSSFWorkbook(in);
        }
        return workbook;
    }

    public static Object getValue(Cell cell) {
        Object obj = null;
        if (cell == null) {
            return null;
        }
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    public static XSSFWorkbook getXSSFWorkbook(String sheetName, int colunms) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        /*创建一个sheet页*/
        XSSFSheet sheet = workbook.createSheet(sheetName);
        /*设计样式*/
        XSSFCellStyle cellStyle = getWorkbooStyle(workbook);

        /*设置列的宽度*/
        for (int index = 0; index < colunms; index++) {
            sheet.setColumnWidth(index, DataQualityConstants.COLUMN_WIDTH_30PX);
        }

        return workbook;
    }

    public static XSSFCellStyle getWorkbooStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        /*水平居中*/
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFFont font = workbook.createFont();
        /*设置字体名称*/
        font.setFontName(DataQualityConstants.FONT_NAME);
        /*设置字号*/
        font.setFontHeightInPoints((short) 28);
        /*设置是否为斜体*/
        font.setItalic(false);
        /*设置是否加粗*/
        font.setBold(true);
        /*设置字体颜色*/
        font.setColor(IndexedColors.RED.index);

        cellStyle.setFont(font);
        return cellStyle;
    }
}
