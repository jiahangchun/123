package com.tp.components.v1;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TransDocForm extends DialogWrapper {

    private String projectName;
    private TransDocSwing transDocSwing = new TransDocSwing();

    public TransDocForm(@Nullable Project project) {
        super(project);
        setTitle("文档转换小工具");
        this.projectName = project.getName();
        init();
    }

    @Override
    protected JComponent createNorthPanel() {
        return transDocSwing.initNorth();
    }

    @Override
    protected JComponent createSouthPanel() {
        return transDocSwing.initSouth();
    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return transDocSwing.initCenter();

    }
}
