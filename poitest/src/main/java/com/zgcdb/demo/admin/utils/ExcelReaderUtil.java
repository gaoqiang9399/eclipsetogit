package com.zgcdb.demo.admin.utils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Auther: hwj
 * @ Date: 2019/8/8 16:52
 * @ Description: ��ȡexcel������
 */
public class ExcelReaderUtil {

    /**
     * @methodName: readExcel ��ȡexcel������
     * @param: [is, tClass]  �����ʵ����,��Ա��������ֻ���ǻ������ͺ��ַ���
     * @return: java.util.List<T>
     * @auther: ��ǿ
     * @date: 2020/11/23 11:24
     * @Description: ��ȡexcel�ļ�,����ת��Ϊjavabean
     */
    public static <T> List<T> readExcel2Bean(InputStream is, Class<T> tClass)
            throws IOException, IllegalAccessException, InstantiationException {
        List<List<String>> list = ExcelReaderUtil.readExcel(is);
        //-----------------------�������ݵ�ʵ�弯�Ͽ�ʼ-----------------------------------
        List<T> listBean = new ArrayList<T>();
        Field[] fields = tClass.getDeclaredFields();
        T uBean = null;
        for (int i = 1; i < list.size(); i++) {// i=1����Ϊ��һ�в�Ҫ
            uBean = (T) tClass.newInstance();
            List<String> listStr = list.get(i);
            for (int j = 0; j < listStr.size(); j++) {
                if (j>=fields.length){
                    break;
                }
                Field field = fields[j];
                String datastring = listStr.get(j);
                field.setAccessible(true);
                if (datastring.length()>0&&datastring!=null) {
                    Class<?> type = field.getType();
                    // ֻ֧��8�л������ͺ�String���� ������������ ���������
                    if (type==String.class){
                        field.set(uBean,datastring);
                    }else  if(type==Integer.class||type==int.class){
                        field.set(uBean,Integer.parseInt(datastring));
                    }else  if(type==Double.class||type==double.class){
                        field.set(uBean,Double.parseDouble(datastring));
                    } else  if(type==Float.class||type==float.class){
                        field.set(uBean,Float.parseFloat(datastring));
                    } else  if(type==Long.class||type==long.class){
                        field.set(uBean,Long.parseLong(datastring));
                    }else if (type==Boolean.class||type==boolean.class){
                        field.set(uBean,Boolean.parseBoolean(datastring));
                    }else if (type==Short.class||type==short.class){
                        field.set(uBean,Short.parseShort(datastring));
                    }else if (type==Byte.class||type==byte.class){
                        field.set(uBean,Byte.parseByte(datastring));
                    }else if (type==Character.class ||type==char.class){
                        field.set(uBean,datastring.charAt(0));
                    }
                }
            }
            listBean.add(uBean);
        }
        return listBean;
    }

    /**
     * Excel��ȡ ����,��������
     */
    private static List<List<String>> readExcel(InputStream is)
            throws IOException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** �õ���һ��sheet */
        Sheet sheet = wb.getSheetAt(0);
        /** �õ�Excel������ */
        int totalRows = sheet.getPhysicalNumberOfRows();
        /** �õ�Excel������ */
        int totalCells = 0;
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** ѭ��Excel���� */
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            List<String> rowLst = new ArrayList<String>();
            /** ѭ��Excel���� */
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    HSSFDataFormatter hSSFDataFormatter = new HSSFDataFormatter();
                    cellValue = hSSFDataFormatter.formatCellValue(cell);
                }
                rowLst.add(cellValue);
            }
            /** �����r�еĵ�c�� */
            dataLst.add(rowLst);
        }
        return dataLst;
    }
}