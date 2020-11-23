/**
 * Created by zhangzhuo on 2014/12/23.
 */
define(function(require, exports, module) {
    var flagSearch = false;
    var $ = require("jquery");
    require("pace/js/pace.js");
    //引用bootstrap-switch库
    require("bootstrap-switch/js/bootstrap-switch.min.js");
    require("easydropdown/js/jquery.easydropdown.js");
    //引用bootstrap-js库
    require("bootstrap/js/bootstrap.min.js");
    //引用list-js库
    require("list/js/list.min.js");
    require("./myAlert.js");
    require("./loadFunction.js");
    //引用常用方法
    var util = require("commonUtil/commonUtil.js");
    //获取iframe margin-bottom值
    var iframeMarginTop = util.splitPx($("#formIframe").css("margin-top"));
    var iframeAttributeSetting = require("./iframeAttributeSetting.js");
    var commonFunction = require("./commonFunction.js");
    //ajax获取from.table名称跟描述
    var contextpath = $("#contextpath").val();
    
    
   $.post(contextpath+"/tech/dragDesginer/FormForToolBean/loadFormData", function(data){
        //-------------------更改form中的节点
        var formSearchUl = $(".form-search-ul");
        formSearchUl.empty();
        var dataForm = data.form;
        for(var i=0; i<dataForm.length;i++){
             formSearchUl.append("<li  onclick='searchClickLi(this)'><div class='dragFormName'>"+dataForm[i].dragFormName+"</div><div class='dragFormDesc'>"+dataForm[i].dragFormDesc+"</div></li>")
        }
        //查询的配置
        var formSearchOsptions = {
             valueNames: [ 'dragFormName','dragFormDesc' ]
        };
        var formList = new List('form-search', formSearchOsptions);
    },"json");


    //计算当前屏幕大小  给iframe设置宽度
    var currentWindowHeight = $(window).height();

    //为了兼容ie浏览器 以及不顶到底边均减少5像素
    $("#formIframe").attr("height", (currentWindowHeight - iframeMarginTop)-5 + "px");
    $("[name='onOrOff']").bootstrapSwitch(
        {
            onText:"是",
            offText:"否",
            offColor:'success',
            state:false,
            onSwitchChange:function(event, state){
                if(state){
                    $(this).parent().find(".bootstrap-switch-label").text("否");
                }else{
                    $(this).parent().find(".bootstrap-switch-label").text("是");
                }

            }
        });
    $("[name='onOrOff']").parent().find(".bootstrap-switch-label").addClass("checkunchoseshow").text("是");
    var showId = "";
    $(".form-control-val").focusin(function(e){
        showId=$(this).val();
        $(this).val("");
        var parentId = $(this).parents("#form-search").attr("id");
        if(parentId===undefined){
            parentId = $(this).parents("#table-search").attr("id");
        }
        changeList("#"+parentId);
    });
    $(".form-control-val").focusout(function(e){
        if($(this).val()==""){
            $(this).val(showId);
        }
        var parentId =  $(this).parents("#form-search").attr("id");
        if(parentId===undefined){
            parentId =  $(this).parents("#table-search").attr("id");
        }
        //防止点击事件不起作用
        setTimeout(function(){
            $("#"+parentId).find(".list").hide();
        },200);
    });
    // search的input绑定键盘监听
    $("#form-search,#table-search").keyup(function (e) {
        // 键盘按钮判断 上，下，回车
        var parentId = "#" + $(this).attr("id");
        switch(e.which)
        {
            // 回车
            case 13:   enterKey(parentId);
                break;
            // 上
            case 38:    upKey(parentId);
                break;
            // 下
            case 40:   downKey(parentId);
                break;
            ////退格键
            //case 8:   rebackKey(parentId);
            //    break;
            // 默认则改变内容
            default : changeList(parentId);
                break;
        }
    });
    window.choiceFormTable = function(obj,tableFormStr){
        var designerType = $("#designerType").val();
        var designerId = $("#designerId").val();
        var fileName = commonFunction.getFileName();
        if(tableFormStr=="form"){
            if(designerType=="table"){
                $("#form-search").show();
                $("#table-search").hide();
                $("#designerType").val("form");
                $(obj).parents(".btn-group").find(".showDesName").find("span").eq(0).html("表单");
                if(fileName==false){
                    window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
                }
                $(".tab_ctrls .tab_ctrl").each(function(){
                    // table_tab_ctrl
                    $(this).removeClass("title_choice").addClass("title_nochoice");
                    if($(this).attr("role")=="format"){
                        $(this).removeClass("title_nochoice").addClass("title_choice");
                    }
                });
                $(".table_tab_ctrl").removeClass("table_tab_ctrl");
            }
        }else if(tableFormStr=="table"){
            //if(designerType=="form"){
                $("#designerType").val("table");
                $("#form-search").hide();
                $("#table-search").show();
                if(!flagSearch){
                    flagSearch = true;
                    $.post(contextpath+"/tech/dragDesginer/TableForToolBean/loadTableData", function(data){
                        //------------------再更改table中的节点
                        var tableSearchUl = $(".table-search-ul");
                        tableSearchUl.empty();
                        var dataTable = data.table;
                        for(var i=0; i<dataTable.length;i++){
                            tableSearchUl.append("<li onclick='searchClickLi(this)'><div class='dragTableName'>"+dataTable[i].dragTableName+"</div><div class='dragTableDesc'>"+dataTable[i].dragTableDesc+"</div></li>")
                        }
                        //查询的配置
                        var tableSearchOsptions = {
                            valueNames: [ 'dragTableName','dragTableDesc' ]
                        };
                        var tableList = new List('table-search', tableSearchOsptions);
                    },"json");
                }
                if(fileName==false) {
                    window.parent.window.$("#formIframe").attr("src", "./dragTableMain.jsp");
                }
                $(obj).parents(".btn-group").find(".showDesName").find("span").eq(0).html("列表");
                $(".tab_ctrls .tab_ctrl").each(function(){
                   // table_tab_ctrl
                    $(this).removeClass("title_choice").addClass("title_nochoice");
                    if($(this).attr("role")=="attribute"){
                        $(this).removeClass("title_nochoice").addClass("title_choice");
                    }else{
                        $(this).addClass("table_tab_ctrl");
                    }
                });
                $(".formula_validate").parent().addClass("table_tab_ctrl");
            //}
        }
    }
    //table-search/form-search的ul li绑定点击事件
    window.searchClickLi = function(e){
        var dragName = "";
        var dragDesc = "";
        var parentId = "#" +$(e).parents("#form-search,#table-search").attr("id");
        $(parentId).find(".li_active").removeClass("li_active");
        $(e).addClass("li_active");
        var li_active = $(parentId).find(".li_active");
        if(li_active.length == 0){
            return;
        }
        if(parentId == "#form-search"){
            dragName = $(li_active).find(".dragFormName").text();
            dragDesc = $(li_active).find(".dragFormDesc").text();
            $(".formInfor").find(".fa-list-ul").show();
            $(".formInfor").find(".fa-th").hide();
            $("#form-search .form-control").val(dragName);
            window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
        }else if(parentId == "#table-search"){
            dragName = $(li_active).find(".dragTableName").text();
            dragDesc = $(li_active).find(".dragTableDesc").text();
            $(".formInfor").find(".fa-list-ul").hide();
            $(".formInfor").find(".fa-th").show();
            $("#table-search .form-control").val(dragName);
            window.parent.window.$("#formIframe").attr("src","./dragTableMain.jsp");
        }
        //给form或者table赋值
        $("#designerId").val(dragName);
        $("#designerName").val(dragDesc);
        //隐藏ul
        $(parentId).find(".list").hide();
        //just 为了特效 - -
        $(".formInfor").hide();
        //打开form/table infor
        $(".formInfor").fadeIn(600);
    }

    // 键盘上方法
    function upKey(parentId){
        // 获取当前active的
        var li_active = $(parentId).find(".li_active");
        // 如果此时有上一个元素
        var next_li_active = li_active.prev();

        if(next_li_active.length != 0){
            $(li_active).removeClass("li_active");
            $(next_li_active).addClass("li_active");
        }
    }

    // 键盘下
    function downKey(parentId){
        // 获取当前active的
        var li_active = $(parentId).find(".li_active");
        // 如果此时有下一个元素
        var next_li_active = li_active.next();

        if(next_li_active.length != 0){
            $(li_active).removeClass("li_active");
            $(next_li_active).addClass("li_active");
        }
    }

    // 键盘回车
    function enterKey(parentId){

        //如果当前下面没有可选中的   返回空
        var li_active = $(parentId).find(".li_active")
        if(li_active.length == 0){
            return;
        }
        var dragName = "";
        var dragDesc = "";
        if(parentId == "#form-search"){
            dragName = $(li_active).find(".dragFormName").text();
            dragDesc = $(li_active).find(".dragFormDesc").text();
            $(".formInfor").find(".fa-list-ul").show();
            $(".formInfor").find(".fa-th").hide();
            $("#form-search .form-control").val(dragName);
            window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
        }else if(parentId == "#table-search"){
            dragName = $(li_active).find(".dragTableName").text();
            dragDesc = $(li_active).find(".dragTableDesc").text();
            $(".formInfor").find(".fa-list-ul").hide();
            $(".formInfor").find(".fa-th").show();
            $("#table-search .form-control").val(dragName);
            window.parent.window.$("#formIframe").attr("src","./dragTableMain.jsp");
        }
        //给form或者table赋值
        $("#designerId").val(dragName);
        $("#designerName").val(dragDesc);
        //隐藏ul
        $(parentId).find(".list").hide();
        //just 为了特效 - -
        $(".formInfor").hide();
        //打开form/table infor
        $(".formInfor").fadeIn(600);
    }

    //键盘回退
    function rebackKey(parentId){
        //获取当前输入的内容   如果内容为空  则隐藏ul
        var inputValue = $(parentId).find("input").val();

        if("" == $.trim(inputValue)){
            $(parentId).find(".list").hide();
        }
    }
    //输入其他值
    function changeList(parentId){
        $(parentId).find(".list").show();
        setTimeout("setFirstMenuActive('"+parentId+"')",400);
    }

    //由于setTimeOut  手动暴露方法
    window.setFirstMenuActive =  function(dom){
        //清空所有li_active
        $(dom).find(".li_active").removeClass("li_active");
        //赋新值
        $($(dom).find(".list li")[0]).addClass("li_active");
    }
    //从新加载table/form search列表
    window.reloadFormOrTableSearch = function(tableOrForm){
        if(tableOrForm=="form"){
            $.post(contextpath+"/tech/dragDesginer/FormForToolBean/loadFormData", function(data){
                //-------------------更改form中的节点
                var formSearchUl = $(".form-search-ul");
                formSearchUl.empty();
                var dataForm = data.form;
                for(var i=0; i<dataForm.length;i++){
                    formSearchUl.append("<li  onclick='searchClickLi(this)'><div class='dragFormName'>"+dataForm[i].dragFormName+"</div><div class='dragFormDesc'>"+dataForm[i].dragFormDesc+"</div></li>")
                }
                //查询的配置
                var formSearchOsptions = {
                    valueNames: [ 'dragFormName','dragFormDesc' ]
                };
                var formList = new List('form-search', formSearchOsptions);
            },"json");
        }else if(tableOrForm=="table"){
            $.post(contextpath+"/tech/dragDesginer/TableForToolBean/loadTableData", function(data){
                //------------------再更改table中的节点
                var tableSearchUl = $(".table-search-ul");
                tableSearchUl.empty();
                var dataTable = data.table;
                for(var i=0; i<dataTable.length;i++){
                    tableSearchUl.append("<li onclick='searchClickLi(this)'><div class='dragTableName'>"+dataTable[i].dragTableName+"</div><div class='dragTableDesc'>"+dataTable[i].dragTableDesc+"</div></li>")
                }
                //查询的配置
                var tableSearchOsptions = {
                    valueNames: [ 'dragTableName','dragTableDesc' ]
                };
                var tableList = new List('table-search', tableSearchOsptions);
            },"json");
        }
    }
    $(".tab_ctrl").click(function(){//更改标签
        $(".tab_ctrl").removeClass("title_choice").addClass("title_nochoice");
        $(this).removeClass("title_nochoice").addClass("title_choice");
        $("#formIframe")[0].contentWindow.change_tab($.trim($(this).attr("role")));
    });
    $(function(){
       var designerType = $("#designerType").val();
       //$("#designerId").val("");
       //$("#designerName").val("");
        if(designerType!==undefined&&designerType!=""){
            if(designerType=="form"){
                $(".tab_ctrls .tab_ctrl").each(function(){
                    // table_tab_ctrl
                    $(this).removeClass("title_choice").addClass("title_nochoice");
                    if($(this).attr("role")=="format"){
                        $(this).removeClass("title_nochoice").addClass("title_choice");
                    }
                });
                window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
            }else if(designerType=="table"){
                $(".tab_ctrls .tab_ctrl").each(function(){
                    // table_tab_ctrl
                    $(this).removeClass("title_choice").addClass("title_nochoice");
                    if($(this).attr("role")=="attribute"){
                        $(this).removeClass("title_nochoice").addClass("title_choice");
                    }else{
                        $(this).addClass("table_tab_ctrl");
                    }
                });
                window.parent.window.$("#formIframe").attr("src","./dragTableMain.jsp");
            }
            choiceFormTable($(".changeList"),designerType);
        }else{
            window.parent.window.$("#formIframe").attr("src","./dragFormMain.jsp");
        }
    });
});