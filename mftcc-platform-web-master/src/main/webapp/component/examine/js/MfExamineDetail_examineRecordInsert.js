//选择所有在检查中的贷后检查任务
function selectExamineHisDialog(){
    selectExamineHisData(selectExamineHisBack);
}

//选择贷后检查任务之后的回调方法
function selectExamineHisBack(data) {
    var mfExamineHis = data.mfExamineHis;
    var listData =  data.listData;
    $("input[name='cusNameSupplier']").val(mfExamineHis.cusNameSupplier);
    $("input[name='examHisId']").val(mfExamineHis.examHisId);
    setRelateBussId(mfExamineHis);
    //给检查指标项下拉框赋值
    setIndexIdOptions(listData,data.examCardId);
}

function selectExamineHisData(callback) {
    dialog({
        id: 'examineHisDialog',
        title: "业务选择",
        url: webPath + "/mfExamineHis/getExamineHisData",
        width: 900,
        height: 400,
        backdropOpacity: 0,
        onshow: function () {
            this.returnValue = null;
        }, onclose: function () {
            if (this.returnValue) {
                //返回对象的属性:实体类MfCusCustomer中的所有属性
                if (typeof(callback) == "function") {
                    callback(this.returnValue);
                }
            }
        }
    }).showModal();
}
//给关联业务编号赋值
function setRelateBussId(mfExamineHis) {
    if(mfExamineHis.fincId!=''&&mfExamineHis.fincId!=null&&mfExamineHis.fincId!=undefined){
        $("input[name='relateBussId']").val(mfExamineHis.fincId);
    }else if(mfExamineHis.pactId!=''&&mfExamineHis.pactId!=null&&mfExamineHis.pactId!=undefined){
        $("input[name='relateBussId']").val(mfExamineHis.pactId);
    }else{
        $("input[name='relateBussId']").val(mfExamineHis.cusNoSupplier);
    }
}
var dataArr;
//给检查指标项下拉框赋值
function setIndexIdOptions(listData,examCardId){
    var options ="";
    dataArr = listData["DX"+examCardId];
    var indexId = "";
    for(var i=0;i<dataArr.length;i++){
        var data = dataArr[i];
        if("1"==data.level){
            if(options==""){
                indexId = data.indexId;
            }
            options = options+"<option value='"+data.indexId+"'>"+data.indexName+"</option>>";
        }
    }
    $("select[name='indexId']").empty().append(options);
    //根据检查指标项动态的加载检查指标
    setIndexValueOptions(indexId);
}

//根据检查指标项动态的加载检查指标
function setIndexValueOptions(indexId){
    var options ="";
    for(var i=0;i<dataArr.length;i++){
        var data = dataArr[i];
        if(indexId==data.upIndexId){
            options = options+"<option value='"+data.indexId+"'>"+data.indexName+"</option>>";
        }
    }
    $("select[name='indexValue']").empty().append(options);
}

//保存贷后检查情况信息
function insertForExamineDetailForm(obj,type) {
    var url = $(obj).attr("action");
    var flag = submitJsMethod($(obj).get(0), '');
    if (flag) {
        var paramArray =  $(obj).serializeArray();
        paramArray.push({name:"actionType",value:type});
        var dataParam = JSON.stringify(paramArray);
        $.ajax({
            url : url,
            data : {ajaxData : dataParam},
            success : function(data) {
                if (data.flag == "success") {
                    alert(data.msg,1);
                    // 关闭当前弹层。
                    myclose_click();
                } else {
                    DIALOG.error(top.getMessage("FAILED_SAVE_CONTENT",{content : "", reason: data.msg}));
                }
            },error : function() {
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });
    } else {

    }
}

//关闭新增弹出框，并支持回调
function myclose_click(){
    var url = webPath+'/MfExamineDetailController/getExamineRecordListPage';
    $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
    $(top.window.document).find("#showDialog").remove();
    $(".showDialog .i-x5",parent.document).last().click();
}