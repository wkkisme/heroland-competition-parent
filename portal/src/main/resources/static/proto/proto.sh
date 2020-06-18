#!/bin/bash

outPath=../../java
fileArray=(StockMessageProto)

for i in ${fileArray[@]};
do
    echo "generate cli protocol java code: ${i}.proto"
    protoc --java_out=$outPath ./$i.proto
done
