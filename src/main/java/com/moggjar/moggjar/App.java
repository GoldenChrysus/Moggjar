package com.moggjar.moggjar;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {
	private static Robot robot;
	private static Path settings_path;
	private static JsonObject settings;

	public static void main(String[] args) throws AWTException, IOException {
		getOrCreateSettings();

		robot = new Robot();

		JFrame mainj                  = new JFrame();
		JToggleButton button_local    = new JToggleButton("Local (Page Down)");
		JToggleButton button_upward   = new JToggleButton("Upward (Page Up)");
		JToggleButton button_fleet    = new JToggleButton("Fleet (Numpad *)");
		// JToggleButton button_fleet    = new JToggleButton("Fleet (Shift + 9)"); // Custom keybind request
		JToggleButton button_downward = new JToggleButton("Downward (Numpad -)");
		int window_width              = (settings.has("window_width"))
				? Integer.parseInt(settings.get("window_width").toString())
				: 600;
		int window_height             = (settings.has("window_height"))
				? Integer.parseInt(settings.get("window_height").toString())
				: 300;
	
		mainj.setSize(window_width, window_height);
		mainj.setLayout(new GridLayout(4, 1));
		mainj.setAlwaysOnTop(true);
		mainj.add(button_local);
		mainj.add(button_upward);
		mainj.add(button_fleet);
		mainj.add(button_downward);
		mainj.setVisible(true);
		button_local.setVisible(true);
		button_upward.setVisible(true);
		button_fleet.setVisible(true);
		button_downward.setVisible(true);
		
		if (settings.has("window_x") && settings.has("window_y")) {
			mainj.setLocation(Integer.parseInt(settings.get("window_x").toString()), Integer.parseInt(settings.get("window_y").toString()));
		}

		mainj.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				settings.addProperty("window_height", mainj.getHeight());
				settings.addProperty("window_width", mainj.getWidth());
				settings.addProperty("window_x", mainj.getX());
				settings.addProperty("window_y", mainj.getY());

				try {
					saveSettings();
				} catch (IOException ex) {
				}

				System.exit(0);
			}
		});
		
		button_local.addItemListener((ItemEvent ev) -> {
			toggleKey(ev, KeyEvent.VK_PAGE_DOWN);
		});

		button_upward.addItemListener((ItemEvent ev) -> {
			toggleKey(ev, KeyEvent.VK_PAGE_UP);
		});

		button_fleet.addItemListener((ItemEvent ev) -> {
			toggleKey(ev, KeyEvent.VK_MULTIPLY);

			// Custom keybind request: shift + 9
			// toggleKey(ev, KeyEvent.VK_SHIFT);
			// toggleKey(ev, KeyEvent.VK_9);
		});

		button_downward.addItemListener((ItemEvent ev) -> {
			toggleKey(ev, KeyEvent.VK_SUBTRACT);
		});
	}

	private static void toggleKey(ItemEvent ev, int target_key) {
		int state = ev.getStateChange();

		if (state == ItemEvent.SELECTED){
			robot.keyPress(target_key);
		} else if (state == ItemEvent.DESELECTED){
			robot.keyRelease(target_key);
		}
	}
	
	private static void getOrCreateSettings() throws IOException {
		settings      = new JsonObject();
		settings_path = Paths.get("settings.txt");
		
		String settings_file = "";
		
		if (Files.exists(settings_path)) {
			settings_file = Files.readString(settings_path);
		} else {
			Files.createFile(settings_path);
		}
		
		if (!settings_file.isEmpty()) {
			JsonParser parser = new JsonParser();

			settings = parser.parse(settings_file).getAsJsonObject();
		}
	}
	
	private static void saveSettings() throws IOException {
		Files.writeString(settings_path, settings.toString());
	}
}