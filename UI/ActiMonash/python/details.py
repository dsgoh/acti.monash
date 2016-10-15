def details(f):
    out = open("detail.csv","w")

    file = f.readlines()
    for line in file:
        line = line.replace('"',"").replace("'","").split(",")
        if len(line)>1:
            if line[0] and line[0][-1] == ":" and line[1] and line[1] != "\n" and line[1] != "Not Entered\n" and line[1] !="Not Applicable\n":
                    out.write(" ".join(line))

    out.close()
