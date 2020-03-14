import network
from machine import Pin, ADC
from time import sleep


#for connecting to wifi
station = network.WLAN(network.STA_IF)
station.active(True)
station.connect("WIFI_NAME", "YOUR_WIFI_PASS")

#for light sensor input pin
pot= ADC(Pin(34))
pot.atten(ADC.ATTN_11DB) #giving full 3.3 volts to the light sensor

if station.isconnected(): #checkig if wifi is connected. (for future use)
  print('WLAN connection succeeded!')
  print(station.ifconfig()) #printing the ip addresses
  
  #setting pins for inbuild board and external LED
  led = Pin(2, Pin.OUT)
  r_led=Pin(15, Pin.OUT)
  g_led=Pin(5, Pin.OUT)
  b_led=Pin(4, Pin.OUT)
  
  
  while True:
    pot_value=pot.read()
    print(pot_value)
    r_led.value(0)
    g_led.value(0)
    b_led.value(0)
    print(r_led.value())
    sleep(1)
  
  #If the value is lesser than 1000 then the lights are turned on, else turned off. (future would be to increase the #intensity as and when the nautral light reduces.
	if pot_value<=1000:
      led.value(not led.value())
      r_led.value(not r_led.value())
      g_led.value(not g_led.value())
      b_led.value(not b_led.value())    
      sleep(100)
    
    
else:
  print('error connecting')
  
  

