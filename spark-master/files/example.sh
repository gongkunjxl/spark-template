$SPARK_HOME/bin/spark-submit --master spark://master.gongkun.com:7077 --class org.apache.spark.examples.SparkPi --executor-memory 512m $SPARK_HOME/examples/jars/spark-examples_2.11-2.1.0.jar
