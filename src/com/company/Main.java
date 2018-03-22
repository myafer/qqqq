package com.company;

import api.HttpHelper;
import api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/usr/local/Cellar/chromedriver/2.36/bin/chromedriver"); // 此处PATH替换为你的chromedriver所在路径
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//
//        Boolean loginBoolean = Test.login("afer", "123456qwe");
//        if (loginBoolean) {
//            Test.getUserInfos();
//            for (int k = 0; k < 100; k++) {
//                driver.get("http://candy.foodc.co/index/index/index.html?from_id=fc_5ab1fe3134230");
//                System.out.println("获取手机号");
//                String num = Test.getMobileNum("46156");
//                String pnum = num.substring(0, 11);
//                System.out.println("发送手机号");
//                driver.findElement(By.id("phone")).sendKeys(pnum);
//                driver.findElement(By.id("adress")).sendKeys("0x6f46cf5569aefa1acc1009290c8e043747172d89");
//                System.out.println("点击发送验证码");
//                driver.findElement(By.id("sendcode")).click();
//                System.out.println(pnum);
//                String bb = "";
//                for (int i = 0; i < 180; i++) {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (Exception e) {
//                    }
//                    bb = Test.getVcodeAndReleaseMobile(pnum, "afer");
//                    if (bb.contains(pnum)) {
//                        break;
//                    }
//                }
//                System.out.println(bb);
//                String vcode = bb.substring(26, 32);
//                System.out.println(vcode);
//
//                driver.findElement(By.id("password")).sendKeys("111111");
//                driver.findElement(By.id("p_code")).sendKeys(vcode);
//                driver.findElement(By.id("reg")).click();
//            }
//        }
//        else {
//            System.out.println("登陆失败");
//        }

        String loginstr = login();
        System.out.println(loginstr);
        System.out.println(loginstr.contains("|"));
        if (loginstr.contains("|")) {
            String[] strings = loginstr.split("\\|");
             if (strings[0].equals("success")) {

                 for (int i = 0; i < 100000; i ++) {
                     driver.get("http://candy.foodc.co/index/index/index.html?from_id=fc_5ab34aac111dd");
                     String token = strings[1];
                     System.out.println(token);
                     String pstr = getnum(token, "15443");
                     String[] pstrs = pstr.split("\\|");
                     System.out.println("pppp" + pstr);
                     if (loginstr.contains("|") && pstrs[0].equals("success")) {
                         System.out.println(pstrs[1]);
                         String phnum = pstrs[1];

                         driver.findElement(By.id("phone")).sendKeys(phnum);
                         driver.findElement(By.id("address")).sendKeys("0x6f46cf5569aefa1acc1009290c8e043747172d89");
                         System.out.println("点击发送验证码");
                         driver.findElement(By.id("sendcode")).click();

                         String vvcode = "";
                         int j = 0;
                         for (j = 0; j < 20; j++) {
                             String vercode = getvercode(token, "15443", phnum);
                             System.out.println("vvcode  " + vercode);
                             String[] vercodes = vercode.split("\\|");
                             if (vercode.contains("|") && vercodes[0].equals("success")) {
                                 vvcode = vercode.substring(22, 28);
                                 break;
                             }
                             try {
                                 Thread.sleep(3000);
                             } catch (Exception e) {
                             }
                         }
                         if (!vvcode.equals("")) {
                             driver.findElement(By.id("password")).sendKeys("111111");
                             driver.findElement(By.id("p_code")).sendKeys(vvcode);
                             driver.findElement(By.id("reg")).click();
                         }
                     }

                     try {
                         Thread.sleep(2000);
                     } catch (Exception e) {
                     }
                 }
             }

        }



    }

    public static String login() {
        try {
           return HttpHelper.getHtml("http://api.fxhyd.cn/UserInterface.aspx?action=login&username=1216489627&password=2bshequ");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getnum(String token, String itemid) {
//        http://api.fxhyd.cn/UserInterface.aspx?action=getmobile&token=TOKEN&itemid=项目编号&excludeno=排除号段
        try {
            return HttpHelper.getHtml("http://api.fxhyd.cn/UserInterface.aspx?action=getmobile&token=" + token +"&itemid=" + itemid);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getvercode(String token, String itemid, String pnum) {
//        http://api.fxhyd.cn/UserInterface.aspx?action=getmobile&token=TOKEN&itemid=项目编号&excludeno=排除号段
        try {
            return HttpHelper.getHtml("http://api.fxhyd.cn/UserInterface.aspx?action=getsms&token=" + token +"&itemid=" + itemid + "&mobile=" + pnum + "&release=1");
        } catch (Exception e) {
            return "";
        }
    }

}
