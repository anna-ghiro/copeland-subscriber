package com.example.copelandconsumer.service;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

public class Subscriber {
    public static void main(String[] args) throws Exception {

        JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = factory.createConnection("admin", "password");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic("RandomTopic");

        MessageConsumer subscriber = session.createConsumer(destination);

        String response;
        do {
            Message msg = subscriber.receive();
            response = ((TextMessage) msg).getText();

            System.out.println("Received = " + response);

        } while (!response.equalsIgnoreCase("Quit"));

        connection.close();
    }
}

