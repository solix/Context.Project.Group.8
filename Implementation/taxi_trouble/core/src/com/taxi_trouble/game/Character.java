package com.taxi_trouble.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
//.
public class Character {

    private Sprite standing;
    private Sprite walking1;
    private Sprite walking2;

    public Character(Sprite standing, Sprite walking1, Sprite walking2) {
        this.standing = standing;
        this.walking1 = walking1;
        this.walking2 = walking2;
    }

    public Sprite getStanding() {
        return standing;
    }

    public void setStanding(Sprite standing) {
        this.standing = standing;
    }

    public Sprite getWalking1() {
        return walking1;
    }

    public void setWalking1(Sprite walking1) {
        this.walking1 = walking1;
    }

    public Sprite getWalking2() {
        return walking2;
    }

    public void setWalking2(Sprite walking2) {
        this.walking2 = walking2;
    }

}
