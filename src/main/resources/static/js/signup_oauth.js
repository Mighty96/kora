var main = {
    init : function () {
        var _this = this;
        $('#btn-signup').on('click', function() {
            _this.save();
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
        if (_this.birthdayChk() && _this.nicknameChk() )
        {
            var data = {
                birthday: $('#birthday').val(),
                nickname: $('#nickname').val()
            };
            $.ajax({
                type: 'POST',
                url: '/api/signup/user_oauth',
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