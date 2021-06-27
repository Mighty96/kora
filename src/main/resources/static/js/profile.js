var main = {
    init : function () {
        var _this = this;
        $('#btn-update').on('click', function() {
            _this.update();
        });
        $('#nickname').on('change', function() {
            _this.nicknameChk();
        });
    },
    update : function () {
        var _this = this;
        if (_this.nicknameChk() )
        {
            var data = {
                nickname: $('#nickname').val(),
                introduction: $('#introduction').val()
            };

            $('#btn-update').prop('disabled', true);
            $('#btn-update').prop('value', "처리중...");

            $.ajax({
                type: 'PUT',
                url: '/api/user/update',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: function() {
                    swal({
                        title: "회원정보 수정 완료!",
                        icon: "success"
                    })
                    .then(() =>{
                        window.location.reload();
                    });
                },
                error: function(e) {
                    $('#btn-update').prop('disabled', false);
                    $('#btn-update').prop('value', "등록");
                    alert(JSON.stringify(error));
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