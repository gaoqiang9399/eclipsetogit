<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
		<title>凭证详情</title>
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
		</style>
		<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/voucher/js/voucher.js"></script>
<%-- 		<script type="text/javascript" src="${webPath}/component/finance/js/jatoolsPrinter.js"></script> --%>
	<script type="text/javascript">
		
			
	</script>
	</head>
	<body class=" overflowHidden">
		<div class="scroll-content">
			<div id="pzEdit"></div>
			<div class="col-xs-12 column"style="width: 1140px; top: -20px;margin:auto;float: none;">
				<div id="doc_div"></div>
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>

	</div>
</body>
	<script type="text/javascript">
		var voucherNo = '${dataMap.voucherNo}';
		var voucherNos = '${dataMap.voucherNo}';//附件使用了这个变量
		var jzFlag = '${dataMap.jzFlag}';
		//上传附件的功能
		var aloneFlag = true;
		var dataDocParm = {
			relNo : '${dataMap.voucherNo}',
			docType : "voucher",
			docTypeName : "附件",
			docSplitName : "文件",
			query : 'Y'==jzFlag ? "query" : "",
		};
		
		$(function(){
			which = '${dataMap.which}';
			businessNo = '${dataMap.businessNo}';
			//自定义滚动条
			$(".page-voucher").mCustomScrollbar({
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
			});
			$(".scroll-content").mCustomScrollbar({
				advanced:{
					theme:"minimal-dark",
					updateOnContentResize:true
				}
			});
			var for_which = 'edit';
			if('Y'==jzFlag){
				for_which = 'view';
			}
			var isClose = ${dataMap.isClose};
			$('#pzEdit').voucher({
				id:'vchEdit',
				is_show_audit: !isClose,
				show_next_btn:true,
				is_JzVch:jzFlag,
				pzno:voucherNo,
				for_which: for_which,
				cb:function(){
					allVchNo = '${dataMap.allVchNo}';
					if(allVchNo != ''){
						var vchNo = allVchNo.split('@');
						var len = vchNo.length;
						for(var i = 1; i < len; i++){
							if(voucherNo==vchNo[i]){
								if(i > 1){
									prevNo = vchNo[i - 1];
									$("#prev").removeClass("ui-btn-prev-dis");							
								}
								if(i < (len - 1)){
									nextNo = vchNo[i + 1];
									$("#next").removeClass("ui-btn-next-dis");							
								}
							}
						}
					}else{
						$('#prevNextDiv').hide();
					}
				}
			});
			
		})
		
// 		function testPrint(){
// 			window.open('${webPath}/cwVoucherMst/toVoucherPrint?voucherNo=pz17022811075524310', '_self', '')
// 		}
		
// 		declareJatoolsPrinter();
// 		function doPrint(how) {
// 			$.get('${webPath}/cwVoucherMst/toVoucherPrint', {'voucherNo':'pz17022811075524310'}, function(data){
// 			    myDoc = {
// 			    	printBackground:true,//打印背景颜色
// 			        settings : {
// 			        	leftMargin : 0,
// 			        	rightMargin : 0,
// 			            orientation : '2',
// 			            paperName : 'a4'
// 			        }, // 配置页边距（单位1/10mm），orientation:1为纵向,2为横向，选择a4纸张进行打印
// 		            importedStyle:[webPath+'/component/finance/voucher/css/vchprint.css'],//引用Css
// // 		            fitToPage  :true,   //必要时缩放打印
// 			        documents : {html: data},
// 			        page_div_prefix: "vch",
// 			        copyrights : '杰创软件拥有版权  www.jatools.com' // 版权声明,必须   
// 			    };
// 			    if (how == '打印预览...')
// 			    	getJatoolsPrinter().printPreview(myDoc); // 打印预览
// 			    else if (how == '打印...')
// 			    	getJatoolsPrinter().print(myDoc, true); // 打印前弹出打印设置对话框
// 			    else
// 			    	getJatoolsPrinter().print(myDoc, false); // 不弹出对话框打印
// 			})
// 		}
	</script>
</html>