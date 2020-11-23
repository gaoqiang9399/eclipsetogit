package app.util;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

public class FileIOStreamTool {
    public static void generateFileIOStream(File file, HttpServletResponse response, boolean isDelete) throws Exception {
        BufferedInputStream fis = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            //fis.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete){
                file.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (fis !=null){
                fis.close();
            }
        }
    }

}
