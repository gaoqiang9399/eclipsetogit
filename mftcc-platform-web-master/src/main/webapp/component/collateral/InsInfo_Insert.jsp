<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>

	</head>
	<script type="text/javascript">
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					theme : "minimal-dark",
// 					updateOnContentResize : true
// 				}
// 			});
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			var groupNameLabel = $("input[name=groupName]").parents(".rows")
					.find(".form-label");
			var groupNameLabelText = $(groupNameLabel).text();
			$(groupNameLabel).empty().append(
					"<span class='required'>*</span>" + groupNameLabelText);
			$("input[name=groupName]").attr("mustinput", "1");
			$("input[name=insEnd]").val('${busEndDate}');
			//$("input[name=insEnd]").attr("onclick", "fPopUpCalendarDlg({min: '${busEndDate}',init:true});");
			//页面加载时判断投保时间是否有值
            var time = 	$("input[name=insStart]").val();
            $("#start_i").attr("onclick", "selectrili(this,null,null,null,rollBackStartTime)");
            if(time==null || time==''){//无值时 给投保到期时间添加无限制时间选择事件
                $("#end_i").attr("onclick", "selectrili(this,null,null,null,rollBackEndTime)");
            }
		});
		//投保时间 回调
		function rollBackStartTime(){
		    var time = 	$("input[name=insStart]").val();
            $("#end_i").attr("onclick", "selectrili(this,null,'"+time+"',null,rollBackEndTime)");
		};
		//保险到期时间 回调
		function rollBackEndTime(){
            var timeend = $("input[name=insEnd]").val();
            $("#start_i").attr("onclick", "selectrili(this,null,null,'"+timeend+"',rollBackStartTime)");
		};

        function getLegalIdType() {
			var legalIdType = $("select[name =legalIdType]").val();
			if (legalIdType == "0") {
				$("input[name=legalIdNum]").attr("alt", "idnum");
			} else {
				$("input[name=legalIdNum]").attr("alt", "tmp");
			}
			$("input[name=legalIdNum]").val("");
		};

		function ifGroupCustomer(obj) {
			var ifGroupType = $(obj).val();
			if (ifGroupType == 0) {//非集团客户
				$("input[name=groupName]").attr("mustinput", "0");
				$("input[name=groupName]").parents(".rows").hide();
			} else {//集团客户
				$("input[name=groupName]").attr("mustinput", "1");
				$("input[name=groupName]").parents(".rows").show();
			}
		}

		function insertInsInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
                            window.top.alert(data.msg, 1);
                            //					  alert(top.getMessage("SUCCEED_OPERATION"),1);
							top.addFlag = true;
                            top.htmlStrFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
								top.updateFlag = true;
								top.tableName = "ins_info";
							}
							myclose_click();
							//window.close();
							//myclose_showDialogClick();
							if (callback && typeof (callback) == "function") {
								callback.call(this, data);
							}
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		}

		function getCusMngNameDialog(userInfo) {
			$("input[name=cusMngName]").val(userInfo.opName);
			$("input[name=cusMngNo]").val(userInfo.opNo);
		};

		//从集团客户放大镜赋值给表单属性
		function getGroInfoArtDialog(groupInfo) {
			$("input[name=groupName]").val(groupInfo.groupName);
			$("input[name=groupNo]").val(groupInfo.groupNo);
			$("input[name='groupName']").attr("readonly", true);
		};

		function selectAreaCallBack(areaInfo) {
			$("input[name=careaProvice]").val(areaInfo.disName);
		};
			//表单信息提示
	function func_uior_addTips(obj,msg){
		var $this =$(obj);
		var val = $this.val();
		if ($this.hasClass("Required")) {
			$this.removeClass("Required");
		}
		if($this.parent().find(".Required-font").length>0){
			$this.parent().find(".Required-font").remove();
		}
		//if(val==null||val==""||typeof(val)=="undefined"){
			//var $label = $('<label class="Required-font"><i class="i i-jingbao"></i>'+msg+'</label>');
			var $label = $('<div class="error required">'+msg+'</div>');
			$label.appendTo($this.parent());
			$this.addClass("Required");
		//}
		$this.one("focus.addTips", function(){
			$label.remove();
		});
	};
	function validateDupInsNo(){
				$.ajax({
					url:webPath+"/insInfo/validateDupInsNoAjax",
					data : {
						ajaxData : 	$("input[name=insNo]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=insNo]"),"保单编号已存在");
							$("input[name=insNo]").val("");
						}
					},
					error : function(data) {
						$("input[name=fairNo]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};
	</script>
	<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="insInfoInsert" theme="simple" name="operform" action="${webPath}/insInfo/insertAjax">
						<dhcc:bootstarpTag property="formdlinsinfo0002" mode="query"/>
					</form>	
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertInsInfo('#insInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
