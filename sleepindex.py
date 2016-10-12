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

def sleep_index(sleep,activity): #appends sleep index of each point to list
    item = average_p(activity)
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
    average = get_activity_list("sleepfile.csv")
    sleep_index(sleep_index,average)
    movingaverages = average_p(average)
    while index < (len(time_stamps)):
        light_data = [line[index],epoch[index],date_stamp[index],time_stamps[index],off_wrist_status[index],activity_count[index],marker[index],white_light_values[index],red_light_values[index],green_light_values[index],blue_light_values[index],sleep_wake[index],interval_status[index],sleep_status[index],sleep_index[index],movingaverages[index]]
        write = csv.writer(data,delimiter=',')
        write.writerow(light_data)
        index +=1