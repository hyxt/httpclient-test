package com.hyxt.http;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by serv on 2014/8/22.
 */
public class SSLHttpClientBuilder {


    /** 允许的协议 */
    private String[] supportedProtocols = new String[] { "TLSv1" };

    /** 允许的密码套件 */
    private String[] supportedCipherSuites = null;

    /** 加密证书 */
    private InputStream keyStoreInputStream;

    /** 加密秘钥 */
    private String keyStorePassword ;

    /** 加密类型 */
    private String keyStoreType = KeyStore.getDefaultType();

    /** 信任密码 */
    private String trustStorePassword;
    /** 信任证书 */
    private InputStream trustStoreInputStream;
    /** 信任证书类型 */
    private String trustStoreType;


    //Hostname校验, 例如: firefox 和 ie的区别 默认是 跟firefox的一致
    private X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;

    public static SSLHttpClientBuilder create(){
        return new SSLHttpClientBuilder();
    }

    private SSLHttpClientBuilder() {

    }


    /**
     * 设置加密证书
     * @param keyStoreBytes
     * @return
     */
    public final SSLHttpClientBuilder setKeyStore( final byte[] keyStoreBytes){
        this.keyStoreInputStream = new ByteArrayInputStream(keyStoreBytes);
        return this;
    }

    /**
     * 设置加密证书
     * @param keyStoreInputStream
     * @return
     */
    public final SSLHttpClientBuilder setKeyStore( InputStream keyStoreInputStream){
        this.keyStoreInputStream = keyStoreInputStream;
        return this;
    }

    /**
     * 设置信任证书
     * @param trustStoreInputStream
     * @return
     */
    public final SSLHttpClientBuilder setTrustStore( InputStream trustStoreInputStream){
        this.trustStoreInputStream = trustStoreInputStream;
        return this;
    }

    /**
     * 设置允许的协议
     * @param supportedProtocols
     * @return
     */
    public final SSLHttpClientBuilder setSupportedProtocols(final String[] supportedProtocols){
        this.supportedProtocols = supportedProtocols;
        return this;
    }

    /**
     * 设置允许的密码套件
     * @param supportedCipherSuites
     * @return
     */
    public final SSLHttpClientBuilder setSupportedCipherSuites(final String[] supportedCipherSuites){
        this.supportedCipherSuites = supportedCipherSuites;
        return this;
    }

    /**
     * 设置hostname校验器
     * @param hostnameVerifier
     * @return
     */
    public final SSLHttpClientBuilder setHostnameVerifier( final X509HostnameVerifier hostnameVerifier){
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    /**
     * 设置加密秘钥
     * @param keyStorePassword
     * @return
     */
    public final SSLHttpClientBuilder setKeyStorePassword(final String keyStorePassword){
        this.keyStorePassword = keyStorePassword;
        return this;
    }


    /**
     * 设置加密证书类型
     * @param keyStoreType
     */
    public final SSLHttpClientBuilder setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
        return this;
    }

    /**
     * 设置信任秘钥
     * @param trustStorePassword
     * @return
     */
    public final SSLHttpClientBuilder setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    /**
     * 设置信任证书类型
     * @param trustStoreType
     */
    public final SSLHttpClientBuilder setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
        return this;
    }

    /**
     * 设置信任证书
     * @param trustStoreBytes
     * @return
     */
    public final SSLHttpClientBuilder setTrustStore( final byte[] trustStoreBytes){
        this.trustStoreInputStream = new ByteArrayInputStream(trustStoreBytes);
        return this;
    }

    /**
     * 生成restTemplate对象
     * @return
     */
    public final HttpClient build(){
        try {
            KeyStore keyStore = null;
            if(keyStoreInputStream!=null){
                keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(keyStoreInputStream, keyStorePassword==null?null:keyStorePassword.toCharArray());
            }

            KeyStore trustStore = null;
            if(trustStoreInputStream!=null){
                trustStore = KeyStore.getInstance(trustStoreType);
                trustStore.load(trustStoreInputStream, trustStorePassword==null?null:trustStorePassword.toCharArray());
            }

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore,keyStorePassword==null?null:keyStorePassword.toCharArray())
                    .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    supportedProtocols,
                    supportedCipherSuites,
                    hostnameVerifier);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
