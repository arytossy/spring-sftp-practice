FROM centos:centos8

RUN dnf -y install \
    openssh-server

RUN mv /etc/ssh/sshd_config /etc/ssh/sshd_config.old
COPY sftp001/sshd_config etc/ssh/sshd_config
RUN chmod 600 /etc/ssh/sshd_config

RUN mv /run/nologin /run/nologin.disabled

COPY _hostkeys/sftp001/* /etc/ssh/
RUN chmod 600 /etc/ssh/ssh_host_rsa_key
RUN chmod 644 /etc/ssh/ssh_host_rsa_key.pub

RUN useradd sftpuser

RUN mkdir /home/sftpuser/upload/

COPY _hostkeys/client001/ssh_host_rsa_key.pub /home/sftpuser/.ssh/authorized_keys
RUN chown -R sftpuser:sftpuser /home/sftpuser
RUN chmod 600 /home/sftpuser/.ssh/authorized_keys
RUN chmod 700 /home/sftpuser/.ssh

CMD ["/usr/sbin/sshd", "-D"]

# CMD ["tail", "-f", "/dev/null"]