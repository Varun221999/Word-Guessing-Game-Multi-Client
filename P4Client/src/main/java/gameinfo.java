import java.io.Serializable;
import java.util.ArrayList;

public class gameinfo implements Serializable {
    private static final long serialVersionUID=1L;

    String first_word = "";
    boolean guesses_over = false;
    boolean all_attempts = false;
    boolean won = false;
    boolean won1 = false; //to identify which category user selected or won from
    boolean won2 = false; //to identify which category user selected or won from

    boolean pressed_playagain= false;

    boolean sub_won = false;
    boolean sub_won1 = false; //to identify which category user selected or won from
    boolean sub_won2 = false; //to identify which category user selected or won from


    int count=0;
    boolean pressed_animal ;
    boolean pressed_country = false;
    int index_of= 0;
    boolean pressed_sports = false;
    int guesses = 6;  //these two are temporary here we have to initialize them in gameinfo class
    boolean wrong_guess = false;
    boolean attempts = false;



}
