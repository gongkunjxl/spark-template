echo -e "\n"
$HADOOP_HOME/sbin/start-dfs.sh

echo -e "\n"
$HADOOP_HOME/sbin/start-yarn.sh

echo -e "\n"
$SPARK_HOME/sbin/start-all.sh

echo -e "\n"
PATH=$PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin
