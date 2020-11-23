package app.component.sys.entity;

import java.io.Serializable;

/**
 * 角色菜单关联实体类
 *
 * @author leopard mailto:haoxiaofeng@dhcc.com.cn
 * @date 2010-11-9
 * @see 
 * 修改记录:
 */
public class SysRoleMenu implements Serializable {
	
	private String menuNo;              //菜单编号
	private String menuName;            //菜单名称
	private String menuNrl;			//
	private String menuLvl;


	public SysRoleMenu() {
		this.menuName = "";
		this.menuNo = "";
	}


	public String getMenuNo() {
		return menuNo;
	}


	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public String getMenuNrl() {
		return menuNrl;
	}


	public void setMenuNrl(String menuNrl) {
		this.menuNrl = menuNrl;
	}


	public String getMenuLvl() {
		return menuLvl;
	}


	public void setMenuLvl(String menuLvl) {
		this.menuLvl = menuLvl;
	}

}
