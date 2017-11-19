var username = null;
var monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];
initializeNotifications = function(user){
    username = user;
};

$(document).ready(function(){
    $.ajax({
        type : "get",
        url : "/notifications/" + username,
        success : function(notifications) {
            displayNotifications(notifications);
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });

    function displayNotifications(notifications){
        var list = $('#navbar-notifications');
        let count = 0;
        if(notifications.length === 0){
            list.append('<p>Parece que no tienes notificaciones</p>');
        }
        notifications.forEach(function(notification){
            let date = getDateMessage(notification.date);
            if(!notification.checked){
                count++;
                list.append('<div class="item">\n' +
                    '<div class="inner">\n' +
                    '<div class="title">\n' +
                    '<span class="pull-right">'+ date +'</span>\n' +
                    '<strong>'+ notification.title + '  <span class="label label-info font-weight-700">New</span></strong>\n' +
                    '</div>\n' +
                    '<div class="descr">\n' +
                    notification.message +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '<hr style="margin: 1rem 15px;"/>');
            }else{
                list.append('<div class="item">\n' +
                    '<div class="inner">\n' +
                    '<div class="title">\n' +
                    '<span class="pull-right">'+ date +'</span>\n' +
                    '<strong>'+ notification.title + '</strong>\n' +
                    '</div>\n' +
                    '<div class="descr">\n' +
                    notification.message +
                    '</div>\n' +
                    '</div>\n' +
                    '</div>\n' +
                    '<hr style="margin: 1rem 15px;"/>');
            }
        });
        let span = $('#nav-notificiation');
        span.text(count);
        span.attr('data-number', count);
    }

    function getDateMessage(epoc){
        let date = new Date(epoc);
        let now = new Date();
        let hours = Math.abs(now - date) / 36e5;
        let hoursTruncate = Math.trunc(hours);
        if(hoursTruncate === 0){
            return 'Recientemente';
        }
        if(hoursTruncate === 1){
            return 'Hace una hora';
        }
        if(hoursTruncate < 24){
            return 'Hace ' + hoursTruncate + ' horas';
        }
        let day = ("0" + date.getDate()).slice(-2);
        let month = date.getMonth();
        return day + ' de ' + monthNames[month];
    }
});