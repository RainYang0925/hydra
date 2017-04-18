#!/bin/bash
if [ $# -lt 1 ]
then
	echo "usage:runTemplate.sh [template_file] [data_file] [output_file]"
	exit 1
fi
# cd ..
currpath=`pwd`

basepath=$(cd `dirname $0`; pwd)

cd $basepath

echo "------------------ copy maven libs--------------"
# mvn clean install package -Dmaven.test.skip=true
# rm -rf lib/
# mkdir -p lib/target/
# cp -f target/*.jar lib/target/
# mvn dependency:copy-dependencies -DoutputDirectory=lib/ -Dmdep.useRepositoryLayout=true
echo "------------------ copy maven libs finish--------------"

rm -rf logs/*.log*

# export CPATH=`find target -type d | awk 'BEGIN {libpath=""} {libpath= libpath$0"/*:"} END {print libpath}'`
export CPATH=`find $basepath/target -name "*.jar" | awk 'BEGIN {libpath=""} {libpath=libpath$0":"} END {print libpath} '`
CPATH="$CPATH$basepath/conf:$basepath/js:$basepath/hydra.jar"
echo $CPATH
#java -classpath $CPATH com.xiaohongshu.automation.Application $1


function runJava(){
    java -classpath $CPATH com.xiaohongshu.automation.utils.TemplateTool $1 $2 $3
    cd $currpath
    return $?
}

runJava $1 $2 $3




#java -classpath hydra.jar com.xiaohongshu.automation.utils.TemplateTool $1 $2 $3