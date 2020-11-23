<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveConfig.js"></script>
<script type="text/javascript">
    var cusNo = "${cusNo}";
    $(function () {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                updateOnContentResize : true
            }
        })
    })

    //信息确认
    function save(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                type : "POST",
                data:{ajaxData:JSON.stringify($(obj).serializeArray())},
                url : "${webPath}/archiveConfig/insertConfig",
                dataType : "json",
                success : function(data) {
                    LoadingAnimate.stop();
                    if(data.flag=="success"){
                        window.top.alert(data.msg,1);
                        myclose_click();
                    }else{
                        window.top.alert(data.msg,0);
                    }
                }
            });
        }
    }

    function archiveInfoCallBack(archiveInfo){
        $("input[name=relationNo]").val(archiveInfo.ids);
        $("input[name=archiveName]").val(archiveInfo.names);
        $("input[name=archiveDesc]").val(archiveInfo.descs);
    };

    function closeWindow(){
        myclose_click();
    };
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-title"></div>
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/archiveConfig/insertConfig">
                    <dhcc:bootstarpTag property="formarchiveconfiginsert" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="save('#insertForm');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
