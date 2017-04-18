package com.xiaohongshu.automation.service.webdriver.pages;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zren on 17/3/23.
 */
public class WXPageObjectTest {

    @Test
    public void testWXMsg(){
        WXPageObject page = new WXPageObject();
        page.sendMsgTo("hello, This is a message sent from Xiaohongshu Weixin Robot, you can check the quip for https://xiaohongshu.quip.com/vJbpALak7Rna...By Arthur.Ren from QA team.","舒淮");
    }

}