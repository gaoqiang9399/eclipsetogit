var parems = {
    "id": "",
    "projectId": "",
    "creditAppId": "",
    "projectExplain": "",
    "projectImgs": [],
    "enterpriseHistory": "",
    "companyId": "",
    "companyName": "",
    "ownershipStructure": "",
    "contributive": [],
    "ownershipStructureImgs": [],
    "isIllegality": 0,
    "reportSharePledgeVo": [],
    "stockRights": "",
    "reportPunishVo": [],
    "administrativeSanction": "",
    "reportLegalProceedingsVo": [],
    "legalProceedings": "",
    "isDiscredit": 0,
    "discredit": "", //失信记录
    "realPersonCreditRecord": "", //征信记录说明
    "realPersonCreditRecordFile": [], //征信附件
    "reportFile":[],  //项目要素附件
    "creditFile":[],  //增信措施附件
    "businessDetails": "",
    "businessDetailsImg": [],
    "supplyChainStatus": "",
    "supplyChainStatusImg": [],
    "accountYear": "",
    "accountMonth": "",
    "monetary": [],
    "receivable": [],
    "accounts": [],
    "prepaid": [],
    "otherPay": [],
    "fixedSsets": [],
    "longInvestment": [],
    "receivables": [],
    "apportioned": [],
    "accountsPay": [],
    "shortYermBorrowing": [],
    "notesPay": [],
    "monetarySize": "",
    "receivableSize": "",
    "accountsSize": "",
    "prepaidSize": "",
    "otherPaySize": "",
    "fixedSsetsSize": "",
    "longInvestmentSize": "",
    "receivablesSize": "",
    "apportionedSize": "",
    "accountsPaySize": "",
    "shortYermBorrowingSize": "",
    "notesPaySize": "",
    "reportFinancialIndexWordVo1":[],
    "reportFinancialIndexWordVo2":[],
    "reportFinancialIndexWordVo3":[],
    "monetaryFunds": "",
    "billReceivable": "",
    "accountsReceivable": "",
    "prepaidAccount": "", //预付款说明
    "apportionedCost": "",
    "otherReceivables": "",
    "longTermEquityInvestment": "",
    "fixedAssets": "",
    "shortLoan": "",
    "notesPayable": "",
    "accountsPayable": "",
    "otherPayable": "",
    "costAndIncome": "",
    "year": "",
    "industry": "",
    "reportAccountAnalysisVo": [],
    "reportFinancingBankVo": [],
    "reportFinancingGuaranteeVo": [],
    "financingBank": "",
    "financingGuarantee": "",
    "projectConclusion": "",
    "projectFile": [],
    "financingSubject":"",
    "financingCost":"",
    "repaymentMethod":"",
    "financingAmount":"",
    "termOfFinancing":"",
    "financingDiscountRate":"",
    "useOfFunds":"",
    "payment":"",
    "windControlMeasures":"",
    "additionalCreditMeasures":"",
    "projectBackground":"",  //企业背景
    "companyCreditRecord":"" , //企业征信记录
    "scope":"",  //行业范围
    "industryOne":""  ,//行业五级分类
    "industryTwo":"",
    "industryThree":"",
    "industryFour":"",
    "industryFive":"",
    "stock":"" , //(存货)
    "intangibleAssets":"",  //(无形资产)
    "salaryPayable":""  ,//(应付职工薪酬)
    "profitabilityExplain":"" , //(盈利能力说明)
    "assetQualityExplain":"" , //(资产质量说明)
    "solvencyExplain":""  ,//(偿债能力说明)
    "advancePayment":""  //预收款项
};

var initparems = {
    "id": "",
    "projectId": "",
    "creditAppId": "",
    "projectExplain": "",
    "projectImgs": [],
    "enterpriseHistory": "",
    "companyId": "",
    "companyName": "",
    "ownershipStructure": "",
    "contributive": [],
    "ownershipStructureImgs": [],
    "isIllegality": 0,
    "reportSharePledgeVo": [],
    "stockRights": "",
    "reportPunishVo": [],
    "administrativeSanction": "",
    "reportLegalProceedingsVo": [],
    "legalProceedings": "",
    "isDiscredit": 0,
    "discredit": "", //失信记录
    "realPersonCreditRecord": "", //征信记录说明
    "realPersonCreditRecordFile": [], //征信附件
    "reportFile":[],  //项目要素附件
    "creditFile":[],  //增信措施附件
    "businessDetails": "",
    "businessDetailsImg": [],
    "reportFinancialIndexWordVo1":[],
    "reportFinancialIndexWordVo2":[],
    "reportFinancialIndexWordVo3":[],
    "supplyChainStatus": "",
    "supplyChainStatusImg": [],
    "accountYear": "",
    "accountMonth": "",
    "monetary": [],
    "receivable": [],
    "accounts": [],
    "prepaid": [],
    "otherPay": [],
    "fixedSsets": [],
    "longInvestment": [],
    "receivables": [],
    "apportioned": [],
    "accountsPay": [],
    "shortYermBorrowing": [],
    "notesPay": [],
    "monetarySize": "",
    "receivableSize": "",
    "accountsSize": "",
    "prepaidSize": "",
    "otherPaySize": "",
    "fixedSsetsSize": "",
    "longInvestmentSize": "",
    "receivablesSize": "",
    "apportionedSize": "",
    "accountsPaySize": "",
    "shortYermBorrowingSize": "",
    "notesPaySize": "",
    "monetaryFunds": "",
    "billReceivable": "",
    "accountsReceivable": "",
    "prepaidAccount": "", //预付款说明
    "apportionedCost": "",
    "otherReceivables": "",
    "longTermEquityInvestment": "",
    "fixedAssets": "",
    "shortLoan": "",
    "notesPayable": "",
    "accountsPayable": "",
    "otherPayable": "",
    "costAndIncome": "",
    "year": "",
    "industry": "",
    "reportAccountAnalysisVo": [],
    "reportFinancingBankVo": [],
    "reportFinancingGuaranteeVo": [],
    "financingBank": "",
    "financingGuarantee": "",
    "projectConclusion": "",
    "projectFile": [],
    "financingSubject":"",
    "financingCost":"",
    "repaymentMethod":"",
    "financingAmount":"",
    "termOfFinancing":"",
    "financingDiscountRate":"",
    "useOfFunds":"",
    "payment":"",
    "windControlMeasures":"",
    "additionalCreditMeasures":"",
    "projectBackground":"",  //企业背景
    "companyCreditRecord":"" , //企业征信记录
    "scope":"",  //行业范围
    "industryOne":""  ,//行业五级分类
    "industryTwo":"",
    "industryThree":"",
    "industryFour":"",
    "industryFive":"",
    "stock":""  ,//(存货)
    "intangibleAssets":""  ,//(无形资产)
    "salaryPayable":"" , //(应付职工薪酬)
    "profitabilityExplain":"" , //(盈利能力说明)
    "assetQualityExplain":""  ,//(资产质量说明)
    "solvencyExplain":"" , //(偿债能力说明)
    "advancePayment":""
};

function setFileArray(uploadfile, data) { //回填图片附件
    $("#" + uploadfile).html("");
    for (var i = 0; i < data.length; i++) {
        var res = data[i];
        var src = "/credit/uploadfile/showImage?token=" + getUrlParam('token') + "&path=" + res.uploadfileUrl;
        $("#" + uploadfile).append(
            '<div class="img-upload">' +
            '<span class="img-upload-close" >X</span>' +
            '<img  src="' + src + '" data-fileName="' + res.fileName + '" data-uploadfileUrl="' + res.uploadfileUrl + '" data-uploadName="' + res.uploadName + '"  class="layui-upload-img">' +
            '</div>'
        );
        $("div#" + uploadfile + " div.img-upload span.img-upload-close").on("click", function(){
            $(this).parent(".img-upload").remove();
        });
        $("div#" + uploadfile + " div.img-upload img").on("click", function(){
                var name = $(this).attr("data-uploadfileurl") 
                 layer.open({
                  type: 1,
                  title: ['查看图片'],
                  skin: "layui-layer-lan",
                  area: ['80%', '80%'],
                  content: $("#formPic"),
                  anim: 0,
                  shade: 0,
                  cancel: function(index, layero){
                     // location.href="/credit/uploadfile/showImage?token=" + token;
                  }
                });
                 $(".pic").attr("src","/credit/uploadfile/showImage?token=" + token+"&path="+name);
        });
    }
}

/**
 * 回填附件
 */
function setProjectFiler() {


}


function setparmes(pares) {
    var name;
    var element,tr,consult;
    for (var item in pares) {
        // if(item == "ownershipStructure"){    //公司股权结构
        //     UE.delEditor('ownershipStructure');
        //     var ue = UE.getEditor('ownershipStructure',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue.ready(function (){
        //         $('[name="ownershipStructure"]').val(pares.ownershipStructure)
        //         /*console.log($('[name="ownershipStructure"]').val())*/
        //         window.setTimeout(ue.setContent($('[name="ownershipStructure"]').val()),500);
        //     });
        // }else if(item == "projectExplain"){
        //     UE.delEditor('projectExplain');
        //     var ue2 = UE.getEditor('projectExplain',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue2.ready(function (){
        //         $('[name="projectExplain"]').val(pares.projectExplain);
        //         console.log("======="+$('[name="projectExplain"]').val());
        //         window.setTimeout(ue2.setContent($('[name="projectExplain"]').val()),500);
        //     });
        // }else if(item == "companyCreditRecord"){
        //     UE.delEditor('companyCreditRecord');
        //     var ue3 = UE.getEditor('companyCreditRecord',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue3.ready(function (){
        //         $('[name="companyCreditRecord"]').val(pares.companyCreditRecord)
        //         /*console.log($('[name="companyCreditRecord"]').val())*/
        //         window.setTimeout(ue3.setContent($('[name="companyCreditRecord"]').val()),500);
        //     });
        // }else if(item == "enterpriseHistory"){
        //     UE.delEditor('enterpriseHistory');
        //     var ue4 = UE.getEditor('enterpriseHistory',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue4.ready(function (){
        //         $('[name="enterpriseHistory"]').val(pares.enterpriseHistory)
        //         /*console.log($('[name="enterpriseHistory"]').val())*/
        //         window.setTimeout(ue4.setContent($('[name="enterpriseHistory"]').val()),500);
        //     });
        // }else if(item == "realPersonCreditRecord"){
        //     UE.delEditor('realPersonCreditRecord');
        //     var ue5 = UE.getEditor('realPersonCreditRecord',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue5.ready(function (){
        //         $('[name="realPersonCreditRecord"]').val(pares.realPersonCreditRecord)
        //         /*console.log($('[name="realPersonCreditRecord"]').val())*/
        //         window.setTimeout(ue5.setContent($('[name="realPersonCreditRecord"]').val()),500);
        //     });
        // }else if(item == "businessDetails"){
        //     UE.delEditor('businessDetails');
        //     var ue6 = UE.getEditor('businessDetails',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue6.ready(function (){
        //         $('[name="businessDetails"]').val(pares.businessDetails)
        //         /*console.log($('[name="businessDetails"]').val())*/
        //         window.setTimeout(ue6.setContent($('[name="businessDetails"]').val()),500);
        //     });
        // }else if(item == "supplyChainStatus"){
        //     UE.delEditor('supplyChainStatus');
        //     var ue7 = UE.getEditor('supplyChainStatus',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue7.ready(function (){
        //         $('[name="supplyChainStatus"]').val(pares.supplyChainStatus)
        //         /*console.log($('[name="supplyChainStatus"]').val())*/
        //         window.setTimeout(ue7.setContent($('[name="supplyChainStatus"]').val()),500);
        //     });
        // }else if(item == "costAndIncome"){
        //     UE.delEditor('costAndIncome');
        //     var ue8 = UE.getEditor('costAndIncome',{
        //     autoHeightEnabled: false
        //     ,enableAutoSave:false
        //     ,autoFloatEnabled: true
        //     ,initialFrameHeight : 200//编辑器高度，默认320
        // })
        //     ue8.ready(function (){
        //         $('[name="costAndIncome"]').val(pares.costAndIncome)
        //         /*console.log($('[name="supplyChainStatus"]').val())*/
        //         window.setTimeout(ue8.setContent($('[name="costAndIncome"]').val()),500);
        //     });
        // }
        if (item == 'accountYear' || item == 'accountMonth') {
            /*getAssetsAndLiabilities(pares);*/
            if(pares.accountYear != null || pares.accountMonth != null){
                $("[name='AssetsAndLiabilities']").val(pares.accountYear + "-" + pares.accountMonth);
            }
            continue;
        } else if (item == "isDiscredit" || item == "isIllegality") {
            var isok = (pares[item] == 1);
            $('[name="' + item + '"]').attr("checked", isok);
            continue;
        }
        switch (item) {
            //图片列表
            case "projectImgs": //项目图片
                $("#projectImgs").html("");
                editFile(pares[item],item)
                name = "projectImgs";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "projectImgs";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            case "supplyChainStatusImg": //供应链状况图片
                $("#supplyChainStatusImg").html("");
                editFile(pares[item],item)
                name = "supplyChainStatusImg";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "supplyChainStatusImg";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            case "ownershipStructureImgs": //供应链状况图片
                $("#ownershipStructureImgs").html("");
                editFile(pares[item],item)
                name = "ownershipStructureImgs";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "ownershipStructureImgs";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            case "businessDetailsImg": //企业经营情况及业务详述
                $("#businessDetailsImg").html("");
                editFile(pares[item],item)
                name = "businessDetailsImg";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "businessDetailsImg";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            /*case "projectImgs":*/ //项目交易结构
                /*setFileArray(item, pares[item]);
                continue;*/
            case "realPersonCreditRecordFile":
                $("#realPersonCreditRecordFile").html("");
                editFile(pares[item],item)
                name = "realPersonCreditRecordFile";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "realPersonCreditRecordFile";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            case "projectFile":
                $("#projectFile").html("");
                editFile(pares[item],item)
                name = "projectFile";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "projectFile";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
                //申请人基本信息
            case "reportFile":  //项目要素附件
                $("#reportFile").html("");
                editFile(pares[item],item)
                name = "reportFile";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "reportFile";
                                    console.log("#file"+name + data)
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    $("#"+name+data).attr("action","")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
                   console.log($("#"+id).attr("action")+"222222222")
               })
                continue;
            case "creditFile": //增信措施附件
                $("#creditFile").html("");
                editFile(pares[item],item)
                name = "creditFile";
                $.each(pares[item], function (data, event) {
                     layui.upload.render({
                                elem: "#fileUpload"+name+data
                                , url: '/credit/uploadfile/upload?token=' + token
                                , accept: 'file'
                                , auto: true
                                , size: 0
                                , before: function (obj) {
                                    layer.load();
                                }
                                , done: function (res, index, upload) {
                                    layer.closeAll('loading'); //关闭loading
                                    layer.msg('上传成功', {icon: 1});
                                    var items = res.data;
                                    var name = "creditFile";
                                    $("#file"+name + data).attr("data-url", items.uploadfileUrl);
                                    $("#file"+name + data).attr("data-filename", items.fileName);
                                    $("#file"+name + data).attr("data-uploadname", items.uploadName);
                                    $("#imageFile"+name+data).val(items.uploadfileUrl);
                                    $("#imageName"+name+data).val(items.fileName);
                                    $("#imageGenerate"+name+data).val(items.uploadName);
                                    /*$("#fileUpload"+item + data).html("修改");*/
                                    $("#fileName"+name + data).val(items.fileName)
                                    $("#file"+name + data).find(".fileView").removeClass("none")
                                    $("#file"+name+data).find(".downFile").removeClass("none")
                                    var fileName =  items.fileName;
                                    var arr = fileName.split(".");
                                    if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx" || arr[1] == "bmp" || arr[1] == "emp"){
                                       $("#file"+name+data).find(".fileView").addClass("none")
                                   }else{
                                       $("#file"+name+data).find(".fileView").removeClass("none")
                                   }
                                }
                                , error: function () {
                                    layer.closeAll('loading');//关闭loading
                                    layer.msg('上传失败', {icon: 2});
                                }
                     });
                });
                //下载新增的资质文件
                $(".RecordFile").on("click",".downFile",function(){
                   var id = $(this).attr("name");
                   $("#"+id).attr("action","");
                   var name= $(this).parents("tr").find("input[name=imageGenerate]").val();
                   $("#"+id).attr("action", "/credit/uploadfile/downloadAll?name="+name+"&token="+token);
               })
                continue;
            case "reportSharePledgeVo":
            case "reportPunishVo":
            case "reportLegalProceedingsVo":
                var data;
                if (item == "reportLegalProceedingsVo") { //法律咨询
                    data = pageDate.index_2.tab4;
                } else if (item == "reportPunishVo") { //行政处罚
                    data = pageDate.index_2.tab3;
                } else if (item == "reportSharePledgeVo") { //股权质押
                    data = pageDate.index_2.tab2;
                }
                $("#" + item).find("tbody").html('');
                for (var i = 0; pares[item]!=null&&i < pares[item].length; i++) {
                    element = pares[item][i];
                    tr = addTbody(data, element);
                    if ($("#" + item).find("tbody").length > 0) {
                        $("#" + item + " tbody").append(tr);
                    } else {
                        $("#" + item).append("<tbody>{{0}}</tbody>".format([tr]));
                    }
                }
                continue;
            case "monetary": //1 货币资金
            case "receivable": //2 应收票据
            case "accounts": //3 应收账款
            case "prepaid": //4 预付账款
            case "fixedSsets": //5 待摊费用
            case "otherPay": //6 其他应收款
            case "longInvestment": //7 长期股权投资
            case "receivables": //8 固定资产
            case "apportioned": //9  短期借款
            case "accountsPay": //10 应付票据
            case "shortYermBorrowing": //11.应付账款
            case "notesPay": //应付账款
                $("#" + item).find("tbody").html('');
                if (null == pares[item]) {
                    continue;
                }
                for (var index = 0; index < pares[item].length; index++) {
                    element = pares[item][index];
                    tr = addTbody(pageDate.index_5, element);
                    /*if ($("#" + item).find("tbody").length > 0) {*/
                        $("#" + item).append(tr);
                    /*} else {
                        $("#" + item).append("<tbody>{{0}}</tbody>".format([tr]));
                    }*/

                }
                continue;
            case "reportFinancingBankVo":
                $("#" + item).find("tbody").html('');
                editRz(pares[item],"reportFinancingBankVo")
                consult = 0;
                $("#reportFinancingBankVo").find(".loan").each(function () {
                    var numb = relieveThousands($(this).val());
                    if (numb == "") {
                        numb = 0;
                    }
                    consult += parseFloat(numb, 2);
                });
                consult = toThousands(consult);
                $(".reportFinancingBankVo").html(consult)
                /*for (var index = 0; index < pares[item].length; index++) {
                    var element = pares[item][index];
                    var tr = addTbody(pageDate.financing.tab1, element);
                    if ($("#" + item).find("tbody").length > 0) {
                        $("#" + item + " tbody").append(tr);
                    } else {
                        $("#" + item).append("<tbody>{{0}}</tbody>".format([tr]));
                    }
                }*/
                continue;
            case "reportFinancingGuaranteeVo":
                $("#" + item).find("tbody").html('');
                editRz(pares[item],"reportFinancingGuaranteeVo")
                consult = 0;
                $("#reportFinancingGuaranteeVo").find(".amountOfGuarantee").each(function () {
                    var numb = relieveThousands($(this).val());
                    if (numb == "") {
                        numb = 0;
                    }
                    consult += parseFloat(numb, 2);
                });
                consult = toThousands(consult);
                $(".reportFinancingGuaranteeVo").html(consult)
                /*for (var index = 0; index < pares[item].length; index++) {
                    var element = pares[item][index];
                    var tr = addTbody(pageDate.financing.tab2, element);
                    if ($("#" + item).find("tbody").length > 0) {
                        $("#" + item + " tbody").append(tr);
                    } else {
                        $("#" + item).append("<tbody>{{0}}</tbody>".format([tr]));
                    }
                }*/
                continue;
            // case "reportNo":
            // case "createTime":
            //     $('div.' + item).text(pares[item]);
            //     continue;
        }

        layui.use(['form','element'], function(){
        var form = layui.form
        if ($('[name="' + item + '"]').length > 0) {
            if ($('[name="' + item + '"]').get(0).tagName == "TEXTAREA" || $('[name="' + item + '"]').get(0).tagName == "INPUT" || $('[name="' + item + '"]').get(0).tagName == "SELECT") {
                console.log("============"+item);
                /*if(item == "industryOne"){
                    // debugger
                  $('[name="' + item + '"]').val(pares[item]);
                }*/
                $('[name="' + item + '"]').val(pares[item]);
                form.render()
            }
        }
        });
        
        /*if(item == "ownershipStructure" || item == "projectExplain" || item == "companyCreditRecord" || item == "enterpriseHistory" || item == "realPersonCreditRecord" || item == "businessDetails" || item == "supplyChainStatus"){
            var ue = UE.getEditor(item)
            ue.ready(function (){
                $('[name="' + item + '"]').val(pares[item])
                console.log($('[name="' + item + '"]').val() + item)
                window.setTimeout(ue.setContent($('[name="' + item + '"]').val()),2000);              
            }); 
            UE.getEditor(item).execCommand('insertHtml', pares[item])            
        } */

    }
    
}
//所有附件信息回填
    function editFile(data,docm){
      var content = "";
      $.each(data, function (d, event) {
        var fileName =  event.fileName;
        var str=""
        var index = fileName.lastIndexOf(".");  
        str  = fileName .substring(index, fileName.length);
        var arr = str.split(".");
        content += "<tr id='file" + docm + "" + d + "'  data-url='" + event.uploadfileUrl + "' data-fileName='" + event.fileName + "' data-uploadName='" + event.uploadName + "'>";
        content += "<td>";
        content += "<input type='text' class='layui-input' id='fileName" + docm + "" + d + "' value='"+event.fileName+"' name='name' disabled style='border:none; background:none'>";
        content += "</td>";
        content += "<td>";
        content += "<div class='operation'>";
        content += "<span class='blue fileUpload' name = '" + d + "' id='fileUpload" + docm + "" + d + "'>修改</span>";
        content += "<input type='hidden' class='layui-input' id='imageFile" + docm + "" + d + "' name='imageFile' value='"+event.uploadfileUrl+"'>";
        content += "<input type='hidden' class='layui-input' id='imageName" + docm + "" + d + "' name='imageName' value='"+event.fileName+"'>";
        content += "<input type='hidden' class='layui-input' id='imageGenerate" + docm + "" + d + "' name='imageGenerate' value='"+event.uploadName+"'>";
        if(arr[1] == "zip" || arr[1] == "rar" || arr[1] == "xlsx" || arr[1] == "xls" || arr[1] == "doc" || arr[1] == "docx"|| arr[1] == "bmp" || arr[1] == "emp"){
          content += "<span class='blue fileView none' name = '" + d + "'>查看</span>";
        }else{
         content += "<span class='blue fileView' name = '" + d + "'>查看</span>"; 
        }
        content += "<span class='blue deleteFile' name = '" + d + "'>删除</span>";
        content += "<form method='post' id='"+docm+""+d+"' action=''>";
        content +=  "<button class='blue downFile none' type='submit' name = '"+docm+""+d+"' style='border:none;background:none'>下载</button>";
        content += "</form>";
        content += "<div>";
        content += "</td>";
        content += "</tr>";
      });  
      $("#" + docm).append(content);
      $(".downFile").show(); 
    }


/**
 * 获取附件
 * 返回list
 */
function getFileArray(imgDoc) {
    var array = [];
    $("#" + imgDoc).find("img").each(function () {
        var imgData = $(this).data();

        array.push({
            fileName: imgData.filename,
            uploadName: imgData.uploadname,
            uploadfileUrl: imgData.uploadfileurl
        });
    });

    return array;
}
/**
 * 初始化
 */
function initData() {
    parems = initparems;
    for (var key in initparems) {
        switch (key) {
            case "projectImgs": //项目图片
            case "supplyChainStatusImg": //供应链状况图片
            case "businessDetailsImg": //企业经营情况及业务详述
            case "projectImgs": //项目交易结构
                $("#" + key).html("");
                continue;
            case "realPersonCreditRecordFile":
            case "projectFile":
            case "creditFile":
            case "reportFile":
            case "ownershipStructureImgs":
                $("#" + key).html("");
                continue;

            case "reportSharePledgeVo":
            case "reportPunishVo":
            case "reportLegalProceedingsVo":
            case "monetary": //1 货币资金
            case "receivable": //2 应收票据
            case "accounts": //3 应收账款
            case "prepaid": //4 预付账款
            case "fixedSsets": //5 待摊费用
            case "otherPay": //6 其他应收款
            case "longInvestment": //7 长期股权投资
            case "receivables": //8 固定资产
            case "apportioned": //9  短期借款
            case "accountsPay": //10 应付票据
            case "shortYermBorrowing": //11.应付账款
            case "notesPay": //应付账款
            case "reportFinancingBankVo":
            case "reportFinancingGuaranteeVo":
                $("#" + key + "  tbody").html('');
                continue;
            case "contributive":
                $("#Company_ownership_structure  tbody").html('');
                continue;
        }
        if ($('[name="' + key + '"]').length > 0) {
            if ($('[name="' + key + '"]').get(0).tagName == "TEXTAREA" || $('[name="' + key + '"]').get(0).tagName == "INPUT") {
                $('[name="' + key + '"]').val("");
            }
        }
    }
    for (var keyOther in company) {
        var docm = $("#CompanyInfo .business_Info ." + keyOther);
        if (docm.length > 0) {
            docm.text(""); //社会信用代码
        }
    }

    $("#lirun").html("");
    $("#fuzhai").html("");
    $(".companyName").html("");
}