<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String contextpath = request.getContextPath();
%>
<!doctype html>
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
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/myBootStrap.css"/>
<!--引用自己的样式 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/table/css/dragTableMain.css"/>
<!--引用font awesome 图标库-->
<link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet"/>
<link href="${webPath}/tech/dragDesginer/sea-modules/font-awesome/css/font-awesome-animation.css" type="text/css" rel="stylesheet"/>
<!--页面加载样式-->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/pace/css/blue/pace-theme-flash.css" />
<!-- select样式 -->
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/sea-modules/easydropdown/css/easydropdown.css"/>
<link rel="stylesheet" href="${webPath}/tech/dragDesginer/app/common/css/leaseSelect.css">
<body>
<div id="drag">
    <div class="left-nav ">
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                    <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                        aria-expanded="false" aria-controls="collapseOne">
                        <a class="collapsed menu menuClick" style="height:50px;padding-top:20px;padding-bottom:10px;">
                            <!-- <span class="fa fa-gear"></span>基本组件 -->
                             <div class="parentFrom parentFrom_little " style="top:8px;left:10px;"></div><div class="FromFont FromFontColor FromFontColorClick" style="margin-left:50px;">基本组件</div>
                        </a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse  in " aria-labelledby="headingOne">
                    <div class="list-group">
                        <table class="list-table">
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa faa-horizontal fa-square-o  "></i><span>文本描述</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2">
                                               <span dataType="label">文本描述</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-calendar faa-horizontal"></i><span>日期类型</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2">
                                                <span  dataType="date">日期类型</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-caret-square-o-down faa-horizontal"></i><span>下拉框</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2">
                                                <span dataType="select">下拉框</span>
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

                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2" style="height:100%">
                                               <input type="checkbox"><span dataType="ckeckbox">复选框</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-link faa-horizontal"></i><span>链接</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2" style="height:100%">
                                                <span dataType="link">链接</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-square faa-horizontal"></i><span>操作按钮</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2" style="height:100%">
                                                 <span dataType="button">操作按钮</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-cut faa-horizontal"></i><span>截取</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2" style="height:100%">
                                                <span dataType="cut">截取</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="single">
                                    <div class="drag clone">
                                        <div class="cloneDisplayObj faa-parent animated-hover">
                                            <i class="fa fa-question faa-horizontal"></i><span>其他元素</span>
                                        </div>
                                        <div class="reallyObj" style="display:none">
                                            <div class="leftObj tdlable  leftborder2" style="height:100%">
                                                <span dataType="other">其他元素</span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="mainContent" style="background-image:url('app/common/img/Subform_bg.png'); overflow: hidden;height: 2000px">
        <div class="setProperties"  tableId="">
            <div class="fixed_head" style="">
                <div class="attribte_head">
                    <span class="show_icon lable_name"></span>
                    <span id="tableLabel" class="showName">列名称</span>
                    <span class="span_lableName_table"  style="display: none">
                        <input class="input_110">
                    </span>
                </div>
                <div class="attribte_head">
                    <span class="show_icon file_name"></span>
                    <span  id="fieldName" class="showName">字段名称</span>
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
                            <span>宽度</span>
                            <span><input id="tableWidth" class="input_40"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>单位</span>
                            <span class="span_40 a_select" >
                                <span class="span_30" >
                                    <input id="widthType" class="input_30 blur_readonly" title="单位">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 80px;display: none;">
                                    <li selected="true" value="0">百分比</li>
                                    <li value="1">PX</li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span>参数</span>
                            <span><input id="tableTypePara" class="input_320"></span>
                        </div>
                        <div class="show_icon_position">
                            <span class="span_label">列顺序</span>
                             <span class="span_40  a_up_down">
                                <span class="span_30">
                                    <input  id="tableIndexed" class="input_30">
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
                            <span class="span_label">类型</span>
                            <span class="span_40 a_select" style="width:107px">
                                <span class="span_30" style="width: 95px">
                                    <input id="tableType" class="input_30 blur_readonly" style="width: 95px" title="类型">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 104px;display: none">
                                    <li value="0">文本框</li>
                                    <li value="1">日期型</li>
                                    <li value="2">复选框</li>
                                    <li value="3">截取</li>
                                    <li value="4">超链接</li>
                                    <li value="5">操作按钮</li>
                                    <li value="6">选项描述</li>
                                    <li value="7">树展示</li>
                                    <li value="8">无字段</li>
                                    <li value="9">文本框(可编辑)</li>
                                    <li value="10">选项描述(可编辑)</li>
                                    <li value="11">从库中选择</li>
                                    <li value="12">金额型(元)</li>
                                    <li value="13">金额型(分)</li>
                                    <li value="14">金额型(角)</li>
                                    <li value="15">金额型(万元)</li>
                                    <li value="16">金额型(亿元)</li>
                                    <li value="17">百分号％</li>
                                    <li value="18">千分号‰</li>
                                    <li value="19">万分号</li>
                                    <li value="20">时间戳</li>
                                    <li value="30">时间戳(String)</li>
                                    <li value="99">隐藏域</li>
                                </ul>
                            </span>
                        </div>
                        <div class="show_icon_position">
                            <span class="icon_select">
                                <span class="event_icon team_icon selectIcon">
                                    <span id="tableAlign" val="3" class="show_icon show_icon_event form_align_right" classm="form_align_right" title="标签对齐方式"></span>
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
                            <span class="span_label">排序标志</span>
                            <span class="span_40 a_select" style="width:107px">
                                <span class="span_30" style="width: 95px">
                                    <input id="sortType" class="input_30 blur_readonly" style="width: 95px" title="类型">
                                </span>
                                <a class="a_select_down a_hover">
                                    <span class="fa fa-sort-desc"></span>
                                </a>
                                <ul class="showSelect" style="width: 104px;display: none">
                                    <li value=""></li>
                                    <li value="0">页面级别排序</li>
                                    <li value="1">数据库级别排序</li>
                                    <li value="2">禁止排序</li>
                                </ul>
                            </span>
                        </div>
                    </div>
                    <div class="two_line">
                        <div class="split_line_vertical" style="float:left"></div>
                        <div class="show_icon_position">
                            <span>按钮不显示条件</span>
                            <span><input id="tableAuthority" type="text" class="input_80"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>浮动框显示内容</span>
                            <span><input id="tableMytitle" type="text" class="input_60"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>按钮权限标志</span>
                            <span><input id="tableButtonMark" type="text" class="input_80"></span>
                        </div>
                        <div class="show_icon_position">
                            <span>列表样式</span>
                            <span><input id="fieldStyle" type="text" class="input_110"></span>
                        </div>
                         <div class="show_icon_position">
                            <span>默认显示内容</span>
                            <span><input id="defaultShow" type="text" class="input_60"></span>
                        </div>
                         <div class="show_icon_position">
                            <span>字段换色参数</span>
                            <span><input id="colorCol" type="text" class="input_60"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--工具栏-->
        <!--<div class="mainTool">-->
            <!--&lt;!&ndash;行操作&ndash;&gt;-->
        <!--</div>-->
        <div class="whowContent">
            <div class="showHead" style="position: relative">
                <h1><!--列表名称--></h1>
                <div class="btn-group btn-group-show">
                    <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="button" aria-expanded="false"><span class="fa fa-ellipsis-h"></span>&nbsp;&nbsp;分页展示&nbsp;&nbsp;&nbsp;<span class="caret"></span></button>
                    <ul role="menu" class="dropdown-menu">
                        <li><a id="pageShowWindows">显示页数</a></li>
                        <li><a id="pageNoFilp">无翻转也</a></li>
                        <li><a id="pageNoNumber">不显示页数</a></li>
                        <li><a id="pageShowNoGo">显示页数(无go)</a></li>
                        <li><a id="pageHideNoGo">不显示页数(无go)</a></li>
                    </ul>
                </div>
            </div>
            <div class="tableShow">
            <!-- 展现 table2 -->
                <table id="table2" width="90%">
                    <colgroup>
                        <col/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <td></td>
                        </tr>
                </table>
                <div id="pageShow" style="table-layout:fixed;">
                    <div class="page_wn">
                        <ul>
                            <li>
                                每页：
                                <input name="pageSize" type="text" size="3" style="float:none;"
                                       class="listPageSize pageStyle" value="1">
                                行
                            </li>
                            <li>
                                跳转到：
                                <input name="eadis_page" class="pageStyle" type="text" style="width:30px;float:none;" size="3" value="1">
                                <input name="submitgo" type="submit" class="submitgo"
                                       value="go">
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false;">尾页</a>
                            </li>
                            <li >
                                <a href="javascript:void(0)"
                                   onclick="return false; ">>></a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">3</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">2</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">1</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; "><<</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; ">首页</a>
                            </li>
                            <li class="allRecords">总记录:<span>25</span></li>


                            <li class="allPages">总页数:<span>3</span></li>

                        </ul>
                    </div>
                </div>
                <div id="pageNoNumberShow" style="table-layout:fixed;">
                    <div class="page_wn">
                        <ul>
                            <li>
                                每页：
                                <input name="pageSize" type="text" size="3" style="float:none;"
                                       class="listPageSize pageStyle" value="1">
                                行
                            </li>
                            <li>
                                跳转到：
                                <input name="eadis_page" class="pageStyle" type="text" style="width:30px;float:none;" size="3" value="1">
                                <input name="submitgo" type="submit" class="submitgo"
                                       value="go">
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="pageShowNoGoSHow" style="table-layout:fixed;">
                    <div class="page_wn">
                        <ul>
                            <li>
                                每页：
                                <input name="pageSize" type="text" size="3" style="float:none;"
                                       class="listPageSize pageStyle" value="1">
                                行
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false;">尾页</a>
                            </li>
                            <li >
                                <a href="javascript:void(0)"
                                   onclick="return false; ">>></a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">3</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">2</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">1</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; "><<</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; ">首页</a>
                            </li>
                            <li class="allRecords">总记录:<span>25</span></li>


                            <li class="allPages">总页数:<span>3</span></li>

                        </ul>
                    </div>
                </div>
                <div id="pageHidenNoGoSHow" style="table-layout:fixed;">
                    <div class="page_wn">
                        <ul>
                            <li>
                                每页：
                                <input name="pageSize" type="text" size="3" style="float:none;"
                                       class="listPageSize pageStyle" value="1">
                                行
                            </li>
                            <li>
                                <a href="javascript:void(0)"onclick="return false;">尾页</a>
                            </li>
                            <li >
                                <a href="javascript:void(0)"
                                   onclick="return false; ">>></a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">3</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">2</a>
                            </li>
                            <li class="noMar">
                                <a class="pageC" href="javascript:void(0)"
                                   onclick="return false; ">1</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; "><<</a>
                            </li>
                            <li>
                                <a href="javascript:void(0)"
                                   onclick="return false; ">首页</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
      </div>
    </div>
</div>
<script src="${webPath}/tech/dragDesginer/sea-modules/seajs/sea.js"></script>
<script src="${webPath}/tech/dragDesginer/sea-modules/seajs/seajs-preload.js" ></script>
<script src="${webPath}/tech/dragDesginer/app/common/config/tableConfig.js"></script>
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
    seajs.use("${webPath}/tech/dragDesginer/app/table/js/dragTableMain.js");
    //解决兼容性
    seajs.use('compatibility/html5shiv.min.js');
    seajs.use('compatibility/respond.min.1.4.2.js');
     seajs.use('${webPath}/tech/dragDesginer/app/common/js/leaseSelect.js',function(auto){
        	   $("#fieldStyle").leaseSelect({
        		   data:fieldList,
        		   callback:function(p){
        		   	$("#fieldStyle").trigger("change");
        		   }
        	   });
        });
</script>
</body>
</html>

