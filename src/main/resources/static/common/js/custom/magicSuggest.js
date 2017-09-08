var ms = {};
var tags;

// Metodo que busca los tags e inicializa el plugin
ms.initialize = function(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/tags",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            tags = data;
            ms = $("#ms").magicSuggest({
                data: function(q){
                    var e = q || '', r=[], c = tags;
                    for(var i=0; i<c.length; i++){
                        if(c[i].name.toLowerCase().indexOf(e.toLowerCase()) > -1)
                            r.push({id: c[i].id, name: c[i].name});
                    }
                    return r;
                },
                allowFreeEntries: false,
                useTabKey: true
            });
        },
        error: function (e) {
            alert("Ha ocurrido un error creando el item, lo sentimos. Error: " + JSON.stringify(e));
            console.log("ERROR Posting tags ajax: ", JSON.stringify(e));
        }
    });
};