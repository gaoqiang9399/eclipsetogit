<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
			<style>
			.addsty01{
				float: right;
			}
			.table_content .ls_list tr td{
				padding-right:10px;
			}
			.tuSty{
				color: black;
			}
			
			 .editbox {
			    width: 100%;
			    border: 1px solid #ddd;
			    height: 39px;
			    line-height: 39px;
			    padding: 6px 4px;
			    text-align: right;
			}
			.editbox:focus {
   				border: 1px solid #00b8ec;
   				text-align: left;
			}
			.mbts{
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwInitBal/findByPageAjax",//列表数据查询的url
			    	tableId:"tableinitbal0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30000,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		
			    	}
			    });
			 });
			
			function toInitPage(){
				window.location.href = webPath + "/component/finance/paramset/CwInitBal_InitPage.jsp?listType=1";
			}
			function ajaxAddDetail(obj){
				
				var acchrt = $(obj).attr("acchrt");
				top.addFlag = false;
				top.htmlStrFlag = false;
			/* 	top.createShowDialog("${webPath}/cwInitBal/initAssistInsert","增加明细",'70','70',function(){
					if(top.addFlag){
						updateTableData();//重新加载列表数据
						
					}
				}); */
				//
				window.parent.openBigForm('${webPath}/cwInitBal/initAssistInsert?accHrt='+acchrt, '增加明细',closeCallBack, 70, 70);
			}
			function closeCallBack() {
				myclose();
				updateTableData();//重新加载列表数据
			};
			//部门回调
			function setSysOrgInfo(sysOrg){
				$("input[name=brName]").val(sysOrg.brName);
				$("input[name=brNo]").val(sysOrg.brNo);
			}
			/*
			系iu给金额 
			*/
			function initAddBals(iobj){
				var acchrt = $(iobj).attr("acchrt");
				var voucherno = $(iobj).attr("voucherno");
				var bal = $(iobj).val();
				if(!bal){
					return;
				}
				
				var pdata ={'accHrt':acchrt,'voucherNo':voucherno,'bal':bal};
				$.ajax({
					url:webPath+'/cwInitBal/addInitAssistBalAjax',
					type:'post',
					data:pdata,
					async:false,
					dataType:'json',
					error:function(){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					},
					success:function(data){
						if(data.flag == "success"){
							if(data.result=='1111'){
							}else{
								updateTableData();//重新加载列表数据
							}
						}
					}
				});
				
			}
			/*
			删除凭证的操作
			*/
			function ajaxDelByAccHrt(dobj){
				var acchrt = $(dobj).attr("acchrt");
				var voucherno = $(dobj).attr("voucherno");
				var bal = $(dobj).val();
				var pdata ={'accHrt':acchrt,'voucherNo':voucherno,'bal':bal};
				$.ajax({
					url:webPath+'/cwInitBal/delInitAssistBalAjax',
					type:'post',
					data:pdata,
					async:false,
					dataType:'json',
					error:function(){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					},
					success:function(data){
						if(data.flag == "success"){
							if(data.result=='1111'){
							}else{
								updateTableData();//重新加载列表数据
							}
						}
					}
				});
			}
		
			/*
			试算平衡校验
			*/
			function checkBalBalance(){
				top.createShowDialog("${webPath}/cwInitBal/checkBalBalance","试算平衡检查",'50','50',function(){
					
				});
				
			}
			
		</script>
	
	</head>
		<body style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
						<div class="btn-div">
							<c:if test='${dataMap.initFlag=="1"}'>
								<button type="button" class="btn btn-primary pull-left" onclick="toInitPage();">导入</button>
<!-- 								<button type="button" class="btn btn-info pull-left" onclick="toInitPage();">导入</button> -->
								<span></span>
								<!-- <button type="button" class="btn btn-info pull-left" onclick="saveBal();">保存</button> -->
								<button type="button" class="btn btn-primary pull-left mbts " onclick="checkBalBalance();">试算平衡</button>
							</c:if>
<!-- 							<button type="button" class="btn btn-primary " onclick="checkBalBalance();">试算平衡</button> -->
							<ol class="breadcrumb pull-left">
							<li><a
								href="${webPath}/cwParamset/cwParamEntrance">设置</a></li>
							<li class="active">财务初始余额</li>
						</ol>
						</div>
				<!-- 	<div class="search-div">
						<div class="col-xs-3 column znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="科目编码/科目名称">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
					</div> -->
					<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=科目名称/科目编号"/>
						<!-- end -->
				</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;"></div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
</html>
