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

var vis = (function(){
    var stateKey, eventKey, keys = {
        hidden: "visibilitychange",
        webkitHidden: "webkitvisibilitychange",
        mozHidden: "mozvisibilitychange",
        msHidden: "msvisibilitychange"
    };
    for (stateKey in keys) {
        if (stateKey in document) {
            eventKey = keys[stateKey];
            break;
        }
    }
    return function(c) {
        if (c) document.addEventListener(eventKey, c);
        return !document[stateKey];
    }
})();

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
        pushNotification(message);
    }
};

pushNotification = function(response){
    Push.create(response.user.name, {
        body: response.message,
        icon: response.user.photo1,
        timeout: 4000,
        onClick: function () {
            window.focus();
            this.close();
        }
    });
};

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}