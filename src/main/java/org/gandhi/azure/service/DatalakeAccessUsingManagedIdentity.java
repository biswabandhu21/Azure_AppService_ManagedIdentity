package org.gandhi.azure.service;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.file.datalake.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@NoArgsConstructor
public class DatalakeAccessUsingManagedIdentity {

    DataLakeServiceClient serviceClient;

    @PostConstruct
    public void init() {
        DataLakeServiceClientBuilder builder = new DataLakeServiceClientBuilder();
        serviceClient = builder.credential(new DefaultAzureCredentialBuilder().build())
                .endpoint("https://<storage_account_name>.dfs.core.windows.net")
                .buildClient();

        log.info("Blob client using managed identity created successfully - " + serviceClient);

    }

    public void uploadFileToBlobStorage(byte[] uploadedFileBytes) {

        DataLakeFileSystemClient dataLakeFileSystemClient = serviceClient.getFileSystemClient("<container_name>");
        DataLakeDirectoryClient dataLakeDirectoryClient = dataLakeFileSystemClient.createDirectory("<directory_name>");
        InputStream inputStream = new ByteArrayInputStream(uploadedFileBytes);
        DataLakeFileClient dataLakeFileClient = dataLakeDirectoryClient.createFile("<file_name>");
        dataLakeFileClient.append(inputStream,0,uploadedFileBytes.length);
        dataLakeFileClient.flush(uploadedFileBytes.length);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("Error closing input stream");
        }
    }
}
