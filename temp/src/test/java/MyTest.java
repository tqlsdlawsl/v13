import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zsp
 * @Date 2019/6/18
 */
public class MyTest {

    @Test
    public void grabPage() throws ClientProtocolException, IOException {
        //1.创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //2.定义资源路径
        String path = "http://stu.1000phone.net/student.php/Exam/index";
        //3.创建get对象
        HttpGet get = new HttpGet(path);
        //4.执行get请求，获取response对象
        CloseableHttpResponse response = client.execute(get);
        //5.获取响应结果
        int code = response.getStatusLine().getStatusCode();
        //6.处理结果
        if (code == 200) {
            //7.获取响应结果
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                System.out.print(new String(bs, 0, len));
            }
        } else {

        }
    }

}
