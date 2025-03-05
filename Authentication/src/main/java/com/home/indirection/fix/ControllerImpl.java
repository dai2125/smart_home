package com.home.indirection.fix;

public class ControllerImpl implements Controller {

    private final Model model;
    private final View view;

    ControllerImpl(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void render() {
        view.print(model);
    }
}