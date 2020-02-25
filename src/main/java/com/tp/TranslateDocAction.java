package com.tp;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.tp.components.TransDocForm;

public class TranslateDocAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        new TransDocForm().showForm();
    }
}
