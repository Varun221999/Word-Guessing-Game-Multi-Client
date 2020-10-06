import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;
    gameinfo info;
    gameinfo info1;
    boolean contains;
    boolean contains1;
    boolean contains2;
    private Consumer<Serializable> callback;
    Client() { }
    Client(Consumer<Serializable> call){

        callback = call;
    }

    public void run() {

        try {
            socketClient= new Socket("127.0.0.1",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}

        while(true) {

            try {
               // String data =  in.readObject().toString();
                 info1=  (gameinfo) in.readObject();    //reading values coming back from server
//                callback.accept("Player # "+ info1.count + "\n"); //todo: maybe uncomment needed
//                gameinfo info1 = (gameinfo) in.readObject();

                if(info1.won)
                {
                    contains = info1.won;
                    info1.sub_won = true;
                    callback.accept(

                                        "guesses left: " + info1.guesses + "\n"
                                        + "Correct Guess Word located at : " + info1.index_of + "\n"
                                     +     "You Won Animals Round " + "\n"
                    ); //printing

                }

                if(info1.won1)
                {
                    contains2 = info1.won1;
                    callback.accept(

                            "guesses left: " + info1.guesses + "\n"
                                    + "Correct Guess Word located at : " + info1.index_of + "\n"
                                    +     "You Won sports Round " + "\n"
                    ); //printing

                }


                if(info1.won2)
                {
                    System.out.println(info1.won  + "\n");
                    System.out.println(info1.won2  + "\n");
                     contains1 = info1.won2;
                     info1.sub_won2 = true;
                    callback.accept(


                            "guesses left: " + info1.guesses + "\n"
                                    + "Correct Guess Word located at : " + info1.index_of + "\n"
                                    +     "You Won Country Round " + "\n"
                    ); //printing

                }
                if(info1.guesses<=6 &&info1.index_of>=0 && !info1.first_word.isEmpty() && !info1.wrong_guess ){ //&&!info1.won2 &&!info1.won1&&!info1.won) {
                    callback.accept(
                            "guesses left: " + info1.guesses + "\n" +
                                     "Correct Guess Word located at : " + info1.index_of + "\n"
                    ); //printing
                }

                if(info1.wrong_guess) {
                    callback.accept(
                            "Wrong Guess" + "\n" +
                                    "guesses left: " + info1.guesses + "\n"
                    ); //printing
                }

                //   callback.accept(data);  //printing
            }
            catch(Exception e) {}
        }

    }

    public void send(String data) {
        info = new gameinfo();
        try {
             info.first_word = data;
             info.guesses = info1.guesses;
            info.won = info1.won;
            info.won1 = info1.won1;
            info.won2 = info1.won2;

            out.writeObject(info); //writing back to the server

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void send1(Boolean data) {
        info= new gameinfo();
        try {
            info.pressed_animal = data;
            info.pressed_country = false;
            info.pressed_sports = false;

            if(contains1 && contains2)
            { info.sub_won2 =true;
             info.sub_won1 =true;
            }
            else
            {
                if(contains1)
                {
                    info.sub_won2 =true;
                }
                if(contains2)
                {
                    info.sub_won1 =true;

                }
            }
            out.writeObject(info); //writing back to the server

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void send2(Boolean data) {
        info= new gameinfo();
        try {
            info.pressed_animal = false;
            info.pressed_country = data;
            info.pressed_sports = false;

            if(contains && contains2)
            { info.sub_won =true;
                info.sub_won2 =true;
            }
            else
            {
                if(contains)
                {
                    info.sub_won =true;
                }
                if(contains2)
                {
                    info.sub_won2 =true;

                }
            }
            out.writeObject(info); //writing back to the server

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void send3(Boolean data) {

        info= new gameinfo();
        try {
            info.pressed_animal = false;
            info.pressed_country = false;
            info.pressed_sports = data;

            if(contains && contains1)
            { info.sub_won =true;
                info.sub_won2 =true;
            }
            else
            {
                if(contains)
                {
                    info.sub_won =true;
                }
                if(contains1)
                {
                    info.sub_won2 =true;

                }
            }

            out.writeObject(info); //writing back to the server

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void send_playagain() {

        info= new gameinfo();
        try {

                info.pressed_playagain=true;
                contains = false;
                contains1 = false;
                contains2 = false;
            out.writeObject(info); //writing back to the server

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
