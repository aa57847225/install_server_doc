//package com.whl.demo.test;
//
//import com.whl.demo.service.UserService;
//import com.whl.demo.util.RedisUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
///**
// * @program: cache-redis
// * @description:
// * @author: Mr.Wang
// * @create: 2018-09-21 09:42
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TestUser {
//
//    @Resource
//    UserMapper userMapper;
//
//    @Resource
//    RedisUtils redisUtils;
//
//    @Resource
//    UserService userService;
//
//    CountDownLatch countDownLatch = new CountDownLatch(10);
//
//    @Before
//    public void before(){
//        redisUtils.del("user");
//    }
//
//    @Test
//    public void testFindAll()
//    {
//
//        System.out.println("===========================");
//        List<User> users =  userMapper.findAll();
//        for (User u:users)
//        {
//            System.out.println(u.getUserName()+","+u.getPassword()+","+u.getUserName());
//        }
//        //System.out.println(userMapper.findAll().toString());
//        System.out.println("===========================");
//    }
//
//
//    @Test
//    public void testRedis()
//    {
//
//        System.out.println("===========================");
//        System.out.println(redisUtils.exists("aaa"));
//        System.out.println(redisUtils.hget("user-permission-db1","1890"));
//        System.out.println("===========================");
//    }
//
//    @Test
//    public void testCache()
//    {
//
//        System.out.println("===========================");
//        System.out.println(userService.getAllUser().toString());
//        System.out.println("===========================");
//    }
//
//    @Test
//    public void testCache3()
//    {
//
//        System.out.println("===========================");
//        System.out.println(userService.quertyForTemplate());
//        System.out.println("===========================");
//    }
//
//    @Test
//    public void testCache2() throws InterruptedException {
//        for(int i=0;i<10;i++){
//            new Thread(new queryUser()).start();
//            countDownLatch.countDown();
//        }
//        Thread.currentThread().sleep(5000);
//    }
//
//    private class queryUser implements Runnable{
//        @Override
//        public void run() {
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            List<User> users = userService.getAllUser3();
////            List<User> users = userService.quertyForTemplate();
//        }
//    }
//
//}
