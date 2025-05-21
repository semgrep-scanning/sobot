package com.sobot.chat.widget.kpswitch.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.FrameLayout;
import com.sobot.chat.widget.kpswitch.IFSPanelConflictLayout;
import com.sobot.chat.widget.kpswitch.IPanelHeightTarget;
import com.sobot.chat.widget.kpswitch.handler.KPSwitchFSPanelLayoutHandler;
import com.sobot.chat.widget.kpswitch.util.ViewUtil;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/kpswitch/widget/KPSwitchFSPanelFrameLayout.class */
public class KPSwitchFSPanelFrameLayout extends FrameLayout implements IFSPanelConflictLayout, IPanelHeightTarget {
    private KPSwitchFSPanelLayoutHandler panelHandler;

    public KPSwitchFSPanelFrameLayout(Context context) {
        super(context);
        init();
    }

    public KPSwitchFSPanelFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public KPSwitchFSPanelFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public KPSwitchFSPanelFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void init() {
        this.panelHandler = new KPSwitchFSPanelLayoutHandler(this);
    }

    @Override // com.sobot.chat.widget.kpswitch.IPanelHeightTarget
    public void onKeyboardShowing(boolean z) {
        this.panelHandler.onKeyboardShowing(z);
    }

    @Override // com.sobot.chat.widget.kpswitch.IFSPanelConflictLayout
    public void recordKeyboardStatus(Window window) {
        this.panelHandler.recordKeyboardStatus(window);
    }

    @Override // com.sobot.chat.widget.kpswitch.IPanelHeightTarget
    public void refreshHeight(int i) {
        ViewUtil.refreshHeight(this, i);
    }
}
