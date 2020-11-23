<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusFamilyInfo.js'> </script>
		<script type="text/javascript" src='${webPath}/component/cus/identitycheck/js/IdentityCheck.js?v=${cssJsVersion}'></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
					
				<form  method="post" id="MfCusFamilyInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusFamilyInfo/updateAjax">
					<dhcc:bootstarpTag property="formcusRelationBase" mode="query" />
				</form>
			</div>
		</div>
	</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="saveCusFamilyInfoInsert('#MfCusFamilyInfoUpdate','update');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
		<script type="text/javascript">
            $(function() {
                var ajaxData = '${ajaxData}';
                ajaxData = JSON.parse(ajaxData);
                //与客户关系选择组件
                /*$("select[name=relative]").popupSelection({
                    searchOn: true,//启用搜索
                    inline: true,//下拉模式
                    multiple: false,//单选
                    addBtn: {
                        "title": "新增",
                        "fun": function (hiddenInput, elem) {
                            $(elem).popupSelection("hideSelect", elem);
                            BASE.openDialogForSelect('新增与客户关系', 'CUS_PERS_REL', elem);
                        }
                    }
                });*/
                console.log("初始加载测试开始没有")
                $("select[name=relative]").popupSelection({
                    searchOn: true,//启用搜索
                    inline: true,
                    multiple: false,
                    selectClass: "old",
                    items: ajaxData.cusPersRelType,
                    addBtn: {
                        "title": '新增',
                        "fun": function (elem) {
                            $(".pops-select").hide();
                            BASE.openDialogForSelect('新增与客户关系', 'CUS_PERS_REL', elem);
                        }
                    }
                    , changeCallback: function (elem, obj) {
                        // 如果是配偶关系自动带出性别。
                        var relative = $("input[name='relative']").val();
                        var relativeClass = $("input[name=relative]").parents("td").attr("class");
                        var marrige = ajaxData.marrige;

                        if (relative == "1") {
                            if(marrige == "1"){
                                alert("未婚状态不能登记配偶信息",0);
                                $("input[name=relative]").parents("td").find(".pops-value").html(" ")
                            }
                            $(":input[name=sex]").val(ajaxData.baseSex);
                            $("select[name=popssex]").popupSelection("selectedById", ajaxData.baseSex);
                        }

                    }
                });

                //证件选择组件
                $("select[name=idType]").popupSelection({
                    searchOn: true,//启用搜索
                    inline: true,//下拉模式
                    multiple: false//单选
                });
            });
            // //个人健康选择组件
            // $("select[name=healthStat]").popupSelection({
            // 			searchOn:true,//启用搜索
            // 			inline:true,//下拉模式
            // 			multiple:false//单选
            // });
		</script>
	</body>
</html>