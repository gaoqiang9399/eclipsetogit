<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwCashierAccount/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecashier0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(options,data){
// 						$("#tablist td[mytitle]:contains('...')").initMytitle();	
						//显示列参考DemoSpecial_List.jsp
						var accountType = getMyFilterVal();
						var tit = "1"==accountType ? "accountNo,accountName,curNo,accName,buttonCol" : "accountNo,accountName,curNo,bankNo,accName,buttonCol";
						$(".search-title").find("span").attr("value", tit);
						showTable(false, '');
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			
			function addAccount(){
				var accountType = getMyFilterVal();
				var tit = "1"==accountType ? "现金" : "银行";
				top.openBigForm('${webPath}/cwCashierAccount/input?accountType='+ accountType, '新增' + tit + "账户", function(){
					updateTableData();//重新加载列表数据
				});	
			}
			
			function editAccount(obj, ajaxUrl){
				var accountType = getMyFilterVal();
				var tit = "1"==accountType ? "现金" : "银行";
				top.openBigForm(webPath+ajaxUrl, '修改' + tit + "账户", function(){
					updateTableData();//重新加载列表数据
				});	
			}
			
			//获取筛选选中的值，不存在默认为1
			function getMyFilterVal(){
				var accountType ="1";
				$(".filter-val").each(function(){
			    	var myfilter = eval($(this).find("input[type=hidden]").val());
					if(myfilter!=""){
						accountType = myfilter[0].value;
					}
				});
				return accountType;
			}
			
			
			
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary pull-left" onclick="addAccount()">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/cwCashierJournal/getListPage">出纳日记账</a></li>
						<li class="active">出纳账户管理</li>
					</ol>
				</div>
				<div class="search-title hidden"><span value=""></span></div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=名称/编号"/>
						<!-- end -->
				</div>
			</div>
		</div>
		
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!--页面显示区域-->
				 <div id="content" class="table_content"  style="height: auto;">
					</div> 
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
<script type="text/javascript">
	filter_dic = [ {
		"optCode" : "accountType",
		"optName" : "账户类型",
		"parm" : [ {
			"optName" : "现金",
			"optCode" : "1"
		}, {
			"optName" : "银行存款",
			"optCode" : "2"
		}, ],
		"dicType" : "y_n"
	} ];
</script>
</html>
