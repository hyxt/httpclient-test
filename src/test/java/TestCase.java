import com.hyxt.http.SSLUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by serv on 2014/9/2.
 */
public class TestCase {

    public static final String token = "Vx0H9o7NMAOwrFW9uV49AvRBZ/8=";

    //统一支付
    @Test
    public void test01() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/rest/unifiedorder/?appToken="+token,"unifiedorder.json");
    }

    //订单列表查询
    @Test
    public void test02() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/rest/orderQuery/?appToken="+token,"orderQuery.json");
    }

    //订单详情查询
    @Test
    public void test03() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/rest/orderDetail/?appToken="+token,"orderDetail.json");
    }

    //退款
    @Test
    public void test04() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/rest/refund/?appToken="+token,"refund.json");
    }

}
