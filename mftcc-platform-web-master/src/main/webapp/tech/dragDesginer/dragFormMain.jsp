<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String contextpath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <title>表单编辑器</title>
</head>

<!--  以后统一把相对路径  ${webPath}/    修改为绝对路径  ${webPath}/  -->

<!--bootstap库	-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/bootstrap/css/bootstrap.min.css"/>
<!-- drag.js样式库 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/jquery-ui/css/jquery-ui.css"/>
<!--引用font awesome 图标库-->
<link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet"/>
<link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome-animation.css" type="text/css" rel="stylesheet"/> 
<!-- select样式 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/easydropdown/css/easydropdown.css"/>
<!-- 日期框样式 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/datetimepicker/css/jquery.datetimepicker.css"/>
<!--页面加载样式-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/pace/css/blue/pace-theme-flash.css" />
<!--引用自己的样式 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/form/css/dragFormMain.css"/>
<!--更改bootstrap样式-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/myBootStrap.css"/>
<!--form配置初始化样式-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/form/css/initFormSetting.css"/>
<!--<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/customScrollbar/css/jquery.mCustomScrollbar.css">-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/customScrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/leaseSelect.css">
<body>
<div id="drag">
    <div class="left-nav">
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingThree">
                    <h4 class="panel-title collapsed" data-toggle="collapse" data-parent="#accordion"
                        href="#collapseThree"
                        aria-expanded="false" aria-controls="collapseTwo">
                        <a class="collapsed menu menuClick" style="height:50px;padding-top:20px;padding-bottom:10px;">
                           <!-- <span class="fa fa-flag"></span>标准组件-->
                           <div class="parentFrom parentFrom_little " style="top:8px;left:10px;"></div><div class="FromFont FromFontColor FromFontColorClick" style="margin-left:50px;">标准控件</div>
                        </a>
                    </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse in collapseThree" role="tabpanel" aria-labelledby="headingThree">
                    <div class="list-group">
                        <table class="list-table">
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa faa-horizontal fa-square-o  "></i><span>文本字段（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragInputGroup" >
                                            <div class="dragType" data-type="dragInputGroup">
                                                <div class="leftObj tdlable  leftborder1 dataType" data-type="dragInputGroupLeft">
                                                    输入框
                                                </div>
                                                <div class="rightObj tdvalue rightinput dataType" data-type="dragInputGroupRight">
                                                    <input type="text" class="INPUT_TEXT" value=""/>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-caret-square-o-down faa-horizontal"></i><span>选择域（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragSelectGroup" >
                                            <div class="dragType" data-type="dragSelectGroup">
                                                <div class="leftObj tdlable  leftborder1 dataType"  data-type="dragSelectGroupLeft">
                                                    选择域
                                                </div>
                                                <div class="rightObj tdvalue rightinput dataType" data-type="dragSelectGroupRight">
                                                    <select class="groupSelect">
                                                        <option value="0">身份证</option>
                                                        <option value="1">户口薄</option>
                                                        <option value="1">护照</option>
                                                        <option value="1">军官证</option>
                                                        <option value="1">士兵证</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-calendar faa-horizontal"></i><span>日期框（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragDateGroup" >
                                            <div class="dragType" data-type="dragDateGroup">
                                                <div class="leftObj tdlable  leftborder1 dataType" data-type="dragDateGroupLeft">
                                                    日期框
                                                </div>
                                                <div class="rightObj tdvalue rightinput dataType" data-type="dragDateGroupRight">
                                                    <input type="text" title="登记日期" maxlength="8" value="2014-01-03"
                                                           class="groupDate"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-list-alt faa-horizontal"></i><span>文本区域（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none"  data-type="dragTextAreaGroup">
                                            <div class="leftObj tdlable  leftborder1 dataType" data-type="dragTextAreaGroupLeft">
                                                文本区域
                                            </div>
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragTextAreaGroupRight">
                                                <textarea name="main_sour" title="文本区域" mustinput="0"
                                                          maxlength="100" dataType="0" cols="30"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-check-square-o faa-horizontal"></i><span>复选框（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none"  data-type="dragCheckboxGroup">
                                            <div class="leftObj tdlable  leftborder1 dataType" data-type="dragCheckboxGroupLeft">
                                                复选框
                                            </div>
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragCheckboxGroupRight">
                                                <input type="checkbox" value="复选框"><span>复选框</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa  fa-dot-circle-o faa-horizontal"></i><span>单选域（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none"  data-type="dragRadioGroup">
                                            <div class="leftObj tdlable  leftborder1 dataType" data-type="dragRadioGroupLeft">
                                                单选域
                                            </div>
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragRadioGroupRight">
                                                <input type="radio" value="单选域"><span>单选域</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa  fa-lock faa-horizontal"></i><span>密码域（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none"  data-type="dragPassWordGroup">
                                            <div class="leftObj tdlable  leftborder1 dataType" data-type="dragPassWordGroupLeft">
                                                密码域
                                            </div>
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragPassWordGroupRight">
                                                <input type="password" class="INPUT_TEXT" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="single">
                                    <div ondblclick="consoleAdd()">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-minus-square-o faa-horizontal"></i><span>隐藏域</span>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-question faa-horizontal"></i><span>其他元素（组合）</span>
                                        </div>
                                        <div class="reallyObj" style="display:none"  data-type="dragOtherGroup">
                                            <div class="leftObj tdlable  leftborder1 dataType" data-type="dragOtherGroupLeft">
                                                其他元素
                                            </div>
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragOtherGroupRight">
                                                <input class="INPUT_TEXT" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <!--基本组件 -->
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                        aria-expanded="false" aria-controls="collapseOne">
                        <a class="collapsed  menu">
                            <!--<span class="fa fa-gear"></span>基本组件-->
                            <div class="parentFrom BasicControl"></div><div class="FromFont FromFontColor">基本控件</div>
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse collapseOne" aria-labelledby="headingOne">
                    <div class="list-group">
                        <table class="list-table">
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa faa-horizontal fa-square-o"></i><span>文本字段</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragLable">
                                            <div class="leftObj tdlable  leftborder1 data-type" data-type="dragLable">
                                                标签
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa faa-horizontal fa-square-o "></i><span>输入框</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragInput">
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragInput">
                                                <input type="text" class="INPUT_TEXT" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-caret-square-o-down faa-horizontal"></i><span>选择域</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragSelect">
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragSelect">
                                                <select class="baseSelect">
                                                    <option value="0">身份证</option>
                                                    <option value="1">户口薄</option>
                                                    <option value="1">护照</option>
                                                    <option value="1">军官证</option>
                                                    <option value="1">士兵证</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-calendar faa-horizontal"></i><span>日期框</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragDate">
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragDate">
                                                <input type="text" title="登记日期" maxlength="8" value="2014-01-03"
                                                       class="baseDate"/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-list-alt faa-horizontal"></i><span>文本区域</span>
                                        </div>

                                        <div class="reallyObj" style="display:none" data-type="dragTextArea">
                                            <div class="rightObj tdvalue rightinput" data-type="dragTextArea">
                                                <textarea name="main_sour" title="文本区域" mustinput="0"
                                                          maxlength="100" dataType="0"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-check-square-o faa-horizontal"></i><span>复选框</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragCheckbox">
                                            <div class="rightObj tdvalue rightinput" data-type="dragCheckbox">
                                               <input type="checkbox" value="复选框"><span>复选框</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa  fa-dot-circle-o faa-horizontal"></i><span>单选域</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragRadio">
                                            <div class="rightObj tdvalue rightinput" data-type="dragRadio">
                                                <input type="radio" value="单选域"><span>单选域</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa  fa-lock faa-horizontal"></i><span>密码域</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragPassWord">
                                            <div class="rightObj tdvalue rightinput dataType" data-type="dragPassWord">
                                                <input type="password" class="INPUT_TEXT" value=""/>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo">
                    <h4 class="panel-title collapsed" data-toggle="collapse" data-parent="#accordion"
                        href="#collapseTwo"
                        aria-expanded="false" aria-controls="collapseTwo">
                        <a class="collapsed  menu">
                           <!-- <span class="fa fa-gears"></span>特殊组件-->
                           <div class="parentFrom CustomizeControl"></div><div class="FromFont FromFontColor">特殊控件</div>
                        </a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                    <div class="list-group">
                        <table class="list-table">
                            <tr class="collapseTwo">
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-header faa-horizontal"></i><span>标题栏</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragBartop" >
                                            <div class="leftObj tdvalue dataType" data-type="dragBartop">
                                                <div class="bartop bartop1 tdlable">标题栏</div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr class="collapseOne">
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-angle-left faa-horizontal"></i><span>锚点开始</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragAnchorStart" >
                                            <div class="leftObj tdvalue dataType" data-type="dragAnchorStart">
                                                <div class="bartop bartop1 tdlable">锚点开始</div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr class="collapseOne">
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-code faa-horizontal"></i><span>锚点结束</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragAnchorEnd" >
                                            <div class="leftObj tdvalue dataType" data-type="dragAnchorEnd">
                                                <div class="bartop bartop1 tdlable">锚点结束</div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFour">
                    <h4 class="panel-title collapsed" data-toggle="collapse" data-parent="#accordion"
                        href="#collapseFour"
                        aria-expanded="false" aria-controls="collapseFour">
                        <a class="collapsed  menu">
                            <!--<span class="i i-bi-square-o"></span>自定义组件-->
                            <div class="parentFrom SpecialControl"></div><div class="FromFont1 FromFontColor">自定义控件</div>
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse collapseFour" role="tabpanel" aria-labelledby="headingFour">
                    <div class="list-group">
                        <table class="list-table">
                            <!--<tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa faa-horizontal fa-square-o"></i><span>自定义模板</span>
                                        </div>
                                        <div class="reallyObj" style="display:none" data-type="dragCustom" >
                                            <div class="dragType" data-type="dragCustom">
                                                <div class="leftObj tdlable  leftborder1 dataType" data-type="dragCustom">
                                                    自定义模板
                                                </div>
                                                <div class="rightObj tdvalue rightinput dataType" data-type="dragCustom">
                                                    <input type="text" class="INPUT_TEXT" value=""/>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </td>
                            </tr>-->
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="mainContent" style="background-image:url('app/common/img/Subform_bg.png'); overflow: hidden">
        <div class="setProperties"  formActiveId="">
            <div class="fixed_head" style="">
                <div class="attribte_head">
                    <span class="show_icon lable_name"></span>
                    <span id="labelName" class="showName">标签名</span>
                    <span class="span_lableName"  style="display: none">
                        <input class="input_110">
                    </span>
                </div>
                <div class="attribte_head">
                    <span class="show_icon file_name"></span>
                    <span  id="fieldName" class="showName">字段名</span>
                    <span class="span_filedName" style="display: none">
                        <input class="input_110">
                    </span>
                </div>
            </div>
            <div class="float_head">
                <div class="format open_tab">
                    <div style="float: none;height: 32px">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span class="show_icon event_icon add_row" onclick="crtlRow('insert')" title="添加行"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="show_icon event_icon add_cell" onclick="crtlCol('insert')" title="添加列"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="show_icon event_icon del_row" onclick="crtlRow('delete')" title="删除行"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="show_icon event_icon del_cell" onclick="crtlCol('delete')" title="删除列"></span>
                        </div>
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span>标签宽</span>
                            <span><input id="labelWidth" class="input_40"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>字段宽</span>
                            <span><input id="fieldWidth" class="input_40"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">文本域行数</span>
                             <span class="span_40  a_up_down">
                                <span class="span_30">
                                    <input  id="textAreaFieldRows" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span class="span_label">标签行次</span>
                           <span class="span_40 a_up_down">
                                <span class="span_30">
                                    <input id="row" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">标签列次</span>
                             <span class="span_40 a_up_down">
                                <span class="span_30">
                                    <input id="labelCol" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">字段列次</span>
                            <span class="span_40 a_up_down">
                                <span class="span_30">
                                    <input id="fieldCol" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">显示方向</span>
                            <span style="width:72px" class="span_40 a_select">
                                <span style="width: 60px" class="span_30">
                                    <input title="显示方向" style="width: 60px" class="input_30 blur_readonly" id="horVer">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul style="width: 69px;display: none" class="showSelect">
                                    <li selected="true" value="0">
                                        横向显示
                                    </li>
                                    <li value="1">
                                        纵向显示
                                    </li>

                                </ul>
                            </span>
                        </div>
                    </div>
                    <div class="two_line">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span class="icon_select">
                                <span class="event_icon team_icon selectIcon">
                                    <span id="labelAlign" val="3" class="show_icon show_icon_event form_align_right" classm="form_align_right" title="标签对齐方式"></span>
                                    <span class="fa fa-sort-desc"></span>
                                </span>
                                 <ul class="showSelectIcon" style="width: 120px;height: 101px;display: none">
                                    <li val="1" classm="form_align_left" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_left"></span>
                                        </span>
                                        <span class="selectlabel">左对齐</span>
                                     </li>
                                    <li val="2"  classm="form_align_center" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_center"></span>
                                        </span>
                                        <span class="selectlabel">居中</span>
                                    </li>
                                    <li val="3" classm="form_align_right" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_right"></span>
                                        </span>
                                        <span class="selectlabel">右对齐</span>
                                    </li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="icon_select">
                                <span class="event_icon team_icon selectIcon">
                                    <span id="fieldAlign" val="1" class="show_icon show_icon_event form_align_left" classm="form_align_left" title="字段对齐方式"></span>
                                    <span class="fa fa-sort-desc"></span>
                                </span>
                                 <ul class="showSelectIcon" style="width: 120px;height: 101px;display: none">
                                     <li val="1" classm="form_align_left" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_left"></span>
                                        </span>
                                         <span class="selectlabel">左对齐</span>
                                     </li>
                                     <li val="2" classm="form_align_center" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_center"></span>
                                        </span>
                                         <span class="selectlabel">居中</span>
                                     </li>
                                     <li val="3" classm="form_align_right" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon form_align_right"></span>
                                        </span>
                                         <span class="selectlabel">右对齐</span>
                                     </li>
                                 </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="icon_select">
                                <span class="event_icon team_icon selectIcon">
                                    <span id="selRange" val="0" class="show_icon show_icon_event selRange_all" classm="selRange_all" title="定义范围"></span>
                                    <span class="fa fa-sort-desc"></span>
                                </span>
                                <ul class="showSelectIcon" style="width: 120px;height: 101px;display: none">
                                    <li val="0" classm="selRange_all" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon selRange_all"></span>
                                        </span>
                                        <span class="selectlabel">全部显示</span>
                                     </li>
                                    <li val="2" classm="selRange_left" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon selRange_left"></span>
                                        </span>
                                        <span class="selectlabel">隐藏标签</span>
                                    </li>
                                    <li val="1" classm="selRange_right" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon selRange_right"></span>
                                        </span>
                                        <span class="selectlabel">隐藏字段</span>
                                    </li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="icon_select">
                                <span class="event_icon team_icon selectIcon">
                                    <span id="labelDirect" class="show_icon show_icon_event labelDirect_row" val="0" classm="labelDirect_row" title="文字方向"></span>
                                    <span class="fa fa-sort-desc"></span>
                                </span>
                                <ul class="showSelectIcon" style="width: 120px;height: 68px;display: none">
                                    <li val="0" classm="labelDirect_row" style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon labelDirect_row"></span>
                                        </span>
                                        <span class="selectlabel">横向</span>
                                     </li>
                                    <li val="1" classm="labelDirect_cell"  style="width: 118px;">
                                        <span class="iconlable">
                                            <span class="show_icon labelDirect_cell"></span>
                                        </span>
                                        <span class="selectlabel">纵向</span>
                                    </li>
                                </ul>
                            </span>
                        </div>
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span class="span_label">标签跨行</span>
                            <span class="span_40  a_up_down">
                                <span class="span_30">
                                    <input id="rowSpan" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">标签跨列</span>
                             <span class="span_40  a_up_down">
                                <span class="span_30">
                                    <input id="labelColSpan" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">字段跨列</span>
                             <span class="span_40  a_up_down">
                                <span class="span_30">
                                    <input id="fieldColSpan" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span>标签样式</span>
                            <span><input id="labelStyle" type="text" class="input_120"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>字段样式</span>
                            <span><input id="fieldStyle" type="text" class="input_120"></span>
                        </div>
                    </div>
                </div>
                <div class="attribute colse_tab">
                    <div style="float: none;height: 32px">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span>初值</span>
                            <span><input id="initValue" type="text" class="input_40"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>单位</span>
                            <span><input id="unit" class="input_40"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>尺寸/选项</span>
                            <span><input id="fieldSize" class="input_70"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>参数</span>
                            <span><input id="para" class="input_70"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">最大长度</span>
                            <span class="span_40 a_up_down">
                                <span class="span_30">
                                    <input  id="maxLength" class="input_30">
                                </span>
                                <a class="a_up a_hover">
                                    <span class="fa fa-sort-asc"></span>
                                </a>
                                <a class="a_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">字段类型</span>
                            <span class="span_40 a_select" style="width:107px">
                                <span class="span_30" style="width: 95px">
                                    <input id="fieldType" class="input_30 blur_readonly" style="width: 95px" title="字段类型">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 104px;display: none">
                                    <li value="1" selected="true">
                                         文本字段
                                     </li>
                                    <li value="2">
                                        选择域
                                    </li>
                                    <li value="21">
                                        选择域(可选)
                                    </li>
                                    <li value="22">
                                        选择域(手工)
                                    </li>
                                    <li value="3">
                                        复选域
                                    </li>
                                    <li value="31">
                                        自定义复选域
                                    </li>
                                    <li value="32">
                                        从库中复选域
                                    </li>
                                    <li value="4">
                                        单选域
                                    </li>
                                    <li value="41">
                                        单选域(手工)
                                    </li>
                                    <li value="5">
                                        文本域
                                    </li>
                                    <li value="6">
                                        密码域
                                    </li>
                                    <li value="7">
                                        从库中选择
                                    </li>
                                    <li value="33">
                                        从库中选择(可选)
                                    </li>
                                    <li value="81">
                                        自定义选择
                                    </li>
                                    <li value="8">
                                        自定义选择(可选)
                                    </li>
                                    <li value="9">
                                        文件
                                    </li>
                                    <li value="90">备用字段</li>
                                    <li value="91">
                       				座机（区号-主机号）
                                    </li>
                                   <li value="92">
                                   	座机（区号-主机号-分机号）
                                    </li>
                                    <li value="99">
                                        隐藏域
                                    </li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">数据类型</span>
                            <span class="span_40 a_select" style="width:72px">
                                <span class="span_30" style="width: 60px">
                                    <input  id="dataType" class="input_30 blur_readonly" style="width: 60px" title="数据类型">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 69px;display: none">
                                    <li value="0" selected>
                                        String
                                    </li>
                                    <li value="1">
                                        Int
                                    </li>
                                    <li value="2">
                                        Long
                                    </li>
                                    <li value="3">
                                        Double(2)
                                    </li>
                                    <li value="4">
                                        Float
                                    </li>
                                    <li value="5">
                                        Boolean
                                    </li>
                                    <li value="6">
                                        Date
                                    </li>
                                    <li value="8">
                                        Double(4)
                                    </li>
                                    <li value="9">
                                        Double(6)
                                    </li>
                                    <li value="12">
                                        金额(元)
                                    </li>
                                    <li value="13">
                                        金额(分)
                                    </li>
                                    <li value="14">
                                        金额(角)
                                    </li>
                                    <li value="15">
                                        金额(万元)
                                    </li>
                                    <li value="16">
                                        金额(亿元)
                                    </li>
                                    <li value="17">
                                        百分号%
                                    </li>
                                    <li value="18">
                                        千分号‰
                                    </li>
                                    <li value="19"><!--&#8241 为万分号 ?-->
                                        万分号&#8241;
                                    </li>
                                    <li value="20">
                                        时间戳
                                    </li>
                                </ul>
                            </span>
                        </div>
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span id="readonly" class="show_icon event_icon readonly-unchoice yes_no_btn" choice="readonly" val="0" title="只读"></span>
                        </div>
                        <div class="show_icon_position">
                            <span  id="mustInput" class="show_icon event_icon mandatory-unchoice yes_no_btn" choice="mandatory" val="0" title="必填"></span>
                        </div>
                        <!--<div class="show_icon_position">
                            <span id="writeAuth" class="show_icon event_icon fa fa-edit yes_no_btn writeAuth-unchoice" choice="writeAuth" val="0" title="是否可写"></span>
                        </div>-->
                       <!-- <div class="show_icon_position">
                            <span class="show_icon parameter"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="show_icon fields_title"></span>
                        </div>-->
                    </div>
                    <div class="two_line">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span>标签链接</span>
                            <span><input id="labelLink" type="text" class="input_260"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>按钮链接</span>
                            <span><input id="buttonCondition" type="text" class="input_260"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>字段说明</span>
                            <span><input id="alt" type="text" class="input_120"></span>
                        </div>
                         <div class="show_icon_position">
                            <span>可编辑角色</span>
                            <span><input id="writeAuth" type="text" class="input_120"></span>
                        </div>
                    </div>
                </div>
                <div class="interaction colse_tab">
                    <div style="float: none;height: 32px">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                         <span class="span_40 a_select" style="width:72px">
                                <span class="span_30" style="width: 60px">
                                    <input  id="calculationType" class="input_30 blur_readonly" style="width: 60px" title="数据类型">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 69px;display: none">
                                    <li value="onBlur" selected>
                                       onBlur
                                    </li>
                                    <li value="onClick">
                                        onClick
                                    </li>
                                    <li value="onFocus">
                                        onFocus
                                    </li>
                                     <li value="onChange">
                                         onChange
                                     </li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="event_icon calculation_btn" style="display: block;float: left">
                                <span id="calculationVal" class="show_icon calculation" val="" calculationShow="" style="float: left"></span>
                                <span class="span_label margin-top-5">联动计算</span>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span>截取保留长度</span>
                            <span><input type="text" class="input_120" id="cutOff"></span>
                        </div>
                    </div>
                    <div class="two_line">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position eventConfigurations">
                            <span class="eventConfigurationShow">
                                <span class="show_icon event_set float_left"></span>
                                <span class="span_label margin-top-5">事件配置</span>
                            </span>
                            <span class="span_label margin-top-5-bold">{</span>
                            <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onclick" class="event_name" roles="onClick">onClick</span>
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onfocus" class="event_name" roles="onFocus">onFocus</span><!--获得焦点-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onblur" class="event_name" roles="onBlur">onBlur</span><!--离开焦点-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onkeyup" class="event_name" roles="onKeyup">onKeyup</span><!--键盘松开-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onkeydown" class="event_name" roles="onKeydown">onKeydown</span><!--键盘按下-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onchange" class="event_name" roles="onChange">onChange</span><!--内容变更-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onkeypress" class="event_name" roles="onKeypress">onKeypress</span><!--鼠标经过-->
                            </span>
                             <span class="float_left event event_icon margin-top-5 padding-left-right transparent_border" style="display: none">
                                <span class="fa fa-circle float_left"></span>
                                <span id="onmousedown" class="event_name" roles="onMousedown">onMousedown</span><!--鼠标按下-->
                            </span>
                            <span class="show_icon add_event float_left event_icon eventConfiguration_btn" title="新增事件"></span>
                            <span class="span_label margin-top-5-bold">}</span>
                        </div>

                </div>
                </div>
            </div>
        </div>
        <div style="">
            <!--删除添加行列-->
            <div id="delColCtrl">
            </div>
            <!--列表属性配置-->
            <!-- table2 -->
            <div class="whowContent">
                <div class="showHead">
                    <h1><!--表单名称：--></h1>
                </div>
                <div class="tableShow" style="position: relative">
                    <table id="table2" cellspacing="0px" cellpadding="0px" width="98%">
                        <colgroup>
                            <col>
                            <col>
                            <col>
                            <col>
                            <col>
                            <col>
                        </colgroup>
                        <tbody>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                    <div id="delRowCtrl" style="width: 10px;float: right;margin-right:2px; position: relative"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="linkageCalculation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 660px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close linkageCalculationColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalTableLabel" style="color:#5c5c5c;margin-left: 15px">联动计算</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" style="margin-left: 0px;margin-right: 0px">
                        <div class="row">
                            <div class="col-md-4" style="padding: 0px;">
                                <div class="row btnCtrl">
                                    <span style="margin-top: -15px;float: left;font-size:12px;color:#5c5c5c">公式字段:</span>
                                    <span id="calculationInsertSelect" class="span_40 a_select" style="width:165px;height: 25px">
                                        <span class="span_30" style="width: 153px">
                                            <input class="input_30 blur_readonly" style="width: 153px;height: 25px" title="字段类型">
                                        </span>
                                        <a class="a_select_down a_hover" style="height: 25px">
                                            <span class="fa fa-sort-desc" style="margin: 6px 0 0 2px;"></span>
                                        </a>
                                        <ul class="showSelect" style="width: 199px;display: none;margin-top: 24px;max-height:155px">
                                            <li value="textfiled">
                                                 文本字段(textfiled)
                                             </li>
                                            <li value="testarea">
                                                选择域(testarea)
                                            </li>
                                        </ul>
                                    </span>
                                    <span style="float: left;margin-left: 2px">
                                        <button type="button" id="calculationInsert" class="btn btn-default btnInput" style="padding: 0;margin: 0;line-height: 23px;height: 25px;">插入</button>
                                    </span>
                                </div>
                                <div class="row" style="margin-top: 5px">
                                    <span style="float: left;font-size:12px;color:#5c5c5c">联动公式计算:</span>
                                    <textarea id="calculation" rows="5" calculationShow="" style="width: 200px;font-size: 12px;resize: none"></textarea>
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div id="calculationSetVal" class="validateSetting" style="display: block;width: 400px;border: none">
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
                            <button type="button" id="linkageCalculationConfirm" class="btn btn-default" style="padding: 1px 5px">确定</button>
                            <button type="button" class="btn btn-default linkageCalculationColse" style="padding: 1px 5px">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="eventConfiguration" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 350px;height: 300px">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close eventConfigurationColse"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="eventModalTableLabel" style="color:#5c5c5c;padding-left:15px ">事件配置</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" style="margin-left: 0px;margin-right: 0px">
                        <div class="row">
                            <div class="col-md-12" style="padding: 0px;">
                                <div class="row">
                                    <span style="margin-top: -15px;float: left;font-size:12px;color:#5c5c5c;padding: 5px 0">事件类型:</span>
                                <span class="span_40 a_select" style="width:280px;height: 25px">
                                    <span class="span_30" style="width: 268px">
                                        <input class="input_30 blur_readonly" style="width: 268px;height: 25px" title="字段类型">
                                    </span>
                                    <a class="a_select_down a_hover" style="height: 25px">
                                        <span class="fa fa-sort-desc" style="margin: 6px 0 0 2px;"></span>
                                    </a>
                                    <ul class="showSelect" style="width: 279px;display: none;margin-top: 24px">
                                        <li value="onClick" selected="true">
                                             点击事件(onClick)
                                         </li>
                                        <li value="onFocus">
                                            获得焦点(onFocus)
                                        </li>
                                        <li value="onBlur">
                                            焦点离开(onBlur)
                                        </li>
                                        <li value="onKeyup">
                                            键盘松开(onKeyup)
                                        </li>
                                         <li value="onKeydown">
                                            键盘按下(onKeydown）
                                        </li>
                                        <li value="onChange">
                                            内容变更(onChange)
                                        </li>
                                        <li value="onKeypress">
                                            鼠标经过(onKeypress)
                                        </li>
                                        <li value="onMousedown">
                                            鼠标按下(onMousedown)
                                        </li>
                                    </ul>
                                </span>
                                </div>
                                <div class="row" style="margin-top: 5px">
                                    <span style="float: left;font-size:12px;color:#5c5c5c;padding: 5px 0">执行代码:</span>
                                    <textarea id="eventConfigurationShow" rows="5" calculationShow="" style="width: 280px;resize:none;font-size: 12px;resize: none"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer" style="padding: 2px 0 0 2px">
                            <button type="button" id="eventConfigurationConfirm" class="btn btn-default" style="padding: 1px 5px">确定</button>
                            <button type="button" class="btn btn-default eventConfigurationColse" style="padding: 1px 5px">取消</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--控制台-->
<div class="console">
    <div class="console-box">
        <div class="spin-icon">
            <!--<i class="fa fa-eye faa-pulse animated  "></i>-->
            <i class="fa fa-eye faa-flash animated-hover " title="控制台"></i>
        </div>
        <div class="console-setting">
            <div class="title">控制台</div>

            <ul class="todo-list" >
               <!-- <li class="border-left-blue">
                    <!-- Todo Text -->
                  <!--  <label class="text">隐藏域:父页面值(parNames)</label>
                    <div class="tools" onclick="consoleDelete(this)">
                        <b sign="trash" class="cC0 nui-ico nui-ico-smartTrash">
                            <b class="nui-ico nui-ico-smartTrash-head"></b>
                            <b class="nui-ico nui-ico-smartTrash-body"></b>
                        </b>
                    </div>
                </li>
                <li class="border-left-purple">
                    <label class="text">隐藏域:子页面值(popNames)</label>

                    <div class="tools" onclick="consoleDelete(this)">
                        <b sign="trash" class="cC0 nui-ico nui-ico-smartTrash">
                            <b class="nui-ico nui-ico-smartTrash-head"></b>
                            <b class="nui-ico nui-ico-smartTrash-body"></b>
                        </b>
                    </div>
                </li>
                <li class="border-left-orange">
                    <label class="text">隐藏域:机构号(brNo)</label>

                    <div class="tools" onclick="consoleDelete(this)">
                        <b sign="trash" class="cC0 nui-ico nui-ico-smartTrash">
                            <b class="nui-ico nui-ico-smartTrash-head"></b>
                            <b class="nui-ico nui-ico-smartTrash-body"></b>
                        </b>
                    </div>
                </li>-->
            </ul>
            <div class="bottom"></div>
        </div>
    </div>
</div>
<!--右键提示对话框-->
<div id="rightPrompt" class="myDragDiv">
    <div class="btn-group">
        <span class="btn btn-default  dropdown-toggle" data-toggle="dropdown" style="background-color: #428bca">
            操作面板
        </span>
    </div>
    <div class="btn-group">
        <button type="button" class="btn btn-default" onclick="delTdContent()">
            删除字段
        </button>
        <button type="button" class="btn btn-default" onclick="setHidden()">
            设置为隐藏域
        </button>
        <button type="button" class="btn btn-default" onclick="addModelTool()">
            加入模板
        </button>
        <button type="button" class="btn btn-default" onclick="addCurrentRow()">
            当前行插入行
        </button>
        <button type="button" class="btn btn-default" onclick="delCurrentRow()">
            删除当前行
        </button>
        <button type="button" class="btn btn-default" onclick="addCurrentCol()">
            当前列插入列
        </button>
        <button type="button" class="btn btn-default" onclick="delCurrentCol()">
            删除当前列
        </button>
    </div>
</div>
<!--ctrl dvi - end-->
    <script src="${webPath}/tech/dragDesginer/sea-modules/seajs/sea.js"></script>
    <script src="${webPath}/tech/dragDesginer/sea-modules/seajs/seajs-preload.js"></script>
    <script src="${webPath}/tech/dragDesginer/app/common/config/formConfig.js"></script>
    <script>
        // Set configuration
        seajs.config({
            base: "${webPath}/tech/dragDesginer/sea-modules/",
            alias: {
                "jquery": "jquery/jquery-1.11.2.min.js"
            },
            preload: ['jquery'],
            charset: 'utf-8'
        });
       seajs.use("${webPath}/tech/dragDesginer/app/form/js/dragFormMain.js");
        //解决兼容性
        seajs.use('compatibility/html5shiv.min.js');
        seajs.use('compatibility/respond.min.1.4.2.js');
        seajs.use('${webPath}/tech/dragDesginer/app/common/js/leaseSelect.js',function(auto){
        	   $("#labelStyle").leaseSelect({
        		   data:labelList,
        		   callback:function(p){
        				$("#labelStyle").trigger("change");
        		   }
        	   });
        	   $("#fieldStyle").leaseSelect({
        		   data:fieldList,
        		   callback:function(p){
        				$("#fieldStyle").trigger("change");
        		   }
        	   });
        	   $("#eventConfigurationShow").leaseSelect({
        		   data:eventList,
        		   callback:function(p){
        				$("#fieldStyle").trigger("change");
        		   }
        	   });
        });
    </script>

</body>
</html>