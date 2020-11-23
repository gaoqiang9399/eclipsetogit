//通过这个函数来异步获取信息   获取二代证信息
function erDaiZhengAjax(url,callback){
    var xmlHttpReq = null;  //声明一个空对象用来装入XMLHttpRequest
    if (window.ActiveXObject){//IE5 IE6是以ActiveXObject的方式引入XMLHttpRequest的
        xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest){//除IE5 IE6 以外的浏览器XMLHttpRequest是window的子对象
        xmlHttpReq = new XMLHttpRequest();//实例化一个XMLHttpRequest
    }
    if(xmlHttpReq != null){  //如果对象实例化成功
        xmlHttpReq.open("GET",url,true);  //调用open()方法并采用异步方式
        //xmlHttpReq.setRequestHeader('If-Modified-Since', '0');
        //xmlHttpReq.open("GET",url,false);
        xmlHttpReq.onreadystatechange=RequestCallBack; //设置回调函数
        xmlHttpReq.send(null);  //因为使用get方式提交，所以可以使用null参调用
    }
    function RequestCallBack(){//一旦readyState值改变，将会调用这个函数
        if(xmlHttpReq.readyState == 4){
            if(xmlHttpReq.status == 200){
                if(typeof callback == "function"){

                    try{
                        callback(xmlHttpReq.responseText);
                    }catch(e){

                        //document.getElementById("textInfo").innerHTML = "数据解析失败。";
                        //window.top.alert("读取身份证信息数据失败。", 0);
                        window.top.alert(top.getMessage("FAILED_OPERATION","身份证信息数据解析"), 0);
                    }
                }
            }else{
                //document.getElementById("textInfo").innerHTML = "代理服务未启动。";
                // {"opration":"客户基本信息","reason":data.msg}
                window.top.alert(top.getMessage("NO_ENABLE","二代证代理服务"), 0);
                //window.top.alert(top.getMessage("FAILED_OPERATION_CONTENT",{"operation":"二代证读取","reason":"代理服务未启动"}), 0);
            }
        }
    }
}

function getIdCardInfo(ele){
    var url = "http://127.0.0.1:10012/readCard";
    erDaiZhengAjax(url,function(str){
        var idInfos = str.split("|");
        if(idInfos[0]!="0"){//出错，显示错误信息

            //document.getElementById("textInfo").innerHTML = str;
            //document.getElementById("photoInfo").innerHTML = "";
            window.top.alert(top.getMessage("FAILED_OPERATION_CONTENT",{"operation":"二代证读取","reason":str}), 0);
            return;
        }
        // var innerHtml = "";
        if(idInfos[1]=="1"){//显示身份证信息
            //innerHtml+="<span>姓名：    "+idInfos[2]+"</span></br>";
            //innerHtml+="<span>性别代码："+idInfos[3]+"</span></br>";
            //innerHtml+="<span>性别：    "+idInfos[4]+"</span></br>";
            //innerHtml+="<span>民族代码："+idInfos[5]+"</span></br>";
            //innerHtml+="<span>民族：    "+idInfos[6]+"</span></br>";
            //innerHtml+="<span>出生：    "+idInfos[7]+"</span></br>";
            //innerHtml+="<span>住址：    "+idInfos[8]+"</span></br>";
            //innerHtml+="<span>身份号码："+idInfos[9]+"</span></br>";
            //innerHtml+="<span>签发机关："+idInfos[10]+"</span></br>";
            //innerHtml+="<span>有效期限："+idInfos[11]+"-"+idInfos[12]+"</span></br>";
            //document.getElementById("textInfo").innerHTML = innerHtml;
            //document.getElementById("photoInfo").innerHTML = "<img src=data:image/jpeg;base64,"+idInfos[13]+"></img>";

            $("input[name=idNum]").val(idInfos[9]);
            $("input[name=cusName]").val(idInfos[2]);
        }
        else{//显示外国人永久居留证信息
            //innerHtml+="<span>英文姓名："+idInfos[2]+"</span></br>";
            //innerHtml+="<span>性别代码："+idInfos[3]+"</span></br>";
            //innerHtml+="<span>性别：    "+idInfos[4]+"</span></br>";
            //innerHtml+="<span>居留证号码："+idInfos[5]+"</span></br>";
            //innerHtml+="<span>国籍代码："+idInfos[6]+"</span></br>";
            //innerHtml+="<span>国籍：    "+idInfos[7]+"</span></br>";
            //innerHtml+="<span>中文姓名："+idInfos[8]+"</span></br>";
            //innerHtml+="<span>生效日期："+idInfos[9]+"</span></br>";
            //innerHtml+="<span>失效日期："+idInfos[10]+"</span></br>";
            //innerHtml+="<span>出生日期："+idInfos[11]+"</span></br>";
            //innerHtml+="<span>证件版本："+idInfos[12]+"</span></br>";
            //innerHtml+="<span>签发机关代码："+idInfos[13]+"</span></br>";
            //innerHtml+="<span>签发机关："+idInfos[14]+"</span></br>";
            //document.getElementById("textInfo").innerHTML = innerHtml;
            //document.getElementById("photoInfo").innerHTML = "<img src=data:image/jpeg;base64,"+idInfos[15]+"></img>";
            $("input[name=idNum]").val(idInfos[5]);
            $("input[name=cusName]").val(idInfos[8]);
        }
    });
}

function getIdCardInfoCheck(ele){
    var cusName = $("input[name=cusName]").val();
    var url = "http://127.0.0.1:10012/readCard";
    erDaiZhengAjax(url,function(str){
        var idInfos = str.split("|");
        if(idInfos[0]!="0"){
            window.top.alert(top.getMessage("FAILED_OPERATION_CONTENT",{"operation":"二代证读取","reason":str}), 0);
            return;
        }
        var name;
        if(idInfos[1]=="1"){
            name = idInfos[2];
            if(cusName == name){
                $("input[name=idNum]").val(idInfos[9]);
                $("input[name=idCardName]").val("与客户名称一致");
            }else{
                $("input[name=idNum]").val(idInfos[9]);
                $("input[name=idCardName]").val(name);
            }
        }
        else{
            name = idInfos[8];
            if(cusName == name){
                $("input[name=idNum]").val(idInfos[5]);
                $("input[name=idCardName]").val("与客户名称一致");
            }else{
                $("input[name=idNum]").val(idInfos[5]);
                $("input[name=idCardName]").val(name);
            }

        }
    });
}

// 判定是否为IE浏览器
function isIE() { //ie?
    if (!!window.ActiveXObject || "ActiveXObject" in window){
        return true;
    } else{
        return false;
    }
}
var TotalTimelenth=0;
var timer;
var TimeLenth = document.getElementById("TimeLenth");
function TimeGo(){
    TotalTimelenth++;
    //TimeLenth.value=TotalTimelenth;
    var Hour,Minue,Second;
    Hour=Math.floor(TotalTimelenth/3600);
    Minue=Math.floor((TotalTimelenth/60)%60);
    Second=Math.floor(TotalTimelenth%60);
    //Time=Hour+"时"+Minue+"分"+Second+"秒";
    document.getElementById("TimeLenth").innerHTML = Hour+":"+Minue+":"+Second;
}

// IE浏览器普天仪器拍照
var IEGetPhoto = function(window, $) {
//	<div style="text-align:center;" >
//		<object classid="clsid:454C18E2-8B7D-43C6-8C17-B1825B49D7DE" id="captrue"  width="480" height="360" ></object>
//	</div>
//	$("body").append('<div style="text-align:center;" ><object classid="clsid:454C18E2-8B7D-43C6-8C17-B1825B49D7DE"'
//			+' id="captrue"  width="480" height="360" ></object></div>');
    var flag = false;
    // 开启主摄像头
    var _start1Camera = function(){
        _stopCamera();
        //captrue.SetVideoDispMode(1);
        var str = captrue.bStartPlay();
        if(str){
            flag = true;
            isVice = false;
        }
    };
    // 开启副摄像头
    var _start2Camera = function(){
        _stopCamera();
        var str = captrue.bStartPlay2(0);
        if(str){
            flag = true;
            isVice = true;
        }
    };
    // 关闭摄像头
    var _stopCamera = function(){
        window.clearInterval(timer);
        TotalTimelenth=0;
        var str = captrue.bStopPlay();
        if(str){
            flag = false;
        }

    };
    // 保存JPG格式的图片 filePath——保存图片的路径    filename——保存图片的名称（不用包含后缀名）
    function saveMethod(filePath,fileName){
        var str=captrue.bSaveJPG(filePath,fileName);
        return str;
    }
    var _saveAsJPG = function(){
        // var str=captrue.bSaveJPG("G:\\","JPG");
        var fileName = new Date().getTime();
        // var str=captrue.bSaveJPG(filePath,fileName);
        if(flag){
            var filePath = $("input[name=filePath]").val();
            if(isFilePathRight(filePath)){
                var str = saveMethod(filePath,fileName);
                if(str){
                    fileName = fileName+".jpg ";
                    var param = "relNo="+relNo+"&docSplitNo=1000000156";
                    captrue.bUpLoadImage(filePath+fileName, '192.168.2.20', 8090, "/uploadsservlet?"+param);

                    // captrue.bUpLoadImage(filePath+fileName, photoUploadIp, photoUploadPort, "/uploadsservlet?"+param);
                }else{
                    alert(top.getMessage("FAILED_SAVE_CONTENT",{"content":fileName,"reason":e}));
                }
            }	else{
                alert(top.getMessage("FAILED_SAVE_CONTENT",{"content":"图片","reason":"保存路径不存在或不合法"}));
            }

        }else{
            //alert("请先打开摄像头！");
            alert(top.getMessage("NO_ENABLE","摄像头"));
        }

    };

    //face++人脸比对

    //录屏开始
    var _startRecord = function(){
        var fileName = new Date().getTime();
        if(flag){
            var filePath = $("input[name=filePath]").val();
            if(isFilePathRight(filePath)){
                fileName = fileName + '.avi';
                recordFile = filePath+fileName;
                captrue.bStartRec(recordFile);
                $("#record").show();
                $("#startRecord").hide();
                timer = window.setInterval("TimeGo()",1000);
            }else{
                alert(top.getMessage("FAILED_SAVE_CONTENT",{"content":"录屏","reason":"保存路径不存在或不合法"}));
            }
        }else{
            alert(top.getMessage("NO_ENABLE","摄像头"));
        }
    };

    //录屏结束
    var _stopRecord = function(){
        captrue.bStopRec();
        window.clearInterval(timer);
        TotalTimelenth=0;

        alert("录屏结束");
        if(recordFile != ""){
            var param = "uploadsservlet?relNo="+relNo+"&docSplitNo=1000000156";
            captrue.bUpLoadImage(recordFile, photoUploadIp, photoUploadPort, "/uploadsservlet?"+param);
            recordFile = "";
            $("#record").hide();
            $("#startRecord").show();
        }else{
            alert(top.getMessage("FIRST_OPERATION","开启录屏"));
        }
    };

    return {
        start1Camera:_start1Camera,
        start2Camera:_start2Camera,
        stopCamera:_stopCamera,
        saveAsJPG:_saveAsJPG,
        startRecord:_startRecord,
        stopRecord:_stopRecord,
    };
}(window, jQuery);

//非IE普天拍照
var noIEGetPhoto = function(window, $) {
    // <p><img id="photo" src="" width=900 height=480 ></p>
    var flag = false;

    var webSocket = null;
    try{
        webSocket = new WebSocket('ws://localhost:1818');
        webSocket.onerror = function(event){
            onError(event);
        };

        webSocket.onopen = function(event){
            onOpen(event);
        };

        webSocket.onclose = function(event){
            onClose(event);
            alert(event.data);
        };

        webSocket.onmessage = function(event){
            onMessage(event);
        };
    }catch(e){
    }
    var begin_data="data:image/jpeg;base64,";
    function onMessage(event){

        if(event.data.indexOf('BarCodeTransferBegin')>=0){
            alert(event.data.replace('BarCodeTransferBegin','').replace('BarCodeTransferEnd',''));
        } else if (event.data.indexOf('BeginBase64')>=0) {
            //document.getElementById("preview").src = begin_data + event.data.substring(0, event.data.length - 11);
            alert(event.data.replace('BeginBase64','').replace('EndBase64',''));
            //alert(begin_data + event.data.substring(0, event.data.length - 11));
        } else {
            document.getElementById('photo').src =begin_data+event.data;
        };

    }
    function onError(event){
        //      alert(event.data);
    }

    function start(el){
        if(el.indexOf('bStartRec') != -1){
            timer = window.setInterval("TimeGo()",1000);
        }
        if(el=='bStopRec'){
            window.clearInterval(timer);
            TotalTimelenth=0;
            //window.top.alert("录屏结束",3);
        }
        webSocket.send(el);
        return false;
    }
    function vout_OnClick(){

    }

    var _start1Camera = function(){
        _stopCamera();
        start('bStartPlay');
        flag = true;
        isVice = false;
    };

    var _start2Camera = function(){
        _stopCamera();
        start('bStartPlay2');
        flag = true;
        isVice = true;
    };

    var _stopCamera = function(){
        window.clearInterval(timer);
        TotalTimelenth=0;
        start('bStopPlay');
        flag = false;
    };
    function saveMethod(filePath,fileName){
        start('bSaveJPG('+filePath+','+fileName+')');
    }
    function upload(fileFullPath,photoUploadIp,param){
        start('bUpLoadImage('+fileFullPath+','+photoUploadIp+','+photoUploadPort+','+param+')');
    }


    var _saveAsJPG = function(){
        var fileName = new Date().getTime();
        if(flag){
            var filePath = $("input[name=filePath]").val();
            if(isFilePathRight(filePath)){
                saveMethod(filePath,fileName);
                fileName = fileName+".jpg";
                var fileFullPath = filePath+fileName;
                //+"&docBizNo="+docBizNo+"&cusNo="+cusNo+"&docType=04&docSplitNo=1000000156&scNo=contract_sign&opNo="+opNo
                var param ="/uploadsservlet?relNo="+relNo+"&docSplitNo=1000000156";
                //upload(fileFullPath,photoUploadIp,param);
                //start('bUpLoadImage(F:/Photo.JPG,192.168.2.32,8080,/factor/servlet/uploadServlet)')
                if (!String.prototype.trim){
                    /*---------------------------------------
                     * 清除字符串两端空格，包含换行符、制表符
                     *---------------------------------------*/
                    String.prototype.trim = function () {
                        return this.replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "");
                    };
                }
                photoUploadIp = photoUploadIp.trim();
                start('bUpLoadImage('+fileFullPath+','+photoUploadIp+','+photoUploadPort+','+param+')');
                // start('bUpLoadImage(C:\\1533628675308.jpg,192.168.2.20,8090,/uploadsservlet)');
            }else{
                window.top.alert(top.getMessage("FAILED_SAVE_CONTENT",{"content":"图片","reason":"保存路径不存在或不合法"}),0);
            };
        }else{
            window.top.alert(top.getMessage("NO_ENABLE","摄像头"),1);
        }
    };

    var _startRecord = function(){
        var fileName = new Date().getTime();
        if(flag){
            var filePath = $("input[name=filePath]").val();
            if(isFilePathRight(filePath)){
                fileName = fileName + '.avi';
                recordFile = filePath+fileName;
                start('bStartRec('+recordFile+')');
                $("#record").show();
                $("#startRecord").hide();
            }else{
                window.top.alert(top.getMessage("FAILED_SAVE_CONTENT",{"content":"录像","reason":"保存路径不存在或不合法"}),0);
            };
        }else{
            window.top.alert(top.getMessage("NO_ENABLE","摄像头"),1);
        }
    };

    var _stopRecord = function(){
        start('bStopRec');
        if(recordFile != ""){
            var param ="/uploadsservlet?relNo="+relNo+"&docSplitNo=1000000156";
            start('bUpLoadImage('+recordFile+','+photoUploadIp+','+photoUploadPort+','+param+')');
            recordFile = "";
            $("#record").hide();
            $("#startRecord").show();
        }else{
            window.top.alert(top.getMessage("FIRST_OPERATION","开启录屏"),1);
        }
    };

    var _paraSet = function(){
        start('displayVideoPara');
    };
    var _paraSetPIN = function(){
        start('vSetCapturePin');
    };
    var _rotateMain = function(){
        start('bStartPlayRotate(90)');
    };
    return {

        start1Camera:_start1Camera,
        start2Camera:_start2Camera,
        stopCamera:_stopCamera,
        saveAsJPG:_saveAsJPG,
        paraSet:_paraSet,
        paraSetPIN:_paraSetPIN,
        rotateMain:_rotateMain,
        startRecord:_startRecord,
        stopRecord:_stopRecord,
    };
}(window, jQuery);
//根据是否为ie内核选择普天拍照方式
function getPhotoUtil(){
    var photoutil;
    if(isIE()){
        photoutil = IEGetPhoto;
    }else{
        photoutil = noIEGetPhoto;
    }
    return photoutil;
};
//判断目录格式： D:/aa/bb/
function isFilePathRight(filePath) {

    var regx = /^([a-zA-Z]){1}:([\/]{1}[\w]+)*[\/]{1}$/;
    return regx.test(filePath);
}
//请求face++比对
function faceCompare(){
    $("#faceCompareResult").val("");
    $.ajax({
        url : "Rong360ActionAjax_getFaceCompareResultAjax.action?cusNo="+cusNo+"&relNo="+relNo+"&docBizNo="+docBizNo,
        type : "post",
        dataType : "json",
        success : function(data) {
            if(data.flag == 'success'){
                var confidence = data.result_faceid.confidence;
                var le = data.le;
                var isRight = confidence > le ? true:false;
                if(isRight){
                    $("#faceCompareResult").val("匹配  相似度"+confidence.toFixed(2)+"%");
                }else{
                    $("#faceCompareResult").val("不匹配  相似度"+confidence.toFixed(2)+"%");
                }

            }else{
                if(isIE()){
                    alert(data.msg);
                }else{
                    alert(data.msg,0);
                }
            }
        },
        error : function() {

        }
    });
    $("#compareResult").show();
    $("#compare").hide();

}

function facePlusUpload(){
    $("#openPhoto").click();
    $("#compare").show();
    $("#compareResult").hide();
}

// $('#load').after('<input type="file" id="load_xls" name="file" style="display:none" onchange ="uploadFile()">');
// $('#load').click(function(){
//    document.getElementById("load_xls").click();
// });
function uploadFile(fileObj,docSplitNo) {
    var myform = new FormData();
    myform.append('file',$(fileObj)[0].files[0]);
    $.ajax({
        url: "/uploadsservlet?relNo="+relNo+"&docSplitNo=1000000156",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        dataType : "json",
        success: function (data){
            if(data.flag=="success"){
                if(isIE()){
                    alert(top.getMessage("SUCCEED_UPLOAD"));
                }else{
                    alert(top.getMessage("SUCCEED_UPLOAD"),3);
                }
            }
        },
        error:function(data, XMLHttpRequest, textStatus, errorThrown){

        }
    });
}


