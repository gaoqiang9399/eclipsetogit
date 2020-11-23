;
var RealEstateAssessment = function(window, $){
    var _selectAreaByStepLoadingDialogProvinces = function (callback, ele) {
        dialog({
            id: "areaDialog",
            title: '房产所在城市',
            url: webPath + '/nmdArea/getLv1PageProvinces',
            width: 350,
            height: 420,
            backdropOpacity: 0,
            onshow: function () {
                this.returnValue = null;
            }, onclose: function () {
                if (this.returnValue) {
                    //返回对象的属性disNo,disName
                    if (typeof(callback) == "function") {
                        callback(this.returnValue, ele);
                    } else {
                    }
                }
            }
        }).showModal();
    }


    //查询小区
    var _selectResidentialarea=function() {
        $("input[name=communityName]").parent().find(".pops-value").html("");//重新选择重置
        $("input[name=communityId]").val("");
        $("input[name=buildingName]").parent().find(".pops-value").html("");
        $("input[name=buildingName]").val("");
        $("input[name=buildingId]").val("");
        $("input[name=unitName]").parent().find(".pops-value").html("");
        $("input[name=unitName]").val("");
        $("input[name=unitId]").val("");
        $("input[name=houseName]").parent().find(".pops-value").html("");
        $("input[name=houseName]").val("");
        $("input[name=houseId]").val("");
        var cityCode=$("input[name=cityCode]").val();
        var communityName=$("input[name=pleAddress]").val();
        if(""==cityCode || null==cityCode){
            window.top.alert("请先选择城市",0);
            return false;
        }
        if(""==communityName || null==communityName){
            window.top.alert("请输入地址或小区名",0);
            return false;
        }
        $.ajax({
            url : webPath+"/pledgeBaseInfo/selectResidentialarea?cityCode="+cityCode+"&communityName="+communityName,//请求数据URL
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.ifValue=="1"){
                    $("input[name=communityName]").parent().find('div').remove();
                    $('input[name=communityName]').popupList({
                        searchOn: false, //启用搜索
                        multiple: false, //false单选，true多选，默认多选
                        ajaxUrl:webPath+"/pledgeBaseInfo/analysisData?gsonStr="+encodeURIComponent(data.gsonStr)+"&dataType="+"c",//请求数据URL
                        valueElem:"input[name=communityId]",//真实值选择器
                        title: "选择小区",//标题
                        changeCallback:function(elem){
                        },
                        tablehead:{//列表显示列配置
                            "communityName":{disName:"小区名称",align:"center"},
                            "extOstr15":{disName:"小区所在地",align:"center"}
                        },
                        returnData:{//返回值配置
                            disName:"communityName",//显示值
                            value:"communityId"//真实值
                        }
                    });
                    $("input[name=communityName]").parent().find(".pops-value").click();
                }else if(data.ifValue=="2"){
                    alert("未查询到小区信息或该城市未授权！", 1);
                }else {
                    alert(data.msg, 0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","查询小区"),0);
            }
        })
    }

    //查询楼栋
    var _selectBuilding=function() {
        $("input[name=buildingName]").parent().find(".pops-value").html("");
        $("input[name=buildingId]").val("");
        $("input[name=unitName]").parent().find(".pops-value").html("");
        $("input[name=unitName]").val("");
        $("input[name=unitId]").val("");
        $("input[name=houseName]").parent().find(".pops-value").html("");
        $("input[name=houseName]").val("");
        $("input[name=houseId]").val("");
        var cityCode=$("input[name=cityCode]").val();
        var communityID=$("input[name=communityId]").val();
        if(""==cityCode || null==cityCode){
            window.top.alert("请先选择城市",0);
            return false;
        }
        if(""==communityID || null==communityID){
            window.top.alert("没有该楼栋信息,请手动输入",0);
            return false;
        }
        $.ajax({
            url : webPath+"/pledgeBaseInfo/selectHouseInfo?cityCode="+cityCode+"&communityID="+communityID,//请求数据URL
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.ifValue=="1"){
                    $("input[name=buildingName]").parent().find('div').remove();
                    $('input[name=buildingName]').popupList({
                        searchOn: false, //启用搜索
                        multiple: false, //false单选，true多选，默认多选
                        ajaxUrl:webPath+"/pledgeBaseInfo/analysisData?gsonStr="+encodeURIComponent(data.gsonStr)+"&dataType="+"b",//请求数据URL
                        valueElem:"input[name=buildingId]",//真实值选择器
                        title: "选择楼栋",//标题
                        changeCallback:function(elem){
                            $("input[name=buildingName]").parent().find(".pops-value").hide();
                            $("input[name=buildingName]").css("display","block");
                        },
                        tablehead:{//列表显示列配置
                            "buildingName":{disName:"所在楼栋",align:"center"},
                        },
                        returnData:{//返回值配置
                            disName:"buildingName",//显示值
                            value:"buildingId"//真实值
                        }
                    });
                    $("input[name=buildingName]").parent().find(".pops-value").click();
                }else if(data.ifValue=="2"){
                    alert("未查询到楼栋信息，请手动输入信息", 1);
                }else {
                    alert(data.msg, 0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","查询楼栋"),0);
            }
        })
    }

    //查询单元
    var _selectUnit=function() {
        $("input[name=unitName]").parent().find(".pops-value").html("");
        $("input[name=unitId]").val("");
        $("input[name=houseName]").parent().find(".pops-value").html("");
        $("input[name=houseName]").val("");
        $("input[name=houseId]").val("");
        var cityCode=$("input[name=cityCode]").val();
        var communityID=$("input[name=communityId]").val();
        var buildingID=$("input[name=buildingId]").val();
        if(""==cityCode || null==cityCode){
            window.top.alert("请先选择城市",0);
            return false;
        }
        if(""==buildingID || null==buildingID){
            window.top.alert("没有该单元信息,请手动输入",0);
            return false;
        }
        if(""==communityID || null==communityID){
            window.top.alert("没有该楼栋信息,请手动输入",0);
            return false;
        }
        $.ajax({
            url : webPath+"/pledgeBaseInfo/selectHouseInfo?cityCode="+cityCode+"&communityID="+communityID+"&buildingID="+buildingID,//请求数据URL
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.ifValue=="1"){
                    $("input[name=unitName]").parent().find('div').remove();
                    $('input[name=unitName]').popupList({
                        searchOn: false, //启用搜索
                        multiple: false, //false单选，true多选，默认多选
                        ajaxUrl:webPath+"/pledgeBaseInfo/analysisData?gsonStr="+encodeURIComponent(data.gsonStr)+"&dataType="+"u",//请求数据URL
                        valueElem:"input[name=unitId]",//真实值选择器
                        title: "选择单元",//标题
                        changeCallback:function(elem){
                            $("input[name=unitName]").parent().find(".pops-value").hide();
                            $("input[name=unitName]").css("display","block");
                        },
                        tablehead:{//列表显示列配置
                            "unitName":{disName:"所在单元",align:"center"},
                        },
                        returnData:{//返回值配置
                            disName:"unitName",//显示值
                            value:"unitId"//真实值
                        }
                    });
                    $("input[name=unitName]").parent().find(".pops-value").click();
                }else if(data.ifValue=="2"){
                    alert("未查询到单元信息，请手动输入信息", 1);
                }else {
                    alert(data.msg, 0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","查询单元"),0);
            }
        })
    }

    //查询户
    var _selectHouse=function() {
        $("input[name=houseName]").parent().find(".pops-value").html("");
        $("input[name=houseId]").val("");
        var cityCode=$("input[name=cityCode]").val();
        var communityID=$("input[name=communityId]").val();
        var buildingID=$("input[name=buildingId]").val();
        var unitID=$("input[name=unitId]").val();
        if(""==cityCode || null==cityCode){
            window.top.alert("请先选择城市",0);
            return false;
        }
        if(""==unitID || null==unitID){
            window.top.alert("没有该户号信息,请手动输入",0);
            return false;
        }
        if(""==buildingID || null==buildingID){
            window.top.alert("没有该单元信息,请手动输入",0);
            return false;
        }
        if(""==communityID || null==communityID){
            window.top.alert("没有该小区信息,请手动输入",0);
            return false;
        }
        $.ajax({
            url : webPath+"/pledgeBaseInfo/selectHouseInfo?cityCode="+cityCode+"&communityID="+communityID+"&buildingID="+buildingID+"&unitID"+unitID,//请求数据URL
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.ifValue=="1"){
                    $("input[name=houseName]").parent().find('div').remove();
                    $('input[name=houseName]').popupList({
                        searchOn: false, //启用搜索
                        multiple: false, //false单选，true多选，默认多选
                        ajaxUrl:webPath+"/pledgeBaseInfo/analysisData?gsonStr="+encodeURIComponent(data.gsonStr)+"&dataType="+"h",//请求数据URL
                        valueElem:"input[name=houseId]",//真实值选择器
                        title: "选择户号",//标题
                        changeCallback:function(elem){
                            $("input[name=houseName]").parent().find(".pops-value").hide();
                            $("input[name=houseName]").css("display","block");
                        },
                        tablehead:{//列表显示列配置
                            "houseName":{disName:"所在户号",align:"center"},
                        },
                        returnData:{//返回值配置
                            disName:"houseName",//显示值
                            value:"houseId"//真实值
                        }
                    });
                    $("input[name=houseName]").parent().find(".pops-value").click();
                }else if(data.ifValue=="2"){
                    alert("未查询到住户信息，请手动输入信息", 1);
                }else {
                    alert(data.msg, 0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","查询住户"),0);
            }
        })
    }

    //在线估值
    var _onlineAssessment=function () {
        var houseType=$("select[name=houseType]").val();
        if(houseType =="10008"){
            initOnlineAssessment();
        }else if(houseType=="10009"){
            initOnlineAssessment();
        }else {
            alert("在线评估只支持个人用房-住宅和别墅！", 0);
            return false;
        }
    }
    //发起在线评估 222
    var initOnlineAssessment =function () {
        var communityId=$("input[name=communityId]").val();
        var address=$("input[name=address]").val();
        if(""==communityId && ""==address){
            window.top.alert("未找到对应小区，请填写产证地址",0);
            return false;
        }
        var flag = submitJsMethod($("#pledgeBaseInfoInsert").get(0), '');
        if(flag){
            var dataParam =JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
            $.ajax({
                url : webPath+"/pledgeBaseInfo/onlineAssessment",
                data:{ajaxData:dataParam},
                type : "POST",
                dataType : "json",
                success : function(data) {
                    evalState=data.evalState;
                    if(evalState=="1"){
                        $("#requesTitle").attr("style","display:block;");
                        $("#onlineDlevalinfo0002").empty().html(data.formHtml);
                        alert("在线估值成功，请下拉查看估值结果",1);
                    }else if(evalState=="2"){
                        evalTaskNum=data.evalTaskNum;
                        alert(data.msg, 1);
                    }else {
                        alert(data.msg, 1);
                    }
                },error:function(){
                    alert(top.getMessage("FAILED_OPERATION","在线估值"),0);
                }
            })
        }
    }

    //发起在线评估
    var initiateOnlineAssessment=function () {
        var communityId=$("input[name=communityId]").val();
        var address=$("input[name=address]").val();
        if(""==communityId && ""==address){
            window.top.alert("未找到对应小区，请填写押品地点",0);
            return false;
        }
        var flag = submitJsMethod($("#pledgeBaseInfoInsert").get(0), '');
        if(flag){
            var dataParam =JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
            $.ajax({
                url : webPath+"/pledgeBaseInfo/assessment",
                data:{ajaxData:dataParam},
                type : "POST",
                dataType : "json",
                success : function(data) {
                    evalState=data.evalState;
                    if(evalState=="1"){
                        $("#requesTitle").attr("style","display:block;");
                        $("#onlineDlevalinfo0002").empty().html(data.formHtml);
                        alert("在线估值成功，请下拉查看估值结果",1);
                    }else if(evalState=="2"){
                        evalTaskNum=data.evalTaskNum;
                        alert(data.msg, 1);
                    }else {
                        alert(data.msg, 1);
                    }
                },error:function(){
                    alert(top.getMessage("FAILED_OPERATION","在线估值"),0);
                }
            })
        }
    }

    //人工估值结果查询
    var _requesSelect=function (obj,url) {
        $.ajax({
            url : webPath+obj,
            type : "POST",
            dataType : "json",
            success : function(data) {
                if("success"== data.flag){
                    alert(data.msg, 1);
                    window.location.reload();
                }else {
                    alert(data.msg, 0);
                }
            },error:function(){
                alert(top.getMessage("FAILED_OPERATION","房产评估查询"),0);
            }
        })
    }

    //抵质押率校验
    var _validateRate=function () {
        var num = $("input[name=mortRate]").val();
        if (num.length != 0) {
            var reg = /^\-[0-9]*.?[0-9]*$/;
            if (reg.test(num)) {
                alert("抵质押率不能输入负值", 0);
                $("input[name=mortRate]").val("");
            }
        }
    }
    //评估有效期校验
    var _validateMonth=function () {
        var num  = $("input[name=validTerm]").val();
        if(num.length!=0){
            var reg = /^\-[0-9]*$/;
            if(reg.test(num)){
                alert("评估有效期不能输入负值", 0);
                $("input[name=validTerm]").val("");
            }
        }
    }

    return {
        selectAreaByStepLoadingDialogProvinces:_selectAreaByStepLoadingDialogProvinces,
        selectResidentialarea:_selectResidentialarea,
        selectBuilding:_selectBuilding,
        selectUnit:_selectUnit,
        selectHouse:_selectHouse,
        onlineAssessment:_onlineAssessment,
        requesSelect:_requesSelect,
        validateRate:_validateRate,
        validateMonth:_validateMonth
    };
}(window, jQuery);

//分级加载areaProvice隐藏，areaCity显示
function selectAreaProviceCallBack(areaInfo){
    $("input[name=cityCode]").val(areaInfo.disNo);
    $("input[name=cityName]").val(areaInfo.disName);
    $("input[name=communityName]").parent().find(".pops-value").html("");//重新选择重置
    $("input[name=communityId]").val("");
    $("input[name=buildingName]").parent().find(".pops-value").html("");
    $("input[name=buildingId]").val("");
    $("input[name=unitName]").parent().find(".pops-value").html("");
    $("input[name=unitId]").val("");
    $("input[name=houseName]").parent().find(".pops-value").html("");
    $("input[name=houseId]").val("");
};