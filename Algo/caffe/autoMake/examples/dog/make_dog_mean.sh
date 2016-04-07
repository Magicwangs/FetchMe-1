#!/usr/bin/env sh

EXAMPLE=examples/dog
DATA=data/dog
TOOLS=build/tools

$TOOLS/compute_image_mean $EXAMPLE/dog_train_lmdb \
  $DATA/dog_mean.binaryproto

echo "Done."
