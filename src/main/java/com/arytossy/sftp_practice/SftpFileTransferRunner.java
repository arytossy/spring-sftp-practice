package com.arytossy.sftp_practice;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SftpFileTransferRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(SftpFileTransferRunner.class);

    @Autowired
    private FileTransferService fileTransferService;

    @Value("${sftp.path.local-dir}")
    private String localDirPath;
    @Value("${sftp.path.remote-dir}")
    private String remoteDirPath;

    @Override
    public void run(String... args) throws Exception {

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String localFilename = "testfile_" + timestamp;
        String remoteFilename = "testfile_uploaded_" + timestamp;

        File testfile = new File(localDirPath, localFilename);
        try {
            if (testfile.createNewFile()) {
                logger.info("New file has created: " + localFilename);
            } else {
                logger.warn("The file already exists: " + localFilename);
            }
        } catch (IOException ex) {
            logger.error("I/O error occurered.", ex);
        }
        
        logger.info("Start upload file");
        boolean isUploaded = fileTransferService.upload(localDirPath + localFilename, remoteDirPath + remoteFilename);
        logger.info("Upload result: " + String.valueOf(isUploaded));
        
    }
    
}
