import com.bmw.seckill.SeckillAdminApplication;
import com.bmw.seckill.common.base.BaseResponse;
import com.bmw.seckill.dao.SeckillAdminDao;
import com.bmw.seckill.model.SeckillAdmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillAdminApplication.class)
public class adminTest {

    @Autowired
    SeckillAdminDao seckillAdminDao;


    @Test
    public void insertTest(){
        SeckillAdmin seckillAdmin = new SeckillAdmin();
        seckillAdmin.setLoginName("xbc");
        seckillAdmin.setPassword("123456");
        seckillAdmin.setCreateTime(new Date());
        seckillAdmin.setName("TT");
        seckillAdminDao.insert(seckillAdmin);
        System.out.println("seckillAdmin = " + seckillAdmin);
        System.out.println("-----------------------------------");
    }
}
