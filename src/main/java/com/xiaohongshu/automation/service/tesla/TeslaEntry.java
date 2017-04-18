package com.xiaohongshu.automation.service.tesla;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * @author lingxue created on 7/21/16
 * @version v0.1
 **/

public class TeslaEntry {

    private TeslaTest teslaTest = new TeslaTest();
    private TeslaPara teslaPara = new TeslaPara();
    private boolean executeResult = false;

    @Given("Telsa Service is \"(.*?)\"")
    public void teslaSerivce(String service){
        teslaPara.setService(service);
    }

    @Given("Tesla method is \"(.*?)\"")
    public void teslaMethod(String method){
        teslaPara.setMethod(method);
    }

    @When("Tesla inValue is \"(.*?)\"")
    public void teslaInvalue(String inValue){
//        teslaPara.setInclass(TeslaParaHelper.getInclassFromServiceAndMethod(teslaPara.getService(), teslaPara.getMethod()));
        teslaPara.setInvalue(inValue);
        teslaTest.setPara(teslaPara);
        executeResult = teslaTest.execute();
    }

    @Then("Tesla Execute Result should be \"(.*?)\"")
    public void teslaExecuteResult(String result){
        Assert.assertEquals(Boolean.valueOf(result), executeResult);
    }

}
