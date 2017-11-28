FROM kiwenlau/serf-dnsmasq:0.1.0

MAINTAINER gongkun <gongkunjxl@163.com>

RUN apt-get update && apt-get install -y openssh-server wget vim

#configure ssh free key access
COPY files/* /tmp/

RUN mkdir /var/run/sshd && \
ssh-keygen -t rsa -f ~/.ssh/id_rsa -P '' && \
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys && \
mv /tmp/ssh_config ~/.ssh/config && \
sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

