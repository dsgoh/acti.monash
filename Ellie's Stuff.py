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
data = open("full_csv","w",newline = "")  #writing the sleep CSV
while index < (len(time_stamps)):
    light_data = [line[index],epoch[index],date_stamp[index],time_stamps[index],off_wrist_status[index],activity_count[index],marker[index],white_light_values[index],red_light_values[index],green_light_values[index],blue_light_values[index],sleep_wake[index],interval_status[index],sleep_status[index]]
    write = csv.writer(data,delimiter=',')
    write.writerow(light_data)
    index +=1

#Brendan can use the csv_writer to write the analysis inputs and the actiwatch data properties





def get_activity_list(data): #gets activity data and places into list
    activity = []
    with open(data,"r") as file:
        for line in file:
            headers = file.readline()
            datalist = line.split(",")
            activity.append(datalist[5])
    return activity

def average_p(time): #does averages for all values
    averages=[]
    for i in range(len(time)):
        total = 0
        count = 0
        for k in range(-10,11):
            if i-k>=0 and i-k<len(time):
                total += time[i-k]
                count+=1
        averages.append(total/count)
    return(averages)

def average_ten(activity): #works out average of each point
    average = average_p(activity)
    return average


def sleep_index(sleep): #appends sleep index of each point to list
    item = average_p(time)
    for average in item:
        if average > 200:
            sleep.append(10)
        elif average < 60:
            sleep.append(0)
        elif 60 <= average < 75.55:
            sleep.append(1)
        elif 75.55 <= average < 91.1:
            sleep.append(2)
        elif 91.1 <= average < 106.65:
            sleep.append(3)
        elif 106.65 <= average < 122.2:
            sleep.append(4)
        elif 122.2 <= average < 137.75:
            sleep.append(5)
        elif 137.75 <= average < 153.3:
            sleep.append(6)
        elif 153.3 <= average < 168.85:
            sleep.append(7)
        elif 168.85 <= average < 184.4:
            sleep.append(8)
        elif 184.4 <= average < 200:
            sleep.append(9)

def full_csv_second(CSV):
    index = 0
    data = open(CSV,"w",newline = "")  #rewriting the sleep CSV
    sleep_index = []
    sleep_index_list(sleep_index)
    while index < (len(time_stamps)):
        light_data = [line[index],epoch[index],date_stamp[index],time_stamps[index],off_wrist_status[index],activity_count[index],marker[index],white_light_values[index],red_light_values[index],green_light_values[index],blue_light_values[index],sleep_wake[index],interval_status[index],sleep_status[index],sleep_index[index]]
        write = csv.writer(data,delimiter=',')
        write.writerow(light_data)
        index +=1