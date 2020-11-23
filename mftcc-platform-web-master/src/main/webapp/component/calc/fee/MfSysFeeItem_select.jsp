<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link type="text/css" rel="stylesheet" href="${webPath}/component/cus/css/MfCusType_MutiSel.css"/>
		<style type="text/css">
		.cursor-pointer{
			cursor: pointer;
		}
		
		</style>
	</head>
	<script type="text/javascript">
	var baseFeeItemList = eval("(" + '${json}' + ")").baseFeeItemList;
	var html = "<table><tbody>";
	var ind = 0;
	$(function(){
		//贷款用途的信息
		$.each(baseFeeItemList,function(i,baseFeeItem){
			var optCode = baseFeeItem.optCode;
			var optName = baseFeeItem.optName;
			ind++;
			if(ind == 1){
				html = html + "<tr class='sysKind-tr'><td>";
			}else{
				html = html + "<td>";
			}
			html = html + '<li class="risk_class_item item_true" data-optCode="'+optCode+'" data-optName="'+optName+'" title="'+optName+'" onclick="confirm(this);">'
					+'<span class="checkbox_true"><i class="i i-gouxuan"></i></span> '
					+'<label class="item_key"></label>'
					+'<lable style="padding-left: 5px;">'+optName+'</lable>'
					+'</li>';
			if(ind == 3){
				html = html + "</td></tr>";
				ind =0;
			}else{
				html = html + "</td>";
			}
		});
		if(ind != 3){
			html = html + "</tr>";
		}
		html = html +"</tbody></table>";
		$("#container").append(html);
		$("#container").height($("body").height()-95);
	});
	function confirm(obj){
		var optCode="";
		var optName="";
		var baseFeeItem = new Object();
		optCode = $(obj).data("optcode");
		optName = $(obj).data("optname");
		
		baseFeeItem.itemNo = optCode;
		baseFeeItem.itemName =optName;
		parent.dialog.get("sysFeeItemDialog").close(baseFeeItem);
	};
	</script>
	<body class="body_bg">
		<div class="container risk_class" id="container">
		</div>
	</body>
</html>