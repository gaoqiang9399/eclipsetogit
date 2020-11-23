<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%
	String contextPath = request.getContextPath();
%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<script type="text/javascript">
	var cusNo='${param.cusNo}';
	var cusType='${param.cusType}';
	var cusTypeTmp = cusType;
	var busSubmitCnt = '';
	var pageView = '';
	$(function(){
		// 渠道商，资金机构，核心企业等都走这个方法
		$.ajax({
				url:webPath+"/mfBusTrench/getCusTableListAjax?cusNo="+ cusNo+"&cusType="+cusTypeTmp,
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
		var userNames=userInfo.brName.replace(new RegExp(/(;)/g),'|');
		//提交修改的关联业务员
		var ajaxData=[{"name":"trenchOpNo","value":users},{"name":"trenchOpName","value":userNames},{"name":"trenchUid","value":$("[name=trenchUid]").val()}];
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
		var userNames=userInfo.brName.replace(new RegExp(/(;)/g),'|');
		//提交修改的关联业务员
		var ajaxData=[{"name":"agenciesOpNo","value":users},{"name":"agenciesOpName","value":userNames},{"name":"agenciesUid","value":$("[name=agenciesUid]").val()}];
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
	function coreCompanyCallBack(userInfo){
		var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
		var userNames=userInfo.brName.replace(new RegExp(/(;)/g),'|');
		//提交修改的关联业务员
		var ajaxData=[{"name":"coreCompanyOpNo","value":users},{"name":"coreCompanyOpName","value":userNames},{"name":"coreCompanyUid","value":$("[name=coreCompanyUid]").val()}];
		$.ajax({
			url:webPath+"/mfCusCoreCompany/updateAjaxByOne",
			data:{ajaxData:JSON.stringify(ajaxData)},
			dataType:'json',
			type:'post'
		})
		$("[name=coreCompanyOpName]").val(userInfo.brName);
		$("[name=coreCompanyOpNo]").val(users);
		$("body").click();
		$("[name=coreCompanyOpName]").parent().prev().text(userInfo.brName);
	}
</script>
<div class="row clearfix">
	<div class="col-xs-12 column info-block">
		<div class="block-add" style="display: none;"></div>
	</div>
</div>
