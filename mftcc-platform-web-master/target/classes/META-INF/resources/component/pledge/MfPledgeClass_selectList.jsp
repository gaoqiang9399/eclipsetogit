<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		var appId = '${appId}';
		var cusNo = '${cusNo}';
		var pledgeNo = '${pledgeNo}';
		var pledgeClassNo;
			$(function(){
			     myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfPledgeClass/selectByPageAjax",//列表数据查询的url
			    	tableId:"tablenmd00003",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    }); 
			 });
			function enterClick(lk) 
			{
				var parm=lk.split("?")[1];
				var parmArray=parm.split("=");
				pledgeClassNo = parmArray[1];
				//跳页面
				//var url="MfBusPledgeAction_input.action?query='0'&cusNo="+cusNo+"&appId="+appId+"&pledgeClassNo="+pledgeClassNo;
				var url = webPath+"/mfBusPledgeDetail/input?cusNo="+cusNo+"&appId="+appId+"&pledgeNo="+pledgeNo+"&addWkfFlag=0"+"&pledgeClassNo="+pledgeClassNo;
				openPledgeForm(url);
				//$(top.window.document).find("#showDialog .close").click();
			}
			
			function openPledgeForm(url){
				$.ajax({
						url:webPath+"/mfPledgeClass/getPleClassByClassNoAjax",
						data:{classNo:pledgeClassNo},
						type:'post',
						dataType:'json',
						success:function(data){
							var formid_new = data.formid_new;
							var formid_old = data.formid_old;
							url = url+'&formid_new='+formid_new+'&formid_old='+formid_old;
							var obj = $(top.window.document).find("body");
							obj.find("#bigFormShowiframe").attr("src","");
							obj.find("#bigFormShowiframe").attr("src",url);
							//top.createShowDialog(url,'押品登记','90','90');
							
						},
						error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					})
			}
			
			function wkfCallBack(){

				}
		</script>
	</head>
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div col-xs-3 pull-right"">
						<div class="znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
						</div>
					</div>
				</div>
				<!--页面显示区域-->
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div id="content" class="table_content"  style="height: auto;">
						</div>
					</div>
				</div>
			</div>
	</body>
</html>
