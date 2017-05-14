package com.fenyx.ui;

import com.fenyx.render.Color;
import com.fenyx.render.RenderAPI;

public class UIMenu extends UIPanel {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected int orientation;

    public UIMenu(int orientation) {
        setBackgroundColor(Color.gray);

        this.orientation = orientation;
    }

    public void onDraw() {
        RenderAPI.drawStraight(this.x, getBoundHeight(), this.width + 1, 0, this.background_color.mix(Color.white));

        if (this.orientation == 1) {
            RenderAPI.drawStraight(getBoundWidth(), this.y, 0, this.height, this.background_color.mix(Color.white));
        }
    }

    public final void add(UIMenuElement e) {
        if (this.orientation == 0) {
            if (!this.elements.is_empty()) {
                UI last = this.elements.get_last();
                e.setPosition(last.getX() + last.getWidth() + 2, 0);
            }
        } else if ((this.orientation == 1)
                && (!this.elements.is_empty())) {
            UI last = this.elements.get_last();
            e.setPosition(0, last.getBoundHeight());

            setHeight(e.getBoundHeight());

            int max_width = this.elements.get(0).getWidth();

            for (UI e_tmp : getElements())
                if (max_width < e_tmp.getWidth())
                    max_width = e_tmp.getWidth();
            for (UI e_tmp : getElements()) {
                e_tmp.setWidth(max_width);
            }
            if (max_width < e.getWidth()) {
                max_width = e.getWidth();
            }
            e.setWidth(max_width);
            setWidth(max_width);
        }

        super.add(e);
    }
}
