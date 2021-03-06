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

pushNotification = function(name, message, image){
    if(Push.Permission.has()){
        Push.create(name, {
            body: message,
            icon: image,
            timeout: 10000,
            onClick: function () {
                window.focus();
                this.close();
            }
        });
    }
};