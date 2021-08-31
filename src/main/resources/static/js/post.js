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
            postId: $('#post-id').val(),
            commentId: 0
        };
        $.ajax({
            type: 'POST',
            url: '/api/comments',
            dataType: 'text',
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
            error: function(request) {
                var outputMessage = JSON.parse(request.responseText).outputMessage;
                Swal.fire({
                    title: outputMessage,
                    icon: "error"
                 });
            }
        });
    }
};

function re_save(commentId) {
    var data = {
        context: $('#re-context').val(),
        postId: $('#post-id').val(),
        commentId: commentId
    };
    $.ajax({
        type: 'POST',
        url: '/api/comments',
        dataType: 'text',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function() {
            Swal.fire({
                title: "대댓글을 등록했어요!",
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

function post_del() {
    var board = $('#board').val();

    Swal.fire({
        title: '게시글을 삭제하시나요?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                 type: 'DELETE',
                 url: '/api/posts/' + $('#post-id').val(),
                 success: function() {
                     Swal.fire({
                         title: "게시글을 삭제했어요.",
                         icon: "success"
                     })
                     .then(() =>{
                         window.location.href= '/board/' + board + '?page=0';
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
        dataType: 'text',
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
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
             });
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
        error: function(request) {
            var outputMessage = JSON.parse(request.responseText).outputMessage;
            Swal.fire({
                title: outputMessage,
                icon: "error"
             });
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

function reCommentBox(commentId) {
    $('#re-comment-box').remove();

    var reCommentBoxHtml =
    '<div id="re-comment-box" class="re-comment-form">' +
        '<div class="re-comment-mini-container p-2" style="border-bottom: none!important; border-left: none!important;">' +
            '<textarea class="comment-context" id="re-context" placeholder="대댓글"></textarea>' +
        '</div>' +
        '<div style="display: inline-block; width: 100%;">' +
             '<input onclick="re_save(' + commentId + ')" type="button" class="comment-input-button" id="btn-re-comment" value="댓글 남기기">' +
        '</div>' +
    '</div>'

    $('#' + commentId).after(reCommentBoxHtml);
}

main.init();