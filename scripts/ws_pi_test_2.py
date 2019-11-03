from ws4py.client.threadedclient import WebSocketClient
import time, requests

esp8266host = "ws://water.local:81/"

class DummyClient(WebSocketClient):
    def opened(self):
        print("Websocket opened")
    def closed(self, code, reason=None):
        print("Connexion closed")
    def received_message(self, m):
        print(m)

if __name__ == '__main__':
    try:
        ws = DummyClient(esp8266host)
        ws.connect()
        print("Ready!")

        i = 0
        while i < 101:
            payload = "led0:" + str(i)
            ws.send(payload)
            time.sleep(.20)
            i += 1

        print("Finished")
        ws.send("led0:0")
        ws.close()
        exit()

    except KeyboardInterrupt:
        ws.send("led0:0")
        ws.close()
