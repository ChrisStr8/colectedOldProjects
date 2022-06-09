// This program is copyright VUW.
// You are granted permission to use it to construct your answer to an ENGR110 assignment.
// You may not distribute it in any other way without permission.
/* Code for COMP110 Assignment

 * Name: Chrstopher Straight
 * Usercode:straigchri
 * ID:300363269
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;


/** AdventureGame   */

public class AdventureGame{

    //----------Fields -------------------
    /*# YOUR CODE HERE */
    private Player player;
    private Pod currentPod;
    private Pod dockingPod;
    private ArrayList<Pod> allPods = new ArrayList<>();
    private static final int trapDamage = 10;
    private static final int RecoveryKitHealth = 30;
    private static final int maxWeight = 10;
    

    //---------- Constructor and interface ------------------
    /** Construct a new AdventureGame object and initialise the interface */
    public AdventureGame(){
        UI.initialise();
        UI.addButton("List Pack", this::doList);
        UI.addButton("Portal A", ()->{this.goPortal(0);});
        UI.addButton("Portal B", ()->{this.goPortal(1);});
        UI.addButton("Portal C", ()->{this.goPortal(2);});
        UI.addButton("Disable Trap", this::doDisable);
        UI.addButton("Look", this::doLook);
        UI.addButton("Search", this::doSearch);
        UI.addButton("Pickup", this::doPickUp);
        UI.addButton("PutDown", this::doPutDown);
        UI.addButton("Use Kit", this::useRecoveryKit);
        UI.addButton("Quit", UI::quit);

        UI.setDivider(1.0);  //show only the text pane
        this.initialiseGame();
    }

    //----------- Methods to respond to buttons ----------------

    /** List the items in the player's pack */
    public void doList(){
        if (player==null){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        player.listItems();
    }


    /** Exit the current pod going through the specified portal number */
    public void goPortal(int num){
        if (player==null){ return; }
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        currentPod.resetRecoveryKit();
        currentPod=currentPod.goPortal(num);
        UI.clearText();
        UI.println(currentPod.getDescription());  
        if (currentPod.getTrap()!=null){
            currentPod.getTrap().getItems();
        }
        if (player.hasDataCache() && currentPod.name.equals("Docking Pod")){
            UI.clearText();
            UI.println("YOU WIN");
        }
    }

    /** Look around at the pod and report what's there (except for datacache)*/
    public void doLook(){
        if (player==null || checkTrap()){ return; }
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        if (currentPod.getItem()!=null){
            UI.println("Item: "+currentPod.getItem().getName()+"\n"+"Item weight: "+currentPod.getItem().getWeight());
        }else{UI.println("there are no items here");}
        if (currentPod.hasRecoveryKit() && currentPod.recoveryKitUsed()){
            UI.println("There is an unused revovery kit");
        }else{UI.println("there is no revovery kit here");}
    }

    /** Search for the data cache, and pick it up if it is found.
     *  If the player has a torch, then there is a higher probability of
     *  finding the datacache (assuming it is in the pod) than if the player
     *  doesn't have a torch.
     */
    public void doSearch(){
        if (player==null || checkTrap()){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        if (currentPod.hasDataCache()){
            double random = 1+(Math.random()*((100-1)+1));
            if(player.hasTorch() && random<70){
                player.getCache();
                currentPod.removeCache();
                UI.println("you got the data cache");
            }else if (random<50){
                player.getCache();
                currentPod.removeCache();
                UI.println("you got the data cache");
            }
        }else {UI.println("you didn't find the data cache");}
    }


    /** Attempt to pick up an item from the pod and put it in the pack.
     *  If item makes the pack too heavy, then puts the item back in the pod */
    public void doPickUp(){
        if (player==null || checkTrap()){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        Item i = currentPod.removeItem();
        if (player.packWeight()+i.getWeight()<maxWeight){
            player.addItem(i);
            UI.println("The "+i.getName()+" was picked up"); 
        } else {
            currentPod.addItem(i);
            UI.println("The is too full"); 
        }
    }

    /** Attempt to put down an item from the pack. */
    public void doPutDown(){
        if (player==null || checkTrap()){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
    }                

    /** Attempt to disable the trap in the current pod.
     * If there is no such trap, or it is already disabled, return immediately.
     * If disabling the trap with the players current pack of items doesn't work,
     *  the player is damaged. If their health is now <=0, then the game is over
     */
    public void doDisable(){
        if (player==null){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        if (currentPod.getTrap()==null || !currentPod.getTrap().isActive()){return;}
        if (!currentPod.getTrap().disable(player.getPack())){
            player.damagePlayer(trapDamage);
        }
    }

    /** If there is a recovery kit in the pod that hasn't been already used on
     *  this visit, then use it (increase the player's health) and remember that
     *  the kit has now been used.
     */
    public void useRecoveryKit(){
        if (player==null || checkTrap()){ return;}
        /*# YOUR CODE HERE */
        if (player.getHealth()<0){
            UI.clearText();
            UI.println("YOU ARE DEAD");
            return;
        }
        if (currentPod.hasRecoveryKit()){
            currentPod.useRecoveryKit();
            player.healPlayer(RecoveryKitHealth);
            if (player.getHealth()>100){
                int damage = player.getHealth()-100;
                player.damagePlayer(damage);
                UI.println("the player was healed by "+(RecoveryKitHealth-damage));
            }else {
                UI.println("the player was healed by "+RecoveryKitHealth);
            }
        }else {UI.println("There is no Recovery Kit");}
    }

    // ------------ Utility methods ---------------------------
    /** Check if there is an active trap. If so, set it off and damage the player.
     *  Returns true if the player got damaged. 
     */
    private boolean checkTrap(){
        /*# YOUR CODE HERE */
        if(currentPod.getTrap()!=null && currentPod.getTrap().isActive()){
            player.damagePlayer(trapDamage);
            UI.println("the player was delt "+trapDamage+" damage\nthe players health is "+player.getHealth());
            if (player.getHealth()<0){
                UI.clearText();
                UI.println("YOU ARE DEAD");
                return true;
            }
            return true;
        }
        return false;
    }



    // ---------- Initialise -------------------------

    /** Intialise all the pods in the game and the player
     *  YOU DO NOT NEED TO USE THIS METHOD - YOU CAN REPLACE IT WITH YOUR OWN
     *  The code provided is a pretty simple initialisation process.
     *  It makes assumptions about the constructors and some methods for other classes.
     *  You will need to change it if it doesn't fit with the rest of your design
     *  It reads the pod descriptions from the game-data.txt file, and connects
     *  them in a circle, with random cross-links. 
     *  It then reads descriptions of the traps (and the items that disable them),
     *   makes Trap objects and Item objects,
     *   puts the Traps and the items in random pods 
     *  Puts a torch Item into one of the pods
     *  Makes a player
     *  Assumes constructors for Pod, Player, Item, and Trap
     *  Assumes allPods field, and several methods on traps, pods, and items
     *   You will need to modify the code if you have different constructors and methods.
     */
    public void initialiseGame(){
        Scanner data = null;
        try{
            //create pods from game-data file: 
            data = new Scanner(new File("game-data.txt"));
            //ignore comment lines, (starting with '# ')
            while (data.hasNext("#")){data.nextLine();}
            //read number of pods
            int numPods = data.nextInt(); data.nextLine();
            //read  name, has-recovery-kit,  has-data-cache
            for (int i=0; i<numPods; i++){
                // ASSUMES a Pod constructor!!!
                String podName = data.nextLine().trim();
                boolean hasRecoveryKit = data.nextBoolean();
                boolean hasDataCache = data.nextBoolean();
                Pod pod = new Pod(podName, hasRecoveryKit, hasDataCache);                //**MAY NEED TO CHANGE***
                allPods.add(pod);
                data.nextLine();
            }
            dockingPod = allPods.get(0);
            // connect them in circle, to ensure that there is a path
            for (int i=0; i<numPods; i++){
                Pod pod1 = allPods.get(i);
                Pod pod2 = allPods.get((i+1)%numPods);
                // ASSUMES one-way connections
                pod1.addPortalTo(pod2);               //**MAY NEED TO CHANGE***
            }
            // connect each pod to two random other pods.
            for (Pod pod : allPods){
                Pod podB = allPods.get((int)(Math.random()*allPods.size()));
                Pod podC = allPods.get((int)(Math.random()*allPods.size()));
                pod.addPortalTo(podB);               //**MAY NEED TO CHANGE***
                pod.addPortalTo(podC);               //**MAY NEED TO CHANGE***
            }
            UI.printf("Created %d pods\n", allPods.size());

            // Read trap name and items to disable trap, to make Traps and Item
            ArrayList<Trap> traps = new ArrayList<Trap>();
            ArrayList<Item> items = new ArrayList<Item>();
            while (data.hasNext()){
                //trap name, followed by number of items to disable trap,
                //followed by items (weight, name)
                String trapName = data.nextLine().trim();
                ArrayList<Item> itemsForTrap = new ArrayList<Item>();
                int numItems = data.nextInt(); data.nextLine();
                for (int i=0; i<numItems; i++){
                    double weight = data.nextDouble();
                    String itemName = data.nextLine().trim();
                    // ASSUMES Item contructor:
                    Item it = new Item(itemName, weight);             //**MAY NEED TO CHANGE***
                    itemsForTrap.add(it);
                }
                // ASSUMES Trap contructor:
                Trap trap = new Trap(trapName, itemsForTrap);         //**MAY NEED TO CHANGE***
                traps.add(trap);
                items.addAll(itemsForTrap);
            }
            data.close();

            // ASSUMES Item contructor:
            items.add(new Item("Torch", 0.4));
            //put the traps in random rooms (other than the dockingPod)
            //but not in rooms that already have a trap
            UI.printf("Created %d traps and %d items\n", traps.size(), items.size());
            while (!traps.isEmpty()){
                Pod pod = allPods.get(1+(int) (Math.random()*numPods-1));
                // ASSUMES methods on Pod
                if (pod.getTrap() == null){               //**MAY NEED TO CHANGE***
                    pod.setTrap(traps.remove(0));         //**MAY NEED TO CHANGE***
                }
            }
            //put the Items in random rooms.
            while (!items.isEmpty()){
                Pod pod = allPods.get((int) (Math.random()*numPods));
                // ASSUMES method on Pod
                pod.addItem(items.remove(0));            //**MAY NEED TO CHANGE***
            }
            //
            UI.printf("added traps and items to Pods\n");
            currentPod = dockingPod;
            player = new Player();                       //**MAY NEED TO CHANGE***
            UI.println("You are at the Docking pod:");
            // ASSUMES method on Pod
            UI.println(currentPod.getDescription());     //**MAY NEED TO CHANGE***
           }
        catch(InputMismatchException e){UI.println("Wrong type of data at: " + data.nextLine());}
        catch(IOException e){UI.println("Failed to read data correctly:\n" + e);}
    }

    public static void main(String[] args){
        new AdventureGame();
    }


}
