var MfCusCreditApply_submitInput = function (window, $) {
    var _init = function () {
        _kindEcho();
    };

    /** 产品回显 */
    var _kindEcho = function () {
        $.each(porductList, function (idx, obj) {
            var $tr;
            if (idx == 0) {
                $tr = $(".newPro");
            } else {
                $("#newButton button").click();
                $tr = $(".addPro").last();
            }

            $tr.find("input[name^='kindNo']").val(obj.kindNo);
            $tr.find("input[name^='kindNo']").parent("div").find(".pops-value").text(obj.kindName);
            $tr.find("input[name^='creditAmt']").val( Pss.fmoney(obj.creditAmt, 2) );
        });

        $("#newButton").hide();
        $(".addPro #delButton").hide();
    };

    return {
        init: _init
    };
}(window, jQuery);
