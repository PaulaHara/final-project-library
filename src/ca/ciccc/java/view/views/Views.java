package ca.ciccc.java.view.views;

import ca.ciccc.java.controller.LibraryController;
import ca.ciccc.java.view.readers.InputReader;
import ca.ciccc.java.view.readers.OutputReader;

/**
 * @author paula on 23/04/18.
 */
public abstract class Views {
    protected InputReader inputReader = new InputReader();
    protected OutputReader outputReader = new OutputReader();

    /**
     * View to insert a new data in the DB
     */
    public abstract void addView(LibraryController controller);

    /**
     * View to remove a data from the DB
     */
    public abstract void removeView(LibraryController controller);
}
