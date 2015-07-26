var ws = new WebSocket("ws://127.0.0.1:8090/chat");

ws.onopen = function () {
    appendLog("Connected. Press 'Start'.")
};

ws.onmessage = function (evt) {
    appendLog('Server: ' + evt.data)
};

ws.onclose = function () {
    appendLog("Disconnected.")
    ws = undefined;
};

ws.onerror = function (err) {
    console.log("ERROR!", err)
};

function appendLog(logText) {
    var log = document.getElementById("console");
    log.value = log.value + logText + "\n"
}

function start() {
    if (ws == undefined) {
        ws = new WebSocket("ws://127.0.0.1:8090/chat");
    }

    sendToServer('start')
}


function sendToServer(msg) {
    ws.send(msg);
}