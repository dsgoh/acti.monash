import statistics
import csv
f = open("sleepfile.txt")
names = [columnName.lower() for columnName in (f.readline().replace('"',"")).split(",")]
info = {} #Contains the light level and sleep status for all times
rangeInfo = [[]for i in range(0,4)]
indexes={}
neededColumns = ["Off-Wrist Status","Epoch","Activity","White Light","Red Light","Green Light","Blue Light","Sleep/Wake"]
if neededColumns[0].lower() in names:
    indexes[neededColumns[0]]=names.index[neededColumns[0].lower()]
for needed in neededColumns[1:]:
    indexes[needed]=names.index(needed.lower())
for data in csv.reader(f):
    if ("Off-Wrist Status" not in indexes or int(data[indexes["Off-Wrist Status"]])==0) and "NaN" not in [data[indexes[column]] for column in neededColumns[1:]]:
        colours=[]
        for colour in neededColumns[3:7]:
            colours.append(float(data[indexes[colour]]))
        info[int(data[1])]=(colours,int(data[11]))
        for colour in range(4):
            rangeInfo[colour].append((colours[colour],int(data[1])))
colCount = 0
result = [[] for i in range(4)]
totalAsleep = [0 for i in range(4)]
colourTrans = {3: ["Blue",(0,0,1)], 2: ["Green",(0,1,0)], 1: ["Red",(1,0,0)], 0: ["White",(173/255,173/255,173/255)]}
colourLength = []
for rangeCol in rangeInfo:
    minval = min(rangeCol)
    maxval = max(rangeCol)
    rangeCol = [val for val in sorted(rangeCol) if val[0]!=maxval[0] and val[0]!=minval[0]]
    colourLength.append(len(rangeCol))
    ranges = 60
    change = len(rangeCol)/ranges
    i = 0
    bins = []
    while len(bins)<ranges: #float equalities
        bins.append(rangeCol[round(i*change):round((i+1)*change)-1])
        i+=1
    maxProb = (0,0)
    binCount = 0
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
                totalAsleep[colCount]+=1
        prob = 100*counter/len(bin)
        if prob > maxProb[0]:
            maxProb = (prob,binCount)
        #print("\n--------------Summary of Sleep when %s Light Level is Between %s and %s (photons/s*m^2)--------------"%(colourTrans[colCount],round(bin[0][0]),round(bin[-1][0])))

        #print("Probablity of Sleep: %s%%\nAverage number of counts spent asleep in the next hour after being exposed to this light level\nMean: %s, Median: %s, Mode.txt: %s"%((round(100*(100*counter/len(bin)))/100),round(100*statistics.mean(full))/100,statistics.median(full), statistics.mode(full)))
        if binCount != 0:
            lBound = (bins[binCount-1][-1][0]+bins[binCount][0][0])/2
        else:
            lBound = bin[0][0]
        if binCount != ranges-1:
            uBound = (bins[binCount+1][0][0]+bins[binCount][-1][0])/2
        result[colCount].append((round(lBound),round(uBound),round(100*statistics.mean(full))/100, round(100*prob)/100,freqHist))
        binCount += 1
    colCount += 1
f = open("LightCluster.csv", "w", newline="")
csvfile = csv.writer(f)
csvfile.writerow(["Colour", "Lower Bound of Range", "Upper Bound of Range", "Spike Size", "Occurences"])
f2 = open("LightInfo.csv", "w", newline="")
csvfile2 = csv.writer(f2)
csvfile2.writerow(["Colour", "Bin Number","Lower Bound of Range", "Upper Bound of Range", "Probablity of Sleep", "Average Counts spent Asleep"])
bin = 1
lines = [[]for i in range(4)]
for colour in range(4):
    colInfo = result[colour]
    colourLineInfo = [[]for i in range(2)]
    for cRange in colInfo:
        for spikeSize in cRange[4]:
            csvfile.writerow([colourTrans[colour][0]]+[bin]+list(cRange[:2])+[spikeSize]+[cRange[4][spikeSize]])
        csvfile2.writerow([colourTrans[colour][0]]+[bin]+list(cRange[:2])+[cRange[3]]+[cRange[2]])
        colourLineInfo[0].append((cRange[0],cRange[1],cRange[3]))
        colourLineInfo[1].append((cRange[0], cRange[1],cRange[2]))
    bin+=1
    lines[colour] = colourLineInfo
import matplotlib.pyplot as plt
import math
for colour in range(4):
    for i in range(2):
        ax = plt.gca()
        for point in lines[colour][i]:
            if colour!=0:
                ax.bar(math.log10(point[1]),point[2],(math.log10(point[0])-math.log10(point[1])),color = colourTrans[colour][1])
            else:
                ax.bar(point[1], point[2], (point[0] - point[1]),color=colourTrans[colour][1])
        if colour!=0:
            plt.xlabel("Brightness, Log10(Photon Intensity) of %s Light"%(colourTrans[colour][0]))
            if i==0:
                plt.plot([math.log10(lines[colour][i][0][0]),math.log10(lines[colour][i][-1][0])],[100*totalAsleep[colour]/colourLength[colour],100*totalAsleep[colour]/colourLength[colour]],color = (0,0,0))
        else:
            plt.xlabel("Brightness, Lux of White Light")
            if i==0:
                plt.plot([lines[colour][i][0][0], lines[colour][i][-1][0]],[100 * totalAsleep[colour] / colourLength[colour], 100 * totalAsleep[colour] / colourLength[colour]],color=(0, 0, 0))
        if i==0:
            plt.ylabel("Percentage (%%) Chance of Sleep, with Expected Chance Prior")
        else:
            plt.ylabel("Expected time (in minutes) spent asleep\nafter being exposed to light level in an hour")
        plt.savefig("%s %s.png"%(colourTrans[colour][0],("Probablity","Count_Asleep")[i]))
        plt.gcf().clear()
