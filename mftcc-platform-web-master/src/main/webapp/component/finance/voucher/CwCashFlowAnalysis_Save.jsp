<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
<title></title>
<style type="text/css">
	.search-addon{
				float: right;
				top: -24px;
			    right: 15px;
			}
</style>
</style>
</head>
<body>
<div class="row clearfix">
	<div class="col-md-12 column">
		<div id="content" class="table_content"  style="height: auto;">
			<dhcc:tableTag property="tablecashflowanalysissave0001" paginate="cwCashFlowAnalysisList" head="true"></dhcc:tableTag> 
		</div>
	</div>
</div>	
<div class="formRowCenter" >
	<!-- <input type="button" value="保存" onclick="myInsertAjax()"/>
	<input type="button" value="关闭" Class="myclose"/>   onclick="myclose_showDialog();"-->
	<dhcc:thirdButton value="保存" action="保存" onclick="myInsertAjax()"></dhcc:thirdButton>
	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" ></dhcc:thirdButton>
</div>
</body>
<script type="text/javascript" >
var voucherNo='${param.voucherNo}';
//查询条件的form表单的jquery
$(function(){
	$(".cancel").bind("click", function(event){//取消只移除弹框
		$(parent.document).find("#showDialog").remove();
	});
	$(".click_search").each(function(){
		$(this).bind("click",function(){
			getTreeCashType(this)
		})
	})
})
//获取现金流量项
function getTreeCashType(dom){
	openCashItemTreeDialog("002",function(data){
		if(data==''){
			return false;
		}
		var baseData=$(dom).find("input").attr("data");
		var baseObj = eval('(' + baseData + ')');
		var brfs=baseObj.brfsAmt;//基础数据
		var isInput=baseObj.isInput;
		$(dom).find("input").val(data.callName);
		if(data.isInput !=isInput){//相反则现金流量取相反值
			brfs=(-1)*brfs;
			brfs=changeTwoDecimal(brfs);
			$(dom).parents("tr").find("td:last").html(brfs)
		}else{
			$(dom).parents("tr").find("td:last").html(brfs)
		}
	});
// 	$(top.window.document).find("#showDialog").css("z-index","1066");
}
function myInsertAjax(){//点击保存
	LoadingAnimate.start();
	var arr = new Array();//创建数组
	var o=new Object();//创建对象
	o.voucherNo=voucherNo;
	var i=0;
	$("tbody tr:not(:last)").each(function(i,p){
		var item=new Object();
		var uid=$(this).find("td:first").find("input").val();
		item.uid=uid;
		var reportItem=$(this).find("td:eq(2)").find("input").val();
		item.reportItem=reportItem;
		var brfsAmt=$(this).find("td:last").text();
		item.brfsAmt=brfsAmt;
		arr[i]=item;
	});
	o.list=arr;
	$.ajax({
		url:webPath+'/cwVoucherMst/cashFlowAnalysisSaveAjax',
		data:'ajaxData='+JSON.stringify(o),
		dataType:'json',
		type:'post',
		success:function(data){
		LoadingAnimate.stop();
			if(data.flag=="success"){
 				alert(data.msg,1);
				//$('.myclose').click();
				//$(parent.document).find("#showDialog").remove();
// 				$(top.window.document).find("#showDialog .close").click();//触发父页面的回调函数
			}else{
				alert(data.msg,1)
			}
		},
		error:function(){
			LoadingAnimate.stop();
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	})
}
</script>
</html>
