#the start pagerank.sh
rm PageRank.jar
rm *.class
javac PageRank.java
jar -cvf PageRank.jar ./PageRank*.class
hadoop jar PageRank.jar PageRank 
