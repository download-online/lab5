package ru.jefremov.prog;

import ru.jefremov.prog.exceptions.ExitInterruptionException;
import ru.jefremov.prog.exceptions.QuitInterruptionException;
import ru.jefremov.prog.exceptions.SavedCollectionInteractionException;
import ru.jefremov.prog.exceptions.command.CommandInterruptionException;
import ru.jefremov.prog.exceptions.command.CommandLaunchException;
import ru.jefremov.prog.interaction.InteractiveSubmitter;
import ru.jefremov.prog.interaction.Printer;
import ru.jefremov.prog.managers.*;
import ru.jefremov.prog.models.Ticket;

import java.util.LinkedHashSet;

public class Main {
    public static void main(String[] args) {
        String variableName = "LAB5";
        String path = System.getenv(variableName);
        String exitMessage = "Program execution finished";

        if (path==null) {
            Printer.println("Could not get the path to the file from the environment variable "+variableName);
            Printer.println(exitMessage);
            return;
        }

        Administrator administrator = new Administrator(new InteractiveSubmitter(), path);
        administrator.ticketValidator.reviewPrice(null);
        ModeManager modeManager = administrator.modeManager;
        CommandManager commandManager = administrator.commandManager;

        boolean running = true;

        try {
            LinkedHashSet<Ticket> tickets = administrator.collectionFileInteraction.loadCollection();
            boolean loaded = administrator.storage.loadCollection(tickets);
            if (!loaded) {
                Printer.println("The file contains an incorrect collection:");
                Printer.println(administrator.storage.reviewCollection(tickets));
                Printer.println(exitMessage);
                return;
            }
            Printer.println("The collection was successfully loaded.");
        } catch (SavedCollectionInteractionException e) {
            Printer.println(e.getMessage());
            Printer.println("Collection loading failed");
            Printer.println(exitMessage);
            return;
        }
        Printer.println("");
        Printer.print("> ");
        while (running) {
            try {
                if (!modeManager.hasNext()) {
                    running = false;
                    Printer.println("The input has ended.");
                    break;
                }

            } catch (QuitInterruptionException ignored) {

            }
            String line = modeManager.next();
            if (line==null || line.isBlank()) {
                continue;
            }
            String[] arg = line.split(" ",2);
            String word = arg[0];

            try {
                commandManager.launchCommand(word,line);
                Printer.print("> ");
            } catch (CommandInterruptionException e) {
                Printer.println(e.getMessage());
                running = modeManager.interrupt();
            } catch (CommandLaunchException e) {
                Printer.println(e.getMessage());
                Printer.print("> ");
            } catch (ExitInterruptionException e) {
                running = false;
            } catch (QuitInterruptionException e) {
                Printer.print("> ");
            } catch (Exception e){
                Printer.println("UNEXPECTED: \n"+e.getMessage());
                Printer.print("> ");
            }
        }
        Printer.println(exitMessage);
    }
}