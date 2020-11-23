<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<link rel="stylesheet" href="component/tools/charge/cloudManager.css" type="text/css"></link>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#list-outer",//页面内容绑定的id
			url : webPath+"/accountPay/getServiceAjax",//列表数据查询的url
			tableId : "tableCM003",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			ownHeight : true,
			parentHeight:true,
			callback:function(){
			}
		});
		
	});
 function doOpenOrClose(obj){
	var strs= new Array(); //定义一数组
	var canshu= new Array(); //定义一数组
	strs=obj.split("?"); //字符分割
	var canshu = strs[1]+"";
	var cifAccount = "${manageDetil.cifAccount}";
 	var getPath = '';
	var allLocal = window.location;// 获取全部地址 
	var localStr = allLocal.toString();// 转换为字符串
	// 获取http:
	var loc = localStr.split("/"); // 转为字符串
	var local1 = loc[0];
	// 获取域名
	var loc2 = localStr.split("/"); // 转为字符串
	var realm_ = loc2.slice(2, 3);
	getPath = local1 + "//" + realm_;
	DIALOG.open(getPath   
			+'/factor/component/tools/charge/openService.jsp?'+canshu,window,
			'dialogHeight:200px;dialogWidth:450px;help：no;status:no;title:开通;',function(){
				if(top.dialog.getCurrent().returnValue.errorcode=="0000"){
					alert(top.getMessage("SUCCEED_SAVE"));
					myCustomScrollbar({
											obj : "#list-outer",//页面内容绑定的id
											url : webPath+"/accountPay/getServiceAjax",//列表数据查询的url
											tableId : "tableCM003",//列表数据查询的table编号
											tableType : "thirdTableTag",//table所需解析标签的种类
											pageSize : 30,//加载默认行数(不填为系统默认行数)
											ownHeight : true,
											parentHeight:true,
											callback:function(){
											}
										});
				}else{
					alert(  top.getMessage("FAILED_SAVE"));
				}
	});
} 
</script>
<title>开通服务</title>
</head>
<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<a class="btn btn-primary" href="${webPath}/accountPay/getDetilPage">返回</a>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
				</div>
			</div>
</div>
<div id="list-outer" class="table_content"></div>
<!-- <div id="open_div" class="kaitong_div" style="display: none;">
	<ul>
		<li>
			<label>短信后缀：</label>
			<input type="text" id="smsSuffix">
		</li>
		
	</ul>
</div> -->
</body>
</html>