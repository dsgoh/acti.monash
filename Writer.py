import csv

def csv_writer(data,path): #writing function where the path is the file you would like to open
    with open(path, "w") as csv_file:
        writer = csv.writer(csv_file,delimiter=',')
        for item in data:
            writer.writerow(item)


time_stamps = [1,2]
activity_count = [2,2]
sleep_status = ["SLEEP",2]
red_light_values = ["1.3X#",2]
blue_light_values = ["fwihw",2]
white_light_values = ["jwf",2]
green_light_values = ["124367",2]
marker = [1,2]


index = 0
data = open("graph_data.csv","w",newline = "")  #writing the graph CSV
while index < (len(time_stamps)):
    graph_data = [time_stamps[index],activity_count[index],sleep_status[index],red_light_values[index],blue_light_values[index],white_light_values[index],marker[index]]
    # csv_writer(graph_data,"graph_data.csv")
    write = csv.writer(data,delimiter=',')
    write.writerow(graph_data)
    index +=1

index = 0
data = open("light_values.csv","w",newline = "")  #writing the light CSV
while index < (len(time_stamps)):
    light_data = [time_stamps[index],sleep_status[index],red_light_values[index],blue_light_values[index],white_light_values[index],green_light_values[index],marker[index]]
    # csv_writer(graph_data,"graph_data.csv")
    write = csv.writer(data,delimiter=',')
    write.writerow(light_data)
    index +=1
