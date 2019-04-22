/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Cassu
 */
public class ModelList extends AbstractListModel<Object> {
        
    public static final String MODELS = "models";
    private ArrayList<Object> model;
    
    public ModelList(){
        model = new ArrayList<Object>();
    }

    @Override
    public int getSize(){
        return model.size();
    }
    
    @Override
    public Object getElementAt(int index) {
        return model.get(index);
    }
    
    public void add(Object x){
        if(model.add(x)){
           fireContentsChanged(MODELS, 0, model.size());
        }
    }
    
    public void add(Object[] x){
        
        for(Object y : x){
            if(model.add(y)){
               fireContentsChanged(MODELS, 0, model.size());
            }
        }
    }
}
