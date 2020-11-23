<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/msgconf/js/MfMsgPledge.js'> </script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData = eval("("+ajaxData+")");
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				
				$('select[name=funType]').popupSelection({
					searchOn: true, //启用搜索
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback : function (obj, elem) {
						MfMsgPledge.changeFunType();
					}
				});
				
				$("input[name=classId]").popupSelection({
					searchOn:false,//启用搜索
					inline:false,//下拉模式
					multiple:true,//多选
					labelShow:false,//选择区域显示已选择项
					title : "押品类别",//标题
					items:ajaxData.collClass
				});
				
				/* $('select[name=triggerType]').popupSelection({
					searchOn: true, //启用搜索
					inline: true, //下拉模式
					multiple: false //单选
				}); */
				
				$('select[name=sendFrequencyUnit]').popupSelection({
					searchOn: true, //启用搜索
					inline: true, //下拉模式
					multiple: false //单选
				});
				
				$('input[name=reciverType]').popupSelection({
					searchOn:false,//启用搜索
					inline:false,//下拉模式
					multiple:true,//多选
					labelShow:false,//选择区域显示已选择项
					title : "发送对象",//标题
					items:ajaxData.reciverTypeItems
				});
				
				$('input[name=sendType]').popupSelection({
					searchOn:false,//启用搜索
					inline:false,//下拉模式
					multiple:true,//多选
					labelShow:false,//选择区域显示已选择项
					title : "发送方式",//标题
					items:ajaxData.sendTypeItems
				});
				
				$("input[name=varUsage]").popupSelection({		
					searchOn:true,//启用搜索
					inline:false,//下拉模式
					multiple:false,//多选
					labelShow:false,//选择区域显示已选择项
					title : "变量来源",//标题
					items:ajaxData.varUserageItems
				});
				
				//$("input[name=classId]").attr("readOnly","readOnly").removeAttr("onclick");
				MfMsgPledge.changeWaveType("insert");
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="mfMsgPledge" theme="simple" name="operform" action="${webPath}/mfMsgPledge/insertAjax">
					<dhcc:bootstarpTag property="formmfmsgpledge0001" mode="query" />
				</form>
			</div>
		</div>
	</div> 
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfMsgPledge.ajaxUpdateThis('#mfMsgPledge');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div> 
	</body>
</html>
