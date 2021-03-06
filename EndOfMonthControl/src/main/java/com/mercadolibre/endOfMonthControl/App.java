package com.mercadolibre.endOfMonthControl;

import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mercadolibre.endOfMonthControl.gui.MainWindowController;
import com.mercadolibre.endOfMonthControl.guice.EndOfMonthControlModule;

public class App {
	public static void main(String[] args) {

		LoggerFactory.getLogger(App.class).info("App starting. Loading Guice environment.");

		/*
		 * Guice.createInjector() takes your Modules, and returns a new Injector
		 * instance. Most applications will call this method exactly once, in
		 * their main() method.
		 */
		Injector injector = Guice.createInjector(new EndOfMonthControlModule());

		LoggerFactory.getLogger(App.class).info("Finished loading Guice environment.");
		
		MainWindowController controller = injector.getInstance(MainWindowController.class);
		controller.initWindow();

	}
}
