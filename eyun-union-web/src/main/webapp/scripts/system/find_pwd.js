/* 手机找回密码 */
var mb_pswd = {
    s: 60, t: null,
    init: function () {// 绑定回车事件 优化用户操作
        document.onkeydown = function (event) {
            event = event || window.event;
            if (event.keyCode == 13) {
                this.find_pwd();
            }
            return;
        }.bind(this);
    },
    // 手机验证已通过
    chk_mobile: function () {
        var mobile = $('#mobile');
        mobile.next('span').html('');
        if ($.trim(mobile.val()) == '') {
            mobile.next('i').html('请输入手机号');
            return false;
        } else if (!/^1[3,5,8,4,7]\d{5}\d{4}?$/.test(mobile.val())) {//^1[3,4,5,6,7,8,9][0-9]{9}$
            mobile.next('i').html('请输入正确的手机号');
            return false;
        }
        return true;
    },
    getcode: function () {
        var mobile = $('#mobile');
        var userName = $('#userName');
        if (this.chk_mobile() === false) return false;
        // 检查号码 并发送验证码
        var response = nova_request(ctx + '/system/user/pwd_login.do', {
            mobile: mobile.val(),
            userName: userName.val()
        }, 'post');
        //如果未注册易韵联盟 不给发送短信 减少短信开支
        if (response == -1) {
            mobile.next('i').html('易盟号或手机号码错误');
        } else {
            mobile.next('i').html("");
            this.sendReq();//发送
        }
        return;
    },
    wait: function () {
        var code = $('#code');
        var sendCode = $("#sendCode");
        this.s = this.s - 1;
        if (this.s == 0) {
            code.next('u').html('');
            sendCode.attr("disabled",false);
            this.s = 60;
            clearTimeout(this.t);
            return;
        }
        code.next('u').html(this.s + '秒后重新获取');
    },
    sendReq: function () {
        var mobile = $('#mobile');
        var code = $('#code');
        var userName = $("#userName");
        var sendCode = $("#sendCode");
        sendCode.attr("disabled",true);
        // wait
        this.t = setInterval("mb_pswd.wait()", 1000);
        var response = nova_request(ctx + '/system/user/sendCode_login.do', {mobile: mobile.val(),userName:userName.val()}, 'post');
        if (response != 1) {
            var eMsg = "" + (response == -4 ? '请求频繁，请稍后再试' : '短信发送失败');
            sendCode.attr("disabled",false);
            mobile.next('i').html(eMsg);
            code.next('u').html('');
            clearTimeout(this.t);
            //return;
        } else {
            mobile.next('span').html('');
        }
    },
    find_pwd: function () {
        var mobile = $('#mobile');
        var code = $('#code');
        var mbs = this.chk_mobile();
        if (mbs === true) {
            var response = nova_request(ctx + '/system/user/verifyCode_login.do', {
                code: code.val(),
                mobile: mobile.val()
            }, 'post');
            if (response != 0) {
                mobile.next('i').html("验证失败，请检查手机号或重新发送短信");
                return;
            } else {
                $('#get_password').submit();
                return;
            }
        }
    }
};
//ajax请求
function nova_request(url, param, method) {
    var callback = "";
    $.ajax({
        async: false,
        type: method,
        url: url,
        data: param,
        dataType: 'json',
        success: function (data) {
            callback = data;
        }
    });
    return callback;
}
//重置密码
var pwdreset = {
    _pwd: null,
    _re_pwd: null,
    pwd: null,
    re_pwd: null,
    init: function () {
        this._pwd = $("#pwd");
        this._re_pwd = $("#re_pwd");
        this._pwd.keyup(this.count_psd );
        this._pwd.blur(this.chkpwdreset );
        this._re_pwd.blur(this.chk_repwdreset );
        this._re_pwd.focus(this.op_repwdreset);
    },
    chkpwdreset: function () {
        var password = pwdreset._pwd;
        var re_password = pwdreset._re_pwd;
        password.next('.msg').html('');
        password.next('span').removeClass('error');
        password.next('span').removeClass('pass');
        password.removeClass('form_input_error');
        var msg = 'init';

        if (password.val() == '') {
            msg = '请输入密码';
        } else if (password.val().length > 16) {
            msg = '密码不能超过16位 ';
        } else if (password.val().length < 6) {
            msg = '密码至少要有6位';
        } else if (re_password.val() != '' && password.val() != '' && re_password.val() != password.val()) {//两次密码输入不正确
            msg = '两遍输入的密码不一致';
        } else if (pwdreset.chk_zh(password.val()) == -1) {
            msg = '包含非法字符';
        } else if (password.val().length > 5 && password.val().length < 17 && re_password.val() == password.val()) {//两次密码输入正确
            password.next('span').addClass('pass').html('<span class="ico__pass"></span>');
            pwdreset.pwd_ok(password, re_password);
            pwdreset.pwd = true;
            return true;
        } else if (re_password.val().length == 0 && password.val().length >= 6 && password.val().length <= 16 && password.val().length != 0) {//只输入密码A
            pwdreset.count_psd();
            pwdreset.pwd = true;
            return true;
        }
        if (msg != 'init') {
            password.next('i').html(msg);
            password.addClass('form_input_error');
            re_password.next('i').html('');
            pwdreset.pwd = false;
            return false;
        }
    },
    chk_repwdreset: function () {
        var passwd = pwdreset._pwd;
        var re_pswd = pwdreset._re_pwd;
        //re_pswd.next('span').removeClass('error');
        //re_pswd.removeClass('form_input_error');
        //re_pswd.next('.msg').html();
        var msg = 'init';

        if (re_pswd.val() == '') {
            msg = '请再输入一遍密码';
        } else if (re_pswd.val().length < 6) {
            msg = '密码至少要有6位';
        } else if (pwdreset.chk_zh(re_pswd.val()) == -1) {
            msg = "包含非法字符";
        } else if (re_pswd.val().length>=6 && re_pswd.val() != passwd.val() && passwd.val() != '') {//两次密码输入不正确
            msg = "两遍输入的密码不一致";
            passwd.next('i').html(msg);
            passwd.addClass('form_input_error');
            pwdreset.re_pwd = false;
            return false;
        } else if (re_pswd.val() != passwd.val() && passwd.val() == '') {//只输入如B
            passwd.next('i').html('');
        } else if (re_pswd.val() == passwd.val() && re_pswd.val().length > 5 && re_pswd.val().length < 17) {//两次密码正确
            //$('.RackPW_list u').css({"width":"45px","height":"20px","padding-left": "25px","line-height":"51px"});
            //re_pswd.next('u').html("OK");
            //$('.RackPW_list u').html("OK");
            pwdreset.pwd_ok(passwd, re_pswd);
            pwdreset.re_pwd = true;
            return true;
        }
        if (msg != 'init') {
            //passwd.next('span').removeClass('pass');
            //re_pswd.addClass('form_input_error');
            re_pswd.next('i').html(msg);
            pwdreset.re_pwd = false;
            return false;
        }
    },
    pwd_ok: function (ps, rps) {
        ps.next('i').html("");
        //ps.removeClass("form_input_error");
        pwdreset.count_psd();
        //ps.next().addClass("pass");
        ps.next('i').html("");
    },
    op_repwdreset: function () {
        var re_pswd = pwdreset._re_pwd;
        re_pswd.next('i').html('');
    },
    count_psd: function(){
        var password = pwdreset._pwd;
        //password.next('span').removeClass('error').html('');
        var chars = pwdreset.chars(password.val());
        if(password.val().length>0 && password.val().length<6){
            $('.RackPW_strength').html('密码强度：弱<span class="pswd_state pswd_state_def"><span class="level level_0"></span><span class="level level_0_1"></span><span class="level level_0_2"></span></span><span class="pswd_result">强</span>');
        }else if( 9>=password.val().length && password.val().length>=6 && chars==1 ){
            $('.RackPW_strength').html("密码强度：<span class='pswd_state'><span class='level level_1'></span></span><span class='pswd_result'>弱</span>");
            return false;
        }else if( (9>=password.val().length && password.val().length>=6 && chars==2) || (16>=password.val().length && password.val().length>=10 && chars==1) ){
            $('.RackPW_strength').html("密码强度：<span class='pswd_state'><span class='level level_1'></span><span class='level level_2'></span></span><span class='pswd_result'>中</span>");
            return false;
        }else if( (9>=password.val().length && password.val().length>=6 && chars==3) || (16>=password.val().length && password.val().length>=10 && chars==2) ){
            $('.RackPW_strength').html( "密码强度：<span class='pswd_state'><span class='level level_1'></span><span class='level level_2'></span><span class='level level_3'></span></span><span class='pswd_result'>强</span>");
            return;
        }else if(16<password.val().length){
            password.next('i').html('密码不能超过16位');
            //password.addClass('form_input_error');
            return false;
        }
        return true;
    },
    chars: function(str){
        var a=0,b=0,c=0;
        for(var i=0;i<str.length;i++){
            if(str.charAt(i).charCodeAt(0)>=48 && str.charAt(i).charCodeAt(0) <=57){//数字
                a = 1;
            }else if( (str.charAt(i).charCodeAt(0)>=65 && str.charAt(i).charCodeAt(0)<=90) || (str.charAt(i).charCodeAt(0)>=97 && str.charAt(i).charCodeAt(0)<=122) ){//字母
                b = 1;
            }else{//符号
                c = 1;
            }
        }
        return (a+b+c);
    },
    chk_zh: function(p){
        for(var i=0;i<p.length;i++){
            var code = p.charAt(i).charCodeAt(0);
            if(code<33 || code>126){
                return -1;
            }
        }
        return 1;
    }
};

var Setpwd = {
    init: function(){
        var passwd = $('#pwd');
        var re_passwd = $('#re_pwd');
        passwd.focus();
        pwdreset._pwd = passwd;
        pwdreset._re_pwd = re_passwd;

        passwd.focus(function(){
            $('.msg').hide();
        });
        passwd.blur(pwdreset.chkpwdreset);
        passwd.keyup(pwdreset.count_psd);

        re_passwd.focus(function(){
            $('.msg').hide();
        });
        re_passwd.blur(pwdreset.chk_repwdreset);
    },
    setpwd: function(){
        if(pwdreset.chkpwdreset()==true && pwdreset.chk_repwdreset()==true){
            //$('setpwd').submit();
            var pwd = $('#pwd').val();
            //var r_pwd = $('re_pswd').value;
            var code = $('#code').val();
            var mobile = $('#mobile').val();
            var userName = $('#userName').val();
            var response = nova_request(ctx + '/system/user/resetPassword_login.do', {
                code: code,
                mobile: mobile,
                userName: userName,
                password: pwd
            }, 'post');
            if(response == -1) {
                $('#pwd').next('i').html('系统异常');
            }else if(response == -2){
                $('#pwd').next('i').html("验证码已过期，请"+"<a href ='"+ctx+"/system/user/find_pwd_login.do'>重新验证</a>");
            }else {
                location.href = ctx+"/";
            }
        }
    }
};