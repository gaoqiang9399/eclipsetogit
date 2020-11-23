<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信申请表单详情</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<style type="text/css">
			[data-toggle="collapse"] .i-open-down {
			    display: none;
			}
			.collapsed[data-toggle="collapse"] .i-close-up {
			    display: none;
			}
			.collapsed[data-toggle="collapse"] .i-open-down {
			    display: block;
			}
			.upload_body .table_content table{
				background: #ffffff!important;
			}
			.upload_body .table_content span{
				text-align: center;
				width: 100%;
				display: inline-block;
				background: #ffffff!important;
				height: 50px;
				line-height: 50px;
				font-size: 14px;
			}
			.upload_body .list-table .ls_list thead tr th{
				padding-left: 10px!important;
			}
			.upload_body .list-table .ls_list tbody tr td{
				padding-left: 10px!important;
			}
			.upload_body .list-table{
				margin-top: 0px!important;
			}
			.upload_body li a .node_name{
				color: #32b5cb;
			}
			.upload_body li a .download-btn{
				color: #32b5cb;
				font-size: 16px;
			}
			.upload_body li{
				list-style: none !important;
			}
			.upload_body li a .download-btn1{
				position: absolute;
				right: 150px;
				top: 10px;
				color: #32b5cb;
				font-size: 16px
			}
		</style>
		<script type="text/javascript">
			var modelNo = "<%=(String)request.getAttribute("modelNo")%>";
			var path = "${webPath}";
			var basePath = "${webPath}";
			var appId = "${appId}";//授信申请编号。新增授信申请编号，调整授信编号
			var wkfAppId = "${wkfAppId}";
			var openType = "${openType}";
			var creditFlag = "${creditFlag}";
			var query = "${query}";     //该值标志位是否可以上传
			var mfCusPorductCreditList = eval("(" + '${json}' + ")").mfCusPorductCreditList;
			var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
			var index = 0;  //动态增加产品计数使用
			var cusNo='${cusNo}';
			var docShowType='1';
			var cusType='${cusType}';
			var baseType='${baseType}';
			var creditType='${creditType}';
			var $form=$("#operform");
			var relId=appId;
			var tempType="REPORT";//尽调报告
			var scNo='${scNo}';;//尽调报告
			var userId ="";
			var creditAppId = '${creditAppId}';
			var groupFiled = '${groupFiled}';
            var docParm ="&cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
			$(function(){
				mfCusCreditApplyDetail.init();
                mfCusCreditApply_InvestDetail.init();
			});
	        var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<c:if  test="openType == 0">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<form  method="post" theme="simple" name="operform" id="operform" action="">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
							<div class="configContent">

							</div>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
				</div>
		</div>
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Detail.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_InvestDetail.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>  
		<script type='text/javascript' src='${webPath}/dwr/util.js'></script>  
		<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
</html>