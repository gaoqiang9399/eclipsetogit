<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/component/sys/css/sysUser.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/sys/js/sysUser.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/idvalidate.js"></script>
		<script type="text/javascript" >
		var ajaxData = '${ajaxData}';
		var mcsBar;
	    ajaxData = eval("("+ajaxData+")");
	    var opNoType = "${opNoType}";
		$(function(){
		   mcsBar = myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/sysUser/getAllUserByBrNoByPageAjax?opNoType="+opNoType,//列表数据查询的url
		    	tableId:"tablesysforapp5001",//列表数据查询的table编号
		    	tableType:"thirdTableTag"//table所需解析标签的种类
	    		/* callback:function(){
			    	$("table").tableRcswitcher({
			    	name:"userFlag",onText:"生效",offText:"失效"});
			    }// */
		    });
		    var arr = ajaxData.channel;
		    for(var i in arr) {
		    	var html = "<option value='"+arr[i].optCode+"'>"+arr[i].optName+"</option>";
				$("#channelType").append(html);
			}
		 console.log(ajaxData.channel);
				
		});
		</script>
		<script type="text/javascript">
			function clearError(obj) {
				$(obj).removeClass("Required");
				if($(obj).next().next().text()=="不能为空") {
					$(obj).next().next().remove();
				}
			}
			//添加客户经理
			function insertCusMng(obj,url){
				var channelType = $("#channelType").val();
				if(channelType){
					$.ajax({
			            url : url+"&channelType="+channelType, 
			            type:"get",
			            async:false,
			            success : function(data) {
			            	if (data.flag == "success") {
			            		window.top.alert(data.msg, 3);
			            		myclose_click();
							} else {
								window.top.alert(data.msg, 0);
							}
			            }
					 });		
				}else{
					window.top.alert("请选择渠道类型", 0);
				}
			}
		</script>
		<style type="text/css">
			.Required {
				border:1px solid red;
			}
			.Required-font {
				color:red;
			}
			.search-group .filter_btn_myFilter{
				margin-left:-6px;
			}
			.select3-placeholder{
				line-height:16px;
			}
			.addAmt-input{
			    height: 32px;
			    line-height: 26px;
			}
		</style>
	</head>	
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="SysUser_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column addAmt-input">
							
							<label>渠道类型：</label>
							<select name="channelType" id="channelType">
								<option value="">请选择渠道类型</option>
							</select>
					  
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		addDefFliter("3","用户角色","roleNo","ROLE_NO","");
		
	filter_dic =[{"optCode":"opName","optName":"操作员名称","dicType":"val"},{"optCode":"idNo","optName":"身份证","dicType":"val"},{"optCode":"mobile","optName":"手机","dicType":"val"}];</script>
</html>
