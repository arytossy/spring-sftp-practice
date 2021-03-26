FROM centos:centos8

RUN dnf -y install \
    git \
    java-1.8.0-openjdk-devel \
    java-11-openjdk-devel \
    maven

COPY ../_hostkeys/client001/ssh_host_rsa_key* /etc/ssh/
RUN chmod 600 /etc/ssh/ssh_host_rsa_key
RUN chmod 644 /etc/ssh/ssh_host_rsa_key.pub

RUN mkdir /tmp/will_be_upload/