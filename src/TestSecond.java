import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

/**
 * @author zhaochanglang
 * @since 2021-7-13
 */
public class TestSecond extends JFrame {
    int width=1280;                     //the game frame's width
    int height=800;                     //the game frame's height
    int x=540;                          //the snake body's location
    int y=300;                          //the snake body's location
    long speed=100;                     //the snake body's speed
    final int TURN_UP=1;                //the snake's turn
    final int TURN_DOWN=2;              //the snake's turn
    final int TURN_LEFT=3;              //the snake's turn
    final int TURN_RIGHT=4;             //the snake's turn
    int tend=TURN_RIGHT;                //the default turn
    int score=0;                        //the score
    JPanel jPanel=new JPanel();
    JButton startBtn=new JButton("开始");
    JLabel scoreLabel;
    JLabel snakeHead;
    JLabel[] snakeBody;
    JLabel food;
    JFrame jFrame=this;

    /**
     * 加载窗口
     * load the frame
     */
    public TestSecond(){
        setButton();
        createScore();
        createFood();
        createSnakeHead();
        setPanel();
        setFrame();
        addCloseEvent();
        addFoodEvent();
        addMoveEvent();
    }

    /**
     *
     * @param oldArr snake's body 蛇身数组
     * @return snake's new body  增长后的蛇身数组
     * snake body lengthen 让蛇身变长
     */
    public JLabel[] changeArr(JLabel[] oldArr){
        JLabel[] newArr= Arrays.copyOf(oldArr,oldArr.length+1);
        return newArr;
    }

    /**
     * 设置窗体
     * set game frame
     */
    public void setFrame(){
        jFrame.setSize(width,height);
        jFrame.setLocation(200,100);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
    }

    /**
     * 设置游戏的空间Panel
     * set the game space Panel
     */
    public void setPanel(){
        jPanel.setLayout(null);
        jPanel.setSize(1000,600);
        jPanel.setLocation(100,10);
        jPanel.setBackground(Color.gray);
        jFrame.add(jPanel);
    }

    /**
     * 设置食物位置
     * set the food's option
     */
    public void setFood(){
        Random random=new Random();
        int foodX=random.nextInt(50);
        int foodY=random.nextInt(30);
        food.setLocation(foodX*20,foodY*20);
        food.setVisible(true);
    }

    /**
     * 设置蛇头位置
     * set the snake head's location
     */
    public void setSnakeHead(){
        snakeHead.setLocation(x,y);
        snakeHead.setVisible(true);
    }

    /**
     * 设置蛇身
     * set the snake body
     */
    public void setSnakeBody(){
        for (int i=0;i<snakeBody.length;i++) {
            snakeBody[i].setLocation(x-20*(i+1),y);
            snakeBody[i].setVisible(true);
        }
    }

    /**
     * 设置按钮
     * set the button
     */
    public void setButton(){
        startBtn.setSize(150,75);
        startBtn.setBackground(Color.gray);
        startBtn.setForeground(Color.WHITE);
        startBtn.setLocation(500,625);
        jFrame.add(startBtn);
    }

    /**
     * 创建一个食物
     * create the food
     */
    public void createFood(){
        food=new JLabel();
        food.setVisible(true);
        food.setOpaque(true);
        food.setBackground(Color.YELLOW);
        food.setSize(20,20);
        food.setVisible(false);
        jPanel.add(food);
    }

    /**
     * 创建初始化蛇身
     * create the default snake's body
     */
    public void createSnakeBody(){
        snakeBody=new JLabel[2];
        for (int i=0;i<snakeBody.length;i++){
            snakeBody[i]=new JLabel();
            snakeBody[i].setSize(20,20);
            snakeBody[i].setBackground(Color.red);
            snakeBody[i].setOpaque(true);
            snakeBody[i].setVisible(false);
            jPanel.add(snakeBody[i]);
        }
    }

    /**
     * 创建蛇头
     * set the snake's head
     */
    public void createSnakeHead(){
        snakeHead=new JLabel();
        snakeHead.setOpaque(true);
        snakeHead.setBackground(Color.ORANGE);
        snakeHead.setSize(20,20);
        snakeHead.setVisible(false);
        jPanel.add(snakeHead);
    }

    /**
     * snake lengthen
     * @return new snake body
     */
    public JLabel addSnakeBody(){
        JLabel jl=new JLabel();
        jl.setBackground(Color.red);
        jl.setSize(20,20);
        jl.setOpaque(true);
        jl.setVisible(true);
        return jl;
    }

    /**
     * 设置默认分数和分数相关组件的位置
     * set default score and the scoreBox's location
     */
    public void createScore(){
        scoreLabel=new JLabel("你的分数为:0");
        scoreLabel.setSize(300,75);
        scoreLabel.setOpaque(true);
        scoreLabel.setFont(new Font("",Font.BOLD,20));
        scoreLabel.setLocation(680,625);
        jFrame.add(scoreLabel);
    }

    /**
     * 让蛇移动的方法
     * snake move function
     */
    public void move(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //if the snake catch the food ,snake lengthen,replace the food,当蛇吃到食物时蛇变长,重新设定食物位置
                if (snakeHead.getX()==food.getX()&&snakeHead.getY()==food.getY()){
                    setFood();
                    snakeBody=changeArr(snakeBody);
                    snakeBody[snakeBody.length-1]=addSnakeBody();
                    jPanel.add(snakeBody[snakeBody.length-1]);
                    scoreLabel.setText("你的分数为:"+(++score));
                }
                //if the snake catch the border,game over,当蛇头碰到边界时游戏结束
                if(snakeHead.getX()>=0&&snakeHead.getX()<=1000&&snakeHead.getY()>=0&&snakeHead.getY()<=600){
                    //snake move
                    for (int i=snakeBody.length-1;i>=0;i--) {
                        if (i==0) snakeBody[i].setLocation(snakeHead.getLocation());
                        else snakeBody[i].setLocation(snakeBody[i-1].getLocation());
                    }
                    if (tend==TURN_UP) snakeHead.setLocation(x,y-=20);
                    else if (tend==TURN_DOWN) snakeHead.setLocation(x,y+=20);
                    else if (tend==TURN_LEFT) snakeHead.setLocation(x-=20,y);
                    else if (tend==TURN_RIGHT) snakeHead.setLocation(x+=20,y);
                }else {
                    timer.cancel();
                    scoreLabel.setText("游戏结束你的分数为"+score);
                    startBtn.setVisible(true);
                }
                //if snake head catch the snake body ,game over,当蛇头碰到蛇身时游戏结束
                for(int i=0;i<snakeBody.length;i++){
                    if (snakeBody[i].getX()==snakeHead.getX()&&snakeBody[i].getY()==snakeHead.getY()){
                        timer.cancel();
                        scoreLabel.setText("游戏结束你的分数为"+score);
                        startBtn.setVisible(true);
                    }
                }
            }
        }, new Date(), speed);
    }

    /**
     * 按键盘上的按钮改变蛇的方向
     * press the keyboard to change the snake's turn
     */
    public void addMoveEvent(){
        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==e.VK_UP&&tend!=TURN_DOWN) tend=TURN_UP;
                else if (e.getKeyCode()==e.VK_DOWN&&tend!=TURN_UP) tend=TURN_DOWN;
                else if (e.getKeyCode()==e.VK_LEFT&&tend!=TURN_RIGHT) tend=TURN_LEFT;
                else if (e.getKeyCode()==e.VK_RIGHT&&tend!=TURN_LEFT) tend=TURN_RIGHT;
            }
        });
    }

    /**
     * 关闭窗口
     * closing the frame
     */
    public void addCloseEvent(){
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * 鼠标事件
     * MouseEvent
     */
    public void addFoodEvent(){
        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                x=540;
                y=300;
                score=0;
                if (snakeBody!=null){
                    for (int i=0;i<snakeBody.length;i++) {
                        jPanel.remove(snakeBody[i]);
                    }
                }
                scoreLabel.setText("你的分数为:0");
                createSnakeBody();
                setFood();
                setSnakeHead();
                setSnakeBody();
                move();
                jFrame.requestFocus();
                startBtn.setVisible(false);
            }
        });
    }
}
