package com.example.mail;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class TestSendMail {
    public static void main(String[] args) throws Exception {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < 100; i++) {
            stack.push(String.valueOf(i));
        }
        for (int i = 0; i <stack.size();i++){
            System.out.println(stack.pop());
        }
    }



    public static long SECOND = 1000;

    public synchronized  void test() throws MessagingException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            wait(SECOND * 5);
            new MailUtil().send();
        }
    }

}
