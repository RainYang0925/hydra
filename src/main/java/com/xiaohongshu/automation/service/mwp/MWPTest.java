package com.xiaohongshu.automation.service.mwp;

//import com.mogujie.mwpsdk.MWP;
//import com.mogujie.mwpsdk.api.EasyRemote;
//import com.mogujie.mwpsdk.api.IRemoteResponse;
//import com.mogujie.mwpsdk.api.RemoteBizDomain;
//import com.mogujie.mwpsdk.api.RemoteConfiguration;


/**
 * @author lingxue created on 6/21/16
 * @version v0.1
 **/
public class MWPTest {
//public class MWPTest extends CommonService {

//    public MWPTest(){
//        para = new MWPPara();
//        EasyRemote.setRemoteFactory(new EasyRemote.RemoteFactory() {
//            public EasyRemote.IRemote create() {
//                return MWP.instance();
//            }
//        });
//    }
//
//    /**
//     * 执行测试
//     * @return 返回执行结果,true为成功,false为失败
//     */
//    @Override
//    public boolean execute(){
//        LoggerFactory.getLogger(this.getClass()).info("----------Enter MWPTest");
//        if(((MWPPara)para).getApi() == null){
//            LoggerFactory.getLogger(this.getClass()).error("Execute Fail Due to api null");
//            return false;
//        }
//        else{
//            Object params = null;
//            if(((MWPPara)para).getInvalue() != null){
//                try{
//                    String replaceParams = Utils.replace(((MWPPara) para).getInvalue());
//                    params = JSON.parseObject(replaceParams, Class.forName(((MWPPara) para).getInclass()));
//                    ((MWPPara) para).setInvalue(replaceParams);
//                    LoggerFactory.getLogger(this.getClass()).info("Request Data=" + JSONObject.toJSONString((para)));
//                    Utils.replace = "";
//                }
//                catch (Exception e){
//                    LoggerFactory.getLogger(this.getClass()).error("Execute Fail Due to invalue parse fail: " + ((MWPPara) para).getInvalue());
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//
//            try {
//                RemoteBizDomain bizDomain = new RemoteBizDomain();
//                bizDomain.setHeaders(((MWPPara) para).getHeaders());
//                bizDomain.setQuery(((MWPPara) para).getQuery());
//
//                try {
//                    EasyRemote.addBizDomain(((MWPPara) para).getBIZ_KEY(), bizDomain);
//                }
//                catch (Exception e){
//
//                }
//
//                RemoteConfiguration remoteConfiguration = RemoteConfiguration.newBuilder(null)
//                        .setEnv(((MWPPara)para).getEnv())
//                        .setUserAgent(((MWPPara)para).getUserAgent())
//                        .setAppName(((MWPPara)para).getAppName())
//                        .setAppKey(((MWPPara)para).getAppKey())
//                        .setAppSecret(((MWPPara)para).getAppSecret())
//                        .setLegacyMode(true)
//                        .setTtid(((MWPPara)para).getTtid())
//                        .build();
//                EasyRemote.init(remoteConfiguration);
//                EasyRemote.registerLoginInfo(((MWPPara) para).getUid(), ((MWPPara) para).getSid());
//
//                EasyRemote.setCustomHost(((MWPPara) para).getIp());
//
//                response = EasyRemote.getRemote()
//                        .bizDomainKey(((MWPPara)para).getBIZ_KEY())
//                        .method(((MWPPara) para).getMethod())
//                        .apiAndVersionIs(((MWPPara)para).getApi(), ((MWPPara)para).getVersion())
//                        .parameterIs(params)
//                        .debugIP(((MWPPara)para).getBusinessIp())
//                        .needSecurity(((MWPPara) para).isHttps())
//                        .syncCall();
//            }
//            catch (Exception e){
//                LoggerFactory.getLogger(this.getClass()).error("Execute Fail Exception");
//                e.printStackTrace();
//                return false;
//            }
//            LoggerFactory.getLogger(this.getClass()).info("Execute Result = Pass");
//            return true;
//        }
//    }
//
//
//    /**
//     * 获取执行结果的头部
//     * @return 执行结果的头部
//     */
//    public Map<String,String> getHeaders(){
//        return ((IRemoteResponse)response).getHeaders();
//    }
//
//    /**
//     * 获取执行结果的状态码
//     * @return 执行结果的状态码
//     */
//    public int getStateCode(){
//        return ((IRemoteResponse)response).getStateCode();
//    }
//
//    /**
//     * 获取执行结果的数据
//     * @return 执行结果的数据
//     */
//    public String getResponseData(){
//        return JSONObject.toJSONString(((IRemoteResponse) response).getData(), SerializerFeature.WriteNonStringKeyAsString);
//    }
//
//    /**
//     * 获取执行结果的ret
//     * @return 执行结果的ret
//     */
//    public String getRet(){
//        return ((IRemoteResponse)response).getRet();
//    }
//
//    public void setPara(MWPPara para){
//        this.para = para;
//    }
//
//    public MWPPara getPara(){
//        return (MWPPara)para;
//    }
//
//    public static void main(String[] args){
//        MWPTest mwpTest= new MWPTest();
//        MWPPara para = new MWPPara();
//        para.setApi("mwp.member.homeIndex");
//    //    para.setInvalue("{\"keyword\": \"衣服\"}");
//        String uid = "132v52q";
//        para.setUid(uid);
//        para.setSid(UserUtils.getDecodeSignFromUid(uid, false));
//        para.setVersion("2.3");
//        para.setInvalue("{}");
//
////        para.getHeaders().put("test", "1");
////
////        para.setInvalue("{ \"payId\" : \"123456789123456789\"}");
//        mwpTest.setPara(para);
//
//        System.out.println(mwpTest.execute());
//        System.out.println(mwpTest.getHeaders());
//    }

}
