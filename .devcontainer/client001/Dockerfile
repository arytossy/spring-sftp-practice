FROM centos:centos8

RUN dnf -y install \
    git \
    java-1.8.0-openjdk-devel \
    java-11-openjdk-devel \
    maven

COPY ../_hostkeys/client001/* /etc/ssh/
RUN chmod 600 /etc/ssh/ssh_host_*_key
RUN chmod 644 /etc/ssh/ssh_host_*_key.pub