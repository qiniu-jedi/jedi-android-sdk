package qiniu;


import android.util.Log;

import com.google.gson.Gson;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiniu
 * 上传资源类
 */

public class UploadRes {

    private Configuration config;

    private static UploadManager uploadManager;  // 上传类

    public UploadRes() {
        defaultInitConf();
        initUploadManager();
    }

    // 默认的配置文件，可根据自己需要写
    public void defaultInitConf() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                // .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                // .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
    }

    // 初始化上传类
    public void initUploadManager() {
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    // 上传方法
    public void uploadFile() {
        //————http上传,指定zone的具体区域——
        //Zone.zone0:华东
        //Zone.zone1:华北
        //Zone.zone2:华南
        //Zone.zoneNa0:北美
        //———http上传，自动识别上传区域——
        //Zone.httpAutoZone

        //———https上传，自动识别上传区域——
        //Zone.httpsAutoZone

        //Configuration config = new Configuration.Builder().zone(Zone.httpAutoZone).build();
        //UploadManager uploadManager = new UploadManager(config);

        String data = "<File对象 或 文件路径 或 字节数组 >";
        String key = "<指定七牛服务上的文件名 或 在客户端指定 或 null >";
        String token = "<从服务端获取>";

        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);

    }

    /**
     * 从服务器端获取到上传的token
     * 参数含义： 点播空间名，失效时间，类型
     * 详细信息参考点播云开发者文档
     */
    private String getVodUpToken(String hub, int deadline, String type) {
        Gson gson = new Gson();

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("hub", hub);
        map.put("type", "video");  //默认值
        map.put("deadline", 6 * 3600);  //默认值

        if (deadline > 0) {
            map.put("deadline", deadline);
        }

        if (type != null && type.length() > 0) {
            map.put("type", type);
        }

        String bodyStr = gson.toJson(map);
        String uptoken = null;
        /**
         * 下面将   header ，method，bodystr  以post的方式请求用户自己的服务器端
         * 获取到auth，具体的auth鉴权方式，参考点播云的开发者文档。
         *
         * uptoken=  http request to you server
         **/
        return uptoken;
    }
}
