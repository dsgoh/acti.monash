#Light Version

import csv

def csv_writer(data,path): #writing function where the path is the file you would like to open
    with open(path, "w") as csv_file:
        writer = csv.writer(csv_file,delimiter=',')
        for item in data:
            writer.writerow(item)


line = fullist[0]
epoch = fulllist[1]
date_stamp = fullist[2]
time_stamps = fullist[3]
off_wrist_status = fullist[4]
activity_count = fullist[5]
marker = fullist[6]
white_light_values = fullist[7]
red_light_values = fullist[8]
green_light_values = fullist[9]
blue_light_values = fullist[10]
sleep_wake = fullist[11]
interval_status = fullist[12]
sleep_status = fullist[13]

index = 0
data = open("sleepfile.csv","w",newline = "")  #writing the sleep CSV
while index < (len(time_stamps)):
    light_data = [line[index],epoch[index],date_stamp[index],time_stamps[index],off_wrist_status[index],activity_count[index],marker[index],white_light_values[index],red_light_values[index],green_light_values[index],blue_light_values[index],sleep_wake[index],interval_status[index],sleep_status[index]]
    write = csv.writer(data,delimiter=',')
    write.writerow(light_data)
    index +=1

#Brendan can use the csv_writer to write the analysis inputs and the actiwatch data properties
