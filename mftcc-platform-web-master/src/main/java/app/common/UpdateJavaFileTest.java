/**
 * Copyright (C) DXHM 版权所有
 * 文件名： changeJavaFileTest.java
 * 包名： app.common
 * 说明：
 * @author Javelin
 * @date 2018年3月6日 下午5:10:27
 * @version V1.0
 */
package app.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 类名： UpdateJavaFileTest 描述：
 * 
 * @author Javelin
 * @date 2018年3月6日 下午5:10:27
 * 
 * 
 */
public class UpdateJavaFileTest {


	public static boolean readfile(String filepath) throws  IOException {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("path=" + file.getPath());

			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath());
						updateJavaAction2Controller(readfile, filepath);
//						updateJavaBo2Feign(readfile, filepath);
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}else {
					}
				}

			}else {
			}

		} catch (IOException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 方法描述： Action 2 Controller
	 * 
	 * @param readfile
	 * @param filepath
	 * @return
	 * @throws IOException
	 *             boolean
	 * @author Javelin
	 * @date 2018年3月7日 下午1:52:44
	 */
	public static boolean updateJavaAction2Controller(File readfile, String filepath) throws IOException {
		String fileName = readfile.getName();
		if (fileName.endsWith("Action.java")) {
			String newName = fileName.replace("Action", "Controller");
			boolean addRequst = true;
			boolean isFirstForm = true;
			InputStreamReader rdCto = null;
			BufferedReader bfReader = null;
			OutputStreamWriter fw = null;
			BufferedWriter bw = null;
			try {
				List<String> fileList = new ArrayList<String>();
				rdCto = new InputStreamReader(new FileInputStream(readfile));
				bfReader = new BufferedReader(rdCto);
				String txtline = null;
				boolean isDel = false;
				String mapp = "", formName = "";
				while ((txtline = bfReader.readLine()) != null) {
					isDel = false;
					if (txtline.contains(".bo.")) {
						if (addRequst) {
							fileList.add("import javax.servlet.http.HttpServletRequest;" + "\r\n");
							fileList.add("import javax.servlet.http.HttpServletResponse;" + "\r\n");
							fileList.add("import org.springframework.beans.factory.annotation.Autowired;" + "\r\n");
							fileList.add("import org.springframework.stereotype.Controller;" + "\r\n");
							fileList.add("import org.springframework.ui.Model;" + "\r\n");
							fileList.add("import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n");
							fileList.add("import org.springframework.web.bind.annotation.ResponseBody;" + "\r\n");
							addRequst = false;
						}
						txtline = txtline.replaceAll(".bo.", ".feign.");
						txtline = txtline.replaceAll("Bo;", "Feign;");
					} else if (txtline.contains("struts2.ServletActionContext")) {
						isDel = true;
					} else if (txtline.startsWith("public class")) {
						addRequst = true;
						mapp = fileName.substring(0, fileName.length() - 11);
						mapp = mapp.substring(0, 1).toLowerCase() + mapp.substring(1);
						fileList.add("@Controller" + "\r\n");
						fileList.add("@RequestMapping(\"/" + mapp + "\")" + "\r\n");
						txtline = txtline.replaceAll(fileName.substring(0, fileName.length() - 5), newName.substring(0, newName.length() - 5));
					} else if (txtline.contains("Bo") && !txtline.contains("Boo")) {
						if (addRequst) {
							fileList.add("\t@Autowired" + "\r\n");
							fileList.add("\tprivate HttpServletRequest request;" + "\r\n");
							fileList.add("\t@Autowired" + "\r\n");
							fileList.add("\tprivate HttpServletResponse response;" + "\r\n");
							addRequst = false;
						}
						if (txtline.endsWith("Bo;")) {
							fileList.add("\t@Autowired" + "\r\n");
						}
						txtline = txtline.replaceAll("Bo ", "Feign ");
						txtline = txtline.replaceAll("Bo;", "Feign;");
						txtline = txtline.replaceAll("Bo\\.", "Feign.");
					} else if (txtline.contains("Interface")) {
						if (addRequst && txtline.startsWith("import")) {
							fileList.add("import javax.servlet.http.HttpServletRequest;" + "\r\n");
							fileList.add("import javax.servlet.http.HttpServletResponse;" + "\r\n");
							fileList.add("import org.springframework.beans.factory.annotation.Autowired;" + "\r\n");
							fileList.add("import org.springframework.stereotype.Controller;" + "\r\n");
							fileList.add("import org.springframework.ui.Model;" + "\r\n");
							fileList.add("import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n");
							fileList.add("import org.springframework.web.bind.annotation.ResponseBody;" + "\r\n");
							addRequst = false;
						}
						if (addRequst) {
							fileList.add("\t@Autowired" + "\r\n");
							fileList.add("\tprivate HttpServletRequest request;" + "\r\n");
							fileList.add("\t@Autowired" + "\r\n");
							fileList.add("\tprivate HttpServletResponse response;" + "\r\n");
							addRequst = false;
						}
						if (txtline.endsWith("Interface;") && !txtline.startsWith("import")) {
							fileList.add("\t@Autowired" + "\r\n");
						}
						txtline = txtline.replaceAll("Interface ", "InterfaceFeign ");
						txtline = txtline.replaceAll("Interface;", "InterfaceFeign;");
						txtline = txtline.replaceAll("Interface\\.", "InterfaceFeign.");
					} else if (txtline.contains("private") && (txtline.indexOf("(") < 0 || txtline.contains("FormService"))) {
						if (txtline.contains("FormData") && isFirstForm) {
							String[] fd = txtline.split(";");
							String[] fdm = fd[0].split(" ");
							formName = fdm[fdm.length - 1];
							isFirstForm = false;
						}
						isDel = true;
					} else if (txtline.contains("public String") || txtline.contains("public void")) {
						int leftIndex = txtline.indexOf("(");
						String[] before = txtline.substring(0, leftIndex).split(" ");
						String method = before[before.length - 1];
						if (method.contains("Ajax")) {
							fileList.add("\t@RequestMapping(value = \"/" + method + "\")" + "\r\n");
							fileList.add("\t@ResponseBody" + "\r\n");
							txtline = txtline.replaceAll("public String ", "public Map<String, Object> ");
							if (method.contains("findByPageAjax")) {
								txtline = txtline.replaceAll("\\(\\)", "(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData)");
							} else {
								txtline = txtline.replaceAll("\\(\\)", "(String ajaxData)");
							}
						} else {
							fileList.add("\t@RequestMapping(value = \"/" + method + "\")" + "\r\n");
							if ("getListPage".equals(method) || "input".equals(method) || "insert".equals(method) || txtline.contains("public void")) {
								txtline = txtline.replaceAll("\\(\\)", "(Model model)");
							} else {
								txtline = txtline.replaceAll("\\(\\)", "(Model model, String ajaxData)");
							}
						}
					} else if (txtline.contains("ServletActionContext")) {
						if (txtline.contains("ActionContext.initialize")) {
							fileList.add("\t\tFormService formService = new FormService();" + "\r\n");
						}
						txtline = txtline.replaceAll("ServletActionContext\\.getRequest\\(\\)", "request");
						txtline = txtline.replaceAll("ServletActionContext\\.getResponse\\(\\)", "response");
					} else if (txtline.contains("dataMap = new ")) {
						txtline = txtline.replaceAll("dataMap = new ", "Map<String, Object> dataMap = new ");
					} else if (txtline.contains("formService.")) {
						txtline = txtline.replaceAll("\t\t", "");
						txtline = "\t\tFormData " + txtline;
					} else if (txtline.contains(" = new ")) {
						String[] newObj = txtline.split(" = new ");
						if (!newObj[0].trim().contains(" ") && newObj[1].indexOf("(") > 0) {
							String objName = newObj[1].substring(0, newObj[1].indexOf("("));
							txtline = txtline.replaceAll("\t\t", "");
							txtline = "\t\t" + objName + " " + txtline;
						}
					} else if (txtline.contains("ipage.setPageNo")) {
						fileList.add("\t\t\tipage.setPageSize(pageSize);" + "\r\n");
					} else if (txtline.contains("SUCCESS;")) {
						txtline = txtline.replaceAll("SUCCESS;", "dataMap;");
					} else if (txtline.contains("return \"")) {
						fileList.add("\t\tmodel.addAttribute(\"" + formName + "\", " + formName + ");" + "\r\n");
						fileList.add("\t\tmodel.addAttribute(\"query\", \"\");" + "\r\n");
					} else if (txtline.contains("addActionMessage")) {
						int leftIndex = txtline.indexOf("(");
						txtline = txtline.substring(0, leftIndex + 1) + "model, " + txtline.substring(leftIndex + 1);
					} else if (txtline.contains("return getListPage")) {
						int leftIndex = txtline.indexOf("(");
						txtline = txtline.substring(0, leftIndex + 1) + "model" + txtline.substring(leftIndex + 1);
					}else {
					}
					if (!isDel) {
						fileList.add(txtline + "\r\n");
					}
				}

				// 写文件
				String dstpath = filepath + File.separator + "controller" + File.separator;
				new File(dstpath).mkdirs();
				File newFile = new File(dstpath + newName);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}

				fw = new OutputStreamWriter(new FileOutputStream(newFile), "utf-8");
				bw = new BufferedWriter(fw);
				for (Iterator<String> iterator = fileList.iterator(); iterator.hasNext(); ) {
					String string = iterator.next();
					bw.write(string);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bfReader != null) {
						bfReader.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (rdCto != null) {
						rdCto.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (fw != null) {
						fw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (bw != null) {
						bw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	/**
	 * 方法描述： Bo 2 Feign
	 * 
	 * @param readfile
	 * @return
	 * @throws Exception
	 *             boolean
	 * @author Javelin
	 * @date 2018年3月6日 下午6:07:53
	 */
	public static boolean updateJavaBo2Feign(File readfile, String filepath) throws IOException {
		String fileName = readfile.getName();
		boolean isInterface = false;
		boolean isFirstImport = true;
		if (fileName.endsWith("Bo.java") || fileName.endsWith("Interface.java")) {
			String newName = fileName.replace("Bo", "Feign");
			if (fileName.endsWith("Interface.java")) {
				isInterface = true;
				newName = fileName.replace("Interface", "InterfaceFeign");
			}
			InputStreamReader rdCto = null;
			BufferedReader bfReader = null;
			OutputStreamWriter fw = null;
			BufferedWriter bw = null;
			try {
				List<String> fileList = new ArrayList<String>();
				rdCto = new InputStreamReader(new FileInputStream(readfile));
				bfReader = new BufferedReader(rdCto);
				String txtline = null;
				while ((txtline = bfReader.readLine()) != null) {
					int leftIndex = txtline.indexOf("(");
					int rightIndex = txtline.indexOf(")");
					if (txtline.startsWith("import") && isFirstImport) {
						fileList.add("import org.springframework.cloud.netflix.feign.FeignClient;" + "\r\n");
						fileList.add("import org.springframework.web.bind.annotation.RequestBody;" + "\r\n");
						fileList.add("import org.springframework.web.bind.annotation.RequestMapping;" + "\r\n");
						fileList.add("import org.springframework.web.bind.annotation.RequestParam;" + "\r\n");
						isFirstImport = false;
					} else if (txtline.startsWith("public") && txtline.contains("interface")) {
						fileList.add("@FeignClient(\"mftcc-platform-factor\")" + "\r\n");
//						txtline = "public interface " + newName.substring(0, newName.length() - 5) + " {";
						txtline = txtline.replaceAll(fileName.substring(0, fileName.length() - 5), newName.substring(0, newName.length() - 5));
					} else if (txtline.contains("public") || (leftIndex > 0 && rightIndex > 0 && txtline.contains("Exception"))) {
						String before = txtline.substring(0, leftIndex);
						String middle = txtline.substring(leftIndex);
						String after = "";
						if (rightIndex > 0) {
							middle = txtline.substring(leftIndex, rightIndex);
							after = txtline.substring(rightIndex);
						}
						String bf[] = before.split(" ");
						String mapp = fileName.substring(0, (fileName.length() - 7));
						if (isInterface) {
							mapp = fileName.substring(0, (fileName.length() - 5));
						}
						mapp = mapp.substring(0, 1).toLowerCase() + mapp.substring(1);
						fileList.add("\t@RequestMapping(value = \"/" + mapp + "/" + bf[bf.length - 1] + "\")" + "\r\n");
						if (rightIndex - leftIndex > 6) {
							middle = middle.replaceAll("\\(", "(@RequestBody ");
							if (middle.indexOf(",") > 0) {
								String[] param = middle.split(",");
								for (int i = 0; i < param.length; i++) {
									if (param[i].contains("Map<") || param[i].contains("RequestBody")) {
										continue;
									} else {
										String[] ps = param[i].split(" ");
										String p = ps[ps.length - 1];
										if (param[i].contains(">") && !param[i].contains("List")) {
											if (param[i - 1].contains("(")) {
												continue;
											}
											if (param.length > 2) {
												param[i - 1] = "@RequestParam(\"" + p + "\") " + param[i - 1];
											}
										} else {
											param[i] = "@RequestParam(\"" + p + "\") " + param[i];
										}
									}
								}
								StringBuffer pb = new StringBuffer();
								for (int i = 0; i < param.length; i++) {
									pb.append(",").append(param[i]);
									if (middle.endsWith(",") && i == (param.length - 1)) {
										pb.append(",");
									}
								}
								middle = pb.toString().substring(1);
							}
						}

						txtline = before + middle + after;
					} else if (txtline.indexOf(",") > 0 && txtline.indexOf("*") < 0) {
						String before = txtline;
						String after = "";
						if (rightIndex > 0) {
							before = txtline.substring(0, rightIndex);
							after = txtline.substring(rightIndex);
						}
						String[] param = before.split(",");
						for (int i = 0; i < param.length; i++) {
							if (param[i].contains("Map<") || param[i].contains("RequestBody")) {
								continue;
							} else {
								String[] ps = param[i].split(" ");
								String p = ps[ps.length - 1];
								if (param[i].contains(">") && !param[i].contains("List")) {
									if (param[i - 1].contains("(")) {
										continue;
									}
									if (param.length > 2) {
										param[i - 1] = "@RequestParam(\"" + p + "\") " + param[i - 1];
									}
								} else {
									param[i] = "@RequestParam(\"" + p + "\") " + param[i];
								}
							}
						}
						StringBuffer pb = new StringBuffer();
						for (int i = 0; i < param.length; i++) {
							pb.append(",").append(param[i]);
							if (before.endsWith(",") && i == (param.length - 1)) {
								pb.append(",");
							}
						}
						before = pb.toString().substring(1);
						txtline = before + after;
					}else {
					}
					fileList.add(txtline + "\r\n");
				}
				bfReader.close();
				rdCto.close();

				// 写文件
				String dstpath = filepath + File.separator + "feign" + File.separator;
				new File(dstpath).mkdirs();
				File newFile = new File(dstpath + newName);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}

				fw = new OutputStreamWriter(new FileOutputStream(newFile), "utf-8");
				bw = new BufferedWriter(fw);
				for (Iterator<String> iterator = fileList.iterator(); iterator.hasNext();) {
					String string = iterator.next();
					bw.write(string);
				}
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (bfReader != null) {
						bfReader.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (rdCto != null) {
						rdCto.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (fw != null) {
						fw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (bw != null) {
						bw.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
