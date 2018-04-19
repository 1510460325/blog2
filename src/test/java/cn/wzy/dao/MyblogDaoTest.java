package cn.wzy.dao;

import cn.wzy.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy
 * @Date 2018/4/10 12:28
 */
public class MyblogDaoTest extends BaseTest {

    @Autowired
    private MyblogDao myblogDao;

    @Autowired
    private Leaving_messageDao leaving_messageDao;

    @Autowired
    private Blog_tagDao blog_tagDao;

    @Autowired
    private Blog_InfoDao blog_infoDao;
    @Test
    public void test() {
        System.out.println(myblogDao.selectByPrimaryKey(1));
    }

    @Test
    public void test1() {
        System.out.println(leaving_messageDao.selectByPrimaryKey(8));
    }
    @Test
    public void test2() {
        System.out.println(blog_tagDao.selectByPrimaryKey(5));
    }
    @Test
    public void test4() {
        System.out.println(blog_infoDao.selectByPrimaryKey(1));
    }

    @Test
    public void test5() {
        System.out.println(blog_infoDao.getAdminPassword());
    }
}
