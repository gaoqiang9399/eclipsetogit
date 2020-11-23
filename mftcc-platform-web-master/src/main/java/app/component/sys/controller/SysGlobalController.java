package app.component.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

import app.component.sys.entity.SysGlobal;
import app.component.sys.feign.SysGlobalFeign;
import cn.mftcc.util.CacheUtil;
import cn.mftcc.util.CacheUtil.CACHE_KEY;

/**
 * @author sampson
 *
 */
@Controller
@RequestMapping("/sysGlobal")
public class SysGlobalController extends BaseFormBean {
	  
	private static final long serialVersionUID = 4094026222278033798L;

	@Autowired
	//注入SysAreaBO
	private SysGlobalFeign sysGlobalFeign;
	
	
	@RequestMapping(value = "/getSysGlobalPage")
	public String getSysGlobalPage(Model model) throws Exception{
		SysGlobal sysGlobal = sysGlobalFeign.getSysGlobal();
		model.addAttribute("sysGlobal", sysGlobal);
		return "list";	
	}
	
	@RequestMapping(value = "/updateSts")
	public String updateSts(Model model,String sysSts) throws Exception{
		SysGlobal sysGlobal = new SysGlobal();
		sysGlobal.setSysSts(sysSts);
		try {
			sysGlobalFeign.updateSts(sysGlobal);
			if ("2".equals(sysSts)) {
				kickAllUserDown();//将所有用户踢下线
			}
			if("1".equals(sysSts)){
				this.addActionMessage(model,"系统启用成功,可以做正常业务操作!");
			}else if("2".equals(sysSts)){
				this.addActionMessage(model,"系统停用成功,可以开始日终处理!");
			}else {
			}
		} catch (Exception e) {
			this.addActionError(model,e.getMessage());
		}
		return getSysGlobalPage(model);	
	}
	/**
	 * 将所有用户踢下线
	 * @throws Exception 
	 */
	private void kickAllUserDown() throws Exception{
		CacheUtil.removeCache(CACHE_KEY.hUserName);
	}
	@RequestMapping(value = "/update")
	public String update(double doc_size,Model model) throws Exception{
		SysGlobal sysGlobal = new SysGlobal();
		sysGlobal.setDocSize(doc_size);
		sysGlobalFeign.updateDocSize(sysGlobal);
		this.addActionMessage(model,"保存成功!");
		return getSysGlobalPage(model);	
	}
	
}