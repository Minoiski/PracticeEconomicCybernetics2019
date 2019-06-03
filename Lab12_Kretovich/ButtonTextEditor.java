/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.PropertyEditorSupport;

public class ButtonTextEditor extends PropertyEditorSupport{
    @Override
    public String[] getTags() {
	return new String[] { "Check 1", "Check 2", "Check 3" };
    }
    
    /** Convert each of those value names into the actual value. */
    @Override
    public void setAsText(String s) { 
        if (s.equals("Check 1")) setValue(ButtonText.check1);
	else if (s.equals("Check 2")) setValue(ButtonText.check2);
	else if (s.equals("Check 3")) setValue(ButtonText.check3);
	else throw new IllegalArgumentException(s); 
    }
    
    /** This is an important method for code generation. */
    @Override
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("Check 1")) return "Check 1";
	else if (o.equals("Check 2")) return "Check 2";
	else if (o.equals("Check 3")) return "Check 3";
	return null;
    }
}
