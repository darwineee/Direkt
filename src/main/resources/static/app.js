$(function() {
    // jQuery element references
    const $connect = $("#connect");
    const $disconnect = $("#disconnect");
    const $messageInput = $("#message");
    const $wsUrl = $("#ws-url");
    const $jwtToken = $("#jwt-token");
    const $roomId = $("#room-id");
    const $messageLog = $("#message-log");
    const $receivedMessages = $("#received-messages");
    const $subscribeRoom = $("#subscribe-room");
    const $unsubscribeRoom = $("#unsubscribe-room");
    const $sendMessage = $("#send-message");

    let stompClient = null;
    let subscribedRoom = null;
    let personalErrorSubscription = null;
    let roomChangeSubscription = null;

    function updateSubscriptionUI(isSubscribed) {
        $subscribeRoom.prop('disabled', isSubscribed);
        $unsubscribeRoom.prop('disabled', !isSubscribed);
        $roomId.prop('disabled', isSubscribed);
        // Add message UI state management
        $messageInput.prop('disabled', !isSubscribed);
        $sendMessage.prop('disabled', !isSubscribed);

        // Update placeholder text
        if (!isSubscribed) {
            $messageInput.attr('placeholder', 'Subscribe to a room first to send messages');
        } else {
            $messageInput.attr('placeholder', 'Enter your message');
        }
    }

    function setConnected(connected) {
        $connect.prop("disabled", connected);
        $disconnect.prop("disabled", !connected);
        if (!connected) {
            subscribedRoom = null;
            personalErrorSubscription = null;
            roomChangeSubscription = null;
            updateSubscriptionUI(false);
        }
    }

    function logMessage(message, type = 'info') {
        const timestamp = new Date().toLocaleTimeString();
        const messageClass = type === 'error' ? 'list-group-item-danger' :
            type === 'success' ? 'list-group-item-success' :
                'list-group-item-info';
        $messageLog.append(`<li class="list-group-item ${messageClass}">[${timestamp}] ${message}</li>`);
        $messageLog.scrollTop($messageLog[0].scrollHeight);
    }

    function logReceivedMessage(message, type = 'message') {
        const timestamp = new Date().toLocaleTimeString();
        let messageHtml = '';

        try {
            const messageData = JSON.parse(message);
            switch(type) {
                case 'system':
                    messageHtml = `<p class="text-muted">[${timestamp}] System: ${messageData.data}</p>`;
                    break;
                case 'error':
                    messageHtml = `<p class="text-danger">[${timestamp}] Error: ${messageData.data}</p>`;
                    break;
                default:
                    messageHtml = `<p>[User ${messageData.from}] [${timestamp}]<br/>${messageData.msg.text}</p>`;
            }
        } catch (e) {
            messageHtml = `<p>[${timestamp}] ${message}</p>`;
        }

        $receivedMessages.append(messageHtml);
        $receivedMessages.scrollTop($receivedMessages[0].scrollHeight);
    }

    function connect() {
        const wsUrl = $wsUrl.val();
        const jwtToken = $jwtToken.val();

        if (!jwtToken) {
            logMessage('JWT Token is required', 'error');
            return;
        }

        stompClient = new StompJs.Client({
            brokerURL: wsUrl,
            connectHeaders: {
                Authorization: `Bearer ${jwtToken}`
            },
            debug: function(str) {
                console.debug(str);
            }
        });

        stompClient.onConnect = (frame) => {
            setConnected(true);
            console.log(frame.headers);
            const email = frame.headers['user-name'];
            const sessionId = frame.headers['session'];
            logMessage('Connected to WebSocket: ' + email + ", sessionId: " + sessionId, 'success');
            updateSubscriptionUI(false);
            subscribeToPersonalErrors(frame.headers['session']);
        };

        stompClient.onWebSocketError = (error) => {
            console.error('WebSocket error: ', error);
            logMessage('WebSocket error: ' + error, 'error');
        };

        stompClient.onStompError = (frame) => {
            console.error('STOMP error: ' + frame.headers['message']);
            logMessage('STOMP error: ' + frame.headers['message'], 'error');
        };

        stompClient.onDisconnect = () => {
            setConnected(false);
            logMessage('Disconnected from WebSocket');
            updateSubscriptionUI(false);
        };

        stompClient.activate();
    }

    function subscribeToPersonalErrors(sessionId) {
        if (personalErrorSubscription) {
            personalErrorSubscription.unsubscribe();
        }
        const dest = `/user/${sessionId}/sub/err.queue`;
        personalErrorSubscription = stompClient.subscribe(dest, (message) => {
            logReceivedMessage(message.body, 'error');
            logMessage('Received error: ' + message.body, 'error');
        });
    }

    function unsubscribeFromCurrentRoom() {
        if (!subscribedRoom) {
            logMessage("Not subscribed to any room", 'error');
            return;
        }

        if (roomChangeSubscription) {
            roomChangeSubscription.unsubscribe();
            logMessage("Unsubscribed from room: " + subscribedRoom, 'success');
            roomChangeSubscription = null;
            subscribedRoom = null;
            updateSubscriptionUI(false);
        }
    }

    function disconnect() {
        if (stompClient !== null) {
            if (stompClient.connected) {
                unsubscribeFromCurrentRoom();
                if (personalErrorSubscription) {
                    personalErrorSubscription.unsubscribe();
                }
                stompClient.deactivate();
            }
        }
        setConnected(false);
        updateSubscriptionUI(false);
        logMessage("Disconnected from WebSocket");
    }

    function subscribeRoom() {
        const roomId = $roomId.val();

        if (!roomId) {
            logMessage("Room ID is required", 'error');
            return;
        }

        if (subscribedRoom === roomId) {
            logMessage("Already subscribed to room: " + subscribedRoom, 'error');
            return;
        }

        // Unsubscribe from previous room if any
        if (roomChangeSubscription) {
            unsubscribeFromCurrentRoom();
        }

        try {
            roomChangeSubscription = stompClient.subscribe(`/sub/room.change/${roomId}`, (message) => {
                try {
                    // console.log(message)
                    const messageData = JSON.parse(message.body);
                    console.log(messageData);
                    if (messageData.type === 'SYSTEM') {
                        logReceivedMessage(message.body, 'system');
                    } else {
                        logReceivedMessage(message.body, 'message');
                    }
                } catch (e) {
                    logReceivedMessage(message.body, 'message');
                }
            });

            subscribedRoom = roomId;
            updateSubscriptionUI(true);
            logMessage("Subscribed to room: " + roomId, 'success');
        } catch (error) {
            logMessage("Failed to subscribe to room: " + error, 'error');
            updateSubscriptionUI(false);
        }
    }

    function sendMessage() {
        const message = $messageInput.val();
        const roomId = subscribedRoom;

        if (!message) {
            logMessage("Message cannot be empty", 'error');
            return;
        }

        if (!roomId) {
            logMessage("You need to subscribe to a room first", 'error');
            return;
        }

        try {
            stompClient.publish({
                destination: `/send/room/${roomId}`,
                body: JSON.stringify({ data: message })
            });
            $messageInput.val(''); // Clear message input
            logMessage("Sent message: " + message, 'success');
        } catch (error) {
            logMessage("Failed to send message: " + error, 'error');
        }
    }

    // Bind event handlers
    $connect.on('click', connect);
    $disconnect.on('click', disconnect);
    $subscribeRoom.on('click', subscribeRoom);
    $unsubscribeRoom.on('click', unsubscribeFromCurrentRoom);
    $sendMessage.on('click', sendMessage);

    $messageInput.on('keypress', (e) => {
        if (e.which === 13) { // Enter key
            sendMessage();
        }
    });

    // Initialize UI state
    setConnected(false);
    updateSubscriptionUI(false);

    console.log('WebSocket client initialized');
});