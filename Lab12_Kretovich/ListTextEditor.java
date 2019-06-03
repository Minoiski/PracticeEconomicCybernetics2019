/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.PropertyEditorSupport;


public class ListTextEditor extends PropertyEditorSupport{
    @Override
    public String[] getTags() {
	return new String[] { "1.", "a)", "a." };
    }
    
    /** Convert each of those value names into the actual value. */
    @Override
    public void setAsText(String s) { 
        if (s.equals("1.")) setValue(ListText.first);
	else if (s.equals("a)")) setValue(ListText.second);
	else if (s.equals("a.")) setValue(ListText.third);
	else throw new IllegalArgumentException(s); 
    }
    
    /** This is an important method for code generation. */
    @Override
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("1.")) return "1.";
	else if (o.equals("a)")) return "a)";
	else if (o.equals("a.")) return "a.";
	return null;
    }
}
