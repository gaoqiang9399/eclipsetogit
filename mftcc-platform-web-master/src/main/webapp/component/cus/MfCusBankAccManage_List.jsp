<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				var cusNo = '${cusNo}';
				var url = webPath+"/mfCusBankAccManage/findByPageByCusNoAjax?useFlag="+"1";
				if(cusNo != null){
					url = webPath+"/mfCusBankAccManage/findByPageByCusNoAjax?cusNo="+cusNo+"&useFlag="+"1";
				}else{
					
				}
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:"tablecusbank00002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			    $("[mytitle^=bankNo]").each(function(i,item){
					var itemBankNo = $(item).text();
					var space = " ";
					var formatBankNo = itemBankNo.substring(0,4)+space+itemBankNo.substring(4,8)+space+itemBankNo.substring(8,12)+space+itemBankNo.substring(12,16)+space+itemBankNo.substring(16);
					var itemBankNo = $(item).text(formatBankNo);
				})
			 });
			function choseBankAcc(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var bankAcc = new Object();
				bankAcc.id = parmArray[0].split("=")[1];
				bankAcc.accountNo = parmArray[1].split("=")[1];
				bankAcc.accountName = parmArray[2].split("=")[1];
				bankAcc.bank = parmArray[3].split("=")[1];
				parent.dialog.get('bankAccDialog').close(bankAcc);
			}
		</script>
	</head>
	<body class="bg-white overFlowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div col-xs-7 pull-right">
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
