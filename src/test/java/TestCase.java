import com.hyxt.http.SSLUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by serv on 2014/9/2.
 */
public class TestCase {

    //统一支付
    @Test
    public void test01() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/unifiedorder/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=","unifiedorder.json");
    }

    //订单列表查询
    @Test
    public void test02() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/orderQuery/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=","orderQuery.json");
    }

    //订单详情查询
    @Test
    public void test03() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/orderDetail/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=","orderDetail.json");
    }

    //退款
    @Test
    public void test04() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/refund/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=","refund.json");
    }

    //发起退款申请
    @Test
    public void test05() throws IOException {
        SSLUtils.post("https://192.168.1.111:8443/audit/?appToken=3xCfcAt4ssxkMEP4MOf/mGi83Ms=","refund.json");
    }
}
