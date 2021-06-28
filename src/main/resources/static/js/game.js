var main = {
    init : function () {
        var _this = this;
        $('#btn-comment').on('click', function() {
            _this.save();
        });
    },
    save : function () {
        var data = {
            context: $('#context').val(),
            gameId: $('#id').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/oneLineComments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                swal({
                    title: "한줄평을 등록했어요!",
                    icon: "success"
                })
                .then(() =>{
                    window.location.reload();
                });
            },
            error: function(e) {
                alert('몰라');
            }
        });
    }
};

function update_mode(_rid) {
    var html= "";
    html += '<textarea class="form-control" id="update" placeholder="댓글">' + $('#rid' + _rid).text() + '</textarea>'
    html += '<button onclick="update(' + _rid + ')" type="button" class="btn btn-success" id="btn-update">완료</button>'
    html += '<button onclick="window.location.reload()" type="button" class="btn btn-danger" id="btn-update-cancel">취소</button>'
    console.log(html);
    $('#rid' + _rid).replaceWith(html);
    $('#rid' + _rid).focus();
}

function update(_rid) {
    var data = {
        context: $('#update').val()
    }
    $.ajax({
        type: 'POST',
        url: '/api/oneLineComments/' + _rid,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function() {
            swal({
                title: "한줄평을 수정했어요!",
                icon: "success"
            })
            .then(() =>{
                window.location.reload();
            });
        },
        error: function(e) {
            alert('몰라');
        }
    });
}

function del(_rid) {
    $.ajax({
        type: 'DELETE',
        url: '/api/oneLineComments/' + _rid,
        success: function() {
            swal({
                title: "한줄평을 삭제했어요!",
                icon: "success"
            })
            .then(() =>{
                window.location.reload();
            });
        },
        error: function(e) {
            alert('몰라');
        }
    });
}

function gameLikeUp() {
    $.ajax({
        type: 'GET',
        url: '/api/games/' + $('#id').val() + '/like',
        success: function() {
            swal({
                title: "게임을 추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            swal({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function gameHateUp() {
    $.ajax({
        type: 'GET',
        url: '/api/games/' + $('#id').val() + '/hate',
        success: function() {
            swal({
                title: "게임을 비추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            swal({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function reLikeUp(commentId) {
    $.ajax({
        type: 'GET',
        url: '/api/oneLineComments/' + commentId + '/like',
        success: function() {
            swal({
                title: "한줄평을 추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            swal({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function reHateUp(commentId) {
    $.ajax({
        type: 'GET',
        url: '/api/oneLineComments/' + commentId + '/hate',
        success: function() {
            swal({
                title: "한줄평을 비추천했어요!",
                icon: "success"
            })
            .then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            swal({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

main.init();