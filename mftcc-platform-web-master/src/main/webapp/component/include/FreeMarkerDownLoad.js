;
var FreeMarkerDownLoad=function(window,$){
    //templateNo下载模板ID
    var postData = {"templateNo":"Test","cusNo":"2020000000225"};
    //1、生成word、zip
    //2、下载zip
    var _freeMarkerDownLoad = function(){
        $.ajax({
            url:webPath+"/freeMarkerDownLoad/generateZipPackage",
            type:'post',
            dataType:'json',
            contentType : "application/json",
            data : JSON.stringify(postData),
            success:function(data) {
                if(data.success) {
                    window.location.href = webPath + "/freeMarkerDownLoad/downLoadZipPackage?fileName="+data.fileName;
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error:function() {
                window.top.alert("下载失败,请联系管理员", 0);
            }
        });
    };


    return{
        freeMarkerDownLoad:_freeMarkerDownLoad
    };
}(window,jQuery);