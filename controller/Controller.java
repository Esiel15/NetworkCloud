package controller;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import model.CloudCore;
import model.FileHandler;
import views.MainWindow;

public class Controller {
    private CloudCore core;
    private MainWindow mainWindow;

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        // controller.initCore();
        controller.initWindow();
    }

    private void initCore() {
        FileHandler configFile = new FileHandler();

        // Check if config file is in directory
        if (configFile.open("config.json", "r")) {
            JSONObject config = new JSONObject();
            try {
                // Read file and convert it in JSON map
                config = new JSONObject(configFile.read());
                // Check if path for system files is actually a valid directory
                File systemDirectory = new File(config.getString("path"));
                if (systemDirectory.isDirectory()) {
                    core = new CloudCore(this, config);
                    core.start(); // Start core
                }
                else {
                    System.out.println("Fatal Error: Specified path in configuration file must be a directory.");
                    System.exit(-1);
                }
            } catch (JSONException | IOException e) {
                System.out.println("Fatal Error: Cannot read configuration file.");
                System.exit(-1);
            }
            
        }
        else {
            System.out.println("Fatal Error: Cannot open configuration file.");
            System.exit(-1);
        }
    }

    private void initWindow() {
        mainWindow = new MainWindow(this);
        mainWindow.start();
    }

    public void exit() {
        System.exit(0);
    }
}