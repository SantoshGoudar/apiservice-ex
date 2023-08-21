package com.example.crd.pojos;

import io.kubernetes.client.common.KubernetesListObject;
import io.kubernetes.client.openapi.models.V1ListMeta;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiServiceList implements KubernetesListObject {
    private String apiVersion;
    private List<ApiService> items = new ArrayList<>();
    private String kind;
    private V1ListMeta metadata;
}