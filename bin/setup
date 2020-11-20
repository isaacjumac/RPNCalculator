#!/bin/bash

# * Install dependencies
# * Build/Compile
# * Run Test Suit to validate
#
# After this is run, bin/rpn-calculator should just work

echo 'Building project...'
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
POMDIR=$(dirname $DIR)

cd $POMDIR
mvn clean package

if [ $? == 0 ]; then
    echo 'rpn-calculator built successfully!'
else
    echo 'Something went wrong. Refer to detailed build log for details. :('
fi
