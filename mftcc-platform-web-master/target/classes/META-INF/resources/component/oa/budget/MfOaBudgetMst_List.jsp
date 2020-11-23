<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">

	$(function(){
		MfOaBudgetMst.init();
	})
	
	function submitCommit(obj, budgetId){
		alert(top.getMessage("CONFIRM_COMMIT"),2,function(){
			jQuery.ajax({
				url:webPath+"/mfOaBudgetMst/startProcessAjax",
				type:"POST",
				data:{budgetId: budgetId},
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						window.top.alert(data.msg,1);
						updateTableData();//重新加载列表数据
					}else if(data.flag == "error"){
						alert(data.msg, 0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	}
	
	function ajaxGetById(obj, url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		MfOaBudgetMst.ajaxGetById(obj, '详情', url);
	}
	
	function showAppHis(obj, url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		MfOaBudgetMst.ajaxGetById(obj, '审批历史', url);
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<dhcc:pmsTag pmsId="oa-budgetmst-apply-btn">
						<button type="button" class="btn btn-primary pull-left" id="budgetInsert">预算申请</button>
					</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=预算标题" />
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;"></div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/oa/budget/js/MfOaBudgetMst_List.js"></script>
</html>