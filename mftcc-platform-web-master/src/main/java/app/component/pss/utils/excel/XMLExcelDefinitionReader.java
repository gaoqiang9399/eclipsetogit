package app.component.pss.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLExcelDefinitionReader implements ExcelDefinitionReader{

	/** 注册信息 */
	private Map<String, ExcelDefinition> registry;

	/** 配置文件路径 */
	private String locations;
	
	private static XMLExcelDefinitionReader xmlExcelDefinitionReader = null;
	
	public static XMLExcelDefinitionReader getInstance(String locations) throws Exception{
		if(xmlExcelDefinitionReader == null){
			synchronized(XMLExcelDefinitionReader.class){
				if(xmlExcelDefinitionReader == null){
					xmlExcelDefinitionReader = new XMLExcelDefinitionReader(locations);
				}
			}
		}
		return xmlExcelDefinitionReader;
	}

	/**
	 * 
	 * @param location xml配置路径
	 * @throws Exception
	 */
	private XMLExcelDefinitionReader(String locations) throws Exception {
		if(StringUtils.isBlank(locations)){
			throw new IllegalArgumentException("locations 不能为空");
		}
		this.locations = locations;
		registry = new HashMap<String, ExcelDefinition>();
		String[] locationArr = StringUtils.split(locations, ",");
		for (String location:locationArr) {
			InputStream fis = null;
			File file = new File(location);
			fis = new FileInputStream(file);
			loadExcelDefinitions(fis);
		}
	}

	/**
	 * 加载解析配置文件信息
	 * @param inputStream
	 * @throws Exception
	 */
	protected void loadExcelDefinitions(InputStream inputStream) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(inputStream);
		registerExcelDefinitions(doc);
		inputStream.close();
	}

	/**
	 * 注册Excel定义信息
	 * @param doc
	 * @throws Exception 
	 */
	protected void registerExcelDefinitions(Document doc) throws Exception {
		Element root = doc.getDocumentElement();
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processExcelDefinition(ele);
			}
		}
	}

	/**
	 * 解析和校验Excel定义
	 * @param ele
	 * @throws Exception 
	 */
	protected void processExcelDefinition(Element ele) throws Exception {
		ExcelDefinition excelDefinition = new ExcelDefinition();
		String id = ele.getAttribute("id");
		if (registry.containsKey(id)) {
			throw new Exception("Excel 配置文件[" + locations + "] , id为 [" + id + "] 的不止一个");
		}
		excelDefinition.setId(id);
		String className = ele.getAttribute("class");
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new Exception("Excel 配置文件[" + locations + "] , id为 [" + id + "] 的 class 为 [" + className + "] 类不存在 ");
		}
		excelDefinition.setClassName(className);
		excelDefinition.setClazz(clazz);
		if(StringUtils.isNotBlank(ele.getAttribute("defaultColumnWidth"))){
			try{
				excelDefinition.setDefaultColumnWidth(Integer.parseInt(ele.getAttribute("defaultColumnWidth")));
			}catch(NumberFormatException e){
				throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
						+ " ] 的 defaultColumnWidth 属性不能为 [ "+ele.getAttribute("defaultColumnWidth")+" ],只能为int类型");
			}
		}
		if(StringUtils.isNotBlank(ele.getAttribute("sheetname"))){
			excelDefinition.setSheetname(ele.getAttribute("sheetname"));
		}
		if(StringUtils.isNotBlank(ele.getAttribute("enableStyle"))){
			excelDefinition.setEnableStyle(Boolean.parseBoolean(ele.getAttribute("enableStyle")));
		}
		//默认对齐方式
		/*String defaultAlign = ele.getAttribute("defaultAlign");
		if(StringUtils.isNotBlank(defaultAlign)){
			try{
				//获取cell对齐方式的常量值
				short constValue = ReflectUtil.getConstValue(CellStyle.class, "ALIGN_"+defaultAlign.toUpperCase());
				excelDefinition.setDefaultAlign(constValue);
			}catch(Exception e){
				throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
						+ " ] 的 defaultAlign 属性不能为 [ "+defaultAlign+" ],目前支持的left,center,right");
			}
		}*/
		//Sheet Index
		if(StringUtils.isNotBlank(ele.getAttribute("sheetIndex"))){
			try{
				int sheetIndex = Integer.parseInt(ele.getAttribute("sheetIndex"));
				if(sheetIndex<0){
					throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
							+ " ] 的 sheetIndex 属性不能为 [ "+ele.getAttribute("sheetIndex")+" ],索引位置至少从0开始");
				}
				excelDefinition.setSheetIndex(sheetIndex);
			}catch(NumberFormatException e){
				throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
						+ " ] 的 sheetIndex 属性不能为 [ "+ele.getAttribute("sheetIndex")+" ],只能为int类型");
			}
		}
		processField(ele, excelDefinition);
		registry.put(id, excelDefinition);
	}

	/**
	 * 解析和校验Field属性定义
	 * @param ele
	 * @param excelDefinition
	 * @throws Exception 
	 */
	protected void processField(Element ele, ExcelDefinition excelDefinition) throws Exception {
		NodeList fieldNode = ele.getElementsByTagName("field");
		if (fieldNode != null) {
			for (int i = 0; i < fieldNode.getLength(); i++) {
				Node node = fieldNode.item(i);
				if (node instanceof Element) {
					FieldValue fieldValue = new FieldValue();
					Element fieldEle = (Element) node;
					
					//子类信息
					String type = fieldEle.getAttribute("type");
					if(StringUtils.isNotBlank(type) && "children".equals(type)){
						NodeList fieldChildNode = fieldEle.getElementsByTagName("filedchild");
						if(fieldChildNode != null){
							for(int c = 0; c < fieldChildNode.getLength(); c++){
								Node childNode = fieldChildNode.item(c);
								if(childNode instanceof Element){
									FieldChildValue fieldChildValue = new FieldChildValue();
									Element fieldChildEle = (Element) childNode;
									String name = fieldChildEle.getAttribute("name");
									fieldChildValue.setName(name);
									String title = fieldChildEle.getAttribute("title");
									fieldChildValue.setTitle(title);
									String isNull = fieldChildEle.getAttribute("isNull");
									if(StringUtils.isNotBlank(isNull)){
										fieldChildValue.setNull(Boolean.parseBoolean(isNull));
									}
									
									fieldValue.getFieldChildValues().add(fieldChildValue);
								}
							}
							excelDefinition.getFieldValues().add(fieldValue);
						}
						continue;
					}
					
					String name = fieldEle.getAttribute("name");
					fieldValue.setName(name);
					String title = fieldEle.getAttribute("title");
					fieldValue.setTitle(title);
					String pattern = fieldEle.getAttribute("pattern");
					if(StringUtils.isNotBlank(pattern)){
						fieldValue.setPattern(pattern);
					}
					String format = fieldEle.getAttribute("format");
					if(StringUtils.isNotBlank(format)){
						fieldValue.setFormat(format);;
					}
					String isNull = fieldEle.getAttribute("isNull");
					if(StringUtils.isNotBlank(isNull)){
						fieldValue.setNull(Boolean.parseBoolean(isNull));
					}
					String regex = fieldEle.getAttribute("regex");
					if(StringUtils.isNotBlank(regex)){
						fieldValue.setRegex(regex);
					}
					String regexErrMsg = fieldEle.getAttribute("regexErrMsg");
					if(StringUtils.isNotBlank(regexErrMsg)){
						fieldValue.setRegexErrMsg(regexErrMsg);
					}
					//对齐方式
					/*String align = fieldEle.getAttribute("align");
					if(StringUtils.isNotBlank(align)){
						try{
							//获取cell对齐方式的常量值
							short constValue = ReflectUtil.getConstValue(CellStyle.class, "ALIGN_"+align.toUpperCase());
							fieldValue.setAlign(constValue);
						}catch(Exception e){
							throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
									+ " ] 的 align 属性不能为 [ "+align+" ],目前支持的left,center,right");
						}
					}*/
					//cell 宽度
					String columnWidth = fieldEle.getAttribute("columnWidth");
					if(StringUtils.isNotBlank(columnWidth)){
						try{
							int intVal = Integer.parseInt(columnWidth);
							fieldValue.setColumnWidth(intVal);
						}catch(NumberFormatException e){
							throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
									+ " ] 的 columnWidth 属性 [ "+columnWidth+" ] 不是一个合法的数值");
						}
					}
					//cell标题背景色
					/*String titleBgColor = fieldEle.getAttribute("titleBgColor");
					if(StringUtils.isNotBlank(titleBgColor)){
						try{
							//获取cell对齐方式的常量值
							IndexedColors color = ReflectUtil.getConstValue(IndexedColors.class,titleBgColor.toUpperCase());
							fieldValue.setTitleBgColor(color.index);
						}catch(Exception e){
							throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
									+ " ] 的 titleBgColor 属性不能为 [ "+titleBgColor+" ],具体看[org.apache.poi.ss.usermodel.IndexedColors]支持的颜色");
						}
					}*/
					//cell标题字体颜色
					/*String titleFountColor = fieldEle.getAttribute("titleFountColor");
					if(StringUtils.isNotBlank(titleFountColor)){
						try{
							//获取cell对齐方式的常量值
							IndexedColors color = ReflectUtil.getConstValue(IndexedColors.class,titleFountColor.toUpperCase());
							if(color==null){
								throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
										+ " ] 的 titleFountColor 属性不能为 [ "+titleFountColor+" ],具体看[org.apache.poi.ss.usermodel.IndexedColors]支持的颜色");
							}
							fieldValue.setTitleFountColor(color.index);
						}catch(Exception e){
							throw new Exception(e);
						}
					}*/
					//cell 样式是否与标题样式一致
					String uniformStyle = fieldEle.getAttribute("uniformStyle");
					if(StringUtils.isNotBlank(uniformStyle)){
						fieldValue.setUniformStyle(Boolean.parseBoolean(uniformStyle));
					}

					//解析自定义转换器
					/*String cellValueConverterName = fieldEle.getAttribute("cellValueConverter");
					if(StringUtils.isNotBlank(cellValueConverterName)){
						try {
							Class<?> clazz = Class.forName(cellValueConverterName);
							if(!CellValueConverter.class.isAssignableFrom(clazz)){
								throw new Exception("配置的："+cellValueConverterName+"错误,不是一个标准的["+CellValueConverter.class.getName()+"]实现");
							}
							fieldValue.setCellValueConverterName(cellValueConverterName);
						} catch (ClassNotFoundException e) {
							throw new Exception("无法找到定义的解析器：["+cellValueConverterName+"]"+"请检查配置信息");
						}
					}*/

					//roundingMode 解析
					/*String roundingMode = fieldEle.getAttribute("roundingMode");
					if(StringUtils.isNotBlank(roundingMode)){
						try{
							//获取roundingMode常量值
							RoundingMode mode =  ReflectUtil.getConstValue(RoundingMode.class,roundingMode.toUpperCase());
							if(mode == null){
								throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
										+ " ] 的 roundingMode 属性不能为 [ "+roundingMode+" ],具体看[java.math.RoundingMode]支持的值");
							}
							fieldValue.setRoundingMode(mode);
						}catch(Exception e){
							throw new Exception(e);
						}
					}*/

					//解析decimalFormat
					String decimalFormatPattern = fieldEle.getAttribute("decimalFormatPattern");
					if(StringUtils.isNotBlank(decimalFormatPattern)){
						try{
							fieldValue.setDecimalFormatPattern(decimalFormatPattern);
							fieldValue.setDecimalFormat(new DecimalFormat(decimalFormatPattern));
							fieldValue.getDecimalFormat().setRoundingMode(fieldValue.getRoundingMode());
						}catch(Exception e){
							throw new Exception("Excel 配置文件[" + locations + "] , id为 [ " + excelDefinition.getId()
									+ " ] 的 decimalFormatPattern 属性不能为 [ "+decimalFormatPattern+" ],请配置标准的JAVA格式");
						}
					}

					//解析其他配置项
					String otherConfig = fieldEle.getAttribute("otherConfig");
					if(StringUtils.isNotBlank(otherConfig)){
						fieldValue.setOtherConfig(otherConfig);
					}

					//解析,值为空时,字段的默认值
					String defaultValue = fieldEle.getAttribute("defaultValue");
					if(StringUtils.isNotBlank(defaultValue)){
						fieldValue.setDefaultValue(defaultValue);
					}

					excelDefinition.getFieldValues().add(fieldValue);
				}
			}
		}
	}

	/**
	 * @return 不可被修改的注册信息
	 */
	@Override
	public Map<String, ExcelDefinition> getRegistry() {
		return Collections.unmodifiableMap(registry);
	}

	public String getLocations() {
		return locations;
	}
}
