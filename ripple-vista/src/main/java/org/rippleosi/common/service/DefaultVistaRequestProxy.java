/*
 *   Copyright 2015 Ripple OSI
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package org.rippleosi.common.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DefaultVistaRequestProxy implements VistaRequestProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractVistaService.class);
    
    @Override
    public <T> ResponseEntity<T> getWithoutSession(String uri, Class<T> cls) {
        HttpEntity<String> request = buildRequest(null);

        return restTemplate().exchange(uri, HttpMethod.GET, request, cls);
    }

    private RestTemplate restTemplate() {
        return new RestTemplate(new BypassSSLHttpRequestFactory());
    }

    private HttpEntity<String> buildRequest(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return new HttpEntity<>(body, headers);
    }

    private class BypassSSLHttpRequestFactory extends SimpleClientHttpRequestFactory {

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection con = (HttpsURLConnection) connection;

                con.setHostnameVerifier((hostname, session) -> true);
                con.setSSLSocketFactory(trustSelfSignedSSL().getSocketFactory());
//                con.setAllowUserInteraction(true);
            }

            super.prepareConnection(connection, httpMethod);
        }

        public SSLContext trustSelfSignedSSL() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {

                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };

                ctx.init(null, new TrustManager[] { tm }, null);
                SSLContext.setDefault(ctx);
                return ctx;
            } catch (Exception ex) {
                LOGGER.debug("Problem setting up self signed certificates", ex);
            }
            return null;
        }
    }
}
