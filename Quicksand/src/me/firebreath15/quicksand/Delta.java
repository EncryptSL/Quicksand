package me.firebreath15.quicksand;

public class Delta {
	
	//This helps check to see if a player moved one block on the arena floor. This helps log correct block data and stops logging useless
	//block data. Also it can help reduce lag.
	public boolean ifChangeWasOne(int oldx, int newx){	
		if(newx-oldx==1 || oldx-newx==1 || newx-oldx==-1 || oldx-newx==-1){
			return true;
		}else{
			return false;
		}
	}
	
	//This helps check to see if a player moved and they did not jump. If a player is running and jumping, we may actually
	//record a block location we don't want. This stops that from happening by only recording blocks broken while running.
	public boolean ifChangeWasZero(int oldx, int newx){
		if(newx-oldx==0 || oldx-newx==0 || newx-oldx==0 || oldx-newx==0){
			return true;
		}else{
			return false;
		}
	}
	
}
