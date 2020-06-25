#!/bin/bash

#outPath=../../../java
outPath= ../../hero/heroland-competition-parent/common/src/main/java
fileArray=(BaseRequestProto BaseResponseProto)

for i in ${fileArray[@]};
do
    echo "generate cli protocol java code: ${i}.proto"
    protoc --java_out=$outPath ./$i.proto
done
