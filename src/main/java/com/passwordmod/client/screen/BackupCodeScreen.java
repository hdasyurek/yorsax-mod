package com.passwordmod.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BackupCodeScreen extends Screen {
    private final String backupCode;

    public BackupCodeScreen(String code) {
        super(Text.literal("Yedek Kodunuz"));
        this.backupCode = code;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Anladim"), button -> {
                    if (this.client != null) {
                        this.client.setScreen(null);
                    }
                })
                .dimensions(centerX - 100, centerY + 40, 200, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 40, 0xFFFF00);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Şifreniz oluşturuldu!"), this.width / 2, this.height / 2 - 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Yedek Kodunuz (unutmayin):"), this.width / 2, this.height / 2 + 5, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(backupCode), this.width / 2, this.height / 2 + 20, 0x00FF00);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}