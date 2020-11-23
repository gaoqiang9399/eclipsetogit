<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
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
			$("input[name=varUsageRel]").popupSelection({		
				searchOn:true,//启用搜索
				inline:false,//下拉模式
				multiple:true,//多选
				labelShow:false,//选择区域显示已选择项
				title : "信息类型",//标题
				items:VARUSAGE
			});	
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
				$("textarea[name=modelContent]").val(data);
		}
		function addMsgInfoModel1(){
			addMsgInfoModel(msgModelCallBack);
		};
		function addMsgInfoModel(callback){
		var listenerId=$("input[name=listenerId]").val();
		var varUsage=$("input[name=varUsageRel]").val();
		varUsage = varUsage.replace(/\|/g,",");
			dialog({
				id:"MfMsgListenerWarnDialog",
				title:'模板内容',
				url: webPath + '/mfMsgListener/getModelInputPage?listenerId='+listenerId+'&varUsage='+varUsage,
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
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">消息模板</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfMsgModleForm" theme="simple" name="operform" action="${webPath}/mfMsgModle/insert">
							<dhcc:bootstarpTag property="formmsgconfig0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave('#MfMsgModleForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
