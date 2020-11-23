package app.component.sys.fajax;

import java.util.List;

import app.base.ServiceException;
import app.base.SourceTemplate;
import app.base.SpringUtil;
import app.component.sys.entity.SysOrg;
import app.component.sys.feign.SysOrgFeign;

public class SysOrgDwr {
	
	public SysOrg searchSysOrg(String brNo)throws ServiceException{
		SysOrg sysOrg = new SysOrg();
		sysOrg.setBrNo(brNo);
		SysOrgFeign sysOrgBo = (SysOrgFeign) SourceTemplate.getSpringContextInstance().getBean(SysOrgFeign.class);
		try {
			sysOrg = sysOrgBo.getById(sysOrg);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return sysOrg;
	}
	
	public String checkDownOne(String brNo){
		String result = "";
		List<SysOrg> sysOrgList = null;
		try{
			SysOrgFeign sysOrgDao = (SysOrgFeign)SpringUtil.getBean(SysOrgFeign.class);
			sysOrgList = sysOrgDao.getByUpOne(brNo);
			if(sysOrgList.size()>0){
				result = "有下级节点，不能删除";
			}else{
				result = "true";
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		return result;
	}
}
