
package com.hangzhou.tonight.lianlianpay.env;

public class EnvConstants {
    private EnvConstants() {
    }

    /**
     * TODO 商户号，商户MD5 key 配置。本测试Demo里的“PARTNER”；强烈建议将私钥配置到服务器上，以免泄露。“MD5_KEY”字段均为测试字段。正式接入需要填写商户自己的字段
     */
    public static final String PARTNER_PREAUTH = "201504071000272504"; // 短信

    public static final String MD5_KEY_PREAUTH = "201504071000272504_test_20150417";

    public static final String PARTNER = "201408071000001546";

    public static final String MD5_KEY = "201408071000001546_test_20140815";
    
    /*
    "busi_partner": "101001",
    "dt_order": "20151001115537",
    "info_order": "ceshi",
    "money_order": "0.01",
    "name_goods": "ceshi",
    "no_order": "376",
    "notify_url": "http://115.29.246.139/ton/p/tonllp.php",
    "oid_partner": "201506051000357507",
    "risk_item": "{\"frms_ware_category\":\"1011\",\"user_info_mercht_userno\":\"9000034\",\"user_info_dt_register\":\"20150611115636\"}",
    "sign": "72e05559e15bfc4c1f9c296020acf962",
    "sign_type": "MD5",
    "valid_order": "10080"*/

    // 商户（RSA）私钥 TODO 强烈建议将私钥配置到服务器上，否则有安全隐患
    // public static final String RSA_PRIVATE =
    // "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOilN4tR7HpNYvSBra/DzebemoAiGtGeaxa+qebx/O2YAdUFPI+xTKTX2ETyqSzGfbxXpmSax7tXOdoa3uyaFnhKRGRvLdq1kTSTu7q5s6gTryxVH2m62Py8Pw0sKcuuV0CxtxkrxUzGQN+QSxf+TyNAv5rYi/ayvsDgWdB3cRqbAgMBAAECgYEAj02d/jqTcO6UQspSY484GLsL7luTq4Vqr5L4cyKiSvQ0RLQ6DsUG0g+Gz0muPb9ymf5fp17UIyjioN+ma5WquncHGm6ElIuRv2jYbGOnl9q2cMyNsAZCiSWfR++op+6UZbzpoNDiYzeKbNUz6L1fJjzCt52w/RbkDncJd2mVDRkCQQD/Uz3QnrWfCeWmBbsAZVoM57n01k7hyLWmDMYoKh8vnzKjrWScDkaQ6qGTbPVL3x0EBoxgb/smnT6/A5XyB9bvAkEA6UKhP1KLi/ImaLFUgLvEvmbUrpzY2I1+jgdsoj9Bm4a8K+KROsnNAIvRsKNgJPWd64uuQntUFPKkcyfBV1MXFQJBAJGs3Mf6xYVIEE75VgiTyx0x2VdoLvmDmqBzCVxBLCnvmuToOU8QlhJ4zFdhA1OWqOdzFQSw34rYjMRPN24wKuECQEqpYhVzpWkA9BxUjli6QUo0feT6HUqLV7O8WqBAIQ7X/IkLdzLa/vwqxM6GLLMHzylixz9OXGZsGAkn83GxDdUCQA9+pQOitY0WranUHeZFKWAHZszSjtbe6wDAdiKdXCfig0/rOdxAODCbQrQs7PYy1ed8DuVQlHPwRGtokVGHATU=";
    public static final String RSA_PRIVATE =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJJL8OLB0J/9pmzHFxpwOeigamHd3Yk6PkZdaL6reDOdlq5mOQ0/xIqXcnaWI/Q7qtT9j/b34hR74ZMyEw4Um5mbWG0C0qK7l6RbQaUExbF/gU+RiVCQ8TQW1qgw/eBh+H47Aj58hGulbfJKfeZJydzpnvTSdT9VitGR9xIJtKdHAgMBAAECgYBMmbzATnE5RGu+qyP6sOZxWoU5Rx03PCrdVw2AQHIIvKvoFxgqSshTNOc3Fngu6osRSM73pmVXCmJbWy3FAp9Rqg2FZfQoX+ds4cnj3QVpeILw6b2Sr0rI2OBkbXGFre/crM+JcjYBAkV7pnwcWRH3EyOvzLUqKs5qEkOycxTi8QJBAOUFVS8ipCnp7Qaynig6PcfJC0JP4GxpFmQu0w1OrmlzP/zezUfRwihTx1NPssJm9HD7KNiBDlgFj0PQJkGbB18CQQCjh90kBAoloAsCxe/qD4w7lbre75P16Kicb+K0FCeJsZrdXpApFhlDo60zPNUJEPph9HFptZfNBE8I8dIesHEZAkEAxe4V8Oa/ennxoBg/GAU936yhTm46R3eLIopVXOrjUb+JTcJBKBDg/Hlri1UV6W2RVRO7+WGQRAKKDtGWPpz9gQJAImZAFIVtBQEnj8vHbfsbSqVyi9blzwLEBTRcAfmDX6mmpA5yUNI/OkVB99dCEQgrQ1PCT7RNXGkdnwoPYzlGcQJBAJQQrWM8SxovyqcN7Md2wRvIjA1Ny7OJGSR8y+0eu/D0GydQbUj1rNdPX5CLNFVwvcgMwkLNUD+u+JSol5+PQHk=";

    // 银通支付（RSA）公钥
    public static final String RSA_YT_PUBLIC =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";

}
