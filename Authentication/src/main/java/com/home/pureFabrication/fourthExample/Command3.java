package com.home.pureFabrication.fourthExample;

public abstract class Command3 {

    public Editor editor;
    private String backup;

    Command3(Editor editor) {
        this.editor = editor;
    }

    void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

}
