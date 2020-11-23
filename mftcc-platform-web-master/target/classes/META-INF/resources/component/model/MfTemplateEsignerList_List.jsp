<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript">
        var cusNo = "${cusNo}";
        var idNum = '${idNum}';
        $(function () {
            myCustomScrollbar({
                obj: "#content", //页面内容绑定的id
                url: webPath + "/mfTemplateEsignerList/findByPageAjax?cusNo=" + cusNo, //列表数据查询的url
                tableId: "tableMfTemplateEsignerListList", //列表数据查询的table编号
                tableType: "thirdTableTag", //table所需解析标签的种类
                pageSize: 30 //加载默认行数(不填为系统默认行数)
                //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
            });
        });
    </script>
    <script type="text/javascript">


        // 批量签约
        var esignAll = function () {
            $.ajax({
                type: "post",
                url: webPath+'/mfTemplateEsignerList/findListForCor',
                data: { cusNo: cusNo},
                dataType: "json",
                success: function (data) {
                    SelectCertificateOnClick();
                    var certInfoContent = GetCertInfoOnClick();
                    if(certInfoContent.indexOf(idNum) == -1){
                        window.top.alert("证书与签章主体不匹配，请选择正确的证书！");
                        return;
                    }
                    var successFlag = true;
                    jQuery.each(data.mfTemplateEsignerLists, function(i,item){
                        var url = "/mfBusPact/getCerCodeCor?esignerNo="+item.esignerNo+"&temBizNo="+item.temBizNo+"&templateNo="+item.templateNo+"&nodeNo="+item.nodeNo;
                        top.esignFlag = true;
                        getDZQY(url);
                        if(!top.esignFlag){
                            successFlag = false;
                            window.top.alert("合同（" + item.pactNo + "）签约失败",0,function(){
                                updateTableData();
                            });
                            return false;
                        }
                    });
                    if(successFlag){
                        window.top.alert("签约成功",3,function(){
                            updateTableData();
                        });
                    }
                },
                error: function (data) {
                    console.log(data);
                }
            });
        }

    </script>
    <script type="text/javascript">
        var CryptoAgent = "";
        function OnLoad() {
            try {
                var eDiv = document.createElement("div");
                if (navigator.appName.indexOf("Internet") >= 0 || navigator.appVersion.indexOf("Trident") >= 0) {
                    if (window.navigator.cpuClass == "x86") {
                        eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Paperless.x86.cab\" classid=\"clsid:B64B695B-348D-400D-8D58-9AAB1DA5851A\" ></object>";
                    }
                    else {
                        eDiv.innerHTML = "<object id=\"CryptoAgent\" codebase=\"CryptoKit.Paperless.x64.cab\" classid=\"clsid:8BF7E683-630E-4B59-9E61-C996B671EBDF\" ></object>";
                    }
                }
                else {
                    eDiv.innerHTML = "<embed id=\"CryptoAgent\" type=\"application/npCryptoKit.Paperless.x86\" style=\"height: 0px; width: 0px\">";
                }
                document.body.appendChild(eDiv);
            }
            catch (e) {
                window.top.alert(errorDesc,0);
                return;
            }
            CryptoAgent = document.getElementById("CryptoAgent");
        }

        // Select certificate
        function SelectCertificateOnClick() {
            try {
                document.getElementById("SelectCertResult").value = "";

                var subjectDNFilter = "";
                var issuerDNFilter = "";
                var serialNumFilter = "";
                var cspName = "";
                var bSelectCertResult = "";
                // subjectDNFilter = document.getElementById("SubjectDNFilter").value;
                // issuerDNFilter = document.getElementById("IssuerDNFilter").value;
                // serialNumFilter = document.getElementById("SerialNumFilter").value;
                // cspName = document.getElementById("CSPNameFilter").value;

                bSelectCertResult = CryptoAgent.SelectCertificate(subjectDNFilter, issuerDNFilter, serialNumFilter, cspName);
                // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
                if (!bSelectCertResult) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    lwindow.top.alert(errorDesc,0);
                    return;
                }
                document.getElementById("SelectCertResult").value = bSelectCertResult;
            }

            catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                window.top.alert(errorDesc,0);
            }
        }



        // Sign hash message
        function SignOnClick() {
            var signature = "";
            sourceHashData = document.getElementById("SourceHashData").value;


            try {
                signature = CryptoAgent.SignHashMsgPKCS7Detached(sourceHashData, "SHA-1");
                if (!signature) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    window.top.alert(errorDesc,0);
                    return;
                }
                // document.getElementById("signature").value = signature;
            } catch (e) {
                window.top.alert(errorDesc,0);
            }
            return signature;
        }





        function getPdf(dateMap,signature){
            url='/mfBusPact/getPdf';
            LoadingAnimate.start();
            $.ajax({
                url:webPath+url,
                type: "POST",
                dataType:'json',
                async:false,
                data:{  templateNo:dateMap.templateNo,
                    id:dateMap.id,
                    signature:signature,
                    temBizNo:dateMap.temBizNo,
                    nodeNo:dateMap.nodeNo,
                    cusNo:dateMap.cusNo,
                },
                success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag == "success"){
                        // window.top.alert("签约成功！",3);
                    }else{
                        top.esignFlag = false;
                    }
                },error:function(data){
                    top.esignFlag = false;
                    LoadingAnimate.stop();
                }
            });
        }
        var GetCertInfoOnClick = function () {
            try {
                var InfoTypeID = "SubjectCN";
                var InfoContent = "";

                document.getElementById("CertInfoContent").value = "";

                // certificate information identifier
                // InfoTypeID = GetSelectedItemValue("InfoTypeID");

                InfoContent = CryptoAgent.GetSignCertInfo(InfoTypeID);
                // Opera浏览器，NPAPI函数执行结果为false时，不能触发异常，需要自己判断返回值。
                if (!InfoContent) {
                    var errorDesc = CryptoAgent.GetLastErrorDesc();
                    layer.alert(errorDesc,{icon:2});
                    return;
                }
                document.getElementById("CertInfoContent").value = InfoContent;
            } catch (e) {
                var errorDesc = CryptoAgent.GetLastErrorDesc();
                layer.alert(errorDesc,{icon:2});
            }
            return InfoContent;
        }
        // 授信详情
        var getDZQY = function (url) {
            LoadingAnimate.start();
            $.ajax({
                url :webPath + url,
                data : {
                },
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        document.getElementById("SourceHashData").value=data.pdfHash;
                        document.getElementById("id").value=data.id;
                        document.getElementById("templateNo").value=data.templateNo;
                        document.getElementById("temBizNo").value=data.temBizNo;
                        document.getElementById("nodeNo").value=data.nodeNo;
                        document.getElementById("cusNo").value=data.cusNo;
                        var signature = SignOnClick();
                        document.getElementById("SelectCertResult").value=signature;
                        getPdf(data,signature);
                    }else{
                        top.esignFlag = false;
                    }
                },
                error : function() {
                    top.esignFlag = false;
                    LoadingAnimate.stop();
                },
            });
        }

    </script>
</head>
<body onload="javascript:OnLoad();">
<div class="container">

    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12">
            <div class="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/产品名称"/>
            </div>
            <div id="show-form" class="btn-div margin_top_10">
                <button type="button" class="btn btn-primary" onclick="esignAll()">批量签约</button>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
        <input type="text" id="SourceHashData" style="width: 650px"  />
        <input type="text" id="SelectCertResult" style="width: 650px">
        <input type="text" id="id" style="width: 650px"  />
        <input type="text" id="templateNo" style="width: 650px"  />
        <input type="text" id="temBizNo" style="width: 650px">
        <input type="text" id="nodeNo" style="width: 650px"  />
        <input type="text" id="cusNo" style="width: 650px">
        <input type="text" id="CertInfoContent" style="width: 650px">
    </div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp" %>
<script type="text/javascript">filter_dic = [{
    "optCode": "ifEsigned",
    "optName": "签章状态",
    "parm": [
        {"optName": "待签约", "optCode": "0"},
        {"optName": "签约中", "optCode": "1"},
        { "optName": "已签约","optCode": "2"}],
    "dicType": "y_n"
}];</script>
</body>
</html>
