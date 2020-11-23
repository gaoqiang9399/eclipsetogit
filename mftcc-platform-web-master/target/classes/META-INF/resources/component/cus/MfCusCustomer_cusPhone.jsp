<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#dataTree.ztree li span.button{
		opacity: 0.5;
		margin-top: 0px;
	}
	#dataTree {
		height: 355px;
		overflow: auto;
	}
	#cusPhoneConfirm:hover {
	    background:#009db7;
	}
	#cusPhoneConfirm{
		 background:#32b5cb; 
	}	
</style>
</head>
<body class="bg-white">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 column id="ztreeDiv">
				<div>
					<ul id="dataTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-xs-12 column">
				<div style="text-align: center; margin-top: 5px;">
					<button id="cusPhoneConfirm" type="button" style="border: none;color: white; height: 30px; width: 60px; cursor: pointer;" onclick="confirmCusPhone();">确认</button>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 滚动轴 -->
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" />
<!--zTree  -->
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript" >
	var zNodes = ${cusPhoneArray};
	
	$(document).ready(function() {
		$.fn.zTree.init($("#dataTree"), setting, zNodes);	
	});
	
	var setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				showLine : false,
				dblClickExpand : false,
				/* addDiyDom : addDiyDom */
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback : {
			}
		};
	
	function confirmCusPhone(){
		
			var selectNodes = $.fn.zTree.getZTreeObj("dataTree").getCheckedNodes(true);
			if(selectNodes.length == 0){
				/* parent.dialog.get("getCusPhoneDialog").close(); */
				parent.dialog.get("getCusPhoneDialog").close();
				return;
			}			
			var cusTel="";
			$.each(selectNodes,function(i,treeNode){
				if(treeNode.isParent){	
				}else{
					if( i == selectNodes.length-1){
						cusTel = cusTel + treeNode.name;
					}else{
						cusTel = cusTel + treeNode.name+";";
					}
				}
			});
			var cusPhoneInfo = new Object();
			cusPhoneInfo.cusTel = cusTel;
			parent.dialog.get("getCusPhoneDialog").close(cusPhoneInfo);
	}
</script>
</html>
