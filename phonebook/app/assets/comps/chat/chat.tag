<chat>

    <ul>
        <li each="{ messages }">
            <p>{ message }</p>
        </li>
    </ul>

    <form onsubmit="{ sendMessage }">
        <div class="row">
            <div class="medium-6 columns">
                <label>Message
                    <input type="text" name="message" placeholder="type a message..">
                </label>
                <button class="button">Send</button>
            </div>
        </div>
    </form>

    <script>

        var wsUri = "ws://localhost:9000/websocket",
            chatWebSocket = new WebSocket(wsUri),
            self = this;

        this.messages = this.opts.messages

        chatWebSocket.onopen = function (event) {
            console.log("CONNECTED")
        }

        chatWebSocket.onclose = function (event) {
            console.log("DISCONNECTED")
        }

        chatWebSocket.onmessage = function(event) {
            self.messages.push({ message: event.data })
        }

        chatWebSocket.onerror = function(event) {
            console.log(event.data)
        }

        sendMessage (e) {
            chatWebSocket.send(this.message.value)
        }

    </script>
</chat>