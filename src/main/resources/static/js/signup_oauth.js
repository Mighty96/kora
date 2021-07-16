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
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: function() {
                    Swal.fire({
                        title: "회원가입을 축하합니다!",
                        icon: "success"
                    })
                    .then(() =>{
                        window.location.href='/';
                    });
                },
                error: function(e) {
                    alert(JSON.stringify(e));
                }
            });
        } else {
            alert('양식에 맞지 않습니다. 다시 작성해주세요.');
        }
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