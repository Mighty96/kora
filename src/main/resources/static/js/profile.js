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
                url: '/api/users',
                dataType: 'text',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                success: function() {
                    Swal.fire({
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
            Swal.fire({
                title: '양식에 맞지 않습니다. 다시 작성해주세요.',
                icon: "error"
            });
        }
    },
    nicknameChk : function () {
        var data = {
            nickname: $('#nickname').val()
        };

        if (data.nickname == $('#pre_nickname').val()) {
            $('.nickname_blank').css("display", "inline-block");
            $('.nickname_check_success').css("display", "none");
            $('.nickname_check_duplicated').css("display", "none");
            $('.nickname_check_fail').css("display", "none");
            return true;
        }

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