$(document).ready(function(){
    $.ajax({
        type : "get",
        url : "/categories",
        success : function(data) {
            console.log("SUCCESS: ", data);
            displayCategories(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });

    function displayCategories(categories){
        var list = $('#sidebar-categories')
        categories.forEach(function(category){
            list.append('<li><a href="/items?id_category=' + category.id +'" class="left-menu-link">'+ category.name +'</a></li>');
        });
    }
});