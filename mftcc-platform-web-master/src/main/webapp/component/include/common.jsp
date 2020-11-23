<%@page import="cn.mftcc.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="message.jsp" %>
<%@ include file="pageOfficePath.jsp" %>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%
// ↓ 使用规范命名的变量。
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
%>
<script type="text/javascript">
var today = "<%=DateUtil.getDate("yyyy-MM-dd")%>";
var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
</script>
<script type="text/javascript" src="${webPath }/layout/ie8/js/es5-shim.js"></script>
<script type="text/javascript" src="${webPath }/layout/ie8/js/es5-sham.js"></script>
<!-- JSON 兼容 -->
<script type="text/javascript" src="${webPath }/layout/ie8/js/json2.js"></script>
<!-- CSS伪类兼容 -->
<script type="text/javascript" src="${webPath }/layout/ie8/js/selectivizr-min.js"></script>
<!-- CSS伪类兼容 -->
<script type="text/javascript" src="${webPath }/layout/ie8/js/jquery.pseudo.js"></script>
<!-- placeholder兼容 -->
<script type="text/javascript" src="${webPath }/component/include/jquery.placeholder.min.js"></script>

<script type="text/javascript" src="${webPath }/component/include/SysConstant.js?v=${cssJsVersion}"></script>
<%--jquery表单序列化--%>
<script type="text/javascript" src="${webPath }/themes/view/js/jquery.serializejson.min.js"></script>
<%--日期控件:
	选择月-----onClick="laydatemonth(this)"
	选择日-----onClick="fPopUpCalendarDlg()",若选择日期需要从当前日期开始，则使用fPopUpCalendarDlg({min:new Date().toLocaleDateString(),}) 
		若选择日期在当前日期之前，则使用fPopUpCalendarDlg({max:new Date().toLocaleDateString()})"
		若需要会调函数，则使用fPopUpCalendarDlg({choose:sssss})"
	选择日(时分秒)-----onClick="laydateTimer(this)"
	选择年-----onClick="laydateYear()"
	选择年月日时分秒格式------onclick="laydateTimer()",若是选择需要从当前日期开始，则使用laydateTimer({min: new Date().toLocaleDateString(),})
			若选择日期在当前日期之前，则使用laydateTimer({max:new Date().toLocaleDateString()})"
		          若需要会调函数，则使用laydateTimer({choose:sssss})"
--%>
<script type="text/javascript" src="${webPath }/component/include/laydate/laydate.dev.js"></script>
<script type="text/javascript" src="${webPath }/component/include/laydate/laydate.month.js"></script>
<script type="text/javascript" src="${webPath }/component/include/laydate/laydate.year.js"></script>
<script type="text/javascript" src="${webPath }/component/include/laydate/FlexoCalendar.js"></script>
<link rel="stylesheet" href="${webPath }/component/include/laydate/need/FlexoCalendar.css" />
<%--自动下拉菜单
	onclick="prodAutoMenu(this,url,callback)"
--%>
<script type="text/javascript" src="${webPath }/component/include/jquery.autocompleter.js"></script>
<script type="text/javascript" src="${webPath }/component/include/jquery.autocompleter.unbound.js"></script>
<link rel="stylesheet" href="${webPath }/component/include/autocompleter.css?v=${cssJsVersion}" />

<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath }/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath }/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath }/component/include/myCustomScrollbarForForm.js"></script>
<script type="text/javascript" src="${webPath }/component/include/myCustomScrollbar.js"></script>
<!-- 自定义JS 基础JS -->
<script type='text/javascript' src='${webPath }/component/include/base.js'></script>
<!-- 自定义js 失去焦点校验 保存校验 -->
<script type="text/javascript" src='${webPath }/component/include/uior_val1.js?v=${cssJsVersion}'> </script>
<script type="text/javascript" src='${webPath }/component/include/xcqi_cal.js'> </script>
<%--详情表单双击修改--%>
<script type='text/javascript' src="${webPath }/component/include/dbClick.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath }/component/include/form.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath }/component/include/format.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath }/component/include/idvalidate.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath }/component/include/freewall.js?v=${cssJsVersion}"></script>
<%--时间轴--%>
<%-- <script type="text/javascript" src="${webPath }/component/include/formline.js"></script> --%>
<script type="text/javascript" src="${webPath }/component/include/navLine.js"></script>
<%--字体图标--%>
<link rel="stylesheet" href="${webPath }/UIplug/Font-Awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="${webPath }/UIplug/Font-Awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${webPath }/UIplug/iconmoon/style.css?v=${cssJsVersion}" />
<%--bootstrap框架--%>
<script type="text/javascript" src="${webPath }/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath }/UIplug/bootstrap/css/bootstrap.min.css" />

<%--滚动样式--%>
<link rel="stylesheet" href="${webPath }/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css?v=${cssJsVersion}" type="text/css"/>
<script src="${webPath }/UIplug/userAgent/userAgent.js" language="javascript" type="text/javascript"></script>
<!-- 行政区划和行业划分 -->
<script type="text/javascript" src="${webPath }/component/include/IndustryAndArea.js"></script>
<link rel="stylesheet" href="${webPath }/component/include/mulitselector/mulitSelect.css?v=${cssJsVersion}" />
<link rel="stylesheet" href="${webPath }/component/include/leaseCheckBox.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath }/component/include/leaseCheckBox.js?v=${cssJsVersion}" ></script>
<script type="text/javascript" src="${webPath }/component/include/jquery.qtip.min.js" ></script>
<link rel="stylesheet" href="${webPath }/component/include/jquery.qtip.min.css" />
<script type="text/javascript" src="${webPath }/component/include/tablelistshowdiv.js?v=${cssJsVersion}" ></script>
<!-- 证件号码验证 -->
<script type="text/javascript" src='${webPath }/component/include/idCheck.js?v=${cssJsVersion}'> </script>
<!-- 弹层关闭的方法 -->
<script type="text/javascript" src='${webPath }/component/include/closePopUpBox.js?v=${cssJsVersion}'> </script>

<!-- ztree -->
<link rel="stylesheet" href="${webPath }/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<script type='text/javascript' src='${webPath }/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
		
<script type="text/javascript" src="${webPath }/component/include/popupSelection/popupSelection.js?v=${cssJsVersion}" ></script>
<script type="text/javascript" src="${webPath }/component/include/popupSelection/popupList.js?v=${cssJsVersion}" ></script>
<script type="text/javascript" src="${webPath }/component/include/popupSelection/selectionComponent.js?v=${cssJsVersion}" ></script>
<!--帮助组件  -->
<%-- <script type="text/javascript" src="${webPath }/component/help/sysHelp.js" ></script> --%>
<%-- <link rel="stylesheet" href="${webPath }/component/help/sysHelp.css" /> --%>

<!-- 弹窗消息模板 -->
<script type="text/javascript" src="${webPath }/component/include/getMessage.js?v=${cssJsVersion}"></script>
<!--artdialog插件  -->
<script type="text/javascript" src="${webPath }/UIplug/artDialog/dist/dialog-plus-min.js"></script>
<link id="ui-dialog" rel="stylesheet" href="${webPath }/UIplug/artDialog/css/ui-dialog${skinSuffix}.css" />
<!-- 引入自定义的弹窗扩展 -->
<script type="text/javascript" src="${webPath }/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>
 
 <!--弹窗选择公用js  -->
 <script type="text/javascript" src="${webPath }/themes/factor/js/selectInfo.js"></script>
 <!--调用规则公共js -->
 <script type="text/javascript" src="${webPath }/themes/factor/js/rulesCall.js"></script>
 <!--字符串工具类公用js  -->
 <script type="text/javascript" src="${webPath }/component/include/StringUtil.js?v=${cssJsVersion}"></script>
 <!--加载动画js  -->
 <script type="text/javascript" src="${webPath }/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
  <!--factor自定义的表单相关js  -->
 <script type="text/javascript" src="${webPath }/themes/factor/js/formFactor.js?v=${cssJsVersion}"></script>
 <!-- 手机号码和证件号码唯一性验证js -->
 <script type="text/javascript" src='${webPath }/component/include/checkUniqueVal.js?v=${cssJsVersion}'> </script>
<!-- 动态子表单依赖js -->
<script type="text/javascript" src="${webPath }/component/include/anchor.js?v=${cssJsVersion}"></script>

<!-- 苹果丽黑字体 -->
<%-- <link rel="stylesheet" href="${webPath }/themes/factor/css/fonts/ARIALN.css?v=${cssJsVersion}" /> --%>

<!--  供应链公共样式 -->
 <script rel="text/javascript" src="${webPath }/themes/factor/js/formStyle.js?v=${cssJsVersion}"></script>
 <link id="BS-factor" rel="stylesheet" href="${webPath }/themes/factor/css/BS-factor${skinSuffix}.css?v=${cssJsVersion}" />
 <link id="main-detail" rel="stylesheet" href="${webPath }/themes/factor/css/main-detail${skinSuffix}.css?v=${cssJsVersion}" />
 <link rel="stylesheet" href="${webPath }/themes/factor/css/form${skinSuffix}.css?v=${cssJsVersion}" />
 <link rel="stylesheet" href="${webPath }/themes/factor/css/rightForm.css?v=${cssJsVersion}" />
 <link id="factor" rel="stylesheet" href="${webPath }/themes/factor/css/factor${skinSuffix}.css?v=${cssJsVersion}" />
 <link id="multi-select" rel="stylesheet" href="${webPath }/themes/factor/css/multi-select${skinSuffix}.css?v=${cssJsVersion}" />
 <link id="common" rel="stylesheet" href="${webPath }/themes/factor/css/common${skinSuffix}.css?v=${cssJsVersion}" />
<!--  富文本公共样式 -->
<script type="text/javascript" src="${webPath}/component/include/ueditor/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${webPath}/component/include/ueditor/lib/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="${webPath}/component/include/ueditor/lib/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
	$(function(){
		$("body").delegate(".form-control[readonly]", "focus", function() {
			$(this).blur();
		});
	});
    <!--textarea 回车长度处理-->
    $(function(){
        var $textarea = $('table textarea').not('[readonly]');
        for (var i = 0; i < $textarea.length; i++) {
            var name = $($textarea[i]).attr('name'); // 每个textarea的name属性
            var maxLengthStr = $($textarea[i]).attr('maxlength'); // 每个textarea的maxlength属性
            if(maxLengthStr=="null" || maxLengthStr==null || maxLengthStr=='undefined' || !isNaN(maxLengthStr)){
                maxLengthStr = $($textarea[i]).attr('maxlength');
            }
            if(maxLengthStr){
                $($textarea[i]).bind('keyup',function(){
                    var maxLengthStr = $(this).attr('maxlength'); // 每个textarea的maxlength属性
                    if(maxLengthStr=="null" || maxLengthStr==null || maxLengthStr=='undefined' || !isNaN(maxLengthStr)){
                        maxLengthStr = $(this).attr('maxlength');
                    }
                    var str=$(this).val();
                    var reg=/^[1-9]*|0$/;
                    if(maxLengthStr && maxLengthStr!='undefined' && reg.test(maxLengthStr)){
                        var maxLength =parseInt(maxLengthStr, 10);
                        str = str.replace(/[\u4e00-\u9fa5]/g,'m');//将中文字符进行转化
                        str = str.replace(/[^\x00-\xff]/g,'q');//将全角字符进行转化
                        var regR = /\r/g;var regN = /\n/g;
                        str = str.replace(regR,"r").replace(regN,"nn");
                        if(str.length>maxLength){
                            var diff = str.length-maxLength;
                            var newStr = $(this).val().substring(0,$(this).val().length-diff);
                            $(this).val(newStr);
                        }
                    }
                });
            }
        }
    });

	/**
	 * 获取当前操作的ifream
	 * @param {Object} timeStamp 时间戳
	 * @param {Object} ifreamHeight 窗体高度
	 */
	window.resizeHeight = function(timeStamp,ifreamHeight,callback){
		var thisIfreamObj;
		$(window.document).find("iframe").each(function(index){
			var thisObj = $(this);
			var thisWindow = $(this).contents().find("body");
			var thisTimeStamp = $(thisWindow).find("#timeStamp").attr("timeStamp");
			if(thisTimeStamp!==undefined&&thisTimeStamp==timeStamp){
				thisIfreamObj = thisObj;
				$(thisIfreamObj).animate({height:ifreamHeight+40},500,function(){
					if(callback!==undefined){
						callback.call(this);
					}
				});
			}
		});
	}
	window.flushIfream = function(){
		window.parent.location.reload();
	}
// 	var par = $("body",parent.document);
// 	try{
// 		if(par.find(".pt-page[name=A]").length>0){
// 			getHelp();
// 		}else{
// 			parent.getHelp();
// 		}
// 	}catch(err){
// 		console.warn("当前页面或者父级页面未引用SysHelp.js文件！ --"+err);
// 	}

    //变更皮肤
    window.top.changeSystemContentSkin=function(oldskin,skin){
        $(window.top.document).find("iframe").each(function () {
            $(this.contentDocument).find("link").each(function(){
               var id = $(this).attr("id");
               if(typeof (id) !="undefined"){
                   if(id!="layDateSkin"){
                       var href =$(this).attr("href");
                       if(oldskin=="default"){
                           $(this).attr("href",href.replace(id, id+"_"+skin));
                       }else{
                           if(skin=="default"){
                               href = href.replace(id + "_" + oldskin, id);
                           }else {
                               href =href.replace(id + "_" + oldskin, id + "_" + skin);
                           }
                           $(this).attr("href", href);
                       }
                   }
               }
           });
       })

    }


   function setUEeditor(textAareas,frameHeight){
	    if(frameHeight==undefined){
            frameHeight=320; //编辑器高度，默认320
        }
	    var areas=textAareas.split("@"); //所有需要使用富文本的名称
	    for(var i=0;i<areas.length;i++){
            var textAareaName=areas[i];
            if(textAareaName=="") return false;
            $('textarea[name="'+textAareaName+'"]').removeClass(); //移除原来的样式
            $('textarea[name="'+textAareaName+'"]').attr("id",textAareaName); //在原来的属性中添加Id
            UE.delEditor(textAareaName);
            UE.getEditor(textAareaName, {
                autoHeightEnabled: false
                , enableAutoSave: false
                , autoFloatEnabled: true
                , initialFrameHeight: frameHeight
            });
           //  if(document.getElementsByName(textAareaName).length>1){
           //      alert("请注意页面出现多个字段名称是"+textAareaName+"的域,请检查!",4);
           //  }
        }
       setValueAndReadOnly(areas);
   }

   function  setValueAndReadOnly(areas){
	    if(areas.length>0){
            for(var n=0;n<1;n++){
                var textAareaName=areas[n];
                var oldVal=$('textarea[name="'+textAareaName+'"]').val(); //获取文本原来的值
                var readOnly=$('textarea[name="'+textAareaName+'"]').attr("readOnly");
                var ue=UE.getEditor(textAareaName);
                ue.ready(function () {
                    ue.setContent(decodeURIComponent(oldVal));
                    if(readOnly!=undefined) {
                        ue.setDisabled('fullscreen');
                        $("#"+textAareaName).find(".edui-editor").find(".edui-editor-toolbarbox").remove();
                        $("#"+textAareaName).find(".edui-editor").find(".edui-editor-wordcount").remove();
                    }
                    areas.splice(0,1);
                    setValueAndReadOnly(areas);
                });
            }
        }
   }


   function  UEeditorEncodeURI(dataParam,textAareas){
      var objectArray= JSON.parse(dataParam);
      var areas=textAareas.split("@");
       for(var i in areas ){
           var textAareaName=areas[i];
           if(textAareaName=="") return false;
           for(var n in objectArray){
               if(objectArray[n].name==textAareaName){
                   objectArray[n].value=encodeURIComponent(objectArray[n].value);
               }
           }
       }
       return JSON.stringify(objectArray);
   }

</script>
