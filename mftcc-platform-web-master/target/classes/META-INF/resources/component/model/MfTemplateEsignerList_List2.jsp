<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>签约列表</title>
	<script type="text/javascript">
        // function findListForDZQYAjax() {
        //     // $.ajax({
        //     //     url: ctxPath + "/mfTemplateBizConfig/getEsignerBizConfigsForListAjax",
        //     //     data: {
        //     //         "nodeNo": nodeNo,
        //     //         "temBizNo": temBizNo,
        //     //     },
        //     //     type: 'post',
        //     //     dataType: 'json',
        //     //     success: function (data) {
        //     //         layer.close(layLoad);
        //     //         if (data.flag == "success") {
        //     //             $("#templateList").html(data.html);
        //     //             $("#esignTemplateDiv").show();
        //     //         }
        //     //     },
        //     //     error: function () {
        //     //         layer.close(layLoad);
        //     //         layer.alert("获取模板列表失败", {icon: 2});
        //     //     },
        //     // });
			// alert("111");
        // }

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
                //data:dataParam,
                data:{  templateNo:dateMap.templateNo,
                    id:dateMap.id,
                    signature:signature,
                    temBizNo:dateMap.temBizNo,
                    nodeNo:dateMap.nodeNo,
                    cusNo:dateMap.cusNo,
                },
                success:function(data){
                    console.log(data);
                    LoadingAnimate.stop();
                    if(data.flag == "success"){
                        // var dataParam = JSON.stringify($(data).serializeArray());
                        window.location.href=webPath + "/mfTemplateEsignerList/findListForDZQY?templateNo="+ data.templateNo+"&temBizNo="+data.temBizNo+"&nodeNo="+data.nodeNo;
                        window.top.alert(" 签约成功！",3);

                    }else{
                        window.top.alert(data.msg,0);
                    }
                },error:function(data){
                    console.log(data);
                    window.top.alert(data.msg,0);
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
            SelectCertificateOnClick();
            var certInfoContent = GetCertInfoOnClick();
            $.ajax({
                url :webPath + url,
                data : {certInfoContent:certInfoContent
                },
                type : 'post',
                dataType : 'json',
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
                        window.top.alert(data.msg,0);
                    }

                },
                error : function() {
                    window.top.alert(data.msg,0);
                    LoadingAnimate.stop();
                },
            });
        }

	</script>

</head>
<body onload="javascript:OnLoad();">
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12">
			<div id="ls_list" class="table_content" style="height: auto;">
				${tableHtml }
			</div>
		</div>
	</div>
	<input type="hidden" id="SourceHashData" style="width: 650px"  />
	<input type="hidden" id="SelectCertResult" style="width: 650px">
	<input type="hidden" id="id" style="width: 650px"  />
	<input type="hidden" id="templateNo" style="width: 650px"  />
	<input type="hidden" id="temBizNo" style="width: 650px">
	<input type="hidden" id="nodeNo" style="width: 650px"  />
	<input type="hidden" id="cusNo" style="width: 650px">
	<input type="hidden" id="CertInfoContent" style="width: 650px">
</div>

</body>
</html>