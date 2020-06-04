package com.heroland.competition.common.constant;

/**
 * @author: peng.zhup
 * @date: 2018/11/30
 * @instructions: 系统核心常量集
 */
public class CoreConstants {

    /**
     * 用户状态1正常使用，2冻结，3注销
     */
    public static final int USER_STATUS_NORMAL = 1;
    public static final int USER_STATUS_FREEZE = 2;
    public static final int USER_STATUS_LOGOFF = 3;

    /**
     * 全局sessionId用户验证码的key
     */
    public static final String CAPTCHA_ID = "CAPTCHAID";



    /**
     * 注册验证码过期时间 单位 s 30分钟
     */
    public static final int REGISTER_CAPTCHA_EXPIRE_TIME = 1800;


    /**
     * 忘记密码验证码过期时间 单位 s 5分钟
     */
    public static final int FORGET_CAPTCHA_EXPIRE_TIME = 300;

    /**
     * 登录验证码过期时间
     */
    public static final int LOGIN_CAPTCHA_EXPIRE_TIME = 180;

    /**
     * 字符串验证码位数
     */
    public static final int CAPTCHA_DIGITS= 4;

    /**
     * 动态加密用户名,密码过期时间
     */
    public static final int ENCRYPT_TIMEOUT = 10;

    /**
     * 申请权限有效期,单位秒
     */
    public static final Integer APPLY_TIMEOUT = 600;

    /**
     *  实名认证 认证信息存放时间
     */
    public static final Integer VERIFIY_TIMEOUT = 1800;

    /**
     * 默认申请有效期一年
     */
    public static final Integer ONE_YEAR = 365;

    /**
     * 在哪个应用系统申请,存cookie
     */
    public static final String APPLY_APPID = "APPLY_APPID";

    /**
     * 新增用户时默认密码
     */
    public static final String DEFAULT_PASSWORD = "bq654321";

    /**
     * 扁鹊共享平台公钥、密钥，不走数据库
     */
    public static final String BQ_APP_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALV/374fHGzwFTFTVHdk3rSmaD52D84AA/jPyEoyZPM7QgjbrFH1jaS58DWoo5x6ipaoK8HmOTizfayh6TFyhiufNx6/Jq5x6+qCYrLzYyRGGA4AZyF5iQ5qi33C6+teXY3OBMGnFA3ISekG8Qyh47hIvZRF7D8DGAKdEisE2iRjAgMBAAECgYBLcoOkubQMSOTWY0hqdUDqoX4V8Ofy/yinhmBBD5/NSGXT3tVMIKInxq9m7tpMUthHdlLyQGyq+FoWqBuS1YOV63IDoICDHjoIKFGtRkxDxB/cxH98CY1DOB414rkFU75XpNFMIQ/rXTQ69z8fAQa9fnldZqo2sWSzETx1SClQEQJBAPl39cKHRfOJqktWDUBze/irnHPrawbN3q2X+UERfKCQHKSM0/IAPNAcrRREhAFJkG6AgbYEN63nZRl52cPYavkCQQC6QFuHJo7fhKsJKnVEfT10tzAFTmyIIAlISNnU8Qzr+2YQD03Jj+05i5/hLRTAr4T0fq7StAXVPObxLUNP/6U7AkAF52fija8DH3vvQn8XymPhs7YFLEohJBgkBZe5iX9okWCMKGUPsh++UR9rfl+dZC9N3i6zrw/eRr6mxrJ3pxSJAkEAiHyFFkNhS9cS54KfsPL+SDu+CwVvb0naw4fvaabDy2LNZlNws5avRw5UuVSp8pItImZ7gpi99YfNzUtYGpXC5QJBANyaFatNoQJmIeCTBgjq8i6BYCsaQSwFLp3UwU6fDzG73tpwENRp6/eydubN26cLfbJrFcki/ftR6Y9j/tKwRf0=";
    public static final String BQ_APP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1f9++Hxxs8BUxU1R3ZN60pmg+dg/OAAP4z8hKMmTzO0II26xR9Y2kufA1qKOceoqWqCvB5jk4s32soekxcoYrnzcevyaucevqgmKy82MkRhgOAGcheYkOaot9wuvrXl2NzgTBpxQNyEnpBvEMoeO4SL2URew/AxgCnRIrBNokYwIDAQAB";

    /**
     * 扁鹊默认图标
     */
    public static final String DEFAULT_ICON = "https://tfsimg.alipay.com/images/openhome/TB1QBxmanXGDuNjmf7Ywu3taXXa.png";

    /**
     * 支付宝第三方登陆 公钥密钥 本应用名扁鹊共享平台
     */

    public static final String URL = "https://openapi.alipay.com/gateway.do";
    public static final String LOGIN_APP_ID = "2018122062646077";
    public static final String FORMAT = "json";
    public static final String CHARSET = "UTF-8";
    public static final String SIGN_TYPE = "RSA2";
    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIqXoipb3sgaXnlrLTfMKTRosht9pAOJxVHs8IfDxES0Tq/r1rPW0m3FyLee7ooifpYT9hvThNHVe74KfS55WCniaWHcgLE37R0FuQAQKaBZ6FRO7CwUD/Kolfl9e2N63u4DA7SKfE0ld2TGbHDQKppvY13gVSJ1P2Bw7niFLH856p11qBQx65/lyl2FK0bGjqSTh46y6SoIzQ5GPyuFaXTvcnbMUSZlAbJsN8IVcI21z2cWqriHJSLFbnDxIm8sIWCWJNXZBefAN8bcjeWoUxxJsxv3mhxp4hTVJD/3QroyaIfvytCqgsJaaNTlNgTpIfc6M0691lxAVh2rYKdtj5AgMBAAECggEBALh6LuIJlDkJ3Fe0UQVjrpKtiR3fo9Pk97y9zUKUzOdQd7S/q68a0u2mK6LC3+SZp63EjPuzdT5YGp+MY13fMLHb6cea651bN/1226bhdt3ZCqqk/AiqmLG+DnuG4ncQa2SwUUc/fZXSdzw380lOacNEzxgo3KVxqJwIUyiDSO+vI8ENWhKfcizbvmKIuLStYu967f5Xa/ZaQvXLeN6Zx0jVXPA9bGIzB1CSHi9QMIwANtcKoH1LvXVVMAAKVPC40tOoEH5i6frjgBrNWlq9JpgjPvA961+rp8JBy1qnPSxz/lCg+iircXze3XMtEK0vjqn4H4e65JoxHjR7r/YweD0CgYEA+mByD9V/c8+Ph63bi8nzHVKX/vfGTNnuKmaeSYFvmsAvtGnrTpE43aGKwMJnZVFImAW22QkkodNapPDiIioS7+4HnuftSoa9ihWDGDsvaVpB6NC3/nIN+SIGgloluVVOW9H2xn1PBMUEdE8eMqd/FdP5dqgfhhsWIsG7zPecdlcCgYEAzSsxsDg2qAEEIfhejkWxYMhHaIAUgD6NjtZJjx78DNwezG1jFtRj7J2KW4eeGPndlIkvgBl353xfjkYyoHYHOQqJexHXAm0prH5gj+UgRLcHR2X5vNePle098S9FSGPwBZiEQ+3KtnMFVKZ4eoRV7xFjJavx7fUCkGGg5RjieS8CgYASqKVXcL2yz8EOh3JZFI+9m64gHi71AziwiD07lR3QFop3k30pucDXI3bHrrUt48TZL3PU8M1+EdYrfJgdm60VnFONEBOq/ieZ5siCQcbivwsyYnuTxsQr0NFTXYx6J1VTmm4dlLES1evjDxJdDAZ4hfeNVDIlOFeJ944bVEiguQKBgG8oTtRtsFcXrdiFyx0D71VDTUT116Wfvmw790Ceheqlu94ACeaNjdHdjN+IO4TGo2FdbinWIpt9S/w2VimPqNwjtFbEVJxQ7noxQjnT5saz7zKkski49WdCUQQCG2RcGOe/OTHgfKqa6LFzO+UNG83ZusDnRr0ouInnCxf0gocrAoGABTgpDtjcU0jpva9TYZ4Mn7jhes5u4mBeDBmbByVA6Imj7IN/atWy/F0tVHOvHfk72x5zs2HMqMU/Pddywsh1RE2FaekTyQQdnayiuvzdTemE8JT7MJ8qmnlnTvLbswbTQtpFricWCAsCN2EG5KKIYWnsI/odtgutKP2rmRPt7VM=";
    public static final String ALIPAY_PUBLIC_KEY_LOGIN = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3j861fFol3lNUB2ncwAFPS+bQjjwO6lEHRFeMSsk+FjROC9b/w8A2xsAGfffXDsr2WnEbuGyT6L1VPEVLDXwzIqDBjoEkr750hSgb0kJGd+Ke7U9TZczuywjXoV4OfRO8Xo6gz0qgjzdEbkdN8MxwW7L0BaEy6zux1qMK1DIrAb7vuATQ4NKnLd+3sgaRAmeXgvagH35fFtNnoDkbViw76MlcLvk3lohks6L2koitAYgUqA5GLa0ckqFkhnhpVkJ9ejduzrToQSQ31gst3sqUelImwnYSuz8YAiyBhvhsbXj+IE+VtzALRuFQ0m7iR9GmImOwJhoeTGgdWgE2xRAhwIDAQAB";
    public static final String AUTH_CODE = "auth_code";
    public static final String GRANT_TYPE = "authorization_code";


    /**
     * 短信成功调用状态码
     */
    public static final Integer SEND_MSG_SUCCESS_CODE = 1;


    /**
     * 注册发送短信类型
     */
    public static final Integer REGISTER_MSG_TYPE = 1;

    /**
     * 忘记密码发送短信类型
     */
    public static final Integer FORGET_MSG_TYPE = 2;

}
