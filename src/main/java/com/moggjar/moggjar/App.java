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
	private static JsonObject settings;

	public static void main(String[] args) throws AWTException, IOException {
		robot    = new Robot();
		settings = new JsonObject();
		
		String settings_file = "";
		Path settings_path   = Paths.get("settings.txt");
		
		if (Files.exists(settings_path)) {
			settings_file = Files.readString(settings_path);
		} else {
			Files.createFile(settings_path);
		}
		
		if (!settings_file.isEmpty()) {
			JsonParser parser = new JsonParser();

			settings = parser.parse(settings_file).getAsJsonObject();
		}

		JFrame mainj                  = new JFrame();
		JToggleButton button_local    = new JToggleButton("Local (Page Down)");
		JToggleButton button_upward   = new JToggleButton("Upward (Page Up)");
		JToggleButton button_fleet    = new JToggleButton("Fleet (Numpad *)");
		JToggleButton button_downward = new JToggleButton("Downward (Numpad -)");
		int window_width              = (settings.has("window_width"))
				? Integer.parseInt(settings.get("window_width").toString())
				: 600;
		int window_height             = (settings.has("window_height"))
				? Integer.parseInt(settings.get("window_height").toString())
				: 300;
	
		mainj.setSize(window_width, window_height);
		mainj.setLayout(new GridLayout(4, 1));
		mainj.add(button_local);
		mainj.add(button_upward);
		mainj.add(button_fleet);
		mainj.add(button_downward);
		mainj.setVisible(true);
		mainj.setAlwaysOnTop(true);
		button_local.setVisible(true);
		button_upward.setVisible(true);
		button_fleet.setVisible(true);
		button_downward.setVisible(true);

		mainj.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				settings.addProperty("window_height", mainj.getHeight());
				settings.addProperty("window_width", mainj.getWidth());
				
				try {
					Files.writeString(settings_path, settings.toString());
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
}