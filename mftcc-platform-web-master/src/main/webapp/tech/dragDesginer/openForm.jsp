<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String currFormId = request.getParameter("formId");
String src="dragForm.jsp";
if(null!=currFormId&&""!=currFormId.trim())
{
src="FormForToolBean/initForm?formId="+currFormId;
}

String contextpath = request.getContextPath();
 String formId = (String)request.getAttribute("formId");
 String formTitle = (String)request.getAttribute("title");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10,Chrome=1" />
    <title>表单编辑器</title>
    <!--  以后统一把相对路径  ${webPath}/    修改为绝对路径  ${webPath}/  -->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/bootstrap/css/bootstrap.min.css"/>
    <!--bootstrap-switch  样式库-->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/bootstrap-switch/css/bootstrap-switch.min.css"/>
    <!--引用font-awesome图标样式库-->
    <link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
    <link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome-animation.css" type="text/css" rel="stylesheet" />
    <!--引用自己的样式库-->
    <!--<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/css/common.css" />-->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/formIframe.css" />
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/myBootStrap.css" />
    <!--页面加载样式-->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/pace/css/pace-theme-corner-indicator.tmpl.css" />
    <!-- select样式 -->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/easydropdown/css/easydropdown.css"/>
    <!--滚动条样式-->
    <link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/customScrollbar/css/jquery.mCustomScrollbar.css">
</head>
<body style="min-width: 1300px">
<div class="navbar head" role="navigation">
    <div class="head_show" style="width: 100%">
        <div class="logo"></div>
        <div class="tab_ctrls">
            <div class="tab_ctrl title_choice" role="format"><span>格式</span></div>
            <div class="tab_ctrl title_nochoice" role="attribute"><span>属性</span></div>
            <div class="tab_ctrl title_nochoice" role="interaction"><span>交互</span></div>
        </div>
        <!--
        <div class="ctrl_btn">
            <span id="delFile" class="location delete"  title="删除"></span>
        </div>
        <div class="btn_split"></div> --><!--公式校验-->
        <div class="ctrl_btn">
            <span class="location formula_validate" title="公式校验"></span>
        </div>
        <div class="btn_split"></div><!--修改-->
        <div class="ctrl_btn">
            <span id="ModifyDescription" class="location edit" title="修改"></span>
        </div>
        <!--
        <div class="btn_split"></div>
        <div class="ctrl_btn">
            <span id="copyFile" class="location copy" title="复制"></span>
        </div>
        -->
        <div class="btn_split"></div><!--同步缓存-->
        <div class="ctrl_btn">
            <span id="refreshCache" class="location refresh" title="同步缓存"></span>
        </div>
        <!--
        <div class="btn_split"></div>
        <div class="ctrl_btn">
            <span id="addDescription"  class="location addnew" title="新增"></span>
        </div>
        -->
        <div style="display: none">
            <input id="designerType" type="hidden" value="form">
            <input id="designerId" type="hidden" value="<%=formId%>">
            <input id="designerName" type="hidden" value="<%=formTitle%>">
        </div>
        <div class="form-search-des"><!--搜索-->
            <div style="float: left;margin-left: 15px">
                <div class="btn-group">
                    <div class="showDesName" data-toggle="dropdown" class="form_or_table_choice">
                        <span style="font-weight: bold;height: 25px;margin:2px 3px 0 0;float: left">表单</span>
                        <span class="fa fa-angle-down" style="font-weight: bold;height: 25px;margin-top:5px;float: left"></span>
                    </div>
                    <!--
                    <ul role="menu" class="dropdown-menu changeList" style="">
                        <li onclick="choiceFormTable(this,'form')"><a>表单</a></li>
                        <li onclick="choiceFormTable(this,'table')"><a>列表</a></li>
                    </ul>
                     -->
                </div>
            </div>
               <!-- <input type="text" style="border:none;background: none;width: 210px;height: 25px" placeholder="Search">-->
                <div class="navbar-form navbar-left" style="float: left;margin-left: 5px" role="search">
                    <div id="form-search">
                        <div id="actionFlag" style="position: relative">
                            <%=formId%>
                        </div>
                        <div class="form-group" style="position: relative;display: none">
                            <input type="text" class="form-control search form-control-val" placeholder="Search" readonly="true" value="<%=formId%>">
                            <ul class="list form-search-ul">
                            </ul>
                        </div>
                    </div>
					<!--
                    <div id="table-search" style="display:none;">
                        <div class="form-group" style="position: relative">
                            <input type="text" class="form-control search form-control-val" placeholder="Search">
                            <ul class="list table-search-ul">
                            </ul>
                        </div>
                    </div>
                    -->
                </div>
            <span class="fa fa-search" style="float: right;margin:5px 12px 0 0;font-size: 16px;color:#a0a8af;"></span>
        </div>
    </div>
</div>

<!--ctrl dvi - start-->
<div class="modal fade" id="addDes" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close addDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabelNew">新增</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">表单号</div>
                                    <input id="addFormId" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group pull-right">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">是否显示标题</div>
                                    <div class="switch" data-on="primary" data-off="info">
                                        <input id="addFormTitleShowFlag" type="checkbox" name="onOrOff" checked />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">表单名称</div>
                                    <input id="addTitle" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="addDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default addDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addTableDes" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close addDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabelTableNew">新增</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">列表编号</div>
                                    <input id="addTableId" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">列表名称</div>
                                    <input id="addTableName" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group pull-right">
                                <div class="input-group ">
                                    <div class="input-group-addon">类&nbsp;&nbsp;&nbsp;&nbsp;型</div>
                                    <select id="addTableType" name="apIdType" title="字段类型" mustinput="0"
                                            maxlength="2"  class="dropdown ">
                                        <option value="1"  selected>显示页数</option>
                                        <option value="2">无翻转页</option>
                                        <option value="3">不显示页数</option>
                                        <option value="4">显示页数(无go)</option>
                                        <option value="5">不显示页数(无go)</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group pull-right">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">导出EXCEL</div>
                                    <div class="switch" data-on="primary" data-off="info">
                                        <input id="addTableExportExcel" type="checkbox" name="onOrOff" checked />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">跳转页链接</div>
                                    <input id="addTablePara" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="addTableDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default addDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="ModifyDes" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modifyDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">修改描述</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">表单号</div>
                                    <input id="formId" class="form-control" type="text" readonly="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group pull-right">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">是否显示标题</div>
                                    <div class="switch" data-on="primary" data-off="info">
                                        <input id="titleShowFlag" type="checkbox" name="onOrOff" checked />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">表单名称</div>
                                    <input id="title" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="modifyDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default modifyDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>
<!--table修改-->
<div class="modal fade" id="modifyTableDes" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close modifyDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabelTable">修改描述</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">列表编号</div>
                                    <input id="modifyTableId" class="form-control" type="text" readonly="true"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">列表名称</div>
                                    <input id="modifyTableName" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group pull-right">
                                <div class="input-group ">
                                    <div class="input-group-addon">类&nbsp;&nbsp;&nbsp;&nbsp;型</div>
                                    <select id="modifyTableType" name="apIdType" title="字段类型" mustinput="0"
                                            maxlength="2"  class="dropdown ">
                                        <option value="1"  selected>显示页数</option>
                                        <option value="2">无翻转页</option>
                                        <option value="3">不显示页数</option>
                                        <option value="4">显示页数(无go)</option>
                                        <option value="5">不显示页数(无go)</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group pull-right">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">导出EXCEL</div>
                                    <div class="switch" data-on="primary" data-off="info">
                                        <input id="modifyTableExportExcel" type="checkbox" name="onOrOff" checked />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">跳转页链接</div>
                                    <input id="modifyTablePageLink" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="modifyTableDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default modifyDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="copyFileDes" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close copyFileDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel1">复制</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">原表单号</div>
                                    <input id="oldFormId" class="form-control" type="text" readonly="true"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">新表单号</div>
                                    <input id="newFormId" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group pull-right">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">是否显示标题</div>
                                    <div class="switch" data-on="primary" data-off="info">
                                        <input id="newTitleShowFlag" type="checkbox" name="onOrOff" checked />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">表单名称</div>
                                    <input id="newTitle" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="copyFileDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default copyFileDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="copyTableFileDes" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close copyFileDesColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalTableLabel">复制</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">原表单号</div>
                                    <input id="oldTableId" class="form-control" type="text" readonly="true"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group text-center">
                                <div class="input-group text-center">
                                    <div class="input-group-addon">新表单号</div>
                                    <input id="newTableId" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group pull-right">
                                <div class="input-group ">
                                    <div class="input-group-addon">类&nbsp;&nbsp;&nbsp;&nbsp;型</div>
                                    <select id="newTableType" name="apIdType" title="字段类型" mustinput="0"
                                            maxlength="2"  class="dropdown ">
                                        <option value="1"  selected>显示页数</option>
                                        <option value="2">无翻转页</option>
                                        <option value="3">不显示页数</option>
                                        <option value="4">显示页数(无go)</option>
                                        <option value="5">不显示页数(无go)</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">表单名称</div>
                                    <input id="newName" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group text-center">
                                <div class="input-group text-center input_group_width">
                                    <div class="input-group-addon">跳转页链接</div>
                                    <input id="newPageLink" class="form-control" type="text"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="copyTableFileDesConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default copyFileDesColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>

<!-- Small modal -->
<div class="modal fade bs-example-modal-sm" id="delFileShow" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close delFileColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel3">删除</h4>
            </div>
            <div class="modal-body">
                <!--是否确定删除 : from01245?-->
            </div>
            <div class="modal-footer">
                <button type="button" id="delFileConfirm" class="btn btn-default btn_2_12">确定</button>
                <button type="button" class="btn btn-default delFileColse btn_2_12">取消</button>
            </div>
        </div>
    </div>
</div>
<!--ctrl dvi - end-->
<div class="modal fade" id="validateSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 660px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close validateSettingColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalTableLabel1" style="color:#5c5c5c;margin-left: 15px">公式校验</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid" style="margin-left: 0px;margin-right: 0px">
                    <div class="row">
                        <div class="col-md-4" style="padding: 0px;">
                            <div class="row btnCtrl">
                                <span style="margin-top: -15px;float: left;font-size:12px;color:#5c5c5c">公式字段:</span>
                                    <span id="validateSettingSelect" class="span_40 a_select" style="width:165px;height: 25px">
                                        <span class="span_30" style="width: 153px">
                                            <input class="input_30 blur_readonly" style="width: 153px;height: 25px" title="字段类型">
                                        </span>
                                        <a class="a_select_down a_hover" style="height: 25px">
                                            <span class="fa fa-sort-desc" style="margin: 6px 0 0 2px;"></span>
                                        </a>
                                        <ul class="showSelect" style="width: 199px;display: none;margin-top: 24px;max-height: 155px">
                                            <li value="1" selected="true">
                                                文本字段
                                            </li>
                                            <li value="2">
                                                选择域
                                            </li>
                                        </ul>
                                    </span>
                                    <span style="float: left;margin-left: 2px">
                                        <input id="valalidateInsert" type="button" class="btn btn-default btnInput" value="插入" style="padding: 0;margin: 0;line-height: 23px;height: 25px;"/>
                                    </span>
                            </div>
                            <div class="row" style="margin-top: 5px">
                                <span style="float: left;font-size:12px;color:#5c5c5c">校验公式:</span>
                                <textarea id="formulaCheckShow" rows="5" calculationShow="" style="width: 200px;font-size: 12px"></textarea>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div id="formulaCheck" class="validateSetting" style="display: block;width: 400px;border: none">
                                <!--数字键盘-->
                                <div id="keypad">
                                    <div class="btn-group">
                                        <button type="button" class="btn btnInput btn-default margin-left-1">%</button>
                                        <button type="button" class="btn btnInput btn-default">/</button>
                                        <button type="button" class="btn btnInput btn-default">*</button>
                                        <button type="button" class="btn btnInput btn-default button-x-1 pull-right">-</button>
                                        <button type="button" class="btn btnInput btn-default ">7</button>
                                        <button type="button" class="btn btnInput btn-default">8</button>
                                        <button type="button" class="btn btnInput btn-default">9</button>
                                        <button type="button" class="btn btnInput btn-default button-y-2 button-x-1 pull-right">+</button>
                                        <button type="button" class="btn btnInput btn-default ">4</button>
                                        <button type="button" class="btn btnInput btn-default">5</button>
                                        <button type="button" class="btn btnInput btn-default">6</button>
                                        <button type="button" class="btn btnInput btn-default button-y-2 button-x-1 pull-right">=</button>
                                        <button type="button" class="btn btnInput btn-default ">1</button>
                                        <button type="button" class="btn btnInput btn-default">2</button>
                                        <button type="button" class="btn btnInput btn-default">3</button>
                                        <button type="button" class="btn btnInput btn-default button-x-2">0</button>
                                        <button type="button" class="btn btnInput btn-default">.</button>
                                    </div>
                                </div>

                                <!--运算符-->
                                <div id="operator">
                                    <div class="btn-group">
                                        <button type="button" class="btn btnInput btn-default margin-left-1">&</button>
                                        <button type="button" class="btn btnInput btn-default">|</button>
                                        <button type="button" class="btn btnInput btn-default button-x-2 fond-size-bold">&larr;</button>
                                        <button type="button" class="btn btnInput btn-default">(</button>
                                        <button type="button" class="btn btnInput btn-default">)</button>
                                        <button type="button" class="btn btnInput btn-default">[</button>
                                        <button type="button" class="btn btnInput btn-default">]</button>
                                        <button type="button" class="btn btnInput btn-default">{</button>
                                        <button type="button" class="btn btnInput btn-default">}</button>
                                        <button type="button" class="btn btnInput btn-default">&le;</button>
                                        <button type="button" class="btn btnInput btn-default">&ge;</button>
                                        <button type="button" class="btn btnInput btn-default">&lt;</button>
                                        <button type="button" class="btn btnInput btn-default">&gt;</button>
                                        <button type="button" class="btn btnInput btn-default">!</button>
                                        <button type="button" class="btn btnInput btn-default">;</button>
                                        <button type="button" class="btn btnInput btn-default button-x-2" id="businessDate"><span class="fa fa-calendar-o"></span>营业日期</button>
                                        <button type="button" class="btn btn-default button-x-2" id="validateFormula"><span class="fa fa-check-circle-o"></span>公式验证</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" style="padding: 2px 0 0 2px">
                        <button type="button" id="validateSettingConfirm" class="btn btn-default" style="padding: 1px 5px">确定</button>
                        <button type="button" class="btn btn-default validateSettingColse" style="padding: 1px 5px">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--mainFrame-->
<iframe id="formIframe" name="formIframe" src="" width="100%" height="550px" frameborder="0" scrolling="none" style='background-image: url("app/common/img/Subform_bg.png");'></iframe>

<script src="${webPath}/tech/dragDesginer/sea-modules/seajs/sea.js" ></script>
<script src="${webPath}/tech/dragDesginer/sea-modules/seajs/seajs-preload.js" ></script>

<script type="text/javascript">
    // Set configuration
    seajs.config({
        base: "${webPath}/tech/dragDesginer/sea-modules/",
        alias: {
            "jquery": "jquery/jquery-1.11.2.min.js"
        },
        preload: ['jquery'],
        charset: 'utf-8'
    });
    seajs.use('${webPath}/tech/dragDesginer/app/common/js/formIframe.js');
    //解决兼容性
    seajs.use('compatibility/html5shiv.min.js');
    seajs.use('compatibility/respond.min.1.4.2.js');
</script>
<input id="contextpath" type="hidden" value="${webPath}">
</body>
</html>