package com.thepaut.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.thepaut.backend.dto.GenericDto;
import com.thepaut.backend.dto.audit.GenericAuditDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTestUtils<A extends GenericAuditDto,T extends GenericDto<A>, C> {

    public static<T,C> T postRequest(String url, C creationDto, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Authorization", "Bearer " + accessToken);


            // Convertir l'objet SampleDataCategory en JSON
            String requestBody = objectMapper.writeValueAsString(creationDto);

            // Set request body
            postRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Execute token request
            HttpResponse response = httpClient.execute(postRequest);

            // Verify the response status code is 200 OK
            assertEquals(201, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            return objectMapper.readValue(responseBody, responseType);
        }
    }

    public static<T> T updateRequest(String url, T genericDto, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Convertir l'objet SampleDataCategory en JSON
            String requestBody = objectMapper.writeValueAsString(genericDto);

            // Set request body
            putRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Execute token request
            HttpResponse response = httpClient.execute(putRequest);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            return objectMapper.readValue(responseBody, responseType);
        }
    }

    public static<T> T getRequestById(String url, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet getRequest = new HttpGet(url);
            getRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Execute token request
            HttpResponse response = httpClient.execute(getRequest);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            return objectMapper.readValue(responseBody, responseType);
        }
    }

    public static<T> List<T> getRequestAll(String baseUrl, Map<String,String> params, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            StringBuilder url = new StringBuilder(baseUrl);

            if (params  != null && !params.isEmpty()) {
                url.append("?");
                boolean firstParam = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (firstParam) {
                        firstParam = false;
                        url.append(entry.getKey()).append("=").append(entry.getValue());
                    } else {
                        url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                    }
                }
            }

            HttpGet getRequest = new HttpGet(url.toString());
            getRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Execute token request
            HttpResponse response = httpClient.execute(getRequest);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, responseType);
            return objectMapper.readValue(responseBody, javaType);
        }
    }

    public static<T> void deleteRequest(String url, String accessToken) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete deleteRequest = new HttpDelete(url);
            deleteRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Execute token request
            HttpResponse response = httpClient.execute(deleteRequest);

            // Verify the response status code is 200 OK
            assertEquals(204, response.getStatusLine().getStatusCode());
        }
    }


    public static<T> T rollbackToPreviousVersionRequest(String url, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Execute token request
            HttpResponse response = httpClient.execute(putRequest);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            return objectMapper.readValue(responseBody, responseType);
        }
    }


    public static<T> T rollbackToSpecificVersionRequest(String url, String accessToken, ObjectMapper objectMapper, Class<T> responseType) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut putRequest = new HttpPut(url);
            putRequest.addHeader("Authorization", "Bearer " + accessToken);

            // Execute token request
            HttpResponse response = httpClient.execute(putRequest);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Extraire le contenu de l'entité de la réponse
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Désérialiser le contenu JSON en un objet SampleDataCategory
            return objectMapper.readValue(responseBody, responseType);
        }
    }
}
