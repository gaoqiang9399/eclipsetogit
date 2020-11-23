<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%
	String contextPath = request.getContextPath();
	Object themeObj = session==null?null:session.getAttribute("color");
	String theme = themeObj==null?"Cred":((String)themeObj);
%>
<%@ include file="../include/message.jsp"%>

<!-- 基础JS jquery -->
<script type="text/javascript" src="${webPath}/themes/menu/js/jquery-1.8.0.min.js"></script>
<!-- 基础JS json解析 --><!-- 是否移出？ -->
<script type="text/javascript" src="${webPath}/themes/menu/js/jquery.serializejson.min.js"></script>
<!-- 日期选择器  -->
<script type='text/javascript' src='${webPath}/themes/menu/js/datepopcalendar.js'></script>
<!-- jqueryUI框架CSS -->
<link href="${webPath}/UIplug/jqueryUI/smoothness/jquery.ui.theme.css" rel="stylesheet" type="text/css" />
<link href="${webPath}/UIplug/jqueryUI/smoothness/jquery.ui.core.css" rel="stylesheet" type="text/css"/>
<link href="${webPath}/UIplug/jqueryUI/smoothness/jquery.ui.dialog.css" type="text/css" rel="stylesheet" />
<link href="${webPath}/UIplug/jqueryUI/smoothness/jquery.ui.button.css" type="text/css" rel="stylesheet" />
<!-- jqueryUI框架JS -->
<script src="${webPath}/UIplug/jqueryUI/js/jquery.ui.core.js"  type="text/javascript"></script>
<script src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<!-- 主框架样式 -->
<link rel="stylesheet" href="${webPath}/themes/menu/css/main.css" type="text/css" />
<!-- 交易页面皮肤样式 -->
<link rel="stylesheet" href="${webPath}/themes/menu/theme_<%=theme%>/Css/sysUI_<%=theme%>.css" type="text/css" />
<!-- 自定义JS 基础JS -->
<script type='text/javascript' src='${webPath}/component/include/base.js'></script>
<!-- 自定义js 失去焦点校验 保存校验 -->
<script type="text/javascript" src='${webPath}/component/include/uior_val.js'> </script>
<script type="text/javascript" src='${webPath}/component/include/xcqi_cal.js'> </script>
<!-- 数据格式校验 --><!-- 待合并 -->
<script type="text/javascript" src="${webPath}/component/include/idvalidate.js"></script>
<script type='text/javascript' src='${webPath}/component/include/idCheck.js'></script>
<!-- 图标样式包    暂时引入 -->
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" type="text/css" />
<!--  -->
<script type="text/javascript" src="${webPath}/component/include/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type='text/javascript' src='${webPath}/component/include/tablelistshowdiv.js'></script>
<script type='text/javascript' src='${webPath}/component/include/improveGrid.js'></script>
<!--  -->
<link rel="stylesheet" href="${webPath}/component/include/screencore.css" type="text/css" />

<script type="text/javascript">
var appwebPath = "${webPath}";
var treeIconPath = "";
treeIconPath = "img";
$(document).ready(pageInit);
function pageInit(){
    /* form隔行换色 */
    var index = 1;
    $(".from_w > tbody > tr").each(function(){
    	if($(this).find("td.bartop").get(0) || $(this).find("td.bartitle").get(0)){
    		index = 1;
    	}else {
	    	if(index%2==0){
	    		$(this).addClass("evenRow");
	    	}
	    	index++;
    	}
    });
    var tabIndex = 1;
    $("table.ls_list").attr("cellSpacing","0");
    $("table.ls_list > tbody >tr").each(function(){
    	if($(this).find("td.bartop").get(0) || $(this).find("td.bartitle").get(0)){
    		tabIndex = 1;
    	}else {
	    	if(tabIndex%2!=0){
	    		$(this).addClass("t1");
	    	}else {
	    		$(this).addClass("t2");
	    	}
	    	tabIndex++;
    	}
    });
    /* 为列表头添加圆角 */
    $("table.ls_list").find("thead tr th").attr("nowrap", "nowrap");
    var firstHead = $("table.ls_list").find("thead tr th:first-child");
    if(!firstHead.attr("class")){
    	firstHead.addClass("first");
    }
    var lastHead = $("table.ls_list").find("thead tr th:last-child");
    if(!lastHead.attr("class")){
    	lastHead.addClass("last");
    }
    $("input:text").each(function(){
        if(!$(this).attr("class") || $(this).attr("class")==""){
    		$(this).addClass("INPUT_TEXT");
        }
    });
    
    // 选取不含有readonly属性的多行文本框
   var $textarea = $('table textarea').not('[readonly]');
    for (var i = 0; i < $textarea.length; i++) {
        var name = $($textarea[i]).attr('name'); // 每个textarea的name属性
        var maxlength = $($textarea[i]).attr('maxlength'); // 每个textarea的maxlength属性
        var $textarea_div = $("<div id='" + name + "-div' class='textarea-div'></div>"); // 包围textarea的Div节点
        $($textarea[i]).wrap($textarea_div);

        var $chars_div = $("<div id='" + name + "-charsdiv' class='chars-info'>" + maxlength / 2 + "</div>"); // 显示剩余字数的Div节点
        $($textarea[i]).after($chars_div); // 将$chars_div节点添加到textarea节点之后
    }
}
</script>
<%
	String sysDate = (String)session.getAttribute("sys_date"); 
	sysDate = "2015-12-25";
%>
<input type="hidden" name="sysDate" id="sysDate" value="<%=sysDate %>" title="营业日期"/>
<div id="screenLockDiv"></div>