var dataVal = {}, wkfRoleNo, wkfRoleName, SynchronousLoadding = true;
$(function() {
	var bodyHeight = $(".table_content").parent().height()-100;
	$(".table_content").height(bodyHeight);
    $(".table_content").each(function() {
        var $tbContObj = $(this);
        $(this).mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            },
            onTotalScrollOffset: 30,
            //回调距什么位置回调
            callbacks: {
                onTotalScroll: function() {
                    updateTableContent($tbContObj);
                }
            }
        });
    });
    
    $(".select-border .colmon input").bind({
        keyup: function(event) {
            var keyVal = event.which;
            if (keyVal == "13") {
            	selectWkfApprovalRole();
                initClick();
            }
        }
    });
    selectWkfApprovalRole();
    
    var documentHeight=$(document).height();
    var finalHeight=documentHeight-117;
    $(".col_content").each(function(){
    	$(this).height(finalHeight);
//    	$(this).find(".table_content").height(finalHeight-50);
    });
    $(".table_content td[mytitle]:contains('...')").initMytitle();
});

/**
 * 单击事件
 */
function initClick() {
    $(".table_content.parent .ls_list tbody tr").unbind();
    $(".table_content.parent .ls_list tbody tr").bind("click", function(event) {
        $(this).parents("table").find(".selected").removeClass("selected");
        $(this).addClass("selected");
        var functionUrl = $(this).find("td[class='auditUrl']").find("a").attr("onclick");
        wkfRoleNo = functionUrl.split("?")[1].split("'")[0].split("&")[0].split("=")[1];
        initWkfApprovalUserLst(wkfRoleNo);
        formExit($(this));
    });
}

/**
 * 子表数据初始化
 * @param {Object} wkfRoleNo
 */
function initWkfApprovalUserLst(wkfRoleNo) {
    var data = {
        wkfRoleNo: wkfRoleNo
    };
    $.ajax({
        type: "post",
        url: webPath+"/wkfApprovalUser/findByPageAjax",
        data: {
            ajaxData: JSON.stringify(data),
            pageSize: 25,
            tableId: "tablewkf0015",
            tableType: "tableTag"
        },
        success: function(data) {
            var $tableContent = $(".table_content.child");
            $tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
            setPager($tableContent, data.ipage);
            intiPageShow($tableContent, data.ipage);
            SynchronousLoadding = false;
        },
        error: function() {
        }
    });
}
/**
 * 获得查询表头
 */
function getSelectInputVal() {
    var wkfRoleName = $(".select-border").find("input[name='wkfRoleName']").val();
    var data = {
        wkfRoleName: wkfRoleName
    };
    return data;
}
/**
 * 全局查询,以及第一次进入
 */
function selectWkfApprovalRole() {
    var dataParam = JSON.stringify(getSelectInputVal());
    var $tableParent = $(".table_content.parent").find("table.ls_list");
    var $tableChild = $(".table_content.child").find("table.ls_list");
    var tableId = $tableParent.attr("title");
    var tableIdc = $tableChild.attr("title");
    var tableType = "thirdTableTag";
    $.ajax({
        type: "post",
        url: webPath+"/wkfApprovalRole/findByPageAjax",
        async: false,
        data: {
            ajaxData: dataParam,
            pageSize: 25,
            tableId: tableId,
            tableIdc: tableIdc,
            tableType: tableType
        },
        success: function(data) {
            $tableParent.find("tbody").html($(data.ipage.result).find("tbody").html());
            setPager($(".table_content.parent"), data.ipage);
            intiPageShow($(".table_content.parent"), data.ipage);
            $tableChild.find("tbody").html("");
            clearPager($(".table_content.child"));
            clearPageShow($(".table_content.child"));
            $(".table_content").mCustomScrollbar("scrollTo", "top");
            SynchronousLoadding = true;
            initClick();
        },
        error: function() {
        
        }
    });
}

/**
 * 滚动条滚动加载
 * @param {Object} $obj
 */
function updateTableContent($obj) {
    var pager = getPager($obj);
    if (pager.pageNo < pager.pageSum) {
        pager.pageNo = pager.pageNo + 1;
        var url, tableId;
        var tableType = "thirdTableTag";
        var flag = $obj.hasClass("parent");
        if (flag) {
            var $tableParent = $(".table_content.parent").find("table.ls_list");
            tableId = $tableParent.attr("title");
            url = webPath+"/wkfApprovalRole/findByPageAjax";
        } else {
            var $tableChild = $(".table_content.child").find("table.ls_list");
            tableId = $tableChild.attr("title");
            url = webPath+"/wkfApprovalUser/findByPageAjax";
        }
        var dataParam = {};
        if (!SynchronousLoadding) {
            dataParam.wkfRoleNo = wkfRoleNo;
        } else {
            dataParam = getSelectInputVal();
        }
        var data = {
            ajaxData: JSON.stringify(dataParam),
            tableType: tableType,
            tableId: tableId,
        };
        data = $.extend({}, data, pager);
        $.ajax({
            type: "post",
            url: url,
            data: data,
            success: function(data) {
                $obj.find("table.ls_list tbody").append($(data.ipage.result).find("tbody").html());
                intiPageShow($obj, data.ipage);
                setPager($obj, data.ipage);
                initClick();
            }
        });
    } else {
    
    }
}
function intiPageShow($obj, pager) {
    var flag = $obj.parent().find(".pagerShow").html();
    if (flag === undefined) {
        var pageShowHtml = '<div class="pagerShow">';
        pageShowHtml += '<span>';
        pageShowHtml += '共计<span class="pageCount">' + pager.pageCounts + '</span>条';
        pageShowHtml += '<span class="split"></span>已加载<span class="loadCount">' + pager.endNum + '</span>条';
        pageShowHtml += '</span>';
        pageShowHtml += '</div>';
        $obj.parent().append(pageShowHtml);
    } else {
        var $pageShow = $obj.parent().find(".pagerShow");
        $pageShow.find(".pageCount").html(pager.pageCounts);
        $pageShow.find(".loadCount").html(pager.endNum);
    }
}

function getPager($obj) {
    var pager = {};
    pager.pageSize = parseInt($obj.find(".pageer").attr("pageSize"));
    pager.pageNo = parseInt($obj.find(".pageer").attr("pageNo"));
    pager.pageSum = parseInt($obj.find(".pageer").attr("pageSum"));
    return pager;
}
function setPager($obj, pager) {
    $obj.find(".pageer").attr("pageSize", pager.pageSize);
    $obj.find(".pageer").attr("pageNo", pager.pageNo);
    $obj.find(".pageer").attr("pageSum", pager.pageSum);
}
function clearPager($obj) {
    $obj.find(".pageer").attr("pageSize", 0);
    $obj.find(".pageer").attr("pageNo", 0);
    $obj.find(".pageer").attr("pageSum", 0);
}
function clearPageShow($obj) {
    var flag = $obj.parent().find(".pagerShow").html();
    if (flag != undefined) {
        var $pageShow = $obj.parent().find(".pagerShow");
        $pageShow.find(".pageCount").html("0");
        $pageShow.find(".loadCount").html("0");
    }
}
function openThisForm(obj) {
    var $colContent = $(obj).parents(".col_content");
    var showFlag = $colContent.find(".form_content").css("display");
    var tableContentHeight = $colContent.find(".table_content").height();
    if (showFlag == "none") {
        $colContent.find(".table_content").animate({
            height: tableContentHeight - 80
        });
        $colContent.find(".form_content").slideDown();
    }
}
//审批角色新增
function wkfApprovalRoleInput(obj, url) {
    openThisForm(obj);
    var $formObj = $(obj).parents(".col_content").find(".form_content form");
    $formObj.attr("action", url);
    $formObj.find("input[type='text']").val("").removeAttr("readOnly").removeClass("readonly_border");
}
//取消
function formExit(obj) {
    var $colContent = $(obj).parents(".col_content");
    if ($colContent.find(".form_content").css("display") == "block") {
        var tableContentHeight = $colContent.find(".table_content").height();
        $colContent.find(".table_content").animate({
            height: tableContentHeight + 100
        });
        $colContent.find(".form_content").slideUp();
    }
}
//审批角色保存
function wkfApprovalRoleSave(obj, url) {
    var ajaxUrl = $(obj).attr("action");
    if (ajaxUrl === undefined || ajaxUrl == "") {
        ajaxUrl = url;
    }
    var $form = $("#formwkf0001");
    var dataParam = {};
    dataParam.wkfRoleName = $form.find("input[name='wkfRoleName']").val();
    dataParam.wkfRoleNo = $form.find("input[name='wkfRoleNo']").val();
    dataParam.wkfRoleBrName = $form.find("input[name='wkfRoleBrName']").val();
    dataParam.wkfRoleBrNo = $form.parents("form").find("input[name='wkfRoleBrNo']").val();
    if (dataParam.wkfRoleName != undefined && dataParam.wkfRoleName != "" && dataParam.wkfRoleName != null  && dataParam.wkfRoleNo != undefined && dataParam.wkfRoleNo != "" && dataParam.wkfRoleNo != null) {
        dataParam = JSON.stringify(changeDateParm(dataParam));
        var tableId = $(obj).parents(".col_content").find(".table_content table.ls_list").attr("title");
        var data = {
            ajaxData: dataParam,
            tableId: tableId
        };
        $.ajax({
            type: "post",
            url: ajaxUrl,
            data: data,
            success: function(data) {
                if (data.flag == "success") {
                	alert(data.msg,1);
                    var $tableContent = $(obj).parents(".col_content").find(".table_content.parent");
                    $tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
                    setPager($tableContent, data.ipage);
                    intiPageShow($tableContent, data.ipage);
                    updateTableContent($tableContent);
                    $(obj).removeAttr("action");
                    initClick();
                    formExit(obj);
                } else {
                   // $.myAlert.Alert(data.msg);
                    alert(data.msg,0);
                }
            },
            error: function() {
                //$.myAlert.Alert(  top.getMessage("FAILED_SAVE"));
                alert(  top.getMessage("FAILED_SAVE"),0);
            }
        });
    } else {
        var msg =top.getMessage("NOT_FORM_EMPTY", "角色编号/角色名称");
        //$.myAlert.Alert(msg);
        alert(msg,0);
    }
}
/**
 * 角色修改跳转
 * @param {Object} obj
 * @param {Object} url
 */
function wkfApprovalRoleUpdate(obj, url) {
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
    $.ajax({
        type: "get",
        url: url,
        success: function(data) {
            if (data.flag == "success") {
                var $v = $(obj).parents("td").parents("tr");
                $v.parents("table").find(".selected").removeClass("selected");
                $v.addClass("selected");
                openThisForm(obj);
                var $form = $(obj).parents(".col_content").find(".form_content form");
                $form.removeAttr("action");
                $form.find("input[name='wkfRoleNo']").val(data.formData.wkfRoleNo).attr("readOnly", "readOnly").addClass("readonly_border");
                $form.find("input[name='wkfRoleName']").val(data.formData.wkfRoleName);
                $form.find("input[name='wkfRoleBrName']").val(data.formData.wkfRoleBrName);
                $form.parents("form").find("input[name='wkfRoleBrNo']").val(data.formData.wkfRoleBrNo);
            } else {
                //$.myAlert.Alert(data.msg);
                alert(data.msg,0);
            }
        },
        error: function() {
            //$.myAlert.Alert(top.getMessage("ERROR_SELECT"));
            alert(top.getMessage("ERROR_SELECT"),0);
        }
    });
    
    if (window.event) {
        //e.returnValue=false;//阻止自身行为
        window.event.cancelBubble = true;
        //阻止冒泡
    } else if (arguments.callee.caller.arguments[0].preventDefault) {
        //e.preventDefault();//阻止自身行为
        arguments.callee.caller.arguments[0].stopPropagation();
        //阻止冒泡
    }
}
/**
 * 删除
 * @param {Object} obj
 * @param {Object} url
 */
function ajaxDelete(obj, url) {
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
    var flag = $(obj).parents(".table_content").hasClass("parent");
    alert("是否确定删除？",2,function(){
        $.ajax({
            type: "get",
            url: url,
            success: function(data) {
                if (data.flag == "success") {
                    var $pageShow = $(obj).parents(".col_content").find(".pagerShow");
                    $(obj).parents("tr").remove();
                    var pageCount = parseInt($pageShow.find(".pageCount").html().trim());
                    $pageShow.find(".pageCount").html((pageCount - 1) >= 0 ? (pageCount - 1) : 0);
                    $pageShow.find(".loadCount").html((pageCount - 1) >= 0 ? (pageCount - 1) : 0);
                    if (flag) {
                        var params = url.split("?");
                        var param = params[1].split("&");
                        $.each(param, function(index, datatemp) {
                            if (datatemp !== undefined && datatemp.split("=") == "wkfRoleNo") {
                                wkfRoleNo = datatemp.split("=")[1];
                                
                            }
                            if(wkfRoleNo!=null&&wkfRoleNo!= undefined&&wkfRoleNo!=''){
                            	initWkfApprovalUserLst(wkfRoleNo);
                                wkfRoleNo = '';
                            }
                        });
                    }
                    formExit(obj);
                    //$.myAlert.Alert(top.getMessage("SUCCEED_DELETE"));
                    alert(top.getMessage("SUCCEED_DELETE"),1);
                } else {
                    //$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
                    alert(top.getMessage("FAILED_DELETE"),0);
                }
            },
            error: function() {
                //$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
                alert(top.getMessage("FAILED_DELETE"),0);
            }
        });
    });
    /*$.myAlert.Confirm("是否确定删除？", "", function() {
        $.ajax({
            type: "get",
            url: url,
            success: function(data) {
                if (data.flag == "success") {
                    var $pageShow = $(obj).parents(".col_content").find(".pagerShow");
                    $(obj).parents("tr").remove();
                    var pageCount = parseInt($pageShow.find(".pageCount").html().trim());
                    $pageShow.find(".pageCount").html((pageCount - 1) >= 0 ? (pageCount - 1) : 0);
                    $pageShow.find(".loadCount").html((pageCount - 1) >= 0 ? (pageCount - 1) : 0);
                    if (flag) {
                        var params = url.split("?");
                        var param = params[1].split("&");
                        $.each(param, function(index, datatemp) {
                            if (datatemp !== undefined && datatemp.split("=") == "wkfRoleNo") {
                                wkfRoleNo = datatemp.split("=")[1];
                                
                            }
                            if(wkfRoleNo!=null&&wkfRoleNo!= undefined&&wkfRoleNo!=''){
                            	initWkfApprovalUserLst(wkfRoleNo);
                                wkfRoleNo = '';
                            }
                        });
                    }
                    formExit(obj);
                    $.myAlert.Alert(top.getMessage("SUCCEED_DELETE"));
                } else {
                    $.myAlert.Alert(top.getMessage("FAILED_DELETE"));
                }
            },
            error: function() {
                $.myAlert.Alert(top.getMessage("FAILED_DELETE"));
            }
        });
    });*/
}

/**审批用户配置**/
function wkfApprovalUserInput(obj, url) {
    if (wkfRoleNo != undefined&&wkfRoleNo!=''&&wkfRoleNo!=null) {
        openThisForm(obj);
        var $formObj = $(obj).parents(".col_content").find(".form_content.child form");
        $formObj.attr("action", url);
        $formObj.find("input[type='text']").val("").removeAttr("readOnly").removeClass("readonly_border");
    } else {
        //$.myAlert.Alert("请选择审批角色,然后新增！");
        alert("请选择审批角色,然后新增！",0);
    }
}

/**审批用户的保存**/
function wkfApprovalUserSave(obj, url) {
    var flag = "insert";
    var ajaxUrl = $(obj).attr("action");
    if (ajaxUrl === undefined || ajaxUrl == "") {
        ajaxUrl = url;
        flag = "update";
    }
    var dataParam = {};
    dataParam.wkfUserName = $(obj).find("input[name='wkfUserName']").val();
    dataParam.displayname = $(obj).find("input[name='displayname']").val();
    dataParam.wkfBrNo = $(obj).find("input[name='wkfBrNo']").val();
    dataParam.wkfRoleNo = wkfRoleNo;
    dataParam.seq = $(obj).find("input[name='seq']").val();
    if (dataParam.wkfUserName != undefined && dataParam.wkfUserName != "" && dataParam.wkfUserName != null  && dataParam.wkfRoleNo != undefined && dataParam.wkfRoleNo != "" && dataParam.wkfRoleNo != null ) {
        dataParam = JSON.stringify(changeDateParm(dataParam));
        var tableId = $(obj).parents(".col_content").find(".table_content.child table.ls_list").attr("title");
        var data = {
            ajaxData: dataParam,
            tableId: tableId,
            wkfRoleNo: wkfRoleNo
        };
        $.ajax({
            type: "post",
            url: ajaxUrl,
            data: data,
            success: function(data) {
                if (data.flag == "success") {
                    var $tableContent = $(obj).parents(".col_content").find(".table_content.child");
                    $tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
                    setPager($tableContent, data.ipage);
                    intiPageShow($tableContent, data.ipage);
                    $(obj).removeAttr("action");
                    initClick();
                    formExit(obj);
                    //$.myAlert.Alert(flag == "insert" ? top.getMessage("SUCCEED_INSERT") : top.getMessage("SUCCEED_SAVE"));
                    alert(flag == "insert" ? top.getMessage("SUCCEED_INSERT") : top.getMessage("SUCCEED_SAVE"),1);
                } else {
                    //$.myAlert.Alert(data.msg);
                    alert(data.msg,0);
                }
            },
            error: function() {
                //$.myAlert.Alert(  top.getMessage("FAILED_SAVE"));
                alert(  top.getMessage("FAILED_SAVE"),0);
            }
        });
    } else {
        var msg =top.getMessage("NOT_FORM_EMPTY", "角色编号/用户名称");
        //$.myAlert.Alert(msg);
        alert(msg,0);
    }

}
/**
 * @param {Object} json
 * return jsonArray
 */
function changeDateParm(json) {
    var jsonArray = [];
    $.each(json, function(name, value) {
        var jsonObj = {};
        jsonObj.name = name;
        jsonObj.value = value;
        jsonArray.push(jsonObj);
    });
    
    return jsonArray;
}

function sysUserCallBack(data) {
    $(".form_content.child form").find("input[name='wkfBrNo']").val(data.brNo);
    $(".form_content.child form").find("input[name='wkfUserName']").val(data.opNo);
}

function sysOrgCallBack(data) {
    $("table[id='formwkf0001']").parents("form").find("input[name='wkfRoleBrNo']").val(data.brNo);
    $("table[id='formwkf0001']").parents("form").find("input[name='wkfRoleBrName']").val(data.brName);
}
/**
 * 防止保持方法
 */
function enterKey() {}
;
function func_uior_valTypeImm(obj) {}
;