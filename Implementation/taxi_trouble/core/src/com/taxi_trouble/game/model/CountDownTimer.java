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
    private int timeLeftSeconds;

    /**Initializes a new CountDownTimer which will count down to zero
     * for a specified amount of time in seconds.
     * 
     * @param timeSeconds : time in seconds to count down
     */
    public CountDownTimer(int timeSeconds) {
        this.timer = new Timer();
        this.timeLeftSeconds = timeSeconds;
        this.timer.stop();
        this.timer.scheduleTask(new Task() {
            @Override
            public void run() {
                timeLeftSeconds--;
            }
        }, 1, 1, timeLeftSeconds - 1);
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
        return this.timeLeftSeconds;
    }

    /**Retrieves the remaining time in minutes format.
     * E.g. for 100 seconds left the output will be "1:40"
     *
     * @return
     */
    public String getMinutesFormat() {
        int minutes = timeLeftSeconds / 60;
        int seconds = timeLeftSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
