package cn.baizhi.test;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
    /*
    pom.xml
    <dependency>
      <groupId>com.aliyun</groupId>
      <artifactId>aliyun-java-sdk-core</artifactId>
      <version>请参见最新版本号</version>
    </dependency>
    */
    public class SendSms {
        public static void main(String[] args) {
            DefaultProfile profile =
                    DefaultProfile
                            .getProfile("cn-beijing", "LTAI5tJsqFUBRbCEQ5srjkhC", "9e0PoaCvXeDbs5mHe8G8zUnR0WyjXT");
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
            request.setSysVersion("2017-05-25");//API的版本号
            request.setSysAction("SendSms");//API的名称
            request.putQueryParameter("PhoneNumbers", "17602658565");//接收短信的手机号码
            request.putQueryParameter("SignName", "登录验证");//短信签名名称

        /*
       【登录验证】验证码1111,您正在进行333身份验证,打死不要告诉别人哦
        * */

            request.putQueryParameter("TemplateCode", "SMS_226415481");//短信模板ID
            String s = "靠自己寸步难行,靠富婆直通天庭";
            String mes = "哈哈哈";
            request.putQueryParameter("TemplateParam", "{\"code\":\""+s+"\",\"product\":\""+mes+"\"}");//短信模板变量对应的实际值
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());

//            System.out.println(response.getHttpStatus());

//            if(response.getHttpStatus() == 200 ){
//
//            }
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
}
