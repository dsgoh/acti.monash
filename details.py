def details(f):
    out = open("detail.csv","w")

    file = f.readlines()
    for line in file:
        if line[0][-1] == ":":
            out.write(" ".join(line))

    out.close()
