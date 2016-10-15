import datetime


import time
import plotly.plotly as py
import plotly.graph_objs as go
from IPython.display import Image
f = open("sleepindex.csv")
counter = -1
bluelightCol = -1

for header in f.readline().split(","):
    counter += 1
    if header == "time":
        timeCol = counter
    if header == "date":
        dateCol = counter
    if header == "activity" or header == "activity\n":
        actCol = counter
    if header == "sleepindex":
        sleepIndCol = counter
    if header == "bluelight":
        bluelightCol = counter



firstRow = f.readline().split(",")
firstTime = firstRow[timeCol]  # must remove this from CSV entirely
firstDate = firstRow[dateCol]  # must remove this from CSV entirely

s = firstDate + " " + firstTime
# FIRST TIME/DATE print(time.mktime(datetime.datetime.strptime(s, "%d/%m/%Y %H:%M:%S").timetuple()))
unixTimes = []
allTheRows = []
actList = []
sleepIndList = []
datetimes = []
bluelight = []
filedata = f.readlines()
for row in filedata:
    row = row.split(",")
    tim = row[timeCol]
    dat = row[dateCol].split("/")
    dat = str(dat[2]+"-"+dat[1]+"-"+dat[0])
    smth = dat + " " + tim
    actList.append(row[actCol].strip("\n"))
    datetimes.append(smth)
    sleepIndList.append(int(row[sleepIndCol]))

    if bluelightCol != -1:
        bluelight.append(row[bluelightCol])


for act in range(0, len(actList)):
    if actList[act] == "nan":
        actList[act] = 0

activity = go.Scatter(
    x=datetimes,
    y=actList,
    fill='tozeroy',

)
colorList = []

for sleepindex in sleepIndList:
    #   str((((thing - 1) * (255 - 0)) / (5000 - 1)) + 0)
    colorList.append('rgba(' + str((sleepindex) * 255 / 10) + "," + str((sleepindex) * 255 / 10)+',255,1)')


sleepIndList = [5000] * len(datetimes)

sleepindex = go.Bar(
            x=datetimes,
            y=sleepIndList,
    marker=dict(
        color=colorList)
)

data = [sleepindex, activity]

margin = go.Margin(l=35,r=35,t=5,b=5, pad=0)

if bluelightCol!=-1:
    light = go.Scatter(
            x=datetimes,
            y=bluelight,
            yaxis = "y2"
    )
    data.append(light)
    layout = go.Layout(autosize = False, width = 20000, height = 700, showlegend=False,
                       xaxis=dict(dtick=10800000, tickwidth=1, ticklen=8),
                       yaxis=dict(rangemode="tozero"),
                       yaxis2=dict(overlaying="y", anchor = "free", side="left", rangemode="tozero"))
    layout2 = go.Layout(showlegend=False, margin=margin, autosize = False, width=1200, height=500,
                           xaxis=dict(autotick=False, showticklabels=False),
                           yaxis=dict(rangemode="tozero", side="left"),
                           yaxis2=dict(overlaying="y", side="right", rangemode="tozero"))
else:
    layout = go.Layout(showlegend=False, autosize = False, width = 20000, height = 700,
                   xaxis=dict(dtick=10800000, tickwidth=1, ticklen=8))
    layout2 = go.Layout(showlegend=False, margin=margin, autosize = False, width=1200, height=500,
                           xaxis=dict(autotick=False, showticklabels=False))


print('plotting')
fig = go.Figure(data=data, layout=layout)

py.image.save_as(fig, filename="full.png")
print("plotted full length")
Image("adult.png")
# day by day images

going = True
x=0
totalmin = (time.mktime(datetime.datetime.strptime(datetimes[-1], "%Y-%m-%d %H:%M:%S").timetuple())- time.mktime(datetime.datetime.strptime(datetimes[0], "%Y-%m-%d %H:%M:%S").timetuple()))/60


if bluelightCol!=-1:
    while going:
        if x+1440<totalmin:
            fig = go.Figure(data=[go.Bar(
                x=datetimes[x:x+1440],
                y=sleepIndList[x:x+1440],
                marker=dict(color=colorList[x:x+1440]))
            ,go.Scatter(
                x=datetimes[x:x+1440],
                y=actList[x:x+1440],
                fill='tozeroy'),
            go.Scatter(
                x=datetimes[x:x+1440],
                y=bluelight[x:x+1440],
                yaxis = "y2"
                )], layout=layout2)
        else:
            fig = go.Figure(data=[go.Bar(
                x=datetimes[x:],
                y=sleepIndList[x:],
                marker=dict(
                color=colorList[x:])
            )
            ,go.Scatter(
                x=datetimes[x:],
                y=actList[x:],
                fill='tozeroy'),
            go.Scatter(
                x=datetimes[x:],
                y=bluelight[x:],
                yaxis = "y2"
                )], layout=layout2)
            going = False
        name = "day"+str(int(x/1440))+".png"
        py.image.save_as(fig,filename = name)
        Image(name)
        print("plotted "+ str(int(x/1440)))
        x += 1440

else:
    while going:
        if x+1440<totalmin:
            fig = go.Figure(data=[go.Bar(
                x=datetimes[x:x+1440],
                y=sleepIndList[x:x+1440],
                marker=dict(color=colorList[x:x+1440]))
            ,go.Scatter(
                x=datetimes[x:x+1440],
                y=actList[x:x+1440],
                fill='tozeroy')], layout=layout2)
        else:
            fig = go.Figure(data=[go.Bar(
                x=datetimes[x:],
                y=sleepIndList[x:],
                marker=dict(
                color=colorList[x:])
            )
            ,go.Scatter(
                x=datetimes[x:],
                y=actList[x:],
                fill='tozeroy')],layout=layout2)
            going = False
        name = "day"+str(int(x/1440))+".png"
        py.image.save_as(fig,filename = name)
        Image(name)
        print("plotted "+ str(int(x/1440)))
        x += 1440

f.close()
out = open("starttime.txt", "w")
out.write(filedata[0].split(",")[timeCol])
out.close()