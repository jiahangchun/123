package com.tp.components.test;

import com.common.CommonUtil;
import com.convert.SwaggerConverter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TransDocForm extends DialogWrapper {
    //下
    private JButton searchButton;
    private JTextArea result;
    private JPanel resultPanel;

    //上
    private JTextField defaultSwaggerContext;
    private JTextField searchUrl;
    private JLabel urlLabel;
    private JLabel swaggerLabel;
    private JPanel searchContext;
    public JPanel allContext;

    public TransDocForm(@Nullable Project project) {
        super(project);
        searchButton.addActionListener(e -> {
            String searchUrlText = searchUrl.getText();
            String swaggerContext = defaultSwaggerContext.getText();
            if (CommonUtil.isEmpty(searchUrlText) || CommonUtil.isEmpty(swaggerContext)) {
                result.setText("请填写必要的参数");
            }
            result.setText("请稍等，我们正在查询数据。你知道的....swagger 很慢的。");
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
     * 展示
     */
    public void showForm() {
        JFrame frame = new JFrame("swagger文档生成工具");
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setContentPane(this.allContext);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    /**
//     * how to perform : http://jdev.tw/blog/3823/intellij-idea-gui-designer-swing-form
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("swagger文档生成工具");
////        frame.setPreferredSize(new Dimension(500, 300));
//        frame.setContentPane(new TransDocForm().allContext);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
////        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }


    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return this.allContext;
    }
}
