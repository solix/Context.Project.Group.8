package com.taxi_trouble.game.multiplayer;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import com.badlogic.gdx.physics.box2d.Body;
import com.taxi_trouble.game.model.Snapshot;
import com.taxi_trouble.game.model.Taxi;

public class CoreMultiplayerAdapter {
	
	AndroidMultiplayerInterface androidMultiplayerInterface;	
	SortedMap<String,String> changes = new TreeMap<String,String>();
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
		//System.out.println("SENDING CHANGES");
		if (changes.isEmpty())
			return;
		String message = "CHANGES " + teamId + " " ;
		for( Entry<String,String> e : changes.entrySet()){
			message+= e.getKey() + " " + e.getValue() + " ";
		}
		System.out.println(message);
		
		androidMultiplayerInterface.sendCar(message);
		changes.clear();
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
		System.out.println("CHANGE ADDED");
		//System.out.println("CHANGES: " + changes.toString());
		
	}
	
	public void newRecord(Taxi taxi){
		records.offer(new Snapshot(taxi.getXPosition(), taxi.getYPosition(), taxi.getBody().getAngle(), taxi.getSpeedKMH()));
	
		
	}
	
	public void broadcast(String message){
		androidMultiplayerInterface.broadcast(message);
	}
	



	/**
	 * Searches the record that is taken at (approximately) the same time as the servers information was send.
	 * @param time
	 * the time of the server information
	 * @return
	 * the correct record.
	 */
	public Snapshot getRecord(int time) {
		if (records.size() < 2)
			return records.poll();
		
		
		Snapshot snapshot = null;
		int differenceCurrentSnap = Integer.MAX_VALUE;
		Snapshot nextSnapshot;
		int differenceNextSnap = Integer.MAX_VALUE - 1;
		
		while (differenceNextSnap < differenceCurrentSnap && records.size() > 2){
			if (records.size() < 2)
				return records.poll();
			snapshot = records.poll(); //get oldest record, remove record from queue.
			differenceCurrentSnap = differenceNextSnap;
			nextSnapshot = records.peek(); // get next oldest record, records stays in queue.
			differenceNextSnap = timeDifference(nextSnapshot, time); //get time difference between next oldest record and server time.
		} 
		
		return snapshot;
		
		
		
	}
	/**
	 * invariant: 0 < difference < 60000
	 * 
	 * @param snapshot
	 * A snapshot with a gamestate record
	 * @param serverTime
	 * the server time in milliseconds
	 * @return
	 * the difference in milliseconds between the snapshot time and serverTime
	 */
	private int timeDifference(Snapshot snapshot , Integer serverTime){
		assert(!(snapshot == null));
		assert(!(serverTime == null));
		System.out.println("TIME DIFFERENCE CALLED WITH SNAPSHOT " + snapshot.toString() + " AND TIME " + serverTime);
		int difference = serverTime - snapshot.getTime();
		if (difference < 0)
			difference = 60000 + difference;
		return difference;
	}
	
	
	
	


}
