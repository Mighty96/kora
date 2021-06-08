var main = {
    init : function () {
        var _this = this;
        $('#btn-login').on('click', function() {
            _this.login();
        });
    },
    login : function () {
        var data = {
            email: $('#email').val(),
            password: $('#pwd').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                window.location.href='/';
            },
            error: function(e) {
                alert('이메일 또는 비밀번호가 올바르지 않습니다.');
                window.location.href='/signin';
            }
        });
    }
};

main.init();