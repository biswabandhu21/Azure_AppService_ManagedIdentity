### Azure_AppService_ManagedIdentity

#### Sample project that uses Azure AppService System Managed Identity to connect to various Azure resources like KeyVault, Event Hub and Azure Datalake Storage from Azure AppService. This is a Spring Boot project where the connectivity to Azure resources are exposed as REST endpoints.

###### Following configuration needs to be done in Azure portal before trying to execute this code:

1) Enable System Managed Identity in Azure AppService.
2) Add AppService in IAM and access policies of respective Azure resources with appropriate roles.

Refer the following link for detailed info - https://docs.microsoft.com/en-us/azure/key-vault/managed-identity
