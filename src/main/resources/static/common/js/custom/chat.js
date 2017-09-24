var user = {};
var stompClient = null;
var conversationId = null;
initialize = function(id, photo, groups){
    user = {
        id: id,
        photo: photo,
        groups: groups
    }
};

$(document).ready(function () {
   $('.conversation').on('click', function () {
       handleConversation($(this));
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

    $('#text').keypress(function(e) {
        var keycode = (e.keyCode ? e.keyCode : e.which);
        if (keycode == '13') {
            e.preventDefault();
            sendMessage();
        }
    });
});

handleConversation = function(currentConversation){
    //Enable text and button
    $('#text').prop('disabled', false);
    $('#send').prop('disabled', false);
    var idOldConversation = $('#idConversation').val();
    var oldConversation = $('#conversation-'+idOldConversation);
    if(oldConversation.hasClass("current")){
        oldConversation.removeClass("current");
    }
    currentConversation.addClass("current");
    conversationId = currentConversation.data('id');
    $('#idConversation').val(conversationId);

    var name = $('#user-'+conversationId).text();
    $('#conversation-title').text(name);
};

display = function(messages){
    var conversation = $('#conversation');
    conversation.html("");
    messages.forEach(function(message){
        displayMessageWithConversation(conversation, message);
    });
};

displayMessageWithConversation = function(conversation, message){
    let userPhoto = message.user.photo1;
    if(message.user.id === userId){
        conversation.append(
            '<div class="conversation-item you">' +
            '<div class="s1">' +
            '<a class="avatar" href="javascript:void(0);">' +
            '<img src="'+ userPhoto +'" alt="Alternative text to the image"/>' +
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
            '<img src="'+ userPhoto +'" alt="Alternative text to the image"/>' +
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
    let userPhoto = message.user.photo1;
    var conversation = $('#conversation');
    if(message.user.id === userId){
        conversation.append(
            '<div class="conversation-item you">' +
            '<div class="s1">' +
            '<a class="avatar" href="javascript:void(0);">' +
            '<img src="'+ user.photo +'" alt="Alternative text to the image"/>' +
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
            '<img src="'+ userPhoto +'" alt="Alternative text to the image"/>' +
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

        user.groups.forEach(function(groupId){
            stompClient.subscribe("/topic/" + groupId, function(messageOutput) {
                showGroupMessageOutput(JSON.parse(messageOutput.body));
            });
        })
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
    var isGroup = $('#conversation-'+conversationId).data('group');
    console.log(isGroup);
    let text = $('#text');
    if(text.val()){
        let message = {
            user: {id: userId},
            userId: userId,
            message:  text.val(),
            conversationId: conversationId,
            date: new Date()
        };
        text.val("");
        displayMessage(message);
        if(isGroup){
            stompClient.send("/app/messages/room/" + conversationId, {},
                JSON.stringify(message));
        }else{
            let sendTo = $('#send-to-' + conversationId).text();
            stompClient.send("/app/messages/" + sendTo, {},
                JSON.stringify(message));
        }
    }

}

function showMessageOutput(messageOutput) {
    if(messageOutput.conversation.id === conversationId){
        displayMessage(messageOutput);
    }
    else{
        showNotification(messageOutput);
    }
}

function showGroupMessageOutput(messageOutput) {
    if(messageOutput.user.id !== user.id){
        if(messageOutput.conversation.id === conversationId){
            displayMessage(messageOutput);
        }
        else{
            showNotification(messageOutput);
        }
    }
}

showNotification = function(message){
    console.log('convId', conversationId);
    $('#user-' + message.conversation.id).css('font-weight', 'bold');
    var lastMessage = $('#message-' + message.conversation.id);
    lastMessage.css('font-weight', 'bold');
    lastMessage.html(message.message);
    var span = $('#span-'+message.conversation.id);
    if(span.hasClass('hide')){
        span.removeClass('hide');
    }
    let unread = parseInt(span.attr('data-unread'));
    span.attr('data-unread', unread+1);
    span.text(unread +1);
};


