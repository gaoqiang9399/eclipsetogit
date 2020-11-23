package cn.mftcc.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创始人 shenhaobing
 * @创建时间 2019/8/14
 * @描述
 */
public class CopySheetUtil {

    public static void copySheetToExcelForXlsx(String fromPath,String toPath,String excelName)
            throws  IOException{
        // 创建新的excel
        XSSFWorkbook xbCreat = null;
        InputStream in = null;
        XSSFWorkbook xb = null;
        try {
            xbCreat = new XSSFWorkbook();
            File file = new File(fromPath);
            for (File excel : file.listFiles()) {
                // 打开已有的excel
                String fileName = excel.getName();
                String suffix = fileName.split("\\.")[1];
                String strExcelPath = fromPath + "\\" + excel.getName();
                in = new FileInputStream(strExcelPath);
                if (("xlsx").equals(suffix)){
                    xb = new XSSFWorkbook(in);
                    for (int ii = 0; ii < xb.getNumberOfSheets(); ii++) {
                        XSSFSheet sheet = xb.getSheetAt(ii);
                        XSSFSheet sheetCreat = xbCreat.createSheet(sheet.getSheetName());
                        // 复制源表中的合并单元格
                        MergerRegionForXlsx(sheetCreat, sheet);
                        int firstRow = sheet.getFirstRowNum();
                        int lastRow = sheet.getLastRowNum();
                        for (int i = firstRow; i <= lastRow; i++) {
                            // 创建新建excel Sheet的行
                            XSSFRow rowCreat = sheetCreat.createRow(i);
                            // 取得源有excel Sheet的行
                            XSSFRow row = sheet.getRow(i);
                            if (row!=null){
                                // 单元格式样
                                int firstCell = row.getFirstCellNum();
                                int lastCell = row.getLastCellNum();
                                for (int j = firstCell; j < lastCell; j++) {
                                    // 自动适应列宽 貌似不起作用
                                    //sheetCreat.autoSizeColumn(j);
                                    System.out.println(row.getCell(j));
                                    rowCreat.createCell(j);
                                    String strVal = "";
                                    if (row.getCell(j) == null) {

                                    } else {
                                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                                        strVal = removeInternalBlank(row.getCell(j).getStringCellValue());
                                    }
                                    System.out.println(strVal);
                                    rowCreat.getCell(j).setCellValue(strVal);
                                }
                            }

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if (xb !=null){
                xb.close();
            }
            if (xbCreat !=null){
                xbCreat.close();
            }
            if (in !=null){
                in.close();
            }
        }

        FileOutputStream fileOut = new FileOutputStream(toPath + excelName + ".xlsx");
        xbCreat.write(fileOut);
        fileOut.close();
    }

    public static String copySheetToExcelForXls(String fromPath,String toPath,String excelName)
            throws  IOException{
        // 创建新的excel
        HSSFWorkbook wbCreat = null;
        InputStream in = null;
        HSSFWorkbook wb = null;
        try{
            wbCreat = new HSSFWorkbook();
            File file = new File(fromPath);
            for (File excel : file.listFiles()) {
                // 打开已有的excel
                String fileName = excel.getName();
                String suffix = fileName.split("\\.")[1];
                String strExcelPath = fromPath + excel.getName();
                in = new FileInputStream(strExcelPath);
                if (("xls").equals(suffix)){
                    wb = new HSSFWorkbook(in);
                    for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
                        HSSFSheet sheet = wb.getSheetAt(ii);
                        HSSFSheet sheetCreat = wbCreat.createSheet(sheet.getSheetName());
                        // 复制源表中的合并单元格
                        MergerRegion(sheetCreat, sheet);
                        int firstRow = sheet.getFirstRowNum();
                        int lastRow = sheet.getLastRowNum();
                        for (int i = firstRow; i <= lastRow; i++) {
                            // 创建新建excel Sheet的行
                            HSSFRow rowCreat = sheetCreat.createRow(i);
                            // 取得源有excel Sheet的行
                            HSSFRow row = sheet.getRow(i);
                            // 单元格式样
                            int firstCell = row.getFirstCellNum();
                            int lastCell = row.getLastCellNum();
                            for (int j = firstCell; j < lastCell; j++) {
                                // 自动适应列宽 貌似不起作用
                                //sheetCreat.autoSizeColumn(j);
                                System.out.println(row.getCell(j));
                                rowCreat.createCell(j);
                                String strVal = "";
                                if (row.getCell(j) == null) {

                                } else {
                                    strVal = removeInternalBlank(row.getCell(j).getStringCellValue());
                                }
                                rowCreat.getCell(j).setCellValue(strVal);
                            }
                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            if (wb != null){
                wb.close();
            }
            if (in != null){
                in.close();
            }
            if (wbCreat != null){
                wbCreat.close();
            }
        }


        FileOutputStream fileOut = new FileOutputStream(toPath + excelName + ".xls");
        wbCreat.write(fileOut);
        fileOut.close();
        return toPath + excelName + ".xls";
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        String fromPath = "D:\\test\\import";// excel存放路径
//        String toPath = "D:\\test\\import1\\";// 保存新EXCEL路径
//        // 新的excel 文件名
//        String excelName = "节目访问量";
//        copySheetToExcelForXlsx(fromPath,toPath,excelName);
        /*// 创建新的excel
        HSSFWorkbook wbCreat = new HSSFWorkbook();
        XSSFWorkbook xbCreat = new XSSFWorkbook();
        File file = new File(fromPath);
        for (File excel : file.listFiles()) {
            // 打开已有的excel
            String fileName = excel.getName();
            String suffix = fileName.split("\\.")[1];
            String strExcelPath = fromPath + "\\" + excel.getName();
            InputStream in = new FileInputStream(strExcelPath);
            if (("xlsx").equals(suffix)){
                XSSFWorkbook xb = new XSSFWorkbook(in);
                for (int ii = 0; ii < xb.getNumberOfSheets(); ii++) {
                    XSSFSheet sheet = xb.getSheetAt(ii);
                    XSSFSheet sheetCreat = xbCreat.createSheet(sheet.getSheetName());
                    // 复制源表中的合并单元格
                    MergerRegionForXlsx(sheetCreat, sheet);
                    int firstRow = sheet.getFirstRowNum();
                    int lastRow = sheet.getLastRowNum();
                    for (int i = firstRow; i <= lastRow; i++) {
                        // 创建新建excel Sheet的行
                        XSSFRow rowCreat = sheetCreat.createRow(i);
                        // 取得源有excel Sheet的行
                        XSSFRow row = sheet.getRow(i);
                        if (row!=null){
                            // 单元格式样
                            int firstCell = row.getFirstCellNum();
                            int lastCell = row.getLastCellNum();
                            for (int j = firstCell; j < lastCell; j++) {
                                // 自动适应列宽 貌似不起作用
                                //sheetCreat.autoSizeColumn(j);
                                System.out.println(row.getCell(j));
                                rowCreat.createCell(j);
                                String strVal = "";
                                if (row.getCell(j) == null) {

                                } else {
                                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                                    strVal = removeInternalBlank(row.getCell(j).getStringCellValue());
                                }
                                System.out.println(strVal);
                                rowCreat.getCell(j).setCellValue(strVal);
                            }
                        }

                    }
                }
            }else if (("xls").equals(suffix)){
                HSSFWorkbook wb = new HSSFWorkbook(in);
                for (int ii = 0; ii < wb.getNumberOfSheets(); ii++) {
                    HSSFSheet sheet = wb.getSheetAt(ii);
                    HSSFSheet sheetCreat = wbCreat.createSheet(sheet.getSheetName());
                    // 复制源表中的合并单元格
                    MergerRegion(sheetCreat, sheet);
                    int firstRow = sheet.getFirstRowNum();
                    int lastRow = sheet.getLastRowNum();
                    for (int i = firstRow; i <= lastRow; i++) {
                        // 创建新建excel Sheet的行
                        HSSFRow rowCreat = sheetCreat.createRow(i);
                        // 取得源有excel Sheet的行
                        HSSFRow row = sheet.getRow(i);
                        // 单元格式样
                        int firstCell = row.getFirstCellNum();
                        int lastCell = row.getLastCellNum();
                        for (int j = firstCell; j < lastCell; j++) {
                            // 自动适应列宽 貌似不起作用
                            //sheetCreat.autoSizeColumn(j);
                            System.out.println(row.getCell(j));
                            rowCreat.createCell(j);
                            String strVal = "";
                            if (row.getCell(j) == null) {

                            } else {
                                strVal = removeInternalBlank(row.getCell(j).getStringCellValue());
                            }
                            rowCreat.getCell(j).setCellValue(strVal);
                        }
                    }
                }
                FileOutputStream fileOut = new FileOutputStream(toPath + excelName + ".xls");
                wbCreat.write(fileOut);
                fileOut.close();
            }
        }
        FileOutputStream fileOut = new FileOutputStream(toPath + excelName + ".xlsx");
        xbCreat.write(fileOut);
        fileOut.close();*/
//    }

    /**
     * 复制原有sheet的合并单元格到新创建的sheet
     *
     * @param sheetCreat 新创建sheet
     * @param sheet      原有的sheet
     */
    private static void MergerRegion(HSSFSheet sheetCreat, HSSFSheet sheet) {
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            Region mergedRegionAt = sheet.getMergedRegionAt(i);
            sheetCreat.addMergedRegion(mergedRegionAt);
        }

    }

    private static void MergerRegionForXlsx(XSSFSheet sheetCreat, XSSFSheet sheet) {
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress mergedRegionAt = sheet.getMergedRegion(i);
            sheetCreat.addMergedRegion(mergedRegionAt);
        }

    }

    /**
     * 去除字符串内部空格
     */
    public static String removeInternalBlank(String s) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        char str[] = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (str[i] == ' ') {
                sb.append(' ');
            } else {
                break;
            }
        }
        String after = m.replaceAll("");
        return sb.toString() + after;
    }

}
