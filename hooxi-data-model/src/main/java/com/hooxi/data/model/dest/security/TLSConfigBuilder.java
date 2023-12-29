package com.hooxi.data.model.dest.security;

public final class TLSConfigBuilder {
  private String ceCert;
  private String publicKey;
  private String privateKey;
  private String password;

  private TLSConfigBuilder() {}

  public static TLSConfigBuilder aTLSConfig() {
    return new TLSConfigBuilder();
  }

  public TLSConfigBuilder withCeCert(String ceCert) {
    this.ceCert = ceCert;
    return this;
  }

  public TLSConfigBuilder withPublicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  public TLSConfigBuilder withPrivateKey(String privateKey) {
    this.privateKey = privateKey;
    return this;
  }

  public TLSConfigBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public TLSConfig build() {
    TLSConfig tLSConfig = new TLSConfig();
    tLSConfig.setCeCert(ceCert);
    tLSConfig.setPublicKey(publicKey);
    tLSConfig.setPrivateKey(privateKey);
    tLSConfig.setPassword(password);
    return tLSConfig;
  }
}
