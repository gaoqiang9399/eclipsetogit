package app.component.thirdservice.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.Font;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfUtil {
    private static Logger logger =LoggerFactory.getLogger(PdfUtil.class);

    public static boolean createHtmlPdf(String html, String fileName, String path,String fontPath) {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        OutputStream os=null;
        try {
            //创建新的pdf文件
            os = new FileOutputStream(path+File.separator+File.separator+fileName+".pdf");
            os.flush();
            os.close();
            //给已创建的pdf文件附html内容
            String outputFile = path+File.separator+File.separator+fileName+".pdf";
            Document document = new Document();
            PdfWriter writer = null;
            writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
            document.open();
            //追加样式
           // String cssPath=path+File.separator+"css"+File.separator+"htmltopdf.css";
           // String cssSource = getURLSource(new File(cssPath));
            //追加字体
            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(fontPath+"font");
            System.out.print("XMLWorkerFontProvider字体生成："+fontProvider);
            //追加html内容
            InputStream bis = new ByteArrayInputStream(html.toString().getBytes());
           // InputStream cssis = new ByteArrayInputStream(cssSource.toString().getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, bis,Charset.forName("UTF-8"),fontProvider);

            document.close();
        } catch (Exception e) {
            logger.error("生成pdf出错", e);
        }finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("生成pdf关闭输出列出错", e);
                }
            }
        }

        return false;

    }
    public static Font getPdfChineseFont(String fontPath) throws Exception {
        BaseFont bfChinese = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
        return fontChinese;
    }
    /**
     * 通过网站域名URL获取该网站的源码
     *
     * @param url
     * @return String
     * @throws Exception
     */
    public static String getURLSource(File url) throws Exception
    {
        InputStream inStream = new FileInputStream(url);
        // 通过输入流获取html二进制数据
        byte [] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
        String htmlSource = new String(data);

        inStream.close();
        return htmlSource;
    }

    /**
     * 把二进制流转化为byte字节数组
     *
     * @param instream
     * @return byte[]
     * @throws Exception
     */
    public static byte [] readInputStream(InputStream instream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte [] buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        instream.close();
        return outStream.toByteArray();
    }

    public static String readTxtFile(String filePath){
        String result = "";
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(lineTxt);
                    result = result + "\n" +lineTxt;
                }
            }else{
                logger.error("找不到指定的文件");
            }
        } catch (Exception e) {
            logger.error("读取文件内容出错", e);
        } finally {
            try {
                if (read != null)
                {
                    read.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return result;
    }
    public static void createReportPdf(String serialno,String reportType,String rootPath,String html){
        if(serialno!=null){
            //css
            String cssPath=rootPath+File.separator+"css"+File.separator+"pdf"+File.separator+"report.css";
            //字体
            String fontPath=rootPath+File.separator+"font"+File.separator+"SIMSUN.TTC";
            //包装
            String htmlTemp="<div class=\"container\"><div class=\"ik-main\">"+html+"</div></div>";
            //去掉表单生成器生成的错误空格
            htmlTemp=htmlTemp.replace("&nbsp;", "");
            htmlTemp=htmlTemp.replace("&nbsp", "");
            //去掉打印
            htmlTemp=htmlTemp.replace("<a class='print-res' href='javascript:void(0);' onclick='printResult(this)'>打印结果</a>", "");
            htmlTemp=htmlTemp.replace("<a class='print-res' target='_blank'  href='/servicemanage/history/showpdf?serialno="+reportType+serialno+"'>打印结果</a>", "");
            //包装成XHTML
            // String content=PDFUse.htmlJoint(htmlTemp, PDFUse.readTxtFile(cssPath));
            //生成pdf
            String fileName=null;
            if(StringUtils.isEmpty(reportType)){
                fileName=serialno;
            }else{
                fileName=reportType+serialno;
            }
            String dir=rootPath+File.separator+"pdf"+File.separator+"report";
            // PDFUse.createHtmlPdf(content, fileName, dir, fontPath);
        }

    }
    public static void createAntiFraudPdf(String serialno,String reportType,String rootPath,String html){
        if(serialno!=null){
            //css
            String cssPath=rootPath+File.separator+"css"+File.separator+"pdf"+File.separator+"antifraud.css";
            //字体
            String fontPath=rootPath+File.separator+"font"+File.separator+"SIMSUN.TTC";
            //包装
            //去掉表单生成器生成的错误空格
            html=html.replace("&nbsp;", "");
            html=html.replace("&nbsp", "");
            //去掉打印
            html=html.replace("<a class='print-res' href='javascript:void(0);' onclick='printResult(this)'>打印结果</a>", "");
            html=html.replace("<a class='print-res' target='_blank'  href='/servicemanage/history/showpdf?serialno="+reportType+serialno+"'>打印结果</a>", "");
            //包装成XHTML
            //  String content= PDFUse.htmlJoint(html, PDFUse.readTxtFile(cssPath));
            //生成pdf
            String fileName=null;
            if(StringUtils.isEmpty(reportType)){
                fileName=serialno;
            }else{
                fileName=reportType+serialno;
            }
            String dir=rootPath+File.separator+"pdf"+File.separator+"report";
            // PDFUse.createHtmlPdf(content, fileName, dir, fontPath);
        }

    }

    public static void createAuthCodePdf(String html,String authCode,String rootPath) throws DocumentException, IOException{
        //追加样式
        String cssPath = "";
        if("showpdfttyy".equals(authCode)){
            cssPath=rootPath+File.separator+"css"+File.separator+"MfCus_RisManagemenTDHtml.css";
            authCode = authCode.substring(0,authCode.length()-4);
        }else{
            cssPath=rootPath+File.separator+"css"+File.separator+"htmltopdf.css";
        }
        //包装成XHTML
        String content=PdfUtil.htmlJoint(html,PdfUtil.readTxtFile(cssPath));
        //字体
        String fontPath=rootPath+File.separator;
        //生成pdf
        PdfUtil.createHtmlPdf(content, authCode, rootPath,fontPath);
    }

    public static void createAuthCodebeePdf(String html,String authCode,String rootPath) throws DocumentException, IOException{
        //追加样式
        String cssPath=rootPath+File.separator+"css"+File.separator+"beehtml.css";
        //包装成XHTML
        String content=PdfUtil.htmlJoint(html,PdfUtil.readTxtFile(cssPath));
        //字体
        String fontPath=rootPath+File.separator;
        //生成pdf
        PdfUtil.createHtmlPdf(content, authCode, rootPath,fontPath);
    }
    public static String htmlJoint(String content,String css){
        StringBuffer html = new StringBuffer();
        //组装成符合W3C标准的html文件，否则不能正确解析
        html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n")  ;
        html.append("<head>\n")  ;
        html.append("<title></title>\n")  ;
        html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" >\n");
       if(StringUtils.isNotEmpty(css)){
           html.append("<style type=\"text/css\">");
           html.append(css);
           html.append("</style>\n");
        }
        html.append("</meta>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append(content);
        html.append("\n");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
    public static void ziti(String fileName,String path,String url,String fontPath) throws DocumentException, IOException
    {
        Document document = new Document();
        OutputStream os = new FileOutputStream(new File(path+"component"+File.separator +"thirdservice"+File.separator +"juxinli"+File.separator +"ftl"+File.separator+fileName+".pdf"));
        PdfWriter.getInstance(document,os);
        document.open();
        //方法一：使用Windows系统字体(TrueType)
        BaseFont baseFont = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

        //方法二：使用iTextAsian.jar中的字体
        //BaseFont baseFont = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

        //方法三：使用资源字体(ClassPath)
        ////BaseFont baseFont = BaseFont.createFont("/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

        Font font = new Font(baseFont);
        document.add(new Paragraph(url,font));
        document.close();
    }
}
