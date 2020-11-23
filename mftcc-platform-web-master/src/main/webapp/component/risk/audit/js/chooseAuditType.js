;
var chooseAuditType = function(window, $) {


	var _submitForm = function(appId,auditType) {
        var url = webPath+'/mfBusRiskAudit/pushMessage';
        LoadingAnimate.start();
        $.ajax({
            url : url,
            data : { appId : appId ,auditType:auditType},
            type : 'post',
            dataType : 'json',
            async:false,
            success : function(data) {
                if (data.flag == "success") {
                    LoadingAnimate.stop();
                    window.top.alert(data.msg, 3);
                    top.flag = true;
                    myclose_showDialog();
                } else {
                    alert(top.getMessage(data.msg), 0);
                    myclose_showDialog();
                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_SAVE"), 0);
            }

        });
        myclose_showDialog();
	};
	


	return {
		submitForm : _submitForm

	};
}(window, jQuery);
