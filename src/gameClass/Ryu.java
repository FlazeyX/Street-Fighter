package gameClass;

import java.awt.Graphics;

import javax.swing.Timer;

import textureClass.RyuTexture;

public class Ryu extends Character {
	boolean right;
	protected Ryu(CharacterInfo info, int speed, boolean right,boolean isAutomated) {
		super(CharacterInfo.RYU, speed, right, isAutomated);
		this.right = right;	
		
		

		if(right){
			idle = RyuTexture.idleRyuRight;
			walk =  RyuTexture.walkRyuRight;
			verticalJump =  RyuTexture.verticalJumpRyuRight;
			diagonalJump =  RyuTexture.diagonalJumpRyuRight;
			punch = RyuTexture.punchRyuRight;
			kick =  RyuTexture.kickRyuRight;



			sneakPunch =  RyuTexture.sneakPunchRyuRight;
			sneakKick =  RyuTexture.sneakKickRyuRight;
			aerialKick = RyuTexture.aerialKickRyuRight;
			special = RyuTexture.speicalRyuRight;

			hitTorso = RyuTexture.ryuTorsoHitRight;
			idleSneak =  RyuTexture.idleSneakRyuRight;

			hitHead = RyuTexture.ryuHeadHitRight;
			knockDown = RyuTexture.knockDownRyuRight;
			defeat = RyuTexture.defeatRyuRight;



		}
		if(!right){
			idle = RyuTexture.idleRyuLeft;
			walk =  RyuTexture.walkRyuLeft;
			verticalJump =  RyuTexture.verticalJumpRyuLeft;
			diagonalJump =  RyuTexture.diagonalJumpRyuLeft;
			punch = RyuTexture.punchRyuLeft;
			kick =  RyuTexture.kickRyuLeft;



			sneakPunch =  RyuTexture.sneakPunchRyuLeft;
			sneakKick =  RyuTexture.sneakKickRyuLeft;
			aerialKick = RyuTexture.aerialKickRyuLeft;
			special = RyuTexture.speicalRyuLeft;

			hitTorso = RyuTexture.ryuTorsoHitLeft;
			idleSneak =  RyuTexture.idleSneakRyuLeft;

			hitHead = RyuTexture.ryuHeadHitLeft;
			knockDown = RyuTexture.knockDownRyuLeft;
			defeat = RyuTexture.defeatRyuLeft;

		}

	}

	@Override
	void jump() {
		if(!inAir){
			Thread jump = new Thread(new Runnable(){
				public void run(){
					for(int index = 0; index < 100; index++){
						y -= 6;
						try{
							Thread.sleep(1);
						}catch(Exception e) {}
					}
				}
			});
			jump.start();
		}
	}

	@Override
	public String toString() {
		return "RYU";
	}



	@Override
	void punch() {
		if(!isAttacking){
			Thread punch = new Thread(new Runnable(){

				@Override
				public void run() {
					isPunching = true;
					punchIndex = 0;

					for(int index = 0; index < RyuTexture.punchRyuRight.length-1; index++){
						punchIndex++;
						try{
							Thread.sleep(50);
						}catch(Exception e) { }
					}

					isPunching = false;
				}
			});
			punch.start();
		}
	}


	@Override
	void kick() {
		if(!isAttacking){
			Thread kick = new Thread(new Runnable(){

				@Override
				public void run() {
					kickIndex = 0;
					isKicking = true;


					for(int index = 0; index < RyuTexture.kickRyuRight.length-1; index++){
						kickIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }
					}

					isKicking = false;
				}

			});
			kick.start();
		}
	}


	@Override
	void special() {
		if(!isAttacking&&!isGettingKnockedDown){
			int energyBallXVelo = 0;
			int xBuffer = 0;
			if(right){
				energyBallXVelo = 10;
				xBuffer = (int) (x+(getInfo().getWidth()));
			}else{
				energyBallXVelo = -10;
				xBuffer = x-(getInfo().getWidth()/2);
			}
			FightPanelLauncher.sprites.add(new EnergyBall(xBuffer,y+(Constants.ENERGYBALL_HEIGHT.getIntValue()),energyBallXVelo,0));
			Thread special = new Thread(new Runnable(){
				
				@Override
				public void run() {
					isSpecial = true;
					specialIndex = 0;
					for(int index = 0; index < RyuTexture.speicalRyuRight.length-1; index++){
						specialIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }
					}
					try{
						Thread.sleep(250);
					}catch(Exception e) { }
					isSpecial = false;
				}

			});
			special.start();
		}
	}

	@Override
	void aerialPunch() {
		if(!isAttacking){
			Thread aerialPunch = new Thread(new Runnable(){
				public void run(){
					isAerialPunching = true;
					sneakKickIndex = 0;
					for(int index = 0; index < RyuTexture.sneakKickRyuRight.length-1; index++){
						sneakKickIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }
					}
					isAerialPunching = false;
				}
			});
			aerialPunch.start();
		}
	}

	@Override
	void aerialKick() {
		if(!isAttacking){
			Thread aerialKick = new Thread(new Runnable(){
				public void run(){
					isAerialKicking = true;
					aerialKickIndex = 0;
					for(int index = 0; index < RyuTexture.aerialKickRyuRight.length-1; index++){
						aerialKickIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }
					}		
					isAerialKicking = false;
				}
			});
			aerialKick.start();
		}
	}

	@Override
	void sneakPunch() {
		if(!isAttacking){
			Thread sneakPunch = new Thread(new Runnable(){

				@Override
				public void run() {
					isLowPunching = true;
					sneakPunchIndex = 0;
					for(int index = 0; index < RyuTexture.sneakPunchRyuRight.length-1; index++){
						sneakPunchIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }

					}		
					isLowPunching = false;
				}

			});
			sneakPunch.start();
		}
	}

	@Override
	void sneakKick() {
		if(!isAttacking){
			Thread sneakKick = new Thread(new Runnable(){
				public void run(){
					isLowKicking = true;
					sneakKickIndex = 0;
					for(int index = 0; index < RyuTexture.sneakKickRyuRight.length-1; index++){
						sneakKickIndex++;
						try{
							Thread.sleep(100);
						}catch(Exception e) { }
					}		
					isLowKicking = false;
				}
			});
			sneakKick.start();
		}
	}
	@Override
	void stand() {
		isSneaking = false;
	}

	@Override
	void sneak() {
		isSneaking = true;
	}

	@Override
	void getHitTorso() {

		Thread gettingHit = new Thread(new Runnable(){
			public void run(){
				isGettingHitTorso = true;
				gettingHitTorsoIndex = 0;
				for(int index = 0; index < RyuTexture.ryuTorsoHitRight.length-1; index++){
					gettingHitTorsoIndex++;
					try{
						Thread.sleep(100);
					}catch(Exception e) { }
				}		
				isGettingHitTorso = false;
			}
		});
		gettingHit.start();

	}

	@Override
	void getHitHead() {
		Thread gettingHit = new Thread(new Runnable(){
			public void run(){
				isGettingHitHead = true;
				gettingHitHeadIndex = 0;
				for(int index = 0; index < RyuTexture.ryuHeadHitRight.length-1; index++){
					gettingHitHeadIndex++;
					try{
						Thread.sleep(100);
					}catch(Exception e) { }
				}		
				isGettingHitHead = false;
			}
		});
		gettingHit.start();

	}

	@Override
	void getKnockedDown() {
		Thread gettingKnocked = new Thread(new Runnable(){
			public void run(){
			
				isGettingKnockedDown = true;
				gettingKnockedDownIndex = 0;
				for(int index = 0; index < RyuTexture.knockDownRyuRight.length-1; index++){
					gettingKnockedDownIndex++;
					
					try{
						Thread.sleep(100);
					}catch(Exception e) { }
				}		
				isGettingKnockedDown = false;
			}
		});
		
		Thread knockBack = new Thread(new Runnable(){
			public void run(){
			for(int index = 0; index < 50; index++){	
				y -= Constants.GRAVITY.getIntValue();
				if(!right){
					setX(6);
				}
				else{
					setX(-6);
				}
				try{
					Thread.sleep(1);
				}catch(Exception e) { }
			}
			}
		});
		
		knockBack.start();
		gettingKnocked.start();

	}

	@Override
	void defeated() {
		if(defeated){
			return;
		}
		Thread defeat = new Thread(new Runnable(){
			public void run(){
				defeated = true;
				defeatedIndex = 0;
				for(int index = 0; index < RyuTexture.defeatRyuRight.length-1; index++){
					defeatedIndex++;
					try{
						Thread.sleep(200);
					}catch(Exception e) { }
				}		
			}
		});
		defeat.start();
	}

}
