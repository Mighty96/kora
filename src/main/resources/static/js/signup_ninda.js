var main = {
    alert : function () {
        $('#resend').on('click', function() {
            Swal.fire({
                title: "인증메일이 다시 발송되었습니다.",
                icon: "success"
            });
        });
    }
};

main.alert();