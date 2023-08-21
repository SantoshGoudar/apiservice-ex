package com.example.crd;

import com.example.crd.pojos.ApiService;
import com.example.crd.pojos.ApiServiceList;
import io.kubernetes.client.extended.controller.Controller;
import io.kubernetes.client.extended.controller.ControllerManager;
import io.kubernetes.client.extended.controller.builder.ControllerBuilder;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.informer.SharedInformerFactory;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.generic.GenericKubernetesApi;

import java.io.IOException;
import java.time.Duration;

public class Application {
    public static void main(String[] args) throws IOException {
        ApiClient apiClient = ClientBuilder.cluster().build();
        SharedInformerFactory sharedInformerFactory = new SharedInformerFactory(apiClient);
        GenericKubernetesApi api = new GenericKubernetesApi(
                ApiService.class, ApiServiceList.class, "kong.definition", "v1", "apiservices", apiClient);
        SharedIndexInformer sharedIndexInformer = sharedInformerFactory.sharedIndexInformerFor(api, ApiService.class, 0);
        Controller apiServiceController = ControllerBuilder.defaultBuilder(sharedInformerFactory)
                .withReconciler(new ApiServiceReconciler(sharedIndexInformer, api)) // required, set the actual reconciler
                .withName("Api service controller") // optional, set name for controller
                .withWorkerCount(10) // optional, set worker thread count
                .withReadyFunc(sharedIndexInformer::hasSynced) // optional, only starts controller when the cache has synced up
                .withReadyTimeout(Duration.ofSeconds(1200))// optional, if controller cannot sync before this time out application will exit
                .watch(workQueue ->
                        ControllerBuilder.controllerWatchBuilder(ApiService.class, workQueue)
                                .build())
                .build();
        ControllerManager manager = ControllerBuilder.controllerManagerBuilder(sharedInformerFactory).addController(apiServiceController).build();
        manager.run();
    }
}
