var MfSysSkinUser_List = function(window, $){
	var _init=function() {
        $("body").mCustomScrollbar({
            advanced: {
                updateOnContentResize: true
            }
        });

        _initSkinItems();
    };


	var _initSkinItems = function(){
        var htmlStr = "";
	    $.each(skinList,function(i,parmDic){
	        var curFlag = parmDic.curFlag;
	        var curSkin="";
	        if(curFlag=="1"){
                curSkin = "cur-skin";
            }
            htmlStr = htmlStr + '<div class="btn btn-div" id="'+parmDic.optCode+'" onclick="MfSysSkinUser_List.updateSystemSkin(this);">'
            +'<div class="skin-div skin-'+parmDic.optCode+' '+curSkin+'">'
            +'<i class="i i-duihao4"></i>'
            +'</div>'
            +'<div class="skin-name">'+parmDic.optName+'</div>'
            +'</div>';
        });
        $(".skin-item").html(htmlStr);
    };


    var _updateSystemSkin = function(obj){
        var skin = $(obj).attr("id");
        $.ajax({
            url: webPath + '/mfSysSkinUser/updateSystemSkinAjax',
            data:{skin:skin},
            success:function(data) {
                if (data.flag == "success") {
                    if(data.oldSkin!=skin){
                        window.top.changeSystemFrameSkin(data.oldSkin,skin);
                        window.top.changeSystemContentSkin(data.oldSkin,skin);
                        $(".skin-div").removeClass("cur-skin");
                        $(obj).find(".skin-div").addClass("cur-skin");
                    }
                }else{
                    alert(data.msg,0);
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL"),0);
            }
        });
    };

	return{
		init:_init,
        updateSystemSkin:_updateSystemSkin,
	};
}(window, jQuery);
