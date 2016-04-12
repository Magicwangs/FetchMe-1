import os


if os.path.isfile('RateTraining.txt') == False:
	print "get RateTraining.txt first"
	exit()
if os.path.isfile('synset_words.txt') == True:
	print "remove synset_words.txt"
	os.remove('synset_words.txt')

rateTrainingFile = open('RateTraining.txt', 'rb')
realRateSynsetFile = open('synset_words.txt', 'ab')

index = []
indexNumber = -1

for oneline in rateTrainingFile.readlines():
	oneline = oneline[2:]
	onelineIndex = oneline.split('/', 1)[0]
	if onelineIndex not in index:
		index.append(onelineIndex)
		indexNumber = indexNumber + 1
		realOut = onelineIndex.strip() + ' ' + str(indexNumber) + '\n'
		print realOut
		realRateSynsetFile.write(realOut)
