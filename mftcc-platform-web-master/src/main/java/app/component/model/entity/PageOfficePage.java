package app.component.model.entity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.FileMakerCtrl;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.ThemeType;
import com.zhuozhengsoft.pageoffice.DocumentOpenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import app.base.SpringUtil;
import app.component.model.controller.MfTemplateBizConfigController;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;

//页面设置
public class PageOfficePage {
	private static Logger logger = LoggerFactory.getLogger(PageOfficePage.class);
	// pageOffice类
	private PageOfficeCtrl poCtrl;
	private PDFCtrl pdfCtrl;
    private FileMakerCtrl fmCtrl;
	private PageContent poCnt;

	public PageOfficePage() {
		super();
	}

	public PageOfficePage(PageContent poCnt, HttpServletRequest request) {
		super();
		String realPathFileName="file://"+poCnt.getPathFileName();//支持linux磁盘路径
		poCnt.setPathFileName(realPathFileName);//转换成磁盘路径
		poCtrl = new PageOfficeCtrl(request);
		//需要在web.xml中配置
		YmlConfig ymlConfig = SpringUtil.getBean(YmlConfig.class);
		String webPath = ymlConfig.getWebservice().get("webPath");
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		if(webPath.startsWith("/")){
			webPath = webPath.substring(1);
		}
		webPath = basePath+"/"+webPath;
		poCtrl.setServerPage(webPath + "/poserver.do");// 此行必须
		String caption = poCnt.getCaption();
		// 根据标题内容设置标题栏
		if (null == caption || "".equals(poCnt.getCaption())) {// 没有标题内容
			poCtrl.setTitlebar(false);// 无标题栏
		} else {
			poCtrl.setTitlebar(true);// 有标题栏
			poCtrl.setCaption(caption);// 标题内容
		}
		// 必选设置
		poCtrl.setMenubar("1".equals(poCnt.getMenuBar()));// 菜单栏
		poCtrl.setCustomToolbar("1".equals(poCnt.getCustomBar()));
		// 默认设定 没有工具栏
		poCtrl.setOfficeToolbars("1".equals(poCnt.getToolBar()));// 工具栏 编辑有 详情置灰
		// 设置文件打开后执行的js function
		poCtrl.setJsFunction_AfterDocumentOpened("afterDocumentOpened()");

		// poCtrl.getRibbonBar().setTabVisible("TabHome",false);//实现隐藏Ribbon栏中的“开始”命令分组.（"TabHome"为该命令分组的名称,false为隐藏，true为显示）
		poCtrl.getRibbonBar().setSharedVisible("FileSave", false);// 实现隐藏Ribbon快速工具栏中的“保存”按钮.（"FileSave"为按钮的名称,false为隐藏，true为显示）
		// poCtrl.getRibbonBar().setGroupVisible("GroupClipboard",
		// false);//实现隐藏“开始”命令分组中的“剪切板”面板.（"GroupClipboard"为该面板的名称,false为隐藏，true为显示）
		try{
			poCtrl.setTagId("PageOfficeCtrl"); // 此行必须
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if ("0".equals(poCnt.getFileType())) {// 自动匹配
			String fname = poCnt.getPathFileName().toLowerCase();
			if (fname.endsWith(".xls") || fname.endsWith(".xlsx")) {
				poCnt.setFileType("2");// 2excel
			} else if (fname.endsWith(".ppt") || fname.endsWith(".pptx")) {
				poCnt.setFileType("3");// 3ppt
			} else {
				poCnt.setFileType("1");// 默认是doc
			}
		}
		String autoFullScreen = poCnt.getAutoFullScreen();
		if (autoFullScreen == null) {
            poCnt.setAutoFullScreen("1");// 默认自动全屏
        }
	}

	/**
	 * 描述：pdf 的构造方法
	 * @param poCnt
	 * @param request
	 * @param pdfCtrl
	 */
	public PageOfficePage(PageContent poCnt, HttpServletRequest request,PDFCtrl pdfCtrl) { 
		super();
		this.pdfCtrl = pdfCtrl;
		String realPathFileName="file://"+poCnt.getPathFileName();//支持linux磁盘路径
		poCnt.setPathFileName(realPathFileName);//转换成磁盘路径
		pdfCtrl.setServerPage(request.getContextPath()+"/poserver.zz"); //设置服务器页面
		pdfCtrl.setTheme(ThemeType.CustomStyle);//设置主题样式

		//添加自定义按钮
		pdfCtrl.addCustomToolButton("打印", "print()", 6);
		pdfCtrl.addCustomToolButton("-", "", 0);
		pdfCtrl.addCustomToolButton("实际大小", "setPageReal()", 16);
		pdfCtrl.addCustomToolButton("适合页面", "setPageFit()", 17);
		pdfCtrl.addCustomToolButton("适合宽度", "setPageWidth()", 18);
		pdfCtrl.addCustomToolButton("-", "", 0);
		pdfCtrl.addCustomToolButton("首页", "firstPage()", 8);
		pdfCtrl.addCustomToolButton("上一页", "previousPage()", 9);
		pdfCtrl.addCustomToolButton("下一页", "nextPage()", 10);
		pdfCtrl.addCustomToolButton("尾页", "lastPage()", 11);
		pdfCtrl.addCustomToolButton("-", "", 0);
		pdfCtrl.addCustomToolButton("左转", "rotateLeft()", 12);
		pdfCtrl.addCustomToolButton("右转", "rotateRight()", 13);
		pdfCtrl.addCustomToolButton("-", "", 0);
		pdfCtrl.addCustomToolButton("放大", "zoomIn()", 14);
		pdfCtrl.addCustomToolButton("缩小", "zoomOut()", 15);
		pdfCtrl.addCustomToolButton("-", "", 0);
		pdfCtrl.addCustomToolButton("全屏", "switchFullScreen()", 4);
		//设置禁止拷贝
		pdfCtrl.setAllowCopy(false);

		String fileName = poCnt.getPathFileName();//定义文件名称
		
		fileName = getOpenFilePath(fileName);
		pdfCtrl.webOpen(fileName);//打开文件
		pdfCtrl.setTagId("PDFCtrl");//此行必须
	}

	/**
    *@desc:pageoffice后台运行构造方法
    *@author lwq
    *@date 2018/7/21 11:33
    *@parm [poCnt, request, fileMakerCtrl]
    *@return
    **/
    public PageOfficePage(PageContent poCnt, HttpServletRequest request,FileMakerCtrl fmCtrl) {
        super();
        this.fmCtrl = fmCtrl;
        String realPathFileName="file://"+poCnt.getPathFileName();//支持linux磁盘路径
        poCnt.setPathFileName(realPathFileName);//转换成磁盘路径
        fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz"); //设置服务器页面
        String localFilePath = poCnt.getPathFileName();//定义文件名称
        String openFilePath = getOpenFilePath(localFilePath);
        fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");//文档保存之后调用js函
        fmCtrl.fillDocumentAsPDF(openFilePath, DocumentOpenType.Word, "lwq.pdf");
    }

	/**
	 * 
	 * 方法描述： 获取PDF的PageOfficePage对象
	 * @param poCnt
	 * @param request
	 * @return
	 * PageOfficePage
	 * @author lwq
	 * @date 2017-12-1 下午2:00:14
	 */
	public static PageOfficePage getPdfPO(PageContent poCnt,
			HttpServletRequest request) {
		PDFCtrl pdfCtrl = new PDFCtrl(request);
		PageOfficePage poPage = new PageOfficePage(poCnt, request, pdfCtrl);
		return poPage;
	}
	
	/**
	 * 
	 * @Description: 得到只读类文档对象
	 * @param poCnt
	 * @param request
	 * @return
	 * @return PageOfficePage
	 * @throws
	 * @author pgq
	 * @date 2015-11-13
	 */
	public static PageOfficePage getDetailPO(PageContent poCnt,
			HttpServletRequest request) {
		// 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
		PageOfficePage poPage = new PageOfficePage(poCnt, request);
		PageOfficeCtrl poCtrl = poPage.getPoCtrl();
		OpenModeType modeType = OpenModeType.docReadOnly;
		switch (Integer.parseInt(poCnt.getFileType())) {
		case 1:
			modeType = OpenModeType.docReadOnly;
			break;
		case 2:
			modeType = OpenModeType.xlsReadOnly;
			break;
		case 3:
			modeType = OpenModeType.pptReadOnly;
			break;
			default:
		}
		poCtrl.setOfficeToolbars(false);// 无工具栏
		poPage.setPoCnt(poCnt);
		lastPoSet(poCtrl, poCnt, modeType);
		return poPage;
	}

	/**
	 * 
	 * @Description: 得到可编辑PageOfficePage对象
	 * @param poCnt
	 * @param request
	 * @return
	 * @return PageOfficePage
	 * @throws
	 * @author pgq
	 * @date 2015-11-13
	 */
	public static PageOfficePage getEditPO(PageContent poCnt,HttpServletRequest request) {
		Logger logger = LoggerFactory.getLogger(PageOfficePage.class);
		// 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
		PageOfficePage poPage = new PageOfficePage(poCnt, request);
		PageOfficeCtrl poCtrl = poPage.getPoCtrl();
		OpenModeType modeType = OpenModeType.docNormalEdit;
		switch (Integer.parseInt(poCnt.getFileType())) {
		case 1:
			if ("1".equals(poCnt.getReadOnly())){
				
				modeType = OpenModeType.docReadOnly;
			} else {
				modeType = OpenModeType.docNormalEdit;
			}
			break;
		case 2:
			if ("1".equals(poCnt.getReadOnly())){
				
				modeType = OpenModeType.xlsReadOnly;
			}else{
				modeType = OpenModeType.xlsNormalEdit;
			}
			break;
		case 3:
			if ("1".equals(poCnt.getReadOnly())){
				modeType = OpenModeType.pptReadOnly;
			}else{
				modeType = OpenModeType.pptNormalEdit;
			}
			break;
			default:
		}

		if ("1".equals(poCnt.getSaveBtn())) {
			String saveFunc = poCnt.getSaveFunc();// 保存JS函数名
			logger.error("**********************************保存函数名saveFunc="+saveFunc);
			poCtrl.addCustomToolButton("保存", saveFunc == null ? "savefile"	: saveFunc, 1);
			//处理反斜杠
			String savePath = poCnt.getSavePath();
			savePath = savePath.replaceAll("\\\\", "/");
			// 设置保存页面
			poCtrl.setSaveFilePage("/pageoffice/pageOffice/poSaveFile.jsp?savePath="+ savePath+ "&saveFileName="	+ poCnt.getSaveFileName());
		}
		if (poCnt.getMarkUrl() != null) {
			poCtrl.addCustomToolButton("书签", "bookMark", 20);
		}

		poPage.setPoCnt(poCnt);
		lastPoSet(poCtrl, poCnt, modeType);
		return poPage;
	}

	/**
	 * 
	 * @Description: 得到新增PageOfficePage对象
	 * @param poCnt
	 * @param request
	 * @return
	 * @return PageOfficePage
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author pgq
	 * @date 2015-11-16
	 */
	public static PageOfficePage getAddPO(PageContent poCnt,
			HttpServletRequest request) throws UnsupportedEncodingException {
		// 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
		PageOfficePage poPage = new PageOfficePage(poCnt, request);
		OpenModeType modeType = OpenModeType.docNormalEdit;
		PageOfficeCtrl poCtrl = poPage.getPoCtrl();
		switch (Integer.parseInt(poCnt.getFileType())) {
		case 1://word类型
			if ("1".equals(poCnt.getReadOnly())) {
                modeType = OpenModeType.docReadOnly;
            } else {
                modeType = OpenModeType.docNormalEdit;
            }
			// 将内容设置到poCtrl中
			poCtrl.setWriter(poCnt.getWordDoc());
			break;
		case 2://excel类型
			if ("1".equals(poCnt.getReadOnly())) {
                modeType = OpenModeType.xlsReadOnly;
            } else {
                modeType = OpenModeType.xlsNormalEdit;
            }
			// 将内容设置到poCtrl中
			poCtrl.setWriter(poCnt.getExcel());
			break;
		case 3://pdf类型
			if ("1".equals(poCnt.getReadOnly())) {
                modeType = OpenModeType.pptReadOnly;
            } else {
                modeType = OpenModeType.pptNormalEdit;
            }
			break;
			default:
		}

		// 非只读才考虑加上保存按钮
		if ("0".equals(poCnt.getReadOnly())) {
			// 若设置则加上保存
			if ("1".equals(poCnt.getSaveBtn())) {
				String saveFunc = poCnt.getSaveFunc();// 保存JS函数名
				if(StringUtil.isEmpty(saveFunc)){
					saveFunc= "savefile";
				}
				poCtrl.addCustomToolButton("保存", saveFunc, 1);
				// 设置保存页面
				poCtrl.setSaveFilePage("/pageoffice/pageOffice/poSaveFile.jsp?savePath="
						+ poCnt.getSavePath()
						+ "&saveFileName="
						+ poCnt.getSaveFileName());
			}
		}
		poPage.setPoCnt(poCnt);
		lastPoSet(poCtrl, poCnt, modeType);
		return poPage;
	}

	/**
	 * 
	 * @Description: 最后设置
	 * @param poCtrl
	 * @param poCnt
	 * @param modeType
	 * @return void
	 * @throws
	 * @author pgq
	 * @date 2015-11-21
	 */
	private static void lastPoSet(PageOfficeCtrl poCtrl, PageContent poCnt,
			OpenModeType modeType) {
		// 设定按钮
		if ("1".equals(poCnt.getPrintBtn())) {
            poCtrl.addCustomToolButton("打印", "poPrint()", 6);
        }
		if ("1".equals(poCnt.getCloseBtn())) {
            poCtrl.addCustomToolButton("关闭", "poFactorClose()", 13);
        }
		if ("1".equals(poCnt.getZoomBtn())) {
            poCtrl.addCustomToolButton(" ", "fullScreen", 4);// 全屏按钮默认有
        }
		String localFilePath =poCnt.getPathFileName();
        String openFilePath = getOpenFilePath(localFilePath);
        poCtrl.webOpen(openFilePath, modeType, "北京微金");
        poCtrl.setTagId("PageOfficeCtrl");//此行必须且放最后
	}

	/**
	*@desc :根据文件传过来的判断文件是本地文件还是来自服务器的文件
	*@author lwq        
	*@date 2018/7/21 16:15
	*@parm [localFilePath]
	*@return java.lang.String
	**/
	public static String getOpenFilePath(String localFilePath){
        String filePath = "";
        String orignalPath = localFilePath;
	    if(localFilePath.startsWith("file:")){
            localFilePath =localFilePath.substring(5);
        }
        File localFile = new File(localFilePath);

        if(localFile.exists()){
            filePath = orignalPath;
        }else{
            YmlConfig ymlConfig = SpringUtil.getBean(YmlConfig.class);
            String webPath = ymlConfig.getWebservice().get("webPath");
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String requestUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
            try {
                requestUrl += webPath +"/docUpLoad/getFileByFilePath?filePath="+URLEncoder.encode(localFilePath, "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("文件路径地址url编码出错：",e);
            }
            filePath = requestUrl;
        }
        return filePath;

    }

	/**
	*@desc 得到一个后台的运行的PageOfficePage对象
	*@author lwq        
	*@date 2018/7/21 10:46
	*@parm [poCnt, request]
	*@return app.component.model.entity.PageOfficePage
	**/
    public static PageOfficePage getFileMakeAddPO(PageContent poCnt,
                                          HttpServletRequest request) throws UnsupportedEncodingException {
        // 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
        FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
        PageOfficePage poPage = new PageOfficePage(poCnt, request,fmCtrl);
        OpenModeType modeType = OpenModeType.docNormalEdit;
        switch (Integer.parseInt(poCnt.getFileType())) {
            case 1://word类型
                if ("1".equals(poCnt.getReadOnly())) {
                    modeType = OpenModeType.docReadOnly;
                } else {
                    modeType = OpenModeType.docNormalEdit;
                }
                // 将内容设置到poCtrl中
                fmCtrl.setWriter(poCnt.getWordDoc());
                break;
            case 2://excel类型
                if ("1".equals(poCnt.getReadOnly())) {
                    modeType = OpenModeType.xlsReadOnly;
                } else {
                    modeType = OpenModeType.xlsNormalEdit;
                }
                // 将内容设置到poCtrl中
                fmCtrl.setWriter(poCnt.getExcel());
                break;
            case 3://pdf类型
                if ("1".equals(poCnt.getReadOnly())) {
                    modeType = OpenModeType.pptReadOnly;
                } else {
                    modeType = OpenModeType.pptNormalEdit;
                }
                break;
                default:
        }

        // 设置保存页面
        fmCtrl.setSaveFilePage("/pageoffice/pageOffice/poSaveFile.jsp?savePath="
                + poCnt.getSavePath()
                + "&saveFileName="
                + poCnt.getSaveFileName());
        poPage.setPoCnt(poCnt);
        return poPage;
    }

	public PageOfficeCtrl getPoCtrl() {
		return poCtrl;
	}

	public void setPoCtrl(PageOfficeCtrl poCtrl) {
		this.poCtrl = poCtrl;
	}

	public PageContent getPoCnt() {
		return poCnt;
	}

	public void setPoCnt(PageContent poCnt) {
		this.poCnt = poCnt;
	}

	public PDFCtrl getPdfCtrl() {
		return pdfCtrl;
	}

	public void setPdfCtrl(PDFCtrl pdfCtrl) {
		this.pdfCtrl = pdfCtrl;
	}

    public FileMakerCtrl getFmCtrl() {
        return fmCtrl;
    }

    public void setFmCtrl(FileMakerCtrl fmCtrl) {
        this.fmCtrl = fmCtrl;
    }
}