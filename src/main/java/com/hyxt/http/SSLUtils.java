package com.hyxt.http;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * Created by serv on 2014/8/25.
 */
public class SSLUtils {

    public static void post(String url,String jsonPath) throws IOException {
        HttpClient template = SSLHttpClientBuilder.create()
                .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .setKeyStoreType("PKCS12")
                .setKeyStore(new FileInputStream("c:/client.p12"))
                .setKeyStorePassword("cx20091031")
                .setTrustStore(new FileInputStream("c:/ca.p12"))
                .setTrustStorePassword("cx20091031")
                .setTrustStoreType("PKCS12")
                .build();


//        String url = "https://192.168.1.111:8443/unifiedorder/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=";


        HttpPost urlRequest = new HttpPost(url);
        urlRequest.setHeader("Content-Type", "application/json");
        urlRequest.setHeader("Accept", "application/json");

        String json = FileUtils.readFileToString(new ClassPathResource(jsonPath).getFile());
        ByteArrayEntity se = new ByteArrayEntity(json.getBytes());
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        urlRequest.setEntity(se);

        HttpResponse execute = template.execute(urlRequest);




        System.out.println(execute.getEntity().toString());

        // 显示结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(execute.getEntity().getContent(), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
