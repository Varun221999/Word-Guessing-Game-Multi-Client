import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import jdk.internal.ref.Cleaner;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    ArrayList<String> sub_contains_animal_name = new ArrayList<>();
    ArrayList<String> sub_contains_country_name = new ArrayList<>();
    ArrayList<String> sub_contains_sports_name = new ArrayList<>();

   String[] contains_animal_name=  {"zebra", "horse", "tiger"};
    String[] contains_Countries_name=  {"france", "germany", "italy"};
    String[] contains_sports_name=  {"hockey", "hurling", "golf"};


    TheServer server;
    private Consumer<Serializable> callback;
    gameinfo info;
    boolean count_second = false;
    boolean count_third = false;
    boolean count_fourth = false;
    boolean winning = false;
    boolean winning1 = false;

    boolean winning2 = false;

    int guesses = 6;  //these two are temporary here we have to initialize them in gameinfo class
    /////we have to fix attempts
    int attempts = 0;
    int attempt1= 0;
    int attempts2=0;
    /////we have to fix attempts

    String contains_name1;

    Server(Consumer<Serializable> call){

        callback = call;
        server = new TheServer();
        server.start();
    }

    Server(){}


    public class TheServer extends Thread{

        TheServer(){}
        public void run() {

            try(ServerSocket mysocket = new ServerSocket(5555);){
                System.out.println("Server is waiting for a client!");


                while(true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();

                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{


        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }
        ClientThread(){}

        public void updateClients(String message) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                }
                catch(Exception e) {}
            }
        }

        public void updateClients1(gameinfo message) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                }
                catch(Exception e) {}
            }
        }

        public boolean animals(boolean pressed_animal)
        {
            if(pressed_animal==true)
            {
                attempts++;

                if(attempts== 1)
                {
                    sub_contains_animal_name.add(contains_animal_name[0]);
                    contains_name1 = contains_animal_name[0];
                    return true;
                }
                if(attempts==2)
                {

                    sub_contains_animal_name.add(contains_animal_name[1]);
                    contains_name1 = contains_animal_name[1];
                    return true;
                }
                if(attempts==3)
                {
                    sub_contains_animal_name.add(contains_animal_name[2]);
                    contains_name1 = contains_animal_name[2];
                    return true;
                }

                count_second = true;
                callback.accept("client: " + count + " pressed: " + "Animals");
                if(info.first_word.length() > 1) {

                    updateClients("Only one letter at a time");
                }
            }
        }

        public boolean country(boolean pressed_country)
        {
            if(pressed_country==true)
            {
                attempt1++; //
                if(attempt1 == 1)
                {
                    sub_contains_country_name.add(contains_Countries_name[0]);

                    contains_name1 = contains_Countries_name[0];

                }
                if(attempt1==2)
                {
                    sub_contains_country_name.add(contains_Countries_name[1]);
                    contains_name1 = contains_Countries_name[1];

                }
                if(attempt1==3)
                {
                    sub_contains_country_name.add(contains_Countries_name[2]);
                    contains_name1 = contains_Countries_name[2];

                }

                count_third= true;
               // contains_animal_name.add("United");
                callback.accept("client: " + count + " pressed: " + "Countries");
                if(info.first_word.length() > 1) {

                    updateClients("Only one letter at a time");
                }
            }
        }

        public boolean sports(boolean pressed_sports)
        {
            if(pressed_sports==true)
            {
                attempts2++;
                if(attempts2 == 1)
                {
                    sub_contains_sports_name.add(contains_sports_name[0]);
                    contains_name1 = contains_sports_name[0];

                }
                if(attempts2==2)
                {
                    sub_contains_sports_name.add(contains_sports_name[1]);

                    contains_name1 = contains_sports_name[1];

                }
                if(attempts2==3)
                {
                    sub_contains_sports_name.add(contains_sports_name[2]);

                    contains_name1 = contains_sports_name[2];

                }

                count_fourth= true;
                // contains_animal_name.add("United");
                callback.accept("client: " + count + " pressed: " + "Sports");
                if(info.first_word.length() > 1) {

                    updateClients("Only one letter at a time");
                }
            }
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

        //    updateClients("new client on server: client #"+count);

            while(true) {
                try {

                    info = (gameinfo) in.readObject();
//////////////this part is for first category///////////
                    if(info.pressed_playagain)
                    {
                        info = new gameinfo();
                        attempts2=0;
                        attempts=0;
                        attempt1=0;
                        winning1=false;
                        winning=false;
                        winning2=false;
                        info.won = false;
                        info.won1 = false;
                        info.won2 = false;

                        // todo: shouldn't this be a new object altogether??
                    }
                    if(info.pressed_animal) {
                        animals(info.pressed_animal);
                        if(info.sub_won2 && info.sub_won1)  //if user plays country and sports first
                        {
                            winning1 = true;
                            winning2 = true;
                        }
                        if(info.sub_won1) //if user plays sports first
                        {
                            winning2 = true;

                        }
                        if(info.sub_won2) //if user plays country first
                        {
                            winning1 = true;

                        }

                    }


                    if(info.pressed_animal ==false && info.pressed_country==true)
                    {
                        count_second=false;
                    }

                    if(count_second==true && !info.first_word.isEmpty()) {

                        String contains_name;

                        if(attempts == 1)
                        {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                //create another array of char here that keep tracks of correct guesses

                                info.index_of = sub_contains_animal_name.get(0).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }//////////////this part is for second category///////////
                            else
                            { if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                     info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            { info.guesses_over = true;
                                count_second = false;       }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0){
                                info.won = true;
                                count_second = false;

                                if(winning2 && winning1)  //if user plays country and sports first
                                {
                                    info.won2 = true;
                                    info.won1 = true;
                                }
                                if(winning2) //if user plays sports first
                                {
                                    info.won1 = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }

                            }//////////////this part is for first category///////////
                        }
                        if(attempts == 2)
                        {


                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {                                //create another array of char here that keep tracks of correct guesses
                                info.index_of = sub_contains_animal_name.get(1).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }//////////////this part is for first category///////////
                            else
                            { if (!contains_name1.contains(info.first_word)) {
                                info.guesses = info.guesses-1;
                                info.wrong_guess = true;
                            }
                            }
                            if(info.guesses == 0)
                            { info.guesses_over = true;
                                count_second = false;       }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0){
                                info.won = true;
                                count_second = false;


                                if(winning2 && winning1)  //if user plays country and sports first
                                {
                                    info.won2 = true;
                                    info.won1 = true;
                                }
                                if(winning2) //if user plays sports first
                                {
                                    info.won1 = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }

                            }//////////////this part is for first category///////////
                        }



                        if(attempts == 3)
                        {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {                                //create another array of char here that keep tracks of correct guesses
                                info.index_of = sub_contains_animal_name.get(2).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }//////////////this part is for first category///////////
                            else
                            { if (!contains_name1.contains(info.first_word)) {
                                info.guesses = info.guesses-1;
                                info.wrong_guess = true;
                            }
                            }
                            if(info.guesses == 0)
                            { info.guesses_over = true;
                                count_second = false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0){
                                info.won = true;
                                count_second = false;


                                if(winning2 && winning1)  //if user plays country and sports first
                                {
                                    info.won2 = true;
                                    info.won1 = true;
                                }
                                if(winning2) //if user plays sports first
                                {
                                    info.won1 = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }
                            }//////////////this part is for first category///////////
                        }


                        if(attempts==3)
                        {
                            info.attempts = true;
                        }

                    }//if condition

/////countries////////////////////////////////////////////////////////////////////////////////////
                   if(info.pressed_country) {
                       country(info.pressed_country);


                       if(info.sub_won && info.sub_won1)  //if user plays animal and sports first
                       {
                           winning = true;
                           winning2 = true;
                       }
                       if(info.sub_won1) //if user plays sports first
                       {
                           winning2 = true;

                       }
                       if(info.sub_won) //if user plays animal first
                       {
                           winning = true;

                       }

                   }
                    if(info.pressed_sports ==true&& info.pressed_country==false)
                    {
                        count_third=false;

                    }


                    //this one fixed the problem





                    if(count_third==true && !info.first_word.isEmpty()) {


                        if(attempt1 == 1) {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                info.index_of = sub_contains_country_name.get(0).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }
                            else
                            {
                                if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                   info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            {
                                info.guesses_over = true;
                                count_third=false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0)
                            {
                                info.won2 = true;
                                count_third=false;



                                if(winning && winning2)  //if user plays animal and sports first
                                {
                                    info.won = true;
                                    info.won1 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning2) //if user plays sport first
                                {
                                    info.won1 = true;
                                }

                            }//////////////this part is for second category///////////
                        }

                        if(attempt1 == 2) {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                info.index_of = sub_contains_country_name.get(1).toUpperCase().indexOf(info.first_word.toUpperCase());
                                // updateClients("Correct Guess Word located at : " + contains_name1.indexOf(info.first_word));
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;


                            }
                            else
                            {
                                if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                    info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            {
                                info.guesses_over = true;
                                //updateClients1(info);
                                count_third=false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0)
                            {
                                info.won2 = true;
                                count_third=false;

                                if(winning && winning2)  //if user plays animal and sports first
                                {
                                    info.won = true;
                                    info.won1 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning2) //if user plays sport first
                                {
                                    info.won1 = true;
                                }
                            }//////////////this part is for second category///////////
                        }


                        if(attempt1 == 3)
                        {


                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {                                //create another array of char here that keep tracks of correct guesses
                                info.index_of = sub_contains_country_name.get(2).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }//////////////this part is for first category///////////
                            else
                            { if (!contains_name1.contains(info.first_word)) {
                                info.guesses = info.guesses-1;
                                info.wrong_guess = true;
                            }
                            }
                            if(info.guesses == 0)
                            { info.guesses_over = true;
                                count_third=false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0){
                                info.won2 = true;
                                count_third=false;


                                if(winning && winning2)  //if user plays animal and sports first
                                {
                                    info.won = true;
                                    info.won1 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning2) //if user plays sport first
                                {
                                    info.won1 = true;
                                }
                            }//////////////this part is for first category///////////
                        }
                        if(attempt1==3)
                        {
                            info.attempts = true;
                        }

                    }

                    ///third category////////////
                    if(info.pressed_sports) {
                        sports(info.pressed_sports);

                        if(info.sub_won2 && info.sub_won)  //if user plays animal and country first
                        {
                            winning = true;
                            winning1 = true;
                        }
                        if(info.sub_won2) //if user plays country first
                        {
                            winning1 = true;

                        }
                        if(info.sub_won) //if user plays animal first
                        {
                            winning = true;

                        }

                    }

                    if(count_fourth==true && !info.first_word.isEmpty()) {


                        if(attempts2 == 1) {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                info.index_of = sub_contains_sports_name.get(0).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }
                            else
                            {
                                if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                    info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            {
                                info.guesses_over = true;
                                count_fourth=false;


                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0)
                            {
                                info.won1 = true;
                                count_fourth=false;

                                if(winning && winning1)  //if user plays animal and country first
                                {
                                    info.won = true;
                                    info.won2 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }


                            }//////////////this part is for third category///////////
                        }

                        if(attempts2 == 2) {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                info.index_of = sub_contains_sports_name.get(1).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;


                            }
                            else
                            {
                                if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                    info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            {
                                info.guesses_over = true;
                                //updateClients1(info);
                                count_fourth=false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0)
                            {
                                info.won1 = true;
                                count_fourth=false;


                                if(winning && winning1)  //if user plays animal and country first
                                {
                                    info.won = true;
                                    info.won2 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }

                            }//////////////this part is for second category///////////
                        }


                        if(attempts2 == 3) {
                            if (contains_name1.toUpperCase().contains(info.first_word.toUpperCase()))
                            {
                                info.index_of = sub_contains_sports_name.get(2).toUpperCase().indexOf(info.first_word.toUpperCase());
                                contains_name1 = contains_name1.replace(info.first_word, "");
                                info.wrong_guess = false;
                            }
                            else
                            { if (!contains_name1.contains(info.first_word)) {
                                    info.guesses = info.guesses-1;
                                    info.wrong_guess = true;
                                }
                            }
                            if(info.guesses == 0)
                            {
                                info.guesses_over = true;
                                //updateClients1(info);
                                count_fourth=false;
                            }
                            //so we are replacing each correct guess with "". If the user guess all the words correctly than the length
                            // will be 0
                            if(contains_name1.length()==0)
                            {
                                info.won1 = true;
                                count_fourth=false;


                                if(winning && winning1)  //if user plays animal and country first
                                {
                                    info.won = true;
                                    info.won2 = true;
                                }
                                if(winning) //if user plays animal first
                                {
                                    info.won = true;

                                }
                                if(winning1) //if user plays country first
                                {
                                    info.won2 = true;
                                }


                            }//////////////this part is for second category///////////
                        }
                        if(attempts2==3)
                        {
                            info.attempts = true;
                        }

                    }


                        updateClients1(info);
                    }
                catch(Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    updateClients("Client #"+count+" has left the server!");
                    clients.remove(this);
                    break;
                }
            }
        }//end of run


    }//end of client thread
}






