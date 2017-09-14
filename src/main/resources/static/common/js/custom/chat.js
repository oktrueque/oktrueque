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


