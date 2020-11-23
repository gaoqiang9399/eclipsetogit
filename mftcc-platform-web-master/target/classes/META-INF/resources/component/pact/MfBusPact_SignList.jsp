<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var _isCheckAll = false;
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusPact/findSignByPageAjax",//列表数据查询的url
			    	tableId:"tablepact0001",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数 (过滤掉已经封挡的数据)
			    });
			    _addPactListCheckAllEvent();
			    _stopPropagation();
			 });
			 
			//全选事件
			var _addPactListCheckAllEvent = function() {
				$(".table-float-head").delegate("th:first-child", "click", function() {
					if (_isCheckAll) {
					 	$("#tablist>tbody tr").find(':checkbox').each(function() {
							this.checked = false;
						});
					 	_isCheckAll = false;
					} else {
					 	$("#tablist>tbody tr").find(':checkbox').each(function() {
							this.checked = true;
						});
					 	_isCheckAll = true;
					}
				});
			};
			
			//停止checkBox事件冒泡
			var _stopPropagation = function() {
				$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
					event.stopPropagation();
				});
			};
			
			var getCheckedPactInfos = function() {
				var pactInfos = new Array();
				var $checkboxes = $("#tablist [name=pactId]:checkbox:checked");
				if ($checkboxes.length > 0) {
					$checkboxes.each(function() {
						var pactInfo = new Object();
						pactInfo.pactId = $(this).val().split("=")[1];
						pactInfos.push(pactInfo);
					});
				}
				return pactInfos;
			};
			
			var checkPushPactInfos = function() {
				var flag = false;
				$("#tablist>tbody tr").find(":checkbox:checked").each(function() {
					var nodeNoVal = $(this).parent().parent().find("input[name=nodeNo]").val();
					console.log(nodeNoVal);
					if(nodeNoVal != 'review_finc'){
						flag = true;
					}
				});
				return flag;
			};
			
			var pushPactInfos = function() {
				var pactInfos = getCheckedPactInfos();
				if(pactInfos.length == 0){
					window.top.alert(top.getMessage("FIRST_SELECT_FIELD", "办理阶段为：'还款计划'的合同信息"), 1);
				}else{
					var flag = checkPushPactInfos();
					if(flag == true){
						window.top.alert("选择的记录中存在办理阶段不为：'还款计划'的记录信息", 0);
					}else{
						var url = webPath+"/mfBusPact/pushPactInfosAjax";
						jQuery.ajax({
							url : url,
							data : {
								pactInfosJson : JSON.stringify(pactInfos)
							},
							type : "POST",
							dataType : "json",
							success : function(data) {
								if(data.flag) {
									window.top.alert(data.msg, 3);
									//_refreshTableData();
								} else {
									window.top.alert(data.msg, 0);
								}
							}, 
							error : function(data) {
								 window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
							}
						});
					}
				}
			};
			
			/**跳转至还款页面**/
			function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				location.href = url;
			  	event.stopPropagation();
			}
			
			function getDetailPage(obj,url){		
				top.LoadingAnimate.start();	
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				window.location.href=url;	
				event.stopPropagation();
			}
			
			var timeFunc=null;
			//监听ctrl键
			document.onkeydown=function(event){
				var e = event || window.event || arguments.callee.caller.arguments[0];
				//若点击了ctrl 键则 清除timeFunc
				if(e && e.ctrlKey){ 
					clearTimeout(timeFunc);
				}
			}; 
			
			function trClick(url){		
				clearTimeout(timeFunc);
				timeFunc=setTimeout(function(){
					top.LoadingAnimate.start();	
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					window.location.href=url;
				}, 300);
			}
				 
		</script>
		<style>
			.table_content .ls_list tr td.change-td-color-已否决{
				color:red;
			}
		</style>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 凤安平台项目:合作社向凤安推送贷款借据请求 -->
				<dhcc:pmsTag pmsId="fatech-pushPactInfos">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="pushPactInfos();">申请放款</button>
					</div>
				</dhcc:pmsTag>
				<div class="search-div">
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
</body>
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic = [
	       {
				"optName" : "产品种类",
				"parm" : ${kindTypeJsonArray},
				"optCode" : "kindNo",
				"dicType" : "y_n"
			},{
                  "optName": "合同金额",
                  "parm": [],
                  "optCode":"pactAmt",
                  "dicType":"num"
              },{
                  "optName": "放款金额",
                  "parm": [],
                  "optCode":"putoutAmt",
                  "dicType":"num"
              },{
                  "optName": "合同期限",
                  "parm": [],
                  "optCode":"term",
                  "dicType":"num"
              },{
             	  "optCode" : "busStage",
				  "optName" : "办理阶段",
				  "parm" : ${flowNodeJsonArray},
				  "dicType" : "y_n"
              },{
                  "optName": "合同开始日期",
                  "parm": [],
                  "optCode":"beginDate",
                  "dicType":"date"
              },{
                  "optName": "放款申请日期",
                  "parm": [],
                  "optCode":"currPutoutAppDate",
                  "dicType":"date"
              }
          ];
          
	</script>
</html>
