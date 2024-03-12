package com.thepaut.backend.containers.keycloaktestcontainer;

import com.thepaut.backend.utils.TestConstants;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
public class KeycloakTestContainer extends KeycloakContainer {

    static KeycloakTestContainer keycloakContainer;

    public static final String DOCKER_IMAGE_NAME_KEYCLOAK = "quay.io/keycloak/keycloak:20.0.1";

    private KeycloakTestContainer() {
        super(DOCKER_IMAGE_NAME_KEYCLOAK);
    }

    public static KeycloakTestContainer getInstance() {
        if (keycloakContainer == null) {
            keycloakContainer = new KeycloakTestContainer();
        }
        keycloakContainer.withRealmImportFile(TestConstants.KEYCLOAK_MYREALM_EXPORT_JSON)
                .withAdminUsername(TestConstants.KEYCLOAK_ADMIN_USERNAME)
                .withAdminPassword(TestConstants.KEYCLOAK_ADMIN_USERNAME_PASSWORD);
        return keycloakContainer;
    }

    public String generateAccessToken(String username, String password) throws IOException {
        // Configure Apache HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Prepare token request
            HttpPost postRequest = new HttpPost(keycloakContainer.getAuthServerUrl() +
                    "/realms/" + TestConstants.KEYCLOAK_REALM + "/protocol/openid-connect/token");

            List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("grant_type", "password"));
            form.add(new BasicNameValuePair("client_id", TestConstants.KEYCLOAK_CLIENT_ID));
            form.add(new BasicNameValuePair("client_secret", TestConstants.KEYCLOAK_CLIENT_SECRET));
            form.add(new BasicNameValuePair("username", username));
            form.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity bodyEntity = new UrlEncodedFormEntity(form, Consts.UTF_8);

            // Set request body
            postRequest.setEntity(bodyEntity);
            log.info("generateAccessToken request : {}", postRequest);
            log.info("generateAccessToken request body : {}", bodyEntity);

            // Execute token request
            HttpResponse response = httpClient.execute(postRequest);

            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            log.info("generateAccessToken response : {}", responseBody);

            // Verify the response status code is 200 OK
            assertEquals(200, response.getStatusLine().getStatusCode());

            // Parse the response and extract the access token
            return responseBody.split("\"access_token\":\"")[1].split("\"")[0];
        }
    }
}