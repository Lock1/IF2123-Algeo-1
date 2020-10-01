# 13519214
# Linux command
# Put script in main repository folder, and run with following commands
# python scriptsaveus.py | java <class file> | tee testlog.txt


# Linear Equation Test Case Check
dir = "test/"
pfx = "tc_"
flnm = ["1a","1b","1c","1d_hb1","1d_hb2","2a","2b","3a","3b"]
sle = ["_gs","_gj","_inv","_crm"]
sqrmtx = [1,0,0,1,1,1,0,1,0]

for a in range(len(flnm)):
    for i in range(4):
        if ((sqrmtx[a]) and (i > 1)) or (i < 2):
            print(1)
            print(i+1)
            print(1)
            print(dir + pfx + flnm[a])
            print("y")
            print(dir + "output/" + pfx + flnm[a] + sle[i] + ".txt")
        else:
            break

# Exit
print("6")
