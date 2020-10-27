package com.heroland.competition.config;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManagerFactory;


//@Component
public class Configuration {

    private String appId;

    @Value("${application.name}")
    private String name;

    @Value("${application.desc}")
    private String desc;

    @Value("${server.ip}")
    private String ip;

    @Value("${server.port}")
    private int port;

    @Value("${server.type}")
    private int appType;

    @Value("${server.max.load}")
    private int maxLoad;

    @Value("${server.hostname}")
    private String hostname;

    @Value("${server.boss.threads}")
    private int bossThreads;

    @Value("${server.worker.threads}")
    private int workerThreads;

    @Value("${server.business.threads}")
    private int businessThreads;

    @Value("${server.useLinuxNativeEpoll}")
    private boolean useLinuxNativeEpoll;

    @Value("${server.ssl.switch}")
    private boolean sslSwitch;

    @Value("${server.ssl.keystoreFile}")
    private String keystoreFile;

    @Value("${server.ssl.keystorePass}")
    private String keystorePass;

    @Value("${server.heartbeat.interval}")
    private int heartbeatInterval;

    @Value("${server.heartbeat.timeout.nums}")
    private int timeoutNums;

    @Value("${server.heartbeat.switch}")
    private boolean heartbeatOpened;

    private String address;

    private String keyStoreFormat = "JKS";

    private String sslProtocol = "TLSv1";

    private String keyManagerFactoryAlgorithm = KeyManagerFactory.getDefaultAlgorithm();

    @PostConstruct
    private void init() {
        this.address = (this.sslSwitch ? "wss://" : "ws://") + this.getHostname() + ":" + this.getPort();
//        this.appId = TokenUtils.MD5(address + "&" + this.appType);

    }

    /**
     * Get worker threads
     * 
     * @return worker threads
     */
    public int getWorkerThreads() {
        int minWorkerThreads = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);
        return this.workerThreads > minWorkerThreads ? this.workerThreads : minWorkerThreads;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public int getBusinessThreads() {
        return businessThreads;
    }

    public void setBusinessThreads(int businessThreads) {
        this.businessThreads = businessThreads;
    }

    public boolean isUseLinuxNativeEpoll() {
        return useLinuxNativeEpoll;
    }

    public void setUseLinuxNativeEpoll(boolean useLinuxNativeEpoll) {
        this.useLinuxNativeEpoll = useLinuxNativeEpoll;
    }

    public boolean isSslSwitch() {
        return sslSwitch;
    }

    public void setSslSwitch(boolean sslSwitch) {
        this.sslSwitch = sslSwitch;
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePass() {
        return keystorePass;
    }

    public void setKeystorePass(String keystorePass) {
        this.keystorePass = keystorePass;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getTimeoutNums() {
        return timeoutNums;
    }

    public void setTimeoutNums(int timeoutNums) {
        this.timeoutNums = timeoutNums;
    }

    public boolean isHeartbeatOpened() {
        return heartbeatOpened;
    }

    public void setHeartbeatOpened(boolean heartbeatOpened) {
        this.heartbeatOpened = heartbeatOpened;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKeyStoreFormat() {
        return keyStoreFormat;
    }

    public void setKeyStoreFormat(String keyStoreFormat) {
        this.keyStoreFormat = keyStoreFormat;
    }

    public String getSslProtocol() {
        return sslProtocol;
    }

    public void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public String getKeyManagerFactoryAlgorithm() {
        return keyManagerFactoryAlgorithm;
    }

    public void setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm) {
        this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm;
    }
}
