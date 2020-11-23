package app.tech.layoutDesginer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;

public class FreemarkerUtil {
	public static JSONObject chartDatas;

	public Template getTemplate(String name, String ftlPath) {
		Template temp = null;
		try {
			// 通过Freemarker的Configuration读取相应的Ftl
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
			cfg.setDefaultEncoding("UTF-8");
			// 设定去哪里读取相应的ftl模板
			cfg.setDirectoryForTemplateLoading(new File(ftlPath));
			// 在模板文件目录中寻找名称为name的模板文件
			temp = cfg.getTemplate(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 控制台输出文件内容
	 * 
	 * @param name
	 * @param rootMap
	 */
	public void print(String name, Map<String, Object> rootMap, String ftlPath) {
		try {
			// 通过Template类可以将模板文件输出到相应的文件
			Template temp = this.getTemplate(name, ftlPath);
			temp.process(rootMap, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将替换后的模板内容输出到文件
	 * 
	 * @param name
	 * @param rootMap
	 * @param outFile
	 */
	public void fprint(String name, Map<String, Object> rootMap, String ftlPath, String outFile) {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
			Template template = this.getTemplate(name, ftlPath);
			template.process(rootMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	/**
	 * overload for SpringMVC 
	 * @param file
	 * @return
	 */
	public static List<String> readFile(InputStream file) {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		String tempString = null;
		int iLine = 1;
		try {
			InputStreamReader isr = new InputStreamReader(file);
			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				if (iLine == 1) {
					list.add(tempString);
				} else if (iLine == 2) {
					list.add(tempString);
				}else {
				}
				iLine++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public static List<String> readFile(File file) {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		String tempString = null;
		int iLine = 1;
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				if (iLine == 1) {
					list.add(tempString);
				} else if (iLine == 2) {
					list.add(tempString);
				}else {
				}
				iLine++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return list;
	}

	public static String layoutCharts(HttpServletRequest request, String filePath) {
		File jspFile = new File(filePath);
		List<String> list = FreemarkerUtil.readFile(jspFile);
		String str = list.get(0);
		str = str.substring(str.indexOf("["), str.lastIndexOf("]") + 1);
		request.setAttribute("cellDatas", str);
		str = list.get(1);
		str = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
		// String seriesData =
		// "[{legname:'最高温度',series:[{name:'周一',value:'11'},{name:'周二',value:'-2'},{name:'周三',value:'4'},{name:'周四',value:'14'},{name:'周五',value:'5'}]},{legname:'最低温度',series:[{name:'周一',value:'7'},{name:'周二',value:'-9'},{name:'周三',value:'0'},{name:'周四',value:'2'},{name:'周五',value:'1'}]}]";
		request.setAttribute("blockDatas", str);
		if (!jspFile.exists()) {
			return null;
		} else {
			return "success";
		}
	}
}
