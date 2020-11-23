<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
		<script type="text/javascript">
			
			function ajaxSubmitThis(obj){
				var modelContent = $("textarea[name=cuslendWarnContent]").val();
				if(modelContent == ''){
					alert("催收模板内容不能为空",0);
					return ;
				}
							
				parent.dialog.get("CuslendWarnDialog").close(modelContent);
			}
		</script>
		<style type="text/css">
		.input-class{
			/*width:84px;*/
			width: auto;
			border:none;
			background:#32b5cb;
			color:#fff;
		}
		.content_btn {
		   top:145px;
		}
		</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="urgeModelInsertForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formcuslendmodel0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" onclick="ajaxSubmitThis('#urgeModelInsertForm');"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	
	<script type="text/javascript">
	    var ajaxData = '${ajaxData}';
		ajaxData = JSON.parse(ajaxData);
		var vuListMap = ajaxData.vuListMap;
		var msg='${msg}';
		if(msg!=undefined&&msg!="undefined"&&msg!=""){
		    alert(msg,4);
		}
		var _initModel = function(){
		var htmlStr = "";
		var divStr = '<div style="margin-top: 20px; margin-bottom: 10px; text-align: left;">';
		var divHtmlStr = "";
		var titleName = "";
		var i = 1;
		$.each(vuListMap,function(keyMap,vuMap){
			if(i != 1){
				divStr = '<div style="margin-top: 0px; margin-bottom: 10px; text-align: left;">';
			}
			divHtmlStr = modelSelectHtml(vuMap);
			/*for(var key in keyMap){
				titleName = keyMap[key];
			}*/
			titleName = keyMap.split("_")[1];
			htmlStr = htmlStr + divStr 
				+ '<span class=\'input-class1\'>' + titleName + '：</span>'
				+ divHtmlStr + '</div>';
			i++;
		});
		
		$('#urgeModelInsertForm').prepend(htmlStr);
	};
	
	var modelSelectHtml = function(mmvlist){
		var htmlStr = "";
		$.each(mmvlist,function(i,mmv){
		    var showName=mmv.tagKeyName.replace(/\\$/g,"");
			htmlStr = htmlStr + '<input type="button" onclick=\'insertText(this)\' class=\'input-class\' name="'
				+ mmv.keyNo + '" value=\'' + showName + '\'> ';
		});
		return htmlStr;
	};
			function insertText(objinput) {
		    	var str = $(objinput).val();
		    	str = '\\${'+str+'}';
		    	$("textarea[name='cuslendWarnContent']").insertContent(str);
		    };
			(function($) {
			_initModel();
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
