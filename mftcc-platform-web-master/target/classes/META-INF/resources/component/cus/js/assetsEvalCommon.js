
//房屋区域回调
//
function selectHouseAreaCallBack(areaInfo){
    $("#houseEvalDiv input[name=areaCode]").val(areaInfo.disNo);
    $("#houseEvalDiv input[name=areaCodeName]").val(areaInfo.disName);
};

//选择房屋类型（物业类型）
function selectPropertypeDialog(callback, ele){
    var areaCode = $("#houseEvalDiv input[name='areaCode']").val();
    if(areaCode != '' && typeof (areaCode) != 'undefined'){
        dialog({
            id:"areaDialog",
            title:'物业分类',
            url: webPath+'/mfHouseEval/getPropertyTypeList?AreaCode='+areaCode,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择房屋所在区域！",0);
    }

};

//房屋类型回掉
function selectPropertypeCallBack(areaInfo){
    $("#houseEvalDiv input[name=thirdPropertyName]").val(areaInfo.disName);
    $("#houseEvalDiv input[name=thirdProperty]").val(areaInfo.disNo);

    $.ajax({
        url : webPath+"/mfHouseEval/getSecondProperty?areaNo="+areaInfo.disNo,
        type : "post",
        dataType : "json",
        success : function(data) {
            if(data.flag == 'success'){
                $("#houseEvalDiv input[name=secondPropertyName]").val(data.areaName);
                $("#houseEvalDiv input[name=secondProperty]").val(data.areaNo);
            }
        },
        error : function() {
        }
    });
};


//选择小区
function selectcommunityDialog(callback, ele){
    var secondProperty = $("#houseEvalDiv input[name='secondProperty']").val();
    if(secondProperty != '' && typeof (secondProperty) != 'undefined'){
        var areaCode = $("#houseEvalDiv input[name='areaCode']").val();
        var addressInput = $("#houseEvalDiv input[name='addressInput']").val();
        var address = $("#houseEvalDiv input[name='address']").val();
        if((addressInput== '' || typeof (addressInput)== 'undefined') && (address=='' || typeof (address)== 'undefined')){
            alert("请先输入小区名称或产证地址！",0);
            return ;
        }
        if(addressInput != '' && typeof (addressInput)!= 'undefined'){
            address = addressInput;
        }
        dialog({
            id:"areaDialog",
            title:'小区',
            url: webPath+'/mfHouseEval/getCommuntyList?PropertyType='+secondProperty+'&Address='+address+'&AreaCode='+areaCode,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择房产类型！",0);
    }
};

//小区回掉
function selectcommunityCallBack(areaInfo){
    $("#houseEvalDiv input[name=communityName]").val(areaInfo.disName);
    $("#houseEvalDiv input[name=communityId]").val(areaInfo.disNo);
    $("#houseEvalDiv input[name=areaCode]").val(areaInfo.areaCode);
};

//选择楼栋
function selectstorieDialog(callback, ele){

    var communityId = $("#houseEvalDiv input[name='communityId']").val();
    if(communityId != '' && typeof (communityId) != 'undefined'){
        var areaCode = $("#houseEvalDiv input[name='areaCode']").val();
        var secondProperty = $("#houseEvalDiv input[name='secondProperty']").val();
        dialog({
            id:"areaDialog",
            title:'楼栋',
            url: webPath+'/mfHouseEval/getStorieList?PropertyType='+secondProperty+'&ProjectId='+communityId+'&AreaCode='+areaCode,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择小区！",0);
    }
};

//楼栋回掉
function selectstoriedCallBack(areaInfo){
    $("#houseEvalDiv input[name=storiedName]").val(areaInfo.disName);
    $("#houseEvalDiv input[name=storiedId]").val(areaInfo.disNo);
    $("#houseEvalDiv input[name=complateDate]").val(areaInfo.complateDate);
};



//选择房间
function selectsingleStoriedDialog(callback, ele){

    var storiedId = $("#houseEvalDiv input[name='storiedId']").val();
    if(storiedId != '' && typeof (storiedId) != 'undefined'){
        var areaCode = $("#houseEvalDiv input[name='areaCode']").val();
        var thirdProperty = $("#houseEvalDiv input[name='thirdProperty']").val();
        var communityId = $("#houseEvalDiv input[name='communityId']").val();
        dialog({
            id:"areaDialog",
            title:'房间',
            url: webPath+'/mfHouseEval/getSingleStorieList?ThirdProperty='+thirdProperty+'&ProjectId='+communityId+'&AreaCode='+areaCode+'&FloorId='+storiedId,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择楼栋！",0);
    }
};

//房间回掉
function selectsingleStoriedCallBack(areaInfo){
    $("#houseEvalDiv input[name=singleStoried]").val(areaInfo.disName);
    $("#houseEvalDiv input[name=singleStoriedId]").val(areaInfo.disNo);

    $("#houseEvalDiv input[name=floorNum]").val(areaInfo.floorNum);
    $("#houseEvalDiv input[name=floor]").val(areaInfo.floor);
    $("#houseEvalDiv input[name=orientation]").val(areaInfo.orientation);
    $("#houseEvalDiv input[name=roomNum]").val(areaInfo.roomNum);
};


//房屋区域回调
function selectAreaCallBackMan(areaInfo){
    $("#houseEvalRDiv input[name=areaCode]").val(areaInfo.disNo);
    $("#houseEvalRDiv input[name=areaCodeName]").val(areaInfo.disName);
};


//选择房屋类型（物业类型）
function selectPropertypeDialogMan(callback, ele){
    var areaCode = $("#houseEvalRDiv input[name='areaCode']").val();
    if(areaCode != '' && typeof (areaCode) != 'undefined'){
        dialog({
            id:"areaDialog",
            title:'物业分类',
            url: webPath+'/mfHouseEval/getPropertyTypeList?AreaCode='+areaCode,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择房屋所在区域！",0);
    }

};
//房屋类型回掉
function selectPropertypeCallBackMan(areaInfo){
    $("#houseEvalRDiv input[name=thirdPropertyName]").val(areaInfo.disName);
    $("#houseEvalRDiv input[name=thirdProperty]").val(areaInfo.disNo);

    $.ajax({
        url : webPath+"/mfHouseEval/getSecondProperty?areaNo="+areaInfo.disNo,
        type : "post",
        dataType : "json",
        success : function(data) {
            if(data.flag == 'success'){
                $("#houseEvalRDiv input[name=secondPropertyName]").val(data.areaName);
                $("#houseEvalRDiv input[name=secondProperty]").val(data.areaNo);
            }
        },
        error : function() {
        }
    });
};



//选择小区
function selectcommunityDialogMan(callback, ele){
    var secondProperty = $("#houseEvalRDiv input[name='secondProperty']").val();
    if(secondProperty != '' && typeof (secondProperty) != 'undefined'){
        var areaCode = $("#houseEvalRDiv input[name='areaCode']").val();
        var addressInput = $("#houseEvalRDiv input[name='addressInput']").val();
        var address = $("#houseEvalRDiv input[name='address']").val();
        if((addressInput== '' || typeof (addressInput)== 'undefined') && (address=='' || typeof (address)== 'undefined')){
            alert("请先输入小区名称或产证地址！",0);
            return ;
        }
        if(addressInput != '' && typeof (addressInput)!= 'undefined'){
            address = addressInput;
        }
        dialog({
            id:"areaDialog",
            title:'小区',
            url: webPath+'/mfHouseEval/getCommuntyList?PropertyType='+secondProperty+'&Address='+address+'&AreaCode='+areaCode,
            width:350,
            height:420,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    //返回对象的属性disNo,disName
                    if(typeof(callback)== "function"){
                        callback(this.returnValue, ele);
                    }else{
                    }
                }
            }
        }).showModal();
    }else{
        alert("请先选择房产类型！",0);
    }
};

//小区回掉
function selectcommunityCallBackMan(areaInfo){
    $("#houseEvalRDiv input[name=communityName]").val(areaInfo.disName);
    $("#houseEvalRDiv input[name=communityId]").val(areaInfo.disNo);
    $("#houseEvalRDiv input[name=areaCode]").val(areaInfo.areaCode);
};
// flag 1-在线评估  2-人工评估
function evalHouseInfo(obj,evalType,relNo) {
    var resultData = '';
    var flag = submitJsMethod($(obj).get(0), '');
    if (flag) {
        var url = $(obj).attr("action");
        var jsonObj = {};
        $.each($(obj).serializeArray(), function(i, field){
            jsonObj[field.name]=field.value;
        });
        if((jsonObj.communityName=='' || typeof (jsonObj.communityName)=='undefined') && (jsonObj.address=='' || typeof (jsonObj.address)=='undefined')){
            alert("请先选择小区或者输入产证地址！",0);
            return ;
        }
        var dataParam = JSON.stringify($(obj).serializeArray());
        $.ajax({
            url : url,
            data : {
                ajaxData:dataParam,relNo:relNo,flag:evalType
            },
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(data) {
                if (data.flag == "success") {
                    alert(data.msg, 1);
                } else {
                    alert(data.msg, 0);
                }
                resultData = data;
            },
            error : function() {
            }
        });
    }
    return resultData
}
