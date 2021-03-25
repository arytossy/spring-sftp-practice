FROM centos:centos8

RUN useradd sshuser
RUN echo 'sshuser:weserve123' | chpasswd

RUN dnf -y install \
    openssh-server

RUN sed -i 's/PermitRootLogin yes/PermitRootLogin no/' /etc/ssh/sshd_config
RUN sed -i 's/#PubkeyAuthentication yes/PubkeyAuthentication yes/' /etc/ssh/sshd_config

RUN mv /run/nologin /run/nologin.disabled

COPY ../_hostkeys/sftp001/* /etc/ssh/
RUN chmod 600 /etc/ssh/ssh_host_*_key
RUN chmod 644 /etc/ssh/ssh_host_*_key.pub

COPY ../_hostkeys/client001/ssh_host_rsa_key.pub /home/sshuser/.ssh/authorized_keys
RUN chown -R sshuser:sshuser /home/sshuser
RUN chmod 600 /home/sshuser/.ssh/authorized_keys
RUN chmod 700 /home/sshuser/.ssh

# RUN ssh-keygen -t rsa -N "" -f /etc/ssh/ssh_host_rsa_key
# RUN ssh-keygen -t ecdsa -N "" -f /etc/ssh/ssh_host_ecdsa_key
# RUN ssh-keygen -t ed25519 -N "" -f /etc/ssh/ssh_host_ed25519_key

CMD ["/usr/sbin/sshd", "-D"]

# CMD ["tail", "-f", "/dev/null"]