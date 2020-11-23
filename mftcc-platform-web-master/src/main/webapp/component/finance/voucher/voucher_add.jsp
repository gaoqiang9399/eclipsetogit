<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>凭证录入</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/voucher.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/list.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/icon.css" />
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/iconColor.css" />
		<style type="text/css">
			* {
			    -webkit-box-sizing: content-box;
			    -moz-box-sizing: content-box;
			    box-sizing: content-box;
			}
			.wrapper { 
				margin: 20px;
			}
		</style>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/voucher/js/voucher.js"></script>
		<script type="text/javascript">
		var aloneFlag = true;
		var dataDocParm = {
			relNo : '${voucherNo}',
			docType : "voucher",
			docTypeName : "附件",
			docSplitName : "",
			query : "",
		};
			
		</script>
	</head>
<body class=" overflowHidden">
	<div class="scroll-content">
		<div id="pzAdd_div"></div>
		<!-- 文件上传  -->
		<div class="col-xs-12 column" style="width:1140px;top:-50px;padding-left: 20px">
			<div id="doc_div"></div>
			<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
		</div>
	</div>
	
</body>
<script type="text/javascript">
		
		var voucherNos = '${voucherNo}';//附件使用
		var zytext = "";//摘要文本，使用赋值
		
		$(function(){
			//自定义滚动条
			$(".page-voucher").mCustomScrollbar({
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
			});
			$(".scroll-content").mCustomScrollbar({
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			$('#pzAdd_div').voucher({
				id:'vchAdd',
				voucherNo:voucherNos,
				for_which: 'add'
			});
			
		})
	</script>
</html>