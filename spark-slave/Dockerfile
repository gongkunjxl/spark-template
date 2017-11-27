FROM gongkunjxl/serf-dnsmasq:0.1.0

MAINTAINER gongkun <gongkunjxl@163.com>

WORKDIR /root

# install openssh-server, openjdk and wget
RUN apt-get update && apt-get install -y openjdk-7-jdk wget vim unzip curl

# install hadoop 2.7.2
#ADD hadoop-2.7.2.tar.gz /root/
RUN wget -c https://github.com/kiwenlau/compile-hadoop/releases/download/2.7.2/hadoop-2.7.2.tar.gz && \
    tar -xzvf hadoop-2.7.2.tar.gz && \
    mv hadoop-2.7.2 /usr/local/hadoop && \
    rm hadoop-2.7.2.tar.gz
#spark
RUN wget http://d3kbcqa49mib13.cloudfront.net/spark-2.1.0-bin-hadoop2.7.tgz && \
    tar -xzvf spark-2.1.0-bin-hadoop2.7.tgz && \
    mv spark-2.1.0-bin-hadoop2.7 /usr/local/spark && \
    rm spark-2.1.0-bin-hadoop2.7.tgz
#scala
RUN wget https://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz && \
    tar -xzvf scala-2.11.8.tgz && \
    mv scala-2.11.8 /usr/local/scala && \
    rm scala-2.11.8.tgz

# set environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
ENV HADOOP_HOME=/usr/local/hadoop
ENV SCALA_HOME=/usr/local/scala
ENV SPARK_HOME=/usr/local/spark
ENV PATH=$PATH:/usr/local/hadoop/bin:/usr/local/hadoop/sbin:$SCALA_HOME/bin:$SPARK_HOME/bin:$SPARK_HOME/sbin

#add files
RUN mkdir -p ~/hdfs/namenode && \ 
    mkdir -p ~/hdfs/datanode && \
    mkdir $HADOOP_HOME/logs

COPY files/* /tmp/

RUN mv /tmp/bashrc ~/.bashrc && \
    mv /tmp/hadoop-env.sh /usr/local/hadoop/etc/hadoop/hadoop-env.sh && \
    mv /tmp/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml && \ 
    mv /tmp/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml && \
    mv /tmp/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml && \
    mv /tmp/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml && \
    mv /tmp/spark-env.sh $SPARK_HOME/conf/spark-env.sh && \
    mv /tmp/start-ssh-serf.sh ~/start-ssh-serf.sh

RUN chmod -R 777 ~/start-ssh-serf.sh && \
    chmod -R 777 $HADOOP_HOME/sbin/start-dfs.sh && \
    chmod -R 777 $HADOOP_HOME/sbin/start-yarn.sh 


RUN chmod 644 ~/.ssh/config
#CMD [ "sh", "-c", "service ssh start; bash"]

EXPOSE 22 8080 8081 7373 7946 9000 50010 50020 50070 50075 50090 50475 8030 8031 8032 8033 8040 8042 8060 8088 50060

CMD '/root/start-ssh-serf.sh'; 'bash'
















