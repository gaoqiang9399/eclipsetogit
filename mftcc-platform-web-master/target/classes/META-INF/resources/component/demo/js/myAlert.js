/**
 * Created by jzh on 2015/1/22.
 */
/*提示框
 * 参数  1.提示框信息  2.提示框名称
 * */
function GenerateHtml(type,msg,title){
    if(title==""||title===undefined){
        title="提示信息";
    }
    var ghtml = "";
    ghtml+='<div class="modal fade bs-example-modal-sm" id="myAlert" tabindex="-1" role="dialog" aria-hidden="true">'+
    '<div class="modal-dialog modal-sm">'+
    '<div class="modal-content">'+
    '<div class="modal-header">'+
    '<button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
    '<h4 class="modal-title">'+title+'</h4>'+
    '</div>'+
    '<div class="modal-body">'+msg+'</div>'+
    '<div class="modal-footer">'+
    '<button type="button" class="btn btn-default alertConfirm">确定</button>';
    if(type=="confirm"){
        ghtml+='<button type="button" class="btn btn-default alertClose">取消</button>';
    }
    ghtml+='</div>'+
    '</div>'+
    '</div>'+
    '</div>';
    $("body").append(ghtml);
    $("#myAlert").modal({
        backdrop:false,
        show:true,
        keyboard: false
    });
    $("body").append("<div class='modal-backdrop modal-backdrop1 fade in'></div>");
}
function ChooseHtml(msg,title,chooseNameArr){
    if(title==""||title===undefined){
        title="提示信息";
    }
    var ghtml = "";
    ghtml+='<div class="modal fade bs-example-modal-sm" id="myAlert" tabindex="-1" role="dialog" aria-hidden="true">'+
        '<div class="modal-dialog modal-sm">'+
        '<div class="modal-content">'+
        '<div class="modal-header">'+
        '<button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'+
        '<h4 class="modal-title">'+title+'</h4>'+
        '</div>'+
        '<div class="modal-body">'+msg+'</div>'+
        '<div class="modal-footer">';
        for(var i=0;i<chooseNameArr.length;i++){
            ghtml+='<button type="button" class="btn btn-default" id="alertChoose_'+i+'">'+chooseNameArr[i]+'</button>';
        }
        ghtml+='<button type="button" class="btn btn-default alertClose">取消</button>';
        ghtml+='</div>'+
    '</div>'+
    '</div>'+
    '</div>';
    $("body").append(ghtml);
    $("#myAlert").modal({
        backdrop:false,
        show:true,
        keyboard: false
    });
    $("body").append("<div class='modal-backdrop modal-backdrop1 fade in'></div>");
}
function myAlertConfirm(callback){
    $("#myAlert .alertConfirm").click(function(){
        $("#myAlert").modal("hide");
        $(".modal-backdrop1").remove();
        $("#myAlert").remove();
        if(typeof(callback) == 'function'){
            callback();
        }
    });
}
function myAlertChooseConfirm(callbackArr){
    for(var i=0;i<callbackArr.length;i++){
        var chooseStr = "#myAlert #alertChoose_"+i;
        $(chooseStr).click(function(e){
            var callBackNo =$(this).attr("id").split("_")[1];
            $("#myAlert").modal("hide");
            $(".modal-backdrop1").remove();
            $("#myAlert").remove();
            if(typeof(callbackArr[callBackNo]) == 'function'){
                callbackArr[callBackNo]();
            }
        });
    }

}

function myAlertCancel(){
    $("#myAlert .alertClose,#myAlert .close").click(function(){
        $("#myAlert").modal("hide");
        $(".modal-backdrop1").remove();
        $("#myAlert").remove();
    });
}
$(function(){
    $.myAlert = {
        Alert:function(msg,title){
            GenerateHtml("alert",msg,title);
            myAlertConfirm();
            myAlertCancel();
        },
        Confirm:function(msg,title,callback){
            GenerateHtml("confirm",msg,title);
            myAlertConfirm(callback);
            myAlertCancel();
        },
        Choose:function(msg,title,chooseNameArr,callbackArr){
            ChooseHtml(msg,title,chooseNameArr);
            myAlertChooseConfirm(callbackArr);
            myAlertCancel();
        }
    }
});