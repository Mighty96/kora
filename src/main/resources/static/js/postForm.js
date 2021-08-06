$(function() {
    CKEDITOR.replace('context',{
        filebrowserUploadUrl: '/api/upload',
        font_names : "GmarketSans/GmarketSans",
        font_defaultLabel : "GmarketSans/GmarketSans",
        fontSize_defaultLabel : "12",
        language : "ko"
    });
});

var main = {
    init : function () {
        var _this = this;
        $('#btn-create').on('click', function() {
            _this.save();
        }),
        $('#btn-modify').on('click', function() {
            _this.modify();
        })
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            context: CKEDITOR.instances.context.getData(),
            board: $('#board-id').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function(data) {
                window.location.href='/board/' + $('#board-id').val() + '/' + data + '?page=0&size=30';
            }
        });
    },
    modify : function () {
        var data = {
            title: $('#title').val(),
            context: $('#context').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/posts/' + $('#id').val(),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                Swal.fire({
                    title: "게시글을 수정했어요!",
                    icon: "success"
                })
                .then(() =>{
                    window.location.href= '/board/' + $('#board').val() + '/' + $('#id').val();
                });
            },
            error: function(e) {
                alert('몰라');
            }
        });
    }
};

main.init();