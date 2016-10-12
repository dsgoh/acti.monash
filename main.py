class MyError(Exception):
    pass
try:
    import os
    import epochcheck
    name = "data"
    extension = ""

    for file in os.listdir('.'):
        if file == name+".csv" or file == name+".txt":
            extension = file.split(".")[-1]
            break

    f = open(name+"."+extension)

    fatalerror = False

    details = epochcheck.details(f)  # extracts details to file "details.txt"
    epochexists = epochcheck.epochcheck(f)  # writes the epoch data to "sleepfile.csv" and returns false if not present
    lightpresent = epochcheck.lightcheck(f)  # writes log type of acti watch to "mode.txt" and returns light present
    error = False
    if not epochexists:
        crashlog = open("crashes.txt","w")
        crashlog.write("Epoch by Epoch data does not exist!")
        crashlog.close()
        error = True
    if not lightpresent:
        crashlog = open("crashes.txt","w")
        crashlog.write("Actiwatch Data Properties must have a logging mode")
        crashlog.close()
        error = True
    if error:
        raise Exception
    import Validate
    crashlog = open("crashes.txt")
    if crashlog.readline() != "None":
        raise Exception

    epochcheck.formatcsv()  # opens "sleepfile.csv" and formats and overwrites this file with formatted
    if lightpresent != "Activity Only":
        import LightData

    import sleepindex
    import dylangraph  # crosses fingers
    import summarynadine # cross hearts

f = open("worked.txt", "w")
f.write("True")
f.close()
except MyError:
    pass
except Exception as e:
    f = open("Error_Log","w")
    f.write(str(e))
    f.close()
    raise Exception

