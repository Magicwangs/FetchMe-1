#!/usr/bin/env sh

EXAMPLE=examples/oxfordCatsAndDogs
DATA=data/oxfordCatsAndDogs
TOOLS=build/tools

$TOOLS/compute_image_mean $EXAMPLE/oxfordCatsAndDogs_train_lmdb \
  $DATA/oxfordCatsAndDogs_mean.binaryproto

echo "Done."
