from ws4py.client.threadedclient import WebSocketClient
# from pandas import pd
import time, requests
from firebase import firebase

air_quality_host = "ws://192.168.4.3:81/"

water_sensor_host = "ws://192.168.4.2:81/"

is_wet = False

temp = 0.0
humidity = 0.0
gas = 0.0


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


class DummyClient(WebSocketClient):

    def opened(self):
        print("Websocket opened")

    def closed(self, code, reason=None):
        print("Connexion closed")

    def received_message(self, m):
        global temp
        global humidity
        global gas

        m = m.data.decode("utf-8")

        values = m.split()

        temp = float(values[0]) / 100.0
        humidity = float(values[1]) / 1000.0
        gas = float(values[2]) / 100.0

        print("Temp: " + str(temp) + ", " + "Humidity: " + str(humidity) + ", " + "Gas: " + str(gas))


def main():
    try:
        ws_air_quality = DummyClient(air_quality_host)
        ws_air_quality.connect()

        ws_water = WaterSensorClient(water_sensor_host)
        ws_water.connect()

        firebase_client = firebase.FirebaseApplication('https://elec390-80d64.firebaseio.com', authentication=None)

        property_1 = '/u/u1/p/p1/r/r1/'
        property_1_water = '/u/u1/p/p1/r/r1/w/'

        print("Ready!")

        while 1:
            air_quality_1 = {'g1': gas, 'h1': humidity, 't1': temp}
            water_sensor_1 = {'n': 'Washing machine', 's': is_wet}
            result = firebase_client.put(property_1, data=air_quality_1, name='a')
            result = firebase_client.put(property_1_water, data=water_sensor_1, name='w1')
            # print(result)
            time.sleep(5)
            pass
    except KeyboardInterrupt:
        print("Finished")
        ws_air_quality.close()
        ws_water.close()
        exit()


if __name__ == '__main__':
    main()
