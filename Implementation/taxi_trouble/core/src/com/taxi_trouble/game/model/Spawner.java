package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Spawner {
    private List<Vector2> passengerspawnpoints;
    private List<Vector2> taxispawnpoints;
    private List<Vector2> destinationpoints;
    
    public Spawner() {
        passengerspawnpoints = new ArrayList<Vector2>();
        taxispawnpoints = new ArrayList<Vector2>();
        destinationpoints = new ArrayList<Vector2>();
    }
    
    public void addPassenger(Vector2 vec){
        passengerspawnpoints.add(vec);
    }
    
    public void addTaxi(Vector2 vec){
        taxispawnpoints.add(vec);
    }
    
    public void addDestination(Vector2 vec) {
        destinationpoints.add(vec);
    }
    
    public Passenger spawnPass(World world){
        int random = (int) (Math.abs(Math.random() * passengerspawnpoints.size() - 1));
        Vector2 position = passengerspawnpoints.get(random);
        //position gives the lower left corner of the tile, so we move him to the center of the tile
        //with the following code.
        position.add(1f,1f);
        Passenger pass = new Passenger(world, 2, 2, position);
        pass.setSprite(new Sprite(new Texture("sprites/characters/character-1-standing.png")));
        return pass;
    }
}
