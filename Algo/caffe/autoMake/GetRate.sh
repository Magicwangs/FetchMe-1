#!/bin/bash
CLASS="oxfordCatsAndDogs"
DataDir="/cygdrive/f/sync/datacollection/$CLASS"

CLASSDATADIR="../data/$CLASS"
CLASSEXAMPLESDIR="../examples/$CLASS"
CLASSMODELSDIR="../models/$CLASS"

BASHDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
RateTrainingFile="$BASHDIR/RateTraining.txt"
RateValingFile="$BASHDIR/RateValing.txt"
#DataDir="/cygdrive/g/GitHub/DLEngine/ImageSource/Dogs"
TrainListTmpFILE="$BASHDIR/TrainListTmp.txt"


if [ -d $CLASSDATADIR ]
then
	echo "$CLASSDATADIR exists good"
else
	echo "make the directory for $CLASSDATADIR"
	`mkdir $CLASSDATADIR`
fi

if [ -d $CLASSEXAMPLESDIR ]
then
	echo "$CLASSEXAMPLESDIR exists good"
else
	echo "make the directory for $CLASSEXAMPLESDIR"
	`mkdir $CLASSEXAMPLESDIR`
fi

if [ -d $CLASSMODELSDIR ]
then
	echo "$CLASSMODELSDIR exists good"
else
	echo "make the directory for $CLASSMODELSDIR"
	`mkdir $CLASSMODELSDIR`
fi

# using sed to treate the template file
sed "s/dog/$CLASS/g" "examples/dog/create_dog.sh" > "$CLASSEXAMPLESDIR/create_$CLASS.sh"
sed "s/dog/$CLASS/g" "examples/dog/make_dog_mean.sh" > "$CLASSEXAMPLESDIR/make_`echo $CLASS`_mean.sh"

sed "s/dog/$CLASS/g" "models/dog/deploy.prototxt" > "$CLASSMODELSDIR/deploy.prototxt"
sed "s/dog/$CLASS/g" "models/dog/solver.prototxt" > "$CLASSMODELSDIR/solver.prototxt"
sed "s/dog/$CLASS/g" "models/dog/train_val.prototxt" > "$CLASSMODELSDIR/train_val.prototxt"

if [ -d $DataDir ]
then
	echo "get data from $DataDir"
else
	echo "plase set a validate directory"
	exit
fi
cd $DataDir
if [ -f $RateTrainingFile ]
then
	echo "remove $RateTrainingFile"
	rm $RateTrainingFile
fi
if [ -f $RateValingFile ]
then
	echo "remove $RateValingFile"
	rm $RateValingFile
fi
if [ -f $TrainListTmpFILE ]
then
	echo "remove $TrainListTmpFILE"
	rm $TrainListTmpFILE
fi
rate=0.7
for dir in `find . -type d -not -name "."`
do
	filecount=`find $dir -type f -name "*.jpg" | wc -l`
	trainfilecount=`echo $filecount*$rate/1 | bc`
	valfilecount=`echo $filecount-$trainfilecount | bc`
	echo "filecount=$filecount"
	echo "trainfilecount=$trainfilecount"
	echo "valfilecount=$valfilecount"
	trainlist=`shuf -i 1-$filecount -n $trainfilecount`
	#echo $trainlist
	echo "$trainlist" > $TrainListTmpFILE
	index=0
	for file in `find $dir -name "*.jpg"`
	do
		index=`echo $index+1 | bc`
		#echo "index=$index"
		isTrain=`sed -n "/^$index$/p" $TrainListTmpFILE | wc -l`
		if [ $isTrain -eq 1 ]
		then
			#echo "train $index"
			echo $file >> $RateTrainingFile
		else
			#echo "val $index"
			echo $file >> $RateValingFile
		fi
		#echo $file
	done
done
if [ -f $TrainListTmpFILE ]
then
	echo "remove $TrainListTmpFILE"
	rm $TrainListTmpFILE
fi
echo 'done'

python GetRealRateTraining.py
python GetRealRateValing.py
`cp RealRateTraining.txt $CLASSDATADIR/train.txt`
`cp RealRateValing.txt $CLASSDATADIR/val.txt`
