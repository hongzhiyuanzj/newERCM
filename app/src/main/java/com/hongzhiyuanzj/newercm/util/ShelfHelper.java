package com.hongzhiyuanzj.newercm.util;

import android.util.Log;

import com.hongzhiyuanzj.newercm.entity.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by hongzhiyuanzj on 2017/5/24.
 */
public class ShelfHelper {

    public static boolean containShelf(String bookid){

        List<Shelf> shelfs = Prefer.getShelf();
        if(shelfs==null){
            return false;
        }
        for(int i=0;i<shelfs.size();i++){
            if(shelfs.get(i).getBookid().equals(bookid)){
                return true;
            }
        }
        return false;

    }

    public static void deleteShelf(String bookid) {
        List<Shelf> shelfs = Prefer.getShelf();
        for(int i=0;i<shelfs.size();i++){
            if(shelfs.get(i).getBookid().equals(bookid)){
                shelfs.remove(shelfs.get(i));
                Prefer.saveShelf(shelfs);
                return;
            }
        }

    }

    public static void addShelf(String id, String name, String author, String imgpath){
        List<Shelf> shelfs = Prefer.getShelf()==null?new ArrayList<Shelf>():Prefer.getShelf();
        Shelf shelf = new Shelf();
        shelf.setAuthor(author);
        shelf.setBookname(name);
        shelf.setBookid(id);
        shelf.setImgPath(imgpath);

        shelfs.add(shelf);
        Prefer.saveShelf(shelfs);
    }

    public static List<Shelf> getShelf(){

        return Prefer.getShelf();
    }
}
