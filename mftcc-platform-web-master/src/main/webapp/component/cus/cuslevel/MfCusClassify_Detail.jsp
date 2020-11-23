<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=${cssJsVersion}" />
	</head>
	<style type="text/css">
	.business-span{
		font-size: 22px;
    	margin-right:5px;
	}
	.advice{
	    top: 0px;
	}
	.adviceinfo{
		color: #f34d00;
		font-size: 18px;
		margin-left:5px;
	}

	</style>
	<body class="overflowHidden bg-white" style="padding-bottom: 60px;">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="cusclassify-form"  theme="simple" name="operform" action="${webPath}/mfCusClassify/insertOrUpdateAjax">
						<dhcc:bootstarpTag property="formcusclassify0001" mode="query" />
					</form>
				</div>
				<div class="margin_0 list-table ">
					<div class="title">
						<span>
						<i class="i i-xing blockDian"></i>
						业务信息</span>
						<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#applyHis">
							<i class='i i-close-up'></i>
							<i class='i i-open-down'></i>
						</button>
					</div>
					<div class="row clearfix padding_top_15 padding_left_40 collapse in" id="applyHis">
						<div class="col-md-12 column">
							<div class="col-md-3 column">
								<p>业务笔数</p>
								<p class="margin_top_15"><span class="business-span color_theme">${dataMap.moreCount}</span><span>笔</span> </p>
							</div>
							<div class="col-md-3 column">
								<p>业务金额</p>
								<p class="margin_top_15"><span class="business-span color_theme">${dataMap.totleAmt}</span><span>万元</span></p>
							</div>
							<div class="col-md-3 column">
								<p>逾期笔数</p>
								<p class="margin_top_15"><span class="business-span color_theme">0</span><span>笔</span></p>
							</div>
							<div class="col-md-3 column">
								<p>逾期金额</p>
								<p class="margin_top_15"><span class="business-span color_theme">0.00</span><span>万元</span></p>
							</div>
				  		</div>		
					</div>
				</div>
				<div class="list-table margin_0">
					<div class="title">
						<span>
						<i class="i i-xing blockDian"></i>
						征信情况</span>
						<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#examineHis">
							<i class='i i-close-up'></i>
							<i class='i i-open-down'></i>
						</button>
					</div>
					<div class="padding_top_15 padding_left_40 collapse in"style="text-align: center;"id="examineHis">
						<p>暂无信息</p>
					</div>
					</div>
				</div>
			</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#cusclassify-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mycloseClassify();"></dhcc:thirdButton>
		</div>
	</body>
	<script type="text/javascript">
	var	cusNo = '${cusNo}';
	$(function() {
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//根据客户的信用等级给出建议的客户分类（先按照下面的规则分类，后续会建立专门的客户分类模型）
		//1、根据客户评级结果给出建议分类，A级（含）以上的建议归为优质客户，C级（含）以下的建议归为黑名单，其他等级建议归为普通客户；
		//2、未评级的客户建议归为普通客户
		var cusLevelId = $("input[name=cusLevelId]").val();
		if(cusLevelId=="1" ||cusLevelId=="2"||cusLevelId=="3"){//优质客户
			$(".adviceinfo").text("优质客户");
		}else if(cusLevelId=="7" ||cusLevelId=="8"||cusLevelId=="9"){//黑名单
			$(".adviceinfo").text("黑名单");
		}else{//普通客户
			$(".adviceinfo").text("普通客户");
		}
	});
	function clearMergerReason(){
		$("textarea[name=mergerReason]").val("");
	}
    //add by lance zhao 20180829 企业客户分类
    function clearMergerReasonForRongzu(){
        $("textarea[name=mergerReason]").val("");
        /*var classifyType = $("select[name=classifyType]").val();
        $.ajax({
            url :webPath+"/mfCusClassify/cusAutoClassify",
            type:"post",
            data:{"cusNo":cusNo},
            async: false,
            dataType:"json",
            success:function(data){
                if(data.flag=="success"){
                    //4-商机客户，5-目标客户
                    if(data.msg == "1" && 5!=classifyType){
                        window.top.alert("系统根据规则将客户归类为'目标客户',请谨慎操作!",0);
                    }else if(data.msg == "2" && 4!=classifyType){
                        window.top.alert("系统根据规则将客户归类为'商机客户',请谨慎操作!",0);
                    }
                }else{
                    window.top.alert(data.msg, 0);
                    LoadingAnimate.stop();
                }
            }
        });*/
	}
	function mycloseClassify(){
		if(top.classify){
			window.location.href = "${webPath}/mfCusClassify/getListPage?cusNo=" + cusNo;
		}else{
			myclose_click();
		}
	}
	function ajaxInsertThis(obj){//obj是form对象
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				success:function(data){
                    LoadingAnimate.stop();
					if(data.flag == "success"){
						top.updateFlag = true;
						top.cusTag = data.mfCusClassify.rankTypeName;
						top.cusClassify= data.mfCusClassify.classifyType;
						mycloseClassify();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}else if(data.flag == 'approve'){
                        window.top.alert(data.msg,3);
                        window.location.href = "${webPath}/mfCusClassify/getListPage?cusNo=" + cusNo;
					}
				},
				error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	</script>
</html>
