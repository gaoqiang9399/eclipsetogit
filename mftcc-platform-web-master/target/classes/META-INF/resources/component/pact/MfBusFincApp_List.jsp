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
			    	url:webPath+"/mfBusFincApp/findByPageAjax",//列表数据查询的url
			    	tableId:"tablefincapp0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 function getFincAppInfoList(flag){
				 location.href = webPath+"/mfBusFincApp/getListPage";
			}
			 function getRepayInfoList(flag){
				 location.href = webPath+"/mfRepayHistory/getListPage";
			}
			/**跳转至还款页面**/
			function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			  	location.href = url;
			}
			 
		</script>
	</head>
	<body >
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column mysearch-div">
							<div class="btn-group">
								<span stype="display:none;" data-toggle="dropdown"
									value="pactId,cusName,cusName,kindName,fincAppAmt,appTime,cusNameMng,fincSts"></span>
								<ul class="search-tab">
									<li class="current" style="margin-right: 0px;"  onclick="getFincAppInfoList(this)"
										value="pactId,cusName,cusName,kindName,fincAppAmt,appTime,cusNameMng,fincSts">放款情况
									</li>
									<li onclick="getRepayInfoList(this)">
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
		filter_dic =[{ "optCode" : "putoutAmt",
			 "optName" : "放款金额",
			 "dicType" : "num" },{ "optCode" : "putoutDate",
			 "optName" : "放款日期",
			 "dicType" : "val" },{ "optCode" : "fincAppAmt",
			 "optName" : "放款申请金额",
			 "dicType" : "num" },{ "optCode" : "fincSts",
			 "optName" : "申请状态",
			 "parm" :[{ "optName" : "已还款未复核",
			 "optCode" : "6" },{ "optName" : "审核中",
			 "optCode" : "2" },{ "optName" : "审批通过已复核",
			 "optCode" : "5" },{ "optName" : "已否决",
			 "optCode" : "3" },{ "optName" : "审批通过待放款",
			 "optCode" : "4" },{ "optName" : "未提交",
			 "optCode" : "1" },{ "optName" : "完结",
			 "optCode" : "7" }], "dicType" : "y_n" }];
                  
 window.ajaxFinReview = function (obj,url){
		jQuery.ajax({
			url:url,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					//$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					alert(data.msg,1);
					window.location.href=webPath+"/mfBusFincApp/getListPage";
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
    
 	window.finReview = function (obj,url){
 		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
 		top.openBigForm(url,"放款复核",closeCallBack1);
    };
    
    function closeCallBack1(){
		if(top.addFlag){
			window.location.href=webPath+"/mfBusFincApp/getListPage";
		}
	};

	</script>
</html>
