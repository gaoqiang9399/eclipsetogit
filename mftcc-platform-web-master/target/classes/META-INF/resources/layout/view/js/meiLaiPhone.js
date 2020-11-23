var meiLaiPhone = {
    cusName:null,
    cusNo:null,
    cusTel:null,
    second:0,
    minute:0,
    sumSecond:0,
    audioUrl:null,
    t:null,
    isTalking:false,
    saveFlag:false,
    callType:null,
    UUID:null,
    /**
     * 隐藏消息框
     */
    closePhoneDialog:function(){
        $("#MeilaiPhoneDiv").hide();
    },
    comingCall(tel){
        meiLaiPhone.callType = "0";
        $("#callInDiv").show();
        meiLaiPhone.cusTel = tel;
        $("#callInDiv_cusTel").text(tel);
        $.ajax({
            url:webPath+"/mfMeilaiPhone/isCustomerAjax",
            type: "POST",
            data:{tel:tel},
            dataType:"json",
            success:function (data) {
               if(data.flag){
                   meiLaiPhone.cusName=data.mfCusCustomer.cusName;
                   meiLaiPhone.cusNo = data.mfCusCustomer.cusNo;
                   $("#callInDiv-isCus").text("是");
                   $("#callInDiv-cusName").text(meiLaiPhone.cusName);
                   $("#MeilaiPhoneDiv-cusName").text(meiLaiPhone.cusName);

               }else{
                   meiLaiPhone.cusName='';
                   $("#callInDiv-isCus").text("否");
                   $("#callInDiv-cusName").text("-");
                   $("#MeilaiPhoneDiv-cusName").text("-");
               }
            },
            error:function () {
                alert("保存失败!");
                console.error("通话记录保存失败："+JSON.stringify(param));
                saveFlag = false;
            }
        })
    },

    answer:function(){
        $("#callInDiv").hide();
        meiLaiPhone.isTalking= true;
        answercall();
        var cusTel =$("#callInDiv_cusTel").text();
        meiLaiPhone.showPhoneDialog(null,cusTel,getNowFormatDate(),"0");
    },
    showPhoneDialog:function(cusName,cusTel,startTime,callType){
        meiLaiPhone.cusTel = cusTel;
        meiLaiPhone.callType = callType;
        if(cusName!="" && cusName!=null){
            $("#MeilaiPhoneDiv-cusName").text(cusName);
        }
        $("#MeilaiPhoneDiv-cusTel").text(cusTel);
        $("#MeilaiPhoneDiv-startTime").text(startTime);
        meiLaiPhone.second = 0;
        meiLaiPhone.minute = 0;
        meiLaiPhone.sumSecond = 0;
        meiLaiPhone.timedCount();

        $("#MeilaiPhoneDiv").show();
    },
    timedCount:function() {
        $("#MeilaiPhoneDiv-timeLong").text(getTimeString(meiLaiPhone.minute)+":"+getTimeString(meiLaiPhone.second));
        meiLaiPhone.second=meiLaiPhone.second+1;
        if(meiLaiPhone.second>=60){
            meiLaiPhone.second=meiLaiPhone.second-60;
            meiLaiPhone.minute=meiLaiPhone.minute+1;
            meiLaiPhone.sumSecond +=1;
        }
        meiLaiPhone.t=setTimeout("meiLaiPhone.timedCount()",1000);
    },
    stopTimmer:function(){
        clearTimeout(meiLaiPhone.t);
    },
    hangUpByOp:function () {
        if(meiLaiPhone.isTalking){
            hangup();
        }else{
            meiLaiPhone.hangUpUpdateTextArea();
        }
        $("#MeilaiPhoneDiv").hide();
    },
    hangUpAutoSave:function (UUID) {
        
        meiLaiPhone.isTalking = false;
        var cusTel = $("#MeilaiPhoneDiv-cusTel").text();
        var startTime = $("#MeilaiPhoneDiv-startTime").text();
        var timeLong = $("#MeilaiPhoneDiv-timeLong").text();
        var textArea = $("MeilaiPhoneDiv-textArea").text();
        var callType = meiLaiPhone.callType;
        meiLaiPhone.UUID = UUID;
        var param = {
            ext1:UUID,
            cusTel:cusTel,
            callTime:startTime,
            callLong:timeLong,
            callType:callType,
            context:textArea
        }

        $.ajax({
            url:webPath+"/mfMeilaiPhone/insertAjax",
            type: "POST",
            data:param,
            dataType:"json",
            async:false,
            success:function () {
                console.log("通话记录保存成功："+JSON.stringify(param));
                saveFlag = true;
                meiLaiPhone.cusName=null;
                meiLaiPhone.cusNo=null;
            },
            error:function () {
                alert("保存失败!");
                console.error("通话记录保存失败："+JSON.stringify(param));
                saveFlag = false;
            }
        })


    },
    hangUpUpdateTextArea:function () {
        var cusTel = meiLaiPhone.cusTel;
        var textArea = $("MeilaiPhoneDiv-textArea").val();
        var param = {
            cusTel:cusTel,
            context:textArea,
            ext1:meiLaiPhone.UUID
        }
        $.ajax({
            url:webPath+"/mfMeilaiPhone/updateAjax",
            type: "POST",
            data:param,
            dataType:"json",
            success:function () {
                console.log("通话记录保存成功："+JSON.stringify(param));
            },
            error:function () {
                alert("保存失败!");
            }
        })

    },
    hangUpUpdateAudio:function () {
        var cusTel =  meiLaiPhone.cusTel;
        var audioUrl =meiLaiPhone.audioUrl;
        var param = {
            cusTel:cusTel,
            file:audioUrl,
            ext1:meiLaiPhone.UUID
        }
        $.ajax({
            url:webPath+"/mfMeilaiPhone/updateAjax",
            type: "POST",
            data:param,
            dataType:"json",
            success:function () {
                console.log("通话记录保存成功："+JSON.stringify(param));
            },
            error:function () {
                alert("保存失败!");
            }
        })
    }
};
function getTimeString(i){
    if (parseInt(i/10)==0){
        return "0"+i;
    }else{
        return ""+i;
    }
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hour + seperator2 + minute
        + seperator2 + second;
    return currentdate;
}




