<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<script type="text/javascript">
	var appId='${appId}';
	var pactId = '${dataMap.pactId}';
	/* 业务中登记担保证明信息和押品详情中登记使用一套表单，暂时不适用闭包， */
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			var groupNameLabel = $("input[name=groupName]").parents(".rows")
					.find(".form-label");
			var groupNameLabelText = $(groupNameLabel).text();
			$(groupNameLabel).empty().append(
					"<span class='required'>*</span>" + groupNameLabelText);
			$("input[name=groupName]").attr("mustinput", "1");
		});
		
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
		//验证业务关联押品是否都进行了押品评估
		function checkPledgeEvalFlag(obj) {
			//storageMergeFlag 是否入库选择入库 0否1是
			var storageMergeFlag=$("[name=storageMergeFlag]").val();
			//是否入库选择是时，验证业务关联押品是否都进行了押品评估
			if(storageMergeFlag=="1"){
				jQuery.ajax({
					url:webPath+"/mfBusCollateralRel/getPledgeEvalFlagAjax",
					data:{appId:appId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						//pledgeEvalFlag 0未添加押品评估1已添加押品评估。
						if(data.pledgeEvalFlag=='1'){
							insertCertiInfo(obj);
						}else if(data.pledgeEvalFlag=='0'){
							alert(top.getMessage("FIRST_OPERATION","押品评估"),0);
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}else{//不验证业务关联押品是否都进行了押品评估
				insertCertiInfo(obj);
			}
		}
		//保存担保登记信息
		function insertCertiInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						appId:appId
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							top.flag=true;
							top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
							}
							myclose_click();
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
		};
		//业务中跳过登记担保证明
		function submitBussProcessAjax() {
				LoadingAnimate.start();
				jQuery.ajax({
					url :webPath+"/certiInfo/submitBussProcessAjax",
					data : {
						appId : appId
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							top.flag=true;
							myclose_click();
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
	function validateDupCertiNo(){
				$.ajax({
					url:webPath+"/certiInfo/validateDupCertiNoAjax",
					data : {
						ajaxData : 	$("input[name=certiNo]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=certiNo]"),"权属证书编号已存在");
							$("input[name=certiNo]").val("");
						}
					},
					error : function(data) {
						$("input[name=certiNo]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};

    function minCertiDueDate() {
        var recDate = $("input[name=recDate]").val();
        var currentDay = new Date();
        //转换日期格式为2018-01-01（string）
        var year = currentDay.getFullYear();
        var mm = "0"+(currentDay.getMonth()+1);
        var day = "0"+currentDay.getDate();
        var currentdayString = year + "-" + mm.substring(mm.length-2,mm.length) + "-" + day.substring(day.length-2,day.length);
        if(recDate==""||recDate==null){
            return currentdayString;
        }else if(new Date(recDate.replace(/-/g, "/"))<=currentDay){
            return recDate;
        }
    };
    function maxRecDate() {
        var certiDueDate = $("input[name=certiDueDate]").val();
        var currentDay = new Date();
        //转换日期格式为2018-01-01（string）
        var year = currentDay.getFullYear();
        var mm = "0"+(currentDay.getMonth()+1);
        var day = "0"+currentDay.getDate();
        var currentdayString = year + "-" + mm.substring(mm.length-2,mm.length) + "-" + day.substring(day.length-2,day.length);
		if(certiDueDate==""||certiDueDate==null){
			return currentdayString;
		}else if(new Date(certiDueDate.replace(/-/g, "/"))<=currentDay){
			return certiDueDate;
		}else{
		    //alert("选的到期日期大于当前时间，默认返回登记日期的maxtime为当前日期");
            return currentdayString;
		}
    };

	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<c:choose>
							<c:when test='${dataMap.vouType=="1"}'>
								<div class="text-center">主担保方式为信用贷款，不需要进行担保登记</div>
							</c:when>
							<c:otherwise>
								<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
								<form  method="post" id="certiInfoInsert" theme="simple" name="operform" action="${webPath}/certiInfo/insertBusAjax">
									<dhcc:bootstarpTag property="formdlcertiinfo0002" mode="query"/>
								</form>	
							</c:otherwise>
						</c:choose>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>	
			</div>
				<div class="formRowCenter">
		   			<c:if test='${dataMap.vouType!="1"}'>
		   				<dhcc:thirdButton value="保存" action="保存" onclick="checkPledgeEvalFlag('#certiInfoInsert');"></dhcc:thirdButton>
		   			</c:if>
		   			<dhcc:thirdButton value="跳过" action="跳过" typeclass="cancel" onclick="submitBussProcessAjax('#certiInfoInsert');"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		   		</div>
   		</div>
	</body>
</html>
