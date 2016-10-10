index_activity_check = []
def activity_check(activity_level): #Looking at the activity level and deciding whether first half of index or second
    acti = int(activity_level)
    if acti <= 30:
        index = 1
        while index <= 5:
            index_activity_check.append(index)
            index += 1
    else:
        index = 5
        while index <= 10:
            index_activity_check.append(index)
            index += 1
    return index_activity_check


light_list = []
lightinfo = open("lightinfo.csv")
headers = lightinfo.readline()
for line in lightinfo:
    datalist = line.split(",")
    light_list.append(datalist[1])
    light_list.append(datalist[2])

blue_light_range = []
1 =

print(light_list)

# def probability_of_light(light_value):
#
#
#
#
#
# def light_check(index_activity_check_value):
#     if 10 in index_activity_check_value:






index = None
# def sleep_index(time_stamp):

#Basically what happens in this enitre thing:
#Check the activity, 1-5 or 5-10
#Check the probability of the light at a point
#Use to determine the rest of the index.