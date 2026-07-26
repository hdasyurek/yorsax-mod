package com.passwordmod.client.screen;

import com.passwordmod.client.PasswordModClient;
import com.passwordmod.storage.PasswordStorage;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class PasswordScreen extends Screen {
    private TextFieldWidget passwordField;
    private ButtonWidget loginButton;
    private ButtonWidget forgotButton;
    private ButtonWidget exitButton;
    private String errorMessage = "";

    public PasswordScreen() {
        super(Text.literal("Dünya Şifresi"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.passwordField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 20, 200, 20, Text.literal(""));
        this.passwordField.setMaxLength(32);
        this.addDrawableChild(this.passwordField);
        this.setInitialFocus(this.passwordField);

        this.loginButton = ButtonWidget.builder(Text.literal("Giriş Yap"), button -> attemptLogin())
                .dimensions(centerX - 100, centerY + 10, 95, 20)
                .build();
        this.addDrawableChild(this.loginButton);

        this.forgotButton = ButtonWidget.builder(Text.literal("Şifremi Unuttum"), button -> {
                    if (this.client != null) {
                        this.client.setScreen(new ForgotPasswordScreen());
                    }
                })
                .dimensions(centerX + 5, centerY + 10, 95, 20)
                .build();
        this.addDrawableChild(this.forgotButton);

        this.exitButton = ButtonWidget.builder(Text.literal("Oyundan Çık"), button -> {
                    if (this.client != null) {
                        this.client.world.disconnect();
                    }
                })
                .dimensions(centerX - 100, centerY + 40, 200, 20)
                .build();
        this.addDrawableChild(this.exitButton);
    }

    private void attemptLogin() {
        String input = this.passwordField.getText();
        if (PasswordStorage.verifyPassword(PasswordModClient.currentWorld, input)) {
            PasswordModClient.authenticated = true;
            if (this.client != null) {
                this.client.setScreen(null);
            }
        } else {
            this.errorMessage = "Hatalı şifre!";
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 60, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Bu dünya şifre korumalidir."), this.width / 2, this.height / 2 - 45, 0xAAAAAA);
        
        if (!errorMessage.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(errorMessage), this.width / 2, this.height / 2 + 70, 0xFF0000);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}