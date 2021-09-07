var main = {
    init : function () {
        var _this = this;
        $('#btn-signup').on('click', function() {
            _this.save();
        });
        $('#nickname').on('change', function() {
            _this.nicknameChk();
        });
    },
    save : function () {
        var _this = this;
        if (_this.nicknameChk())
        {
            var data = {
                nickname: $('#nickname').val()
            };
            $.ajax({
                type: 'POST',
                url: '/api/users/signup/oauth',
                dataType: 'text',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: function() {
                    Swal.fire({
                        title: "회원가입을 축하합니다! 다시 로그인해주세요.",
                        icon: "success"
                    })
                    .then(() =>{
                        window.location.href='/logout';
                    });
                },
                error: function(e) {
                    alert(JSON.stringify(e));
                }
            });
        } else {
            Swal.fire({
                title: '양식에 맞지 않습니다. 다시 작성해주세요.',
                icon: "error"
            });
        }
    },
    nicknameChk : function () {
        var _this = this;
        var data = {
            nickname: $('#nickname').val()
        };

        if (data.nickname.length < 2 || data.nickname.length > 12) {
            $('.nickname_blank').css("display", "none");
            $('.nickname_check_success').css("display", "none");
            $('.nickname_check_duplicated').css("display", "none");
            $('.nickname_check_fail').css("display", "inline-block");
            return false;
        }
        $.ajax({
            type : 'POST',
            url : '/api/users/nicknameChk',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function(result) {

                $('.nickname_blank').css("display", "none");

                if(result == 'success') {
                    $('.nickname_check_success').css("display", "inline-block");
                    $('.nickname_check_duplicated').css("display", "none");
                    $('.nickname_check_fail').css("display", "none");
                    return true;
                } else {
                    $('.nickname_check_duplicated').css("display", "inline-block");
                    $('.nickname_check_success').css("display", "none");
                    $('.nickname_check_fail').css("display", "none");
                    return false;
                }
            }
        });
        return true;
    }
};

main.init();