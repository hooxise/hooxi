package com.hooxi.data.model.dest.security;

public class TLSConfig {
    private String ceCert;
    private String publicKey;
    private String privateKey;
    private String password;

    public String getCeCert() {
        return ceCert;
    }

    public void setCeCert(String ceCert) {
        this.ceCert = ceCert;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
