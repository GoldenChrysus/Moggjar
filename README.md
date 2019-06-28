# Moggjar
Moggjar is the TEST Alliance alternative for Mumble push-to-talk keys for quadriplegics trying to talk in either general or a whisper channel at once.

## Installation
1. Download and install [Java SE 12](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html) for your operating system.
2. Download the [latest JAR release](https://github.com/GoldenChrysus/Moggjar/releases).
3. Run the JAR file.

## Mumble Setup
Your Mumble needs to use the keys as specified in the TEST Wiki Mumble setup. This includes the keys for local, speak upwards, speak downwards, and fleet. Note: if you don't need a certain key (usually the speak downwards key), you don't need to configure it in Mumble; just be sure to not use the corresponding button in this tool. The key bindings are currently hard-coded as follows:
* Local: page down
* Speak upwards: page up
* Fleet: numpad *
* Speak downwards: numpad -

## Contributing
The project in this repository is an Eclipse Maven project. Please use this project for testing and development in order to maintain consistency of releases. The project currently uses Java SE 12 for compiler compliance, so ensure you have this installed.

When making a contribution, please create a fork, make your changes, and make a pull request back to master. In your pull request, delineate your changes and the purpose of your pull.

## To-Do List
* Features:
	* Ability to configure Mumble talk buttons including adding buttons, removing buttons, and modifying keybinds.