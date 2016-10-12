#This code is run early on by the program.
#It takes in a file containing the epoch by epoch data,
#and systematically checks it for flaws
#if it detects a flaw, and output file is written
#which is then intrepreted by the GUI and forced the user
#to select a new file or correct the issues.


import csv
import os
name = "sleepfile" #Placeholder name of the input file

crashes = {} #Log of the crashes and lines it happened at

#addCrash(msg,line)
#Takes in a string as a message, and line number
#(normally as an integer) and adds it to the crash log
#@Params
#msg: A string that explains the error. Displayed by the GUI
#line: The line number in the file the program detected the fault on
def addCrash(msg,line):
    crashes[msg] = crashes.get(msg,[])+[str(line)]

#Checks if the file has the right extension (csv or txt).
#This would detect if the file is downright missing as well,
#but under previous specifications that won't happen
extension = ""
for file in os.listdir('.'):
    if file == name+".csv" or file == name+".txt":
        extension = file.split(".")[-1]
        break
else:
    addCrash("File Not Valid","Filename")
if not crashes:
    #Only continues if there is a valid file
    f = open(name+"."+extension)
    header = f.readline()
    indexes = {} #Keeps track of what column each part of the data is located in
    lineNumber = 1

    #Checks whether it needs to check for light level
    modeFile = open("Mode.txt")
    useLight = 1
    if modeFile.readline()=="Activity Only":
        useLight = 0

    #This next section checks for any flaws in the initial line, which is expected to contain the column names
    if header == "":
        addCrash("Empty File",lineNumber)
    elif header == "\n":
        addCrash("Columns Names Should Be First Line",lineNumber)
    elif "," not in header:
        addCrash("Columns Should Be Seperated By Commas",lineNumber)
    else:
        #This checks if there is any missing columns in the data

        header = header.replace('"', "") #Removes quotation marks from the document. Needed because sometimes data values are encased
        #by quotation marks, which messes up typecasting to int/floats and checking the names
        names = header.split(",")
        names = [name.lower() for name in header.split(",")] #Makes everything case-insensitive
        if "Off-Wrist Status".lower() in names:
            indexes["Off-Wrist Status"] = names.index["Off-Wrist Status".lower()] #Only will check off-wrist if it is present
        for needed in ["Epoch","Activity","Sleep/Wake"]+["White Light","Red Light","Green Light","Blue Light"]*useLight:
            #Only checks if the data we are going to use is present.
            if needed.lower() not in names:
                addCrash("Missing Vital Column " + needed,lineNumber)
            else:
                indexes[needed]=names.index(needed.lower())
    if not crashes:
        #Only continues if there are no problems with the header
        csvfile = csv.reader(f)
        lastVal = 0
        linelength = len(names)
        #This iterates through the data to see if there is any problems with any of the cells
        #Even though some of these flaws might only be small and could just be avoided by ignoring
        #that particular row, this program will still force the user to fix them. This is because
        #the data is meant to be generated automatically, so if these flaws are present it's
        #likely there is a problem in the software that generates the data and as such the data set could be wrong
        for line in csvfile:
            lineNumber += 1
            if len(line)!= linelength:
                addCrash("Number of values per line must be consistent with header line",lineNumber)
            else:
                try:
                    if float(line[indexes["Epoch"]])<0:
                        addCrash("Epoch values should be positive",lineNumber)
                    elif float(line[indexes["Epoch"]])<=lastVal:
                        addCrash("Epoch values must always increase",lineNumber)
                    else:
                        lastVal = float(line[indexes["Epoch"]])
                except:
                    #Called if the program can't turn the data in the epoch column into a float.
                    addCrash("Epoch values need to be numeric",lineNumber)
                if "Off-Wrist Status" in indexes and line[indexes["Off-Wrist Status"]].replace(" ","") not in ("0", "1"): #I use a string comparasion to avoid causing crashes
                    # typecasting to an integer
                    addCrash("Off-Wrist Status must be either 0 or 1",lineNumber)
                for var in ["Activity"]+["White Light","Red Light","Green Light","Blue Light"]*useLight:
                    try:
                        if float(line[indexes[var]])<0:
                            addCrash(var+" values should be positive",lineNumber)
                    except:
                        if line[indexes[var]].lower() != "NaN".lower():
                            addCrash(var+" values should always be numeric or NaN (Infinity/Inf not accepted)",lineNumber)
                if line[indexes["Sleep/Wake"]].replace(" ","").lower() not in ("0", "1", "NaN".lower()):
                    addCrash("Sleep/Wake must be either 0 or 1 or NaN",lineNumber)

#This next section goes ahead and write the crash log to a file so it can be interpreted by the GUI, which runs in Java
f = open("crashes.txt","w")
if crashes:
    firstLine = 1
    for crash in crashes:
        f.write("\n"*(not firstLine)+crash)
        firstLine = 0
else:
    f.write("None")
f.close()