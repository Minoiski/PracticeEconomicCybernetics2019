/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice2018;

import java.beans.PropertyEditorSupport;

public class LabelTextEditor extends PropertyEditorSupport{
    @Override
    public String[] getTags() {
	return new String[] { "Hi World", "Hello World", "Happy World" };
    }
    
    /** Convert each of those value names into the actual value. */
    @Override
    public void setAsText(String s) { 
        if (s.equals("Hi World")) setValue(LabelText.hiWorld);
	else if (s.equals("Hello World")) setValue(LabelText.helloworld);
	else if (s.equals("Happy World")) setValue(LabelText.happyworld);
	else throw new IllegalArgumentException(s); 
    }
    
    /** This is an important method for code generation. */
    @Override
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("Hi World")) return "Hi World";
	else if (o.equals("Hello World")) return "Hello World";
	else if (o.equals("Happy World")) return "Happy World";
	return null;
    }
    
}
