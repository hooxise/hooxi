package com.hooxi.data.model.dest.security;

import io.swagger.v3.oas.annotations.media.Schema;

public class TLSConfig {

  @Schema(description = "CA certificate which issued the certificate to webhook URL. Mainly used for mutual authentication. But can be used in general.")
  private String caCert;
  @Schema(description = "public key used while invoking webhook")
  private String publicKey;
  @Schema(description = "private key used while invoking webhook")
  private String privateKey;
  @Schema(description = "private key password if private key is encrypted")
  private String password;

  public String getCaCert() {
    return caCert;
  }

  public void setCaCert(String caCert) {
    this.caCert = caCert;
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

  @Override
  public String toString() {
    return "TLSConfig{" +
            "ceCert='" + caCert + '\'' +
            ", publicKey='" + publicKey + '\'' +
            ", privateKey='" + privateKey + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
}
