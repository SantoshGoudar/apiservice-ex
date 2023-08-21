package com.example.crd;

import com.example.crd.pojos.ApiService;
import io.kubernetes.client.extended.controller.reconciler.Reconciler;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.util.generic.GenericKubernetesApi;

public class ApiServiceReconciler implements Reconciler {

    SharedIndexInformer<ApiService> sharedInformer;
    GenericKubernetesApi api;

    public ApiServiceReconciler(SharedIndexInformer<ApiService> sharedInformer, GenericKubernetesApi api) {
        this.sharedInformer = sharedInformer;
        this.api = api;
    }

    @Override
    public Result reconcile(Request request) {
        String key = request.getNamespace() + "/" + request.getName();
        ApiService resourceInstance = sharedInformer.getIndexer().getByKey(key);
        if (resourceInstance != null) {
            System.out.println("Got resource... " + key);
            //Call the KONG REST API to register the route
        } else {
            System.out.println("Got resource... for Delete    KEY " + key);
        }
        return new Result(false);
    }


}
