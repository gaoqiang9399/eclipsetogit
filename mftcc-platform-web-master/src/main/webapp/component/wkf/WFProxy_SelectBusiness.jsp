<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	window.name = "curWindow";
			function cancelClick() {
				window.close();
			}
			
			function enterClick() 
			{
				var elements=document.getElementsByName("key");
				var selectedValue = ""
				for(var i=0; i<elements.length; i++) 
				{
					if(elements[i].checked)
					{
						var elementValue=elements[i].value;
						selectedValue=selectedValue+elementValue.split("=")[1]+",";
					}
				}
				selectedValue=selectedValue.substring(0,selectedValue.length-1);
				window.returnValue = selectedValue;
				window.close();
				return;
			}
		</script>
	<body class="body_bg">
	<form method="post" theme="simple" name="cms_form"
		action="${webPath}/wFProxy/selectBusiness" target="curWindow">
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v">
						<div style="vertical-align: bottom;" class="tabCont">
							<div style="float:left" class="tabTitle"></div>
							<input type="button" value="确定" onclick="enterClick()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="取消" onclick="cancelClick()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
						</div>
						<dhcc:tableTag paginate="pocessDefinitionList" property="tablewkf0009" head="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>