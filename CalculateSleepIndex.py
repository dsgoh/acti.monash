def get_activity_list(data, index):  # gets activity data and places into list
    activity = [line[index] for line in data]
    return activity


def average_p(time, off_wrist):  # does averages for all values
    averages = []
    for i in range(len(time)):
        total = 0
        count = 0
        for k in range(-10, 11):
            if 0 <= i-k < len(time) and time[i-k].lower() != "nan" and (not off_wrist or not off_wrist[i-k]):
                total += time[i-k]
                count += 1
        if count != 0:
            averages.append(total/count)
        else:
            averages.append("nan")
    return averages


def sleep_index(activity, off_wrist):  # appends sleep index of each point to list
    item = average_p(activity, off_wrist)
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
    f = open(CSV)

    header = f.readline().lower().split(",")
    actindex = header.index("activity")
    offdata = []
    data = f.readlines()

    if "off-wriststatus" in header:
        offindex = header.index("off-wriststatus")
        offdata = [line[offindex] for line in data]

    writer = open("sleepindexedit.csv", "w", newline="")  # rewriting the sleep CSV
    writer.write(",".join(header))

    activity = get_activity_list(data, actindex)
    sleep_indexes = sleep_index(activity, offdata)
    movingaverages = average_p(activity, offdata)

    for line in range(len(data)):
        writer.write(str(data[line]).strip()+","+str(sleep_indexes[line])+","+str(movingaverages[line]) + "\n")

    writer.close()
    f.close()

# TODO Fix the off data algorithm - rather than having it as a parameter
