<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%
	String contextPath = request.getContextPath();
%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<script type="text/javascript" src="${webPath}/component/cus/commonview/js/MfCusCustomer_ComView.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js"></script>
<script type="text/javascript">
	var cusNo='${param.cusNo}';
	var cusType='${param.cusType}';
	var busSubmitCnt = '';
	var pageView = '';
	$(function(){
		$.ajax({
				url:webPath+"/mfBusTrench/getCusTableListAjax?cusNo="+ cusNo+"&cusType="+cusType,
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
							var tmpDelCnt=0;//移除的个数
							$.each(data.cusTableList, function(i, cusTable) {
								// shouType:1是form2是table  tableDes：中文描述    action:对应路径的action  html：后台传过来的html字符串   dataFullFlag：是否填完信息
								//isMust:是否必填 tableName:对应表名  delFlag：表单标志是否显示
								setBlock1(cusTable.showType, cusTable.tableDes, cusTable.action,cusTable.htmlStr, cusTable.dataFullFlag, cusTable.isMust,cusTable.tableName, i % 4 + 1, cusTable.delFlag,cusTable.sort);
								dblclickflag();//单子段编辑事件
								if(cusTable.delFlag=="1"){
									tmpDelCnt++;
								}
							});
					}
				},error : function() {}
			});
	})
	function selectOpNos(inputName,callback){
		selectUserCustomTitleDialog("选择业务员?"+$("[name="+inputName+"]").val(),function(userInfo){
    			callback(userInfo);
    		});
	}
	function trenchCallBack(userInfo){
		var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
		//提交修改的关联业务员
		var ajaxData=[{"name":"trenchOpNo","value":users},{"name":"trenchUid","value":$("[name=trenchUid]").val()}];
		$.ajax({
			url:webPath+"/mfBusTrench/updateAjaxByOne",
			data:{ajaxData:JSON.stringify(ajaxData)},
			dataType:'json',
			type:'post'
		})
		$("[name=trenchOpName]").val(userInfo.brName);
		$("[name=trenchOpNo]").val(users);
		$("body").click();
		$("[name=trenchOpName]").parent().prev().text(userInfo.brName);
	}
	function agenciesCallBack(userInfo){
		var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
		//提交修改的关联业务员
		var ajaxData=[{"name":"agenciesOpNo","value":users},{"name":"agenciesUid","value":$("[name=agenciesUid]").val()}];
		$.ajax({
			url:webPath+"/mfBusAgencies/updateAjaxByOne",
			data:{ajaxData:JSON.stringify(ajaxData)},
			dataType:'json',
			type:'post'
		})
		$("[name=agenciesOpName]").val(userInfo.brName);
		$("[name=agenciesOpNo]").val(users);
		$("body").click();
		$("[name=agenciesOpName]").parent().prev().text(userInfo.brName);
	}
</script>
<div class="row clearfix">
	<div class="col-xs-12 column info-block">
		<div class="block-add" style="display: none;"></div>
	</div>
</div>
