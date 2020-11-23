/**
 * Created by jzh on 2015/2/3.
 */

define(function(require, exports, module){
    //公用模块
    var contextpath = window.parent.window.$("#contextpath").val();
    /***
     * 获取文件名
     * 参数：form/table
     */
    exports.getFileName = function(objStr) {
        var fileName;
        fileName = window.parent.window.$("#designerId").val();
        if (fileName != null && fileName != "" && fileName !== undefined) {
            fileName = fileName.replace(objStr, "");
        } else {
            fileName = false
        }
        return fileName;
    }
    /**
     * 判断是form或table
     */
    exports.isFormOrTable = function() {
        var flag = "form";
        var fileName = exports.getFileName();
        if(fileName==false){
            var fromFlag = window.parent.window.$("#form-search").css("display");
            var tableFlag = window.parent.window.$("#table-search").css("display");
            if(fromFlag=="none"&&tableFlag=="block"){
                flag="table";
            }
        }else{
            if(fileName.substring(0,5)=="table"){
                flag = "table";
            }
        }
        return flag;
    }
    /***
     * 判断打开表单或列表是否相同
     */
    exports.isEqual = function() {
        var flag =false;
        var fileFlag = exports.isFormOrTable();
        var fileName = exports.getFileName();
        if(fileFlag=="form"){
            if(fileName.substring(0,4)=="form"){
                flag = true;
            }
        }else if(fileFlag=="table"){
            if(fileName.substring(0,5)=="form"){
                flag = true;
            }
        }
        return flag;
    }
    /**
    *重新加载页面
    */
    exports.reloadFormOrTable = function(){
        var flag =  exports.isFormOrTable();
        if(flag=="form"){
            window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
        }else if(flag=="table"){
            window.parent.window.$("#formIframe").attr("src","./dragTableMain.jsp");
        }
    }
    exports.changFilename = function(desType,fileID,fileName){
        if(desType=="form"){
            if(fileID!=false){
                $("#designerId").val("form"+fileID);
                $("#form-search .form-control").val("form"+fileID);
            }
        }else if(desType=="table"){
            if(fileID!=false) {
                $("#designerId").val("table" + fileID);
                $("#table-search .form-control").val("table"+fileID);
            }
        }else{
            if(desType!="modify") {
                $("#designerId").val(fileID);
                $(".form-control").val("");
            }
        }
        if(fileName==""||fileName!=null){
            $("#designerName").val(fileName);
        }
    }
    /**
     * 改变修改的信息
     */
    exports.changeShowMsg = function(objStr){
        if(objStr=="form"){
            $("#addDes")
        }else if(objStr=="table"){

        }
    }
    /**
     *方法：选定selected
     *参数：1.idStr(select id)
     *     2.selectVal(选中的值)
     */
     exports.setSelected = function(idStr,selectVal){
        var selectObj =$("#"+idStr);
        var pObj = selectObj.parents(".dropdown");
        var text = "";
        var count = selectObj.find("option").length;
        selectObj.find("option").removeAttr("selected");
        pObj.find("div ul li").removeClass("active");
        for(var i=0;i<count;i++){
            if(selectObj.get(0).options[i].value == selectVal) {
                text = selectObj.get(0).options[i].text;
                selectObj.get(0).options[i].selected=true;
                pObj.find("div ul li").eq(i).addClass("active");
            }
        }
        pObj.find(".selected").html(text);
    }
    exports.getSelected =function(idStr) {
        var selectObj =$("#"+idStr);
        var pObj = selectObj.parents(".dropdown");
        var selectedVal = "";
        var text = $.trim(pObj.find("div ul .active").text());
        var count = selectObj.find("option").length;
        for(var i=0;i<count;i++){
            if(selectObj.get(0).options[i].text == text) {
                selectedVal = selectObj.get(0).options[i].value;
            }
        }
        return selectedVal;
    }
    /***
     * 获取form字段ID
     * @returns {*|jQuery}
     */
    exports.getFormActiveId =function() {
        var formActiveId =$(".setProperties").attr("formactiveid");
        if(formActiveId===undefined||formActiveId==""){
            formActiveId =false;
        }else{
            formActiveId = $.trim(formActiveId.split("_")[1]);
        }
        return formActiveId;
    }
    /***
     * 设置校验公式的值
     * @param validteVal
     * @param validteShow
     */
    exports.setValidteVal = function(){
        var formId = exports.getFileName("form");
        $.post(contextpath+"/tech/dragDesginer/FormDataForToolBean/getFormValidateElement",{formId: formId},
            function(data){
                if($.trim(data)!=""&&$.trim(data)!="&nbsp"){
                    var obj =  window.parent.window.$("body");
                    var textValue = data.split(",")[0];
                    var textShow = data.split(",")[1];
                    if(textValue==null||textValue=="null"){
                        textValue = "";
                    }
                    if(textShow==null||textShow=="null"){
                        textShow = "";
                    }
                    $(obj).find("#formulaCheckShow").val(textValue);
                    $(obj).find("#formulaCheckShow").attr("calculationShow",textShow);
                }
            }, "html");
    }

    exports.initInsertValues = function(objId){
        var formId = exports.getFileName("form");
        var selectLi = $("#"+objId).find(".showSelect");
        if(selectLi.html()!==undefined){
            $.post(contextpath+"/tech/dragDesginer/FormDataForToolBean/getInitCalculation",{formId: formId},
                function(data){
                    var dropStr = data.split(",");
                    var options = getOptions(dropStr);
                    $("#"+objId).find(".showSelect li").remove();
                    $("#"+objId).find(".showSelect .mCSB_container").append(options);
                }, "html");
        }
    }
    exports.updateShowHead = function(titleName){
        var showHead = $("#formIframe").contents().find(".showHead").find("h1");
        var showHeadText = $.trim($(showHead).text());
        $("#designerName").val(titleName);
        if(titleName!=showHeadText){
            $(showHead).text(titleName);
            $("#formIframe").contents().find(".showHead").width($(showHead).width());
        }
    }

    function getOptions(dropStr){
        var options = "";
        for(var i=0;i<dropStr.length-1;i++){
            var value = dropStr[i].substr(dropStr[i].lastIndexOf("(")+1,dropStr[i].lastIndexOf(")")-dropStr[i].lastIndexOf("(")-1);
            if(i==0){
                options+='<li value="'+value+'">'+dropStr[0]+'</li>';
            }else{
                options+='<li  value="'+value+'">'+dropStr[i]+'</li>';
            }
        }
        return options;
    }

});