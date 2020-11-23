package app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;

/**
 * 更新表单可编辑主键方法
 * 1.根据以配置但字段主键更新 entityPrimaryKeys 隐藏域值
 * 注意：以下三种情况不做任何操作
 *      1).表单中存在entityPrimaryKeys字段不操作。
 *      2).配置但字段编辑角色为空的不处理
 *      3).配置了但字段编辑且主键不一致提示不处理
 * @author JIZH
 * @date 2017-08-26
 */
public class UpdateFormEntityPrimaryKeysUtil {
	/**
	 * 表单路径
	 */
	private final static String getFromPath(){
		Properties fileProps = System.getProperties();
		InputStream in = UpdateFormEntityPrimaryKeysUtil.class.getClassLoader().getResourceAsStream("path.properties");
		try {
			fileProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileProps.getProperty("configPath")+"\\data\\form";
	}
	
	/**
	 * 通过Action调用方式处理
	 * @throws Exception 
	 */
	public static void updateForAction() throws Exception {
		System.out.println("****updateForAction***start***");
		String fromPath = getFromPath();
		File files = new File(fromPath);
		File[] arr = files.listFiles();
		String filename = "";
		FormService formService = new FormService();
		FormData formData;
		for(int i=0;i<arr.length;i++){
			if(arr[i].isFile()){
				filename = arr[i].getName();
				String formname = filename.substring(4,filename.indexOf(".xml"));
				formData = formService.getFormData(formname);
				if(formData==null){
					throw new Exception("表单不存在");
				}else{
					String primaryKeys = "";
					boolean existprimaryKeysHiddenFlag = false;
					int maxFormActiveId = 1;
					FormActive []fromActive = formData.getFormActives();
					FormActive []newfromActive = new FormActive[fromActive.length+1];
					for (int j=0;j<fromActive.length;j++){
						 FormActive formActive = fromActive[j];
						 newfromActive[j]=formActive;
						 if(maxFormActiveId<formActive.getFormActiveId()){
							 maxFormActiveId = formActive.getFormActiveId();
						 }
						 String fieldName = formActive.getFieldName();
						 String labelName = formActive.getLabelName();
						 String writeAuth = formActive.getWriteAuth();
						 String parm = formActive.getPara();
						 if("entityPrimaryKeys".equals(fieldName)){
							 existprimaryKeysHiddenFlag = true;
							 break;
						 }else{
							 if(writeAuth!=null&&!"0".equals(writeAuth) &&!"1".equals(writeAuth)){
								 if(!"".equals(primaryKeys)&&!primaryKeys.equals(parm)){
									 System.out.println("表单"+filename+":存在不同的双击编辑主键");
									 existprimaryKeysHiddenFlag = true;
									 break;
								 }
								 primaryKeys = parm;
							 }
						 }
					}
					if(primaryKeys!=null&&!"".equals(primaryKeys)&&!existprimaryKeysHiddenFlag){
						FormActive insertformActive = new FormActive();
						insertformActive.setFieldName("entityPrimaryKeys");
						insertformActive.setLabelName("entityPrimaryKeys");
						insertformActive.setDataType(0);
						insertformActive.setFieldAlign(1);
						insertformActive.setFieldCol(2);
						insertformActive.setFieldType(99);
						insertformActive.setHorVer("0");
						insertformActive.setLabelAlign(1);
						insertformActive.setLabelCol(1);
						insertformActive.setFormActiveId(++maxFormActiveId);
						insertformActive.setMustInput("");
						insertformActive.setReadonly("");
						insertformActive.setPara(primaryKeys);
						insertformActive.setRow(1);
						newfromActive[fromActive.length] = insertformActive;
						formData.setFormActives(newfromActive);
						formService.writeFromXml(formData,"update");
						System.out.println("更新"+filename+"新增隐藏域entityPrimaryKeys，值为"+primaryKeys);
					}
				}
			}
		}
		System.out.println("****updateForAction***end***");
	}
	/**
	 * 通过主函数执行处理
	 * @throws Exception 
	 */
	private static void updateForMainFuction(String fromPath) throws Exception {
//		String fromPath = "C:/testdata/data/form/";
		File files = new File(fromPath);
		File[] arr = files.listFiles();
		String filename = "";
		File objectFile =null ;
		File newXmlFile = null;
		for(int i=0;i<arr.length;i++){
			if(arr[i].isFile()){
				filename = arr[i].getName();
					objectFile = new File(fromPath+"\\"+filename);
					newXmlFile = new File(fromPath+"\\"+filename);
					try {
						Document doc  = null;
					  	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					    DocumentBuilder db;
					    try {
							db = dbf.newDocumentBuilder();
							doc = db.parse(objectFile);
						} catch (Exception e1) {
							ObjectInputStream ois = null;
							ois = new ObjectInputStream(new FileInputStream(objectFile));
							doc = (Document) ois.readObject();
						}
					    doc.normalize();
					    Element root = doc.getDocumentElement();
					    String formId = root.getAttribute("id");
					    System.out.println(formId);
					    NodeList nodeList = root.getChildNodes();
					    int maxFormActiveId = 0;
					    String primaryKeys = "";
					    boolean existprimaryKeysHiddenFlag = false;
					    for (int j = 0; j <nodeList.getLength();j++) {
					    	Node node = nodeList.item(j);
					    	NamedNodeMap keyMap = node.getAttributes();
					    	if(keyMap!=null){
					    		int formactiveid = Integer.parseInt(keyMap.getNamedItem("formactiveid").getNodeValue());
					    		if(formactiveid>maxFormActiveId){
					    			maxFormActiveId = formactiveid;
					    		}
					    		if(keyMap.getNamedItem("fieldname")!=null&&"entityPrimaryKeys".equals(keyMap.getNamedItem("fieldname").getNodeValue())){
					    			existprimaryKeysHiddenFlag = true;
					    			break;
					    		}else{
					    			if(keyMap.getNamedItem("writeAuth")!=null&&!"0".equals(keyMap.getNamedItem("writeAuth").getNodeValue())&&!"1".equals(keyMap.getNamedItem("writeAuth").getNodeValue())){
					    				String parm = "";
					    				if(keyMap.getNamedItem("param")!=null){
					    					parm = keyMap.getNamedItem("param").getNodeValue();
					    				}
					    				if(!"".equals(primaryKeys)&&!primaryKeys.equals(parm)){
											 System.out.println("表单"+filename+":存在不同的双击编辑主键");
											 existprimaryKeysHiddenFlag = true;
											 break;
										 }
										 primaryKeys = parm;
					    			}
					    		}
					    	}
						}
					    if(primaryKeys!=null&&!"".equals(primaryKeys)&&!existprimaryKeysHiddenFlag){					    	
					    	Element formelement = doc.createElement("formelement");  
					    	Attr fieldName = doc.createAttribute("fieldname");
					    	fieldName.setValue("entityPrimaryKeys");
					    	formelement.setAttributeNode(fieldName);
					    	Attr labelName = doc.createAttribute("labelname"); 
					    	labelName.setValue("entityPrimaryKeys"); 
					    	formelement.setAttributeNode(labelName);
					    	Attr dataType = doc.createAttribute("datatype"); 
					    	dataType.setValue("0");
					    	formelement.setAttributeNode(dataType);
					    	Attr fieldAlign = doc.createAttribute("fieldalign"); 
					    	fieldAlign.setValue("1");
					    	formelement.setAttributeNode(fieldAlign);
					    	Attr fieldCol = doc.createAttribute("fieldcol");
					    	fieldCol.setValue("2");
					    	formelement.setAttributeNode(fieldCol);
					    	Attr fieldType = doc.createAttribute("fieldtype"); 
					    	fieldType.setValue("99");
					    	formelement.setAttributeNode(fieldType);
					    	Attr horVer = doc.createAttribute("horver"); 
					    	horVer.setValue("0");
					    	formelement.setAttributeNode(horVer);
					    	Attr labelAlign = doc.createAttribute("labelalign"); 
					    	labelAlign.setValue("1");
					    	formelement.setAttributeNode(labelAlign);
					    	Attr labelCol = doc.createAttribute("labelcol"); 
					    	labelCol.setValue("1");
					    	formelement.setAttributeNode(labelCol);
					    	Attr formActiveId = doc.createAttribute("formactiveid"); 
					    	formActiveId.setValue((++maxFormActiveId)+"");
					    	formelement.setAttributeNode(formActiveId);
					    	Attr mustInput = doc.createAttribute("mustInput");
					    	mustInput.setValue("");
					    	formelement.setAttributeNode(mustInput);
					    	Attr readonly = doc.createAttribute("readonly");
					    	readonly.setValue("");
					    	formelement.setAttributeNode(readonly);
					    	Attr param = doc.createAttribute("param");
					    	param.setValue(primaryKeys);
					    	formelement.setAttributeNode(param);
					    	Attr row = doc.createAttribute("row"); 
					    	row.setValue("1");
					    	formelement.setAttributeNode(row);
					    	root.appendChild(formelement);
					    	
					    	ObjectOutputStream oos = null;
					    	oos = new ObjectOutputStream(new FileOutputStream(newXmlFile));
					    	oos.writeObject(doc);
					    	oos.flush();
					    	oos.close();
					    	System.out.println("更新"+filename+"新增隐藏域entityPrimaryKeys，值为"+primaryKeys);
					    }
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(e.getMessage());
					}
			}
		}

	}
//	public static void main(String[] args)  throws Exception{
//		updateForMainFuction();
//	}

}
