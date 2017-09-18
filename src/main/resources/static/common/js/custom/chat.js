var userId;
var stompClient = null;
var conversationId = null;
initialize = function(id){
    userId = id;
};

$(document).ready(function () {
   $('.conversation').on('click', function () {
       conversationId = $(this).data('id');
       $('#idConversation').val(conversationId);
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
        displayMessageWithConversation(conversation, message);
    });
};

displayMessageWithConversation = function(conversation, message){
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
};

displayMessage = function(message){
    var conversation = $('#conversation');
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
};

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
        stompClient.subscribe("/user/queue/reply", function(messageOutput) {
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
    var text = $('#text');
    var message = {
        user: {id: userId},
        userId: userId,
        message:  text.val(),
        conversationId: conversationId,
        date: new Date()
    };
    text.val("");
    displayMessage(message);
    var sendTo = $('#send-to-' + conversationId).text();
    stompClient.send("/app/messages/" + sendTo, {},
        JSON.stringify(message));
}

function showMessageOutput(messageOutput) {
    console.log(messageOutput);
    if(messageOutput.conversation.id === conversationId){
        displayMessage(messageOutput);
    }
    else{
        console.log(messageOutput.conversation.id, conversationId);
    }
}


