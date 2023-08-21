package com.example.crd.pojos;

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class ApiService implements KubernetesObject {
    private String apiVersion;
    private String kind;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private V1ObjectMeta metadata;
    private String name;
    private String host;
    private int port;
    private String protocol;
    private Route routes;
    private Status status;

}
