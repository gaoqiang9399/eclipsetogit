<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfBusGpsReg_InsertList.js"></script>
		<script type="text/javascript">
			var appId = "${appId}";
			var wkfAppId = "${wkfAppId}";
			var taskId = "${taskId}";
			var pactId ='${mfBusApply.pactId}';
			var cusNo = '${mfBusApply.cusNo}';
			$(function(){
				MfBusGpsReg_InsertList.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="row clearfix scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="dynamic-block" title="GPS信息登记" name="MfCusFinMainAction" data-sort="1"
				    data-tablename="mf_bus_gps_reg">
				      <div class="list-table">
				       <div class="title">
				       <span class="formName">
				       <i class="i i-xing blockDian"></i>GPS信息登记
				       </span>
				       <button class="btn btn-link formAdd-btn" onclick="MfBusGpsReg_InsertList.addGpsFormInfo();" title="新增"><i class="i i-jia3"></i></button>
				       <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#gpsInfoListDiv"><i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>
				         <div id="gpsInfoListDiv" class="content collapse in" aria-expanded="true">
				         	${tableHtml}
				         </div>
				      </div>
				    </div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfBusGpsReg_InsertList.submitAjax()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
