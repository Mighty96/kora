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
        $('#familyName').on('change', function() {
            _this.familyNameChk();
        });
        $('#givenName').on('change', function() {
            _this.givenNameChk();
        });
        $('#birthday').on('change', function() {
            _this.birthdayChk();
        });
        $('#nickname').on('change', function() {
            _this.nicknameChk();
        });
    },
    save : function () {
        var _this = this;
        if (_this.emailChk() && _this.pwd1Chk() && _this.pwd2Chk() && _this.familyNameChk() && _this.givenNameChk() && _this.birthdayChk() && _this.nicknameChk() )
        {
            var data = {
                email: $('#email').val(),
                password: $('#pwd1').val(),
                familyName: $('#familyName').val(),
                givenName: $('#givenName').val(),
                birthday: $('#birthday').val(),
                nickname: $('#nickname').val()
            };
            $.ajax({
                type: 'POST',
                url: '/api/signup/user',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('회원가입을 축하합니다!');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
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
            url : '/api/signup/userEmailChk',
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
    familyNameChk : function () {
        var familyName = $('#familyName').val();

        if(familyName.length == 0) {
            $('.family_name_blank').css("display", "none");
            $('.family_name_check_blank').css("display", "inline-block");
            return false;
        } else {
            $('.family_name_blank').css("display", "inline-block");
            $('.family_name_check_blank').css("display", "none");
            return true;
        }
        return true;
    },
    givenNameChk : function () {
        var givenName = $('#givenName').val();

        if(givenName.length == 0) {
          $('.given_name_blank').css("display", "none");
          $('.given_name_check_blank').css("display", "inline-block");
          return false;
        } else {
          $('.given_name_blank').css("display", "inline-block");
          $('.given_name_check_blank').css("display", "none");
          return true;
        }
        return true;
    },
    birthdayChk : function () {
        var birthday = $('#birthday').val();

        if(birthday.length == 0) {
          $('.birthday_blank').css("display", "none");
          $('.birthday_check_blank').css("display", "inline-block");
          return false;
        } else {
          $('.birthday_blank').css("display", "inline-block");
          $('.birthday_check_blank').css("display", "none");
          return true;
        }
        return true;
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