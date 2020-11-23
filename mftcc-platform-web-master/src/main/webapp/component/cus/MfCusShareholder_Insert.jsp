<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusShareholder.js'> </script>
	</head>
	<script type="text/javascript">
		var sysProjectName = "${sysProjectName}";
		var registeredCapital = "${registeredCapital}";//企业的注册资本
		$(function() {
			init();
			/* //证件类型选择组件
			$("select[name=idType]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false//单选
					
			});   */ 
			//出资方式选择组件
			var PUSH_CAPITAL_TYPE =JSON.parse('${ajaxData}');
			$("input[name=pushCapitalType]").popupSelection({
					title:true, //标题
					searchOn:true,//启用搜索
					inline:false,//弹窗模式
					multiple:true,//多选
					labelShow:false,//选择区域显示已选择项
					items:PUSH_CAPITAL_TYPE
			});
			/* 影响多选框，暂时去掉 */
			
			/* new LeasePopCheckBox({
					elem:"input[name=pushCapitalType]",
					tagType:"formTag",//普通表单标签（横向表单）
					btn:function(){
						window.top.alert(11111,0);
					},
					selectType:true,
					className:"singleSelector"//复选样式·
				}); */
		/* 	$(document).delegate("[name=shareholderType]","click",function(){
				$("[name=idType]").parent().html(selectHtml);
				options = $("select[name=idType]").find("option");
				if(this.value=="1"){
					makeOptionsJQ(options, 'A,B,C');
					$("[name=idType]").popupSelection({
							searchOn:true,//启用搜索
							inline:true,//下拉模式
							multiple:false//单选
					}); 
				}else if(this.value=="2"){
					makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9');
					$("[name=idType]").popupSelection({
							searchOn:true,//启用搜索
							inline:true,//下拉模式
							multiple:false
					}); 
				}
			}) */
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusShareholderInsert" theme="simple" name="operform" action="${webPath}/mfCusShareholder/insertAjax">
							<dhcc:bootstarpTag property="formcussha00003" mode="query"/>
						</form>
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="saveCusShareholder('#cusShareholderInsert','insert1');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
