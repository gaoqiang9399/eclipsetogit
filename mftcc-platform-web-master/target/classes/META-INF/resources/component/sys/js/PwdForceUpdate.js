var modifyPwd = function(window, $) {
    _init=function(){
        $.ajax({
            url:"/sysUser/pwdUpdteInfoForm",
            success:function(data){
                var html = data.htmlStr;
                $("#sysModifyPwd").empty().html(html);
                $("[name='opName']").val(opName);
                _addVerification();
            }
        });
    };
    //为密码验证加上隐藏字段
    var _addVerification = function() {
        $("input[name='password11']").parent(".input-group").append(
            "<div class='error hide' id='different'>两次输入不同</div>");
        $("input[name='password1']").parent(".input-group").append(
            "<div class='error hide' id='alike'>新旧 密码不能相同</div>");
        $("input[name='password1']")
            .parent(".input-group")
            .append(
                "<div class='error hide' id='password1'>密码必须是以字母开头的至少6位字母或数字</div>");
        $("input[name='password']").parent(".input-group").append(
            "<div class='error hide' id='password'>密码错误</div>");
    };

    // 正则表达式
    var uPattern = /^[a-zA-Z][a-zA-Z0-9]{5,15}$/;
    // 验证密码是否符合正则表达式
    _validate = function(obj) {
        if (trim($("input[name='password1']").val()) != ""&& !uPattern.test($("input[name='password1']").val())) {
            $('#password1').removeClass("hide");
            return false;
        } else {
            $('#password1').addClass("hide");
            return true;
        }
    };
    _trim = function(str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    };

    // 密码验证
    var _checkPwd = function(obj) {
        // 验证是否和原密码是否一致
        if ($("input[name='password']").val() == $("input[name='password1']").val()&&$("input[name='password1']").val()!=''&&$("input[name='password']").val()!='') {
            $('#alike').removeClass("hide");
            return false;
        } else {
            $('#alike').addClass("hide");
        }
        // 验证新密码是否一致
        if ($("input[name='password1']").val() != $("input[name='password11']").val()&&$("input[name='password1']").val()!=''&&$("input[name='password11']").val()!='') {
            $('#different').removeClass("hide");
            return false;
        } else {
            $('#different').addClass("hide");
        }
        return true;
    };

    // ajax验证原密码
    var _validatePwd = function(obj) {
        var pwdflag=false;
        $.ajax({
            url : webPath + "/sysUser/validatePassword",
            type : "POST",
            cache : false,
            data : {
                ajaxData : $("input[name='password']").val(),
            },
            async : false,
            success : function(data) {
                if (data.flag) {
                    $('#password').addClass("hide");
                    pwdflag= true;
                } else {
                    $('#password').removeClass("hide");
                    pwdflag= false;
                }
            }
        });
        return pwdflag;
    };

    //强制修改密码
    var _ajaxUpdate = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            if (_validatePwd(this)) {
                if (_validate(this)) {
                    if (_checkPwd(this)) {
                        $.ajax({
                            url : webPath+"/sysUser/pwdForceUpdate",
                            type : "POST",
                            cache : false,
                            data : {
                                opName : $("[name='opName']").val(),
                                ajaxData : $("input[name='password1']")
                                    .val()
                            },
                            success : function(data) {
                                if (data.flag) {
                                    alert(data.msg, 1);
                                    _sysQuit();
                                } else {
                                    alert(data.msg, 0);
                                }
                            }
                        });
                    }
                }
            }
        }
    };
    /**保存或者关闭执行的都是相同操作，返回到登录界面**/
   var _sysQuit=function(){
        window.top.location.href = webPath+"/sysLogin/logout.action";
    }
    return {
        init : _init,
        ajaxUpdate : _ajaxUpdate,
        checkPwd : _checkPwd,
        validate : _validate,
        validatePwd : _validatePwd,
        sysQuit:_sysQuit
    };
}(window, jQuery);
