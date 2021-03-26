package com.arytossy.sftp_practice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileTransferServiceImpl implements FileTransferService {
    
    private Logger logger = LoggerFactory.getLogger(FileTransferServiceImpl.class);

    @Value("${sftp.host}")
    private String host;
    @Value("${sftp.port}")
    private int port;
    @Value("${sftp.username}")
    private String username;
    @Value("${sftp.path.secret-key}")
    private String secretKeyPath;
    @Value("${sftp.path.known-hosts}")
    private String knownHostsPath;

    @Override
    public boolean upload(String localPath, String remotePath) {
        ChannelSftp channelSftp = createChannelSftp();
        try {
            channelSftp.put(localPath, remotePath);
            return true;
        } catch(SftpException ex) {
            logger.error("Error upload file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return false;
    }

    private ChannelSftp createChannelSftp() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(secretKeyPath);
            jsch.setKnownHosts(knownHostsPath);

            Session session = jsch.getSession(username, host, port);
            session.connect();
            
            Channel channel = session.openChannel("sftp");
            channel.connect();
            return (ChannelSftp) channel;
        } catch (JSchException ex) {
            logger.error("Create ChannelSftp error", ex);
        }

        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channel) {
        try {
            if (channel == null)
                return;
            if (channel.isConnected())
                channel.disconnect();
            if (channel.getSession() != null)
                channel.getSession().disconnect();
        } catch (Exception ex) {
            logger.error("SFTP disconnect error", ex);
        }
    }

}
