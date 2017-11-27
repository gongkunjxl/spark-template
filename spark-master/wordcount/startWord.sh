#the start WordCount.sh
rm WordCount.jar
rm *.class
javac PageRank.java
jar -cvf WordCount.jar ./WordCount*.class
hadoop jar WordCount.jar org.apache.hadoop.examples.WordCount input output 
