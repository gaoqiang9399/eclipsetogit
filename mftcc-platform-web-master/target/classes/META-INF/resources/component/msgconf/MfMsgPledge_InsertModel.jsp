<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'></script>
<script type="text/javascript" src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src='${webPath}/component/msgconf/js/MfMsgPledge.js'></script>
<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
<script type="text/javascript">
	function ajaxSubmitThis(obj) {
		var modelContent = $("textarea[name=modelContent]").val();
		if (modelContent == '') {
			alert("催收模板内容不能为空", 0);
			return;
		}
		parent.dialog.get("MfMsgPledgeModelDialog").close(modelContent);
	}
</script>
<style type="text/css">
.input-class1 {
	width: 90px;
	border: none;
	background: #fff;
	color: #000;
	font-weight: bold;
}
.input-class {
	width: auto;
	border: none;
	background: #94D8E3;
    color: #000;
}

.content_btn {
	top: 145px;
}
</style>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="mfMsgPledgeForm" theme="simple" name="operform" action="">
						<!-- <div style="margin-top: 20px; margin-bottom: 10px; text-align: left;">
							<span class='input-class1'>客户信息：</span> <input type="button"
								onclick='insertText(this)' class='input-class' name="conNo"
								value='合同号'> <input type="button"
								onclick='insertText(this)' class='input-class' value='合同总额'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='还款总额'> <input type="button"
								onclick='insertText(this)' class='input-class' value='本金合计'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='利息合计'> <input type="button"
								onclick='insertText(this)' class='input-class' value='违约金合计'>
						</div>
						<div style="margin-top: 0px; margin-bottom: 10px; text-align: left;">
							<span class='input-class1'>押品信息按钮：</span> <input type="button"
								onclick='insertText(this)' class='input-class' name="conNo"
								value='合同号'> <input type="button"
								onclick='insertText(this)' class='input-class' value='合同总额'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='还款总额'> <input type="button"
								onclick='insertText(this)' class='input-class' value='本金合计'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='利息合计'> <input type="button"
								onclick='insertText(this)' class='input-class' value='违约金合计'>
						</div>
						<div style="margin-top: 0px; margin-bottom: 10px; text-align: left;">
							<span class='input-class1'>借据：</span> <input type="button"
								onclick='insertText(this)' class='input-class' name="conNo"
								value='合同号'> <input type="button"
								onclick='insertText(this)' class='input-class' value='合同总额'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='还款总额'> <input type="button"
								onclick='insertText(this)' class='input-class' value='本金合计'>
							<input type="button" onclick='insertText(this)'
								class='input-class' value='利息合计'> <input type="button"
								onclick='insertText(this)' class='input-class' value='违约金合计'>
						</div> -->
						<dhcc:bootstarpTag property="formmfmsgpledgemodel0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="ajaxSubmitThis('#mfMsgPledgeForm');"></dhcc:thirdButton>
		</div>
	</div>
</body>

<script type="text/javascript">
	var ajaxData = '${ajaxData}';
	ajaxData = JSON.parse(ajaxData);
	var vuListMap = ajaxData.vuListMap;
	
	function insertText(objinput) {
		var str = $(objinput).val();
		str = '{' + str + '}';
		$("textarea[name='modelContent']").insertContent(str);
	};
	
	(function($) {
		//调用js中初始化函数
		MfMsgPledge.initModel();
		$.fn.extend({
			insertContent : function(myValue, t) {
				var $t = $(this)[0];
				if (document.selection) { // ie
					this.focus();
					var sel = document.selection.createRange();
					sel.text = myValue;
					this.focus();
					sel.moveStart('character', -l);
					var wee = sel.text.length;
					if (arguments.length == 2) {
						var l = $t.value.length;
						sel.moveEnd("character", wee + t);
						t <= 0 ? sel.moveStart("character", wee - 2 * t
								- myValue.length) : sel.moveStart(
								"character", wee - t - myValue.length);
						sel.select();
					}
				} else if ($t.selectionStart
						|| $t.selectionStart == '0') {
					var startPos = $t.selectionStart;
					var endPos = $t.selectionEnd;
					var scrollTop = $t.scrollTop;
					$t.value = $t.value.substring(0, startPos)
							+ myValue
							+ $t.value.substring(endPos,
									$t.value.length);
					this.focus();
					$t.selectionStart = startPos + myValue.length;
					$t.selectionEnd = startPos + myValue.length;
					$t.scrollTop = scrollTop;
					if (arguments.length == 2) {
						$t.setSelectionRange(startPos - t,
								$t.selectionEnd + t);
						this.focus();
					}
				} else {
					this.value += myValue;
					this.focus();
				}
			}
		});
	})(jQuery);
</script>
</html>
