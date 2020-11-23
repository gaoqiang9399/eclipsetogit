<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
		 <link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/collateral/js/UploadBill.js?v=${cssJsVersion}'></script>
		<script type="text/javascript" src='${webPath}/component/collateral/js/ShowPledgeDetailInfo.js?v=${cssJsVersion}'></script>
		<script type="text/javascript" >
			var isUpload = false;
			var appId = '${appId}';
			var collateralId = '${collateralId}';
			var webPath = "${webPath}";
			var sysProjectName = "${sysProjectName}";
			var uploader;
			$(function(){
                UploadBill.init();
                UploadBill.initupload();
                //点击上传
                $('#picker').on('click',function(){
                    UploadBill.uploadFinReport();
                });
			});
		</script>
	</head>
	<body class="bg-white " style="overflow: hidden;">
		<div class="container">
			<div class="scroll-content">
				<div class="row clearfix  margin_top_20">
					<div class="col-md-12 column">
						<ul class="tab-ul">
							<li >
								<span>模板下载</span> <i onclick="UploadBill.exportExcel();" class="i i-xiazai"></i>
							</li>
						</ul>
					</div>
				</div>
			<div class="row clearfix margin_top_20">
				<div class="col-xs-6 col-xs-offset-3 column" id="newParm-div">
					<div id="uploader" class="wu-example">
						<div id="thelist" class="cb-upload-div input-group input-group-lg" style="float: left;width: 82%">
							<input name="uploadFile" readonly="readonly" type="text" class="form-control">
							<span id="picker" readonly="readonly" class="input-group-addon pointer">上传合同清单</span>
							<div class="hidden" id="picker-outer"></div>
						</div>
					</div>
					<div style="color: red; margin-bottom: 50px;" id="showMsg"></div>
				</div>
			</div>
			<div class="list-table" id="uploadBillListDiv">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>合同清单</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#uploadBillList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="uploadBillList" name="uploadBillList">
					<dhcc:tableTag property="tablepledgebillupload" paginate="uploadBillList" head="true"></dhcc:tableTag>
				</div>
			</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="UploadBill.saveBill();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>	
</html>
