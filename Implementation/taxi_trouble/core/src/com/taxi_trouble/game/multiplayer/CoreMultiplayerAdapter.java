package com.taxi_trouble.game.multiplayer;

import java.util.Map.Entry;
import java.util.SortedMap;
import com.taxi_trouble.game.model.Taxi;

public class CoreMultiplayerAdapter {
	
	AndroidMultiplayerInterface androidMultiplayerInterface;
	int stateId = 0;	
	SortedMap<String,String> changes;
	
	public CoreMultiplayerAdapter(AndroidMultiplayerInterface multiplayerInterface){
		this.androidMultiplayerInterface = multiplayerInterface;
	}
	
	
	public void sendCar(String message){
		message = stateId + " " + message;
		androidMultiplayerInterface.sendCar(message);		
	}
	
	
	/**
	 * Sends the changes in the change object to the host.
	 */
	public void sendChanges(){
		String message = stateId + " ";
		for( Entry<String,String> e : changes.entrySet()){
			message.concat(e.getKey() + " " + e.getValue() + " ");
		}
		androidMultiplayerInterface.sendCar(message);
	}
	
	/**
	 * If a user causes a change of the game state, this change is added to the change
	 * object and is send the next time sendChanges() is called.
	 * @param key
	 * the name of the value that changed.
	 * @param value
	 * the new value.
	 */
	public void newChange(String key, String value){
		changes.put(key, value);
	}
	
	
	
	


}
