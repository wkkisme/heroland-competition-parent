package com.heroland.competition.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.collect.Maps;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * 阿里云上传接口
 *
 * @author wangkai
 */
@Component
public class OSSManager {

    @Resource
    private OSSClient ossClient;

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    private boolean privateCloud;

    public Map<String, Object> store(FileItem file, CannedAccessControlList access) {
        Map<String, Object> url = Maps.newHashMap();
        String key = getBaseName(file.getName())+ valueOf(randomUUID()) + '.' + getExtension(file.getName());
        if (privateCloud){
            ossClient = getOssClient();
        }
        ossClient.setBucketAcl(bucketName, access);
        ossClient.putObject(bucketName, key, new ByteArrayInputStream(file.get()));
        if (!CannedAccessControlList.Private.equals(access)) {
            url.put("url", endpoint + "/" + bucketName + "/" + key);
        }
        url.put("key", key);
        return url;
    }

    public Map<String, Object> store(FileItem file, CannedAccessControlList access,String fileName) {
        Map<String, Object> url = Maps.newHashMap();
        String key = fileName + '.' + getExtension(file.getName());
        if (privateCloud){
            ossClient = getOssClient();
        }
        ossClient.setBucketAcl(bucketName, access);
        ossClient.putObject(bucketName, key, new ByteArrayInputStream(file.get()));
        if (!CannedAccessControlList.Private.equals(access)) {
            url.put("url", endpoint + "/" + bucketName + "/" + key);
        }
        url.put("key", key);
        return url;
    }

    public Map<String, Object> store(ByteArrayInputStream byteArrayInputStream, String fileName,CannedAccessControlList access) {
        Map<String, Object> url = Maps.newHashMap();
        String key = removeExtension(fileName) + valueOf(randomUUID())+ '.' + getExtension(fileName);
        if (privateCloud){
            ossClient = getOssClient();
        }
        ossClient.setBucketAcl(bucketName, access);
        ossClient.putObject(bucketName, key, byteArrayInputStream);
        if (!CannedAccessControlList.Private.equals(access)) {
            url.put("url", endpoint + "/" + bucketName + "/" + key);
        }
        url.put("key", key);
        return url;
    }

    /**
     * 获取私有云 OssClient
     *
     * @return OSSClient
     */
    private OSSClient getOssClient() {

        return new OSSClient(endpoint, new DefaultCredentialProvider(new Credentials() {
            @Override
            public String getAccessKeyId() {
                return accessKeyId;
            }

            @Override
            public String getSecretAccessKey() {
                return accessKeySecret;
            }

            @Override
            public String getSecurityToken() {
                return null;
            }

            @Override
            public boolean useSecurityToken() {
                return false;
            }
        }), new ClientConfiguration().setSupportCname(false));


    }

    public PutObjectResult putObject(File file) {
        String key = valueOf(randomUUID()) + '.' + file.getName();
        if (privateCloud){
            ossClient = getOssClient();
        }
        return ossClient.putObject(bucketName, key, file);
    }

    public InputStream get(String key) {
        if (privateCloud){
            ossClient = getOssClient();
        }
        OSSObject object = ossClient.getObject(bucketName, key);
        return object.getObjectContent();
    }

    public void delete(String[] keys) {
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
        request.setKeys(newArrayList(keys));
        if (privateCloud){
            ossClient = getOssClient();
        }
        ossClient.deleteObjects(request);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }


    public boolean isPrivateCloud() {
        return privateCloud;
    }

    public void setPrivateCloud(boolean privateCloud) {
        this.privateCloud = privateCloud;
    }
}
