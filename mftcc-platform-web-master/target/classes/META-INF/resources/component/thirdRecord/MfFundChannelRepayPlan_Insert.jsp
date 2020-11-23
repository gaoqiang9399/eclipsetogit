<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript" src="${webPath}/component/thirdRecord/js/MfFundChannelRepayPlan_Insert.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}"> 
<link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayPlan_RepayPlanList.css" />
<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
<style type="text/css">
	.webuploader-pick{
		width:100%!important
	}
</style>
<script type="text/javascript">
	var ajaxData = ${ajaxData};
	var calcIntstFlag =ajaxData.calcIntstFlag;
	var cusNo = '${cusNo}';
	var pactId = '${pactId}';
	var appId =  '${appId}';
	$(function(){
		MfFundChannelRepayPlan_Insert.init();
	 });
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="repayPlanTrial" theme="simple" name="operform" action="${webPath}/mfRepayPlanTrial/calculateAjax">
						<dhcc:bootstarpTag property="formrepayplantrial0002" mode="query" />						
						<div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
							<!-- <button id="mfRepayPlanTrial_button" type="button" class="btn btn-default" value="保存"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="savePlanList();" >保存</button> -->
							<!-- <button id="mfRepayPlanExport_button" type="button" class="btn btn-default" value="下载模板"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="MfFundChannelRepayPlan_Insert.exportPlanList();" >下载模板</button>
							<div id="uploader" class="wu-example">
								<div id="thelist" class="cb-upload-div input-group input-group-lg">
									<input name="uploadFile" readonly="readonly" type="text" class="form-control" style="display:none;">
									<span style="width:0px;height:0px;padding:9px 17px;font-size:15px;" id="picker" readonly="readonly" class="input-group-addon pointer">导入计划</span>
								</div>
							</div> -->
							<!-- <div style="color: blue; margin-bottom: 30px;" id="dataMsg"></div> -->
						</div>
						</form>	
			<div class="table_content" style="width:100%;margin-left:-15px;">
				<div id="changediv" style="position: relative;margin-bottom:10px;margin-top:5px;" align="center">
			    </div>
		    </div>
				<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;margin-bottom:60px;">
				
				</div>
				<!-- <div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
					<button id="mfRepayPlanTrial_button" type="button" class="btn btn-default" value="保存"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="MfFundChannelRepayPlan_Insert.savePlanList();" >保存</button>
					<button id="mfRepayPlanTrial_button" type="button" class="btn btn-default" value="跳过"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="MfFundChannelRepayPlan_Insert.submitBussProcessAjax();" >跳过</button>
				</div> -->
			</div>			
		</div>
	</div>
	</div>
	<div class="formRowCenter" style="text-align: left;position: fixed" >	
		<div class="col-xs-2" id="newParm-div" style="margin-left:30%;">
			<div id="uploader" class="wu-example">
				<div id="thelist" class="cb-upload-div input-group input-group-lg">
					<input name="uploadFile" readonly="readonly" type="text" class="form-control" style="display:none;">
					<span style="width:0px;height:0px;padding:9px 17px;font-size:15px;" id="picker" readonly="readonly" class="input-group-addon pointer">上传还款计划表</span>
				</div>
			</div>
			<!-- <div style="color: blue; margin-bottom: 30px;" id="dataMsg"></div> -->
		</div>
		<dhcc:thirdButton value="下载模板" action="下载模板" onclick="MfFundChannelRepayPlan_Insert.exportPlanList();"></dhcc:thirdButton>
		<dhcc:thirdButton value="保存" action="保存" onclick="MfFundChannelRepayPlan_Insert.savePlanList();"></dhcc:thirdButton>
		<dhcc:thirdButton value="跳过" action="跳过" onclick="MfFundChannelRepayPlan_Insert.submitBussProcessAjax();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	</div>

</body>
</html>
