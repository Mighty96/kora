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
                swal({
                    title: "로그인 성공!",
                    icon: "success"
                })
                .then(() =>{
                    window.location.href='/';
                });
            },
            error: function(e) {
                swal({
                    title: "이메일 또는 비밀번호를 확인해주세요.",
                    icon: "error"
                })
                .then(() =>{
                    window.location.href='/signin';
                })

            }
        });
    }
};

main.init();