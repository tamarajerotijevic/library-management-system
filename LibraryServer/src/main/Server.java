package main;

import forms.ServerForm;

public class Server {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ServerForm().setVisible(true));
    }
}
