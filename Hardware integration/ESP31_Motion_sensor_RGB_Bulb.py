


import network
from machine import Pin, ADC
from time import sleep
station = network.WLAN(network.STA_IF)
intensity =0 
station.active(True)
station.connect("Brown_Nation", "MadHouse91")
pot= ADC(Pin(34))
pot.atten(ADC.ATTN_11DB)
if station.isconnected():
  print('WLAN connection succeeded!')
  print(station.ifconfig())
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
  
    if pot_value>3500:
      led.value(not led.value())
      r_led.value(not r_led.value())
      g_led.value(not g_led.value())
      b_led.value(not b_led.value())    
      sleep(10)
    
    
else:
  print('error connecting')
  
  



