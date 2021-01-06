package at.ac.fhcampuswien.bouncingballs;

public class LockdownMode {

    public static void lockdown (){


        uiSettingsController.getoptLockdown();
        Ball.getSPEED();
        double  ballspeed = Ball.getSPEED();



        if (uiSettingsController.getoptLockdown() == true){

            //Balls don't move... kinda self explanatory

            if (ballspeed > 0){
                Ball.setSPEED(0);
            }


        }
        else { Ball.setSPEED(ballspeed);
        }
    }

}
