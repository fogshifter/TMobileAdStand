package com.tmobile.adstand.service;

//import org.jboss.ejb3.annotation.ResourceAdapter;

//import org.jboss.ejb3.annotation.ResourceAdapter;

//import javax.ejb.ActivationConfigProperty;
import com.tmobile.adstand.managedbean.IndexBean;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.PreDestroy;
//import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ejb.Singleton;
//import javax.inject.Singleton;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
//import javax.jms.MessageListener;
//
//import javax.ejb.MessageDriven;
//
//import javax.jms.Topic;
//
//@MessageDriven(activationConfig = {
//    @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
//    @ActivationConfigProperty(propertyName="destination", propertyValue="tmobile-topic"),
//    @ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge"),
//    @ActivationConfigProperty(propertyName = "connectionParameters", propertyValue = "host=127.0.0.1;port=8161"),
////        @ActivationConfigProperty(propertyName="addressList", propertyValue="127.0.0.1:8161"),
//        @ActivationConfigProperty(propertyName="subscriptionDurability", propertyValue="durable"),
////        @ActivationConfigProperty(propertyName="clientId", propertyValue="myClientId")
//})

//@ResourceAdapter("activemq-rar-5.15.3.rar")
//public class TMobileMessageListener implements MessageListener {
//    @Override
//    public void onMessage(Message message) {
//        try {
//            System.out.println(message.getBody(String.class));
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//}

@Startup
@Singleton
public class TMobileMessageListener {

    private final String activeMqHost = "tcp://t-activemq:61616";//"tcp://localhost:61616";

//    private IndexBean indexBean;
    private Connection connection;
    private Session session;
    private TopicSubscriber subscriber;

    @Inject
    private IndexBean indexBean;

    public TMobileMessageListener() {

//        this.indexBean = indexBean;
        System.out.println("TMobileMessageListener(IndexBean indexBean)");
//        this.indexBean = indexBean;

//        Hashtable<String, String> props = new Hashtable<String, String>();
//        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
//        props.put("java.naming.provider.url", "tcp://localhost:61616");
//        props.put("topic.topic1", "tmobile-topic");
//        props.put("connectionFactoryNames", "topicCF");


        try {
//            Context context = new InitialContext(props);
////            new ActiveMQConnectiveFactory
//            TopicConnectionFactory topicCF = (TopicConnectionFactory) context.lookup("topicCF");
//            context.lo
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMqHost);
            connection = connectionFactory.createConnection();
            connection.setClientID("tmobile-ad-listener");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("tmobile-topic");

            subscriber = session.createDurableSubscriber(topic, "tmobile-ad-listener");
            subscriber.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        System.out.println(textMessage.getText());
                        indexBean.update();
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            connection.start();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            subscriber.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

