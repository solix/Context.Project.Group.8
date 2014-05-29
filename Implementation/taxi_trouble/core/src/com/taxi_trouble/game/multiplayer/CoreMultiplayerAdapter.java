package com.taxi_trouble.game.multiplayer;

import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import com.badlogic.gdx.physics.box2d.Body;
import com.taxi_trouble.game.model.Snapshot;
import com.taxi_trouble.game.model.Taxi;

public class CoreMultiplayerAdapter {
	
	AndroidMultiplayerInterface androidMultiplayerInterface;	
	SortedMap<String,String> changes;
	CircularFifoQueue<Snapshot> records = new CircularFifoQueue<Snapshot>(100);
	private int teamId;
	
	public CoreMultiplayerAdapter(AndroidMultiplayerInterface multiplayerInterface){
		this.androidMultiplayerInterface = multiplayerInterface;
	}
	
	
	
	public void setTeamId(int id){
		this.teamId = id;
	}
	
	/**
	 * Sends the changes in the change object to the host.
	 */
	public void sendChanges(){
		String message = "Changes " + teamId + " " ;
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
	
	public void newRecord(Taxi taxi){
		records.offer(new Snapshot(taxi.getXPosition(), taxi.getYPosition(), taxi.getBody().getAngle(), taxi.getSpeedKMH()));
	
		
	}
	
	public void broadcast(String message){
		androidMultiplayerInterface.broadcast(message);
	}
	
	
	
	


}
