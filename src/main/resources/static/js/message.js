var main = {
    init : function () {
        var message = $('#message').val();
        var status = $('#status').val();
        var url = $('#url').val();
        Swal.fire({
            title: message,
            icon: status
        })
        .then(() => {
            window.location.href=url;
        });
    }
};

main.init();