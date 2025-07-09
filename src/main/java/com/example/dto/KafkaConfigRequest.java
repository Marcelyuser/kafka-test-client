package com.example.dto;

public class KafkaConfigRequest {

    private String bootstrapServers;
    private String securityEnabled; // "true" or "false" 문자열
    private String saslMechanism;
    private String saslJaasConfig;
    private String sslTrustStoreLocation;
    private String sslTrustStorePassword;

    // === Getters & Setters ===
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getSecurityEnabled() {
        return securityEnabled;
    }

    public void setSecurityEnabled(String securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }

    public String getSaslJaasConfig() {
        return saslJaasConfig;
    }

    public void setSaslJaasConfig(String saslJaasConfig) {
        this.saslJaasConfig = saslJaasConfig;
    }

    public String getSslTrustStoreLocation() {
        return sslTrustStoreLocation;
    }

    public void setSslTrustStoreLocation(String sslTrustStoreLocation) {
        this.sslTrustStoreLocation = sslTrustStoreLocation;
    }

    public String getSslTrustStorePassword() {
        return sslTrustStorePassword;
    }

    public void setSslTrustStorePassword(String sslTrustStorePassword) {
        this.sslTrustStorePassword = sslTrustStorePassword;
    }
}
