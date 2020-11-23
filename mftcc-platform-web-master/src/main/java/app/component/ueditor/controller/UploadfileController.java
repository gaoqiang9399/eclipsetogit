package app.component.ueditor.controller;


import app.component.ueditor.api.Constant;
import app.component.ueditor.api.Response;
import app.component.ueditor.api.ResponseEnum;
import app.component.ueditor.entity.UploadfileVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by zhiang.feng on 2018/6/18.
 */
@Configuration
@RestController
@RequestMapping("/credit/uploadfile")
@Slf4j
public class UploadfileController {
    @Value("${mftcc.upload.uploadFilePath}")
    private String url;

    private Logger logger = LoggerFactory.getLogger(UploadfileController.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadfileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    //private static final String url ="http://cdn.crm.4in1fintech.com";


    //private static final String url ="./";
    //private static final String URL ="/usr/local/file/";
    //private static final String url ="E:/img_test/";
    private static final long MAX_FILE_SISE = 1000000;

/*    @RequestMapping("/upload")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        UploadfileVo uploadfileVo =new UploadfileVo();
        BufferedOutputStream bout = null;
        if(!file.isEmpty()) {
            //获取文件类型
            String contentType = file.getContentType();
            if(!contentType.equals("")) {
                //可以对文件类型进行检查
            }
            //获取input域的name属性
            String name = file.getName();
            //获取文件名，带扩展名
            String originFileName = file.getOriginalFilename();
            //获取文件扩展名
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            System.out.println(extension);
            //获取文件大小，单位字节
            long site = file.getSize();
            if(site > MAX_FILE_SISE) {
                //可以对文件大小进行检查
            }

            //构造文件上传后的文件绝对路径，这里取系统时间戳＋文件名作为文件名
            //不推荐这么写，这里只是举例子，这么写会有并发问题
            //应该采用一定的算法生成独一无二的的文件名
            String urlstr = String.valueOf(System.currentTimeMillis()) + extension;
            String fileName = UPLOAD_DIR +urlstr;
            uploadfileVo.setUploadfileUrl(url+"/"+urlstr);
            uploadfileVo.setFileName(originFileName);
            try {
                file.transferTo(new File(fileName));

                FileOutputStream fout = null;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Response.ok().putData(uploadfileVo);
    }*/

    /*
     * 通过流的方式上传文件
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("/upload")
    public Response fileUpload(@RequestParam(required = false, value = "file") MultipartFile file) throws IOException {
        UploadfileVo uploadfileVo = new UploadfileVo();
        //获取input域的name属性
        String name = file.getName();
        //获取文件名，带扩展名
        String originFileName = file.getOriginalFilename();
//        log.info("上传文件,UploadfileController.fileUpload >>>>>>>>>>> originFileName:" + originFileName);
        //获取文件扩展名
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        //获取文件大小，单位字节
        long site = file.getSize();
        if (site > MAX_FILE_SISE) {
            //可以对文件大小进行检查
        }

        long time = System.currentTimeMillis();//new Date().getTime();

        uploadfileVo.setUploadfileUrl(url+"/"+ time + file.getOriginalFilename());
        uploadfileVo.setFileName(originFileName);
        uploadfileVo.setUploadName(time + file.getOriginalFilename());

        //用来检测程序运行时间
        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());
        OutputStream os = null;
        try {
            //获取输出流
             os = new FileOutputStream(url+"/"+ time + file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while ((temp = is.read()) != (-1)) {
                os.write(temp);
            }
            os.flush();
            //os.close();
            is.close();

        } catch (FileNotFoundException e) {
//            log.error("上传文件,UploadfileController.fileUpload >>>>>>>>>>> 失败:" + e.getMessage(), e);
            return Response.error(Constant.ERROR_CODE, "上传失败了");

        }finally {
            if (os != null){
                os.close();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return Response.ok().putData(uploadfileVo);
    }

    /**
     * 在线预览图片
     *
     * @param path
     * @param response
     * @throws IOException
     */
    @RequestMapping("/showImage")
    public void showImage(String path, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        //String fullFileName = ("/upload/" + path);
        String fullFileName = path;
//        log.info("在线预览图片,UploadfileController.showImage >>>>>>>>>>> fullFileName:" + fullFileName);
        FileInputStream fis = new FileInputStream(fullFileName);

        OutputStream os = response.getOutputStream();
        // OutputStream os = null;
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } catch (IOException e) {
//            log.error("在线预览图片,UploadfileController.showImage >>>>>>>>>>> 出错:" + e.getMessage(), e);
        } finally {
            if (os != null) {
                os.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 显示单张图片
     *
     * @return
     */
   /* @RequestMapping("/showImage")
    public ResponseEntity showPhotos(String path){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/
    //下载
    @RequestMapping("/download")
    public String download(String name, HttpServletResponse response, HttpServletRequest request) {
        String filePath = Constant.FILEPATH;
//        log.info("下载,UploadfileController.download >>>>>>>>>>> name:" + name);
//        log.info("下载,UploadfileController.download >>>>>>>>>>> filePath:" + filePath);
        File file = new File(filePath + name);
        if (file.exists()) { //判断文件父目录是否存在
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "download_succeed";
        } else {
//            log.error("下载,UploadfileController.download >>>>>>>>>>>文件不存在1, file:" + file);
            return null;
        }
    }

    /*@RequestMapping("/downloadAll" )
    public void downloadLocal(@RequestBody DownloadVo downloadVo,HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
        String fileName = downloadVo.getName(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = new FileInputStream(downloadVo.getUrl());// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    @RequestMapping("/downloadAll")
    public String downloadAll(String name, HttpServletResponse response, HttpServletRequest request) {
//        log.info("下载,UploadfileController.downloadAll >>>>>>>>>>> name:" + name);
        String[] temp = name.split("/");
        if (temp != null && temp.length > 0) {
            name = temp[temp.length - 1];
        }
        String filename = name;
        String filePath = url;
//        log.info("下载,UploadfileController.downloadAll >>>>>>>>>>> filePath:" + filePath);
        //String filePath ="E://";
        File file = new File(filePath + "/" + filename);
        if (file.exists()) { //判断文件父目录是否存在
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
//                log.error("下载,UploadfileController.downloadAll >>>>>>>>>>>出错：" + e.getMessage(), e);
                return ResponseEnum.DOWNLOAD_FAIL.getMsg();
            }
            try {
                if (bis != null && fis != null) {
                    bis.close();
                    fis.close();
                }
            } catch (IOException e) {
//                log.error("下载,UploadfileController.downloadAll >>>>>>>>>>>关闭流出错：" + e.getMessage(), e);
            }
            //return baseResp.setResp(ResponseEnum.DOWNLOAD_SUCCEED);
        } else {
//            log.error("下载,UploadfileController.downloadAll >>>>>>>>>>>文件不存在2 file:" + file);
            return ResponseEnum.DOWNLOAD_FAIL.getMsg();
        }
        return null;
    }


//    @RequestMapping("/deleteimage")
//    @ResponseBody
//    public Response deleteimage(@RequestBody FileUrlVo fileUrlVo) {
//        File f = new File(fileUrlVo.getSrc());
//        f.delete();
//        return Response.ok();
//    }

    @RequestMapping(value = "/getPdfView")
    @ResponseBody
    public ResponseEntity<byte[]> getPdfView(String uploadfileUrl, String uploadName) {
        logger.info("======UploadfileController.getPdfView,=========pdf预览========pathStr:" + uploadfileUrl);
        // 读取pdf文件到字节里
        Path path = Paths.get(uploadfileUrl);
        byte[] contents = new byte[0];
        try {
            contents = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        //下载
        //headers.setContentDispositionFormData("attachment", filename);
        //查看
        headers.add("content-disposition", "inline;filename=" + uploadName);
        /*try {
            headers.setContentDispositionFormData("attachment", new String(filename.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }


}
