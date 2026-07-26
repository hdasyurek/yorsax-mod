package com.passwordmod.client.screen;

import com.passwordmod.client.PasswordModClient;
import com.passwordmod.storage.PasswordStorage;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class CreatePasswordScreen extends Screen {
    private TextFieldWidget passwordField;
    private TextFieldWidget confirmField;
    private ButtonWidget createButton;
    private String errorMessage = "";
    private String backupCode = "";

    public CreatePasswordScreen() {
        super(Text.literal("Şifre Oluştur"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.passwordField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 30, 200, 20, Text.literal(""));
        this.passwordField.setMaxLength(32);
        this.addDrawableChild(this.passwordField);
        this.setInitialFocus(this.passwordField);

        this.confirmField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY - 5, 200, 20, Text.literal(""));
        this.confirmField.setMaxLength(32);
        this.addDrawableChild(this.confirmField);

        this.createButton = ButtonWidget.builder(Text.literal("Şifre Oluştur"), button -> createPassword())
                .dimensions(centerX - 100, centerY + 25, 200, 20)
                .build();
        this.addDrawableChild(this.createButton);
    }

    private void createPassword() {
        String pass = this.passwordField.getText();
        String confirm = this.confirmField.getText();

        if (pass.isEmpty()) {
            this.errorMessage = "Şifre boş olamaz!";
            return;
        }

        if (!pass.equals(confirm)) {
            this.errorMessage = "Şifreler eşleşmiyor!";
            return;
        }

        this.backupCode = PasswordStorage.setPassword(PasswordModClient.currentWorld, pass);
        PasswordModClient.authenticated = true;
        if (this.client != null) {
            this.client.setScreen(new BackupCodeScreen(backupCode));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 65, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Bu dünya için yeni bir şifre oluşturun."), this.width / 2, this.height / 2 - 50, 0xAAAAAA);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Yeni Şifre"), this.width / 2 - 100, this.height / 2 - 42, 0xCCCCCC);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Tekrar"), this.width / 2 - 100, this.height / 2 - 17, 0xCCCCCC);
        
        if (!errorMessage.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(errorMessage), this.width / 2, this.height / 2 + 55, 0xFF0000);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}