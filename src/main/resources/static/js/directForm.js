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
            japanUrl: $('#japanUrl').val(),
            americaUrl: $('#americaUrl').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/directs',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                window.location.href='/directs';
            }
        });
    }
};

main.init();