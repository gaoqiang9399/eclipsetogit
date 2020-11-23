package app.component.thirdservice.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class ServletFreeMarker {
	private Configuration cfg ;
	/**
	 * 初始化 渲染类
	 * @param servletContext
	 * @param path contextpath下的路径  用于存放 模板 如：/WEB-INF/jsp/ftl/
	 */
	public ServletFreeMarker(ServletContext servletContext, String path) {
		this.cfg = new Configuration(Configuration.VERSION_2_3_22);
		this.cfg.setServletContextForTemplateLoading(servletContext, path);
		this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		this.cfg.setDefaultEncoding("utf-8");
		this.cfg.setEncoding(Locale.CHINA,"utf-8"); 
	}
	
	/**
	 * 用于渲染生成html
	 * @param root 对象
	 * @param ftl 模板
	 * @return
	 */
	public String generateHtml(Map<String,Object> root,String ftl) throws Exception{
		String result=null;
		ByteArrayOutputStream bao=null;
		Writer out = null;
		try {
			Template temp = this.cfg.getTemplate(ftl);
			bao=new ByteArrayOutputStream();
			out = new OutputStreamWriter(bao, "utf-8");
			temp.process(root, out);
			out.flush();
			bao.flush();
			result=new String(bao.toByteArray(),"utf-8");
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
