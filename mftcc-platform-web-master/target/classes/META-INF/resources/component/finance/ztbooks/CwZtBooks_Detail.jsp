<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>帐套详情</title>
		<script type="text/javascript" src="<%=webPath %>/component/finance/ztbooks/js/CwZtBooks_List.js"></script>
		<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = eval("("+ajaxData+")");
		$(function(){
			$("input[name=useOpNo]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:true,//dan选选
				items:ajaxData.userArray,
				changeCallback : function (obj) {//回调
				}
			});
			$("input[name=useBrNo]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//false-单选,true-复选
				items:ajaxData.brArray,
				changeCallback : function (obj) {//回调
				}
			});
			var ztno = $("#ztNo").val();
			 if(ztno=='basic'){
				$("[name=useFlag]").parents("tr").hide();
				//$("[name=popsuseOpNo]").parents("tr").hide();
				$("[name=useOpNo]").parents("tr").hide();
				$("[name=useFlag]").removeAttr("mustinput");
				$("[name=useOpNo]").removeAttr("mustinput");
				$("[name=useOpNo]").val("basic");
			} 
		});
		function ajaxUpdateThis(obj){
			cwZtBooksList.uptZtBooksAjax(obj);
		}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-8 col-md-offset-2">
					<div class="bootstarpTag">
						<!-- <div class="form-title">帐套表</div> -->
						<form method="post" id="CwZtBooksFormDetail" theme="simple" name="operform" action="${webPath}/cwZtBooks/updateAjax">
							<dhcc:bootstarpTag property="formztbooks0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   		<input id="ztNo" type="hidden" value='${finBooks}'/>
	   		<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#CwZtBooksFormDetail');"></dhcc:thirdButton> 
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
</html>