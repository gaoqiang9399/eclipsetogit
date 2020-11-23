<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
        <script type="text/javascript" src="${webPath}/component/collateral/js/realEstateAssessment.js"></script>
        <script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js"></script>
	</head>
	<script type="text/javascript">
		//var classNo = '${classNo}';
		//var pledgeMethod = '${pledgeMethod}';
		var vouType = '${vouType}';
		var ajaxData = '${ajaxData}';
	    ajaxData =JSON.parse(ajaxData);
	    var  isQuote ="0";
	    var entrFlag="";//入口标示，business业务，assets押品,credit 授信
	    var entrance="collateral";//入口标示，business业务，collateral押品
	    var classFirstNo='${classFirstNo}';//一级类别
		var classId;//押品编号
        var evalState;//估值状态
        var evalTaskNum;//估值任务编号
		$(function() {
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced:{
                    updateOnContentResize:true
                }
            });

            //担保方式
            $("select[name=pledgeMethod]").popupSelection({
                searchOn:false,//启用搜索
                inline:true,//下拉模式
                multiple:false,//多选选
                changeCallback : function (obj, elem) {
                    CollateralCommon.refreshFormByVouType("","",obj.val(),"");
                }
            });

			//押品新组件
            if (!$("input[name='classId']").is(":hidden")) {
                $("input[name=classId]").popupSelection({
                    searchOn: true,//启用搜索
                    inline: true,//下拉模式
                    multiple: false,//多选选
                    items: ajaxData.collClass,
                    changeCallback: function (obj, elem) {
                        $("input[name=classSecondName]").val(obj.data("text"));
                        CollateralCommon.freshPledgeBaseForm("", "", "");
                    }
                });
            }

			//给主折扣率和次折扣率添加格式化保留两位小数的操作
			$("input[name='extNum03']").bind("blur",function(){
			  formatToTwo(this);
			});
			$("input[name='extNum04']").bind("blur",function(){
			  formatToTwo(this);
			});
            CollateralCommon.initOnCardsCity();
	});

	function formatToTwo(obj){
		var numberValue = parseFloat($(obj).val());
		$(obj).val(numberValue.toFixed(2));
	}

	function freshPledgeBaseInfo(){
		var classId = $("select[name=classId]").val();
		var dataParam = JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
		$.ajax({
			url:webPath+"/pledgeBaseInfo/freshPledgeFormAjax",
			data:{classId:classId,ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){},
			success:function(data){
				$("#pledgeBaseInfoInsert").empty().html(data.formHtml);
				//var option = $("select[name=classNo]").find("option");
				//makeOptionsJQ(option, data.classNo, data.pledgeMethod);
			},
			error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};

	function insertPledgeBaseInfo(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {ajaxData : dataParam,isQuote:isQuote},
				type : "POST",
				dataType : "json",
				beforeSend : function() {},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						var pledgeNo=data.pledgeNo;
						var url=webPath+"/pledgeBaseInfo/getById?pledgeNo="+pledgeNo;
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						//window.top.alert(data.msg, 1);
						myclose_showDialog();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};


        function insertHousePledgeBaseInfo(obj,skipFlag) {
            var flag = submitJsMethod($(obj).get(0), '');
            if(skipFlag=="2"){//人工申请评估只支持个人用房-住宅和别墅
                var houseType=$("select[name=houseType]").val();
                if(houseType =="10008"){

                }else if(houseType=="10009"){

                }else {
                    alert("人工申请评估只支持个人用房-住宅和别墅！", 0);
                    return false;
                }
            }
            if (flag) {
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                LoadingAnimate.start();
                jQuery.ajax({
                    url : url,
                    data : {ajaxData : dataParam,isQuote:isQuote},
                    type : "POST",
                    dataType : "json",
                    beforeSend : function() {},
                    success : function(data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            var pledgeNo=data.pledgeNo;
                            var url=webPath+"/pledgeBaseInfo/getById?pledgeNo="+pledgeNo;
                            $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
                            if(classId=="17062211213501712" || classId=="17061309580618115"){
                                if(evalState=="1" && skipFlag=="1"){
                                    CollateralInsert.onlineEvalStorage(data.pledgeNo)//在线评级结果存储
                                }
                                if(skipFlag=="2"){
                                    CollateralInsert.assessmentApply(data.pledgeNo);//人工申请
                                }
                            }
                            myclose_showDialog();
                        } else if (data.flag == "error") {
                            alert(data.msg, 0);
                        }
                    },error : function(data) {
                        LoadingAnimate.stop();
                        alert(top.getMessage("FAILED_OPERATION"," "), 0);
                    }
                });
            }
        };

	function cancelInsert(){
		window.location.href=webPath+"/mfBusCollateral/getListPage";
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
	function validateDupExtOstr04(){
				$.ajax({
					url:webPath+"/pledgeBaseInfo/validateDupExtOstr04Ajax",
					data : {
						ajaxData : 	$("input[name=extOstr04]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=extOstr04]"),"发票号码已存在");
							$("input[name=extOstr04]").val("");
						}
					},
					error : function(data) {
						$("input[name=extOstr04]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};

	function validateDupExtOstr07(){
				$.ajax({
					url:webPath+"/pledgeBaseInfo/validateDupExtOstr07Ajax",
					data : {
						ajaxData : 	$("input[name=extOstr07]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=extOstr07]"),"订单号码已存在");
							$("input[name=extOstr07]").val("");
						}
					},
					error : function(data) {
						$("input[name=extOstr07]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};

	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<c:if test='${entranceType eq "assets"}'>
							<div class="form-title">资产登记</div>
						</c:if>
						<c:if test='${entranceType eq "account"}'>
							<div class="form-title">应收账款登记</div>
						</c:if>
						<c:if test='${entranceType eq "lease"}'>
							<div class="form-title">租赁物登记</div>
						</c:if>
		           		<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/pledgeBaseInfo/insertAjax">
							<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						</form>
					</div>
                    <div disable="true" class="content collapse in"  id="valuationResults" style="margin-top: 10px; display:block;">
                        <div class="form-tips" id="requesTitle" style="display:none">在线评估结果，请勿修改。</div>
                        <form method="post" id="onlineDlevalinfo0002" theme="simple" name="operform" >
                        </form>
                    </div>
				</div>
			</div>
            <div class="formRowCenter" id="normal" style="display:block">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertPledgeBaseInfo('#pledgeBaseInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="cancelInsert();"></dhcc:thirdButton>
	   		</div>
            <div class="formRowCenter" id="artificial" style="display:none">
                <dhcc:thirdButton value="保存" action="保存" onclick="insertHousePledgeBaseInfo('#pledgeBaseInfoInsert','1');"></dhcc:thirdButton>
                <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="cancelInsert();"></dhcc:thirdButton>
				<%-- 房产人工估值（需要权限） --%>
				<dhcc:pmsTag pmsId="assets-artificial-apply">
                	<dhcc:thirdButton value="人工估值 " action="人工估值 " typeclass="cancel" onclick="insertHousePledgeBaseInfo('#pledgeBaseInfoInsert','2');"></dhcc:thirdButton>
				</dhcc:pmsTag>
            </div>
   		</div>
	</body>
</html>
