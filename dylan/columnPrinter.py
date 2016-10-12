import datetime
import time
import plotly.plotly as py
import plotly.graph_objs as go
f = open("sleepii.csv")
counter = -1
actCol = 6
for header in f.readline().split(","):
    counter += 1
    if header == "time":
        timeCol = counter
    if header == "date":
        dateCol = counter
    if header == "activity" or header == "activity\n":
        actCol = counter
print(actCol)
firstRow = f.readline().split(",")
firstTime = firstRow[timeCol] #must remove this from CSV entirely
firstDate = firstRow[dateCol] #must remove this from CSV entirely

s = firstDate + " " + firstTime
#FIRST TIME/DATE print(time.mktime(datetime.datetime.strptime(s, "%d/%m/%Y %H:%M:%S").timetuple()))
unixTimes = []
allTheRows = []
actList = []
for row in f.readlines():
    row = row.split(",")
    tim = row[timeCol]
    dat = row[dateCol]
    smth = dat + " " + tim
    actList.append(row[actCol].strip("\n"))
    unixTimes.append(time.mktime(datetime.datetime.strptime(smth, "%d/%m/%Y %H:%M:%S").timetuple()))
    allTheRows.append(row)

allTheRows = allTheRows[1:-1]
unixTimes = unixTimes[1:-1]

print(allTheRows)
print(unixTimes)

for act in range(0, len(actList)):
    if actList[act] == "NaN":
        actList[act] = 0
print(actList)
trace1 = go.Scatter(
    x=unixTimes,
    y=actList,
    fill='tozeroy'

)

data = [trace1]
py.iplot(data, filename='basic-area')