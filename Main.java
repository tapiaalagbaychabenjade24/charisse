package org.example;

import org.example.Accounts.HomePage;

import javax.swing.*;

import java.util.List;

import static org.example.API.getCoins;

public class Main {

    public static void main(String[] args) {
        List<API.Coin> coins = getCoins();

        JFrame frame = new JFrame();
        new HomePage(frame);
        new API();
    }
}