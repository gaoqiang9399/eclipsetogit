package app.component.cus.change;

import app.component.change.entity.MfChangeInfoRecord;
import app.component.cus.cnaps.feign.MfCnapsBankInfoFeign;
import app.component.cus.feign.MfChangeInfoRecordFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCnapsBankInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 07 14:51:12 CST 2017
 **/
@Controller
@RequestMapping("/mfChangeInfoRecord")
public class MfChangeInfoRecordController extends BaseFormBean{
	@Autowired
	HttpServletRequest request;
	@Autowired  
	HttpServletResponse response;
	@Autowired
	private MfChangeInfoRecordFeign mfChangeInfoRecordFeign;
	/**
	 * 信息变更列表
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	/**
	 *
	 * @param cusNo 客户号
	 * @param appId 申请号
	 * @param pactId 合同号
	 * @param fincId 借据号
	 * @param nodeNo 指定节点，多个节点|分割
	 * @param busType cus 客户 app 融资详情 pact 合同 finc借据
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findChangeInfoRecordList")
	@ResponseBody
	public Map<String, Object> findChangeInfoRecordList(String cusNo,String appId,String pactId,String fincId,String nodeNo,String busType) throws Exception {
		Map<String , Object> dataMap = new HashMap<>();
		try {
			MfChangeInfoRecord mfChangeInfoRecord =new MfChangeInfoRecord();
			if(StringUtil.isEmpty(busType)||"cus".equals(busType)){
				mfChangeInfoRecord.setCusNo(cusNo);
			}else if("app".equals(busType)){
				mfChangeInfoRecord.setAppId(appId);
			}else if ("pact".equals(busType)){
				mfChangeInfoRecord.setPactId(pactId);
			}else if ("finc".equals(busType)){
				mfChangeInfoRecord.setFincId(fincId);
			}
			StringBuffer nodeList = new StringBuffer();
			if(StringUtil.isNotEmpty(nodeNo)){
				String[] nodes = nodeNo.split("\\|");
				if(nodes.length>0){
					for (int i = 0; i < nodes.length; i++) {
						nodeList.append("'").append(nodes[i]).append("',");
					}
					String nodeString = nodeList.toString();
					if(nodeString.length()>1){
						nodeString = nodeString.substring(0,nodeString.length()-1);
						mfChangeInfoRecord.setNodeNo("("+nodeString+")");
					}
				}
			}

			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfChangeInfoRecord", mfChangeInfoRecord));
			ipage =  mfChangeInfoRecordFeign.findChangeInfoRecordList(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tableCusChangeInfoList", "tableTag", (List) ipage.getResult(), ipage, true);
			dataMap.put("tableHtml",tableHtml);
			dataMap.put("flag","success");
		}catch (Exception e){
			dataMap.put("flag","error");
			dataMap.put("msg","无变更信息");
			throw e;
		}
		return dataMap;
	}


}
