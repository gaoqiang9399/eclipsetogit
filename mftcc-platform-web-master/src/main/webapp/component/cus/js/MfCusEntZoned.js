var MfCusEntZoned = function(window,$){
    var _init = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    }
    /** 保存 */
    var _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            ajaxInsertCusForm(obj);
        }
    };

    var _getInput = function (obj,url){
        top.openBigForm(url,"企业划型详情", function(){
            window.location.reload();
        });
    };

    var _getStrHtml = function (mfCusEntZonedList){
        var html = '<table  id="tablist" width="100%" border="0"   align="center" cellspacing="1" class="ls_list" title="tablebusrecourseapply">' +
            '<colgroup></colgroup>' +
            '<colgroup></colgroup>' +
            '<colgroup></colgroup>' +
            '<colgroup></colgroup>' +
            '<colgroup style="display:none"></colgroup>' +
            '<colgroup style="display:none"></colgroup>' +
            '<colgroup style="display:none"></colgroup>' +
            '<colgroup style="display:none"></colgroup>' +
            '<colgroup></colgroup>' +
            '<thead> ' +
            '<tr> ' +
            '<th scope="col"  align="center" name="amtSum" sortType="2">总资产</th>' +
            '<th scope="col"  align="center" name="incomeSum" sortType="0">总收入</th>' +
            '<th scope="col"  align="center" name="ext1" sortType="0">人数</th>' +
            '<th scope="col"  align="center" name="wayClassDes" sortType="0">行业</th>' +
            '<th scope="col"  align="center" name="oldProjSize" sortType="0">原企业规模</th>' +
            '<th scope="col"  align="center" name="newProjSize" sortType="0">新企业规模</th>' +
            '<th scope="col"  align="center" name="remark" sortType="0">说明</th>' +
            '<th scope="col"  align="center" name="regTime">创建时间</th>' +
            '</tr>' +
            ' <tbody id="tab"> ' +
            ' <c:forEach items='+mfCusEntZonedList+' var="item">' +
            '<tr>' +
            '<td align="center">item.amtSum</td>' +
            '<td align="center">item.incomeSum</td>' +
            '<td align="center">item.ext1</td>' +
            '<td align="center">item.wayClassDes</td>' +
            '<td align="center">item.oldProjSize</td>' +
            '<td align="center">item.newProjSize</td>' +
            '<td align="center">item.remark</td>' +
            '<td align="center">item.regTime</td>' +
            '</tr>' +
            '</c:forEach>'+
            ' </tbody> ' +
            '</thead>' +
            '</table>';
        $(".block-add").after(html);
    }

    return {
        init : _init,
        ajaxSave : _ajaxSave,
        getInput : _getInput,
        getStrHtml : _getStrHtml
    };
}(window, jQuery);