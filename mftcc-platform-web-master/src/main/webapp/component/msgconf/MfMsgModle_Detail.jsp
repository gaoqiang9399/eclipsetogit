<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript">
		var RECIVER= JSON.parse('${dataMap.reciverTypeItems}');
		var SENDTYPE= JSON.parse('${dataMap.sendTypeItems}');
		var VARUSAGE = JSON.parse('${dataMap.varUserageItems}');
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			// $("input[name=varUsageRel]").popupSelection({
			// 	searchOn:true,//启用搜索
			// 	inline:false,//下拉模式
			// 	multiple:true,//多选
			// 	labelShow:false,//选择区域显示已选择项
			// 	title : "信息类型",//标题
			// 	items:VARUSAGE
			// });
			$("input[name=recCusType]").popupSelection({		
				searchOn:true,//启用搜索
				inline:false,//下拉模式
				multiple:true,//多选
				labelShow:false,//选择区域显示已选择项
				title : "接收人",//标题
				items:RECIVER
			});	
			$("input[name=sendType]").popupSelection({		
				searchOn:true,//启用搜索
				inline:false,//下拉模式
				multiple:true,//多选
				labelShow:false,//选择区域显示已选择项
				title : "发送方式",//标题
				items:SENDTYPE
			});	
		  });
		  	
		function msgModelCallBack(data){
				$("textarea[name=cuslendWarnContent]").val(data);
		}
		function addMsgInfoModel1(){
			addMsgInfoModel(msgModelCallBack);
		};
		/**
		function warnTypeOnChange(obj){
			var warnType =  $(obj).val();
			if(warnType == "04"){
				$($("input[name=pliFreq]").parents("tr").get(0)).hide();
				$("input[name=pliFreq]").val("");	
				$($("select[name=pliFreqUnit]").parents("tr").get(0)).hide();				
				$("select[name=pliFreqUnit]").val("");
			}else{
				$($("input[name=pliFreq]").parents("tr").get(0)).show();
				$("input[name=pliFreq]").val("");	
				$($("select[name=pliFreqUnit]").parents("tr").get(0)).show();				
				$("select[name=pliFreqUnit]").val("");
			}
		}
		*/
		function addMsgInfoModel(callback){
		var cuslendWarnNo=$("input[name=cuslendWarnNo]").val();
		var varUsage=$("input[name=varUsageRel]").val();
		varUsage = varUsage.replace(/\|/g,",");
			dialog({
				id:"CuslendWarnDialog",
				title:'模板内容',
				url: webPath + '/cuslendWarning/getModelInputPageByTag.action?cuslendWarnNo='+cuslendWarnNo+'&varUsage='+varUsage,
				width:1200,
				height:470,
				backdropOpacity:0,
				onshow:function(){
					this.returnValue = null;
					},onclose:function(){
						if(this.returnValue){
							//返回对象的属性:opNo,opName;如果多个，使用@分隔
							if(typeof(callback)== "function"){
								callback(this.returnValue);
							}else{
							}
						}
					}
				}).showModal();
		};
		
		function saveMsgConfig(obj){
		var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				ajaxInsertCusForm(obj);
			}
		};
		</script>
	</head>
		<body class="overflowHidden bg-white">
			<div class="container form-container">
				<div class="scroll-content">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
			            	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="cuslendForm" theme="simple" name="operform" action="${webPath}/cuslendWarning/updateSMSAjax">
								<dhcc:bootstarpTag property="formmsgconfig0002" mode="query"/>
							</form>
						</div>
					</div>
				</div>
				
		
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="saveMsgConfig('#cuslendForm')"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>