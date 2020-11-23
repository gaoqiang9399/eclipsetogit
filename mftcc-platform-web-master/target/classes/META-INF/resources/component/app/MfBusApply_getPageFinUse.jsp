<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
		.cursor-pointer{
			cursor: pointer;
		}
		
		</style>
	</head>
	<script type="text/javascript">
	var tradeList = eval("(" + '${json}' + ")").tradeList;
	$(function(){
		//贷款用途的信息
		var htmlStr = '<table style="margin-left: 10px;margin-top:10px;font-size:12px;"><tbody>';
		$.each(tradeList,function(i,trade){
			if((i +1)%3 ==0 ){
				htmlStr = htmlStr + '<td><input type="radio" onclick="getFincUse(this);" data-option="'+trade.optCode+'"  value="'+trade.optCode+'" title="'+trade.optName+'"  class="cursor-pointer" ></td>	<td data-option="'+trade.optCode+'" onclick="getFincUse(this);" title="'+trade.optName+'"  style="text-align:left;width:30%;"  class="cursor-pointer">'+trade.optName+'</td></tr>';
			}else if((i +1)%3 ==1 ){
				htmlStr = htmlStr + '<tr style="height:30px;">'
						+ '<td > <input type="radio"   value="'+trade.optCode+'" onclick="getFincUse(this);" data-option="'+trade.optCode+'"  title="'+trade.optName+'"  class="cursor-pointer"></td>	<td  data-option="'+trade.optCode+'" onclick="getFincUse(this);" title="'+trade.optName+'" style="text-align:left;width:30%;"  class="cursor-pointer">'+trade.optName+'</td>';
			}else{
				htmlStr = htmlStr + '<td> <input type="radio" data-option="'+trade.optCode+'"  value="'+trade.optCode+'" onclick="getFincUse(this);"  title="'+trade.optName+'"  class="cursor-pointer"></td>	<td  data-option="'+trade.optCode+'" onclick="getFincUse(this);"  title="'+trade.optName+'"  style="text-align:left;width:30%;"  class="cursor-pointer">'+trade.optName+'</td>';
			}
			if((i +1)%3 !=0 && (i +1)==tradeList.length){
				htmlStr = htmlStr + '</tr>';
			}
		});
		htmlStr = htmlStr + '</tbody></table>';
		$("#fincUse-div").append(htmlStr);
		$("input[type=radio]").attr("checked",false);
	});
	function getFincUse(obj){
		var fincUse = new Object();
		fincUse.fincUse = $(obj).data("option");
		fincUse.fincUseShow = $(obj).attr("title");
		//这里必须把dialog给remove掉
		//使用close()只会使弹窗不可见，并不会清除DOM元素，下次再次打开时上次选中的选项仍然在选中状态
		parent.dialog.get("fincUseDialog").close(fincUse).remove();
	};
	
	</script>
	<body class="body_bg">
		<div  id="fincUse-div">
			
		</div>
	</body>
</html>