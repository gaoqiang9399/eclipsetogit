Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


utils = {
    httpurl: false ? "http://47.101.52.224:10808/" : "",
    data: {},
    post: function (data) {
        $.ajax({
            url: utils.httpurl + data.url,
            type: "POST",
            dataType: "JSON",
            contentType: "application/json",
            headers: utils.headers,
            data: JSON.stringify(data.params),
            async:data.async==undefined?true:data.async,
            beforeSend: function () {
                if (data.beforeSend) {
                    data.beforeSend()
                }
            },
            success: function (res) {
                data.success(res);
            },
            error: function (error) {
                if (error.responseJSON == undefined || error.responseJSON.status == 99) {
                    if (window.pageName != "login") {
                        window.location.href = "./restart.html";
                    }
                    return;
                }
                /*layer.msg('服务器异常', { icon: 2 });*/ // 错误
                console.log(error);
                if (data.error != undefined) {
                    data.error(error);
                }
            }
        });
    },
    get: function (data) {
        $.ajax({
            url: utils.httpurl + data.url,
            type: "GET",
            headers: utils.headers,
            success: function (res) {
                data.success(res);
            },
            error: function (error) {
                if (error.responseJSON.status == 99) {
                    layer.msg('重新登录 登录以失效', {icon: 2}); // 错误
                    if (window.pageName != "login") {
                        window.location.href = "./restart.html";
                    }
                    return;
                }
                layer.msg('服务器异常', {icon: 2}); // 错误
                if (data.error != undefined) {
                    data.error(error);
                }
            }
        });
    },
    getNew: function (data) {
        // debugger
        $.ajax({
            url: utils.httpurl + data.url,
            type: "GET",
            headers: utils.getHeaders,
            success: function (res) {
                data.success(res);
            },
            error: function (error) {
                if (error.responseJSON.status == 99) {
                    layer.msg('重新登录 登录以失效', {icon: 2}); // 错误
                    if (window.pageName != "login") {
                        window.location.href = "./restart.html";
                    }
                    return;
                }
                layer.msg('服务器异常', {icon: 2}); // 错误
                if (data.error != undefined) {
                    data.error(error);
                }
            }
        });
    },
    headers: {
        Accept: "application/json; charset=utf-8",
        token: localStorage.getItem('token')
    },
    getHeaders: {
        "Content-Type": "application/json; charset=utf-8",
        Accept: "application/json; charset=utf-8",
        token: localStorage.getItem('token')
    },
    formData: function (data) {
        $.ajax({
            url: utils.httpurl + data.url,
            type: "POST",
            dataType: "JSON",
            headers: {
                contentType: "application/x-www-form-urlencoded",
                token: getUrlParam('token')
            },
            success: function (res) {
                data.success(res);
            },
            error: function (error) {
                if (error.responseJSON.status == 99) {
                    layer.msg('重新登录 登录以失效', {icon: 2}); // 错误
                    if (window.pageName != "login") {
                        window.location.href = "./login.html";
                    }
                    return;
                }
                layer.msg('服务器异常', {icon: 2}); // 错误
                if (data.error != undefined) {
                    data.error(error);
                }
            }
        });
    },
    /**
     *
     * @param data 时间
     * @param str yyyy-mm-dd
     * @returns {string} 返回一个 如 2018-09-09
     */
    date: function (datetimeString, str) {
        if (datetimeString != null && datetimeString != "" && datetimeString != undefined) {
            datetimeString = new Date(datetimeString).format("yyyy-MM-dd hh:mm:ss")
        } else {
            datetimeString = new Date().format("yyyy-MM-dd hh:mm:ss")
        }
        if (str == "yyyy-mm-dd") {

            if (isNaN(Date.parse(datetimeString)))
                datetime = new Date(Date.parse(datetimeString.replace(/-/g, '/').replace(/T/g, ' ')));
            else
                datetime = new Date(Date.parse(datetimeString));

            var year = datetime.getFullYear();
            /*年*/
            var month = datetime.getMonth() + 1;
            /*月*/
            var date = datetime.getDate();
            /*日*/
            month = month < 10 ? '0' + month : month;
            date = date < 10 ? '0' + date : date;
            return year + '-' + month + '-' + date;
        }
    }


}

String.prototype.format = function () {
    if (arguments.length == 0) return this;
    var obj = arguments[0];
    var s = this;

    for (var index = 0; index < obj.length; index++) {
        var data = obj[index] == null ? "" : obj[index];
        s = s.replace(new RegExp("\\{\\{" + index + "\\}\\}", "g"), data);
    }
    return s;
}
if (!String.prototype.trim) {

    /*---------------------------------------
     * 清除字符串两端空格，包含换行符、制表符
     *---------------------------------------*/
    String.prototype.trim = function () {
        return this.replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "");
    }

}

//获取url中的参数，会中文乱码
function getUrlParam(name) {

    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) {
        return unescape(r[2]);
    } else {
        /*if (window.pageName != "login") {
            window.location.href = "./login.html";
        }*/
        return "";
        //返回参数值
    }
}

//数组去重
function uniq(array) {
    var temp = [];
    for (var i = 0; i < array.length; i++) {
        if (array.indexOf(array[i]) == i) {
            temp.push(array[i])
        }
    }
    return temp;
}

//浏览器缓存
function timestamp(url) {
    var getTimestamp = new Date().getTime();
    if (url.indexOf("?") > -1) {
        url = url + "&timestamp=" + getTimestamp
    } else {
        url = url + "?timestamp=" + getTimestamp
    }
    return url;
};

