;
var queryDisagree_reconListPage = function(window,$){
	var _doReconsider = function(obj,url){
        window.top.alert(top.getMessage("CONFIRM_COMMIT"),2,function(){
            $.ajax({
                url:url,
                type:'post',
                data:{},
                beforeSend:function(){
                    LoadingAnimate.start();
                },success:function(data){
                    if(data.flag="success") {
                        window.top.alert(data.msg, 3);
                        window.location.href=webPath+"/mfQueryDisagree/getReconListPage";
                    }else {
                        window.top.alert(data.msg, 0);
                    }
                },error:function(data){
                    window.top.alert(data.msg, 0);
                }
            });
        });

	};


    var _doReconsider2 = function(obj,url){

        window.top.alert("确定要申请吗?",2,function() {
          url = webPath + url  ;
          window.location.href = url;
        })

    };
	return{
        doReconsider:_doReconsider,
        doReconsider2:_doReconsider2
	};

}(window,jQuery);