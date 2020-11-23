<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>新增</title>
</head>
<script type="text/javascript">
    var cusNo = '${cusNo}';
    var projectName = '${projectName}';
    $(function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });

        //证件类型选择组件
        /*  $("select[name=idType]").popupSelection({
                     searchOn:true,//启用搜索
                     inline:true,//下拉模式
                     multiple:false,//单选
                     changeCallback : function (elem) {
                         $("[name=popsidType]").trigger("change");
                     }
         }); */
        $("select[name=idType]").popupSelection({
            height: 20,
            selectClass: "old",
            changeCallback: function (elem) {
                $("[name=popsidType]").trigger("change");
            }
        });
        //由于初始时不确定是何种类型
        var idType = $("[name=idType]").val();
        var $idNum = $("[name=idType]").parents("table").find("input[name=idNum]")[0];
        if (idType == "0") {//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        } else {
            changeValidateType($idNum, "");
        }
        getBirthdayAndSex();

        //就两三个值 不必要使用选择组件
// 		//性别选择组件
//         $("select[name=sex]").popupSelection({
// 					searchOn:true,//启用搜索
// 					inline:true,//下拉模式
// 					multiple:false//单选
// 		});
        //政治面貌选择组件
        $("select[name=ifParty]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false//单选
        });
        //民族选择组件
        $("select[name=nationality]").popupSelection({
            searchOn: true,//启用搜索
            inline: false,//弹框模式
            multiple: false,//单选
            valueClass: "show-text",//自定义显示值样式
            labelShow: false//已选项显示
        });
        // //个人健康状况选择组件
        // $("select[name=healthStat]").popupSelection({
        // 			searchOn:true,//启用搜索
        // 			inline:true,//下拉模式
        // 			multiple:false//单选
        // });
        //婚姻状况选择组件
        $("select[name=marrige]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false//单选
        });
        //最高学历选择组件
        $("select[name=education]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,//下拉模式
            multiple: false//单选
        });

        $("select[name=cusTransRelationNo]").popupSelection({
            searchOn: false,//启用搜索
            inline: true,//下拉模式
            multiple: false//单选
        });

        $("[name=groupName]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false-单选，true-多选，默认多选
            ajaxUrl: webPath + "/mfCusGroup/getCusGroupList",
            valueElem: "input[name='groupNo']",//真实值选择器
            title: "集团",//标题
            labelShow: false,
            changeCallback: function (elem) {//回调方法
                BASE.removePlaceholder($("input[name=" + element + "]"));
                var sltVal = elem.data("selectData");
            },
            tablehead: {//列表显示列配置
                "groupName": "集团名称",
                "idNum": "集团代码"
            },
            returnData: {//返回值配置
                disName: "groupName",//显示值
                value: "idNum"//真实值
            }
        });

        //信息来源选择组件
//         $("select[name=infoOffer]").popupSelection({
// 					searchOn:true,//启用搜索
// 					inline:true,//下拉模式
// 					multiple:false,//单选
// 					addBtn:{
// 						"title":"新增",
// 						"fun":function(hiddenInput, elem){
// 							$(elem).popupSelection("hideSelect", elem);
// 							BASE.openDialogForSelect('新增信息来源','INFO_OFFER', elem);
// 						}
// 					},changeCallback : function (elem) {
// 						handleAnchorFun($("select[name=popsinfoOffer]"));
// 					}
// 		});
// 		//客户所属区域选择组件（废弃，改为表单绑定onclick事件selectAreaByStepLoadingDialog(selectAreaCallBack)）
// 		if (!$("input[name='careaProvice']").is(":hidden")||!$("input[name='careaCounty']").is(":hidden")) {
// 			var areaData = null;
// 			$.ajax({
// 		        url:webPath+"/nmdArea/getAreaListAllAjax",
// 		        type:"post",
// 		        async:false,
// 		        dataType:"json",
// 		        success:function(data){
// 	        		areaData = JSON.parse(data.items);
// 					setAreaDataToDom(areaData);
// 		        }
// 	     	});
// 		};
    });

    //更换信息来源的时候清空推荐信息
    function emptyRecommender() {
        $("input[name=recommenderName]").val("");
        $("input[name=recommenderPhone]").val("");
        $("input[name=recommenderEmployer]").val("");
    }


    function setAreaDataToDom(areaData) {
        $("input[name=careaProvice]").popupSelection({
            //ajaxUrl:webPath+"/nmdArea/getAreaListAllAjax",
            items: areaData,
            searchOn: true,//启用搜索
            multiple: false,//单选
            valueClass: "show-text",//自定义显示值样式
            ztree: true,
            ztreeSetting: setting,
            title: "客户所属地区",
            handle: BASE.getIconInTd($("input[name=careaProvice]")),
            changeCallback: function (elem) {
                var areaNo = elem.data("values").val();
                var node = elem.data("treeNode");
                var parNode = node.getParentNode();
                var address = node.name;
                while (parNode) {
                    address = parNode.name + address;
                    parNode = parNode.getParentNode();
                }
                BASE.removePlaceholder($("input[name=regHomeAddre]"));
                $("input[name=regHomeAddre]").val(address);
                $("input[name=careaCity]").val(address);
                var $careaProviceObj = $("input[name=careaProvice]").parent(".input-group").find(".pops-label-alt");
                $careaProviceObj.removeClass("pops-label-alt");
                $careaProviceObj.html(address);
            }
        });

        //住宅地址区域-阳光金控添加
        $(".alertDilog input[name='careaCounty']").popupSelection({
            //ajaxUrl:webPath+"/nmdArea/getAreaListAllAjax",
            items: areaData,
            searchOn: true,//启用搜索
            multiple: false,//单选
            valueClass: "show-text",//自定义显示值样式
            ztree: true,
            ztreeSetting: setting,
            title: "住宅地址",
            handle: BASE.getIconInTd($("input[name=careaCounty]")),
            changeCallback: function (elem) {
                var areaNo = elem.data("values").val();
                var node = elem.data("treeNode");
                var parNode = node.getParentNode();
                var address = node.name;
                while (parNode) {
                    address = parNode.name + address;
                    parNode = parNode.getParentNode();
                }
                BASE.removePlaceholder($("input[name=careaCounty]"));
                $("input[name=commAddress]").val(address);
                var $careaCountyObj = $("input[name=careaCounty]").parent(".input-group").find(".pops-label-alt");
                $careaCountyObj.removeClass("pops-label-alt");
                $careaCountyObj.html(address);
            }
        });
    }

    function selectAreaCallBack(areaInfo) {
        $("input[name=careaProvice]").val(areaInfo.disNo);
        $("input[name=careaCounty]").val(areaInfo.disNo);

        $("input[name=careaCity]").val(areaInfo.disName);
        $("input[name=regHomeAddre]").val(areaInfo.disName);
        $("input[name=commAddress]").val(areaInfo.disName);
        if(areaInfo.postalCode != null && areaInfo.postalCode != ""){
            $("input[name=postalCode]").val(areaInfo.postalCode);
        }
        getIfLocal(areaInfo.disNo);
    };

    //分级加载（commAddress显示，carea 隐藏）住宅区域划分
    function selectAreaZhuZhaiCallBack(areaInfo) {
        $("input[name=careaCounty]").val(areaInfo.disNo);
        $("input[name=commAddress]").val(areaInfo.disName);
    };

    //分级加载 areaProvice隐藏，areaCity显示
    function selectAreaProviceCallBack(areaInfo) {
        $("input[name=careaProvice]").val(areaInfo.disNo);
        $("input[name=careaCity]").val(areaInfo.disName);
        $("input[name=regHomeAddre]").val(areaInfo.disName);
        if(areaInfo.postalCode != null && areaInfo.postalCode != ""){
            $("input[name=postalCode]").val(areaInfo.postalCode);
        }
        getIfLocal(areaInfo.disNo);
    };
    function getIfLocal(areaNo){
        $.ajax({
            url : webPath + "/mfCusPersBaseInfo/getIfLocalAjax",
            data : {
                areaNo:areaNo
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    $("[name='ifLoc']").val(data.ifLoc);
                }
            },
            error : function() {
            }
        });
    }
    function insertCusPersBaseInfo(obj) {
        //手机号码唯一性验证(去掉手机号码验证)
// 		var $tel =  $("input[name=cusTel]");
// 		var relationId = $("input[name=cusNo]").val();
// 		var cusNo = $("input[name=cusNo]").val();
// 		var unVal = $tel.val();
// 		if(unVal !=null && unVal!=""){
// 			var columnTitle= $tel.attr("title");
// 			var unValCheckResult = checkUniqueVal(unVal,columnTitle,relationId,"MfCusCustomer","20","insert",cusNo);
// 			var checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
// 			var telResultMsg = unValCheckResult.split("&")[1];
// 			if(checkFlag == "1"){
// 				window.top.alert(telResultMsg,2,function(){
// 					checkEmergencyContactTel(obj);
// 				});
// 			}else{
// 				checkEmergencyContactTel(obj);
// 			}
// 		}else{
        checkEmergencyContactTel(obj);
// 		}


    }


    var relationId = $("input[name=cusNo]").val();

    //验证紧急联系人
    function checkEmergencyContactTel(obj) {
        var tel = $("input[name=cusTel]").val();

        var $emergencyContactTel = $("input[name=emergencyContactTel]")
        var unVal = $emergencyContactTel.val();
        if (unVal != null && unVal != "") {
            if (unVal == tel) {
                window.top.alert(top.getMessage("ERROR_TEL_EMER_REPEAT"), 4);
                return false;
            } else {
                var columnTitle = $emergencyContactTel.attr("title");
                var unValCheckResult = checkUniqueVal(unVal, columnTitle, relationId, "MfCusCustomer", "20", "insert", relationId);
                var checkFlag = unValCheckResult.split("&")[0];//result.split("&")[0];
                if (checkFlag == "1") {
                    var telResultMsg = unValCheckResult.split("&")[1];
                    window.top.alert(telResultMsg, 2, function () {
                        top.baseInfo = true;
                        ajaxInsertCusForm(obj);
                    });
                } else {
                    top.baseInfo = true;
                    ajaxInsertCusForm(obj);
                }
            }

        } else {
            top.baseInfo = true;
            ajaxInsertCusForm(obj);
        }
    }

    //根据身份证获取年龄性别信息
    function getBirthdayAndSex() {
        var idType = $("[name='popsidType']").val();
        if (!idType) {
            idType = $("[name='idType']").val();
        }
        if ("0" == idType) {
            StringUtil.setBirthyAndSexByID($("input[name='idNum']"), 'sex', 'brithday', 'age');
        }
    }

    //证件类型变化是修改证件号码验证规则
    function idTypeChange(obj) {
        //证件号码格式验证
        var idType = $(obj).val();
        var $idNum = $(obj).parents("table").find("input[name=idNum]")[0];
        if (idType == "0") {//身份证样式格式
            //如果是身份证，添加校验
            changeValidateType($idNum, "idnum");
        } else {
            changeValidateType($idNum, "");
        }
        $(obj).parents("table").find("input[name=idNum]").focus();
    }

    //邮箱格式校验
    function emailCheck(obj) {
        var email = $(obj).val();
        var myreg = /^([a-zA-Z0-9_-]{1,16})@([a-zA-Z0-9]{1,9})(\.[a-zA-Z0-9]{1,9}){0,3}(\.(?:com|net|org|edu|gov|mil|cn|us)){1,4}$/;
        if (!myreg.test(email)) {
            alert('提示\n\n请输入有效的邮箱！', 0);
            return false;
        }
    }

    function getAllByUplev() {
        top.dialog({
            id: "areaDialog",
            title: '客户渠道类型',
            url: webPath + '/MfCusChannelType/getAllByUplev',
            width: 350,
            height: 420,
            backdropOpacity: 0,
            onshow: function () {
                this.returnValue = null;
            },
            onclose: function () {
                if (this.returnValue) {
                    $("[name='cusChannelType']").val(this.returnValue.disName);
                    $("[name='cusChannelNo']").val(this.returnValue.disNo);
                }
            }
        }).showModal();
    }

    //二级贷款分类
    function getCategories() {
        var type = $('select[name=categoriesClass]').val();
        if (type == null || type == "" || type == undefined) {
            return alert("贷款类别不许为空");
        }
        top.dialog({
            id: "areaDialog",
            title: '二级贷款类别',
            url: webPath + '/mfCusPersBaseInfo/getCategories?type=' + type,
            width: 350,
            height: 420,
            backdropOpacity: 0,
            onshow: function () {
                this.returnValue = null;
            },
            onclose: function () {
                if (this.returnValue) {
                    this.value = this.returnValue.disNo;
                    $("input[name='categories']").val(this.returnValue.disName);
                }
            }
        }).showModal();
    }

    //验证所有请选择
    function asdInsert() {
        insertCusPersBaseInfo('#cusPersBaseInsert');
    }
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag fourColumn">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="cusPersBaseInsert" theme="simple" name="operform"
					  action="${webPath}/mfCusPersBaseInfo/updateAjax">
					<dhcc:bootstarpTag property="formcusPersonBase" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存" onclick="asdInsert();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
