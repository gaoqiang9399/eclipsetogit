<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
    <style type="text/css">
        .cover {
            cursor: default;
        }

        .infoTilte {
            margin-left: 14px;
            margin-bottom: 20px;
            margin-top: 0px;
        }

        .form-margin {
            margin-top: 20px;
            margin-left: 4px;
        }

        .set-disabled {
            color: #aaa;
        }

        .button-bac32B5CB {
            background-color: #32B5CB
        }

        .button-bac32B5CB:hover {
            color: #fff;
            background-color: #018FA7;
        }

        .button-bacFCB865 {
            background-color: #FCB865
        }

        .button-bacFCB865:hover {
            background-color: #E9AA64;
            color: #fff;
        }

        .button-bacE14A47 {
            background-color: #E14A47
        }

        .button-bacE14A47:hover {
            color: #fff;
            background-color: #C9302C
        }

        .button-his {
            margin-top: 20px;
        }

        .info-collect {
            margin-top: -30px;
        }
    </style>
    <script type="text/javascript">
        var appId = '${appId}';
        var cusNo = '${cusNo}';
        var id = '${id}';
        var pactAgenciesInitFlag = '0';
        var aloneFlag = true;
        var dataDocParm = {
            relNo: id,
            docType: "22",
            docTypeName: "非业务合同附件",
            docSplitNoArr: "[{'docSplitNo':20200611113100001}]",
            docSplitName: "附件上传",
            query: ''
        };
        $(function () {
            $("input[name='id']").val(id);
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    theme: "minimal-dark",
                    updateOnContentResize: true
                }
            });
            showDocInfo(id);
        });

        function pactAgenciesInit() {
            var templateId =$("select[name='templateId']").val();
            var agenciesId =$("select[name='agenciesId']").val();
            if(templateId!=""){
                $.ajax({
                    url:  webPath + "/mfBusPact/pactAgenceisInit",
                    data:{templateId:templateId,appId:appId,cusNo:cusNo},
                    type:'post',
                    dataType:'json',
                    success: function (data) {
                        if (data.flag == "success") {
                            $("input[name='address']").val(data.address);
                            $("input[name='legalRepresName']").val(data.legalRepresName);
                            $("input[name='client']").val(data.client);
                            $("input[name='pactName']").val(data.pactName);
                            if(templateId=='119'||templateId=='219'){
                                $.ajax({
                                    url:  webPath + "/mfBusPact/getClientAjax",
                                    data:{templateId:templateId,appId:appId,agenciesId:agenciesId},
                                    type:'post',
                                    dataType:'json',
                                    success: function (data) {
                                        if (data.flag == "success") {
                                            $("input[name='cilent']").val(data.client);
                                        }
                                    }
                                })
                            }
                        }
                    }
                });
            }
        }
        //展示要件资料
        function showDocInfo(id) {
            initAloneDocNodes(id);
            isSupportBase64 = (function () {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function () {
                    if (this.width != 1 || this.height != 1) {
                        support = false;
                    }
                };
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            })();
        };

        function ajaxSave(obj) {
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                $.ajax({
                    url: url,
                    data: {
                        ajaxData: dataParam
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.flag == "success") {
                            myclose_click();
                        } else {
                            alert(data.msg, 0);
                        }
                    },
                    error: function () {
                    }
                });
            }
        };


    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 margin_top_20">
            <div class="bootstarpTag">
                <!-- <div class="form-title"></div> -->
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="formpactExtendCredit" theme="simple" name="operform"
                      action="${webPath}/mfCusCreditApply/insertExtendAjax">
                    <dhcc:bootstarpTag property="formpactExtendCredit" mode="query"/>
                </form>

            </div>
            <%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp" %>
        </div>

    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
        <dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave('#formpactExtendCredit')"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>