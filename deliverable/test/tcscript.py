# 13519214
# Linux command
# Run with following commands on repository folder
# python3 test/tcscript.py | java <class file> | tee test/testlog.txt


# Linear Equation Test Case Check
dir = "test/"
pfx = "tc_"
flnm = ["1a","1b","1c","1d_hb1","1d_hb2","2a","2b","3a","3b","4"]
sle = ["_gauss","_gaussjordan","_inverse","_cramer"]
sqrmtx = [1,0,0,1,1,1,0,1,0,1]

for a in range(len(flnm)):
    for i in range(4):
        if ((sqrmtx[a]) and (i > 1)) or (i < 2):
            print(1)
            print(i+1)
            print(1)
            print(dir + pfx + flnm[a])
            print("y")
            print(dir + "output/lineq/" + pfx + flnm[a] + sle[i] + ".txt")
        else:
            break

# Determinant Test Case Check
dt = ["_cofactor","_reducedrow"]
for a in range(len(flnm)):
    if (sqrmtx[a]):
        for i in range(2):
            print(2)
            print(i+1)
            print(1)
            print(dir + pfx + flnm[a] + "_sqr")
            print("y")
            print(dir + "output/det/" + pfx + flnm[a] + dt[i] + ".txt")

# Inverse Test Case Check
for a in range(len(flnm)):
    if (sqrmtx[a]):
        print(3)
        print(1)
        print(dir + pfx + flnm[a] + "_sqr")
        print("y")
        print(dir + "output/inv/" + pfx + flnm[a] + "_inversemat" + ".txt")

# Interpolation Test Case Check
flnm = ["5_1","5_2","5_3","5_4","6_1","6_2","6_3","6_4","7"]
intpl = "_interpolation"

for a in range(len(flnm)):
    print(4)
    print(1)
    print(dir + pfx + flnm[a])
    print("y")
    print(dir + "output/intpl/" + pfx + flnm[a] + intpl + ".txt")

# Regression Test Case Check
flnm = ["8"]
reg = "_regression"
tcreg = ["50 76 29.3"]

for a in range(len(flnm)):
    print(5)
    print(1)
    print(dir + pfx + flnm[a])
    print(tcreg[a])
    print("y")
    print(dir + "output/reg/" + pfx + flnm[a] + reg + ".txt")

# Exit
print("6")
