<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
				
				<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusShareholder.js'> </script>
				<script type="text/javascript">
					var selectHtml;
					var options;
					var iValue;//股东类型初始值
					var iNo;//号码初始值
					$(function() {
						$(".scroll-content").mCustomScrollbar({
							advanced:{
								updateOnContentResize:true
							}
						});
						selectHtml=$("[name=idType]").parent().html();
						iValue=$("[name=shareholderType]:checked").val();//股东类型初始值
						iNo=$("[name=idNum]").val();
						options = $("select[name=idType]").find("option");
						if(iValue=="1"){//企业
							makeOptionsJQ(options, 'A,B,C',$("[name=idType]").val());
						}else if(iValue=="2"){//个人
							makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9',$("[name=idType]").val());
						}
						//股东类型选择组件
						$("select[name=shareholderType]").popupSelection({
								searchOn:true,//启用搜索
								inline:true,//下拉模式
								multiple:false//单选
						});
						//证件类型选择组件
						$("select[name=idType]").popupSelection({
								searchOn:true,//启用搜索
								inline:true,//下拉模式
								multiple:false//单选
						});
						//股东类型选择组件
						var PUSH_CAPITAL_TYPE =${ajaxData};
						$("input[name=pushCapitalType]").popupSelection({
								searchOn:true,//启用搜索
								inline:false,//下拉模式
								multiple:true,//多选
								items:PUSH_CAPITAL_TYPE
						});
						
						$(document).delegate("[name=shareholderType]","click",function(){
							$("[name=idType]").parent().html(selectHtml);
							options = $("select[name=idType]").find("option");
							if(this.value=="1"){
								if(this.value==iValue){//是初始值,需要传默认选中值
									makeOptionsJQ(options, 'A,B,C',$("[name=idType]").val());
									$("[name=idNum]").val(iNo);
								}else{
									makeOptionsJQ(options, 'A,B,C');
									$("[name=idNum]").val("");
								}
							}else if(this.value=="2"){
								if(this.value==iValue){
									makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9',$("[name=idType]").val());
									$("[name=idNum]").val(iNo);
								}else{
									makeOptionsJQ(options, '0,1,2,3,4,5,6,7,8,9');
									$("[name=idNum]").val("");
								}
							}
							$("[name=idType]").popupSelection({
									searchOn:true,//启用搜索
									inline:true,//下拉模式
									multiple:false//单选
							}); 
						})
					});
				</script>
	</head>
		<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="cusShareholderUpdate" theme="simple" name="operform" action="${webPath}/mfCusShareholder/updateAjax">
						<dhcc:bootstarpTag property="formcussha00003" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="saveCusShareholder('#cusShareholderUpdate','update')"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>