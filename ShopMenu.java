import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
* Creates the class for the shop menu. It implements the action listener class.
**/
public class ShopMenu implements ActionListener {
    private JFrame frame;
    private int startingUpgradePrice;
    private ArrayList<Component> components = new ArrayList<>();
    private JButton healthBuyButton, reloadBuyButton, speedBuyButton, medKitBuyButton;
    private JButton rifleBuyButton, bowBuyButton, sniperBuyButton, slingBuyButton, upgradeGunButton;
    private JButton healthIcon, reloadIcon, speedIcon, medKitIcon;
    private JButton rifleIcon, bowIcon, sniperIcon, slingIcon, upgradeGunIcon;
    private JTextArea healthBoostDesc, reloadBoostDesc, speedBoostDesc, medKitDesc;
    private JTextArea rifleDesc, bowDesc, sniperDesc, slingDesc, upgradeGunDesc;
    private Player player;
    public ShopMenu(JFrame frame, Player player) {
        this.frame = frame;
        this.player = player;
        startingUpgradePrice = 3000;
        // ImageIcons
        ImageIcon healthImg = new ImageIcon("HealthPerk.png");
        ImageIcon reloadImg = new ImageIcon("ReloadPerk.png");
        ImageIcon speedImg = new ImageIcon("SpeedPerk.png");
        ImageIcon medKitImg = new ImageIcon("Medkit.png");
        ImageIcon rifleImg = new ImageIcon("Rifle.png");
        ImageIcon bowImg = new ImageIcon("Bow.png");
        ImageIcon sniperImg = new ImageIcon("Sniper.png");
        ImageIcon slingShotImg = new ImageIcon("Slingshot.png");
        ImageIcon upgradeGunImg = new ImageIcon("PackAPunch.PNG");


        // Declaration of components
        healthBuyButton = new JButton("BUY $500");
        healthIcon = new JButton();
        healthBoostDesc = new JTextArea("Double your max HP.");

        reloadBuyButton = new JButton("BUY $700");
        reloadIcon = new JButton();
        reloadBoostDesc = new JTextArea("1.5x decreased\n reload speed");

        speedBuyButton = new JButton("BUY $600");
        speedIcon = new JButton();
        speedBoostDesc = new JTextArea("Double movement\n speed.");

        medKitBuyButton = new JButton("BUY $400");
        medKitIcon = new JButton();
        medKitDesc = new JTextArea("Replenish to \nfull health.");
        
        rifleBuyButton = new JButton("BUY $1000");
        rifleIcon = new JButton();
        rifleDesc = new JTextArea("RIFLE, greatly \nincreased reload\nspeed, slightly \nincreased damage");

        bowBuyButton = new JButton("BUY $1500");
        bowIcon = new JButton();
        bowDesc = new JTextArea("BOW, slightly \nincreased reload\nspeed and damage");

        sniperBuyButton = new JButton("BUY $1200");
        sniperIcon = new JButton();
        sniperDesc = new JTextArea("SNIPER, greatly \ndecreased reload \nspeed, drastically \nincreased damage");

        slingBuyButton = new JButton("BUY $600");
        slingIcon = new JButton();
        slingDesc = new JTextArea("SLINGSHOT, slightly \ndecreased reload \nspeed, greatly \nincreased damage");

        upgradeGunButton = new JButton("UPGRADE GUN $3000");
        upgradeGunIcon = new JButton();
        upgradeGunDesc = new JTextArea("Upgrades current gun\n - Double damage\n - Double reload speed\n - New bullet animation\n(ADDITIVE STACKS)");

        // Description editables
        healthBoostDesc.setEditable(false);
        reloadBoostDesc.setEditable(false);
        speedBoostDesc.setEditable(false);
        medKitDesc.setEditable(false);
        rifleDesc.setEditable(false);
        bowDesc.setEditable(false);
        sniperDesc.setEditable(false);
        slingDesc.setEditable(false);
        upgradeGunDesc.setEditable(false);
  

        // Buy button fonts
        Font buyFont = new Font(Font.MONOSPACED, Font.BOLD, 10);
        healthBuyButton.setFont(buyFont);
        reloadBuyButton.setFont(buyFont);
        speedBuyButton.setFont(buyFont);
        medKitBuyButton.setFont(buyFont);
        rifleBuyButton.setFont(buyFont);
        bowBuyButton.setFont(buyFont);
        sniperBuyButton.setFont(buyFont);
        slingBuyButton.setFont(buyFont);
        upgradeGunButton.setFont(buyFont);
        
        // Description Fonts
        Font descriptionFont = new Font(Font.SANS_SERIF, 0, 10);
        healthBoostDesc.setFont(descriptionFont);
        reloadBoostDesc.setFont(descriptionFont);
        speedBoostDesc.setFont(descriptionFont);
        medKitDesc.setFont(descriptionFont);
        rifleDesc.setFont(descriptionFont);
        bowDesc.setFont(descriptionFont);
        sniperDesc.setFont(descriptionFont);
        slingDesc.setFont(descriptionFont);
        upgradeGunDesc.setFont(descriptionFont);
        

        // Buy button colors
        Color buyColor = new Color(250, 213, 165);
        healthBuyButton.setBackground(buyColor);
        reloadBuyButton.setBackground(buyColor);
        speedBuyButton.setBackground(buyColor);
        medKitBuyButton.setBackground(buyColor);
        rifleBuyButton.setBackground(buyColor);
        bowBuyButton.setBackground(buyColor);
        sniperBuyButton.setBackground(buyColor);
        slingBuyButton.setBackground(buyColor);
        upgradeGunButton.setBackground(buyColor);
        

        // Description colors
        Color descColor = new Color(166,88,60);
        healthBoostDesc.setBackground(descColor);
        reloadBoostDesc.setBackground(descColor);
        speedBoostDesc.setBackground(descColor);
        medKitDesc.setBackground(descColor);
        rifleDesc.setBackground(descColor);
        bowDesc.setBackground(descColor);
        sniperDesc.setBackground(descColor);
        slingDesc.setBackground(descColor);
        upgradeGunDesc.setBackground(descColor);

        // Icon setting
        healthIcon.setIcon(healthImg);
        reloadIcon.setIcon(reloadImg);
        speedIcon.setIcon(speedImg);
        medKitIcon.setIcon(medKitImg);
        rifleIcon.setIcon(rifleImg);
        bowIcon.setIcon(bowImg);
        sniperIcon.setIcon(sniperImg);
        slingIcon.setIcon(slingShotImg);
        upgradeGunIcon.setIcon(upgradeGunImg);

        // Setting bounds (diff btw buy to icon = 10, diff btw buy to desc = 10, diff btw rows = 20)
        speedBuyButton.setBounds(275,130,100,30); 
        speedIcon.setBounds(305,40,40,80); 
        speedBoostDesc.setBounds(275,170, 100, 30);

        healthBuyButton.setBounds(125,130,100,30);
        healthIcon.setBounds(155,40,40,80);
        healthBoostDesc.setBounds( 125,170, 100, 30);

        reloadBuyButton.setBounds(425,130,100,30); 
        reloadIcon.setBounds(455,40,40,80); 
        reloadBoostDesc.setBounds(425,170, 100, 30);
        
        medKitBuyButton.setBounds(575,130,100,30); 
        medKitIcon.setBounds(585,40,80,80); 
        medKitDesc.setBounds(575,170, 100, 30);

        slingBuyButton.setBounds(125,320,100,30); 
        slingIcon.setBounds(135,230,80,80); 
        slingDesc.setBounds(125,360, 100, 60);

        rifleBuyButton.setBounds(275,320,100,30);
        rifleIcon.setBounds(285,230,80,80);
        rifleDesc.setBounds(275,360, 100, 60);

        sniperBuyButton.setBounds(425,320,100,30); 
        sniperIcon.setBounds(435,230,80,80); 
        sniperDesc.setBounds(425,360, 100, 60);

        bowBuyButton.setBounds(575,320,100,30); 
        bowIcon.setBounds(585,230,80,80); 
        bowDesc.setBounds(575,360, 100, 60);

        upgradeGunButton.setBounds(325,550,150,40); 
        upgradeGunIcon.setBounds(350,440, 100,100); 
        upgradeGunDesc.setBounds(455,460, 120, 70);

        // Setting Focusable
        healthBuyButton.setFocusable(false);
        healthIcon.setFocusable(false);
        healthBoostDesc.setFocusable(false);

        reloadBuyButton.setFocusable(false);
        reloadIcon.setFocusable(false);
        reloadBoostDesc.setFocusable(false);

        speedBuyButton.setFocusable(false);
        speedIcon.setFocusable(false);
        speedBoostDesc.setFocusable(false);

        medKitBuyButton.setFocusable(false);
        medKitIcon.setFocusable(false);
        medKitDesc.setFocusable(false);

        rifleBuyButton.setFocusable(false);
        rifleIcon.setFocusable(false);
        rifleDesc.setFocusable(false);

        bowBuyButton.setFocusable(false);
        bowIcon.setFocusable(false);
        bowDesc.setFocusable(false);

        sniperBuyButton.setFocusable(false);
        sniperIcon.setFocusable(false);
        sniperDesc.setFocusable(false);

        upgradeGunButton.setFocusable(false);
        upgradeGunIcon.setFocusable(false);
        upgradeGunDesc.setFocusable(false);

        // Adding to the frame
        frame.add(healthBuyButton);
        frame.add(healthIcon);
        frame.add(healthBoostDesc);

        frame.add(reloadBuyButton);
        frame.add(reloadIcon);
        frame.add(reloadBoostDesc);

        frame.add(speedBuyButton);
        frame.add(speedIcon);
        frame.add(speedBoostDesc);

        frame.add(medKitBuyButton);
        frame.add(medKitIcon);
        frame.add(medKitDesc);

        frame.add(rifleBuyButton);
        frame.add(rifleIcon);
        frame.add(rifleDesc);

        frame.add(bowBuyButton);
        frame.add(bowIcon);
        frame.add(bowDesc);

        frame.add(sniperBuyButton);
        frame.add(sniperIcon);
        frame.add(sniperDesc);

        frame.add(slingBuyButton);
        frame.add(slingIcon);
        frame.add(slingDesc);

        frame.add(upgradeGunButton);
        frame.add(upgradeGunIcon);
        frame.add(upgradeGunDesc);

        // Adding to the components array
        components.add(healthBuyButton);
        components.add(healthIcon);
        components.add(healthBoostDesc);

        components.add(reloadBuyButton);
        components.add(reloadIcon);
        components.add(reloadBoostDesc);

        components.add(speedBuyButton);
        components.add(speedIcon);
        components.add(speedBoostDesc);
        
        components.add(medKitBuyButton);
        components.add(medKitIcon);
        components.add(medKitDesc);

        components.add(rifleBuyButton);
        components.add(rifleIcon);
        components.add(rifleDesc);

        components.add(bowBuyButton);
        components.add(bowIcon);
        components.add(bowDesc);

        components.add(sniperBuyButton);
        components.add(sniperIcon);
        components.add(sniperDesc);

        components.add(slingBuyButton);
        components.add(slingIcon);
        components.add(slingDesc);

        components.add(upgradeGunButton);
        components.add(upgradeGunIcon);
        components.add(upgradeGunDesc);

        // Declartion of action listeners
        healthBuyButton.addActionListener(this);
        reloadBuyButton.addActionListener(this);
        speedBuyButton.addActionListener(this);
        medKitBuyButton.addActionListener(this);
        rifleBuyButton.addActionListener(this);
        bowBuyButton.addActionListener(this);
        sniperBuyButton.addActionListener(this);
        slingBuyButton.addActionListener(this);
        upgradeGunButton.addActionListener(this);
    }
/**
* @public removes the shop menu from the screen
**/
    public void hideShopMenu() {
        for(Component component : components) {
            component.setVisible(false);
        }
        frame.revalidate();
        frame.repaint();
    }

/**
* @public draws the shop menu from the screen
**/    
    public void showShopMenu() {
        for(Component component : components) {
            component.setVisible(true);
        }
        frame.revalidate();
        frame.repaint();
    }

/**
* @public changes the current player who is using the shop
* @param (player): the player using the shop
**/
    public void changePlayer(Player player) {
        this.player = player;
    }

/**
* @public deals with the buttons on the shop that buys the specific upgrades
* @param (e): the button input
**/
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == healthBuyButton) {
            if(player.getCoins() >= 500 && !player.hasHealthPerk()) {
                player.updateStats("Health_Perk");
                player.deductCoins(500);
                healthBuyButton.setText("PURCHASED");
            }
        }

        if(e.getSource() == reloadBuyButton && !player.hasReloadPerk()) {
            if(player.getCoins() >= 700) {
                player.updateStats("Reload_Perk");
                player.deductCoins(700);
                reloadBuyButton.setText("PURCHASED");
            }
        }

        if(e.getSource() == speedBuyButton && !player.hasSpeedPerk()) {
            if(player.getCoins() >= 600) {
                player.updateStats("Speed_Perk");
                player.deductCoins(600);
                speedBuyButton.setText("PURCHASED");
            }
        }

        if(e.getSource() == medKitBuyButton) {
            if(player.getCoins() >= 400) {
                player.restorHealth();
                player.deductCoins(400);
            }
        }

        if(e.getSource() == slingBuyButton && !player.hasSling()) {
            if(player.getCoins() >= 600) {
                startingUpgradePrice = 3000;
                player.replaceGun(5, 0);
                player.updateGun("Slingshot");
                player.deductCoins(600);
                slingBuyButton.setText("PURCHASED");
                rifleBuyButton.setText("BUY $1000");
                bowBuyButton.setText("BUY $1500");
                sniperBuyButton.setText("BUY $1200");
                upgradeGunButton.setText("UPGRADE GUN $" + startingUpgradePrice);
            }
        }

        if(e.getSource() == rifleBuyButton && !player.hasRifle()) {
            if(player.getCoins() >= 1000) {
                startingUpgradePrice = 3000;
                player.replaceGun(2, 0);
                player.updateGun("Rifle");
                player.deductCoins(1000);
                slingBuyButton.setText("BUY $600");
                rifleBuyButton.setText("PURCHASED");
                bowBuyButton.setText("BUY $1500");
                sniperBuyButton.setText("BUY $1200");
                upgradeGunButton.setText("UPGRADE GUN $" + startingUpgradePrice);
            }
        }

        if(e.getSource() == bowBuyButton && !player.hasBow()) {
            if(player.getCoins() >= 1500) {
                startingUpgradePrice = 3000;
                player.replaceGun(3, 0);
                player.updateGun("Bow");
                player.deductCoins(1500);
                slingBuyButton.setText("BUY $600");
                rifleBuyButton.setText("BUY $1000");
                bowBuyButton.setText("PURCHASED");
                sniperBuyButton.setText("BUY $1200");
                upgradeGunButton.setText("UPGRADE GUN $" + startingUpgradePrice);
            }
        }

        if(e.getSource() == sniperBuyButton && !player.hasSniper()) {
            if(player.getCoins() >= 1200) {
                startingUpgradePrice = 3000;
                player.replaceGun(4, 0);
                player.updateGun("Sniper");
                player.deductCoins(1200);
                slingBuyButton.setText("BUY $600");
                rifleBuyButton.setText("BUT $1000");
                bowBuyButton.setText("BUY $1500");
                sniperBuyButton.setText("PURCHASED");
                upgradeGunButton.setText("UPGRADE GUN $" + startingUpgradePrice);
            }
        }

        if(e.getSource() == upgradeGunButton) {
            if(player.getCoins() >= startingUpgradePrice) {
                player.replaceGun(player.getGunType(), player.getBullet().amountUpgrades() + 1);
                player.deductCoins(startingUpgradePrice);
                startingUpgradePrice *= 3;
                upgradeGunButton.setText("UPGRADE GUN $" + startingUpgradePrice);
            }
        }
        frame.requestFocusInWindow();
    }
}
