<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			   getListInfo();
			 });
			function getListInfo(){
				 myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/mfReceivablePrincipalInterest/findByPageAjax",//列表数据查询的url
				    	tableId:"tableMfReceivablePrincipalInterest",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:30//加载默认行数(不填为系统默认行数)
				    });
			}
			function getDetailPage(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"客户详情", function(){
                    updateTableData();
			 		});			
			};
			function getAppDetailPage(obj,url){	
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url,"项目详情", function(){
                    updateTableData();
			 		});			
			};
			function getReplyDetail(obj,url){
			    url = url.split("?")[1].split("&");	
			    var fincId = url[0].split("=")[1];
			    var appId = url[1].split("=")[1];
			    var busEntrance = url[2].split("=")[1];
			    var fincSts = url[3].split("=")[1];
			    if("5" == fincSts){
			    	url = webPath+"/mfRepayment/repaymentJsp?fincId="+ fincId;
			 		top.openBigForm(url,"还款信息", function(){
                        updateTableData();
			 		});
			    }else if("6" == fincSts){
			    	url = webPath+"/mfBusPact/getPactFincById?fincId="+ fincId +"&appId="+ appId +"&busEntrance="+ busEntrance +"&subStringNub="
					top.openBigForm(url,"还款信息", function(){
                        updateTableData();
			 		});		
			    }
			}
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div top-title">应收本息查询</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript">
		filter_dic =[{"optCode":"planEndDate","optName":"还款到期日期","dicType":"date"},
              {"optCode":"pactEndDate","optName":"合同结束日期","dicType":"date"}];
		</script>
	</body>	
</html>