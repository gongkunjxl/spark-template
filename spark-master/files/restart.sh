#!/bin/bash
pgrep java || exit 0;                                                                                                                                                               
echo "Stopping Hadoop Cluster"                                                                                                                                                      
$HADOOP_INSTALL/sbin/stop-dfs.sh                                                                                                                                                    
echo -e "\n"                                                                                                                                                                        
$HADOOP_INSTALL/sbin/stop-yarn.sh 
echo "Stopping Spark Cluster"
$SPARK_HOME/sbin/stop-all.sh
                                                                                                                                                  
echo "Starting Hadoop Cluster"                                                                                                                                                      
$HADOOP_INSTALL/sbin/start-dfs.sh                                                                                                                                                   
echo -e "\n"                                                                                                                                                                        
$HADOOP_INSTALL/sbin/start-yarn.sh
echo "Starting Spark Cluster"
$SPARK_HOME/sbin/start-all.sh

PATH=$PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin
