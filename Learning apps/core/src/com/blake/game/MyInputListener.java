package com.blake.game;

import com.badlogic.gdx.Input.TextInputListener;

public class MyInputListener implements TextInputListener {
	   @Override
	   public void input (String text) {
		   Buttons.txtVal = text;
   		Assets.settings.putString("name", Buttons.txtVal);

   		Assets.settings.flush();
   		Assets.name = Assets.settings.getString("name", "No name found.");
   		// HOW THE FUCK DO I REMOVE BUTTONS Buttons.window.removeActor(Buttons.mybutton);
   		//Buttons.window.remove();
	   }

	   @Override
	   public void canceled () {
		   //Give random name?  cancelled event from popup
	   }
	}
