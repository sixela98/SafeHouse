from ws4py.client.threadedclient import WebSocketClient
# from pandas import pd
import time, requests
from firebase import firebase

esp8266host = "ws://192.168.4.2:81/"

counter = 0
is_wet = False


class WaterSensorClient(WebSocketClient):

    def opened(self):
        print("Websocket opened")

    def closed(self, code, reason=None):
        print("Connexion closed")

    def received_message(self, m):
        global is_wet

        m = m.data.decode("utf-8")

        if m == str(0):
            is_wet = False
        else:
            is_wet = True

        print(is_wet)


def main():

    try:
        ws_water = WaterSensorClient(esp8266host)
        ws_water.connect()

        firebase_client = firebase.FirebaseApplication('https://elec390-80d64.firebaseio.com', authentication=None)

        property_1 = '/Property1/'
        property_2 = '/Property2/'

        print("Ready!")

        while 1:
            # air_quality_1 = {'Gas': 8102, 'Humidity': 22, 'Name': 'Living Room', 'Temperature': temp}
            water_sensor_1 = {'Name': 'Living Room', 'State': is_wet}
            result = firebase_client.put(property_1, data=water_sensor_1, name='Water sensor 1')
            # print(result)
            # time.sleep(2)
            pass
    except KeyboardInterrupt:
        print("Finished")
        ws.close()
        exit()


if __name__ == '__main__':
    main()
