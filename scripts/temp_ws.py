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
        while(1):
            pass
        print("Finished")
        ws.close()

    except KeyboardInterrupt:
        ws.close()
        exit()
