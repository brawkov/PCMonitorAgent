const messageWindow = document.getElementById("messages");
const sendButton = document.getElementById("send");
const messageInput = document.getElementById("message");

// Создание WebSocket. Открытие соединения
// WebSocket = require 'ws'

// const socket = new WebSocket("ws://localhost:8080/sendStatePC?pcId=2");
const socket = new WebSocket("ws://whispering-bayou-77904.herokuapp.com/sendStatePC?pcId=2");
socket.binaryType = "arraybuffer";
// Обработчик открытия соединения
socket.onopen = function () {
    addMessageToWindow("Соединение установлено");
};
socket.onclose = function (error){
    console.log("close")
    console.log(error)
}

// Обработчик получения сообщения от сервера
socket.onmessage = function (event) {
    console.log(JSON.parse(event.data))
    // addMessageToWindow(`Получено: ${event.data}`);
    document.getElementById('messages').innerHTML = JSON.parse(event.data).pcCpuLoad;
};

sendButton.onclick = function () {
    sendMessage(messageInput.value);
    messageInput.value = "";
};

// Функция для отправки сообщения на сервер
function sendMessage(message) {
    socket.send(message);
    addMessageToWindow(`Отправлено: ${message}`);
}

function addMessageToWindow(message) {
    messageWindow.innerHTML += `<div>${message}</div>`
}
 
