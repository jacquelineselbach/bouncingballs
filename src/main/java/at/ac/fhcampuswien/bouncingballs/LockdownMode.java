package at.ac.fhcampuswien.bouncingballs;


// ---------------------

// THIS CLASS IS NOT IN USE - TO BE DELETED BEFORE THE OFFICIAL SUBMISSION !!!!!!
// also all the references (which are under comment rn as well)

// ---------------------

public class LockdownMode {

    public static void lockdown (){


        uiSettingsController.getoptLockdown();
        //Ball.getSPEED();
        //double  ballspeed = Ball.getSPEED();



        if (uiSettingsController.getoptLockdown() == true){

            //Balls don't move... kinda self explanatory

            if (false){//ballspeed > 0){
                //Ball.setSPEED(0);
            }


        }
        else { //Ball.setSPEED(ballspeed);
        }
    }

}
