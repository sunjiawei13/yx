package cn.baizhi.util;

import cn.baizhi.config.AliYunConfig;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AliYun {

    //也可以前三句提成成员变量
    //yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    //private static String endpoint = AliYunConfig.ENDPOINT;
    //阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    // private static String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
    //private static String accessKeySecret =  AliYunConfig.ACCESS_KEY_SECRET;

    //流式上传   上传字符串    照片
    public static void uploadByBytes(MultipartFile file,String fileName){

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliYunConfig.ACCESS_KEY_SECRET;
        //String bucketName = "<yourBucketName>";//2101class

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写字符串。
        //   String content = "Hello OSS";
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。file.getOriginalFilename()文件名
        PutObjectRequest putObjectRequest = new PutObjectRequest("20210816class", fileName, new ByteArrayInputStream(content));
        // 上传字符串。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }



    //删除方法   删照片
    public static void deleteFile(String fileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret =  AliYunConfig.ACCESS_KEY_SECRET;;
        //删除那个存储空间下
        String bucketName = "20210816class";  //存储空间名
        String objectName = fileName;  //文件名
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }



    //截取视频针
    public static String jqvideo(String filePath){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliYunConfig.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret =  AliYunConfig.ACCESS_KEY_SECRET;;
// 填写视频文件所在的Bucket名称。
        String bucketName = "20210816class";
// 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        String objectName =filePath;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
// 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl.toString();
    }



    // 流式上传     上传网络流   视频
    public static void URLupload(String url,String fileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYunConfig.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret =AliYunConfig.ACCESS_KEY_SECRET;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 填写网络流地址。
        InputStream inputStream=null;
            try{
                inputStream= new URL(url).openStream();
            }catch (Exception e){
                e.printStackTrace();
            }
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("20210816class", "video/"+ fileName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();

    }
    //验证码
    public static Map<String,Object> sendSms(String phone) {

        Map<String,Object> map = new HashMap<>();

        DefaultProfile profile =
                DefaultProfile
                        .getProfile("cn-beijing", AliYunConfig.ACCESS_KEY_ID, AliYunConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "登录验证");

        /*
       【登录验证】验证码1111,您正在进行333身份验证,打死不要告诉别人哦
        * */

        request.putQueryParameter("TemplateCode", "SMS_4020642");
        String s = SecurityCode.getSecurityCode();//验证码
        map.put("yzm", s);
        String mes = "【应学】"; //  正在进行【微信】身份验证
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + s + "\",\"product\":\"" + mes + "\"}");

        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(response.getData());

        Map map1 = JSONObject.parseObject(response.getData(), Map.class);
//        System.out.println(map);

        Object code = map1.get("Code");
        if(code.equals("OK")){
            map.put("code", true);
        }else {
            map.put("code", false);
        }
        return map;
    }


    //文件下载
    //fileName:文件名
    public static void download(String fileName) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYunConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = AliYunConfig.ACCESS_KEY_ID;
        String accessKeySecret =  AliYunConfig.ACCESS_KEY_SECRET;;
        String bucketName = "20210816class";  //存储空间名
        String objectName = fileName;  //文件名
        String localFile="E:\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


}

