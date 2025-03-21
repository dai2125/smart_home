package com.home.pureFabrication.fourthExample;

public class Command2 {

    public Editor editor;
    private String backup;

    Command2(Editor editor) {
        this.editor = editor;
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public boolean execute() {
        return true;
    }
}
