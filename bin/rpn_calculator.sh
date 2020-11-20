#!/usr/bin/env bash
# Complete bin/setup so that after it is
# run, bin/rpn_calculator.sh can be used to launch it.

# This variable contains absolute path of this script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
TARGETDIR=$(dirname $DIR)
JARPATH=$TARGETDIR"/target/RPNCalculator-jar-with-dependencies.jar"

if [ -z "$1" ]
then
    java -jar $JARPATH
else
    INPUT_FILE_DIR=$(readlink -f $1)
    java -jar $JARPATH $INPUT_FILE_DIR
fi