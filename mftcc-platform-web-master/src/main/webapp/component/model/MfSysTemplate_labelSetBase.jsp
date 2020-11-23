<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/";
%>
<html>
	<head>
		<title>设计模板</title>
		<link id="common" rel="stylesheet" href="${webPath}/themes/factor/css/common${skinSuffix}.css" />
		<link id="factor" rel="stylesheet" href="${webPath}/themes/factor/css/factor${skinSuffix}.css" />
		<link id="form" rel="stylesheet" href="${webPath}/themes/factor/css/form${skinSuffix}.css" />
		<link id="BS-factor" rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css"/>
		<link id="multi-select" rel="stylesheet" href="${webPath}/themes/factor/css/multi-select${skinSuffix}.css"/>
		<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript">
		var _setHtml= function(titleName,subList){
			var subHtml="";
			subHtml=subHtml+'<div class="row clearfix margin_top_20" style="padding: 10px 30px;border-bottom: 1px solid #d2d2d2;>'
			   +'<div class="col-xs-12 column">';
			   $.each(subList, function(i, queryItem) {
				   subHtml=subHtml+'<div id="'+queryItem.tagName+'@'+queryItem.id+'" onclick="setvalue(this)" class="col-xs-3" style="text-align:center">';
				   subHtml=subHtml+'<div class="option-div"><span>'+queryItem.tagName+'</span><i class="i i-sanjiaoduihao"></i></div>';
				   subHtml=subHtml+'</div>';
			   });
			   subHtml=subHtml+'</div></div>';
			   return subHtml;
		};
		function setvalue(s){
			self.returnValue=s.id;
			self.close();
		}
		$(function(){
			var itemList = '${ajaxData}';
		    itemList = JSON.parse(itemList);
		    var groupFlag = '${groupFlag}';
			groupFlag = JSON.parse(groupFlag);
		    var htmlStr="";
		    $.each(groupFlag,function(i,groupFlag){
				$.each(itemList,function(i,mapObj){
					for(var key in mapObj) {
						if(key == groupFlag.optCode){
							htmlStr += _setHtml("",mapObj[key]);
						}
					} 
				});
			});
			if(htmlStr == ""){
				htmlStr = "<div style='height:105px;line-height:105px;overflow:hidden;border:1px;text-align:center;font-size:25px ;'><strong>尚未标签设置</strong></div>";
			}
			$("#itemsDiv").html(htmlStr);
		});
	</script>
	</head>
	<body style="overflow: scroll;overflow-x: hidden;">
		<div class="container form-container">
		<div>
			<div id="itemsDiv" class="margin_top_25">
			</div>
		</div>
		</div>
	</body>
</html>