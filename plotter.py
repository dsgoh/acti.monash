def plot(file):
    import datetime
    import time
    import plotly.plotly as py
    import plotly.graph_objs as go
    f = open(file)
    counter = -1

    for header in f.readline().split(","):
        counter += 1
        print(header)
        if header == "time":
            timeCol = counter
        if header == "date":
            dateCol = counter
        if header == "activity" or header == "activity\n":
            actCol = counter
        if header == "sleepindex":
            sleepIndCol = counter



    print(actCol)
    firstRow = f.readline().split(",")
    firstTime = firstRow[timeCol] # must remove this from CSV entirely
    firstDate = firstRow[dateCol] # must remove this from CSV entirely

    s = firstDate + " " + firstTime
    # FIRST TIME/DATE print(time.mktime(datetime.datetime.strptime(s, "%d/%m/%Y %H:%M:%S").timetuple()))
    unixTimes = []
    allTheRows = []
    actList = []
    sleepIndList = []
    for row in f.readlines():
        row = row.split(",")
        tim = row[timeCol]
        dat = row[dateCol]
        smth = dat + " " + tim
        actList.append(row[actCol].strip("\n"))

        #print(row)
        sleepIndList.append(int(row[sleepIndCol])*500)
        unixTimes.append(time.mktime(datetime.datetime.strptime(smth, "%d/%m/%Y %H:%M:%S").timetuple()))
        allTheRows.append(row)

    allTheRows = allTheRows[1:-1]
    unixTimes = unixTimes[1:-1]


    for act in range(0, len(actList)):
        if actList[act] == "nan":
            actList[act] = 0

    trace2 = go.Scatter(
        x=unixTimes,
        y=actList,
        fill='tozeroy',

    )
    colorList = []
    for thing in sleepIndList:
        #   str((((thing - 1) * (255 - 0)) / (5000 - 1)) + 0)
        colorList.append('rgba(' + str((((thing - 1) * (255 - 0)) / (5000 - 1)) + 0) + ',0,0,1)')

    print('HELLO')


    sleepIndList = [5000] * len(unixTimes)
    print(sleepIndList[0:100])

    trace1 = go.Bar(
                x=unixTimes,
                y=sleepIndList,
        marker=dict(
            color=colorList),
    )


    print('AYE')
    data = [trace1, trace2]
    print('plotting')
    py.iplot(data, filename='basic-area')
    print('uh')
