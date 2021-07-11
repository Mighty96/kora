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
            postId: $('#post-id').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                Swal.fire({
                    title: "댓글을 등록했어요!",
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

function post_del() {
    $.ajax({
         type: 'DELETE',
         url: '/api/posts/' + $('#post-id').val(),
         success: function() {
             Swal.fire({
                 title: "게시글을 삭제했어요.",
                 icon: "success"
             })
             .then(() =>{
                 window.location.href= '/board/' + $('#board').val();
             });
         },
         error: function(e) {
             alert('몰라');
         }
    });
}

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
        url: '/api/comments/' + _rid,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function() {
            Swal.fire({
                title: "댓글을 수정했어요!",
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
        url: '/api/comments/' + _rid,
        success: function() {
            Swal.fire({
                title: "댓글을 삭제했어요!",
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

function postLikeUp() {
    $.ajax({
        type: 'GET',
        url: '/api/posts/' + $('#post-id').val() + '/like',
        success: function() {
            Swal.fire({
                title: "게시글을 추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function postHateUp() {
    $.ajax({
        type: 'GET',
        url: '/api/posts/' + $('#post-id').val() + '/hate',
        success: function() {
            Swal.fire({
                title: "게시글을 비추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function reLikeUp(commentId) {
    $.ajax({
        type: 'GET',
        url: '/api/comments/' + commentId + '/like',
        success: function() {
            Swal.fire({
                title: "댓글을 추천했어요!",
                icon: "success"
            }).then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

function reHateUp(commentId) {
    $.ajax({
        type: 'GET',
        url: '/api/comments/' + commentId + '/hate',
        success: function() {
            Swal.fire({
                title: "댓글을 비추천했어요!",
                icon: "success"
            })
            .then(() =>{
                window.location.reload();
            });
        },
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
            });
        }
    });
}

main.init();