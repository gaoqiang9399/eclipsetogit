package app.component.doc.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.directwebremoting.guice.RequestParameters;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import app.base.ServiceException;
import app.component.doc.entity.SceDocTypeRel;
import app.tech.upload.UploadUtil;

@RestController
@RequestMapping("/sysImg")
public class SysImgController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/{fileName}.{suffix}")
	public ResponseEntity<byte[]> getSceDocTypeRels(@PathVariable String fileName,@PathVariable String suffix) throws Exception {
		String savepath = UploadUtil.getFileUploadPath()+File.separator+"sysImg";
		File file = new File(savepath, fileName+"."+suffix);
		InputStream inputStream = null;
		ResponseEntity<byte[]> entity = null;
		try
		{

			if(!file.exists()){
				return null;
			}else{
				inputStream = new BufferedInputStream(new FileInputStream(file));
			}
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attchement;filename="+new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
			HttpStatus statusCode = HttpStatus.OK;
			entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally {
			if(inputStream != null)
			{
				inputStream.close();
			}
		}
		return entity;
	}
	
	 private static byte[] toByteArray(InputStream in) throws IOException {
	        ByteArrayOutputStream out=new ByteArrayOutputStream();
	        byte[] buffer=new byte[1024*4];
	        int n=0;
	        while ( (n=in.read(buffer)) !=-1) {
	            out.write(buffer,0,n);
	        }
	        return out.toByteArray();
	    }
}
