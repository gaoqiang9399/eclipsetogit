;
$(function() {
    /**
     * 2018年7月25日15:58:50
     * yxy
     * 解决不同客户之间弹层返回的数据相互影响的问题；
     */
    top.htmlStrFlag=false;
    //关联关系刷新
    ifRefreshRelation();
    /**滚动条**/
    top.infIntegrity = null;
    $("body").mCustomScrollbar({
        advanced:{
            //滚动条根据内容实时变化
            updateOnContentResize:true
        },
        callbacks: {
            //正在滚动的时候执行回调函数
            whileScrolling: function(){
                if ($(".changeval").length>0) {
                    $(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
                }
            }
        }
    });
    // 授信信息初始化
    // MfCusCredit_cus.init();
    // MfCusCredit.intentionInit();
    /**处理头像信息**/
    var data = headImg;
    if (ifUploadHead != "1") {
        data = "themes/factor/images/" + headImg;
    }
    data = encodeURIComponent(encodeURIComponent(data));
    if(document.getElementById('headImgShow')!=null){
        document.getElementById('headImgShow').src = webPath+ "/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
    }
    /**客户分类展示处理,普通客户，优质客户，黑名单**/
    initCusTrackInfo();
    /**客户信息完整度**/
    if(typeof(cusInfIntegrity) != "undefined" ){
        MfCusDyForm.initCusIntegrity(cusInfIntegrity);
    }
    //初始化关联关系图标
    if(typeof(relation) != "undefined" ){
        getRelation(relation);
    }
    /**展示客户信息模块**/
    //cusTableList：后台传过来的mf_cus_form_config的信息

    if(typeof(cusTableList) != "undefined" ){
        /**客户动态表单处理**/
        MfCusDyForm.init(cusTableList);
    }
    /*
    TongduButton();
    //已选择客户未入业务 客户转单变灰
    if(typeof(MfCusGetNotCusInfo) != "undefined" ){
        MfCusGetNotCusInfo.judgecusslip(cusNo);
    }
    //处理银行卡号
    if ($("[name=MfCusBankAccManageAction]").length == "1") {
        dealBankNo();
    }
    top.LoadingAnimate.stop();

    //根据权限判断是否展示客户信息模块的新增按钮
    /*$.ajax({
        url : webPath+"/mfCusInfoDetail/getUserPmsBizsAjax",
        data : "",
        type : 'post',
        dataType : 'json',
        async:false,
        success : function(data) {
            if(data.flag=="success"){
                var pmBizNo=data.pmBizNo;
                //隐藏社会关系详情新增按钮
                if(pmBizNo.indexOf("cus-list-add-MfCusFamilyInfoAction")<0){
                    $("div[name='MfCusFamilyInfoAction'] .list-table .title .formAdd-btn").hide();
                }
                //隐藏账户管理详情新增按钮
                if(pmBizNo.indexOf("cus-list-add-MfCusBankAccManageAction")<0){
                    $("div[name='MfCusBankAccManageAction'] .list-table .title .formAdd-btn").hide();
                }
            }
        },
        error : function() {
        }
    });*/
    //updateGroupName();
    if(typeof(MfCreditQueryRecordInfo) != "undefined" ){
        MfCreditQueryRecordInfo.init();//尽调报告按钮初始化
    }
    /**客户评级处理**/
    cusEval.init();
    /**客户账户状态处理**/    initCusAccountStsInfo();
    /**信息变更记录处理**/
    cusInfoChangeButton();
    //调用贷后检查初始化方法，处理是否已存在贷后检查
    //BusExamine.init();
    top.LoadingAnimate.stop();
});
function getIfLocal(areaNo){
    $.ajax({
        url : webPath + "/mfCusPersBaseInfo/getIfLocalAjax",
        data : {
            areaNo:areaNo
        },
        type : 'post',
        dataType : 'json',
        async: false,
        success : function(data) {
            if (data.flag == "success") {
                top.ifLoc = data.ifLoc;
                top.ifLocName = data.ifLocName;
            }
        },
        error : function() {
        }
    });
}

//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){
    if(key=="careaCity"){
        data["careaProvice"] = $("input[name=careaProvice]").val();//存储最终的编号
        top.ifLoc = null;
        top.ifLocName = null;
        getIfLocal($("input[name=careaProvice]").val());
        if(typeof (top.ifLoc) != "undefined" && top.ifLoc != null){
            data["ifLoc"] = top.ifLoc;
            $("span[field-name='ifLoc']").find("div[class='fieldShow']").html(top.ifLocName);
            $("select[name='ifLoc']").val(top.ifLoc);
        }
    }else if(key=="wayClassName"){
        data["wayClass"] = $("input[name=wayClass]").val();
        data["wayMaxClass"] = $("input[name=wayMaxClass]").val();
    }else if(key=="beginDate" || key=="endDate"){
        data["loanTerm"] = $(".loanTerm").text();
    }else if(key=="recommenderName"){//推荐者
        data["recommenderNo"] = $("input[name=recommenderNo]").val();//推荐者的编号
        var recommenderPhone =  $("input[name=recommenderPhone]").val();
        if(recommenderPhone!=undefined&&recommenderPhone!=""&&recommenderPhone!=null){
            data["recommenderPhone"] = $("input[name=recommenderPhone]").val();//推荐者手机号
        }
    }else if(key=="address"){
        if($("input[name=carea]").length>0){
            data["carea"] = $("input[name=carea]").val();//存储最终的编号
        }
        if($("input[name=careaCountry]").length>0){
            data["careaCountry"] = $("input[name=careaCountry]").val();//存储最终的编号
        }
    }else if(key=="investigateOpName"){//调查人(权益分析)
        data["investigateOpNo"] = $("input[name=investigateOpNo]").val();//存储最终的编号
    }else if(key=="investigationPeople"){//调查人(现场调查)
        data["investigationPeopleNo"] = $("input[name=investigationPeopleNo]").val();//存储最终的编号
    }else if(key=="cusMngName"){//客户经理
        data["cusMngNo"] = $("input[name=cusMngNo]").val();//存储最终的客户经理编号
    }else if(key=="auxiliaryCusMngName"){//辅办客户经理
        data["auxiliaryCusMngNo"] = $("input[name=auxiliaryCusMngNo]").val();//存储最终的辅办客户经理编号
    }else if(key=="channelSource"){//辅办客户经理
        data["channelSourceNo"] = $("input[name=channelSourceNo]").val();//存储渠道编号
    }else if(key == "wayClassDetail"){//行业分类
        data["wayClass"] = $("input[name=wayClass]").val();
        data["wayMaxClass"] = $("input[name=wayMaxClass]").val();
    }else if(key == "wayClassDes"){
        data["wayClass"] = $("input[name=wayClass]").val();
        data["wayMaxClass"] = $("input[name=wayMaxClass]").val();
    }else if(key=="groupName"){//上级机构
        data["groupNo"] = $("input[name=groupNo]").val();//上级机构编号
    }

    if(key=="province"){  //修改省份 存入的省份id改变，城市置空重选
        data["provinceId"] = $("input[name=provinceId]").val();
        data["city"] = "";
        data["cityId"] = "";
    }else if(key=="city"){  //修改城市 存入的城市id改变
        data["cityId"] = $("input[name=cityId]").val();
    }
    if(key == "idNum")
    {
        //根据身份证号计算出出生年月和性别
        var val = $("input[name=idNum]").val();
        if(18==val.length){
            var sexValue;
            if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16)) {
                sexValue = '0';
            }
            else {
                sexValue = '1';
            }
            var birthdayValue = val.substring(6,10) + "-" + val.substring(10,12) + "-" + val.substring(12,14);
            var myDate = new Date();
            var month = myDate.getMonth() + 1;
            var day = myDate.getDate();
            var ageValue = myDate.getFullYear() - val.substring(6, 10) - 1;
            if (val.substring(10, 12) < month || val.substring(10, 12) == month && val.substring(12, 14) <= day) {
                ageValue++;
            }
            data["age"] = sexValue;
            data["sex"] = sexValue;
            data["brithday"] = birthdayValue;
            if(sexValue == "0")
            {
                sexValue = "男";
            }else{
                sexValue = "女";
            }
            $("div[class='fieldShow z']").text(sexValue);
            $("div[class='fieldShow z1']").text(birthdayValue);
        }
    }
    if(key == "quantity"){
        //收入数量
        var quantity = $("input[name=quantity]").val();
        //收入单价
        var purchasePrice = $("input[name=purchasePrice]").val();
        quantity = quantity.replace(",","");
        purchasePrice = purchasePrice.replace(",","");
        var quantity1 = quantity.replace(",","");
        var purchasePrice1 = purchasePrice.replace(",","");
        var nowPrice = 0.00;
        nowPrice = (quantity1 * 1) * (purchasePrice1 * 1);
        var nowPrice1 = nowPrice.toFixed(2);//保留两位小数
        $("div[class='fieldShow z']").text(nowPrice1);
    }
    if(key == "purchasePrice"){
        //收入数量
        var quantity = $("input[name=quantity]").val();
        //收入单价
        var purchasePrice = $("input[name=purchasePrice]").val();
        quantity = quantity.replace(",","");
        purchasePrice = purchasePrice.replace(",","");
        var quantity1 = quantity.replace(",","");
        var purchasePrice1 = purchasePrice.replace(",","");
        var nowPrice =0.00;
        nowPrice = (quantity1*1)*(purchasePrice1*1);
        var nowPrice1 = nowPrice.toFixed(2);//保留两位小数
        $("div[class='fieldShow z']").text(nowPrice1);
    }
    if(key == "contactsTel"){
        $(".head-content").find("span[id='contactsTel']").html($("input[name=contactsTel]").val());
    }
    if(key == "commAddress"){
        $(".head-content").find("span[id='commAddress']").html($("input[name=commAddress]").val());
    }
}
//证件类型变化是修改证件号码验证规则
function idTypeChange(obj){
    //证件号码格式验证
    var idType = $(obj).val();
    var $idNum = $("input[name=legalIdNum]")[0];
    if(idType=="0"){//身份证样式格式
        //如果是身份证，添加校验
        changeValidateType($idNum, "idnum");
    }else{
        changeValidateType($idNum, "");
    }
    $(obj).parent().parent().find("i").click();
}
//初始化关联关系图标
function getRelation(relation){
    var level;
    if(relation){
        level = "L2";
    }else{
        level = "L1";
    }
    $(".btn-relation").attr("level", level);
}

//初始化客户分类信息
function initCusTrackInfo(){
    if(typeof(rankTypeName)!="undefined"){
        $("#cusNameRate-span").text(rankTypeName);
    }
    if(typeof(cusClassify)!="undefined"){
        if (cusClassify == '1') {
            $("#cusNameRate-span").text("黑名单");
            $(".cus-tag").addClass("btn-danger");
            $(".cus-tag").removeClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-lightgray");
            $(".cus-tag").removeClass("btn-dodgerblue");
        } else if (cusClassify == '2') {
            $(".cus-tag").addClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-danger");
            $(".cus-tag").removeClass("btn-lightgray");
            $(".cus-tag").removeClass("btn-dodgerblue");
        }else if (cusClassify == '4') {
            $("#cusNameRate-span").text("潜在客户");
            $(".cus-tag").addClass("btn-lightgray");
            $(".cus-tag").removeClass("btn-danger");
            $(".cus-tag").removeClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-dodgerblue");
        }else if (cusClassify == '5') {
            $(".cus-tag").addClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-danger");
            $(".cus-tag").removeClass("btn-lightgray");
            $(".cus-tag").removeClass("btn-dodgerblue");
        } else if (cusClassify == '3') {
            $(".cus-tag").addClass("btn-dodgerblue");
            $(".cus-tag").removeClass("btn-danger");
            $(".cus-tag").removeClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-lightgray");
        } else {
            $("#cusNameRate-span").text("潜在客户");
            $(".cus-tag").addClass("btn-lightgray");
            $(".cus-tag").removeClass("btn-danger");
            $(".cus-tag").removeClass("btn-forestgreen");
            $(".cus-tag").removeClass("btn-dodgerblue");
        }
    }

};

//初始化客户账户状态信息
function initCusAccountStsInfo(){
    if(typeof(cusAccountStatus)!=="undefined" && cusAccountStatus){
        if(cusAccountStatusName){
            $("#cusAccountStatus-span").text(cusAccountStatusName);
        }
        if (cusAccountStatus == '01' || cusAccountStatus == '03') {
            $("#cusAccountStatus-button").attr("class","btn btn-view btn-dodgerblue");//系统蓝
        } else if (cusAccountStatus == '02' || cusAccountStatus == '04') {
            $("#cusAccountStatus-button").attr("class","btn btn-view btn-danger");//提醒红
        } else if (cusAccountStatus == '05' || cusAccountStatus == '06') {
            $("#cusAccountStatus-button").attr("class","btn btn-view cus-middle");//预警黄
        }  else {
            $("#cusAccountStatus-span").text("未开户");
            $("#cusAccountStatus-button").attr("class","btn btn-view btn-lightgray");//灰的
        }
    }
};

//查询客户是否有信息变更记录
function cusInfoChangeButton(){
    if ($('#cusInfoChangeRecordQuery').length > 0) {//检查是否存在信息变更记录按钮
        $.ajax({
            url: webPath + '/mfCusInfoChange/getListByCusNoAjax',
            type: 'post',
            data: {cusNo: cusNo},
            async: false,
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    $("#cusInfoChangeRecordQuery").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#cusInfoChangeRecordQuery").addClass("btn-dodgerblue");// 添加蓝色
                } else {
                    $("#cusInfoChangeRecordQuery").removeClass("btn-dodgerblue");// 去掉蓝色
                    $("#cusInfoChangeRecordQuery").addClass("btn-lightgray");// 添加灰色
                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    }
};

//
function getInfList(){
    top.updateFlag = false;
    top.ifCusFinMain = false;
    top.isUpload = false;
    var urlParam = '?cusNo='+ cusNo+"&relNo="+cusNo+"&formEditFlag="+formEditFlag;
    top.openBigForm(webPath+'/mfCusCustomer/getCusInfIntegrityList' + urlParam,'客户信息完整度详情',function() {
        if(top.ifCusFinMain){
            if(top.isUpload){  //财务报表上传成标志
                $.ajax({
                    url : webPath+"/cusFinMain/queryCusFinDataAjax",
                    data : {
                        cusNo : cusNo
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        getFinDataHtml(data);
                    },
                    error : function() {
                    }
                });
            }
        }else{
            addCusFormInfoCall();
            if (top.updateFlag) {
                $.ajax({
                    url : webPath+"/mfCusTable/getListAjax"+urlParam+"&delFlag=0&dataFullFlag=0",
                    type : "POST",
                    dataType : "json",
                    success : function(data) {
                        if (data.flag == "success") {
                            $("#rotate-body").empty();
                            MfCusDyForm.init(data.cusTableList);
                        }
                    },error : function() {}
                });
            }
        }

    });
}
//处理银行卡号
function dealBankNo(){
    $(".bankNo").each(function(i, item) {
        var itemBankNo = $(item).text();
        var itemBankNoHtml = $(item).html();
        if(/\S{5}/.test(itemBankNo)){
            $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
        }
    });
}

//家庭资产表
function showFamilyTable() {
    var filename = "";
    var filepath = "";
    $.ajax({
        url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo?templateNo=18080217110288358",
        type:"post",
        data:"",
        async: false,
        dataType:"json",
        success:function(data){
            var filepath = "";
            var saveBtn = "0";
            var saveUrl = "";
            if(data.saveFlag !="0"){
                saveBtn = "0";
                filepath = "";
                fileName = data.fileName;
            }else{
                //saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+relNo+"&filename="+filename+"&templateNo="+templateNo;
            }
            var poCntJson = {
                filePath : "",
                fileName : data.fileName,
                cusNo : cusNo,
                templateNo :18080217110288358,
                saveUrl :saveUrl,
                saveBtn : saveBtn,
                fileType : 0
            };
            mfPageOffice.openPageOffice(poCntJson);
        },error:function(){alert('error');}
    });
}
//家庭利润表
function showFamilyProfitTable() {
    var filename = "";
    var filepath = "";
    $.ajax({
        url: webPath+"/mfTemplateModelRel/getIfSaveTemplateInfo?templateNo=18080417110288358",
        type:"post",
        async: false,
        dataType:"json",
        success:function(data){
            var filepath = "";
            var saveBtn = "0";
            var saveUrl = "";
            if(data.saveFlag !="0"){
                saveBtn = "0";
                filepath = "";
                filename = data.fileName;
            }else{
                //saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo="+relNo+"&filename="+filename+"&templateNo="+templateNo;
            }
            var poCntJson = {
                filePath : "",
                fileName : data.fileName,
                cusNo : cusNo,
                templateNo :18080417110288358,
                saveUrl :saveUrl,
                saveBtn : saveBtn,
                fileType : 0
            };
            mfPageOffice.openPageOffice(poCntJson);
        },error:function(){alert('error');}
    });
}


function addCusFormInfoCall() {
    var action = new Array("MfCusEquityInfoAction","MfCusFamilyInfoAction","MfCusGuaranteeOuterAction","MfCusHighInfoAction","MfCusKeyManAction","MfCusLegalEquityInfoAction","MfCusLegalMemberAction","MfCusShareholderAction","MfCusFamilyInfoAction");
    if (top.addFlag) {
        MfCusDyForm.initCusIntegrity(top.infIntegrity);
        var cusRelation = action.indexOf(top.action);//判断唯一表中是否存入与关联关系有关的信息
        if(cusRelation!="-1"){
            var Relation = true;
            getRelation(Relation);
        }
        //调用关联关系按钮初始化的方法
        initRelationButton();
        $("#rotate-body").find(".rotate-div[name=" + top.action + "]").remove();
        if ($(".dynamic-block[name=" + top.action + "]").length) {
            var $dynamicBlock = $(".dynamic-block[name=" + top.action + "]");
            $dynamicBlock.find(".formDel-btn").remove();
            $dynamicBlock.show();
            if (top.htmlStrFlag) {
                if (top.showType == "1") {
                    $dynamicBlock.find(".content form").empty();
                    $dynamicBlock.find(".content form").html(top.htmlString);
                    // $dynamicBlock.find(".formAdd-btn").remove();
                } else {
                    $dynamicBlock.find(".content").empty();
                    if (top.action == "MfCusBankAccManageAction") {
                        $dynamicBlock.find(".content").html(top.htmlString+"<input id='updateByOneUrl' type='hidden' value=webPath+'/mfCusBankAccManage/updateByOneAjax'></input>");
                    }else{
                        $dynamicBlock.find(".content").html(top.htmlString);
                    }

                }
                if(top.action=="MfCusCorpBaseInfoAction"){
                    refreshCustomerInfo();
                }
            }
            $("table td[mytitle]:contains('...')").initMytitle();


            var $div = $("div[id="+top.action+"]");
            FormFactor.moreCallBack($div,cusNo,top.action,formEditFlag,top.title)
        } else {
            if (top.htmlStrFlag) {
                MfCusDyForm.setBlock(top.showType, top.title, top.action, top.htmlString,"1", top.isMust, top.tableName, null, null,top.sort);
                dblclickflag();
                if(top.action=="MfCusCorpBaseInfoAction"||top.action=="MfCusPersBaseInfoAction"){
                    refreshCustomerInfo();
                }
            }
        }
        if (top.action == "MfCusBankAccManageAction") {
            dealBankNo();
        }
    }
};
//调用关联关系按钮初始化的方法
function  initRelationButton(){
    $.ajax({
        url:webPath+"/mfCusRelation/isCustomerRelation",
        data : {"cusNo":cusNo},
        type : 'post',
        dataType : 'json',
        async: false,
        success : function(data) {
            if (data.flag=="success") {
                var Relation = true;
                getRelation(Relation);
            }
        }
    });

}
// 获取业务信息
function getBusInfo(appId) {
    LoadingAnimate.start();
    if(busEntrance=="" || busEntrance=="null"){
        busEntrance="apply";
    }
    if(completeFlag == '0'){
        window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
    }else{
        window.location.href = webPath+"/mfBusApply/getSummary?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
    }
};
// 获取合同信息
function getPactInfo(appId) {
    LoadingAnimate.start();
    if(busEntrance=='3'||busEntrance=='6' || busEntrance=='finc'){
        window.location.href=webPath+"/mfBusPact/getPactFincById?fincId="+fincId+"&appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
    }else{
        if(busEntrance=="" || busEntrance=="null"){
            busEntrance="2";
        }
        window.location.href = webPath+"/mfBusPact/getById?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
    }
};
//获取债权信息
function getCreditorInfo(creditId) {
    LoadingAnimate.start();
    window.location.href = webPath+"/p2pCreditorPoolManage/getById?creditId=" + creditId;

};
function getBusDetail(obj, urlThis) {
    var parm = urlThis.split("?")[1];
    var parmArray = parm.split("&");
    var appIdThis = parmArray[0].split("=")[1];
    var appStsThis = parmArray[1].split("=")[1];
    if (appStsThis == "4") {// 表示申请审批通过
        window.location.href = webPath+"/mfBusPact/getById?appId="
            + appIdThis;
    } else {
        window.location.href = webPath+"/mfBusApply/getSummary?appId="
            + appIdThis;
    }
};
function addBlockInfo() {
    // 如果客户基础信息不存在，则先录入基本信息
    /*
     * if(cusBaseFlag == '0'){ addBaseInfo(); return false; }
     */
    $.ajax({
        url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
        type : "post",
        dataType : "json",
        success : function(data) {

            if (data.fullFlag == '1') {// 全部都填写了
                alert("客户资料已经全部完善", 0);
            } else if (data.fullFlag == '0') {
                top.addFlag = false;
                top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
                top.htmlString = null;
                top.action = null;
                top.title = null;
                top.name = null;
                top.cusNo = cusNo;
                top.showType = null;
                top.baseInfo = null;
                // top.baseInfo="0";标识 该表单信息是否是客户的基本信息
                top.openBigForm(webPath+'/mfCusTable/getListPage?cusNo='+ cusNo + '&dataFullFlag=0', '选择表单',addCusFormInfoCall);
            }
        },
        error : function() {

        }
    });

};

function getCusInfoById(obj, getUrl) {// 根据列表超链接获得信息详情，支持编辑
    var $dynamicBlock = $(obj).parents(".dynamic-block");
    var title = $dynamicBlock.attr("title");
    var action = $dynamicBlock.attr("name");
    top.action = action;
    top.showType = "2";
    top.addFlag = false;
    top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
    top.htmlString = null;
    top.baseInfo = null;
    top.getTableUrl = action + "_getListPage.action?cusNo=" + cusNo;
    top.openBigForm(getUrl, title, addCusFormInfoCall);
};
//客户视角要件必填验证
function validateDocMustInput(){
    var len = $(".upload_body").find(".btspan0").length;
    if(len>0){
        var docMsg="";
        $(".upload_body").find(".btspan0").each(function(index){
            var $pThis = $(this).parent();
            docMsg =docMsg+"["+$pThis.data("docname")+"]、";
        });
        alert(top.getMessage("NO_UPLOAD",docMsg.substring(0,docMsg.length-1)),0);
        return false;
    }
    return true;
}

// 业务办理
function applyInsert() {
    // 客户新增业务前置校验（是否开户、是否存在保证金收取中的放款申请）
    var params = {
        "cusNo" : cusNo
    };

    if(!validateDocMustInput()){
        return false;
    }
    if(firstKindNo == '' || firstKindNo == null){
        alert(top.getMessage("FIRST_CHECK_PRODUCT"),0);
        return ;
    }
    //校验客户基本信息处的必填要件上传
    if( !valiCusDocIsUp(cusNo)){
        return false;
    }
    // 判断该客户是否完善了基本信息
    $.ajax({
        url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
        success : function(data) {
            if (data.fullFlag == '1') {// 全部都填写了
                // 准入拦截
                var parmData = {'nodeNo':'cus_apply', 'relNo': cusNo, 'cusNo': cusNo};
                $.ajax({
                    url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
                    type : "post",
                    data : {ajaxData: JSON.stringify(parmData)},
                    dataType : "json",
                    success : function(data) {
                        if (data.exsitRefused == true) {// 存在业务拒绝
                            top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
                        }else if(data.exsitFX == true){//存在风险项
                            alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"新增业务"}), 2, function() {
                                window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
                            });
                        }else if(data.isCredit == "0"){
                            alert("该客户未授信,请先进行授信操作", 0);
                        }else{
                            // top.createShowDialog(webPath+"/mfBusApply/inputQuery?cusNo="+cusNo+"&kindNo="+firstKindNo,"业务申请");
                            //传appId时是为了在业务新增页面取消时返回到原来的页面
                            window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
                        }
                    },
                    error : function() {
                    }
                });
            } else if (data.fullFlag == '0') {
                alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
            }
        }
    });

};
//进件处控制 客户基本信息要件的上传
function valiCusDocIsUp(relNo){
    var flag = true;
    $.ajax({
        type: "post",
        dataType: 'json',
        url:webPath+ "/docBizManage/valiCusDocIsUp",
        data:{relNo:relNo},
        async: false,
        success: function(data) {
            if(!data.flag){
                window.top.alert(data.msg,0);
            }
            flag = data.flag;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
        }
    });
    return flag;
}

function addBaseInfo() {// 增加客户的基本信息
    top.addFlag = false;
    top.action = "MfCusCorpBaseInfoAction";
    top.showType = '1';
    top.baseInfo = "1";
    top.name = "MfCusCorpBaseInfoAction";
    top.openBigForm(webPath+"/mfCusCorpBaseInfo/input?cusNo=" + cusNo,"基本信息", closeCallBack);
};
function checkCusInfoIsFull() {// 验证客户信息是否已经填写完整
    $.ajax({
        url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
        type : "post",
        dataType : "json",
        success : function(data) {
            if (data.fullFlag == '1') {// 全部都填写了

            } else if (data.fullFlag == '0') {

            }
        },
        error : function() {

        }
    });
};

// 客户要件信息
function cusDocInfo() {
    window.parent.openBigForm(webPath+'/docManage/pubUpload?relNo=' + cusNo + '&cusNo=' + cusNo + '&scNo=' + scNo, '图文资料', function() {
    });
};

//客户跟进
function cusTrack(type) {
    top.updateFlag = false;
    top.openBigForm(webPath+'/mfCusTrack/getListPage?cusNo=' + cusNo+ "&query=" + type,'客户跟进',function(){
        if (top.updateFlag){
            getCusTrackTopList();
        }
    });
};


// 客户分类
function cusTag() {
    top.updateFlag = false;
    top.classify = false;
    top.openBigForm(webPath+'/mfCusClassify/getByCusNo?cusNo=' + cusNo,'客户分类', function() {
        if (top.updateFlag) {
            if (top.cusClassify == '1') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            } else if (top.cusClassify == '2') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            } else if (top.cusClassify == '4') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }else if (top.cusClassify == '5') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }else if (top.cusClassify == '3') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-dodgerblue");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
            } else {
                $("#cusNameRate-span").text("潜在客户");
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }
            top.updateFlag = false;
            top.classify = false;
            top.cusClassify="";
        }
    });
};

function cusTagHis() {
    top.updateFlag = false;
    top.classify = false;
    top.openBigForm(webPath+'/mfCusClassify/getListPage?cusNo=' + cusNo,'客户分类', function() {
        if (top.updateFlag) {
            if (top.cusClassify == '1') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            } else if (top.cusClassify == '2') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }  else if (top.cusClassify == '4') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }else if (top.cusClassify == '5') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }else if (top.cusClassify == '3') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-dodgerblue");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
            } else {
                $("#cusNameRate-span").text("潜在客户");
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }
            // 准入拦截
            var parmData = {'nodeNo':'cus_apply', 'relNo': cusNo, 'cusNo': cusNo};
            $.ajax({
                url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
                type : "post",
                data : {ajaxData: JSON.stringify(parmData)},
                dataType : "json",
                success : function(data) {
                    $.ajax({
                        url : webPath+"/riskBizItemRel/getRiskInfoAjax",
                        type : "post",
                        data : {cusNo: cusNo},
                        dataType : "json",
                        success : function(data) {
                            $("#cusRiskLevel").find("span").text(data.riskName);
                            var className = "btn risklevel" + data.riskLevel + " btn-view"
                            $("#cusRiskLevel").attr("class",className);
                        },
                        error : function() {
                        }
                    });
                },
                error : function() {
                }
            });
            top.updateFlag = false;
            top.classify = false;
            top.cusClassify="";

        }
    });
};

//单独提交业务流程
function doCommitProcess(){
    $.ajax({
        url:webPath+"/mfCusCreditApply/commitBusProcessAjax?wkfAppId="+wkfAppId,
        success:function(data){
            if(data.flag=="success"){
                alert(data.msg,1);
                getNextBusPoint();
                $("#wj-modeler1").empty();
                showWkfFlow($("#wj-modeler1"),wkfAppId);
            }else{
                alert(data.msg,0);
            }
        }
    });
}
//提交审批
function processSubmitAjax(){
    if(top.creditFlag){
        wkfCallBack();
        alert(top.getMessage("CONFIRM_OPERATION","提交到审批"),2,function(){
            LoadingAnimate.start();
            $.ajax({
                url : webPath+"/mfCusCreditApply/processSubmitAjax",
                type : 'post',
                dataType : 'json',
                data : {
                    cusNo : cusNo,
                    wkfAppId : wkfAppId
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        if (data.node == "processaudit") {
                            window.top.alert(data.msg, 3);
                            //实时更新授信状态
                            $.ajax({
                                url : webPath+"/mfCusCreditApply/getCreditStsInfo",
                                data : {
                                    wkfAppId : wkfAppId
                                },
                                type : 'post',
                                dataType : 'json',
                                success : function(data) {
                                    var status = data.status;
                                    var creditSum = data.creditSum;
                                    var applySum = data.applySum;
                                    getCreditSts(status,creditSum,applySum);
                                },
                                error : function() {
                                    alert(data.msg, 0);
                                }
                            });
                            getNextBusPoint();
                            $("#wj-modeler1").empty();
                            showWkfFlow($("#wj-modeler1"),wkfAppId);
                        } else {
                            getNextBusPoint();
                            $("#wj-modeler1").empty();
                            showWkfFlow($("#wj-modeler1"),wkfAppId);
                        }
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
    }
}

// 上传头像
function uploadHeadImg() {
    window.parent.openBigForm(webPath+'/mfCusCustomer/uploadHeadImg?relNo='+ cusNo + '&cusNo=' + cusNo, '客户头像',showNewHeadImg);
};
function showNewHeadImg() {
    var data;
    $.ajax({
        url : webPath+"/mfCusCustomer/getIfUploadHeadImg",
        data : {
            cusNo : cusNo
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if (data.flag == "1") {
                data = encodeURIComponent(encodeURIComponent(data.headImg));
                document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
                    + data + "&fileName=user2.jpg";
            } else {
                data = "themes/factor/images/" + data.headImg;
                document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
                    + data + "&fileName=user2.jpg";
            }
        },
        error : function() {
//					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
        }
    });
    delFile();
};
//-------------------------------------财务报表模块功能 START-----------------------------------------------------------//
//获得财务报表信息
function getPfsDialog() {
    top.isUpload = false;
    top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo='+cusNo,'财务报表', function() {
        if(top.isUpload){  //财务报表上传成标志
            $.ajax({
                url : webPath+"/cusFinMain/queryCusFinDataAjax",
                data : {
                    cusNo : cusNo
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    getFinDataHtml(data);
                }
            });
        }
    });
};
//获得跟名下企业财务报表信息
function getPersonPfsDialog() {
    top.isUpload = false;
    $.ajax({
        url : webPath+"/cusFinMain/getPersonCorpList",
        data : {
            cusNo : cusNo
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.corpLen!=0){
                top.openBigForm(webPath+'/cusFinMain/getListPageForPerson?cusNo='+cusNo,'财务报表', function() {
                    if(top.isUpload){  //财务报表上传成标志
                        $.ajax({
                            url : webPath+"/cusFinMain/queryCusFinDataAjax",
                            data : {
                                cusNo : cusNo
                            },
                            type : 'post',
                            dataType : 'json',
                            success : function(data) {
                                getFinDataHtml(data);
                            },
                            error : function() {
                            }
                        });
                    }
                });
            }else{
                alert("请先登记个人名下企业信息!",0);
            }
        },
        error : function() {
        }
    });
};
//组装财务报表信息块
// function getFinDataHtml(data){
// 	MfCusDyForm.initCusIntegrity(top.infIntegrity);
// 	if(data.cusFinMainList.length > 0){
// 		$("div[name=MfCusFinMainAction]").remove();
//         var comparisonStr = "";
//         if(BussNodePmsBiz.checkPmsBiz('cus-multi-period-comparison')){
//             comparisonStr = "<div style='margin-top: -28px;margin-left: 145px;' ><a href='javascript:void(0);' onclick='multiPeriodComparisonView();'>多期对比</a></div>";
//         }
// 		var finDiv = '<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block">'+
//             '<div class="list-table">'+
// 			'<div class="title">'+
// 				'<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>'+
// 				'<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
//                 comparisonStr+
//                 '<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
// 					'<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
// 				'</button>'+
// 			'</div>'+
// 			'<div id="CusFinMainAction" class="content collapse in">'+
// 				'<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
// 					'<colgroup style="width: 10%"></colgroup>'+
// 					'<colgroup style="width: 10%"></colgroup>'+
// 					'<colgroup style="width: 10%"></colgroup>'+
// 					'<colgroup style="width: 10%"></colgroup>'+
// 					'<colgroup style="width: 10%"></colgroup>'+
// 					'<thead>'+
// 						'<tr>'+
// 							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
// 							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
// 							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
// 							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
// 							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">科目余额表</th>'+
// 							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
// 						'</tr>'+
// 					'</thead>'+
// 					'<tbody id="tab">';
// 		var htmlStr = "";
// 		$.each(data.cusFinMainList,function(i,cusFinMain){
// 			var viewStr = webPath+"/cusFinMain/inputReportView?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1";
// 			var confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
// 			var delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
//             var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
//                 proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
//                 cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
//                 subjectStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
//             var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
//             if(reportConfirmFlag != 2){
//                 if(cusFinMain.finRptSts != 1){
//                     opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
//                 }else{
//                     zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
// 					proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
// 					cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
// 					subjectStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
//                 }
//             }
//             if(cusFinMain.ifShowDel == "1"){
//                 opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
//             }
//             if(cusFinMain.ifShowDel == "0"){
//                 opStr = opStr + '<span class="listOpStyle">删除</span>';
//             }
//
// 			if(!cusFinMain.finCapFlag){
// 				zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
// 				if(cusFinMain.finRptSts == 1){
// 					zcStr = "上传";
// 				}
// 			}
// 			if(!cusFinMain.finProFlag){
// 				proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
// 				if(cusFinMain.finRptSts == 1){
// 					proStr = "上传";
// 				}
// 			}
// 			if(!cusFinMain.finCashFlag){
// 				cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
// 				if(cusFinMain.finRptSts == 1){
// 					cashStr = "上传";
// 				}
// 			}
// 			if(!cusFinMain.finSubjectFlag){
// 				subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
// 				if(cusFinMain.finRptSts == 1){
//                     subjectStr = "上传";
// 				}
// 			}
// 			htmlStr += '<tr>'+
// 							'<td align="center" width="10%">'+
// 								'<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
// 							'</td>'+
// 							'<td align="center" width="15%">'+
// 								zcStr+
// 							'</td>'+
// 							'<td align="center" width="15%">'+
// 								proStr+
// 							'</td>'+
// 							'<td align="center" width="15%">'+
// 								cashStr+
// 							'</td>'+
// 							'<td align="center" width="15%">'+
// 								subjectStr+
// 							'</td>'+
// 							'<td align="center" width="20%">'+
// 								opStr+
// 							'</td>'+
// 						'</tr>';
// 		});
// 		finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>';
// 		$(".block-add").after(finDiv);
// 	}
// }
function getFinDataHtml(data){
    MfCusDyForm.initCusIntegrity(top.infIntegrity);
    if(data.cusFinMainList.length > 0){
        $("div[name=MfCusFinMainAction]").remove();
        var subjectDataFlag=BussNodePmsBiz.checkPmsBiz('cus-edit-SubjectData');
        var comparisonStr = "";
        // if(BussNodePmsBiz.checkPmsBiz('cus-multi-period-comparison')){
        //     comparisonStr = "<span style='margin-top: -28px;margin-left: 145px;' ><a href='javascript:void(0);' onclick='multiPeriodComparisonView();'>多期对比</a></span>";
        comparisonStr = "<button  class=' btn btn-link formEdit-btn' onclick='multiPeriodComparisonView()'; '>多期对比</button>";
        // }
        var finDiv = '<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block">'+
            '<div class="list-table">'+
            '<div class="title">';

        if(data.cusType=="2") {  //个人客户时
            finDiv +=  '<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表</span>'+
                '<button title="新增" onclick="getPersonPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>';
        }else{
            finDiv +=  '<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>'+
                '<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>';
        }
        finDiv +=    comparisonStr+
            '<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
            '<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
            '</button>'+
            '</div>'+
            '<div id="CusFinMainAction" class="content collapse in">'+
            '<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<thead>'+
            '<tr>'+
            '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">选择</th>'+
            '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>';
        if(data.cusType=="2"){  //个人客户时
            finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">名下企业名称</th>';
        }
        finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>';
        if(subjectDataFlag) {
            finDiv += '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">科目余额表</th>';
        }
        if(data.cusType=="1"){  //个人客户时
            finDiv +=  '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">口径</th>';
        }
        finDiv += '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody id="tab">';
        var htmlStr = "";
        $.each(data.cusFinMainList,function(i,cusFinMain){
            var viewStr ="";
            var delStr="";
            var confirStr ="";
            if(data.cusType=="2") {  //个人客户时
                viewStr = webPath + "/cusFinMain/inputReportView?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo + "&accRule=1&relationCorpNo=" + cusFinMain.corpNo;
                delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&relationCorpNo=" + cusFinMain.corpNo;
                confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&subjectDataFlag="+subjectDataFlag+"&relationCorpNo=" + cusFinMain.corpNo;
            }else{
                viewStr = webPath + "/cusFinMain/inputReportView?finRptType=" + cusFinMain.reportType + "&finRptDate=" + cusFinMain.weeks + "&cusNo=" + cusFinMain.cusNo + "&accRule=1";
                delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo;
                confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.reportType+"&finRptDate="+cusFinMain.weeks+"&cusNo="+cusFinMain.cusNo+"&subjectDataFlag="+subjectDataFlag;
            }
            var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                subjectStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            var caliberStr = "";
            if(data.cusType=="1") {
                if(cusFinMain.caliber=='1'){
                    caliberStr =  '<span class="listOpStyle">税务</span>&nbsp;&nbsp;&nbsp;&nbsp;';
                }
                if(cusFinMain.caliber=='2'){
                    caliberStr =  '<span class="listOpStyle">管理</span>&nbsp;&nbsp;&nbsp;&nbsp;';
                }
            }
            if(cusFinMain.reportSts != 2){
                opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
            }else{
                zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                    proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                    cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                    subjectStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
            }

            if(cusFinMain.isUsed != 1){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.isUsed == 1){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }

            if(cusFinMain.assetsDataId == null||cusFinMain.assetsDataId == ""){
                if(data.cusType=="2") {  //个人客户时
                    zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                }else{
                    zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                }
                if(cusFinMain.reportSts == 2){
                    zcStr = "上传";
                }
            }
            if(cusFinMain.incomeDataId==null || cusFinMain.incomeDataId==""){
                if(data.cusType=="2") {  //个人客户时
                    proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                }else{
                    proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                }
                if(cusFinMain.reportSts == 2){
                    proStr = "上传";
                }
            }
            if(cusFinMain.cashDataId==null || cusFinMain.cashDataId==""){
                if(data.cusType=="2") {  //个人客户时
                    cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                }else{
                    cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                }
                if(cusFinMain.reportSts == 2){
                    cashStr = "上传";
                }
            }
            if(subjectDataFlag){
                if(cusFinMain.balanceDataId==null || cusFinMain.balanceDataId==""){
                    if(data.cusType=="2") {  //个人客户时
                        subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                    }else{
                        subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
                    }
                    if(cusFinMain.reportSts == 2){
                        subjectStr = "上传";
                    }
                }
            }
            htmlStr += '<tr>';
            if(data.cusType=="1"){
                htmlStr +=  '<td align="center" width="10%"><input type="checkbox" onclick="selectThis(this)" name="accountId" value="'+cusFinMain.accountId+'" /></td>';
            }
            htmlStr +=  '<td align="center" width="10%">'+
                '<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.weeks+'</a>'+
                '</td>';
            if(data.cusType=="2"){
                htmlStr +=    '<td align="center" width="15%">'+ cusFinMain.corpName+'</td>';
            }
            htmlStr +=   '<td align="center" width="15%">'+
                zcStr+
                '</td>'+
                '<td align="center" width="15%">'+
                proStr+
                '</td>'+
                '<td align="center" width="15%">'+
                cashStr+
                '</td>';
            if(subjectDataFlag) {
                htmlStr += '<td align="center" width="15%">' +
                    subjectStr +
                    '</td>';
            }
            htmlStr += '<td align="center" width="5%">'+caliberStr+'</td>';
            htmlStr += '<td align="center" width="15%">'+
                opStr+
                '</td>'+
                '</tr>';
        });
        finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>';
        $(".block-add").after(finDiv);
    }
}
//组装名下企业财务报表信息块
function getPersonFinDataHtml(data){
    MfCusDyForm.initCusIntegrity(top.infIntegrity);
    if(data.cusFinMainList.length > 0){
        $("div[name=MfCusFinMainAction]").remove();
        var finDiv = '<div name="MfCusFinMainAction" title="名下企业财务报表" class="dynamic-block">'+
            '<div class="list-table">'+
            '<div class="title">'+
            '<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表</span>'+
            '<button title="新增" onclick="getPersonPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
            '<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
            '<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
            '</button>'+
            '</div>'+
            '<div id="CusFinMainAction" class="content collapse in">'+
            '<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<colgroup style="width: 10%"></colgroup>'+
            '<thead>'+
            '<tr>'+
            '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
            '<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">名下企业名称</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">科目余额表</th>'+
            '<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody id="tab">';
        var htmlStr = "";
        $.each(data.cusFinMainList,function(i,cusFinMain){
            var viewStr = webPath+"/cusFinMain/inputReportView?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1"+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
            var confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
            var delStr = "/cusFinMain/deleteAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
            var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',
                subjectStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            if(reportConfirmFlag != 2){
                if(cusFinMain.finRptSts != 1){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',
                        subjectStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }
            }
            if(cusFinMain.ifShowDel == "1"){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.ifShowDel == "0"){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }
            if(!cusFinMain.finCapFlag){
                zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                if(cusFinMain.finRptSts == 1){
                    zcStr = "上传";
                }
            }
            if(!cusFinMain.finProFlag){
                proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                if(cusFinMain.finRptSts == 1){
                    proStr = "上传";
                }
            }
            if(!cusFinMain.finCashFlag){
                cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                if(cusFinMain.finRptSts == 1){
                    cashStr = "上传";
                }
            }
            if(!cusFinMain.finSubjectFlag){
                subjectStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
                if(cusFinMain.finRptSts == 1){
                    subjectStr = "上传";
                }
            }
            htmlStr += '<tr>'+
                '<td align="center" width="10%">'+
                '<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
                '</td>'+
                '<td align="center" width="15%">'+
                cusFinMain.relationCorpName+
                '</td>'+
                '<td align="center" width="15%">'+
                zcStr+
                '</td>'+
                '<td align="center" width="15%">'+
                proStr+
                '</td>'+
                '<td align="center" width="15%">'+
                cashStr+
                '</td>'+
                '<td align="center" width="15%">'+
                subjectStr+
                '</td>'+
                '<td align="center" width="20%">'+
                opStr+
                '</td>'+
                '</tr>';
        });
        finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>';
        $(".block-add").after(finDiv);
    }
}
//打开财务报表查看页面
function reportView(obj,url){
    top.openBigForm(url,'财务报表',false);
};
function selectThis(obj) {
    var $this = $(obj);
    if($this.prop("checked") == false){
        $this.attr('checked',false)
    }
}
//打开财务报表多期对比页面
function multiPeriodComparisonView(){
    // $.ajax({
    //     type:"post",
    //     url:webPath+"/cusFinMain/checkFinDataForConfirmAjax?cusNo="+cusNo,
    //     dataType:"json",
    //     success:function(data){
    //         if(data.flag=="success"){
    //             LoadingAnimate.stop();
    //             var url = webPath+"/cusFinMain/multiPeriodComparisonView?cusNo="+cusNo+"&accRule=1";
    //             top.openBigForm(url,'财务报表多期对比',false);
    //         }else if(data.flag=="error"){
    //             LoadingAnimate.stop();
    //             alert(top.getMessage("FIRST_FINC_VERIFY"),0);
    //         }
    //     },error:function(){
    //         LoadingAnimate.stop();
    //         window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
    //     }
    // });
    var value = new Array();
    $("div[name='MfCusFinMainAction'] input[name='accountId']").each(function () {
        var $this = $(this);
        if($this.prop("checked") == true){
            value.push($this.val());
        }
    });
    if(value.length < 2){
        alert("至少选择两期财务报表！",0);
    }else{
        var accountIdArray = JSON.stringify(value);
        var url = webPath+"/cusFinMain/inputReportContrast?accountIdArray="+value;
        top.window.openBigForm(url,'财务报表',function(){
        });
    }
};
//数据确认
function confirmFinMain(obj,url){
    var parm = "?"+url.split("?")[1];
    $.ajax({
        type:"post",
        url:webPath+"/cusFinMain/checkFinDataAjax"+parm,
        dataType:"json",
        success:function(data){
            if(data.flag=="success"){
                if(data.checkFlag == "success"){
                    alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
                        doCofrimData(obj,url);
                    });
                }else{
                    alert(data.msg,2,function(){
                        doCofrimData(obj,url);
                    });

                }
            }else if(data.flag=="error"){
                alert(top.getMessage("ERROR_FIN_VERIFY"),0);
            }
        },error:function(){
            window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
        }
    });
}

//执行确认操作
function doCofrimData(obj,url){
    var tdObj = $(obj).parent();
    $.ajax({
        url:url,
        success:function(data){
            if(data.flag=="success"){
                $(tdObj).parent().find('td:not(:first)').find(".color_theme").css("color","gray");
                $(tdObj).parent().find('td:not(:first)').find('.abatch').css("color","gray");
                $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('href');//去掉a标签中的href属性
                $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('onclick');//去掉a标签中的onclick事件
            }else if(data.flag=="error"){
                window.top.alert(data.msg,0);
            }
        },error:function(){
            window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
        }
    });
}
//-------------------------------------财务报表模块功能 END-----------------------------------------------------------//

// 删除文件
function delFile() {
    var srcPath = "/tmp/";
    $.ajax({
        url : webPath+"/uploadFile/delFile",
        data : {
            srcPath : srcPath
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {

        },
        error : function() {
//			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
        }
    });
};

function cusRelation() {
    top.openBigForm(webPath+'/mfCusRelation/forDetail?cusNo=' + cusNo,
        '关联关系', function(){});
};
function cusRelationIn() {
    top.relation = false;
    window.parent.openBigForm(webPath+'/mfCusRelationType/input?cusNo='+ cusNo, '新增关联关系', function() {
        if(top.relation){
            getRelation(top.relation);
        }
    });
};
// 打开修改客户基本信息页面
function updateCustomerInfo() {
    var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz("cus-baseInfo-edit");
    if(checkPmsBizFlag){
        top.updateFlag = false;
        top.cusName = "";
        top.contactsName = "";
        top.contactsTel = "";
        top.idNum = "";
        top.commAddress = "";
        top.cusSubTypeName=$(".type-name-div").text();
        top.window.openBigForm(webPath+'/mfCusCustomer/toUpdate?cusNo=' + cusNo, '修改客户信息',refreshCustomerInfo);
    }
}
// 刷新客户登记信息以及基本信息
function refreshCustomerInfo() {
    if (top.updateFlag) {
        if(top.cusBaseType=="2"){
            $("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
            $("span[id=contactsTel]").html(top.cusTel!=null&&top.cusTel!=''?top.cusTel:"<span class='unregistered'>未登记</span>");
            $("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
            $("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
            $("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
            $(".cus.head-title").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
            // 刷新基本信息
            var BaseInfoFlag = $("[name=MfCusPersBaseInfoAction]").length;
            if (BaseInfoFlag == "1") {
                $("#MfCusPersBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
            }
        }else{
            $("div[id=cusNameShow]").html(top.cusName!=null&&top.cusName!=''?top.cusName:"<span class='unregistered'>未登记</span>");
            $("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
            $("span[id=contactsTel]").html(top.contactsTel!=null&&top.contactsTel!=''?top.contactsTel:"<span class='unregistered'>未登记</span>");
            $("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
            $("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
            $("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");

            // 刷新基本信息
            var BaseInfoFlag = $("[name=MfCusCorpBaseInfoAction]").length;
            if (BaseInfoFlag == "1") {
                $("#MfCusCorpBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
            }
        }
        $(".cus-type-font").empty().html('<div class="type-name-div">'+top.cusSubTypeName+'</div>');

        updateGroupName();

        window.location.href = webPath + "/mfCusCustomer/getById?cusNo=" + cusNo;
    }
};

function selectAreaCallBack(areaInfo) {
    $("input[name=careaCity]").val(areaInfo.disName);
    $("input[name=careaProvice]").val(areaInfo.disNo);
};

//分级加载areaProvice隐藏，areaCity显示
function selectAreaProviceCallBack(areaInfo){
    $("input[name=careaProvice]").val(areaInfo.disNo);
    $("input[name=careaCity]").val(areaInfo.disName);
    $("input[name=address]").val(areaInfo.disName);
};
//选择常住地址回调
function selectCommAddressCallBack(areaInfo){
    $("input[name=commAddress]").val(areaInfo.disName);
};
//选择登记机关回调
function selectCusRegAuthorityCallBack(areaInfo){
    $("input[name=cusRegAuthority]").val(areaInfo.disName);
};

function selectAddressCallBack(areaInfo) {
    $("input[name=commAddress]").val(areaInfo.disName);
    $("input[name=careaCountry]").val(areaInfo.disNo);
};

function selectWayClassCallBack(waycls) {
    $("input[name=wayClassName]").val(waycls.disName);
    $("input[name=wayClass]").val(waycls.disNo);
    $("input[name=wayMaxClass]").val(waycls.disNo);
};

function nmdWaycCallBack(nmdWayInfo){
    $("input[name=wayClassDes]").val(nmdWayInfo.wayName);
    $("input[name=wayClass]").val(nmdWayInfo.wayNo);
    $("input[name=industryClass]").val(nmdWayInfo.industryClass);
    $("input[name=wayMaxClass]").val(nmdWayInfo.wayMaxClass);
};

function selectBankAreaCallBack(areaInfo) {
    $("input[name=bankArea]").val(areaInfo.disName);
};

//填充押品信息
function setPleInfo(data) {
    $("#pleInfo").addClass("hide");
    $("#pleInfono").addClass("hide");
    $("#pleInfo").removeClass("show");
    $("#pleInfono").removeClass("show");
    if (data.mfBusPledge.id != null) {
        // 填充业务信息
        setCollateralInfo(data.mfBusPledge);
        MfBusCollateralRel_AbstractInfo.collateralRelId = data.mfBusPledge.busCollateralId;
        $("#pleInfo").removeClass("hide");
        $("#pleInfo").addClass("show");
        if(data.busModel!="5"){
            if(data.evalFlag=="0"){
                $("#pledgeInfo").html("没有登记评估信息");
            }
        }
    }else{
        $("#pleInfono").removeClass("hide");
        $("#pleInfono").addClass("show");
        if(busModel=="13"||busModel=="5"){
            $("#title").html("应收账款");
            $("#noPledge").html("暂无应收账款");
            $("#titleLi").attr("class","i i-rece font-icon");
        }
    }
};


//填充业务信息
function setBusInfo(data) {
    var busInfoObj = data.mfbusInfo;
    var mfBusApplyListTmp = data.mfBusApplyList;
    var appName = busInfoObj.appName;
    if (appName.length > 8) {
        appName = appName.substring(0, 8) + "...";
    }
    var htmlStr="";
    var busId = data.mfbusInfo.appId;
    if(data.showFlag=="apply"){
        htmlStr = '<div class="col-xs-3 col-md-3 column">'
            + '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''+ busId + '\');">'
            + '<i class="i i-applyinfo font-icon"></i>'
            + '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
    }else if(data.showFlag=="pact"){
        htmlStr = '<div class="col-xs-3 col-md-3 column">'
            + '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\');">'
            + '<i class="i i-pact font-icon"></i>'
            + '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
    }
    // 如果业务笔数大于3笔
    if (data.mfBusApplyList.length > 3){
        htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
            + '<div class="row clearfix padding_top_20">'
            + '<span>客户共有 <a  class="moreCnt more-apply-count pointer" onclick="getMultiBusList();">'
            + (data.mfBusApplyList.length + 1)
            +'</a> 笔在履行业务'
            + '</span>'
            + '</div>'
            + '</div>';
    } else {
        htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
            + '<div class="row clearfix">'
            + '<div class="col-xs-10 col-md-10 column">';
        if(frompage=="pledge"){//如果主体页面是押品页面，业务不展示多笔业务的情况
            if(data.showFlag=="apply"){
                htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
            }else if(data.showFlag=="pact"){
                htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
            }
        }else{
            // 如果业务笔数为1条
            if (data.mfBusApplyList.length == 0) {
                if(data.showFlag=="apply"){
                    htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
                }else if(data.showFlag=="pact"){
                    htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
                }
            } else {
                var tmpStr = '';
                $.each(data.mfBusApplyList,function(i, appObj) {
                    var appObjName = appObj.appName;
                    var len = busInfoObj.appName.length;
                    if (appObjName.length > len) {
                        appObjName = appObjName.substring(0, len)+ "...";
                    }
                    tmpStr = tmpStr+ '<li class="more-content-li" onclick="switchBus(\''+ appObj.appId+ '\');">'
                        + '<p class="more-title-p"><span>'+ appObjName+ '</span></p>'
                        + '<p class="more-content-p"><span class="more-span">总金额 '+ appObj.appAmt+ '元</span><span class="more-span">期限 '
                        + appObj.termShow + '</span><span class="more-span">利率 '+ appObj.fincRate + '%</span></p>'
                        + '</li>';
                });
                htmlStr = htmlStr
                    + '<div class="btn-group">'
                    + '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
                    + busInfoObj.appName + '">'+ appName
                    + '</button>'
                    + '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                    + '<span class="badge">' + data.mfBusApplyList.length+ '</span>'
                    + '</button>'
                    + '<ul class="dropdown-menu">'+ tmpStr + '</ul>' + '</div>';
            }
        }
        if(data.showFlag == "apply"){
            htmlStr = htmlStr
                + '</div><div class="col-xs-2 col-md-2 column">'
                + '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getBusInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
                + '</div>';
        }else if(data.showFlag == "pact"){
            htmlStr = htmlStr
                + '</div><div class="col-xs-2 col-md-2 column">'
                + '<button type="button" class="btn btn-font-qiehuan" style="margin-top: -5px;" onclick="getPactInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
                + '</div>';
        }

        var unitStr = "天";
        if (busInfoObj.termType == "1") {
            unitStr = "个月";
        }
        htmlStr = htmlStr + '<p>'
            + '<span class="content-span"><i class="i i-rmb"></i>'+ busInfoObj.fincAmt+ '</span><span class="unit-span">万</span>'
            + '<span class="content-span"><i class="i i-richengshezhi"></i>'+ busInfoObj.term + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
            + '<span class="content-span"><i class="i i-tongji1"></i>'+ busInfoObj.fincRate+ '</span><span class="unit-span">%</span>'
            + '</p>'
            + '</div>';
    }
    $("#busInfo .cont-row").html(htmlStr);
};

function selectConAreaCallBack(areaInfo) {
    $("input[name=address]").val(areaInfo.disName);
    $("input[name=careaCountry]").val(areaInfo.disNo);
    $("input[name=carea]").val(areaInfo.disNo);
};
// 切换业务
function switchBus(appId) {
    // 获取业务以及押品信息，替换原页面相应模块中的内容
    $.ajax({
        type : "post",
        dataType : 'json',
        url : webPath+"/mfBusApply/getSwitchBusInfoAjax",
        data : {appId : appId},
        async : false,
        success : function(data) {

            if (data.flag == "success") {
                setBusInfo(data);
                setPleInfo(data);
            } else {
                alert(data.msg, 0);
            }

        },
        error : function() {
            alert(top.getMessage("ERROR_REQUEST_URL", ""), 0);
        }
    });
};

// 多业务大于3条时，弹层列表页面
function getMultiBusList(flag){
    if('apply'==flag){
        top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中业务",function(){});
    }else if('pact'==flag){
        top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在保项目",function(){});
    }else if('finc'==flag){
        top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行借据",function(){});
    }else if('assure'==flag){
        top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"为他人担保",function(){});
    } else if ('fincFinish' == flag) {
        top.openBigForm(webPath + "/mfBusFincApp/getMultiBusFinishList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已完成担保", function () {
        });
    } else if ('repay' == flag) {
        top.openBigForm(webPath + "/mfRepayHistory/getMfRepayHistoryList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "已还历史", function () {
        });
    } else if ('earlyWarning' == flag) {
        top.openBigForm(webPath + "/mfVouAfterTrack/getMultiBusList?cusNo=" + cusNo + "&busEntrance=" + busEntrance, "预警项目", function () {
        });
    }

};
//cusNoTmp临时变量，cusNo进到oneCallback就没有了
var cusNoTmp=cusNo;
//单字段编辑的保存回调方法。
function oneCallback(data,disVal) {
    var name = data[0].name;
    var value = data[0].value;
    var $_form = this;
    var formAction = $_form.attr("action");
    // 如果修改的是XXX表单，则进行XXX相关的业务处理。
    if (formAction == "") {

    } else if (formAction == webPath+"//mfCusCorpBaseInfo/updateAjaxByOne") {
        if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
            //如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
            $.post(webPath+"/mfCusCorpBaseInfo/getByIdAjax", {cusNo: cusNoTmp}, function(data) {
                if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
                    var proj=data.mfCusCorpBaseInfo.projSize;
                    var proJectName="";
                    if("1"==proj){
                        proJectName="大型企业";
                    }
                    if("2"==proj){
                        proJectName="中型企业";
                    }
                    if("3"==proj){
                        proJectName="小型企业";
                    }
                    if("4"==proj){
                        proJectName="微型企业";
                    }
                    if("5"==proj){
                        proJectName="其他";
                    }
                    $(".projSize").html(proJectName);
                }
            });
        }
        if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
            $("span[id="+name+"]").html(value);
        }
        //工商信息
    } else if (formAction == webPath+"//mfCusBusinessInfo/updateAjaxByOne") {
        if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
            //如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
            $.post(webPath+"/mfCusCorpBaseInfo/getByIdAjax", {cusNo: cusNoTmp}, function(data) {
                if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
                    var proj=data.mfCusCorpBaseInfo.projSize;
                    var proJectName="";
                    if("1"==proj){
                        proJectName="大型企业";
                    }
                    if("2"==proj){
                        proJectName="中型企业";
                    }
                    if("3"==proj){
                        proJectName="小型企业";
                    }
                    if("4"==proj){
                        proJectName="微型企业";
                    }
                    if("5"==proj){
                        proJectName="其他";
                    }
                    $(".projSize").html(proJectName);
                }
            });
        }
    } else if (formAction === webPath+"/mfCusBankAccManage/updateByOneAjax") {
        if (name=="accountNo"||name=="accountName"||name=="useType"||name=="bank"){
            getBankByCardNumbe(name,data[1].value);
        }
    } else if (formAction === webPath+"/mfCusProfitLossAnalyse/updateAjaxByOne") {
        if(name=="incomeTotal"||name=="variableCostTotal"||name=="fixFeePay"||name=="otherIncome"||name=="otherOftenFeePay"){//损益分析
            var incomeTotal = $('input[name=incomeTotal]', $_form).val();
            var variableCostTotal = $('input[name=variableCostTotal]', $_form).val();
            var grossProfit = CalcUtil.subtract(incomeTotal.replace(/,/g,''),variableCostTotal.replace(/,/g,''));
            //修改毛利
            $('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(grossProfit,2));
            var fixFeePay = $('input[name=fixFeePay]', $_form).val();
            var netProfit = CalcUtil.subtract(grossProfit,fixFeePay.replace(/,/g,''));
            //修改净利润
            $('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(netProfit,2));
            var otherIncome = $('input[name=otherIncome]', $_form).val();
            var profitSum = CalcUtil.add(netProfit,otherIncome.replace(/,/g,''));
            var otherOftenFeePay = $('input[name=otherOftenFeePay]', $_form).val();
            var disposableIncome = CalcUtil.subtract(profitSum,otherOftenFeePay.replace(/,/g,''));
            //修改可支配收入
            $('input[name=otherOftenFeePay]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(disposableIncome,2));
            BASE.oneRefreshTable("incomeTotal",incomeTotal);
            BASE.oneRefreshTable("variableCostTotal",variableCostTotal);
            BASE.oneRefreshTable("grossProfit",CalcUtil.formatMoney(grossProfit,2));
            BASE.oneRefreshTable("fixFeePay",fixFeePay);
            BASE.oneRefreshTable("netProfit",CalcUtil.formatMoney(netProfit,2));
            BASE.oneRefreshTable("otherIncome",otherIncome);
            BASE.oneRefreshTable("otherOftenFeePay",otherOftenFeePay);
            BASE.oneRefreshTable("disposableIncome",CalcUtil.formatMoney(disposableIncome,2));
        }
    }else if (formAction === webPath+"/mfCusSaleProduct/updateAjaxByOne") {
        if(name=="productNum"||name=="productPrice"||name=="cosRaw1"||name=="cosRaw2"||name=="cosRaw3"||name=="cosRaw4"||"cosLabour1"||"cosLabour2"){//销售产品
            //销售数量
            var productNum = $('input[name=productNum]', $_form).val();
            //销售单价
            var productPrice = $('input[name=productPrice]', $_form).val();
            //销售额
            var saleAmt=0.00;
            if(productNum!=null&&productNum!=''&&productNum!=undefined&&productPrice!=null&&productPrice!=undefined&&productPrice!=''){
                productNum =productNum.replace(",","");
                productPrice =productPrice.replace(",","");
                saleAmt = (productNum*1)*(productPrice*1);
                //销售额
                $('input[name=cosRaw1]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(saleAmt,2));
            }
            var profit =saleAmt;
            //原料成本一
            var cosRaw1 = $('input[name=cosRaw1]', $_form).val();
            if (cosRaw1!=null&&cosRaw1!=undefined&&cosRaw1!=''){
                cosRaw1 =cosRaw1.replace(",","");
                profit = profit*1-cosRaw1*1;
            }
            //原料成本二
            var cosRaw2 = $('input[name=cosRaw2]', $_form).val();
            if (cosRaw2!=null&&cosRaw2!=undefined&&cosRaw2!=''){
                cosRaw2 =cosRaw2.replace(",","");
                profit = profit*1-cosRaw2*1;
            }
            //原料成本三
            var cosRaw3 = $('input[name=cosRaw3]', $_form).val();
            if (cosRaw3!=null&&cosRaw3!=undefined&&cosRaw3!=''){
                cosRaw3 =cosRaw3.replace(",","");
                profit = profit*1-cosRaw3*1;
            }
            //原料成本四
            var cosRaw4 = $('input[name=cosRaw4]', $_form).val();
            if (cosRaw4!=null&&cosRaw4!=undefined&&cosRaw4!=''){
                cosRaw4 =cosRaw4.replace(",","");
                profit = profit*1-cosRaw4*1;
            }
            //劳务成本一
            var cosLabour1 = $('input[name=cosLabour1]', $_form).val();
            if (cosLabour1!=null&&cosLabour1!=undefined&&cosLabour1!=''){
                cosLabour1 =cosLabour1.replace(",","");
                profit = profit*1-cosLabour1*1;
            }
            //劳务成本二
            var cosLabour2 = $('input[name=cosLabour2]', $_form).val();
            if (cosLabour2!=null&&cosLabour2!=undefined&&cosLabour2!=undefined){
                cosLabour2 =cosLabour2.replace(",","");
                profit = profit*1-cosLabour2*1;
            }
            $('input[name=cosLabour2]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            var profitRate=0;
            if(saleAmt!=''&&profit!=''){
                if(saleAmt!=0.00){
                    profitRate =(profit*1)/(saleAmt*1)*100;
                    $('input[name=cosLabour2]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(profitRate,2));
                    BASE.oneRefreshTable("profitRate",CalcUtil.formatMoney(profitRate,2));
                }
            }

            BASE.oneRefreshTable("saleAmt",CalcUtil.formatMoney(saleAmt,2));
            BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));

        }
    } else if (formAction === webPath+"/mfCusPlantBreed/updateAjaxByOne") {
        if(name=="num"||name=="yield"||name=="price"||name=="seedPup"||name=="feiFeed"||name=="pesticideCorn"||name=="dripSoybean"||name=="laborCost"||name=="otherFee"||name=="premisesRental"||name=="wages"||name=="waterElec"||name=="fixedExp"){//种植养殖
            //亩数/出栏数
            var num =  $('input[name=num]', $_form).val();
            //亩产/单个重量
            var yield = $('input[name=yield]', $_form).val();
            //单价
            var price =  $('input[name=price]', $_form).val();
            //收入
            // var income=0.00;
            // if(num!=null&&num!=''&&num!=undefined&&yield!=null&&yield!=undefined&&yield!=''&&price!=null&&price!=undefined&&price!=''){
            //     num = num.replace(",","");
            //     yield = yield.replace(",","");
            //     price =price.replace(",","");
            //     income = (num*1)*(yield*1)*(price*1);
            //     $('input[name=price]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));
            //     BASE.oneRefreshTable("income",CalcUtil.formatMoney(income,2));
            // }
            var profit =income;
            var totalCost = 0.00;
            //种子/幼畜
            var seedPup = $('input[name=seedPup]', $_form).val();
            if (seedPup!=null&&seedPup!=undefined&&seedPup!=''){
                seedPup =seedPup.replace(",","");
                totalCost = totalCost*1+seedPup*1;
            }
            //化肥/饲料
            var feiFeed = $('input[name=feiFeed]', $_form).val();
            if (feiFeed!=null&&feiFeed!=undefined&&feiFeed!=''){
                feiFeed =feiFeed.replace(",","");
                totalCost = totalCost*1+feiFeed*1;
            }
            //农药/玉米
            var pesticideCorn =$('input[name=pesticideCorn]', $_form).val();
            if (pesticideCorn!=null&&pesticideCorn!=undefined&&pesticideCorn!=''){
                pesticideCorn =pesticideCorn.replace(",","");
                totalCost = totalCost*1+pesticideCorn*1;
            }
            //滴灌费用/豆粕
            var dripSoybean =$('input[name=dripSoybean]', $_form).val();
            if (dripSoybean!=null&&dripSoybean!=undefined&&dripSoybean!=''){
                dripSoybean =dripSoybean.replace(",","");
                totalCost = totalCost*1+dripSoybean*1;
            }
            //劳务成本
            var laborCost = $('input[name=laborCost]', $_form).val();
            if (laborCost!=null&&laborCost!=undefined&&laborCost!=''){
                laborCost =laborCost.replace(",","");
                totalCost = totalCost*1+laborCost*1;
            }
            //其他费用
            var otherFee = $('input[name=otherFee]', $_form).val();
            if (otherFee!=null&&otherFee!=undefined&&otherFee!=''){
                otherFee =otherFee.replace(",","");
                totalCost = totalCost*1+otherFee*1;
            }
            if(totalCost!=''){
                $('input[name=premisesRental]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(totalCost,2));
                BASE.oneRefreshTable("totalCost",CalcUtil.formatMoney(totalCost,2));
                profit = profit*1-totalCost*1;
            }
            $('input[name=premisesRental]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));
            //经营场地租金
            var otherTotal = 0.00;
            var premisesRental = $("input[name='premisesRental']").val();
            if (premisesRental!=null&&premisesRental!=undefined&&premisesRental!=''){
                premisesRental =premisesRental.replace(",","");
                otherTotal = otherTotal*1+premisesRental*1;
            }
            //人员工资
            var wages = $('input[name=wages]', $_form).val();
            if (wages!=null&&wages!=undefined&&wages!=''){
                wages =wages.replace(",","");
                otherTotal = otherTotal*1+wages*1;
            }
            //水、电、气费用
            var waterElec = $('input[name=waterElec]', $_form).val();
            if (waterElec!=null&&waterElec!=undefined&&waterElec!=''){
                waterElec =waterElec.replace(",","");
                otherTotal = otherTotal*1+waterElec*1;
            }
            //水、电、气费用
            var fixedExp = $('input[name=fixedExp]', $_form).val();
            if (fixedExp!=null&&fixedExp!=undefined&&fixedExp!=''){
                fixedExp =fixedExp.replace(",","");
                otherTotal = otherTotal*1+fixedExp*1;
            }
            if(otherTotal!=''){
                profit = profit*1-otherTotal*1;
            }
            $('input[name=fixedExp]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            BASE.oneRefreshTable("totalProfit",CalcUtil.formatMoney(profit,2));
        }
    }else if (formAction === webPath+"/mfCusBusService/updateAjaxByOne") {
        if(name=="inNum"||name=="inPrice"||name=="expNum"||name=="expPrice"||name=="busIncome"||name=="busExp"||name=="profit" || name=="profitRate" || name=="regDate"){//商业服务信息
            //收入数量
            var inNum = $('input[name=inNum]', $_form).val();
            //收入单价
            var inPrice = $('input[name=inPrice]', $_form).val();
            //收入合计
            var busIncome=0.00;
            if(inNum!=null&&inNum!=''&&inNum!=undefined&&inPrice!=null&&inPrice!=undefined&&inPrice!=''){
                inNum =inNum.replace(",","");
                inPrice =inPrice.replace(",","");
                busIncome = (inNum*1)*(inPrice*1);
                //收入合计
                $('input[name=inPrice]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(busIncome,2));
                BASE.oneRefreshTable("busIncome",CalcUtil.formatMoney(busIncome,2));
            }

            //支出数量
            var expNum = $('input[name=expNum]', $_form).val();
            //支出单价
            var expPrice = $('input[name=expPrice]', $_form).val();
            //支出合计
            var busExp=0.00;
            if(expNum!=null&&expNum!=''&&expNum!=undefined&&expPrice!=null&&expPrice!=undefined&&expPrice!=''){
                expNum =expNum.replace(",","");
                expPrice =expPrice.replace(",","");
                busExp = (expNum*1)*(expPrice*1);
                //销售额
                $('input[name=expPrice]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(busExp,2));
                BASE.oneRefreshTable("busExp",CalcUtil.formatMoney(busExp,2));
            }

            //业务毛利润
            var profit = 0;
            if(busExp!=null&&busExp!=''&&busExp!=undefined&&busIncome!=null&&busIncome!=undefined&&busIncome!=''){
                profit = (busIncome*1)-(busExp*1);
                //毛利润
                $('input[name=expPrice]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
                BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));
            }

            //业务毛利率
            var profitRate =0;
            if(busExp!=null&&busExp!=''&&busExp!=undefined&&busIncome!=null&&busIncome!=undefined&&busIncome!=''){
                profitRate = (busExp*1)/(busIncome*1)*100;
                //毛利率
                $('input[name=regDate]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profitRate,2));
                BASE.oneRefreshTable("profitRate",CalcUtil.formatMoney(profitRate,2));
            }
        }
    }else if (formAction === webPath+"/mfCusPersonFlowAssetsInfo/updateAjaxByOne") {

        if(name=="quantity"||name=="purchasePrice"){//个人资产存货
            //收入数量
            var quantity = $('input[name=quantity]', $_form).val();
            //收入单价
            var purchasePrice = $('input[name=purchasePrice]', $_form).val();
            var nowPrice =0.00;
            if(quantity!=null&&quantity!=''&&quantity!=undefined&&purchasePrice!=null&&purchasePrice!=undefined&&purchasePrice!=''){
                quantity = quantity.replace(",","");
                purchasePrice = purchasePrice.replace(",","");
                nowPrice = (quantity*1)*(purchasePrice*1);
                //毛利率
                // $('input[name=quantity]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(nowPrice,2));
                BASE.oneRefreshTable("nowPrice",CalcUtil.formatMoney(nowPrice,2));
            }
        }
        if(name == "nowPrice") { BASE.oneRefreshTable("nowPrice",disVal);}
        if(name == "assetsType"){BASE.oneRefreshTable("assetsTypeName",disVal);}
        if(name == "assetsName"){BASE.oneRefreshTable("assetsName",disVal);}//资产名称
    }else if (formAction === webPath+"/mfCusDebtArmourInfo/updateAjaxByOne") {//负债信息(铁甲网)
        if(name=="overAmt"||name=="loanAmt"||name=="pettyLoanAmt"||name=="creditCardDebtAmt"
            ||name=="externalGuaranteeAmt"||name=="otherDebtAmt"){
            var overAmt = $('input[name=overAmt]', $_form).val().replace(/,/g,'');
            var loanAmt = $('input[name=loanAmt]', $_form).val().replace(/,/g,'');
            var pettyLoanAmt = $('input[name=pettyLoanAmt]', $_form).val().replace(/,/g,'');
            var creditCardDebtAmt = $('input[name=creditCardDebtAmt]', $_form).val().replace(/,/g,'');
            var externalGuaranteeAmt = $('input[name=externalGuaranteeAmt]', $_form).val().replace(/,/g,'');
            var otherDebtAmt = $('input[name=otherDebtAmt]', $_form).val().replace(/,/g,'');
            //求和
            var sumAmt1 = CalcUtil.add(overAmt,loanAmt);
            var sumAmt2 = CalcUtil.add(pettyLoanAmt,creditCardDebtAmt);
            var sumAmt3 = CalcUtil.add(externalGuaranteeAmt,otherDebtAmt);
            var sumAmt11 = CalcUtil.add(sumAmt1,sumAmt2);
            var sumAmt = CalcUtil.add(sumAmt11,sumAmt3);
            $('input[name=otherDebtAmt]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(sumAmt,2));
        }
    } else if(formAction === webPath+"/mfCusFarmerIncExpe/updateAjaxByOne"){//农户收支情况(吉时与)
        if(name=="operateIncome"||name=="otherIncome"||name=="laborIncome"||name=="subsidyIncome"){

            var operateIncome = $("input[name=operateIncome]", $_form).val().replace(/,/g,'');//工资收入
            var otherIncome = $("input[name=otherIncome]", $_form).val().replace(/,/g,'');//其他收入
            var laborIncome = $("input[name=laborIncome]", $_form).val().replace(/,/g,'');//其他家庭成员收入
            var subsidyIncome = $("input[name=subsidyIncome]", $_form).val().replace(/,/g,'');//房屋租赁收入
            //
            var addTotal1 = CalcUtil.add(operateIncome,otherIncome);
            var addTotal2 = CalcUtil.add(laborIncome,subsidyIncome);
            //
            var totalIncome = CalcUtil.add(addTotal1,addTotal2);

            $('input[name=plantCost]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(totalIncome,2));

            var totalOutgo  = $('input[name=medicaOut]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html().replace(/,/g,'');
            var income =	CalcUtil.subtract(totalIncome,totalOutgo);
            $('input[name=otherCost]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));

        }else if(name=="plantCost"||name=="feedCost"||name=="medicaOut"||name=="consumeOut"||name=="liveCost"||name=="otherCost"){
            var plantCost = $("input[name=plantCost]", $_form).val().replace(/,/g,'');//房租支出
            var feedCost = $("input[name=feedCost]", $_form).val().replace(/,/g,'');//水、电、气费支出
            var medicaOut = $("input[name=medicaOut]", $_form).val().replace(/,/g,'');//医疗支出
            var consumeOut = $("input[name=consumeOut]", $_form).val().replace(/,/g,'');//教育支出

            var liveCost = $("input[name=liveCost]", $_form).val().replace(/,/g,'');//生活费支出
            var otherCost = $("input[name=otherCost]", $_form).val().replace(/,/g,'');//其他支出

            var addTotal1 = CalcUtil.add(plantCost,feedCost);
            var addTotal2 = CalcUtil.add(medicaOut, consumeOut);
            var addTotal3 = CalcUtil.add(liveCost, otherCost);
            //求和
            var addTotal4 = CalcUtil.add(addTotal1,addTotal2);
            var totalOutgo = CalcUtil.add(addTotal3,addTotal4);

            $('input[name=medicaOut]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(totalOutgo,2));
            var  totalIncome  =	$('input[name=plantCost]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html().replace(/,/g,'');

            var income =	CalcUtil.subtract(totalIncome,totalOutgo);
            $('input[name=otherCost]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));

        }
    }else if(formAction === webPath+"/mfCusHighInfo/updateByOneAjax")//高管信息
    {
        if (name == "sex" || name =="idNum"|| name == "brithday")
        {
            var idnum = $('input[name=idNum]', $_form).val();
            BASE.oneRefreshTable("idNum",idnum);
        }
        BASE.oneRefreshTable("highName",$('input[name=highName]', $_form).val());
        BASE.oneRefreshTable("highCusType",$('input[name=highCusType]', $_form).val());
        BASE.oneRefreshTable("education",$('input[name=education]', $_form).val());
    }else if(formAction === webPath+"/mfCusPersonCorp/updateAjaxByOne")//个人名下企业
    {
        var creditCode = $('input[name=idNum]',$_form).val();
        BASE.oneRefreshTable("idNum",creditCode);
        BASE.oneRefreshTable("registeredCapital",$('input[name=registeredCapital]', $_form).val());
        if(name=="corpNature"){
            refreshCusPersonCorpTable(name,data[1].value);
        }
    } else {
        /**
         *  基本不会重复的，或者基本完全通用的，可以不加条件来处理
         *  有重复的，需要把两个都独立出去。
         */
        //mf_cus_customer mf_cus_register mf_cus_corp_base_info
        if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
            $("span[id="+name+"]", $_form).html(value);
        }
        if(name=="debtType"||name=="useType"||name=="highCusType"||name=="education"||name=="highCusType"||name=="education"||name=="sellArea"||name=="sellWayclass"||name=="isLegal"||"relative"==name||name=="saleArea"||name=="goodsRule"||name=="changeType"||name=="corpNature"||name=="registeredType"||name=="idCardInfo"||name=="regBookInfo"||name=="conEnvironment"||name=="conPaymentt"||name=="casePeopleType"||name=="dishonestPersonType"||name=="isJudg"||name=="assetsType"||name=="agrOrgType"||name=="corpKind"||name=="duty"||name=="expType"||name=="expName"||"projectType"){
            BASE.oneRefreshTable(name,disVal);
        }else if(name=="careaCity"){
            var careaProvice = $("input[name=careaProvice]", $_form).val();
            var cusNo = $("input[name=cusNo]", $_form).val();
            $.ajax({
                url : webPath+"/mfCusCorpBaseInfo/updateCareaAjax",
                data : {
                    ajaxData : careaProvice ,cusNo:cusNoTmp
                },
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                }
            });
        }else if(name=="initialCapital"||name=="durateDisposableIncomeTotal"||name=="durateOutNotOftenPay"||name=="assetDepreciate"||name=="durateAssetInject"){//权益分析
            var initialCapital = $('input[name=initialCapital]').val();//启动资本
            var durateDisposableIncomeTotal = $('input[name=durateDisposableIncomeTotal]', $_form).val();//期间可支配收入合计
            var durateOutNotOftenPay = $('input[name=durateOutNotOftenPay]', $_form).val();//期间表外非经常性支出
            var assetDepreciate = $('input[name=assetDepreciate]', $_form).val();//资产折旧
            var durateAssetInject = $('input[name=durateAssetInject]', $_form).val();//期间注资

            var incomeValue = CalcUtil.add(initialCapital.replace(/,/g,''),durateDisposableIncomeTotal.replace(/,/g,''));
            var incomeTotalValue = CalcUtil.add(incomeValue,durateAssetInject.replace(/,/g,''));
            var payValue = CalcUtil.add(durateOutNotOftenPay.replace(/,/g,''),assetDepreciate.replace(/,/g,''));
            var ownerAssetValue = CalcUtil.subtract(incomeTotalValue,payValue);
            //修改所有者权益
            $('input[name=durateAssetInject]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(ownerAssetValue,2));
        }else if(name=="ifGroup"){
            updateGroupName();
        }else{
            if ($_form.attr("id") === 'listForm') {
                BASE.oneRefreshTable(name,value);
            }
        }
    }

}
function refreshCusPersonCorpTable(name,corpId){
    $.ajax({
        url:webPath+"/mfCusPersonCorp/getByIdAjax",
        data:{corpId:corpId},
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.flag == "success") {
                if (name == "corpNature") {
                    refreshTable(name, data.mfCusPersonCorp);
                    var index = $("th[name=corpNature]").index();
                    $(".listshow-tr").prev().find("td").eq(index).html(data.corpNature);

                }
            }
        }
    });
}
function getBankByCardNumbe(name,bankId) {
    $.ajax({
        url:webPath+"/mfCusBankAccManage/getByIdForOneAjax",
        data:{id:bankId},
        type:'post',
        dataType:'json',
        success:function(data){
            if(data.flag == "success"){
                //$("#MfCusBankAccManageAction").html(data.htmlStr);
                if(name=="accountNo"){
                    refreshTable(name,data.mfCusBankAccManage);
                    var index=	$("th[name=bankAuthSts]").index();
                    $(".listshow-tr").prev().find("td").eq(index).html(data.bankAuthSts);
                }else if (name=="useType"){
                    var index=	$("th[name=useType]").index();
                    $(".listshow-tr").prev().find("td").eq(index).html(data.userType);
                }else if(name=="accountName"){
                    refreshTable(name,data.mfCusBankAccManage);
                    var index=	$("th[name=accountName]").index();
                    $(".listshow-tr").prev().find("td").eq(index).html(data.accountName);
                }else if(name=="bank"){
                    refreshTable(name,data.mfCusBankAccManage);
                    var index=	$("th[name=bank]").index();
                    $(".listshow-tr").prev().find("td").eq(index).html(data.bank);
                }
                $("input[name=bank]").val(data.mfCusBankAccManage.bank);
                $("input[name=bankNumbei]").val(data.mfCusBankAccManage.bankNumbei);
                if($("input[name=bank]").is(":visible")){
                    $("input[name=bank]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bank);
                };
                if($("input[name=bankNumbei]").is(":visible")){
                    $("input[name=bankNumbei]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bankNumbei);
                }
            }else{
                $("input[name=bank]").val("");
                $("input[name=bankNumbei]").val("");
            }
        },error:function(){
            window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
        }
    });
};
//
function refreshTable(name,data){
    var index;
    if(name=="accountNo"){
        index = $("th[name=accountNo]").index();
        $(".listshow-tr").prev().find("td").eq(index).find('a').html(data.accountNo);
        BASE.oneRefreshTable("bank",data.bank);
        dealBankNo();
    }else if (name=="useType"){
        var useType = 	$("select[name=useType]").parent().parent().find("div[class=fieldShow]").html();
        BASE.oneRefreshTable(name,useType);
    }else if (name=="accountName"){
        var accountName = 	$("input[name=accountName]").parent().parent().find("div[class=fieldShow]").html();
        BASE.oneRefreshTable(name,accountName);
    }else if (name=="bank"){
        var bank = 	$("input[name=bank]").parent().parent().find("div[class=fieldShow]").html();
        BASE.oneRefreshTable(name,bank);
    }
}
//联网核查跳转到增值服务页面
function addService(){
    LoadingAnimate.start();
    $.ajax({
        url :webPath+"/mfCusCustomer/toAddServicePage",
        type:"post",
        data:{"cusNo":cusNo,"showType":"0"},
        success:function(data){
            LoadingAnimate.stop();
            if(data.flag=="success"){
                var url = data.url;
                var customer = data.mfCusCustomer;
                var data =customer.headImg;
                if (customer.ifUploadHead != "1") {
                    data = "themes/factor/images/" + customer.headImg;
                }
                data = encodeURIComponent(encodeURIComponent(data));
                headImgShowSrc = basePath+webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
                if(customer.cusType=="202"){//个人客户
                    /*var headImg=customer.headImg;
                    var ifUploadHead = customer.ifUploadHead;
                    var data = headImg;
                    if(ifUploadHead!="1"){
                        data = "themes/factor/images/"+headImg;
                    }
                    data = encodeURIComponent(encodeURIComponent(data));
                    var ipandport=window.document.location.href;

                    var port=window.location.href;
                    var src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=user2.jpg";*/
                    url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
                }else{
                    url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType+"&contactsName="+customer.contactsName+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));

                }
                window.location.href=url;
            }else{
                LoadingAnimate.stop();
                alert(data.msg, 0);
            }
        }
    });
}

//风险拦截
function cusRisk(){
    if(!(dialog.get('riskInfoDialog') == null)){
        dialog.get('riskInfoDialog').close();
    }
    top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
};

//打开客户基本认证报告页面
function openCustomerCerReport(){
    if(comReportFlag =="3"){
        //查询是否同盾的报告
        var reportData = tdReport("0");
        if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
            if(reportData.errorCode == "00000"){
                alert(reportData.errorMsg, 0);
                return ;
            }
            var TD_data = reportData.data;
            TD_data = $.parseJSON(TD_data);
            $.showTDReport(TD_data);
        }
    }
    else{
        top.updateFlag = false;
        top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo+'&appId='+appId, '认证报告',function(){},"75","90");
    }
}

//个人负债表单更新开始日期和结束日期，联动计算期限
function getLoanTerm(datas){
    var beginDate =  $("input[name=beginDate]").val().replace(/\-/g, "");
    var endDate = $("input[name=endDate]").val().replace(/\-/g, "");
    var key = $(".changeval .inputText").attr("name");
    if(key=="beginDate"){
        beginDate = datas.replace(/\-/g, "");
    }else if(key =="endDate"){
        endDate = datas.replace(/\-/g, "");
    }
    if(beginDate!="" && endDate!=""){
        UtilDwr.getMonthsAndDays(beginDate,endDate,function(data){
            var loanTerm="";
            if(data[0]>0){
                loanTerm=loanTerm+data[0]+"月";
            }
            if(data[1]>0){
                loanTerm=loanTerm+data[1]+"天";
            }
            $(".loanTerm").text(loanTerm);
        });

    }
    return true;
}
// 更新是否集团客户
function updateGroupName(){
    if($("select[name='ifGroup']").size() > 0){
        var value = $("select[name='ifGroup']").val();
        if(value == "1"){
            $("input[name='groupName']").parent().parent().parent().show();
        }else{
            $("input[name='groupName']").parent().parent().parent().hide();
        }
    }
};
//同盾认证报告 ,submitFlag-重新提交查询 0-否 1-是
var tdReport = function (submitFlag){
    var resultMap = null;
    $.ajax({
        type : "post",
        url : webPath+"/mfTongDunReportAuth/getTDReportAjax?cusNo="+cusNo+"&appId="+appId+"&submitFlag="+submitFlag,
        async : false,
        success : function(data){
            resultMap = data;
        },
        error:function(){
            alert("认证报告生成失败",0);
        }
    });
    return resultMap;
};

//重新认证同盾的报告
var reportApply = function (){
    alert(top.getMessage("CONFIRM_OPERATION","认证"),2,function(){
        var reportData = tdReport("1");
        if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
            if(reportData.errorCode == "00000"){
                alert(reportData.errorMsg, 0);
                return;
            }
            var TD_data = reportData.data;
            TD_data = $.parseJSON(TD_data);
            $.showTDReport(TD_data);
        }
    });
};

//阳光银行放款渠道是否允许自助放款设置
var channelSelfPutoutSet = function(){
    top.window.openBigForm(webPath+'/mfCusSelfPutoutConfigApply/input?cusNo='+cusNo, '自助放款设置',function(){

    });
};

//保证金释放
var releaseCashDepositApp = function(){
    top.openBigForm(webPath+'/mfBusReleaseCashDeposit/getInnerListPage?cusNo=' + cusNo, '保证金释放', function(){});
};

//开户
var mfBusOpenAccountApp = function(){
//	top.openBigForm(webPath+'/mfBusOpenAccount/getInnerListPage?cusNo=' + cusNo, '开户', function(){});

    var validateUrl = webPath + "/mfBusOpenAccount/inputValidateAjax?cusNo=" + cusNo;
    $.ajax({
        url : validateUrl,
        success : function(data){
            if (data.flag == "success") {
                top.openBigForm(webPath+'/mfBusOpenAccount/innerInputNew?cusNo=' + cusNo, '开户', function(){});
            }else{
                window.top.alert(data.msg, 0);
            }
        },error : function(data){
            alert(top.getMessage("ERROR_SERVER"),0);
        }
    });
};

//销户
var mfBusCloseAccountApp = function(){
//	top.openBigForm(webPath+'/mfBusCloseAccount/getInnerListPage?cusNo=' + cusNo, '销户', function(){});

    var validateUrl = webPath + "/mfBusCloseAccount/inputValidateAjax?cusNo=" + cusNo;
    $.ajax({
        url : validateUrl,
        success : function(data){
            if (data.flag == "success") {
                top.openBigForm(webPath+'/mfBusCloseAccount/innerInputNew?cusNo=' + cusNo, '销户', function(){});
            }else{
                window.top.alert(data.msg, 0);
            }
        },error : function(data){
            alert(top.getMessage("ERROR_SERVER"),0);
        }
    });
};

//赎证
var redeemCertificateApp = function(){
    top.openBigForm(webPath+'/mfBusRedeemCertificate/getInnerListPage?cusNo=' + cusNo, '赎证', function(){});
}


// 判断修改客户信息是否走审批
var ifApprovalCusInfo = function(dataParam,identification){
    var flag = false;
    var effectFlag;
    if(effectFlag == "2"){// 客户状态为销户，不允许修改数据
        flag = true;
        alert("客户已被销户，不允许修改客户信息");
        return flag;
    }
    jQuery.ajax({
        url: webPath + "/mfCusKeyInfoFields/ifKeyField",
        data:{ajaxData:dataParam,identification:identification},
        type:"POST",
        dataType:"json",
        async:false,//关键
        success:function(data){
            if (data.flag == "error") {
                flag = true;
                alert("保存失败");
            } else {
                if (data.ifKeyField == "1") {
                    dataParam = data.updeteJson;
                    ifApprovl = data.ifApprovl
                    if (ifApprovl == 0) {
                        ifApprovlRecordInfo(data.mfCusInfoChange)
                        flag = false;
                    } else {
                        flag = true;
                        top.openBigForm(webPath + "/mfCusInfoChange/input?ajaxData=" + encodeURIComponent(data.mfCusInfoChange), "客户信息修改申请", function () {
                        })
                    }
                } else {
                    flag = false;
                }
            }
        },error:function(data){
            flag = true;
            alert("保存失败");
        }
    });
    return flag;

}


//配置字段但不走审批
var ifApprovlRecordInfo = function(mfCusInfoChange){
    jQuery.ajax({
        url: webPath + "/mfCusInfoChange/ifApprovlRecordInfo",
        data: {ajaxData: mfCusInfoChange},
        type: "POST",
        dataType: "json",
        async: false,//关键
        success: function (data) {
            if (data.flag == "success") {//单字段修改成功后，改变信息变更记录按钮颜色
                $("#cusInfoChangeRecordQuery").removeClass("btn-lightgray");// 去掉灰色样式
                $("#cusInfoChangeRecordQuery").addClass("btn-dodgerblue");// 添加蓝色
            }
        }, error: function () {
            LoadingAnimate.stop();
        }
    });
}


//获取用户信息变更记录
var openCusInfoChangeRecord = function(){
    top.window.openBigForm(webPath+"/mfCusInfoChange/getListPage?cusNo="+cusNo,"客户信息变更记录",function(){
    });
};
//刷新关联关系
function refreshRelation(){
    $.ajax({
        url:webPath+'/mfCusRelation/getListPageAjax',
        type:'post',
        data:{cusNo:cusNo},
        dataType:"json",
        async: false,
        success:function(data){
            if(data.flag=="success"){
            }else{
                relation=true;
            }
        },error:function(){
            alert(top.getMessage("ERROR_REQUEST_URL", ""));
            LoadingAnimate.stop();
        }
    });
};
//获取用户要件信息
/*var win = null;
var openApplyDataUpload = function () {
    if (win && win.open && !win.closed){
        win.focus();
    }else{
        win = window.open(webPath + "/docManage/openNewDoc.action?cusNo="+cusNo+"&appId="+appId+"&isCusDoc="+isCusDoc);
    }

}*/
//一键展开/收起
function expansion(){
    $(".formAdd-btn.pull-right").each(function(){
        $(this).click();
    });
    if(expansionFlag==0){
        $("#expansion").html('一键展开');
        expansionFlag =1;
        return;
    }else if(expansionFlag==1){
        $("#expansion").html('一键收起');
        expansionFlag =0;
    }
}
//如果已经有关联关系就不再刷新
function ifRefreshRelation(){
    var flag = "";
    if(typeof(relation)!="undefined"&&relation==false){
        $.ajax({
            url:webPath+'/mfCusRelation/getListByCusAjax',
            type:'post',
            data:{cusNo:cusNo},
            async: false,
            dataType:"json",
            success:function(data){
                flag= data.flag;
                if(flag=="success"){
                    refreshRelation()
                }else{
                    relation=true;
                }
            },error:function(){
                LoadingAnimate.stop();
            }
        });
    }}
//查看三方记录信息
function getRiskReport(){
    top.window.openBigForm(webPath+"/mfThirdServiceRecord/riskReport?cusNo="+cusNo+"&query=query&appId="+appId,'风控查询', initRiskReportButton);
};
// 三方风险信息查询
function getRiskManagement(){
    top.window.openBigForm(webPath+"/apiReturnRecord/openRiskVerification?cusNo="+cusNo,'关联人联网查询', TongduButton());
};
//初始化同盾核查按钮
function TongduButton(){
    if($('#riskQuerys').length>0){//检查是否存在预审批报告按钮
        $.ajax({
            url:webPath+'/mfThirdServiceRecord/getListByCusNoAjax',
            type:'post',
            data:{cusNo:cusNo},
            async: false,
            dataType:"json",
            success:function(data){
                if(data.flag=="success"){
                    $("#riskQuerys").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#riskQuerys").addClass("btn-dodgerblue");// 添加蓝色
                }else{
                    $("#riskQuerys").removeClass("btn-dodgerblue");// 去掉蓝色
                    $("#riskQuerys").addClass("btn-lightgray");// 添加灰色
                }
            },error:function(){
                LoadingAnimate.stop();
            }
        });
    }
};

//初始化三方查询按钮
function initRiskReportButton(){
    if($('#riskQuery').length>0){//检查是否存在预审批报告按钮
        $.ajax({
            url:webPath+'/mfThirdServiceRecord/getListByCusNoAjax',
            type:'post',
            data:{cusNo:cusNo},
            async: false,
            dataType:"json",
            success:function(data){
                if(data.flag=="success"){
                    $("#riskQuery").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#riskQuery").addClass("btn-dodgerblue");// 添加蓝色
                }else{
                    $("#riskQuery").removeClass("btn-dodgerblue");// 去掉蓝色
                    $("#riskQuery").addClass("btn-lightgray");// 添加灰色
                }
            },error:function(){
                LoadingAnimate.stop();
            }
        });
    }
};

//--------------------对外担保信息详情的校验---------------------------
function   GaiGuarantee (obj){
    if(parseInt($("input[name='guaranteeAmt']").val().replace(/,/g,'')) < parseInt($(obj).val().replace(/,/g,''))){
        window.top.alert("担保余额不能大于担保总额",0);
        return false;
    }
};

function   GaiGuaranteeAmt (obj){
    if(parseInt($(obj).val().replace(/,/g,'')) < parseInt($("input[name='guaranteeBal']").val().replace(/,/g,''))){
        window.top.alert("担保总额不能小于担保余额",0);
        return false;
    }
};

function   GaiLoantee (obj){
    if(parseInt($("input[name='loanAmt']").val().replace(/,/g,'')) < parseInt($(obj).val().replace(/,/g,''))){
        window.top.alert("贷款余额不能大于贷款总额",0);
        return false;
    }
};

function   GaiLoanteeAmt (obj){
    if(parseInt($(obj).val().replace(/,/g,'')) < parseInt($("input[name='loanBal']").val().replace(/,/g,''))){
        window.top.alert("贷款总额不能小于贷款余额",0);
        return false;
    }
};
//--------------------对外担保信息详情的校验---------------------------

//校验身份证号级联与证件进行判断
function     JiaoYan(obj){
    if(parseInt($("select[name='idType']").val())!=0){
        return  true;
    }
    var val = $(obj).val();
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if( !pattern.test(val)){
        window.top.alert("请输入正确的身份证号",0);
        return false;
    };
};

//校验身份证号级联与证件进行判断
function     JiaoYanIdNum(obj){
    var val = $(obj).val();
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if( !pattern.test(val)){
        window.top.alert("请输入正确的身份证号",0);
        return  false;
    };
};



//获取苗种厂家银行账号
function   getCwBankCusAccMangeAjax (){
    var  userType = "03";
    $("input[name=seedFactory]").popupList({
        searchOn: true, //启用搜索
        multiple: false, //false单选，true多选，默认多选
        ajaxUrl : webPath+"/cwCusBankAccManage/getCwBankCusAccMangeAjax?useType="+userType, // 请求数据URL
        valueElem:"input[name='seedFactory']",//真实值选择器
        title: "银行账户",//标题
        changeCallback:function(elem){//回调方法
            var tmpData = elem.data("selectData");
            $("input[name=popsseedFactory]").val(tmpData.accountName);
            //$("input[name=collectAccName]").val(tmpData.accountName);
            //$("input[name=collectBank]").val(tmpData.bank);
        },
        tablehead:{//列表显示列配置
            "accountNo":"账号",
            "accountName":"账号名称",
            "bank":"开户行"
        },
        returnData:{//返回值配置
            disName:"accountName",//显示值
            value:"accountName"//真实值
        }
    });
    $("input[name=seedFactory]").next().click();
};

//获取冷藏厂家银行账号
function getReCwBankCusAccMangeAjax() {
    var userType = "02";
    $("input[name=recycleFirm]").popupList({
        searchOn: true, //启用搜索
        multiple: false, //false单选，true多选，默认多选
        ajaxUrl: webPath + "/cwCusBankAccManage/getCwBankCusAccMangeAjax?useType=" + userType, // 请求数据URL
        valueElem: "input[name='recycleFirm']",//真实值选择器
        title: "银行账户",//标题
        changeCallback: function (elem) {//回调方法
            var tmpData = elem.data("selectData");
            $("input[name=popsrecycleFirm]").val(tmpData.accountName);
            //$("input[name=collectAccName]").val(tmpData.accountName);
            //$("input[name=collectBank]").val(tmpData.bank);
        },
        tablehead: {//列表显示列配置
            "accountNo": "账号",
            "accountName": "账号名称",
            "bank": "开户行"
        },
        returnData: {//返回值配置
            disName: "accountName",//显示值
            value: "accountName"//真实值
        }
    });
    $("input[name=recycleFirm]").next().click();
};

//客户自动分类
function cusAutoClassify() {
    $.ajax({
        url :webPath+"/mfCusClassify/cusAutoClassify",
        type:"post",
        data:{"cusNo":cusNo},
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag=="success"){
                if (data.rankType == "1") {
                    $(".cus-tag").addClass("btn-danger");
                    $(".cus-tag").removeClass("btn-forestgreen");
                    $(".cus-tag").removeClass("btn-lightgray");
                    $(".cus-tag").removeClass("btn-dodgerblue");
                } else if (data.rankType == "2") {
                    $(".cus-tag").addClass("btn-forestgreen");
                    $(".cus-tag").removeClass("btn-danger");
                    $(".cus-tag").removeClass("btn-lightgray");
                    $(".cus-tag").removeClass("btn-dodgerblue");
                } else if(data.rankType == "3"){
                    $(".cus-tag").addClass("btn-dodgerblue");
                    $(".cus-tag").removeClass("btn-danger");
                    $(".cus-tag").removeClass("btn-forestgreen");
                    $(".cus-tag").removeClass("btn-lightgray");
                }else {
                    $(".cus-tag").addClass("btn-lightgray");
                    $(".cus-tag").removeClass("btn-danger");
                    $(".cus-tag").removeClass("btn-forestgreen");
                    $(".cus-tag").removeClass("btn-dodgerblue");
                }
                $("#cusNameRate-span").text(data.rankTypeName);
                window.top.alert("系统根据规则已将客户归类为'"+data.rankTypeName+"'!",3);
            }else{
                window.top.alert(data.msg, 0);
                LoadingAnimate.stop();
            }
        }
    });
};

//更换信息来源的时候清空推荐信息
function emptyRecommender() {
    $("input[name=recommenderName]").val("");
    $("input[name=recommenderPhone]").val("");
    $("input[name=recommenderEmployer]").val("");
}


//更换信息来源的时候清空推荐信息
function emptyRecommenderDetail() {
    $("div[class='fieldShow recommenderName']").text("");
}

function backUp() {
    $('.mCustomScrollbar').mCustomScrollbar("scrollTo", "top");
}


//社会信用代码
function creditCodeCheck(obj){
    var idNo = trim(obj.value);
    var title = obj.title;
    var msg = '';
    idNo = idNo.toUpperCase();
    if(idNo.length!=18){
        //myAlert("社会信用代码必须为18位！");
        //alert("社会信用代码必须为18位！",0);
        msg = "社会信用代码必须为18位！";
        alert(msg, 0);
        return false;
    }

    var sum = 0;
    var arr=new Array();
    arr=idNo.split("");
    //myAlert(arr[0]);
    for(var i=0;i<arr.length-1;i++){
        var c=arr[i];
        if(typeof(c)=="NaN"||typeof(c)=="undefined"){
            //myAlert("请输入正确的社会信用代码！");
            //alert("请输入正确的社会信用代码！",0);
            msg = "请输入正确的社会信用代码！";
            alert(msg, 0);
            return false;
        }
        var a=getMapC(c);
        if(typeof(a)=="NaN"||typeof(a)=="undefined"){
            //myAlert("请输入正确的社会信用代码！");
            msg = "请输入正确的社会信用代码！";
            alert(msg, 0);
            return false;
        }
        var b=getMapW((i+1).toString());
        if(typeof(b)=="NaN"||typeof(b)=="undefined"){
            //myAlert("请输入正确的社会信用代码！");
            msg = "请输入正确的社会信用代码！";
            alert(msg, 0);
            return false;
        }
        sum += a * b;
        if(typeof(sum)=="NaN"||typeof(sum)=="undefined"){
            //myAlert("请输入正确的社会信用代码！");
            msg = "请输入正确的社会信用代码！";
            alert(msg, 0);
            return false;
        }
    }
    var mod=sum%31;
    if(mod == 0)
    {
        var res = "0";
    }
    else {
        var res = getMapR((31-mod).toString());
    }
    if(typeof(res)=="NaN"||typeof(res)=="undefined"){
        //myAlert("请输入正确的社会信用代码！");
        msg = "请输入正确的社会信用代码！";
        alert(msg, 0);
        return false;
    }
    if(arr[arr.length-1]!=(res.toString())){
        //myAlert("请输入正确的社会信用代码！");
        msg = "请输入正确的社会信用代码！";
        alert(msg, 0);
        return false;
    }
    return true;
}
//邮箱格式校验
function emailCheck(obj){
    var email = $(obj).val();
    var myreg = /^([a-zA-Z0-9_-]{1,16})@([a-zA-Z0-9]{1,9})(\.[a-zA-Z0-9]{1,9}){0,3}(\.(?:com|net|org|edu|gov|mil|cn|us)){1,4}$/;
    if(!myreg.test(email))
    {
        alert('提示\n\n请输入有效的邮箱！',0);
        return false;
    }
}
//根据证件类型校验身份证号进行判断（证件类型为身份证时）
function     idJiaoYan(obj){
    if($("select[name='legalIdType']").val() != undefined)
    {
        var idtype = $("select[name='legalIdType']").val();
        if(idtype != "0")
        {
            return true;
        }
    }else
    {
        var obj0 = ($("p[class='font-small z']")).parent().find($("span[class='font-smallup bold']"));
        var obj1 = obj0.find($("div[class='fieldShow']"));
        var idtype = obj1.html();
        if(idtype != "身份证")
        {
            return true;
        }
    }
    var val = $(obj).val();
    var pattern = /(^[1-9]\d{5}(18|19|2([0-9]))\d{2}(0[0-9]|10|11|12)([0-2][1-9]|30|31)\d{3}[0-9Xx]$)/;
    if( !pattern.test(val)){
        window.top.alert("请输入正确的身份证号",0);
        return false;
    };
};

// 根据证件类型校验身份证号进行判断（证件类型为身份证时）
function idJiaoYan2(obj) {
    var val = $(obj).val();
    var pattern = /(^[1-9]\d{5}(18|19|2([0-9]))\d{2}(0[0-9]|10|11|12)([0-2][1-9]|30|31)\d{3}[0-9Xx]$)/;
    if (!pattern.test(val)) {
        window.top.alert("请输入正确的身份证号", 0);
        return false;
    }
}

function cusCreditQueryApp(){
    top.openBigForm(webPath+"/mfCreditQueryApp/getListPage?cusNo="+cusNo,"征信查询申请列表", function(){

    });
}

function getSubjectDetailPage(obj,url){
    top.openBigForm(webPath+url,"重点关注科目余额详情", function(){

    });



}
;

