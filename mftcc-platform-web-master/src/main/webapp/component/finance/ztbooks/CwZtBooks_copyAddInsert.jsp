<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" /> --%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="<%=webPath %>/component/finance/ztbooks/js/CwZtBooks_List.js"></script>
		<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = eval("("+ajaxData+")");
			//alert(ajaxData);
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

			});
			function saveZtBooksAjax(obj){
				$("[name=weeks]").attr("mustinput","1");//给属性添加必填项
				cwZtBooksList.copyAddBooks(obj);
			}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">帐套表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="CwZtBooksForm" theme="simple" name="operform" action="${webPath}/cwZtBooks/copyAddBooksAjax">
							<dhcc:bootstarpTag property="formztbooks0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="saveZtBooksAjax('#CwZtBooksForm')"></dhcc:thirdButton>
	   		<%-- 	<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose_showDialog();"></dhcc:thirdButton> --%>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
	   			
	   		</div>	
		</div>
	</body>
	<script type="text/javascript">
		//alert('${dataMap.bookCode}');
		$(function(){
			//$("[name=weeks]").val("201701");
			//$('input[name=bookCode]').val('${dataMap.bookCode}');
			
			$("[name=weeks]").attr('onclick',"laydatemonth(this)");
			//$("[name=weeks]").addClass("i i-rili");
			/* $("[name=weeks]").addClass("cw-week"); */
			$("[name=weeks]").removeAttr("mustinput");
			//var weekd = $("[name=weeks]").parent();
			//var st = "<span class=\"input-group-addon\"><i class=\"i i-rili pointer\" onclick=\"weekclick()\"></i></span>";
			//weekd.append(st);
		});
 		 function weeksblur(){
			$("[name=weeks]").removeAttr("mustinput");
		 } 

	</script>
</html>
