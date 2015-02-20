package com.blake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Buttons implements InputProcessor { // InputAdapter also, override
	// the methods you need

	static Stage stage;
	static Stage bstage;
	static TextButton mybutton;
	TextButtonStyle textButtonStyle;
	BitmapFont font;
	static Skin skin;
	TextureAtlas buttonAtlas;

	static String txtVal;
	static Window window;
	MyInputListener listener = new MyInputListener();
	private TextField textField;

	Buttons() {

		stage = new Stage();
		GameScreen.multiplexer.addProcessor(stage);
		GameScreen.multiplexer.addProcessor(this);
		font = new BitmapFont();
		skin = new Skin();
		TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("pics/load.png")));
		TextureRegionDrawable backDrawable = new TextureRegionDrawable(region);
		WindowStyle mywind = new WindowStyle(new BitmapFont(), Color.WHITE, backDrawable);
		Window window = new Window("my window", mywind);

		window.setWidth(Gdx.graphics.getWidth() / 2);
		window.setHeight(Gdx.graphics.getHeight() / 3);
		window.setPosition(600, 400);

		// skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/packs.atlas"));
		// Sprite blake = buttonAtlas.createSprite("button");
		// skin.add("button1", Assets.fred);
		skin.addRegions(buttonAtlas);

		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("button");
		textButtonStyle.down = skin.getDrawable("button");
		textButtonStyle.checked = skin.getDrawable("button");

		mybutton = new TextButton("Enter Name", textButtonStyle);
		mybutton.setPosition(200, 200);
		mybutton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// multiplexer.addProcessor(GameScreen.buttons);

				Gdx.input.setOnscreenKeyboardVisible(true);
				Gdx.input.getTextInput(new MyInputListener(), "Your Name", "", "Enter your name.");

			}
		});

		TextFieldStyle style = new TextFieldStyle();
		style.fontColor = Color.RED;
		style.font = new BitmapFont();

		textField = new TextField("Enter your name", style);
		textField.setMaxLength(13);
		// textField.setBlinkTime(1.5f);
		textField.setPosition(40, 75);
		textField.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {

				txtVal = textField.getText();
				System.out.println(txtVal);
				Assets.settings.putString("name", txtVal);
				Assets.settings.flush();
				Assets.name = Assets.settings.getString("name", "No name found.");
			}
		});

		TextField texfield = new TextField("HEY IME HEREREREE", style);
		texfield.setMessageText("Click here!@@@@!!");
		texfield.setBlinkTime(1.5f);
		texfield.setAlignment(Align.center);

		window.add(texfield);
		window.add(mybutton);
		stage.addActor(window);
		stage.addActor(textField);
		stage.addActor(texfield);

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == (Keys.ENTER)) {
			System.out.println("enter");
			Gdx.input.setOnscreenKeyboardVisible(false);

		}
		if (keycode == (Keys.K)) {
			System.out.println("enter");
			Gdx.input.setOnscreenKeyboardVisible(false);

		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		System.out.println("x:" + screenX + " y:" + screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		System.out.println("scrolling");
		return false;
	}
}
