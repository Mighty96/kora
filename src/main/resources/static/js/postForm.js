var main = {
    init : function () {
        var _this = this;
        $('#btn-create').on('click', function() {
            _this.save();
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
    }
};

main.init();