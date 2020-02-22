package org.gandhi.azure.service;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@NoArgsConstructor

public class EventHubAccessUsingManagedIdentity{

    private EventHubProducerClient eventHubProducerClient;

    @PostConstruct
    public void init() {
        TokenCredential credential = new DefaultAzureCredentialBuilder().build();
        eventHubProducerClient = new EventHubClientBuilder()
                .credential(
                        "<event_hub_namespace_name>.servicebus.windows.net",
                        "<event_hub_name>",
                        credential)
                .buildProducerClient();
        log.info("EventHubProducerClient using managed identity created successfully - " + eventHubProducerClient);
    }

    public void sendEvent(String eventMessage) {
        final Gson gson = new GsonBuilder().create();
        eventHubProducerClient.createBatch().tryAdd(new EventData(gson.toJson(eventMessage)));
    }

}
