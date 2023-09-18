package com.ticketmaster.controller;

import com.ticketmaster.model.Location;
import com.ticketmaster.model.Team;
import com.ticketmaster.model.User;
import com.ticketmaster.view.components.ConsoleView;
import com.ticketmaster.view.components.InputCollectorRegex;
import com.ticketmaster.view.components.TextComponent;
import com.ticketmaster.view.utils.ConsoleTextColor;
import com.ticketmaster.view.utils.DialogResult;
import com.ticketmaster.view.utils.RegexSelector;

class MainController implements ControllerT<Object, Object> {

    private TicketQueueController ticketQueueController = new TicketQueueController();

    @Override
    public Object run(Object o) {
        ConsoleView mainView = new ConsoleView();

        mainView.addPassiveComponents(new TextComponent(
                "\n" +
                        "\n" +
                        "ooooooooooooo ooooo   .oooooo.   oooo    oooo oooooooooooo ooooooooooooo      ooo        ooooo       .o.        .oooooo..o ooooooooooooo oooooooooooo ooooooooo.   \n" +
                        "8'   888   `8 `888'  d8P'  `Y8b  `888   .8P'  `888'     `8 8'   888   `8      `88.       .888'      .888.      d8P'    `Y8 8'   888   `8 `888'     `8 `888   `Y88. \n" +
                        "     888       888  888           888  d8'     888              888            888b     d'888      .8\"888.     Y88bo.           888       888          888   .d88' \n" +
                        "     888       888  888           88888[       888oooo8         888            8 Y88. .P  888     .8' `888.     `\"Y8888o.       888       888oooo8     888ooo88P'  \n" +
                        "     888       888  888           888`88b.     888    \"         888            8  `888'   888    .88ooo8888.        `\"Y88b      888       888    \"     888`88b.    \n" +
                        "     888       888  `88b    ooo   888  `88b.   888       o      888            8    Y     888   .8'     `888.  oo     .d8P      888       888       o  888  `88b.  \n" +
                        "    o888o     o888o  `Y8bood8P'  o888o  o888o o888ooooood8     o888o          o8o        o888o o88o     o8888o 8\"\"88888P'      o888o     o888ooooood8 o888o  o888o \n" +
                        "                                                                                                                                                                   \n" +
                        "                                                                                                                                                                   \n" +
                        "                                                                                                                                                                   "
        ));
        TextComponent mainViewBadUsernamePassword = new TextComponent("Wrong username or password, try again.", ConsoleTextColor.RED, true);
        mainView.addPassiveComponents(mainViewBadUsernamePassword);

        mainView.addInputCollector(new InputCollectorRegex("Enter username, leave it blank to go back: ", "Invalid username, it cannot contain numbers", "", RegexSelector.NO_NUMBERS.getRegex()));
        mainView.addInputCollector(new InputCollectorRegex("Enter password, leave it blank to go back: ", "", "", RegexSelector.ANYTHING.getRegex()));


        DialogResult result =DialogResult.AWAITING;
        while (result != DialogResult.ESCAPE){
            result = mainView.show();

            if(result == DialogResult.SUCCESS){
                String username = mainView.getUserInputs().get(0);
                String password = mainView.getUserInputs().get(1);

                //FAKE USER, TODO, REPLACE WITH A REAL ONE
                User user = new User("aav", "123", new Team("LAS1-OTS", new Location("LAS1")));
                if(user != null){
                    mainViewBadUsernamePassword.hide();
                    //Calling the TicketQueue controller to run, passing the user as a parameter
                    ticketQueueController.run(user);
                }else{
                    mainViewBadUsernamePassword.unHide();
                    continue;
                }
            }
        }

        return null;
    }
}