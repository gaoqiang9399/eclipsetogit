package app.component.nmd.entity;
      
public class TblFormDef implements java.io.Serializable {
public TblFormDef(){}
//变量定义
private String process_id;           //流程id
private String node_id;              //节点id
private String menu_url;             //菜单URL
private String menu_name;            //菜单名称
private String actt;
private String name1;

//set方法
public void setProcess_id(String process_id){ this.process_id=process_id;} 
public void setNode_id(String node_id){ this.node_id=node_id;} 
public void setMenu_url(String menu_url){ this.menu_url=menu_url;} 
public void setMenu_name(String menu_name){ this.menu_name=menu_name;} 
//get方法
public String getProcess_id(){return process_id;} 
public String getNode_id(){return node_id;} 
public String getMenu_url(){return menu_url;} 
public String getMenu_name(){return menu_name;}
public String getActt() {
	return actt;
}
public void setActt(String actt) {
	this.actt = actt;
}
public String getName1() {
	return name1;
}
public void setName1(String name1) {
	this.name1 = name1;
} 
}
