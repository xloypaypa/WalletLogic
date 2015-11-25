package model.config;

import model.tool.RSA;
import org.dom4j.Element;

import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * Created by xlo on 2015/11/25.
 * it's the encryption config
 */
public class EncryptionConfig implements ConfigInterface {
    protected KeyPair keyPair;

    public static EncryptionConfig getConfig() {
        return (EncryptionConfig) ConfigManager.getConfigManager().getConfig(EncryptionConfig.class);
    }

    @Override
    public void init() throws Exception {
        Element root = ConfigInterface.getRootElement(ConfigManager.configPathConfig.getConfigFilePath(this.getClass()));
        RSA.keySize = Integer.valueOf(root.getText());
        this.keyPair = RSA.buildKeyPair();
    }

    @Override
    public void reload() throws Exception {

    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}
