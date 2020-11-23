package app.component.frontview.action;

import app.base.imageTools.ImgCompress;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocFeign;
import app.component.pact.entity.MfBusPact;
import app.component.sys.entity.SysUser;
import app.tech.upload.FeignSpringFormEncoder;
import app.tech.upload.UploadUtil;
import cn.mftcc.util.DateUtil;
import feign.Feign;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.UUID;

import app.component.doc.feign.DocManageFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.base.SpringUtil;
import app.component.doc.feign.DocTypeConfigFeign;
import app.component.sysInterface.SysInterfaceFeign;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@WebServlet(name = "Uploadsservlet")
public class Uploadsservlet extends HttpServlet {
    public Logger logger = LoggerFactory.getLogger(Uploadsservlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter outs = response.getWriter();
        InputStream is = null;
        try {
            DocManage docManage = null;
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            Map<String, String> dataMap = new HashMap<>();
            String fileName = null;
            String relNo = request.getParameter("relNo");
            MfBusPact mfBusPact = new MfBusPact();
            MfBusPactFeign mfBusPactFeign = SpringUtil.getBean(MfBusPactFeign.class);
            // MfBusPactFeign mfBusPactFeign =Feign.builder().encoder(new FeignSpringFormEncoder()).target(MfBusPactFeign.class, httpurl);
            mfBusPact.setPactId(relNo);
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            String scNo = null;
            String cusNo = null;
            String opNo = null;
            String docBizNo = null;
            if(mfBusPact!=null){
                 scNo = mfBusPact.getNodeNo();
                 cusNo = mfBusPact.getCusNo();
                 opNo = mfBusPact.getOpNo();
                 docBizNo = mfBusPact.getAppId();

            }else{
                throw new Exception("获取合同信息为空，参数pactId="+relNo);
            }

            String docSplitNo = request.getParameter("dsp");
            if (docSplitNo == null) {
                docSplitNo = request.getParameter("docSplitNo");
            }
            DocTypeConfig docTypeConfig = new DocTypeConfig();

            DocFeign docFeign = SpringUtil.getBean(DocFeign.class);
            docTypeConfig.setDocSplitNo(docSplitNo);
            docTypeConfig = docFeign.getByIdDocTypeConfig(docTypeConfig);
            String docType = docTypeConfig.getDocType();


            DiskFileItemFactory fac = new DiskFileItemFactory();
            //设置缓存文件大小
            fac.setSizeThreshold(1024 * 1024);
            //缓存文件位置，这里取的是默认的位置
            fac.setRepository(fac.getRepository());
            ServletFileUpload upload = new ServletFileUpload(fac);
            //设置最大允许上传的文件大小，这里是5MB
            upload.setFileSizeMax(1024 * 1024 * 50);
            List fileList = null;

            fileList = upload.parseRequest(request);
            Iterator iter = fileList.iterator();
            while (iter.hasNext()) {
                FileItem fileItem = (FileItem) iter.next();
                if (!fileItem.isFormField()) {
                    String name = fileItem.getName();
                    String fileSize = new Long(fileItem.getSize()).toString();
                    if (name == null || "".equals(name) || "0".equals(fileSize)) {
                        continue;
                    }
                    //截取出纯文件名
                    name = name.substring(name.lastIndexOf("/") + 1);
                    StringBuilder path = new StringBuilder();
                    StringBuilder pathNew = new StringBuilder();
                    path.append(UploadUtil.getFileUploadPath());
                    UUID uuid = UUID.randomUUID();
                    path.append(File.separator).append(relNo).append(File.separator).append(docType);
                    path.append(File.separator).append(uuid.toString().replaceAll("-", ""));
                    pathNew.append(File.separator).append(relNo).append(File.separator).append(docType);
                    pathNew.append(File.separator).append(uuid.toString().replaceAll("-", ""));
                    String realpath = path.toString();
                    String realpathNew = pathNew.toString();
                    fileName = name;
                    File f = new File(realpath);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    //存储文件
                    File saveFile = new File(realpath + File.separator + name);
                    fileItem.write(saveFile);

                    is = new FileInputStream(saveFile);
                    docManage = new DocManage();
                    docManage.setDocSize(this.bytes2kb(is.available()));
                    request.setAttribute("docSize", this.bytes2kb(is.available()));
                    if (name.toLowerCase().indexOf("jpg") != -1) {
                        ImgCompress imgCom = new ImgCompress(saveFile, realpath, fileName);
                        File tempFile = imgCom.resizeFix(105, 75);
                        docManage.setCompressPath(tempFile.getPath());
                    }
                    docManage.setRegDate(DateUtil.getDate());
                    SysUser sysUser = new SysUser();
                    //  User.getRegNo()
                    sysUser.setOpNo(opNo);
                    SysInterfaceFeign sysInterfaceFeign = SpringUtil.getBean(SysInterfaceFeign.class);
                    sysUser = sysInterfaceFeign.getSysUserById(sysUser);
                    docManage.setRegNo(opNo);
                    docManage.setOpNo(opNo);
                    docManage.setOpName(sysUser.getOpName());
                    docManage.setBrNo(sysUser.getBrNo());
                    docManage.setBrName(sysUser.getBrName());
                    docManage.setOrgNo(sysUser.getBrNo());
                    docManage.setDocName(fileName);
                    docManage.setDocAddr(realpathNew + File.separator + fileName);
                    docManage.setDocBizNo(docBizNo);
                    docManage.setScNo(scNo);
                    docManage.setDocSplitNo(docSplitNo);
                    docManage.setDocEncryptAddr(docManage.getDocAddr());
                    docManage.setDocType(docType);
                    docManage.setRelNo(relNo);
                    docManage.setCusNo(cusNo);
                    docFeign.insertDocManage(docManage);
                     /*File file = (File) fileItem;
                     if(!file.exists()){
                     }*/
                }
            }
            dataMap.put("flag", "success");
            JSONObject jsonObject = JSONObject.fromObject(dataMap);
            outs.write(jsonObject.toString());
        } catch (FileUploadException ex) {
//            response.sendRedirect("admin/upload.jsp?result=size");
            /*response.sendRedirect("/index.jsp");*/
            logger.error("UploadsServlet出错：",ex);
        } catch (Exception e) {
            logger.error("UploadsServlet出错：",e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
            try {
                if (outs != null) {
                    outs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }

    public Double bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024);
        double returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .doubleValue();
        BigDecimal b = new BigDecimal(returnValue);
        returnValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return returnValue;
    }
}
