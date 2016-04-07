import os


if os.path.isfile('RateValing.txt') == False:
	print "get RateValing.txt first"
	exit()
if os.path.isfile('RealRateValing.txt') == True:
	print "remove RealRateValing.txt"
	os.remove('RealRateValing.txt')
rateValingFile = open('RateValing.txt', 'rb')
realRateValingFile = open('RealRateValing.txt', 'ab')

index = []
indexNumber = -1

for oneline in rateValingFile.readlines():
	oneline = oneline[2:]
	onelineIndex = oneline.split('/', 1)[0]
	twolineIndex = oneline.split('/', 1)[1]
	if onelineIndex not in index:
		index.append(onelineIndex)
		indexNumber = indexNumber + 1
	realOut = twolineIndex.strip() + ' ' + str(indexNumber) + '\n'
	print realOut
	realRateValingFile.write(realOut)
