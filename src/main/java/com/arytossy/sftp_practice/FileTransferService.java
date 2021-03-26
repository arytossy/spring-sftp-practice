package com.arytossy.sftp_practice;

public interface FileTransferService {
    boolean upload(String localPath, String remotePath);
}
