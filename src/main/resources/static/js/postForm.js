var main = {
    init : function () {
        var _this = this;
        $('#btn-create').on('click', function() {
            _this.save();
        }),
        $('#btn-modify').on('click', function() {
            _this.modify();
        }),
        $('#btn-img').on('click', function() {
            _this.img();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            context: $('#context').val(),
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
    },
    img : function () {
        var file = $('#img')[0].files[0];
        var formData = new FormData();
        formData.append('data', file);

        $.ajax({
            type: 'POST',
            url: '/api/upload',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                $('#result-image').attr("src", data);
            },
            error: function(error) {
                alert(error);
            }
        });
    }
};

main.init();