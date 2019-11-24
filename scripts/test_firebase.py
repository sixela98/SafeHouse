from firebase import firebase

temp = 0



def main():
    global temp

    firebase_client = firebase.FirebaseApplication('https://elec390-80d64.firebaseio.com', authentication=None)
    result = firebase_client.get(
        '/Property1/', None)



    print(result)

    air_quality_1 = {'Gas': 8102, 'Humidity': 22, 'Name': 'Living Room', 'Temperature': 30}

    property_1 = '/Property1/'

    result = firebase_client.put(property_1, data=air_quality_1, name='Air quality 1')

    print(result)


if __name__ == '__main__':
    main()
