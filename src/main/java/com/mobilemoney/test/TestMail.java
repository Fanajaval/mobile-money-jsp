package com.mobilemoney.test;

import com.mobilemoney.util.EmailUtility;

public class TestMail {

    public static void main(String[] args) {

        EmailUtility.sendEmail(

            "andriatoavimanana@gmail.com",

            "Test Mobile Money",

            "Ceci est un test d'envoi email depuis Java."
        );
    }
}