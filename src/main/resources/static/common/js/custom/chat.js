var userId;

initialize = function(id){
    userId = id;
};

$(document).ready(function () {
   $('.conversation').on('click', function () {
       var conversationId = $(this).data('id');
       $.ajax({
           type : "get",
           url : "/profile/conversations/" + conversationId + "/messages",
           success : function(messages) {
               console.log("SUCCESS: ", messages);
               display(messages);
           },
           error : function(e) {
               console.log("ERROR: ", e);
           }
       });
   });
   connect();
});

display = function(messages){
    var conversation = $('#conversation');
    conversation.html("");
    messages.forEach(function(message){
        if(message.user.id === userId){
            conversation.append(
                '<div class="conversation-item you">' +
                '<div class="s1">' +
                '<a class="avatar" href="javascript:void(0);">' +
                '<img src="" alt="Alternative text to the image"/>' +
                '</a>' +
                '</div>' +
                '<div class="s2">' +
                '<p>'+ message.message +'</p>' +
                '</div>' +
                '</div>'
            );
        }else{
            conversation.append(
                '<div class="conversation-item">' +
                '<div class="s1">' +
                '<a class="avatar" href="javascript:void(0);">' +
                '<img src="" alt="Alternative text to the image"/>' +
                '</a>' +
                '</div>' +
                '<div class="s2">' +
                '<p>'+ message.message +'</p>' +
                '</div>' +
                '</div>'
            );
        }

    });
};

var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility
        = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/profile/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var from = document.getElementById('from').value;
    var text = document.getElementById('text').value;
    stompClient.send("/app/chat", {},
        JSON.stringify({'from':from, 'text':text}));
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(messageOutput.from + ": "
        + messageOutput.text + " (" + messageOutput.time + ")"));
    response.appendChild(p);
}


