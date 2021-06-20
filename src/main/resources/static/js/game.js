var main = {
    init : function () {
        var _this = this;
        $('#btn-comment').on('click', function() {
            console.log('?');
            _this.save();
        });
    },
    save : function () {
        var data = {
            context: $('#context').val()
        };
        $.ajax({
            type: 'POST',
            url: '/game/' + $('#id').val() + '/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function() {
                window.location.href='/game/' + $('#id').val();
                alert('한줄평이 등록되었습니다.')
            },
            error: function(e) {
                alert('몰라');
            }
        });
    }
};

main.init();