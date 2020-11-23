<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer.js'> </script>
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer_EntList.js'> </script>
	</head>
    <style type="text/css">
		.input-box .block-view{
			right:25px;
		}
	</style>
	<script type="text/javascript">
	var options;
	var cusType = '${cusType}';
	var appId = '${appId}';	
	var selectFlag = false;
	var ajaxData = '${ajaxData}';
	var baseType = '${baseType}';
	//ajaxData = JSON.parse(ajaxData);
	$(function(){
		$("form").attr("action","${webPath}/mfBusApply/insertBusPartAjax?appId="+appId);
		options = $("select[name=idType]").find("option");
		getIdType();
		//客户类型选择组件
	      $("select[name=cusType]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//弹窗模式
				multiple:false,//单选
				//changeCallback:chooseCusType1
		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('[name=cusSubType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			items:ajaxData.cusSubType
		});
	});
	function getIdType(){
		var isThreeToOne = $("select[name=isThreeToOne]").val();
		if (isThreeToOne == "0") {
			makeOptionsJQ(options, 'A,C');
			$("select[name=idType]").val("A");
		} else if (isThreeToOne == "1") {
			makeOptionsJQ(options, 'B');
			$("select[name=idType]").val("B");
		}
		idTypeChange();
	};
	function idTypeChange(){
// 		var idType = $("select[name=idType]").val()
// 		if(idType=="A"){
// 			$("input[name=idNum]").attr("alt","organ");
// 		}else if(idType=="B"){
// 			$("input[name=idNum]").attr("alt","credit");
// 		}else if(idType=="C"){
// 			$("input[name=idNum]").attr("alt","licence");
// 		}
// 		$("input[name=idNum]").val("");
	};
	window.ajaxInsert1 = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			insertBusPart(obj);
		}
	};
	
	function insertBusPart(obj){
		var url = webPath+"/mfCusCustomer/selectForBusAjax";
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,appId:appId,baseType:baseType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 top.flag = true;
					 myclose_click();
				} else {
					alert(data.msg, 0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	function insertCallBack(data){
		top.addCusFlag = true;
		var cusInfo = new Object();
		cusInfo.cusNo = data.cusNo;
		cusInfo.cusName = $("input[name=cusName]").val();
		cusInfo.contactsTel = $("input[name=contactsTel]").val();
		cusInfo.contactsName = $("input[name=contactsName]").val();
		cusInfo.commAddress = $("textarea[name=commAddress]").val();
		top.cusInfo = cusInfo;
		
		myclose_click();
	};
	function getCusInfoArtDialog(cusInfo){
		//cusInfo不为空,说明选择放大镜中客户
		if(cusInfo!=null){
			selectFlag=true;
		}
		$("input[name=cusNo]").val(cusInfo.cusNo);
		$.ajax({
				url :"${webPath}/mfCusCorpBaseInfo/getByIdAjax",
				data : {
					cusNo : cusInfo.cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						$("input[name=registeredCapital]").val(data.mfCusCorpBaseInfo.registeredCapital).attr("readonly",true);
						var beginDate = data.mfCusCorpBaseInfo.beginDate;
						var endDate = data.mfCusCorpBaseInfo.endDate;
						if(beginDate!=""&&beginDate!=null){
							beginDate = beginDate.substring(0,4)+"-"+beginDate.substring(4,6)+"-"+beginDate.substring(6);
							$("input[name=beginDate]").val(beginDate).attr("readonly",true);
							$("input[name=beginDate]").removeAttr("onclick");							
						}
						if(endDate!=""&&endDate!=null){							
						 	endDate = endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6);							
							$("input[name=endDate]").val(endDate).attr("readonly",true);
							$("input[name=endDate]").removeAttr("onclick");
						}
						$("input[name=postalCode]").val(data.mfCusCorpBaseInfo.postalCode).attr("readonly",true);						
						$("input[name=legalRepresName]").val(data.mfCusCorpBaseInfo.legalRepresName).attr("readonly",true);
					
						$("input[name=cusName]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=idNum]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=registeredCapital]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=beginDate]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=endDate]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=postalCode]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=legalRepresName]").parent().find("span[class='wrap-placeholder']").remove();
						$("input[name=contactsTel]").parent().find("span[class='wrap-placeholder']").remove();
					} else {
					}
				},
			});		
		$("input[name=cusName]").val(cusInfo.cusName).attr("readonly",true);
		$("input[name=cusName]").val(cusInfo.cusName).attr("readonly",true);
		$("input[name=idNum]").val(cusInfo.idNum).attr("readonly",true);
		$("input[name=contactsTel]").val(cusInfo.contactsTel).attr("readonly",true);
		$("input[name=contactsName]").val(cusInfo.contactsName).attr("readonly",true);
		$("input[name=commAddress]").val(cusInfo.commAddress).attr("readonly",true);
// 		$("input[type=text]").attr("readonly",true);
		$(".input-box .block-view").remove(); 
	};
	function checkCredit(cusInfo){
		var cusNo = "";
		if(baseType == "3"){//核心企业
			cusNo = cusInfo.coreCompanyUid
		}
		$.ajax({
			url:webPath+"/mfCusCreditContract/checkCreditByCusNoAjax?cusNo=" + cusNo + "&appId=" + appId + "&baseType=" + baseType,
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					  if(baseType=="3"){//核心企业
	                        getCusCoreCompanyInfo(cusInfo);
						}else{
	                        getCusInfoArtDialog(cusInfo);
	                    }
				}else{
					window.top.alert(data.msg,3);
				}
			},error : function() {
				alert(data.msg,0);
			}
		});
	}

    function getCusCoreCompanyInfo(cusInfo){
        //cusInfo不为空,说明选择放大镜中客户
        if(cusInfo!=null){
            selectFlag=true;
        }
        $("input[name=cusNo]").val(cusInfo.coreCompanyUid);
        $.ajax({
            url :"${webPath}/mfCusCoreCompany/getByCoreCompanyUidAjax",
            data : {
                coreCompanyUid : cusInfo.coreCompanyUid
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    $("input[name=registeredCapital]").val(data.mfCusCoreCompany.registeredCapital).attr("readonly",true);
                    var beginDate = data.mfCusCoreCompany.beginDate;
                    var endDate = data.mfCusCoreCompany.endDate;
                    if(beginDate!=""&&beginDate!=null){
                        beginDate = beginDate.substring(0,4)+"-"+beginDate.substring(4,6)+"-"+beginDate.substring(6);
                        $("input[name=beginDate]").val(beginDate).attr("readonly",true);
                        $("input[name=beginDate]").removeAttr("onclick");
                    }
                    if(endDate!=""&&endDate!=null){
                        endDate = endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6);
                        $("input[name=endDate]").val(endDate).attr("readonly",true);
                        $("input[name=endDate]").removeAttr("onclick");
                    }
                    $("input[name=postalCode]").val(data.mfCusCoreCompany.postalCode).attr("readonly",true);
                    $("input[name=legalRepresName]").val(data.mfCusCoreCompany.legalRepresName).attr("readonly",true);

                    $("input[name=cusName]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=idNum]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=registeredCapital]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=beginDate]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=endDate]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=postalCode]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=legalRepresName]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=contactsTel]").parent().find("span[class='wrap-placeholder']").remove();
                    $("input[name=cusName]").val(cusInfo.coreCompanyName).attr("readonly",true);
                    $("input[name=idNum]").val(data.mfCusCoreCompany.socialCreditCode).attr("readonly",true);
                    $("input[name=contactsTel]").val(cusInfo.coreCompanyButtPhone).attr("readonly",true);
                    $("input[name=contactsName]").val(cusInfo.coreCompanyButtName).attr("readonly",true);
                    $("input[name=commAddress]").val(cusInfo.commAddress).attr("readonly",true);
                    $("input[name=creditSum]").val(data.mfCusCoreCompany.creditSum).attr("readonly",true);
                    $("input[name=authBal]").val(data.mfCusCoreCompany.authBal).attr("readonly",true);
                } else {
                    window.top.alert(data.msg, 3);
                }
            },
        });

// 		$("input[type=text]").attr("readonly",true);
        $(".input-box .block-view").remove();
    };
	function selectCusData(){
		cusType = $("input[name=cusType]").val();
		selectCusDialog(getCusInfoArtDialog,cusType,null,"1");
	}
	//选择核心企业并校验其是否授信
	function selectCusAndCheckCredit(){
		if(baseType == "3"){
			selectCusCoreCompanyDialog(checkCredit);
		}else{
			cusType = $("input[name=cusType]").val();
			selectCusDialog(checkCredit,cusType,null,"1");
		}
	}
	function selectAreaCallBack(areaInfo){
		$("input[name=commAddress]").val(areaInfo.disName);
	};
	//注册资本不能输入负数 
	function controlMax(){
		var registeredCapitalVal = $("input[name='registeredCapital']").val();
		if(registeredCapitalVal>="0"){
			
		}else{
			$("input[name='registeredCapital']").val("");
		}
		
	}
	function chooseCusType1(){
		var val = $("input[name=cusType]").val();
		var tmpUrl="/mfCusCustomer/input2?cusType="+val +"&appId="+appId;
		document.location.href=tmpUrl;
	}
	function skipSubmit(){
		alert(top.getMessage("CONFIRM_OPERATION","跳过此节点"),2,function(){
			LoadingAnimate.start();
		$.ajax({
				url:webPath+"/mfBusApply/commitBusProcessAjax?appId="+appId,
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						top.flag = true;
						window.top.alert(data.msg, 3);
						myclose_click();
					}else if (data.flag == "checkError") {
						window.top.alert(data.msg, 3);
					}
				},error : function() {
					alert(data.msg,0);
				}
			});
		});
	};
	function getNextBusPoint(){
		$(".block-next").empty();
		$(".block-next").unbind();
		var busPointInfo = '';
		$.ajax({
			url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.wkfFlag!="0"){
					appSts = data.mfBusApply.appSts;
					var approveInfo="";
					var approveNodeName=null;
					var approvePartName=null;
					if(appSts == '1' || appSts == '6'){
						approveNodeName=data.mfBusApply.approveNodeName;
						approvePartName=data.mfBusApply.approvePartName;
						if(approvePartName!=""&&approvePartName!=null){
							var nodeNameArr = approveNodeName.split(",");
							var partNameArr = approvePartName.split(",");
							var tipStr = "";
							for(var i=0;i<nodeNameArr.length;i++){
								tipStr = tipStr + nodeNameArr[i] +"岗位的【" + partNameArr[i]+"】,";
							}
							tipStr = tipStr.substring(0, tipStr.length-1);
							approveInfo="正在"+tipStr+"审批";
						}else{
							approveInfo="正在"+approveNodeName+"岗位审批";
						}
						if(approveInfo.length>40){
							//提示内容长度大于40时，截取展示并添加鼠标title提示。
							var approveTitleInfo=approveInfo;
							approveInfo=approveInfo.substring(0, 40)+"...";
							busPointInfo = "<span title="+approveTitleInfo+">申请已提交，"+approveInfo+"</span>";
						}else{
							busPointInfo = "<span>申请已提交，"+approveInfo+"</span>";
						}
						$(".block-next").append(busPointInfo);
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}else if(appSts=="2" && (data.nodeName=="apply_approval" || data.nodeName=="primary_apply_approval")){//如果审批状态是通过，但是当前节点还是在业务审批阶段的话，需要手动触发一下业务流程的提交
						var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("manual_submit");
						if(checkPmsBizFlag){
							$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>业务提交>></a></span>");
							$(".block-next").click(function(){
								alert(top.getMessage("CONFIRM_OPERATION","业务提交"),2,function(){
									doCommitProcess();
								});
							}); 
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}else{
							$(".block-next").append("<span>暂时没有权限操作业务提交</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}
					}else if(appSts == '4'){//审批通过
						busPointInfo = "<span>申请已审批通过，请在签约阶段中查看信息</span>";
						$(".block-next").append(busPointInfo);
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}else if(appSts == '5'){//申请被否决
						busPointInfo = "<span>申请已被否决，业务结束</span>";
						$(".block-next").append(busPointInfo);
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}else{
						$.ajax({
							url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
							type:'post',
							dataType:'json',
							success:function(data){
								var url = data.url;
								var title=data.title;
								nodeName = data.nodeName;
								if(data.wkfFlag!="0"){
									var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
									if(checkPmsBizFlag){
										//分单特殊处理
										if(data.assign){
											$(".block-next").append("<span>暂时没有权限操作该节点</span>");
											$(".next-div").removeClass("hide");
											$(".next-div").addClass("show");
										}else if(data.isShow){
											$(".block-next").append("<span>"+title+"</span>");
											$(".next-div").removeClass("hide");
											$(".next-div").addClass("show");
											
										}else{
											$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+">></a></span>");
											$(".block-next").click(function(){
												toNextBusPoint(url,title,nodeName);
											}); 
											$(".next-div").removeClass("hide");
											$(".next-div").addClass("show");
											if(nodeName!="investigation"){
												//调查资料
												var vouType = '${vouType}';
												if(vouType!="1" && pleFlag=="1"){
													$(".bus-investigate").show();
												}
											}
										}
									}else{
										$(".block-next").append("<span>暂时没有权限操作该节点</span>");
										$(".next-div").removeClass("hide");
										$(".next-div").addClass("show");
									}
								}
							}
						});
					}
				}
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
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/selectForBusAjax">
							<dhcc:bootstarpTag property="formcus00005" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="跳过" action="跳过" onclick="skipSubmit()"></dhcc:thirdButton>
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert1('#cusInsertForm')"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
