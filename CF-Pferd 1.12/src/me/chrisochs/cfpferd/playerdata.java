package me.chrisochs.cfpferd;

import java.util.Random;
import java.util.UUID;

import playerdata.PlayerdataAPI;

public class playerdata {
	public playerdata(){}
	
	private String stdpath = "CF-Pferd.";
	
	public String getString(UUID uuid, String path){
		return PlayerdataAPI.getString(uuid,stdpath+path);
	}
	
	public int getInt(UUID uuid, String path){
		return PlayerdataAPI.getInt(uuid, stdpath+path);
	}
	
	public boolean getBoolean(UUID uuid, String path){
		return PlayerdataAPI.getBoolean(uuid, stdpath+path);
	}
	
	public void set(UUID uuid, String path, Object value){
		PlayerdataAPI.set(uuid, stdpath+path, value);
	}
	
	
	public void setupPlayerConfig(UUID uuid){
		if(getString(uuid, stdpath.replaceAll(".", "")) == null){
			//Create Math Random
			Random random = new Random();
			//Create Chances
			int StyleChance = random.nextInt(5);
			int ColorChance = random.nextInt(7);
			//Set Color String
			String color = null;
		 	if(ColorChance == 0){
		 		color= "BROWN";
		    }else if(ColorChance==1){
		    	color= "BLACK";			    
		    }else if(ColorChance==2){
		    	color= "CHESTNUT";			    
		    }else if(ColorChance==3){
		    	color= "CREAMY";			    
		    }else if(ColorChance==4){
		    	color= "DARK_BROWN";			    
		    	}else if(ColorChance==5){
			    	color= "GRAY";			    
		        }else if(ColorChance==6){
			    	color= "WHITE";			    
		        }else{}
			//End of Setting Color String
			//Set Style String
			String style = null;
			if(StyleChance == 0){
				style="BLACK_DOTS";
			}else if(StyleChance==1){
				style="WHITE_DOTS";
			}else if(StyleChance==2){
				style="NONE";
			}else if(StyleChance==3){
				style="WHITEFIELD";
			}else if(StyleChance==4){
				style="WHITE";
			}else{}

			//Write Strings to Playerdata Config
			set(uuid, "Color", color);
			set(uuid, "Style", style);
			set(uuid, "Adult", true);
			set(uuid, "Armor", "NONE");
			set(uuid, "Speed", 1);
			set(uuid, "JumpStrenght", 1);
		}
	}
}