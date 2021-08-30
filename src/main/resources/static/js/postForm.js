$(function() {
    CKEDITOR.replace('context',{
        filebrowserUploadUrl: '/api/upload',
        font_names : "GmarketSans/GmarketSans",
        font_defaultLabel : "GmarketSans/GmarketSans",
        fontSize_defaultLabel : "12",
        height: 600,
        language : "ko"
    }),
    CKEDITOR.on('dialogDefinition', function (ev) {
        var dialogName = ev.data.name;
        var dialog = ev.data.definition.dialog;
        var dialogDefinition = ev.data.definition;
        if (dialogName == 'image') {
            dialog.on('show', function (obj) {
                this.selectPage('Upload'); //업로드텝으로 시작
            });
            dialogDefinition.removeContents('advanced'); // 자세히탭 제거
            dialogDefinition.removeContents('Link'); // 링크탭 제거
        }
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
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function(data) {
                Swal.fire({
                    title: "게시글을 등록했어요!",
                    icon: "success"
                })
                .then(() =>{
                    window.location.href='/board/' + $('#board-id').val() + '/' + data + '?page=0';
                });
            },
            error: function(e) {
                alert('몰라');
            }
        });
    },
    modify : function () {
        var data = {
            title: $('#title').val(),
            context: CKEDITOR.instances.context.getData(),
        };
        $.ajax({
            type: 'POST',
            url: '/api/posts/' + $('#id').val(),
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                Swal.fire({
                    title: "게시글을 수정했어요!",
                    icon: "success"
                })
                .then(() =>{
                    window.location.href= '/board/' + $('#board').val() + '/' + $('#id').val() + '?page=0';
                });
            },
            error: function(e) {
                alert('몰라');
            }
        });
    }
};

main.init();