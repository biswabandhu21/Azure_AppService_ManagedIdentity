package org.gandhi.azure.controller;

import lombok.extern.slf4j.Slf4j;
import org.gandhi.azure.service.DatalakeAccessUsingManagedIdentity;
import org.gandhi.azure.service.EventHubAccessUsingManagedIdentity;
import org.gandhi.azure.service.KeyVaultAccessUsingManagedIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
@RequestMapping("/connect")
public class AppServiceManagedIdentityController {

    @Autowired
    DatalakeAccessUsingManagedIdentity datalakeAccessUsingManagedIdentity;

    @Autowired
    EventHubAccessUsingManagedIdentity eventHubAccessUsingManagedIdentity;

    @Autowired
    KeyVaultAccessUsingManagedIdentity keyVaultAccessUsingManagedIdentity;

    @GetMapping(value = "/keyvault")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody  String keyVaultConnector() throws Exception {
        return keyVaultAccessUsingManagedIdentity.getSecret("<secret_name");
    }

    @GetMapping(value = "/eventhub")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody  String eventHubConnector() {
        eventHubAccessUsingManagedIdentity.sendEvent("<event_message>");
        return "Event sent successfully";
    }

    @GetMapping(value = "/datalake")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody  String dataLakeConnector() {
        datalakeAccessUsingManagedIdentity.uploadFileToBlobStorage("test_bytes".getBytes());
        return "Data stored successfully";
    }
}
