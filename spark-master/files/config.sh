#!/bin/bash
echo master.gongkun.com > /usr/local/hadoop/etc/hadoop/slaves;
echo master.gongkun.com > /usr/local/spark/conf/slaves;
for i in  $(seq $(serf members|grep slave|grep alive |wc -l)) ; do echo slave$i.gongkun.com >> /usr/local/hadoop/etc/hadoop/slaves ; done;
for i in  $(seq $(serf members|grep slave|grep alive |wc -l)) ; do echo slave$i.gongkun.com >> /usr/local/spark/conf/slaves ; done;
cat /etc/hosts|grep -v spark|grep -v master > /tmp/hosts
cat /tmp/hosts > /etc/hosts
serf members |grep alive|awk -F :7946 '{print $1}'|awk '{print $2 "\t" $1}'>> /etc/hosts;
serf members |grep master|awk -F :  '{print $1}'|awk '{print $2"\tmaster.gongkun.com"}' >> /etc/hosts;
cc=0;cat /etc/hosts|grep spark-slave|while read line ; do ((cc++)); echo $line slave$cc.gongkun.com >> /etc/hosts; done;
cat /usr/local/hadoop/etc/hadoop/slaves|grep slave|while read line ; do  scp -o StrictHostKeyChecking=no /etc/hosts $line:/etc/hosts;  done;


