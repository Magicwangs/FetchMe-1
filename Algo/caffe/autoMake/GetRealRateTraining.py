import os


if os.path.isfile('RateTraining.txt') == False:
	print "get RateTraining.txt first"
	exit()
if os.path.isfile('RealRateTraining.txt') == True:
	print "remove RealRateTraining.txt"
	os.remove('RealRateTraining.txt')

rateTrainingFile = open('RateTraining.txt', 'rb')
realRateTrainingFile = open('RealRateTraining.txt', 'ab')

index = []
indexNumber = -1

for oneline in rateTrainingFile.readlines():
	oneline = oneline[2:]
	onelineIndex = oneline.split('/', 1)[0]
	if onelineIndex not in index:
		index.append(onelineIndex)
		indexNumber = indexNumber + 1
	realOut = oneline.strip() + ' ' + str(indexNumber) + '\n'
	print realOut
	realRateTrainingFile.write(realOut)
