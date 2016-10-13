import csv
f = open("data.csv")
out = open("detail.csv","w")

reader = csv.reader(f)
for line in reader:
    if line[0][-1] == ":":
        out.write(" ".join(line))
        
f.close()
out.close()
