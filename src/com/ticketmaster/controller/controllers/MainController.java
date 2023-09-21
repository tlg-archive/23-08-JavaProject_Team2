package com.ticketmaster.controller.controllers;

import com.ticketmaster.controller.db.UserDB;
import com.ticketmaster.controller.framework.Controller;
import com.ticketmaster.controller.io.LogoIO;
import com.ticketmaster.model.User;

import com.ticketmaster.view.framework.*;
import com.ticketmaster.view.utils.ConsoleText;

import com.ticketmaster.view.utils.ConsoleTextColor;
import com.ticketmaster.view.utils.DialogResult;
import com.ticketmaster.view.utils.RegexSelector;

public class MainController implements Controller<Object, Object> {

    private TicketQueueController ticketQueueController = new TicketQueueController();

    @Override
    public Object run(Object o){


        ConsoleView mainView = new ConsoleView();

        mainView.addPassiveComponents(new TextComponent(LogoIO.getLogo()));
        TextComponent mainViewBadUsernamePassword = new TextComponent("Wrong username or password, try again.", ConsoleTextColor.RED, true);
        mainView.addPassiveComponents(mainViewBadUsernamePassword);
        mainView.addPassiveComponents(new MultiTextComponent(
                new ConsoleText("Leave blank and press "),
                new ConsoleText("ENTER ", ConsoleTextColor.GREEN),
                new ConsoleText("to exit the application.")
        ));

        mainView.addInputCollector(new ValidatorInputCollector("username: ", "Invalid username", "", this::validateUsername));
        mainView.addInputCollector(new RegexInputCollector("password: ", "", "", RegexSelector.ANYTHING.getRegex()));


        DialogResult result = DialogResult.AWAITING;
        while (result != DialogResult.ESCAPE){
            result = mainView.show();

            if(result == DialogResult.SUCCESS){
                String username = mainView.getUserInputs().get(0);
                String password = mainView.getUserInputs().get(1);

                try {
                    User user = UserDB.authenticate(username, password);
                    if(user != null){
                        ticketQueueController = new TicketQueueController();
                        mainViewBadUsernamePassword.hide();
                        //Calling the TicketQueue controller to run, passing the user as a parameter
                        ticketQueueController.run(user);
                    }else{
                        mainViewBadUsernamePassword.unHide();
                        continue;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private boolean validateUsername(String s) {
        if(s == null) {
            return false;
        }
        else{
            return !Character.isDigit(s.charAt(0));
        }
    }
}