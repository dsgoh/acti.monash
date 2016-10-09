import statistics
import csv
f = open("sleepfile.txt")
names = [columnName.lower() for columnName in (f.readline().replace('"',"")).split(",")]
info = {} #Contains the light level and sleep status for all times
rangeInfo = [[]for i in range(0,4)]
indexes={}
neededColumns = ["Off-Wrist Status","Epoch","Activity","White Light","Red Light","Green Light","Blue Light","Sleep/Wake"]
for needed in neededColumns:
    indexes[needed]=names.index(needed.lower())
for data in csv.reader(f):
    if int(data[indexes["Off-Wrist Status"]])==0 and "NaN" not in [data[indexes[column]] for column in neededColumns[1:]]:
        colours=[]
        for colour in neededColumns[3:7]:
            colours.append(float(data[indexes[colour]]))
        info[int(data[1])]=(colours,int(data[11]))
        for colour in range(4):
            rangeInfo[colour].append((colours[colour],int(data[1])))
colCount = 0
result = [[] for i in range(4)]
for rangeCol in rangeInfo:
    rangeCol = sorted(rangeCol)
    ranges = 10
    print(ranges)
    change = len(rangeCol)/ranges
    i = 0
    bins = []
    while len(bins)<ranges: #float equalities
        bins.append(rangeCol[round(i*change):round((i+1)*change)-1])
        i+=1
    maxProb = (0,0)
    binCount = 0
    colourTrans = {3: "Blue", 2: "Green", 1: "Red", 0: "White"}
    for bin in bins:
        counter = 0
        freqHist = {i: 0 for i in range(0,60)}
        full = []
        for point in bin:
            freq = 0
            for i in range(1, 61):
                if point[1] + i in info:
                    if not info[point[1] + i][1]:
                        freq += 1
            freqHist[freq] = freqHist.get(freq, 0) + 1
            full.append(freq)
            if not info[point[1]][1]:
                counter += 1
        prob = 100*counter/len(bin)
        if prob > maxProb[0]:
            maxProb = (prob,binCount)
        #print("\n--------------Summary of Sleep when %s Light Level is Between %s and %s (photons/s*m^2)--------------"%(colourTrans[colCount],round(bin[0][0]),round(bin[-1][0])))
        binCount += 1
        #print("Probablity of Sleep: %s%%\nAverage number of counts spent asleep in the next hour after being exposed to this light level\nMean: %s, Median: %s, Mode: %s"%((round(100*(100*counter/len(bin)))/100),round(100*statistics.mean(full))/100,statistics.median(full), statistics.mode(full)))
        result[colCount].append((round(bin[0][0]),round(bin[-1][0]),round(100*statistics.mean(full))/100, round(100*prob)/100,freqHist))
    colCount += 1
f = open("LightCluster.csv", "w", newline="")
csvfile = csv.writer(f)
csvfile.writerow(["Colour", "Lower Bound of Range", "Upper Bound of Range", "Spike Size", "Occurences"])
f2 = open("LightInfo.csv", "w", newline="")
csvfile2 = csv.writer(f2)
csvfile2.writerow(["Colour", "Lower Bound of Range", "Upper Bound of Range", "Probablity of Sleep", "Average Counts spent Asleep"])
for colour in (0,1,2,3):
    colInfo = result[colour]
    for cRange in colInfo:
        for spikeSize in cRange[4]:
            csvfile.writerow([colourTrans[colour]]+list(cRange[:2])+[spikeSize]+[cRange[4][spikeSize]])
        csvfile2.writerow([colourTrans[colour]]+list(cRange[:2])+[cRange[3]]+[cRange[2]])
