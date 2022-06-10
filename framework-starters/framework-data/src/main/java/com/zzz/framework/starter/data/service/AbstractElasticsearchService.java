package com.zzz.framework.starter.data.service;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author zhouzhanqi
 * @date 2022/3/4 2:17 PM
 * @desc es服务
 */
public abstract class AbstractElasticsearchService {

    private final ElasticsearchRestTemplate restTemplate;

    protected AbstractElasticsearchService(ElasticsearchRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



}
