var dataVal = {}, regionNo, name, SynchronousLoadding = true;
$(function() {
    var bodyHeight = document.body.clientHeight - 145;
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
            	selectSysAreaConf();
                initClick();
            }
        }
    });
    selectSysAreaConf();
});

/**
 * 单击事件
 */
function initClick() {
    $(".table_content.parent .ls_list tbody tr").unbind();
    $(".table_content.parent .ls_list tbody tr").bind("click", function(event) {
        $(this).parents("table").find(".selected").removeClass("selected");
        $(this).addClass("selected");
        var functionUrl = $(this).find("td").eq(1).find("a").attr("onclick");
        regionNo = functionUrl.split("?")[1].split("'")[0].split("&")[0].split("=")[1];
        initSysAreaRegionRel(regionNo);
        formExit($(this));
    });
}

/**
 * 子表数据初始化
 * @param {Object} regionNo
 */
function initSysAreaRegionRel(regionNo) {
    var data = {
        regionNo: regionNo
    };
    $.ajax({
        type: "post",
        url: webPath+"/sysAreaRegionRel/findByPageAjax",
        data: {
            ajaxData: JSON.stringify(data),
            pageSize: 25,
            tableId: "tablesys1002",
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
    var name = $("#formsys1001").find("input[name='regionName']").val();
    var data = {
        regionName:name
    };
    return data;
}
/**
 * 全局查询,以及第一次进入
 */
function selectSysAreaConf() {
    var dataParam = JSON.stringify(getSelectInputVal());
    var $tableParent = $(".table_content.parent").find("table.ls_list");
    var $tableChild = $(".table_content.child").find("table.ls_list");
    var tableId = $tableParent.attr("title");
    var tableIdc = $tableChild.attr("title");
    var tableType = "thirdTableTag";
    $.ajax({
        type: "post",
        url: webPath+"/sysAreaConf/findByPageAjax",
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
            url = webPath+"/sysAreaConf/findByPageAjax";
        } else {
            var $tableChild = $(".table_content.child").find("table.ls_list");
            tableId = $tableChild.attr("title");
            url = webPath+"/sysAreaRegionRel/findByPageAjax";
        }
        var dataParam = {};
        if (!SynchronousLoadding) {
            dataParam.regionNo = regionNo;
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
function sysAreaConfInput(obj, url) {
    openThisForm(obj);
    var $formObj = $(obj).parents(".col_content").find(".form_content form");
    $formObj.attr("action", url);
    $formObj.find("input[type='text']").val("");
    $formObj.find("select").val("");
    $formObj.find("textarea]").val("");
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
//还款方式保存
function sysAreaConfSave(obj, url) {
    var ajaxUrl = $(obj).attr("action");
    var $form = $(obj);
    var dataParam = {};
    dataParam.regionName = $form.find("input[name='regionName']").val();
    if (ajaxUrl === undefined || ajaxUrl === "") {
        ajaxUrl = url;
        dataParam.regionNo = $form.find("input[name=regionNo]").val();
        dataParam.sts = $form.find("input[name='sts']").val();
    }
    if (dataParam.regionName != undefined && dataParam.regionName != "" && dataParam.regionName != null  ) {
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
                    var $tableContent = $(obj).parents(".col_content").find(".table_content.parent");
                    $tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
                    setPager($tableContent, data.ipage);
                    intiPageShow($tableContent, data.ipage);
                    $(obj).removeAttr("action");
                    initClick();
                    formExit(obj);
                } else {
                    $.myAlert.Alert(data.msg);
                }
            },
            error: function() {
                $.myAlert.Alert("保存失败！");
            }
        });
    } else {
        var msg = top.getMessage("NOT_FORM_EMPTY", "必须输入的项目");
        $.myAlert.Alert(msg);
    }
}
/**
 * 方式修改跳转
 * @param {Object} obj
 * @param {Object} url
 */
function sysAreaConfUpdate(obj, url) {
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
                $.each(data.formData ,function(name,value){
                	
                	setFormEleValue(name,value,$form);
                });
                
            } else {
                $.myAlert.Alert(data.msg);
            }
        },
        error: function() {
            $.myAlert.Alert(top.getMessage("ERROR_SELECT"));
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
    var flag = $(obj).parents(".table_content").hasClass("parent");
    $.myAlert.Confirm("是否确定删除？", "", function() {
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
                            if (datatemp !== undefined && datatemp.split("=") == "regionNo") {
                                regionNo = datatemp.split("=")[1];
                                
                            }
                            if(regionNo!=null&&regionNo!= undefined&&regionNo!=''){
                            	initAcShdMtdParmDtlLst(regionNo);
                                regionNo = '';
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
    });
}

/**还款阶段配置**/
function sysAreaRegionRelInput(obj, url) {
    if (regionNo != undefined&&regionNo!=''&&regionNo!=null) {
        openThisForm(obj);
        var $formObj = $(obj).parents(".col_content").find(".form_content.child form");
        $formObj.attr("action", url);
        $formObj.find("input[type='text']").val("");
    } else {
        $.myAlert.Alert("请选择大区,然后新增！");
    }
}

/**还款阶段的保存**/
function sysAreaRegionRelSave(obj, url) {
    var flag = "insert";
    var ajaxUrl = $(obj).attr("action");
    if (ajaxUrl === undefined || ajaxUrl == "") {
        ajaxUrl = url;
        flag = "update";
    }
    var dataParam = {};
    dataParam.regionName = $(obj).find("input[name='regionName']").val();
    dataParam.provNo=$(obj).find("input[name='provNo']").val() ;
    dataParam.provName=$(obj).find("input[name='provName']").val() ;
    dataParam.id=$(obj).find("input[name='id']").val() ;
    dataParam.regionNo = regionNo;    
    if (dataParam.provName != undefined && dataParam.provName != "" && dataParam.provName != null ) {
        dataParam = JSON.stringify(changeDateParm(dataParam));
        var tableId = $(obj).parents(".col_content").find(".table_content.child table.ls_list").attr("title");
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
                    var $tableContent = $(obj).parents(".col_content").find(".table_content.child");
                    $tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
                    setPager($tableContent, data.ipage);
                    intiPageShow($tableContent, data.ipage);
                    $(obj).removeAttr("action");
                    initClick();
                    formExit(obj);
                    $.myAlert.Alert(flag == "insert" ? top.getMessage("SUCCEED_INSERT") : top.getMessage("SUCCEED_SAVE"));
                } else {
                    $.myAlert.Alert(data.msg);
                }
            },
            error: function() {
                $.myAlert.Alert("保存失败！");
            }
        });
    } else {
        var msg = "必须输入项目不能为空！";
        $.myAlert.Alert(msg);
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


/**
 * 方式修改跳转
 * @param {Object} obj
 * @param {Object} url
 */
function sysAreaRegionRelUpdate(obj, url) {
    $.ajax({
        type: "get",
        url: url,
        success: function(data) {
            if (data.flag == "success") {
                openThisForm(obj);
                var $form = $(obj).parents(".col_content").find(".form_content form");
                $form.removeAttr("action");
                $.each(data.formData ,function(name,value){
                	setFormEleValue(name,value,$form);
                });
            } else {
                $.myAlert.Alert(data.msg);
            }
        },
        error: function() {
            $.myAlert.Alert(top.getMessage("ERROR_SELECT"));
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

function areaNameCallBack(data){
	$("input[name='provNo']").val(data.areaNo);
}
/**
 * 防止保持方法
 */
function enterKey() {}
;
function func_uior_valTypeImm(obj) {}
;
