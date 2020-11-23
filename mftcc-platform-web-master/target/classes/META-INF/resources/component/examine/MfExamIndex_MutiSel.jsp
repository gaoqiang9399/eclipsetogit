<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link type="text/css" rel="stylesheet" href="${webPath}/component/cus/css/MfCusType_MutiSel.css"/>
<style type="text/css">

</style>
<script type="text/javascript">
	$(function(){
		//这是已选择的客户类型
		var indexNo = '${indexNo}';
		indexNo = indexNo.split("@");
		var dataMap = <%=request.getAttribute("dataMap")%>;
		var examIndexList = dataMap.examIndexList;
		var html = "<table><tbody>";
		var ind = 0;
		$.each(examIndexList,function(i,examIndex){
			ind++;
			if(ind == 1){
				html = html + "<tr class='sysKind-tr'><td>";
			}else{
				html = html + "<td>";
			}
			html = html + '<li class="risk_class_item item_false" data-indexNameEn="'+examIndex.indexNameEn+'" data-indexName="'+examIndex.indexName+'" title="'+examIndex.indexName+'" onclick="getNotSelectedItem(this);">'
					+'<span class="checkbox_false"><i class="i i-gouxuan"></i></span> '
					+'<label class="item_key"></label>'
					+'<lable style="padding-left: 5px;">'+examIndex.indexName+'</lable>'
					+'</li>';
			if(ind == 4){
				html = html + "</td></tr>";
				ind =0;
			}else{
				html = html + "</td>";
			}
		});
		if(ind != 4){
			html = html + "</tr>";
		}
		html = html +"</tbody></table>";
		$("#container").append(html);
		
		$.each(indexNo,function(i,indexNoThis){
			if(indexNoThis){
				var $obj = $("li[data-indexNameEn="+indexNoThis+"]");
				var spanCheckBox = $obj.find("span");
				spanCheckBox.removeClass("checkbox_false");
				spanCheckBox.addClass("checkbox_true");
				$obj.removeClass("item_false");
				$obj.addClass("item_true");
			}
		});
		
		$("#container").height($("body").height());
	 });
	function getNotSelectedItem(obj){
		var spanCheckBox = $(obj).find("span");
		var checkbox_false = spanCheckBox.hasClass("checkbox_false");
		if(checkbox_false){
			spanCheckBox.removeClass("checkbox_false");
			spanCheckBox.addClass("checkbox_true");
			$(obj).removeClass("item_false");
			$(obj).addClass("item_true");
		}else{
			spanCheckBox.removeClass("checkbox_true");
			spanCheckBox.addClass("checkbox_false");
			$(obj).removeClass("item_true");
			$(obj).addClass("item_false");
		}
	};
	function confirm(){
		var indexNameEn="";
		var indexName="";
		var examIndex = new Object();
		var length = $("li.risk_class_item.item_true").length;
		$("li.risk_class_item.item_true").each(function(i,objThis){
			if(i == length-1){
				indexNameEn = indexNameEn + $(objThis).data("indexnameen");
				indexName = indexName + $(objThis).data("indexname");
			}else{
				indexNameEn = indexNameEn + $(objThis).data("indexnameen")+"@";
				indexName = indexName + $(objThis).data("indexname")+"@";
			}
		});
		examIndex.indexNameEn = indexNameEn;
		examIndex.indexName =indexName;
		parent.dialog.get("examIndexMutiDialog").close(examIndex);
	};
</script>
</head>
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container risk_class" id="container">
			
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="confirm();"></dhcc:thirdButton>
		</div>
	</body>
</html>