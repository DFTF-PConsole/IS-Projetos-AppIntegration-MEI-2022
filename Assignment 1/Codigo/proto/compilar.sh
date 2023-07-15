#!/bin/bash

SRC_DIR=.
DST_DIR=../src/main/java

./protoc -I=$SRC_DIR --java_out=$DST_DIR App.proto
