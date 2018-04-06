package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 1/30/18.
 */

class GameScreen extends ScreenAdapter {

    private static final String MESSAGE_READY = "Ready?";
    private static final String MESSAGE_GAME_OVER = "Game Over";
    private static final String MESSAGE_PAUSED = "Paused";
    private final int startingScore;
    private Label labelScore;
    private Label message;
    private TextButton btnPause;
    private LevelEnd levelEnd;
    private BackStage backStage;

    public int getCurrentScore() {
        return tama.getScore();
    }

    public enum GameState{
        RUNNING,
        PAUSED,
        READY,
        GAME_OVER,
        LEVEL_END
    }

    public GameState gameState;
    private final Skin skin;
    private final TamaJumperGame game;
    private final Vector3 touchPoint;
    private MyStage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    public final Random rand;
    private Group platforms;
    private Tama tama;
    private Group coins;
    private Group enemies;
    private float heightSoFar=0;





    public GameScreen(TamaJumperGame game, int startScore) {
        this.game=game;
        rand = new Random();
        touchPoint = new Vector3();
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        startingScore=startScore;
    }

    @Override
    public void show() {
        super.show();
        gameState=GameState.READY;
        camera=new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH/ 2, VIEWPORT_HEIGHT / 2, 0);
        viewport =new FillViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT, camera);
        stage=new MyStage( viewport, game);
        backStage=new BackStage(viewport,game);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        generateObjects();
        backStage.generateObjects();
        btnPause=new TextButton("Pause", skin);
        btnPause.setWidth(Platform.WIDTH);
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //super.clicked(event, x, y);
                System.out.println("pause clicked");
                gameState=GameState.PAUSED;
            }
        });
        labelScore = new Label("Score: 0", skin);
        updateUi();
        stage.addActor(btnPause);
        stage.addActor(labelScore);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(2f/255f,8f/255f,36f/255f,1f);
        if(Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                switch (gameState){
                    case RUNNING:
                        break;
                    case READY:
                    case PAUSED:
                        gameState=GameState.RUNNING;
                        return;
                    case LEVEL_END:
                    case GAME_OVER:
                    default:
                        game.changeScreen(TamaJumperGame.MENU);
                        return;
                }
        }
        switch (gameState){
            case READY:
                drawMessage(MESSAGE_READY);
                break;
            case PAUSED:
                drawMessage(MESSAGE_PAUSED);
                break;
            case LEVEL_END:
            case GAME_OVER:
                if(Prefs.setHighscore(tama.getScore())){
                    drawMessage(MESSAGE_GAME_OVER+"\nNew highscore!");
                }else drawMessage(MESSAGE_GAME_OVER);
                break;
            default:
                drawMessage(null);
                break;
        }
        if(gameState==GameState.RUNNING){
            tama.setGameStarted(true);
            checkCollisions();
            if(tama.getY()>camera.position.y) {
                camera.position.y=tama.getY();
                camera.update();
            }
            checkGameOver();
            stage.act(delta);
            backStage.act(delta);
        }else {
            tama.setGameStarted(false);
            tama.act(delta);
        }

        updateUi();
        backStage.draw();
        stage.draw();
    }

    private void checkGameOver() {
        heightSoFar = Math.max(tama.getY(), heightSoFar);
        if (heightSoFar - stage.getHeight()/2 > tama.getY()) {
            gameOver();
        }
    }

    private void updateUi() {
        String scoreText="Score: "+tama.getScore();
        labelScore.setText(scoreText);
        labelScore.setPosition(16,camera.position.y+VIEWPORT_HEIGHT/2-labelScore.getHeight()*2.5f);
        btnPause.setPosition(stage.getWidth()-btnPause.getWidth(),camera.position.y+VIEWPORT_HEIGHT/2-btnPause.getHeight()*1.75f);
    }
    private void drawMessage(String text){
        if(message!=null)message.remove();

        if(text==null) {
            stage.addActor(btnPause);
        }else {
            message=new Label(text,skin);
            message.setPosition(camera.position.x-message.getWidth()/2,camera.position.y-message.getHeight());
            message.setText(text);
            stage.addActor(message);
            btnPause.remove();
        }
    }

    private void checkCollisions() {
        Platform platform= (Platform) checkActorCollisions(platforms,true);
        if(platform!=null){
            tama.hitPlatform(platform);
        }

        Coin coin= (Coin) checkActorCollisions(coins,false);
        if(coin!=null){
            tama.hitCoin(coin);
        }


        Enemy enemy = (Enemy) checkActorCollisions(enemies,false);
        if(enemy!=null){
            if(!tama.hitEnemy(enemy)){
                gameOver();
            }
        }

        if(checkActorCollisions(levelEnd,false)!=null){
            gameState=GameState.LEVEL_END;
            game.changeScreen(TamaJumperGame.WIN);
        }

    }

    private void gameOver() {
        gameState=GameState.GAME_OVER;
        Assets.playSound(Assets.gameOverSound);

    }

    private Actor checkActorCollisions(Actor actor, boolean legsOnly){
        Actor hitActor = actor.hit(tama.getRight(),tama.getTop() - tama.getHeight(),false);
        if(hitActor==null) {
            hitActor = actor.hit(tama.getRight()-tama.getWidth(),tama.getTop() - tama.getHeight(),false);
        }
        if(!legsOnly) {
            if (hitActor == null) {
                hitActor = actor.hit(tama.getRight() - tama.getWidth(), tama.getTop(), false);
            }
            if (hitActor == null) {
                hitActor = actor.hit(tama.getRight(), tama.getTop(), false);
            }
        }
        return hitActor;
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        backStage.dispose();
    }


    private void generateObjects() {
        tama=new Tama(stage.getWidth()/2,stage.getHeight()/2,startingScore);
        float y = Platform.HEIGHT / 2;
        float maxJumpHeight = Tama.JUMP_VELOCITY * Tama.JUMP_VELOCITY / (2 * -Config.GRAVITY.y);
        platforms = new Group();
        coins = new Group();
        enemies = new Group();


    while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
        Platform platform = Platform.generatePlatform(y,rand);
        platforms.addActor(platform);



        if (y > WORLD_HEIGHT / 10 && rand.nextFloat() > (1-Config.ENEMY_GENERATION_PROBABILITY)) {
            Enemy enemy = new Enemy(platform.getX()+ Enemy.WIDTH/2,
                    platform.getY()+ Platform.HEIGHT, rand.nextBoolean(),Enemy.getRandomType());
            enemies.addActor(enemy);
        }else if (platform.getType()== Platform.Type.NORMAL && rand.nextFloat() > (1-Config.COIN_GENERATION_PROBABILITY)) {
            Coin coin = new Coin(platform.getX()+ Coin.WIDTH/2
                    , platform.getY()+ Platform.HEIGHT,Coin.getRandomScore());
            coins.addActor(coin);
        }



        y += (maxJumpHeight*0.8f - 0.5f);
        float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;

        float randomSeed= rand.nextFloat();

        if(randomSeed<0.5f) randomSeed*=2;
        else if(randomSeed<0.75f)randomSeed/=1.2f;
        else randomSeed/=2;
        y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
    }
        levelEnd = new LevelEnd(WORLD_WIDTH / 2, y/4);
        stage.addActor(platforms);
        stage.addActor(coins);
        stage.addActor(enemies);
        stage.addActor(levelEnd);
        stage.addActor(tama);

    }

}
