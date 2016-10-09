index_activity_check = []
def activity_check(activity_level):
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


lightinfo = open("lightinfo.csv","r")
def light_check(index_activity_check_value,light_value):
    light = int(light_value)
    if 10 in index_activity_check_value:





index = None
# def sleep_index(time_stamp):
