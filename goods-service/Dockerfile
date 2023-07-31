FROM ubuntu:20.04

RUN apt-get update;
RUN apt-get install -y openjdk-17-jdk;
RUN apt-get install -y git
RUN apt-get install -y openssh-server
RUN apt-get clean;

RUN mkdir /root/workspace
RUN echo 'root:passwd' | chpasswd
RUN echo "PermitRootLogin yes" >> /etc/ssh/sshd_config
RUN echo "PasswordAuthentication yes" >> /etc/ssh/sshd_config
RUN echo "/etc/init.d/ssh start" >> /root/.bashrc
RUN echo "cd /root/workspace" >> /root/.bashrc

COPY ./build/libs/goods-service-0.0.1-SNAPSHOT.jar /root/workspace
CMD [“java”, “-jar”, ”goods-service-0.0.1-SNAPSHOT.jar”]

EXPOSE 8080 22