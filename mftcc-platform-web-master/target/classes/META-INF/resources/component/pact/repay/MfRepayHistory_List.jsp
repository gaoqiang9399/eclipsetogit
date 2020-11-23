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
				    	url:webPath+"/mfRepayHistory/findByPageAjax",//列表数据查询的url
				    	tableId:"tablerepay0001",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:30,//加载默认行数(不填为系统默认行数)
				    	ownHeight:true
				   });
			 });
			function getFincAppInfoList(flag){
				 location.href = webPath+"/mfBusFincApp/getListPage";
			}
			 function getRepayInfoList(flag){
				 location.href = webPath+"/mfRepayHistory/getListPage";
			}
		</script>
	</head>
	<body>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column mysearch-div">
							<div class="btn-group">
								<span stype="display:none;" data-toggle="dropdown"
									value="pactId,cusName,cusName,kindName,fincAppAmt,appTime,cusNameMng,fincSts"></span>
								<ul class="search-tab">
									<li style="margin-right: 0px;" onclick="getFincAppInfoList(this)"
										value="pactId,cusName,cusName,kindName,fincAppAmt,appTime,cusNameMng,fincSts">放款情况
									</li>
									<li class="current" onclick="getRepayInfoList(this)">
									还款情况
									</li>
								</ul>
							</div>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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
	</body>	
	<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic =[{ "optCode" : "shouldRepayAmt",
		 "optName" : "应还金额",
		 "dicType" : "num" },{ "optCode" : "realRepayAmt",
		 "optName" : "实还金额",
		 "dicType" : "num" },{ "optCode" : "repayDate",
		 "optName" : "还款日期",
		 "dicType" : "val" },{ "optCode" : "discountAmt",
		 "optName" : "优惠金额",
		 "dicType" : "num" },{ "optCode" : "paymentType",
		 "optName" : "支付方式",
		 "parm" :[{ "optName" : "银行转账",
		 "optCode" : "2" },{ "optName" : "现金",
		 "optCode" : "1" }], "dicType" : "	银行转账" }];
	 
	window.ajaxRepReview = function (obj,url){
		jQuery.ajax({
			url:url,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					//$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					alert(data.msg,1);
					window.location.href=webPath+"/mfRepayHistory/getListPage";
				}else if(data.flag == "error"){
					$.myAlert.Alert(data.msg);
				}
			},error:function(data){
				//$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		$(obj).parents(".content_form").hide();
    };
	</script>
</html>
