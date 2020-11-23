package app.tech.formCompare.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import app.tech.formCompare.entity.FormBean;

/**
 * 
 * @author Admin 需要修改的方法 getFormBeanList,updateFormForPath,getFormFileList
 */
public class CompareXmlFile {

	public static void main(String[] args) {
		// 开发路径
		String devpath = "D:\\workspace_Factor\\factor\\data\\form\\";
		// 更新合并form表单
		// updateFormForPath需，按需修改
		updateFormForPath(devpath, getFormBeanList());
		System.out.println("更新完成");
	}

	/**
	 * 合并方式： 根据修改的数据集合，去匹配修改， 新增节点会从开发端的表单取出，直接添加到新生成的文件中， 删除会直接删除对应的节点
	 * 修改某个节点的属性， 需根据实体类的beforeValue值先去判断客户端是否也修改过，
	 * 如果修改过会再去根据forceUpdate值去判断是否强制更新， 否则直接根据currValue的值，直接覆盖节点属性的值
	 * 
	 * 注意：更新文件会直接替换客户端文件 type;//1-新增字段，2-更新,9-删除 forceUpdate;//0-不强更新，1-强制更新
	 * 
	 * @param devPath
	 *            开发端表单文件夹路径
	 * @param clientPath
	 *            客户端表单文件夹路径
	 * @param formBeanList
	 *            所有修改属性集合
	 * 
	 */
	public static void updateFormForPath(String devPath, List<FormBean> formBeanList) {
		// 将数据按照formId 分类
		Map<String, List<FormBean>> map = getListToMap(formBeanList);

		// 根据查询的出具集合遍历出需要修改的form表单
		for (String key : map.keySet()) {
			// 自定义文件命名开发端
			String devFilePath = devPath + key + ".xml";

			// 获取客户端对应的多个文件
			List<String> list = getFormFileList(key);

			// 调用更新方法
			// key实际为表单编号
			// devFilePath开发端文件路径
			// clientFilePath客户端文件路径
			// map.get(key)当前表单需要修改的集合
			// 遍历更新客户端文件
			for (String str : list) {
				updateForm(key, devFilePath, str, map.get(key));
			}

		}
	}

	/**
	 * 需要替换成系统获取的数据，返回List<FormBean> 集合
	 * 
	 * @return
	 */
	public static List<FormBean> getFormBeanList() {
		List<FormBean> formBeanList = new ArrayList<FormBean>();
//		// 新增
		FormBean demo = new FormBean();
		demo.setFormId("formcusPersonBase");
		demo.setFieldName("ceshi1");
		demo.setAnchor("postalCode");
		demo.setPosition(2);
		demo.setType(1);
//		// 删除
//		FormBean demo2 = new FormBean();
//		demo2.setFormId("formdoc0009");
//		demo2.setFieldName("docBizNo");
//		demo2.setType(9);
		// 修改
		FormBean demo3 = new FormBean();
		demo3.setFormId("formcusPersonBase");
		demo3.setFieldName("postalCode");
		demo3.setProperty("onclick");
		demo3.setBeforeValue("");
		demo3.setCurrValue("test();");
		demo3.setType(2);
//		// 修改 不强制更新
//		FormBean demo4 = new FormBean();
//		demo4.setFormId("formdoc0009");
//		demo4.setFieldName("dogType");
//		demo4.setProperty("maxlength");
//		demo4.setBeforeValue("2");
//		demo4.setCurrValue("20");
//		demo4.setType(2);
//		demo4.setForceUpdate(0);
//		// 强制更新
//		FormBean demo5 = new FormBean();
//		demo5.setFormId("formdoc0009");
//		demo5.setFieldName("docSplitNo");
//		demo5.setProperty("maxlength");
//		demo5.setBeforeValue("20");
//		demo5.setCurrValue("30");
//		demo5.setType(2);
//		demo5.setForceUpdate(1);

//		formBeanList.add(demo);
//		formBeanList.add(demo2);
		formBeanList.add(demo3);
//		formBeanList.add(demo4);
//		formBeanList.add(demo5);

		return formBeanList;
	}

	/**
	 * 获取客户端列表文件，返回List<String> 集合
	 * 
	 * @return
	 */
	public static List<String> getFormFileList(String formId) {
		List<String> list = new ArrayList<String>();
		String fileName1 = "D:\\workspace_Factor\\factor\\data\\form\\formcusPersonBase_add205.xml";
		String fileName2 = "D:\\workspace_Factor\\factor\\data\\form\\formcusPersonBase_add202.xml";
//		String fileName2 = "c:/cus2" + formId + ".xml";
//		String fileName3 = "e:/cus3" + formId + ".xml";
//		String fileName4 = "f:/cus4" + formId + ".xml";
		list.add(fileName1);
		list.add(fileName2);
//		list.add(fileName3);
//		list.add(fileName4);
		return list;
	}

	/**
	 * 
	 * @param formId
	 *            要更新的表单编号
	 * @param devPath
	 *            开发端表单文件路径
	 * @param clientPath
	 *            客户端表单文件路径
	 * @param formBeanList
	 *            修改属性集合
	 */
	public static void updateForm(String formId, String devPath, String clientPath, List<FormBean> formBeanList) {
		Map<String, List<FormBean>> propmap = getPropMap(formBeanList);
		Map<String, List<FormBean>> addmap = getAddMap(formBeanList);
		Map<String, List<FormBean>> delmap = getDelMap(formBeanList);
		updateXML(devPath, clientPath, propmap.get(formId), addmap.get(formId), delmap.get(formId));
	}

	/**
	 * 
	 * @param formId
	 *            要更新的表单编号
	 * @param devFile
	 *            开发端表单文件
	 * @param clientFile
	 *            客户端表单文件
	 * @param formBeanList
	 *            修改属性集合
	 */
	public static void updateForm(String formId, File devFile, File clientFile, List<FormBean> formBeanList) {
		Map<String, List<FormBean>> propmap = getPropMap(formBeanList);
		Map<String, List<FormBean>> addmap = getAddMap(formBeanList);
		Map<String, List<FormBean>> delmap = getDelMap(formBeanList);
		updateXML(devFile, clientFile, propmap.get(formId), addmap.get(formId), delmap.get(formId));
	}

	public static void updateXML(String devPath, String clientPath, List<FormBean> propList, List<FormBean> addList,
			List<FormBean> delList) {
		File devFile = new File(devPath);
		File clientFile = new File(clientPath);
		if (clientFile.isFile() && devFile.isFile()) {
			updateXML(devFile, clientFile, propList, addList, delList);
		}
	}

	/**
	 * 更新合并节点
	 * 
	 * @param devFile
	 * @param clientFile
	 * @param propList
	 * @param addList
	 * @param delList
	 *            遍历太多 待优化
	 */
	public static void updateXML(File devFile, File clientFile, List<FormBean> propList, List<FormBean> addList,
			List<FormBean> delList) {
		Element devRoot = getXMLRoot(devFile);
		Element clientRoot = getXMLRoot(clientFile);

		//新增节点并排序
		if (addList!=null) {
			elementAddAndSort(devRoot,clientRoot,addList);	
		}
		// 获取开发和客户端所有节点
		List<Element> devListElement = devRoot.elements();
		List<Element> clientListElement = clientRoot.elements();
		// 要删除的节点集合
		List<Element> delElement = new ArrayList<Element>();
		// 修改节点属性和获取删除节点的集合
		for (Element element : clientListElement) {
			if (propList != null) {
				for (FormBean formBean : propList) {
					if (formBean.getFieldName().equals(element.attributeValue("fieldname"))) { // 判断更新前属性值是否一样，一样直接更新，不一样，判断是否强制更新
						if (formBean.getBeforeValue()==null||element.attributeValue(formBean.getProperty())==null||formBean.getBeforeValue().equals(element.attributeValue(formBean.getProperty()))
								|| formBean.getForceUpdate() == 1
								|| element.attributeValue(formBean.getProperty()) == null) {
							setAttribute(element, formBean.getProperty(), formBean.getCurrValue());
						}
					}
				}
			}
			if (delList != null) {
				for (FormBean formBean : delList) {
					if (formBean.getFieldName().equals(element.attributeValue("fieldname"))) {
						delElement.add(element);
					}
				}
			}
		}

		Element root = DocumentHelper.createElement("form");
		List<Attribute> attrs = clientRoot.attributes();
		// 获取form节点属性
		for (Attribute attribute : attrs) {
			root.add((Attribute) attribute.clone());
		}
		// 保存修改后的节点
		for (int i = 0; i < clientListElement.size(); i++) {
			setAttribute(clientListElement.get(i), "formactiveid", (i+1)+"");
			root.add((Element) clientListElement.get(i).clone());
		}
		// 删除节点
		for (Element element : delElement) {
			root.remove(element);
		}
		Document document = DocumentHelper.createDocument(root);
		// 保存
		saveXml(clientFile, document);
	}

	/**
	 * 保存XML
	 * 
	 * @param file
	 * @param document
	 */
	public static void saveXml(File file, Document document) {
		OutputFormat format = new OutputFormat("    ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(new FileOutputStream(file), format);
			xmlWriter.write(document);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (xmlWriter != null) {
				try {
					xmlWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取root节点
	 *
	 * @param file
	 * @return
	 */
	public static Element getXMLRoot(File file) {
		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
		Document document;
		Element root = null;
		try {
			document = sax.read(file);
			root = document.getRootElement();// 获取根节点
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
			DocXmlUtil dx = new DocXmlUtil();
			org.w3c.dom.Document doc2 = dx.docParse(file);
			dx.save(file, doc2);
			root = getXMLRoot(file);
		}
		return root;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Element getXMLRoot(String path) {
		SAXReader sax = new SAXReader();// 创建一个SAXReader对象
		Document document;
		Element root = null;
		File file = new File(path);
		if (file.isFile()) {
			try {
				document = sax.read(file);
				root = document.getRootElement();// 获取根节点
			} catch (DocumentException e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
				DocXmlUtil dx = new DocXmlUtil();
				org.w3c.dom.Document doc2 = dx.docParse(file);
				dx.save(file, doc2);
				root = getXMLRoot(file);
			}
		}
		return root;
	}
	/**
	 * 新增节点并排序
	 * @param devRoot
	 * @param clientRoot
	 * @param addList
	 */
	public static void elementAddAndSort(Element devRoot, Element clientRoot, List<FormBean> addList) {
		List<Element> devElems = devRoot.elements();
		List<Element> clientElems = clientRoot.elements();
		Map<String, Element> addMap = new HashMap<String, Element>();
		Map<String, Element> clientMap = new HashMap<String, Element>();
		if (addList != null) {
			for (FormBean formBean : addList) {
				for (Element element : devElems) {
					if (formBean.getFieldName().equals(element.attributeValue("fieldname"))) {
						addMap.put(formBean.getFieldName(), element);
					}
				}
				for (Element element : clientElems) {
					if (formBean.getAnchor().equals(element.attributeValue("fieldname"))) {
						clientMap.put(formBean.getAnchor(), element);
					}
				}
			}

			for (FormBean formBean : addList) {
				Element anchorElement = clientMap.get(formBean.getAnchor());
				Element addElement = addMap.get(formBean.getFieldName());
				//锚点行次
				int anchorRow = Integer.parseInt(anchorElement.attributeValue("row"));
				// int anchorCol =
				// Integer.parseInt(anchorElement.attributeValue("labelCol"));

				if (formBean.getPosition() == 0) {// 在上方加入
					
					for (Element tempElem : clientElems) {
						//在锚点和锚点之后的行次加1
						int tempRow = Integer.parseInt(tempElem.attributeValue("row"));
						if (tempRow >= anchorRow) {
							setAttribute(tempElem, "row", (tempRow + 1) + "");
						}
					}
					//新加的行放在之前的锚点行
					setAttribute(addElement, "row", anchorRow + "");
					setAttribute(addElement, "labelcol", "1");
					Attribute attr = addElement.attribute("labelcolspan");
					if (attr != null) {
						int addColSpan = Integer.parseInt(attr.getValue());
						setAttribute(addElement, "fieldcol", (addColSpan + 1) + "");
					} else {
						setAttribute(addElement, "fieldcol", "2");
					}
					clientRoot.add((Element) addElement.clone());
				} else if (formBean.getPosition() == 1) {// 向下添加
					for (Element tempElem : clientElems) {
						int tempRow = Integer.parseInt(tempElem.attributeValue("row"));
						if (tempRow > anchorRow) {
							setAttribute(tempElem, "row", (tempRow + 1) + "");
						}
					}
					setAttribute(addElement, "row", (anchorRow + 1) + "");
					//标签行次
					setAttribute(addElement, "labelcol", "1");
					//标签跨列
					Attribute attr = addElement.attribute("labelcolspan");
					if (attr != null) {
						int addColSpan = Integer.parseInt(attr.getValue());
						setAttribute(addElement, "fieldcol", (addColSpan + 1) + "");
					} else {
						//字段行次
						setAttribute(addElement, "fieldcol", "2");
					}
					clientRoot.add((Element) addElement.clone());

				} else if (formBean.getPosition() == 2) {// 向左侧添加
					for (Element tempElem : clientElems) {
						int tempRow = Integer.parseInt(tempElem.attributeValue("row"));
						if (tempRow >= anchorRow) {
							//判断锚点位置
							if ("1".equals(anchorElement.attributeValue("labelcol"))) {// 在左侧 ,直接
								setAttribute(tempElem, "row", (tempRow + 1) + "");
							} else {// 不再左侧
								//判断该节点是否为锚点
								if (tempRow == anchorRow && tempElem.attributeValue("fieldname").equals(anchorElement.attributeValue("fieldname"))) {
									setAttribute(tempElem, "row", (tempRow + 1) + "");
								} else if (tempRow > anchorRow) {
									setAttribute(tempElem, "row", (tempRow + 1) + "");
								}else {
								}
							}

						}
					}
					setAttribute(addElement, "labelcol", "1");

					if ("1".equals(anchorElement.attributeValue("labelcol"))) {// 在左侧
						//判断是否字段跨列
						Attribute attr = addElement.attribute("labelcolspan");
						setAttribute(addElement, "row", (anchorRow) + "");
						if (attr != null) {
							int addColSpan = Integer.parseInt(attr.getValue());
							setAttribute(addElement, "fieldcol", (addColSpan + 1) + "");
						} else {
							setAttribute(addElement, "fieldcol", "2");
						}
					} else {// 不再左侧
						int anchorTempCol = 1;
						Attribute attrLabelColSpan = addElement.attribute("labelcolspan");
						Attribute attrFieldcolspan = addElement.attribute("fieldcolspan");
						setAttribute(addElement, "row", (anchorRow + 1) + "");
						if (attrLabelColSpan != null) {
							anchorTempCol += Integer.parseInt(attrLabelColSpan.getValue());
						}else{
							anchorTempCol+=1;
						}
						if (attrFieldcolspan != null) {
							anchorTempCol += Integer.parseInt(attrFieldcolspan.getValue());
						}
						setAttribute(anchorElement, "labelcol", (anchorTempCol + 1) + "");
						Attribute attr = anchorElement.attribute("labelcolspan");
						if (attr != null) {
							setAttribute(anchorElement, "fieldcol",
									anchorTempCol + Integer.parseInt(attr.getValue()) + "");
						}else{
							setAttribute(anchorElement, "fieldcol", (anchorTempCol + 2) + "");
						}

					}
					clientRoot.add((Element) addElement.clone());
				} else if (formBean.getPosition() == 3) {// 向右侧添加
					for (Element tempElem : clientElems) {
						int tempRow = Integer.parseInt(tempElem.attributeValue("row"));
						if (tempRow >= anchorRow) {
							if ("1".equals(anchorElement.attributeValue("labelcol"))) {// 在左侧
								if (tempRow == anchorRow && !tempElem.attributeValue("fieldname")
										.equals(anchorElement.attributeValue("fieldname"))) {
									setAttribute(tempElem, "row", (tempRow + 1) + "");
								} else if (tempRow > anchorRow) {
									setAttribute(tempElem, "row", (tempRow + 1) + "");
								}else {
								}
							} else {// 在右侧
								if (tempRow > anchorRow) {
									setAttribute(tempElem, "row", (tempRow + 1) + "");
								}
							}

						}
					}
					if ("1".equals(anchorElement.attributeValue("labelcol"))) {// 在左侧
						setAttribute(addElement, "row", (anchorRow) + "");
						int addLabelCol = Integer.parseInt(anchorElement.attributeValue("fieldcol"));
						Attribute anchorFieldcolspan = anchorElement.attribute("fieldcolspan");
						if (anchorFieldcolspan != null) {
							addLabelCol += Integer.parseInt(anchorFieldcolspan.getValue());
						} else {
							addLabelCol += 1;
						}
						setAttribute(addElement, "labelcol", addLabelCol + "");
						Attribute addLabelColSpan = addElement.attribute("labelcolspan");
						if (addLabelColSpan != null) {
							setAttribute(addElement, "fieldcol",
									(addLabelCol + Integer.parseInt(addLabelColSpan.getValue())) + "");
						} else {
							setAttribute(addElement, "fieldcol", (addLabelCol + 1) + "");
						}
					} else {// 在右侧
						setAttribute(addElement, "row", (anchorRow + 1) + "");
						setAttribute(addElement, "labelcol", "1");
						Attribute attr = addElement.attribute("labelcolspan");
						if (attr != null) {
							int addColSpan = Integer.parseInt(attr.getValue());
							setAttribute(addElement, "fieldcol", (addColSpan + 1) + "");
						} else {
							setAttribute(addElement, "fieldcol", "2");
						}
					}
					clientRoot.add((Element) addElement.clone());
				}else {
				}

			}
		}}

	/**
	 * 根据路径取出对应表单
	 * 
	 * @param path
	 * @param formmap
	 * @return
	 */
	public static File getFileMap(String path) {
		File s = new File(path);
		if (s.isFile()) {
			return s;
		}
		return null;
	}

	/**
	 * 构建修改属性的map
	 * 
	 * @param formBeanList
	 * @return
	 */
	public static Map<String, List<FormBean>> getPropMap(List<FormBean> formBeanList) {
		return getListToMap(formBeanList, 2);
	}

	public static Map<String, List<FormBean>> getAddMap(List<FormBean> formBeanList) {
		return getListToMap(formBeanList, 1);
	}

	public static Map<String, List<FormBean>> getDelMap(List<FormBean> formBeanList) {
		return getListToMap(formBeanList, 9);
	}

	/**
	 * 根据表单编号，list 转 map
	 * 
	 * @param formBeanList
	 * @return
	 */
	public static Map<String, List<FormBean>> getListToMap(List<FormBean> formBeanList) {
		Map<String, List<FormBean>> map = new HashMap<String, List<FormBean>>();
		for (FormBean formBean : formBeanList) {
			if (map.containsKey(formBean.getFormId())) {
				List<FormBean> list = map.get(formBean.getFormId());
				list.add(formBean);
				map.put(formBean.getFormId(), list);
			} else {
				List<FormBean> list = new ArrayList<FormBean>();
				list.add(formBean);
				map.put(formBean.getFormId(), list);
			}
		}
		return map;
	}

	public static Map<String, List<FormBean>> getListToMap(List<FormBean> formBeanList, int type) {
		Map<String, List<FormBean>> map = new HashMap<String, List<FormBean>>();
		for (FormBean formBean : formBeanList) {
			if (formBean.getType() == type) {
				if (map.containsKey(formBean.getFormId())) {
					List<FormBean> list = map.get(formBean.getFormId());
					list.add(formBean);
					map.put(formBean.getFormId(), list);
				} else {
					List<FormBean> list = new ArrayList<FormBean>();
					list.add(formBean);
					map.put(formBean.getFormId(), list);
				}
			}
		}
		return map;
	}

	/**
	 * 对指定的节点增加属性和文本
	 * 
	 * @param elmt
	 * @param name
	 * @param value
	 * @param text
	 * @return
	 */
	public static Element addAttribute(Element elmt, String name, String value) {
		elmt.addAttribute(name, value);
		return elmt;
	}

	/**
	 * 修改指定节点的属性和文本
	 * 
	 * @param elmt
	 * @param name
	 * @param value
	 * @param text
	 * @return
	 */
	public static Element setAttribute(Element elmt, String name, String value) {
		if (elmt!=null) {
			Attribute attribute = elmt.attribute(name);
			if(attribute==null){
				addAttribute(elmt,name,value);
			}else{
				attribute.setValue(value);
//				List list = new ArrayList();
//				list.add(attribute);
//				elmt.setAttributes(list);
			}
		}else {
			System.err.println("配置的新增字段不存在");
		}
	
		
		return elmt;
	}

	/**
	 * 删除指定节点的指定属性
	 * 
	 * @param elmt
	 * @param name
	 * @return
	 */
	public static Element removeAttribute(Element elmt, String name) {
		elmt.remove(elmt.attribute(name));
		return elmt;
	}

}
