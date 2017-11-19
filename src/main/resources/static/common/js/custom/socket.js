var stompClient = null;

$(document).ready(function(){
    $.ajax({
        type : "get",
        url : "/profile/conversations/notifications",
        success : function(data) {
            displayInSidebar(data.unread);
            connect(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
});

function displayInSidebar(count){
    let unread = parseInt(count);
    let span = $('#sb-messages');
    if(unread > 0){
        span.text(count);
    }
    span.attr('data-unread', count);
};

function connect(data) {
    let socket = new SockJS('/profile/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe("/user/queue/reply", function(messageOutput) {
            showReply(JSON.parse(messageOutput.body));
        });
        stompClient.subscribe("/user/queue/notification", function(messageOutput) {
            showNotification(JSON.parse(messageOutput.body));
        });
        data.groups.forEach(function(groupId){
            stompClient.subscribe("/topic/" + groupId, function(messageOutput) {
                showNotification(JSON.parse(messageOutput.body));
            });
        })
    });
}

showReply = function(message){
    let span = $('#sb-messages');
    span.css('display', 'inline');
    let unread = parseInt(span.attr('data-unread'));
    span.attr('data-unread', unread+1);
    span.text(unread +1);

    if(!vis()){
        pushNotification(message.user.name, message.message, message.user.photo1);
    }
};

showNotification = function(message){
    if(!vis()){
        pushNotification(message.user.name, message.message, message.user.photo1);
    }else{
        swal(message.user.name, message.message);
    }

    //Notification in navbar
    var list = $('#navbar-notifications');
    list.prepend('<div class="item">\n' +
        '<div class="inner">\n' +
        '<div class="title">\n' +
        '<span class="pull-right">Justo ahora</span>\n' +
        '<strong>'+ message.user.name + '  <span class="label label-info font-weight-700">New</span></strong>\n' +
        '</div>\n' +
        '<div class="descr">\n' +
        message.message +
        '</div>\n' +
        '</div>\n' +
        '</div>\n' +
        '<hr style="margin: 1rem 15px;"/>');

    let span = $('#nav-notificiation');
    let count = span.data('number');
    count += 1;
    span.text(count);
    span.data('number', count);
};

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}