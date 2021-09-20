/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorui;

/**
 *
 * @author roshanantonyarunkumar
 */
public class Person {
    private String name;
    private String username;
    private int id;
    
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    
    public String getName(){
        return name;
    }
    
    public String getUsername(){
        return username;
    }
    
    public int getID(){
        return id;
    }
}
