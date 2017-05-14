package com.fenyx.ui;

import com.fenyx.api.ScreenConfig;
import com.fenyx.render.RenderAPI;

public final class UIManager {

    private static final UIPool uis = new UIPool();
    private static boolean state = false;
    private static UI current;
    public static boolean draw_bounds;

    public static void setActive(boolean active) {
        state = active;
    }

    public static void destroy(UI ui) {
        ui.onDestroy();

        if (uis.contains(ui))
            uis.remove(ui);
    }

    public static void add(UI ui) {
        uis.add(ui);
    }

    public static void remove(UI ui) {
        uis.remove(ui);
    }

    public static void clear() {
        uis.clear();
    }

    public static int size() {
        return uis.size();
    }

    public static void update() {
        if (!state) {
            return;
        }
        sort();

        for (UI ui : uis.get_all())
            ui.update();
    }

    public static void draw() {
        if (!state) {
            return;
        }
        RenderAPI.setOrtho(ScreenConfig.screen_width, ScreenConfig.screen_height, true);

        for (UI ui : uis.get_all()) {
            ui.draw();
        }
    }

    private static void sort() {
        for (int i = 0; i < size(); i++) {
            for (int j = size() - 2; j >= i; j--) {
                UI ui1 = uis.get(j);
                UI ui2 = uis.get(j + 1);

                if (ui1.layer > ui2.layer)
                    uis.swap(j, j + 1);
            }
        }
    }

    public static boolean is_empty() {
        return uis.is_empty();
    }

    public static void setCurrent(UI ui) {
        if (ui != null) {
            ui.setEnabled(true);
            ui.setActive(true);
            ui.setVisible(true);
        } else {
            current.setEnabled(false);
            current.setActive(false);
            current.setVisible(false);
        }

        current = ui;
    }

    public void onTop(UI ui) {
        for (UI tmp : uis.get_all())
            if (tmp.layer == UI.LAYER_TOP)
                tmp.layer -= 1;
        ui.setLayer(UI.LAYER_TOP);
    }
}
