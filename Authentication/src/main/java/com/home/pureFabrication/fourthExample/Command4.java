package com.home.pureFabrication.fourthExample;

public abstract class Command4 {

    public Editor editor;
    private String backup;

    Command4() {
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract boolean execute();
}
