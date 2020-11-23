<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<meta name="renderer" content="webkit|ie-stand|ie-comp">
	<title>会计科目列表</title>
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
	<script type="text/javascript" src="${webPath}/component/risk/layer/layer.js"></script>
	<!-- 科目树 -->
	<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
	<script src="js/common.js"></script>
	<style type="text/css">
		* {
			    -webkit-box-sizing: content-box;
			    -moz-box-sizing: content-box;
			    box-sizing: content-box;
			}
		/* subject-dialog */
		body{background-color:#fff;}
		.subject-dialog{width:437px;}
		.subject-dialog .hd{background-position:10;}
		.subject-dialog .cat{float:left;}
		.subject-dialog .cat .cur{color:#2383c0;}
		.subject-dialog .hd .add{float:right;}
		.subject-dialog .bd{ width:437px; height:500px;}
		
		#subject-category{border-left:0;}
		#subject-category li{width:40px;text-align:center;border-top:0;}
		#subject-category .last{border-right:none;}
	</style>
	<script type="text/javascript">
		var reportTypeId='${param.reportTypeId}';
		var setting = {
				data: {
					simpleData: {
						enable: !0,
						idKey: "id",
						pIdKey: "parentId",
						rootPId: 0
					},
					key: {
						name: "showName"
					}
				},
				callback: {
					onDblClick: zTreeOnDblClick
				}
	
			};
			
		var zNodes;
	
		function getDataItem(){
			$.ajaxSettings.async = false;
			$.getJSON("${webPath}/cwReportItem/getDataItemForTree",'reportTypeId='+reportTypeId, function(data){
				zNodes = data.items;
			});
			$.fn.zTree.init($("#subject-tree"), setting, zNodes);
		}
		
		function zTreeOnDblClick(event, treeId, treeNode) {
			if(treeNode.isParent){//父节点不允许双击选中
				return false;
			}
			if(treeNode.operationType!='1'){
				//alert("该项用于统计发生额，不可选择。", 2);
				alert(top.getMessage("ONLY_CW_ACCOUNT_COUNT", ""),1);
				return false;											
			}
			if(treeNode.isInput==null||treeNode.isInput==''){
				//alert("此项尚未配置现金流方向或属于系统固定统计项，请选择其它项。",2);
				alert(top.getMessage("NO_CW_CASHFLLOW_SET", ""),1);
				return false;
			}
			top.cwBackData = treeNode;//获取摘要返回父页面
			$(top.window.document).find("#showDialog .close").click();//关闭此页面
			//给单击进来的文本框赋值
		};
	
	$(function(){
		//自定义滚动条
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		getDataItem();
	});
	</script>
</head>
<body>
<div class="subject-dialog">
	<div class="bd scroll-content">
		<ul id="subject-tree" class="ztree"></ul>
	</div>
</div>
</body>
</html>