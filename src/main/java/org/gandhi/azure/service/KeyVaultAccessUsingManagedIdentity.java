package org.gandhi.azure.service;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.AppServiceMSICredentials;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretBundle;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@Slf4j
@NoArgsConstructor
public class KeyVaultAccessUsingManagedIdentity {

    private static final String MSI_ENDPOINT_ENV_VARIABLE = "MSI_ENDPOINT";
    private static final String MSI_SECRET_ENV_VARIABLE = "MSI_SECRET";
    private KeyVaultClient keyVaultClient;

    @PostConstruct
    public void init() {
        String msiEndpoint =  System.getenv(MSI_ENDPOINT_ENV_VARIABLE);
        String msiSecret =  System.getenv(MSI_SECRET_ENV_VARIABLE);
        AppServiceMSICredentials msiCredentials = new AppServiceMSICredentials(AzureEnvironment.AZURE,msiEndpoint, msiSecret);
        keyVaultClient = new KeyVaultClient(msiCredentials);
        log.info("KeyVault client using managed identity created successfully - " + keyVaultClient);
    }

    public String getSecret(String token) throws Exception{

            SecretBundle secretBundle = keyVaultClient.getSecret("https://<keyvault_name>.vault.azure.net/", token);
            if (Objects.isNull(secretBundle)) {
                throw new Exception("Invalid token.");
            }
            return secretBundle.value();

    }
}
