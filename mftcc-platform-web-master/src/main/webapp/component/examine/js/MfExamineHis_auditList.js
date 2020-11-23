var MfExamineHis_auditList=function(window,$){
    //初始化列表
    var _init=function(){
        myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/MfExamineDetailController/findAuditByPageAjax",//列表数据查询的url
            tableId:"tableexamineAuditList",//列表数据查询的table编号
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30//加载默认行数(不填为系统默认行数)
        });
    };

    //新增贷后检查情况
    var _addExamineDetail = function (url,templateType) {
        /*top.addFlag = false;
        top.createShowDialog(url,"新增贷后检查情况",'90','90',function(){
            if(top.addFlag){
                updateTableData();//重新加载列表数据
            }
        });*/
        url = url + '?templateType='+templateType;
        top.window.openBigForm(url,"实地核查",function(){
            updateTableData();//重新加在列表数据taskShowDialogiframe
        });
    };

    //跳转到查看实地核查登记页面
    var _getExamineUpdatePage = function (obj,url){
        var urlStr = webPath+url+"&examEntrance=loanAfter";
        top.window.openBigForm(urlStr,"实地核查登记",function(){
            updateTableData();//重新加在列表数据taskShowDialogiframe
        });
    };

    //跳转到查看实地核查详情页面
    var _getExamineDetailPage = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url+"&pasSts=1&examEntrance=loanAfter";
        }else{
            url =webPath + "/" + url+"&pasSts=1&examEntrance=loanAfter";
        }
        top.window.openBigForm(url,"实地核查详情",function(){});
    };

    //跳转到客户详情页面
    var _getDetailPage = function (obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.window.openBigForm(url,"客户详情",function(){});
    };

    return{
        init:_init,
        getDetailPage:_getDetailPage,
        getExamineDetailPage:_getExamineDetailPage,
        getExamineUpdatePage:_getExamineUpdatePage,
        addExamineDetail:_addExamineDetail
    };
}(window,jQuery);