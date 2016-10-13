#Function: "error_gateway" to be used before Marc's validification process.
#Note that the function takes four arguments:
#1. The name of the file being read (eg."Actigraphy data_Adult.csv")
#2. The name of a text file or similar capable of holding an error message (eg. "Error_File.txt")
#3. The name of a text file or similar capable of holding the data type - activity and light or no light
#4. The name of a csv file capable of being written to that will hold the clean data (eg. "Output_data.csv")

def error_gateway(inputfilename, errorfilename, datatypefile, outputfile):

#Creation of variables
    xdata = open(inputfilename)
    xerrorfile = open(errorfilename, 'w')
    xdatatype = open(datatypefile, 'w')
    xoutputcsv = open(outputfile, 'w')
    listdata = []
    mylist = []
    newfulllist = []
    ablist = []
    linecounter = -1
    propertypresent = False
    epochpresent = False
    logmodeline = 0
    datatype = "null"

#Putting the original data file into one big list of lists
    for line in xdata:
        mylist = line.split(",")
        listdata.append(mylist)

#Checking to see if the data and properties are present (Also checks position of logging mode and the data)
    for a in listdata:
        linecounter += 1
        for i in a:
            if "Actiwatch Data Properties" in i:
                propertypresent = True
                propertyline = linecounter
            elif "Epoch-by-Epoch" in i:
                epochpresent = True
                epochline = linecounter
            elif "Marker" in i:
                dataline = True
                dataline = linecounter
            elif "Logging Mode" in i:
                logmodeline = linecounter

#Returning an error if either the data or properties aren't present
    if epochpresent == False:
        xerrorfile.write("Error: Epoch-by-Epoch data not present in file. Please submit a valid file.")
        xerrorfile.close()
        return("Error")
    elif propertypresent == False:
        xerrorfile.write("Error: Actiwatch Data Properties not present in file. Please submit a valid file.")
        xerrorfile.close()
        return("Error")
    else:
        xerrorfile.write("None")

#Finding out which type of data it is
    datatype = listdata[logmodeline]
    datatype = datatype[1]
#Datatype presented as either "Activity Only" or "Activity
#A single word will be written to the data file; either "Activity" if ONLY activity or "Light" if Activity AND Light
    if "Only" in datatype:
        xdatatype.write("Activity Only")
        xdatatype.close()
    else:
        xdatatype.write("Light")
        xdatatype.close()

#Deleting useless data and null values
    del(listdata[0:dataline])
    del(listdata[1])
    del(listdata[len(listdata)-1])

#Removes quotation marks and turns all string values into lower case
    for a in listdata:
        ablist = []
        for b in a:
            b = b.replace('"', '').replace(" ", "")
            b = b.lower()
            ablist.append(b)
        newfulllist.append(ablist)

#Writing to a csv file in proper format
    for a in newfulllist:
        for b in a:
            xoutputcsv.write(b)
            if b != "\n":
                xoutputcsv.write(",")
    return("Success")