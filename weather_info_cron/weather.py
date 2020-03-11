import requests, json 
import time
from datetime import datetime
import csv
  
api_key = "2f30e9abf594e715af4449414b055800"
base_url = "http://api.openweathermap.org/data/2.5/weather?"
city_name = "Dublin,IE"
weather_api_url = base_url + "appid=" + api_key + "&q=" + city_name + "&units=metric"
response = requests.get(weather_api_url) 
  
x = response.json() 

  
# Parsing the response to get weather attributes
if x["cod"] != "404": 

    y = x["main"] 

    current_temperature = y["temp"] 
  
    current_pressure = y["pressure"] 
  
    current_humidiy = y["humidity"] 
  
    z = x["weather"] 

    weather_description = z[0]["description"] 
    
    sun_times = x['sys']
    
    sunrise = sun_times['sunrise']
    sunset = sun_times['sunset']
    
    # Writing the data to a csv file
    
    with open('weather_data.csv', mode='w') as file:
        writer = csv.writer(file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        writer.writerow(['updated_time', 'temperature', 'sunrise', 'sunset', 'description'])
        writer.writerow([datetime.now().strftime("%m/%d/%Y, %H:%M:%S"), current_temperature, time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(sunrise)),
                         time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(sunset)), weather_description])

else: 
    print("Could not write data to csv") 