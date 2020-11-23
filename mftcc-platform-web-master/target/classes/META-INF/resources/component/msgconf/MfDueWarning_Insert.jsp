<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
		var WARNINF_TYPE= JSON.parse('${dataMap.warningTypeItems}');
		var SENDTYPE= JSON.parse('${dataMap.sendTypeItems}');
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});

			$("input[name=cuslendWarnType]").popupSelection({
				searchOn:true,//启用搜索
				inline:false,//下拉模式
				multiple:false,//多选
				labelShow:false,//选择区域显示已选择项
				title : "预警类型",//标题
				items:WARNINF_TYPE,
                handle : BASE.getIconInTd($("input[name=cuslendWarnType]")),
                changeCallback : function (elem) {
                   var cuslendWarnType = $("input[name=cuslendWarnType]").val();
                   var url = webPath+"/cuslendWarning/getListByCuslendWarningTypeAjax"
                    jQuery.ajax({
                        url:url,
                        data:{cuslendWarnType:cuslendWarnType},
                        type:"POST",
                        dataType:"json",
                        beforeSend:function(){
                        },success:function(data){
                            if(data != null && data !=""){
                                var receiverArrayItems= JSON.parse(data.receiverArrayItems);
                                if(receiverArrayItems !=null && receiverArrayItems != ""){

                                    $("input[name=recCusType]").popupSelection({
                                        searchOn:false,//启用搜索
                                        inline:false,//下拉模式
                                        multiple:true,//多选
                                        labelShow:false,//选择区域显示已选择项
                                        title : "接收人",//标题
                                        items:receiverArrayItems
                                    });
                                }
                            }
                        },error:function(data){
                            LoadingAnimate.stop();
                            window.top.alert(data.msg,0);
                        }
                    });
                }
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
            arr=data.split('|');
			$("textarea[name=cuslendWarnContent]").val(arr[0]);
            $("input[name=cuslendContentArgs]").val(arr[1]);
		}

		function addMsgInfoModel1(){
			addMsgInfoModel(msgModelCallBack);
		};
		function addMsgInfoModel(callback){
		var cuslendWarnType=$("input[name=cuslendWarnType]").val();
		//var recCusType=$("input[name=recCusType]").val();
            //recCusType = recCusType.replace(/\|/g,",");
			dialog({
				id:"CuslendWarnDialog",
				title:'模板内容',
				url: webPath + '/cuslendWarning/getWarningModelInputPage?cuslendWarnType='+cuslendWarnType,
				width:1200,
				height:470,
				backdropOpacity:0,
				onshow:function(){
					this.returnValue = null;
					},onclose:function(){
						if(this.returnValue){
							//
							if(typeof(callback)== "function"){
								callback(this.returnValue);
							}else{
							}
						}
					}
				}).showModal();
		};
		
		function saveCuslengWarn(obj){
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
						<div class="form-title"></div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfDueWarning" theme="simple" name="operform" action="${webPath}/cuslendWarning/insertCuslendWarnAjax">
							<dhcc:bootstarpTag property="formduewarningbase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="saveCuslengWarn('#MfDueWarning')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>