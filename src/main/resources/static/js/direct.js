var main = {
    init : function () {
        var _this = this;
        $('#btn-impression').on('click', function() {
            _this.save();
        });
    },
    save : function () {
        var data = {
            context: $('#context').val(),
            directId: $('#id').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/impression',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                swal({
                    title: "소감을 등록했어요!",
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
    $('#rid' + _rid).replaceWith(html);
    $('#rid' + _rid).focus();
}

function update(_rid) {
    var data = {
        context: $('#update').val()
    }
    $.ajax({
        type: 'POST',
        url: '/api/impression/' + _rid,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data),
        success: function() {
            swal({
                title: "소감을 수정했어요!",
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
        url: '/api/impression/' + _rid,
        success: function() {
            swal({
                title: "소감을 삭제했어요!",
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

function directLikeUp() {
    $.ajax({
        type: 'GET',
        url: '/api/direct/' + $('#id').val() + '/like',
        success: function() {
            swal({
                title: "닌텐도 다이렉트를 추천했어요!",
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

function directHateUp() {
    $.ajax({
        type: 'GET',
        url: '/api/direct/' + $('#id').val() + '/hate',
        success: function() {
            swal({
                title: "닌텐도 다이렉트를 비추천했어요!",
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

function reLikeUp(impressionId) {
    $.ajax({
        type: 'GET',
        url: '/api/impression/' + impressionId + '/like',
        success: function() {
            swal({
                title: "소감을 추천했어요!",
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

function reHateUp(impressionId) {
    $.ajax({
        type: 'GET',
        url: '/api/impression/' + impressionId + '/hate',
        success: function() {
            swal({
                title: "소감을 비추천했어요!",
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