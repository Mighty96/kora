var main = {
    init : function () {
        var _this = this;
        $('#btn-signup').on('click', function() {
            _this.save();
        });
        $('#email').on('change', function() {
            _this.emailChk();
        });
        $('#pwd1').on('change', function() {
            _this.pwd1Chk();
        });
        $('#pwd2').on('change', function() {
            _this.pwd2Chk();
        });
        $('#nickname').on('change', function() {
            _this.nicknameChk();
        });
    },
    save : function () {
        var _this = this;
        if (_this.emailChk() && _this.pwd1Chk() && _this.pwd2Chk() && _this.nicknameChk() )
        {
            var data = {
                email: $('#email').val(),
                password: $('#pwd1').val(),
                nickname: $('#nickname').val()
            };

            $('#btn-signup').prop('disabled', true);
            $('#btn-signup').prop('value', "처리중...");

            $.ajax({
                type: 'POST',
                url: '/api/user/signup/ninda',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: function() {
                    swal({
                        title: "이메일로 인증메일이 발송되었습니다.",
                        icon: "success"
                    })
                    .then(() =>{
                        window.location.href='/';
                    });
                },
                error: function(e) {
                    $('#btn-signup').prop('disabled', false);
                    $('#btn-signup').prop('value', "등록");
                    alert(JSON.stringify(error));
                }
            });
        } else {
            alert('양식에 맞지 않습니다. 다시 작성해주세요.');
        }
    },
    emailChk : function () {
        var _this = this;
        var data = {
            email: $('#email').val()
        };

        if (_this.isEmail($('#email').val()) == false) {
            $('.email_blank').css("display", "none");
            $('.email_check_success').css("display", "none");
            $('.email_check_duplicated').css("display", "none");
            $('.email_check_fail').css("display", "inline-block");
            return false;
        }
        $.ajax({
            type : 'POST',
            url : '/api/user/signup/emailChk',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function(result) {

                $('.email_blank').css("display", "none");

                if(result == 'success') {
                    $('.email_check_success').css("display", "inline-block");
                    $('.email_check_duplicated').css("display", "none");
                    $('.email_check_fail').css("display", "none");
                    return true;
                } else {
                    $('.email_check_duplicated').css("display", "inline-block");
                    $('.email_check_success').css("display", "none");
                    $('.email_check_fail').css("display", "none");
                    return false;
                }
            }
        });
        return true;
    },
    isEmail : function (email) {
        var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        return regExp.test(email);
    },
    pwd1Chk : function () {
        var _this = this;
        var pwd1 = $('#pwd1').val();

        $('.pwd1_blank').css("display", "none");

        if(_this.isPassword(pwd1) == true) {
            $('.pwd1_check_success').css("display", "inline-block");
            $('.pwd1_check_fail').css("display", "none");
            return true;
        } else {
            $('.pwd1_check_fail').css("display", "inline-block");
            $('.pwd1_check_success').css("display", "none");
            return false;
        }
        return true;
    },
    pwd2Chk : function () {
        var pwd1 = $('#pwd1').val();
        var pwd2 = $('#pwd2').val();

        $('.pwd2_blank').css("display", "none");

        if(pwd1 == pwd2) {
            $('.pwd2_check_success').css("display", "inline-block");
            $('.pwd2_check_fail').css("display", "none");
            return true;
        } else {
            $('.pwd2_check_fail').css("display", "inline-block");
            $('.pwd2_check_success').css("display", "none");
            return false;
        }
        return true;
    },
    isPassword : function (pwd) {
        var regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;
        return regExp.test(pwd);
    },
    nicknameChk : function () {
        var nickname = $('#nickname').val();

        $('.nickname_blank').css("display", "none");
        if(nickname.length >=2 && nickname.length<=12) {
          $('.nickname_check_success').css("display", "inline-block");
          $('.nickname_check_fail').css("display", "none");
          return true;
        } else {
          $('.nickname_check_success').css("display", "none");
          $('.nickname_check_fail').css("display", "inline-block");
          return false;
        }
        return true;
    }
};

main.init();