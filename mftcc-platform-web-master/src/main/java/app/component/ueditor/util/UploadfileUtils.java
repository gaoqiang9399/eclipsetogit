package app.component.ueditor.util;

import app.component.ueditor.entity.Ueditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


/**
 * Created by zhiang.feng on 2018/6/18.
 */
public class UploadfileUtils {

    private Logger logger = LoggerFactory.getLogger(UploadfileUtils.class);

    private final ResourceLoader resourceLoader;


    public UploadfileUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static final long MAX_FILE_SISE = 1000000;

/*

    //下载
    @RequestMapping("/download")
    public  String download(String name,HttpServletResponse response, HttpServletRequest request) {
        String filePath = Contant.FILEPATH;
        File file = new File(filePath+name);
        if(file.exists()){ //判断文件父目录是否存在
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + name);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
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
        }
        return null;
    }

    @RequestMapping("/deleteimage")
    @ResponseBody
    public  Response deleteimage(@RequestBody FileUrlVo fileUrlVo){
        File f = new File(fileUrlVo.getSrc());
        f.delete();
        return Response.ok();
    }

    @RequestMapping(value="/getPdfView")
    @ResponseBody
    public ResponseEntity<byte[]> getPdfView(String uploadfileUrl , String uploadName) {
        logger.info("======pdf预览========getPdfView===========");
        logger.info("======getPdfView , pathStr : " + uploadfileUrl);
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
        */
/*try {
            headers.setContentDispositionFormData("attachment", new String(filename.getBytes("utf-8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*//*

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }
*/


    public static Ueditor uploadImage(String url, MultipartFile file) throws IOException {

        Ueditor ueditor = new Ueditor();
        //获取input域的name属性
        String name = file.getName();
        //获取文件名，带扩展名
        String originFileName = file.getOriginalFilename();
        //获取文件扩展名
        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        //获取文件大小，单位字节
        long site = file.getSize();
        if (site > MAX_FILE_SISE) {
            //可以对文件大小进行检查
        }
        long time = System.currentTimeMillis();

        //用来检测程序运行时间
        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());
        OutputStream os = null;
        try {
            String imgUrl = url +"/"+ time + extension;
            //获取输出流
            os = new FileOutputStream(imgUrl);

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
            ueditor.setState("SUCCESS");
            ueditor.setUrl("/showImage?path=" + URLEncoder.encode( imgUrl, "UTF-8" ));
            ueditor.setTitle(originFileName);
            ueditor.setOriginal(originFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os !=null){
                os.close();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return ueditor;
    }


}
