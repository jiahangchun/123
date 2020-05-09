package com.tp.components.v1;

import com.tp.components.v1.common.JMCommonUtil;
import com.tp.components.v1.convert.SwaggerConverter;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class TransDocSwing {
    private JPanel north = new JPanel();
    private JPanel center = new JPanel();
    private JPanel south = new JPanel();

    public JPanel initNorth() {
        //定义表单的标题部分，放置到IDEA会话框的顶部位置
        JLabel title = new JLabel("转换工具");
        title.setFont(new Font("微软雅黑", Font.PLAIN, 26)); //字体样式
        title.setHorizontalAlignment(SwingConstants.CENTER); //水平居中
        title.setVerticalAlignment(SwingConstants.CENTER); //垂直居中
        north.add(title);
        return north;
    }

    public JPanel initCenter() {
        center.setPreferredSize(new Dimension(500, 300));
        center.setLayout(new BorderLayout(0, 0));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        center.add(northPanel, BorderLayout.NORTH);
        JTextField dataSource = new JTextField();
        dataSource.setText("http://localhost:8080/platform/v2/api-docs");
        northPanel.add(dataSource, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        JButton search = new JButton();
        search.setText("查询");
        northPanel.add(search, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JTextField searchUrl = new JTextField();
        searchUrl.setText("/crm/shipId/get/default/shipId");
        northPanel.add(searchUrl, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        center.add(panel3, BorderLayout.CENTER);
        JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        JTextArea result = new JTextArea();
        scrollPane1.setViewportView(result);


        search.addActionListener(x -> {
            String searchUrlText = searchUrl.getText();
            String swaggerContext = dataSource.getText();
            if (JMCommonUtil.isEmpty(searchUrlText) || JMCommonUtil.isEmpty(swaggerContext)) {
                result.setText("请填写必要的参数");
            }
            result.setText("请稍等，我们正在查询数据。你知道的....swagger 很慢的。");
            String data = SwaggerConverter.getMarkDownStr(swaggerContext, searchUrlText);
            System.out.println(data);
            result.setText(data);
        });
        return center;
    }

    public JPanel initSouth() {
        return south;
    }

}
