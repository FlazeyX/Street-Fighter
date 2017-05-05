package gameClass;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import textureClass.RyuTexture;
import textureClass.Texture;


@SuppressWarnings("serial")
public class FightPanelLauncher extends JPanel implements Runnable{
	private Thread gameLoop;
	private boolean isRunning;
	private Character c;
	private Map m;
	private GameType g;
	private JFrame frame;
	private CardLayout cardLayout = new CardLayout();
	private JPanel screen = new JPanel(cardLayout);
	protected static ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	protected static ArrayList<GameMoves> keysPressedPlayer = new ArrayList<GameMoves>();
	protected FightPanelLauncher(Character c, Map m, GameType g){
		this.c = c;
		this.m = m;
		this.g = g;
		loadCharacterImages();
		sprites.add(c);
		panel();
		keys();
		start();
	}
	void loadCharacterImages(){
		
	Texture.loadRyuTextures();
		
	}

	void keys(){
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("O"), "O");
		getActionMap().put("O", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.next(screen);
			}

		});
		switch(g){
		case FIGHT:
			manyPlayerKeys();
			break;
		case TRAINING:
			singlePlayerKeys();
			break;
		default:
			break;

		}
	}
	void manyPlayerKeys(){
	}
	void singlePlayerKeys(){
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "A");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "W");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "S");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "rS");

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "rA");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "rD");


		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("J"), "PUNCH");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("K"), "KICK");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("L"), "SPECIAL");


		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.setXVelo(-c.getSpeed());
				keysPressedPlayer.add(GameMoves.LEFT);
			}

		});
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.setXVelo(c.getSpeed());
				keysPressedPlayer.add(GameMoves.RIGHT);

			}

		});
		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.jump();
				keysPressedPlayer.add(GameMoves.JUMP);

			}

		});
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.sneak();
				keysPressedPlayer.add(GameMoves.SNEAK);

			}

		});
		getActionMap().put("rS", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.stand();
				keysPressedPlayer.add(GameMoves.STAND);

			}

		});
		getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.setXVelo(0);
				keysPressedPlayer.add(GameMoves.STOP);

			}

		});
		getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.setXVelo(0);
				keysPressedPlayer.add(GameMoves.STOP);

			}

		});
		getActionMap().put("PUNCH", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.punch();
				keysPressedPlayer.add(GameMoves.PUNCH);

			}

		});
		getActionMap().put("KICK", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//	c.kick();
				keysPressedPlayer.add(GameMoves.KICK);

			}

		});
		getActionMap().put("SPECIAL", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//c.special();
				keysPressedPlayer.add(GameMoves.SPECIAL);

			}

		});
	}
	void panel(){
		frame = new JFrame();
		OptionLauncher.changePanel(screen, cardLayout);
		screen.add(this, null);
		screen.add(OptionLauncher.panel, null);
		frame.add(screen);
		this.setLayout(null);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardLayout.show(screen, null);
	}
	synchronized void start(){
		gameLoop = new Thread(this);
		isRunning = true;
		gameLoop.start();
		boundsCheck();
		readKeys();
	}
	void readKeys(){
		Thread keyReading = new Thread(new Runnable(){
			public void run(){
				while(isRunning){

					if(keysPressedPlayer.isEmpty()){

						try{
							Thread.sleep(1);
						}catch(Exception e) { }
						continue;
					}
					//reading keys
					GameMoves g = keysPressedPlayer.remove(0);
					switch(g){
					case JUMP:
						c.jump();
						break;
					case KICK:
						if(!c.inAir){
							if(c.isSneaking){
								c.sneakKick();
							}else{
								c.kick();
							}
						}else{
							c.aerialKick();
						}
						break;
					case LEFT:
						c.setXVelo(-c.getSpeed());
						break;
					case PUNCH:
						if(!c.inAir){
							if(c.isSneaking){
								c.sneakPunch();
							}else{
								c.punch();
							}
						}else{
							c.aerialPunch();
						}							
						break;
					case RIGHT:
						c.setXVelo(c.getSpeed());
						break;
					case SNEAK:
						c.sneak();
						break;
					case SPECIAL:
						c.special();
						break;
					case STAND:
						c.stand();
						break;
					case STOP:
						c.setXVelo(0);
						break;
					default:
						break;

					}

					try{
						Thread.sleep(1);
					}catch(Exception e) { }
				}
			}

		});
		keyReading.start();
	}
	void boundsCheck(){
		Thread boundCheck = new Thread(new Runnable(){
			public void run(){
				while(isRunning){
					//out of bounds check
					for(int index = 0; index < sprites.size(); index++){
						GameObject c = sprites.get(index);
						if(c.gravity){
							if(c.getX()<0){
								c.setX(c.getSpeed());
							}
							else if(c.getX()+Constants.PLAYER_WIDTH.getIntValue()>Constants.SCREEN_WIDTH.getIntValue()){
								c.setX(-c.getSpeed());
							}
						}
						//if in air
						if(c.getY()+Constants.PLAYER_HEIGHT.getIntValue()<(int)(Constants.SCREEN_HEIGHT.getIntValue()*.9)){
							c.inAir = true;
						}else{
							//stuck in floor
							c.inAir = false;
						}
						try{
							Thread.sleep(1);
						}catch(Exception e) { }
					}
				}
			}
		});
		boundCheck.start();
	}
	synchronized void stop(){
		try{
			gameLoop.join();
			isRunning = false;
		}catch(Exception e) { }
	}
	@Override
	public void run() {
		while(isRunning){
			update();
			try{
				Thread.sleep(10);
			}catch(Exception e) { }
		}

	}
	void update(){
		repaint();
		physics();
		updateLocations();
	}
	void physics(){
		for(int index = 0; index < sprites.size(); index++){
			GameObject c = sprites.get(index);
			if(!c.gravity){
				continue;
			}
			if(c.getY()+Constants.PLAYER_HEIGHT.getIntValue()<(int)(Constants.SCREEN_HEIGHT.getIntValue()*.9)){
				c.setYVelo(Constants.GRAVITY.getIntValue());
			}else{
				c.setYVelo(0);
			}

		}
	}
	void updateLocations(){
		for(int index = 0; index < sprites.size(); index++){
			GameObject c = sprites.get(index);
			c.setX(c.getXVelo());
			c.setY(c.getYVelo());
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawMap(g);
		drawCharacters(g);
	}
	void drawCharacters(Graphics g){
		for(int index = 0; index < sprites.size(); index++){
			GameObject c = sprites.get(index);
			c.draw(g);
		}
	}
	void drawMap(Graphics g){
		m.drawMap(g);
	}

}
