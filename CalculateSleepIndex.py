def get_activity_list(data, index): #gets activity data and places into list
    activity = []
    with open(data,"r") as file:
        for line in file:
            headers = file.readline()
            datalist = line.split(",")
            activity.append(datalist[index])
    return activity

def average_p(time, off_wrist): #does averages for all values
    averages=[]
    for i in range(len(time)):
        total = 0
        count = 0
        for k in range(-10,11):
            if i-k>=0 and i-k<len(time) and time[i-k].lower()!="nan" and (not off_wrist or not off_wrist[i-k]):
                total += time[i-k]
                count+=1
        if count != 0:
            averages.append(total/count)
        else:
            averages.append("nan")
    return(averages)

def sleep_index(activity): #appends sleep index of each point to list
    item = average_p(activity)
    sleep = []
    for average in item:
        if average == "nan":
            sleep.append("nan")
        elif average > 200:
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
    return sleep

def full_csv_second(CSV):
    f = open("sleepfile.csv")
    header = f.readline().lower().split(",")
    actindex = header.index("activity")
    offdata = []
    data = open("sleepfile.txt").readlines()
    if "off-wrist status" in header:
        offindex = header.index("off-wrist status")
        offdata = [data[offindex] for line in data]
    writer = open("sleepfile.txt","w",newline = "")  #rewriting the sleep CSV
    sleep_index = []
    average = get_activity_list("sleepfile.csv", actindex)
    sleep_index = sleep_index(average)
    movingaverages = average_p(average, offdata)
    counter = 0
    for line in data:
        writer.write(line+sleep_index[counter]+movingaverages[counter])
        counter += 1
