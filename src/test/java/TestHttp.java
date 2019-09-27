

/*
 * @author: sunxiaoxiong
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestHttp {
    String access_token = "9C7488BBE93288012876923EA40D011F";
    String openid = "89b9d22bef4d94ce070f35c9e78ea172";
    String client_id = "D0A33C4F16AE9F215576B9FF4974AA20";

    public void POST(String url, List<NameValuePair> nvps) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("ContentType", "application/x-www-form-urlencoded");
        // 设置表单提交编码为UTF-8
        UrlEncodedFormEntity entry = new UrlEncodedFormEntity(nvps, "UTF-8");
        entry.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
        httppost.setEntity(entry);
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String ss = EntityUtils.toString(entity, "UTF-8");
        //打印返回结果
        System.out.println(ss);
        EntityUtils.consume(entity);
        httpclient.getConnectionManager().shutdown();
    }

    /**
     * 根据表达式检索专利专利转让检索
     */
    @Test
    public void prsSerachTest() throws Exception {
        String url = "http://open.cnipr.com/cnipr-api/rs/api/search/sf9/" + client_id;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // 变更前权利人=(糖尿兵健康管理(北京)有限公司) or 变更后权利人=(糖尿兵健康管理(北京)有限公司) or 当前权利人=(糖尿兵健康管理(北京)有限公司)
        // 申请号=%
        nvps.add(new BasicNameValuePair("exp", "变更前权利人=('糖尿病健康管理(北京)有限公司') or 变更后权利人=('糖尿病健康管理(北京)有限公司') or 当前权利人=('糖尿病健康管理(北京)有限公司')"));
        nvps.add(new BasicNameValuePair("from", "0"));
        nvps.add(new BasicNameValuePair("to", "8"));
        nvps.add(new BasicNameValuePair("access_token", access_token));
        nvps.add(new BasicNameValuePair("openid", openid));
        this.POST(url, nvps);
    }
}
