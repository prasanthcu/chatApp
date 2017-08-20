package com.mychat.prasanth;

/**
 * Created by prashanth on 31-07-2017.
 */

public class InstantMessage {
    String message;
    String author;

   public InstantMessage(String message,String author){
       this.message = message;
       this.author = author;
   }

   public  InstantMessage(){

   }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
