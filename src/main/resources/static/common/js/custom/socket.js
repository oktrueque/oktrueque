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
            showNotification(JSON.parse(messageOutput.body));
        });
        data.groups.forEach(function(groupId){
            stompClient.subscribe("/topic/" + groupId, function(messageOutput) {
                showNotification(JSON.parse(messageOutput.body));
            });
        })
    });
}

showNotification = function(message){
    let span = $('#sb-messages');
    span.css('display', 'inline');
    let unread = parseInt(span.attr('data-unread'));
    span.attr('data-unread', unread+1);
    span.text(unread +1);

    if(!vis()){
        pushNotification(message.user.name, message.message, message.user.photo1);
    }
};

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}