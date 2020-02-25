package com.tp.components;

import com.common.CommonUtil;
import com.convert.SwaggerConverter;
import org.apache.commons.lang3.Validate;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TransDocForm {
    private JButton check;
    private JTextField searchUrl;
    private JTextArea result;
    private JPanel context;
    private JPanel searchContext;
    private JLabel urlLabel;
    private JTextField defaultSwaggerContext;
    private JButton searchButton;
    private JLabel swaggerLabel;

    public TransDocForm() {
        searchButton.addActionListener(e -> {
            String searchUrlText = searchUrl.getText();
            String swaggerContext = defaultSwaggerContext.getText();
            if (CommonUtil.isEmpty(searchUrlText) || CommonUtil.isEmpty(swaggerContext)) {
                result.setText("请填写必要的参数");
            }
            String data = getMarkDownStr(swaggerContext, searchUrlText);
            System.out.println(data);
            result.setText(data);
        });
    }

    /**
     * 获取详情
     *
     * @param dataUrl
     * @param searchUrl
     * @return
     */
    public static String getMarkDownStr(String dataUrl, String searchUrl) {
        String data = "";
        try {
            URL remoteSwaggerFile = new URL(dataUrl);
            SwaggerConverter swaggerConverter = SwaggerConverter.from(remoteSwaggerFile, searchUrl).build();
            data = swaggerConverter.toConvertString();
        } catch (Exception e) {
            data = "meet error:" + e.getMessage();
        }
        return data;
    }

    /**
     * how to perform : http://jdev.tw/blog/3823/intellij-idea-gui-designer-swing-form
     *
     * @param args
     */
    public static void main(String[] args) {
        new TransDocForm().showForm();
    }

    public void showForm() {
        JFrame frame = new JFrame("swagger文档生成工具");
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setContentPane(new TransDocForm().context);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
