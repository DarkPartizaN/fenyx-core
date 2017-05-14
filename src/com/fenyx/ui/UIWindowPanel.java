package com.fenyx.ui;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;

public class UIWindowPanel
        extends UIPanel {

    private final UIWindowLabel uiWindowLabel;

    public UIWindowPanel() {
        setColor(Color.black);
        setBackgroundColor(Color.gray);

        this.uiWindowLabel = new UIWindowLabel();

        add(this.uiWindowLabel);
    }

    public final void setTitle(String title) {
        this.uiWindowLabel.setTitle(title);
    }

    public void onResize() {
        this.uiWindowLabel.setWidth(this.width);
    }

    public void onDraw() {
        RenderAPI.drawRect(this.x, this.y, this.width, this.height, this.background_color.mix(Color.white));
    }

    private class UIWindowLabel extends UIPanel {

        private final UIText uiTextTitle;
        private final UIMenuElement uiButtonClose;

        public UIWindowLabel() {
            setBackgroundColor(Color.gray.mix(Color.white));

            this.uiTextTitle = new UIText("Window");
            this.uiTextTitle.setColor(Color.white);
            this.uiTextTitle.setPosition(2, 0);

            this.uiButtonClose = new UIMenuElement() {
                public void onClick() {
                    UIWindowPanel.this.uiWindowLabel.getParent().close();
                }
            };
            this.uiButtonClose.setColor(Color.red.mix(Color.white));
            this.uiButtonClose.setBackgroundColor(Color.red);
            this.uiButtonClose.setText("x");
            this.uiButtonClose.setSize(this.font.getHeight(), this.font.getHeight());
            this.uiButtonClose.centerText();

            add(this.uiTextTitle);
            add(this.uiButtonClose);

            setHeight(this.font.getHeight() + 1);
        }

        public final void setTitle(String title) {
            this.uiTextTitle.setText(title);
        }

        public void onResize() {
            this.uiButtonClose.setPosition(this.width - this.uiButtonClose.width, this.y);
        }
    }
}
