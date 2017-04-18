package com.xiaohongshu.automation;

import com.xiaohongshu.automation.runner.TestRunner;

/**
 * @author lingxue created on 6/15/16
 * @version v0.1
 * 整个程序的入口
 **/


public class Application {

    public static void main(String[] args){
        if(args.length < 1){
            printUsage();
        }
        TestRunner.runFromControlFile(args[0]);
    }

    public static void printUsage(){
        System.out.println("---------------------------------------");
        System.out.println("Usage: [control file]");
    }

}
