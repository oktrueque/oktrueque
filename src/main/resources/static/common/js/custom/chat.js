var user = {};
var stompClient = null;
var conversationId = null;
var itemsid = [];
var token = null;
var header = null;
initialize = function(id, photo, name, groups, t, h){
    user = {
        id: id,
        photo: photo,
        name: name,
        groups: groups
    };
    token = t;
    headers = h;
};

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

showLoader = function(){
    $('#conversation').html(
        '<div class="loader-little"></div>'
    );
    $('#trueque-detail').html(
        '<div class="loader-little"></div>'
    );
    $('#get-items').addClass('display-none');
};

$(document).ready(function () {
   $('.conversation').on('click', function () {
       itemsid = [];
       conversationId = $(this).data('id');
       showLoader();
       handleConversation($(this));
       handleNewMessages();
       $.ajax({
           type : "get",
           url : "/profile/conversations/" + conversationId + "/messages",
           success : function(messages) {
               if(messages){
                   display(messages);
               }
               clearUnreadMessages(conversationId);
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

    $('#edit-trueque').on('click', function(){
        editTrueque(this);
    });
    $('#get-items').on('click', function(){
        getItemsForUpdating();
    });
});

clearUnreadMessages = function(conversationId){
    let truequeId = $('#conversation-'+conversationId).data('trueque');
    $.ajax({
        type : "get",
        url : "/profile/conversations/" + conversationId + "/trueque",
        data:{
            truequeId: truequeId
        },
        success : function(data) {
            displayTruequeDetail(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
};

displayTruequeDetail = function(data){
    let detail = $('#trueque-detail');
    detail.html("");
    Object.keys(data).forEach(function(u){
        let li = document.createElement("LI");
        li.appendChild(document.createTextNode(u));
        li.setAttribute("class", "trueque-item");
        let ul = document.createElement("UL");

        data[u].forEach(function(item){
            let itemNode = document.createElement("LI");
            itemNode.appendChild(document.createTextNode(item.name));
            itemNode.setAttribute("class", "trueque-item");
            ul.appendChild(itemNode);

            if(item.user.id === user.id){
                itemsid.push(item.id);
            }
        });

        li.appendChild(ul);
        detail.append(li);

        $('#get-items').removeClass('display-none')
    });
};

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
    $('#idConversation').val(conversationId);

    var name = $('#user-'+conversationId).text();
    $('#conversation-title').text(name);
};

handleNewMessages = function(){
    $('#user-' + conversationId).css('font-weight', 'normal');
    let lastMessage = $('#message-' + conversationId);
    lastMessage.css('font-weight', 'normal');
    let span = $('#span-'+conversationId);
    span.attr('data-unread', 0);
    span.css('display', 'none');
};

display = function(messages){
    var conversation = $('#conversation');
    conversation.html("");
    messages.forEach(function(message){
        displayMessageWithConversation(conversation, message);
    });
    conversation.append(
        '<div id="scroll"></div>'
    );
    conversation.animate({scrollTop: $("#scroll").position().top}, 1000);
};

displayMessageWithConversation = function(conversation, message){
    let userPhoto = message.user.photo1;
    console.log(message);
    if(message.user.id === userId){
        conversation.append(
            '<div id="message-'+ message.id +'" class="conversation-item you">' +
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
            '<div id="message-'+ message.id +'" class="conversation-item">' +
            '<div class="s1">' +
            '<a class="avatar" href="/users/' + message.user.username +'">' +
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

appendMessage = function(message){
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
        if(!vis()){
            pushNotification(message);
        }
    }
    $('#conversation').animate({scrollTop: $('#conversation').prop("scrollHeight")}, 500);
};

pushNotification = function(message){
    Push.create(message.user.name, {
        body: message.message,
        icon: message.user.photo1,
        timeout: 4000,
        onClick: function () {
            window.focus();
            this.close();
        }
    });
};

setLastMessage = function(id, message){
    let lastMessage = $('#message-' + id);
    lastMessage.html(message);
};

function connect() {
    var socket = new SockJS('/profile/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
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
    let text = $('#text');
    if(text.val()){
        let message = {
            user: {id: userId},
            userId: userId,
            message:  text.val(),
            conversationId: conversationId,
            date: new Date(),
            userPhoto: user.photo,
            name: user.name
        };
        text.val("");
        appendMessage(message);
        setLastMessage(message.conversationId, message.message);
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
        appendMessage(messageOutput);
        setLastMessage(messageOutput.conversation.id, messageOutput.message);
    }
    else{
        showNotification(messageOutput);
    }
}

function showGroupMessageOutput(messageOutput) {
    if(messageOutput.user.id !== user.id){
        if(messageOutput.conversation.id === conversationId){
            appendMessage(messageOutput);
            setLastMessage(messageOutput.conversation.id, messageOutput.message);
        }
        else{
            showNotification(messageOutput);
        }
    }
}

showNotification = function(message){
    $('#user-' + message.conversation.id).css('font-weight', 'bold');
    let lastMessage = $('#message-' + message.conversation.id);
    lastMessage.css('font-weight', 'bold');
    lastMessage.html(message.message);
    let span = $('#span-'+message.conversation.id);
    span.css('display', 'block');
    let unread = parseInt(span.attr('data-unread'));
    span.attr('data-unread', unread+1);
    span.text(unread +1);
};

//Edit Trueque methods
addElementToTrueque = function(element){
    element.removeClass("icmn-arrow-up15 link-add");
    element.addClass("icmn-arrow-down15 link-remove");
    element.bind('click', function(){
        removeElementToTrueque($(this));
    });
    let item = element.parent().parent().detach();
    $('#items-offered').append(item);
};

removeElementToTrueque = function(element){
    element.removeClass("icmn-arrow-down15 link-remove");
    element.addClass("icmn-arrow-up15 link-add");
    element.bind('click', function(){
        addElementToTrueque($(this));
    });
    let item = element.parent().parent().detach();
    $('#items-left').append(item);
};

editTrueque = function(btn){
    let ids = [];
    let idTrueque = $('#conversation-'+conversationId).data('trueque');

    $('#items-offered div').each(function(li){
        ids.push(parseInt($(this).attr('id')));
    });
    if(!(itemsid.sort().compare(ids.sort()))) {
        var l = Ladda.create(btn);
        l.start();

        $.ajax({
            type: "POST",
            url: "/trueques/" + idTrueque,
            contentType: "application/json",
            data: JSON.stringify(ids),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (message) {
                console.log(message);
                l.stop();
                $('#modalTruequeDetail').modal('hide');
                if(message){
                    swal("Algo anda mal", message, "error");
                }else{
                    swal("Trueque Actualizado", "Esta propuesta de trueque ha sido correctamente actualizada", "success");
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    }
};

getItemsForUpdating = function(){
    $('#modalTruequeDetail').modal('show');
    $('#items-offered').html(
        '<div class="loader-little"></div>'
    );
    $('#items-left').html(
        '<div class="loader-little"></div>'
    );
    $.ajax({
        type : "get",
        url : "/users/" + user.id + "/items",
        success : function(data) {
            displayItemsForEdit(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
};

displayItemsForEdit = function(items){
  $('#items-offered').html("");
  $('#items-left').html("");

  items.forEach(function(item){
      if(itemsid.includes(item.id)){
          $('#items-offered').append(
              '<div id="'+ item.id +'">' +
              '<li>' + item.name +
              '<i class="icmn-arrow-down15 pull-right link-remove"></i>' +
              '</li>' +
              '</div>'
          );
      }else{
          $('#items-left').append(
              '<div id="'+ item.id +'">' +
              '<li>' + item.name +
              '<i class="icmn-arrow-up15 pull-right link-add"></i>' +
              '</li>' +
              '</div>'
          );
      }
  });

    $('.link-add').on('click', function(){
        addElementToTrueque($(this));
    });
    $('.link-remove').on('click', function(){
        removeElementToTrueque($(this));
    });

};
// Utils
Array.prototype.compare = function(testArr) {
    if (this.length != testArr.length) return false;
    for (var i = 0; i < testArr.length; i++) {
        if (this[i].compare) { //To test values in nested arrays
            if (!this[i].compare(testArr[i])) return false;
        }
        else if (this[i] !== testArr[i]) return false;
    }
    return true;
};