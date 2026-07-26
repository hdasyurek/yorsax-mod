package com.passwordmod.client.screen;

import com.passwordmod.client.PasswordModClient;
import com.passwordmod.storage.PasswordStorage;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ForgotPasswordScreen extends Screen {
    private TextFieldWidget backupField;
    private ButtonWidget verifyButton;
    private ButtonWidget backButton;
    private String message = "";

    public ForgotPasswordScreen() {
        super(Text.literal("Şifremi Unuttum"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.backupField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 10, 200, 20, Text.literal(""));
        this.backupField.setMaxLength(32);
        this.addDrawableChild(this.backupField);
        this.setInitialFocus(this.backupField);

        this.verifyButton = ButtonWidget.builder(Text.literal("Doğrula ve Sıfırla"), button -> verify())
                .dimensions(centerX - 100, centerY + 20, 200, 20)
                .build();
        this.addDrawableChild(this.verifyButton);

        this.backButton = ButtonWidget.builder(Text.literal("Geri"), button -> {
                    if (this.client != null) {
                        this.client.setScreen(new PasswordScreen());
                    }
                })
                .dimensions(centerX - 100, centerY + 45, 200, 20)
                .build();
        this.addDrawableChild(this.backButton);
    }

    private void verify() {
        String code = this.backupField.getText();
        if (PasswordStorage.verifyBackupCode(PasswordModClient.currentWorld, code)) {
            PasswordStorage.removePassword(PasswordModClient.currentWorld);
            message = "Şifre sıfırlandı! Yeni şifre oluşturun.";
            if (this.client != null) {
                this.client.setScreen(new CreatePasswordScreen());
            }
        } else {
            message = "Hatalı yedek kod!";
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Şifre oluştururken verilen yedek kodu girin."), this.width / 2, this.height / 2 - 35, 0xAAAAAA);
        
        if (!message.isEmpty()) {
            int color = message.contains("sıfırlandı") ? 0x00FF00 : 0xFF0000;
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(message), this.width / 2, this.height / 2 + 75, color);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}