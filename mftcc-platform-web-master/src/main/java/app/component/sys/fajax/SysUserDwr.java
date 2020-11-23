package app.component.sys.fajax;
import java.util.ArrayList;
import java.util.List;

import app.base.SpringUtil;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwCacheUtil;
import app.component.finance.ztbooks.entity.CwZtBooks;
import app.component.finance.ztbooks.feign.CwZtBooksFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;

public class SysUserDwr {
	
	public List<SysUser> checkExist(String val)throws Exception{
		List<SysUser> sysUserList =  new ArrayList<SysUser>();
		SysUser sysUser = new SysUser();
		sysUser.setMobile(val);
		sysUser.setOpNo(val);
		sysUser.setOpName(val);//登录不允许用户名登录
		SysUserFeign sysUserBo = (SysUserFeign) SpringUtil.getBean(SysUserFeign.class);
		try {
			 sysUserList = sysUserBo.getByMobileOrUser(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			sysUserList = null;
			throw e;
		}
		return sysUserList;
	}
	
	public List<CwZtBooks> getUserBooksList(String userNo ) throws Exception {
		CwZtBooks cwZtBooks = new CwZtBooks();
		List<CwZtBooks> cwZtBooksList = null; 
		try {
			//自定义查询Bo方法
			cwZtBooks.setOperator(userNo);//操作人员
			cwZtBooks.setUseFlag(CWEnumBean.YES_OR_NO.SHI.getNum());
			cwZtBooks.setDelSts(CWEnumBean.YES_OR_NO.FOU.getNum());
			CwZtBooksFeign cwZtBooksBo = (CwZtBooksFeign) SpringUtil.getBean(CwZtBooksFeign.class);
			cwZtBooksList = cwZtBooksBo.getCwZtBooksList(cwZtBooks);
			if(cwZtBooksList!=null&&cwZtBooksList.size()>0){
			
			}else{
				CwZtBooks cwBooks = new CwZtBooks();
				cwBooks.setFinBooks(CwCacheUtil.CW_BOOKS);
				cwZtBooksList = cwZtBooksBo.getCwZtBooksList(cwBooks);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return cwZtBooksList;
	}
}
