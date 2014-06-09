package com.taxi_trouble.game.model;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**A timer which counts down from a specified time in seconds
 * to zero. Initially the timer is frozen and should be started
 * to initialize the countdown.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class CountDownTimer {
    private Timer timer;
    private int timeleftSeconds;

    /**Initializes a new CountDownTimer which will count down to zero
     * for a specified amount of time in seconds.
     * 
     * @param timeSeconds : time in seconds to count down
     */
    public CountDownTimer(int timeSeconds) {
        this.timer = new Timer();
        this.timeleftSeconds = timeSeconds;
        this.timer.stop();
        this.timer.scheduleTask(new Task() {
            @Override
            public void run() {
                timeleftSeconds--;
            }
        }, 1, 1, timeleftSeconds - 1);
    }

    /**Starts the timer and continues the countdown.
     *
     */
    public void startTimer() {
        this.timer.start();
    }

    /**Stops the timer if the countdown was started.
     *
     */
    public void stopTimer() {
        this.timer.stop();
    }

    /**Retrieves the remaining amount of time in seconds.
     *
     * @return remaining time
     */
    public int getTimeRemaining() {
        return this.timeleftSeconds;
    }
}
