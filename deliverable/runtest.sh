#!/bin/bash
# Using python3 for script

echo "running test..."
python3 test/tcscript.py | java -cp bin Core | tee test/output/testlog.txt
echo "test done"
