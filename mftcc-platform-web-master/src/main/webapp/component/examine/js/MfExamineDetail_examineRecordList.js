$(function(){
    myCustomScrollbar({
        obj:"#content",//页面内容绑定的id
        url:webPath+"/MfExamineDetailController/findExamineRecordByPageAjax",//列表数据查询的url
        tableId:"tableexamineRecordList",//列表数据查询的table编号
        tableType:"tableTag",//table所需解析标签的种类
        pageSize:30//加载默认行数(不填为系统默认行数)
    });
});

//新增贷后检查情况
function addExamineDetail(url,templateType) {
    /*top.addFlag = false;
    top.createShowDialog(url,"新增贷后检查情况",'90','90',function(){
        if(top.addFlag){
            updateTableData();//重新加载列表数据
        }
    });*/
    url = url + '?templateType='+templateType;
    top.window.openBigForm(url,"保后跟踪",function(){
        updateTableData();//重新加在列表数据taskShowDialogiframe
    });
}

//跳转到查看贷后检查详情页面
function getExamineDetailPage(obj,url) {
    if(url.substr(0,1)=="/"){
        url =webPath + url+"&pasSts=1&examEntrance=loanAfter";
    }else{
        url =webPath + "/" + url+"&pasSts=1&examEntrance=loanAfter";
    }
    top.window.openBigForm(url,"贷后检查详情",function(){});
}
//跳转到客户详情页面
function getDetailPage(obj,url){
    if(url.substr(0,1)=="/"){
        url =webPath + url;
    }else{
        url =webPath + "/" + url;
    }
    top.window.openBigForm(url,"客户详情",function(){});
}

//跳转到详情页面
function loanAfterExamineDetailNew (obj,url){
    //跳转到详情页面
    var urlStr = webPath+url+"&examEntrance=loanAfter";
    top.window.openBigForm(urlStr,"贷后检查详情",function(){});
}

function loanAfterExamine (obj,url){
    var urlStr = webPath+url+"&examEntrance=loanAfter";
    top.window.openBigForm(urlStr,"贷后检查",function(){
        updateTableData();//重新加在列表数据taskShowDialogiframe
    });
};
//指派任务
function deleteExamine(obj,url){
    if(url.substr(0,1)=="/"){
        url =webPath + url;
    }else{
        url =webPath + "/" + url;
    }
    $.ajax({
        url : url,
        type : 'post',
        dataType : 'json',
        success : function(data) {
            LoadingAnimate.stop();
            pasNoStr="";
            if(data.flag=="success"){
                window.top.alert(data.msg, 1);
                updateTableData();//重新加载列表数据
            }else if(data.flag=="error"){
                window.top.alert(data.msg, 0);
            }
        },
        error : function() {
            LoadingAnimate.stop();
            alert(top.getMessage("ERROR_REQUEST_URL"));
        }
    });
};