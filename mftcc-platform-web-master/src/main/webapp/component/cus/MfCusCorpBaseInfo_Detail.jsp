<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>--%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>详情</title>
</head>
<script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var addd="";
    var cusNo = '${cusNo}';
    <%--var ajaxData = '${ajaxData}';--%>
    var projectName = '${projectName}';
    // ajaxData =JSON.parse(ajaxData);
    function matchProjectSizeAjax(){
        var wayNo=$("input[name='wayClass']").val();
        var assetSum=$("input[name='assetSum']").val();
        var bussIncome=$("input[name='bussIncome']").val();
        var empCnt=$("input[name='empCnt']").val();
        $.ajax({
            url:webPath+"/mfCusCorpBaseInfo/getMatchProjectSizeAjax",
            data : {"wayNo":wayNo,"assetSum":assetSum,"bussIncome":bussIncome,"empCnt":empCnt},
            type : 'post',
            dataType : 'json',
            async:false,
            success : function(data) {
                if(data.proSizeNo!="") {
                    $("input[name='projSize']").val(data.proSizeNo);
                    $("input[name='projSize']").parent().find("input[class='form-control']").val(data.proSizeName);
                }
            },
            error : function() {
            }
        });

    }
    $(function() {
        /*var POLICY_CHANNEL = ${policyChannel};//政策通道
        //政策通道
        $("input[name=policyChannel]").popupSelection({
            searchOn:true,//启用搜索
            inline:false,//弹出模式
            multiple:true,//多选
            labelShow: false,//不显示已选选项
            title:"政策通道",
            items:POLICY_CHANNEL
        });*/

        $("select[name=corpNature]").popupSelection({
            searchOn : true,//启用搜索
            inline : true,//下拉模式
            multiple : false//单选
        });
        /* 	$("select[name=projSize]").popupSelection({
                searchOn : true,//启用搜索
                inline : true,//下拉模式
                multiple : false//单选

            }); */
        $("select[name=registeredType]").popupSelection({
            searchOn : true,//启用搜索
            inline : true,//下拉模式
            multiple : false//单选
        });
        $("select[name=legalIdType]").popupSelection({
            searchOn : true,//启用搜索
            inline : true,//下拉模式
            multiple : false,//单选
            changeCallback : function (elem) {
                $("[name=popslegalIdType]").trigger("change");
            }
        });
        $("select[name=cusRegAuthority]").popupSelection({
            searchOn : true,//启用搜索
            inline : true,//下拉模式
            multiple : false,//单选
            addBtn:{
                "title":"新增",
                "fun":function(hiddenInput, elem){
                    $(elem).popupSelection("hideSelect", elem);
                    BASE.openDialogForSelect('新增机关信息','REGIST_DEPARMENT', elem);
                }
            }
        });
// 			var  loanUseItems=null;
// 			$.ajax({
// 					url:webPath+"/mfCusCorpBaseInfo/getLoanUseAjax",
// 					data : "",
// 					type : 'post',
// 					dataType : 'json',
// 					async:false,
// 					success : function(data) {
// 						loanUseItems=data.loanUse;
// 					},
// 					error : function() {
// 					}
// 				});
// 			$("select[name=wayClass]").popupSelection({
// 				searchOn : true,//启用搜索
// 				multiple : false,//单选
// 				valueClass : "show-text",//自定义显示值样式
// 				items:loanUseItems,
// 				ztree : true,
// 				ztreeSetting : setting,
// 				title : "行业分类",
// 				handle : BASE.getIconInTd($("input[name=wayClass]")),
// 				changeCallback : function (elem) {
// 				var areaNo=elem.data("values").val();
// 					var node = elem.data("treeNode");
// 					var parNode =  node.getParentNode();
// 					var address=node.name;
// 					var industryClass= node.industryClass;
// 					$("[name=industryClass]").val(industryClass);
// 					while(parNode) {
// 						address=parNode.name+address;
// 						parNode=parNode.getParentNode();
// 					}

// 					matchProjectSizeAjax();
// 					BASE.removePlaceholder($("input[name=wayClass]"));
// 		}
// 			});
// 			$("select[name=infoOffer]").popupSelection({
// 				searchOn:true,//启用搜索
// 				inline:true,//下拉模式
// 				multiple:false,//单选
// 				addBtn:{//添加扩展按钮
// 					"title":"新增",
// 					"fun":function(hiddenInput, elem){
// 						//隐藏选择区域
// 						$(elem).popupSelection("hideSelect", elem);
// 						BASE.openDialogForSelect('新增信息来源','INFO_OFFER', elem);
// 					}
// 				},changeCallback : function (elem) {
// 					handleAnchorFun($("select[name=popsinfoOffer]"));
// 				}
// 			});


        $("input[name='wayClassDes']").bind("change",function(){
            matchProjectSizeAjax();
        });
        $("input[name='assetSum']").bind("blur",function(){
            matchProjectSizeAjax();
        });
        $("input[name='bussIncome']").bind("blur",function(){
            matchProjectSizeAjax();
        });
        $("input[name='empCnt']").bind("blur",function(){
            matchProjectSizeAjax();
        });

        $("input[name=groupName]").parents("tr").hide();
        var groupNameLabel = $("input[name=groupName]").parents("tr").children().eq(0).find(".control-label");
        var groupNameLabelText = $(groupNameLabel).text();
        $(groupNameLabel).empty().append("<font color='#FF0000'>*</font>" + groupNameLabelText);

        /* $("input[name=groupName]").attr("mustinput","1"); */

        $("input[name=stockCode]").parents("tr").hide();


        /* $("input[name=groupName]").popupSelection({
            searchOn:true, //启用搜索
            inline:true, //下拉模式
            multiple:false, //多选
            items:ajaxData.mfCusGroupArray,
            changeCallback : function (obj, elem) {
                $("input[name=groupName]").val(obj.data("text"));
                $("input[name=groupNo]").val(obj.val());
            }
        }); */

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
    /* 	function ifGroupCustomer(obj) {
            var ifGroupType = $(obj).val();
            if (ifGroupType == 0) {//非集团客户
                $("input[name=groupName]").attr("mustinput", "0");
                $("input[name=groupName]").parents("tr").hide();
            } else {//集团客户
                //$("input[name=groupName]").attr("mustinput","1");
                $("input[name=groupName]").parents("tr").show();
            }
        }; */
    function ifListedCompany(obj) {
        var ifListed = $(obj).val();
        if(ifListed == 0){//非上市
            $("input[name=stockCode]").attr("mustinput", "0");
            $("input[name=stockCode]").parents("tr").hide();
        }else{//上市
            $("input[name=stockCode]").parents("tr").show();
        }
    };


    function insertCusCorpBase(obj) {
        debugger;
        //校验贷款卡编码是否存在
        var loanCredNo = $("input[name=loanCredNo]").val();
        if (typeof loanCredNo !== "undefined" && loanCredNo) {
            var params = {
                "loanCredNo" : loanCredNo
            };
        }

        var unVal = $("input[name=legalIdNum]").val();
        var column = $("input[name=legalIdNum]").attr("title");
        var legalIdNumType = $("select[name=legalIdType]").val();
        var relationId = $("input[name=cusNo]").val();
        var result = checkUniqueVal(unVal, column, relationId, "MfCusCorpBaseInfo", "01", "insert");
        var checkFlag = result.split("&")[0];
        result = result.split("&")[1];
        top.baseInfo = true;
        if (checkFlag == "1") {
            window.top.alert(result, 2, function() {
                ajaxInsertCusForm(obj);
            });
        } else {
            ajaxInsertCusForm(obj);
        }
    }

    function getCusMngNameDialog(userInfo) {
        $("input[name=cusMngName]").val(userInfo.opName);
        $("input[name=cusMngNo]").val(userInfo.opNo);
    };
    /* //从集团客户放大镜赋值给表单属性
    function getGroInfoArtDialog(groupInfo) {
        $("input[name=groupName]").val(groupInfo.groupName);
        $("input[name=groupNo]").val(groupInfo.groupNo);
        $("input[name='groupName']").attr("readonly", true);
    }; */
    function selectAreaCallBack(areaInfo) {
        $("input[name=careaProvice]").val(areaInfo.disName);
    };
    //选择常住地址回调
    function selectCommAddressCallBack(areaInfo){
        $("input[name=commAddress]").val(areaInfo.disName);
    };
    //分级加载areaProvice隐藏，areaCity显示
    function selectAreaProviceCallBack(areaInfo){
        $("input[name=careaProvice]").val(areaInfo.disNo);
        $("input[name=careaCity]").val(areaInfo.disName);
        $("input[name=address]").val(areaInfo.disName);
    };

    function selectAreaProvicePosCallBack(areaInfo){
        $("input[name=careaProvice]").val(areaInfo.disNo);
        $("input[name=careaCity]").val(areaInfo.disName);
        $("input[name=address]").val(areaInfo.disName);
        $("input[name=postalCode]").val(areaInfo.postalCode);
    };
    //注册资本不能输入负数
    function controlMax() {
        var registeredCapitalVal = $("input[name='registeredCapital']").val();
        if (registeredCapitalVal >= "0") {

        } else {
            $("input[name='registeredCapital']").val("");
        }

    }
    //证件类型变化是修改证件号码验证规则
    function idTypeChange(obj){
        //证件号码格式验证
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=legalIdNum]")[0];
        if(idType=="0"){//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        }else if(idType == "3"){//军官证样式格式
            //如果是军官证，添加校验
            changeValidateType($idNum, "officersNo");
        }else{
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=legalIdNum]").val("");
        $(obj).parents("table").find("input[name=legalIdNum]").focus();
    };
    //实际控制人证件类型变化时修改证件号码验证规则
    function actualPowerIdTypeChange(obj){
        //证件号码格式验证
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=actualPowerIdNum]")[0];
        if(idType=="0"){//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        }else if(idType == "3"){//军官证样式格式
            //如果是军官证，添加校验
            changeValidateType($idNum, "officersNo");
        }else{
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=actualPowerIdNum]").val("");
        $(obj).parents("table").find("input[name=actualPowerIdNum]").focus();
    };
    //经办人证件类型变化时修改证件号码验证规则
    function agentIdTypeChange(obj){
        //证件号码格式验证
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=agentIdNum]")[0];
        if(idType=="0"){//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        }else if(idType == "3"){//军官证样式格式
            //如果是军官证，添加校验
            changeValidateType($idNum, "officersNo");
        }else{
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=agentIdNum]").val("");
        $(obj).parents("table").find("input[name=agentIdNum]").focus();
    };
    //行业分类选择后的回调处理
    function nmdWaycCallBack(nmdWayInfo){
        var oldWayClass = $("input[name=wayClass]").val();
        $("input[name=wayClassDes]").val(nmdWayInfo.wayName);
        $("input[name=wayClass]").val(nmdWayInfo.wayNo);
        $("[name=industryClass]").val(nmdWayInfo.industryClass);
        $("input[name=wayMaxClass]").val(nmdWayInfo.wayMaxClass);
        if(nmdWayInfo.wayNo!=oldWayClass){//行业分类改变时，修改企业规模
            matchProjectSizeAjax();
        }
    };
    //二级贷款分类
    function getCategories(){
        var type = $('select[name=categoriesClass]').val();
        if(type==null || type=="" || type==undefined){
            return alert("贷款类别不许为空");
        }
        top.dialog({
            id : "areaDialog",
            title : '二级贷款类别',
            url : webPath + '/mfCusCorpBaseInfo/getCategories?type=' + type,
            width : 350,
            height : 420,
            backdropOpacity : 0,
            onshow : function() {
                this.returnValue = null;
            },
            onclose : function() {
                if (this.returnValue) {
                    this.value = this.returnValue.disNo;
                    $("input[name='categories']").val(this.returnValue.disNo);
                    $("input[name='categoriesShow']").val(this.returnValue.disName);
                }
            }
        }).showModal();
    }



    function  selectEnterpriseCus(){
        var ajaxUrl = webPath+"/mfCusGroup/getCusGroupAjax";// 请求数据URL;
        $("input[name=groupName]").parent().find('div').remove();

        $("input[name='groupNameHid']").popupList({
            searchOn : true, // 启用搜索
            multiple : false, // false单选，true多选，默认多选
            ajaxUrl : ajaxUrl,
            valueElem : "input[name='groupNo']",// 真实值选择器
            title : "新增集团客户",// 标题
            labelShow : false,
            changeCallback : function(elem) {// 回调方法
                BASE.removePlaceholder($("input[name='groupName']"));
                var sltVal = elem.data("selectData");
                $("input[name='groupNo']").val(sltVal.idNum);
                $("input[name='groupName']").val(sltVal.groupName);
                $('.hidden-content').find('div[class=pops-value]').remove();
            },
            tablehead : {// 列表显示列配置
                "groupName" : "集团名称",
                "idNum" : "集团代码"
            },
            returnData : {// 返回值配置
                disName : "groupName",// 显示值
                value : "idNum"// 真实值
            }
        });

        $('input[name="groupNameHid"]').next().click();
    }


</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="cusCorpBaseInsert" theme="simple"   name="operform"  action="${webPath}/mfCusCorpBaseInfo/updateAjax">
					<dhcc:bootstarpTag property="formcuscorp00004" mode="query" />
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="insertCusCorpBase('#cusCorpBaseInsert');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
<%--<body class="body_bg">
    <div class="bigform_content">
        <div class="bigForm_content_form">
            <div class="form_title">企业客户基本信息</div>
            &lt;%&ndash;<c:if test="${mfCusCorpBaseInfo==null}">
            未登记客户基本信息
            </c:if>
            <c:if test="${mfCusCorpBaseInfo!=null}">&ndash;%&gt;
            <form  method="post" theme="simple" name="operform" action="${webPath}/mfCusCorpBaseInfo/updateAjaxByOne">
                <dhcc:propertySeeTag property="formcuscorp00004" mode="query"/>
            </form>
            &lt;%&ndash;</c:if>&ndash;%&gt;
        </div>
    </div>
</body>--%>
<script>
    $(function () {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    })
</script>

</html>