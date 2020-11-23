package  app.component.rec.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.rec.entity.MfCollectionTable;
import app.component.rec.feign.MfCollectionTableFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCollectionTableAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sun Mar 19 15:03:41 CST 2017
 **/
@Controller
@RequestMapping(value="/mfCollectionTable")
public class MfCollectionTableController extends BaseFormBean{
	//注入业务层
	@Autowired
	private MfCollectionTableFeign mfCollectionTableFeign;
	
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 获取列表数据
	 * @param mfCollectionTable 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, MfCollectionTable mfCollectionTable,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfCollectionTableFeign.getAll(mfCollectionTable), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formdlmfcollectiontable0002 = formService.getFormData("dlmfcollectiontable0002");
		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		Ipage ipage = this.getIpage();
		List<MfCollectionTable> mfCollectionTableList = (List<MfCollectionTable>)mfCollectionTableFeign.findByPage(ipage, mfCollectionTable).getResult();
		model.addAttribute("formdlmfcollectiontable0002", formdlmfcollectiontable0002);
		model.addAttribute("mfCollectionTableList", mfCollectionTableList);
		return "MfCollectionTable_List";
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formdlmfcollectiontable0002 = formService.getFormData("dlmfcollectiontable0002");
		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		List<MfCollectionTable> mfCollectionTableList = mfCollectionTableFeign.getAll(mfCollectionTable);
		model.addAttribute("formdlmfcollectiontable0002", formdlmfcollectiontable0002);
		model.addAttribute("mfCollectionTableList", mfCollectionTableList);
		return "MfCollectionTable_List";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		try{
			FormData formdlmfcollectiontable0002 = formService.getFormData("dlmfcollectiontable0002");
			getFormValue(formdlmfcollectiontable0002, getMapByJson(ajaxData));
			if(this.validateFormData(formdlmfcollectiontable0002)){
				MfCollectionTable mfCollectionTable = new MfCollectionTable();
				setObjValue(formdlmfcollectiontable0002, mfCollectionTable);
				mfCollectionTableFeign.insert(mfCollectionTable);
				getTableData(tableId,mfCollectionTable,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		try{
			FormData formdlmfcollectiontable0002 = formService.getFormData("dlmfcollectiontable0002");
			getFormValue(formdlmfcollectiontable0002, getMapByJson(ajaxData));
			if(this.validateFormData(formdlmfcollectiontable0002)){
				mfCollectionTable = new MfCollectionTable();
				setObjValue(formdlmfcollectiontable0002, mfCollectionTable);
				mfCollectionTableFeign.update(mfCollectionTable);
				getTableData(tableId,mfCollectionTable,dataMap);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String, Object> getByIdAjax(String tableName,String collectionNo) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		FormData formdlmfcollectiontable0002 = formService.getFormData("dlmfcollectiontable0002");
		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		mfCollectionTable.setTableName(tableName);
		mfCollectionTable.setCollectionNo(collectionNo);
		mfCollectionTable = mfCollectionTableFeign.getById(mfCollectionTable);
		getObjValue(formdlmfcollectiontable0002, mfCollectionTable,formData);
		if(mfCollectionTable!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String, Object> deleteAjax(String tableName,String collectionNo,String ajaxData,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>();  
		MfCollectionTable mfCollectionTable = new MfCollectionTable();
		mfCollectionTable.setTableName(tableName);
		mfCollectionTable.setCollectionNo(collectionNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCollectionTable = (MfCollectionTable)JSONObject.toBean(jb, MfCollectionTable.class);
			mfCollectionTableFeign.delete(mfCollectionTable);
			getTableData(tableId,mfCollectionTable,dataMap);//获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	
}
