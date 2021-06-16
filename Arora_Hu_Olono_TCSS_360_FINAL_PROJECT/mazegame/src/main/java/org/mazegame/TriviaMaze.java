/*
 * Group Project Trivia Maze
 * Fall 2020
 */
package org.mazegame;

import java.awt.EventQueue;

/**
 * The driver for the maze game.
 * @author olono_arora_zixuan
 *
 */
public class TriviaMaze {
	/**
	 * The main class that acts as the initializer for the game.
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuiMain frame = new GuiMain();
            	frame.start();
            }
        });
	}
}
