from ws4py.client.threadedclient import WebSocketClient
# from pandas import pd
import time, requests
from firebase import firebase

esp8266host = "ws://192.168.4.18:81/"

counter = 0
fifty_values_arr = []
temp = 0.0


class DummyClient(WebSocketClient):

    def opened(self):
        print("Websocket opened")

    def closed(self, code, reason=None):
        print("Connexion closed")

    def received_message(self, m):
        global counter
        global fifty_values_arr
        global temp

        m = m.data.decode("utf-8")

        counter += 1

        if counter == 100:
            counter = 0
            average = compute_average_fifty_values(fifty_values_arr)
            fifty_values_arr = []

            temp = average

            # print("Temperature: " + str(average) + "C")
            return

        fifty_values_arr.append(m)

        # print(m)


def compute_average_fifty_values(temp_arr):
    arr_length = len(temp_arr)
    sum_of_temp = 0

    for i in range(arr_length):
        sum_of_temp += float(temp_arr[i])

    average = sum_of_temp / (arr_length + 1)

    average = round(average, 2)

    return average


def main():
    global temp

    try:
        ws = DummyClient(esp8266host)
        ws.connect()

        firebase_client = firebase.FirebaseApplication('https://elec390-80d64.firebaseio.com', authentication=None)

        property_1 = '/Property1/'
        property_2 = '/Property2/'

        print("Ready!")

        while 1:
            air_quality_1 = {'Gas': 8102, 'Humidity': 22, 'Name': 'Living Room', 'Temperature': temp}
            result = firebase_client.put(property_1, data=air_quality_1, name='Air quality 1')
            # print(result)
            # time.sleep(2)
            pass
    except KeyboardInterrupt:
        print("Finished")
        ws.close()
        exit()


if __name__ == '__main__':
    main()
