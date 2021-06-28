var main = {
    init : function () {
        var _this = this;
        $('#btn-newPassword').on('click', function() {
            _this.send();
        });
    },
    send : function () {
        var data = {
            email: $('#email').val()
        };

        $('#btn-newPassword').prop('disabled', true);
        $('#btn-newPassword').prop('value', "처리중...");
        $.ajax({
            type: 'POST',
            url: '/api/users/newPassword',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                swal({
                    title: "이메일을 확인해주세요.",
                    icon: "success"
                })
                .then(() =>{
                    window.location.href='/';
                });
            },
            error: function(e) {
                swal({
                    title: "이메일을 찾을 수 없습니다.",
                    icon: "error"
                })
                .then(() =>{
                    window.location.href='/newPassword';
                    $('#btn-newPassword').prop('disabled', false);
                    $('#btn-newPassword').prop('value', "임시비밀번호 발급");
                })

            }
        });
    }
};

main.init();