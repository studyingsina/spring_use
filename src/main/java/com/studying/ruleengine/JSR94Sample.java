package com.studying.ruleengine;

import org.drools.jsr94.rules.RuleServiceProviderImpl;

import javax.rules.*;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSR94Sample {

    private static RuleServiceProvider ruleProvider;

    private static void initProvider() {
        String uri = RuleServiceProviderImpl.RULE_SERVICE_PROVIDER;
        Class providerClass = RuleServiceProviderImpl.class;

        try {
            //注册ruleProvider
            RuleServiceProviderManager.registerRuleServiceProvider(uri, providerClass);

            //从RuleServiceProviderManager获取ruleProvider
            ruleProvider = RuleServiceProviderManager.getRuleServiceProvider(uri);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void adminSample() {


        try {
            //获取RuleAdministrator实例
            RuleAdministrator admin = ruleProvider.getRuleAdministrator();

            //获取RuleExectuionSetProvider
            HashMap properties = new HashMap();
            properties.put("name", "My Rules");
            properties.put("description", "A trivial rulebase");

            LocalRuleExecutionSetProvider ruleExecutionSetProvider = admin.getLocalRuleExecutionSetProvider(properties);

            //创建RuleExecutionSet
            FileReader reader = new FileReader("/Users/junweizhang/workspace/me/github/spring_use/src/main/resources/bin/sample.drl");
            RuleExecutionSet reSet = ruleExecutionSetProvider.createRuleExecutionSet(reader, properties);

            //注册RuleExecutionSet
            admin.registerRuleExecutionSet("mysample", reSet, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void runtimeSampe() {
        try {
            //获取RuleRuntime, 创建会话
            RuleRuntime runtime = ruleProvider.getRuleRuntime();
            StatelessRuleSession ruleSession = (StatelessRuleSession) runtime.createRuleSession("mysample", null, RuleRuntime.STATELESS_SESSION_TYPE);

            //初始化输入数据
            Message message1 = new Message();
            message1.setMessage("1.Hello World");
            message1.setStatus(Message.HELLO);

            Message message2 = new Message();
            message2.setMessage("2.Goodbye World");
            message2.setStatus(Message.GOODBYE);


            List inputs = new ArrayList();
            inputs.add(message1);
            inputs.add(message2);

            //执行规则
            List<Message> results = ruleSession.executeRules(inputs);
            for (int i = 0; i < results.size(); i++) {
                Message msg = results.get(i);
                System.out.println("pro-" + msg.message);
            }


            //释放会话资源
            ruleSession.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        initProvider();
        adminSample();
        runtimeSampe();
    }


    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}