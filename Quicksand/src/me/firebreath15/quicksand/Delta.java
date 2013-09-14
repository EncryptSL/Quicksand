package me.firebreath15.quicksand;

public class Delta {
	
	public boolean ifChangeWasOne(int oldx, int newx){		
		if(newx-oldx==1 || oldx-newx==1 || newx-oldx==-1 || oldx-newx==-1){
			return true;
		}else{
			return false;
		}
	}
}
