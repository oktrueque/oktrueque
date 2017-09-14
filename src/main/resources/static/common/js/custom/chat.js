$(document).ready(function () {
   $('.conversation').on('click', function () {
       var conversationId = $(this).data('id');
       $.ajax({
           type : "get",
           url : "/profile/conversations/" + conversationId + "/messages",
           success : function(data) {
               console.log("SUCCESS: ", data);
           },
           error : function(e) {
               console.log("ERROR: ", e);
           }
       });
   });
});