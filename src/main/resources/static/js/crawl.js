var main = {
    init : function () {
        var _this = this;
        $('#btn-crawl').on('click', function() {
            _this.crawl();
        });
    },
    crawl : function () {
        $.ajax({
            type: 'GET',
            url: '/api/gameCrawl',
            success: function() {
                Swal.fire({
                    title: "크롤링완료",
                    icon: "success"
                })
                .then(() =>{
                    window.location.reload();
                });
            }
        });
    }
};

main.init();