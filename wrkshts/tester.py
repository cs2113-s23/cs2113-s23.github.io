
import re

def good(input, output):
	searchObj = re.search( r'^checkCode [1-9]\d?\d?\.([A-Z][A-Za-z]* )+ *(\d\d\d\d\d)?$', input, re.M)
	if searchObj:
		if output != "true":
			print "Error, on input '" + input + "', we got 'true' but you expected '" + output + "'"
			return False
	else:
		if output != "false":
			print "Error, on input '" + input + "', we got 'false' but you expected '" + output + "'"
			return False	
	return True	
	
def bad(input, output):
	searchObj = re.search( r'^checkCode [1-9]\d?\d?\.([A-Z][A-Za-z0-9]* )+ *(\d\d\d\d\d)?$', input, re.M)
	if searchObj:
		if output != "true":
			return True
	else:
		if output != "false":
			return True	
	return False		

file = open("./DriverJava1_sample.java")
contents = file.readlines()
file.close()

inputs = []
outputs = {}
ctr = 0
while ctr < len(contents):
	if '",' in contents[ctr]:
		data = contents[ctr]
		start = data.index('"')
		end = data.index('",')
		data = data[start+1:end]
		out = contents[ctr+1]
		start = out.index('"')
		end = out.index('",')
		out = out[start+1:end]
		inputs.append(data)
		outputs[data] = out	
		ctr = ctr + 1
	ctr = ctr + 1

correct = True
ctr = 1
for input in inputs:
	result = good(input, outputs[input])
	if not result:
		print ("for test case " + str(ctr))
	correct = correct and result
	ctr += 1
	
if correct:
	print ("All of your test cases were correct!")
	
bug = False
for input in inputs:
	if bad(input, outputs[input]) and correct:
		bug = True
		print ("Great job! You found the bug")
		
if not bug and correct:
	print ("Try writing more test cases. You did not find the bug.")
	
