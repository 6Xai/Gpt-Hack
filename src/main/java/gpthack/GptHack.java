package gpthack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GptHack implements ClientModInitializer {
	public static final String MOD_ID = "gpt-hack";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyBinding toggleGuiKey; // Key binding for toggling the GUI
	private static boolean isGuiOpen = false; // Track if the GUI is open

	@Override
	public void onInitializeClient() {
		// Register the keybinding
		toggleGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.gpthack.toggle_gui", // Translation key for the keybinding
				GLFW.GLFW_KEY_RIGHT_CONTROL, // Set to Right Control key
				"category.gpthack" // Key binding category
		));

		// Listen for client ticks to detect key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleGuiKey.wasPressed()) {
				isGuiOpen = !isGuiOpen; // Toggle the state of the GUI
				if (isGuiOpen) {
					// Open the GUI
					client.setScreen(new GptHackScreen());
				} else {
					// Close the GUI
					client.setScreen(null);
				}
			}
		});

		LOGGER.info("GptHack mod initialized!");
	}

	// Inner class to define the custom GUI screen
	public static class GptHackScreen extends Screen {
		protected GptHackScreen() {
			super(Text.literal("GptHack GUI"));
		}

		@Override
		public void render(DrawContext context, int mouseX, int mouseY, float delta) {
			// Render the background
			this.renderBackground(context, mouseX, mouseY, delta);

			// Draw centered text
			context.drawCenteredTextWithShadow(
					this.textRenderer,
					"Hello from GptHack!",
					this.width / 2,
					this.height / 2 - 10,
					0xFFFFFF // White color
			);

			super.render(context, mouseX, mouseY, delta);
		}

		@Override
		public boolean shouldPause() {
			return false; // Do not pause the game when the GUI is open
		}
	}
}
