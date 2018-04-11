package game.main;


public class BattleCalculator {
    
    private final GameObject thisObj1;
    private final GameObject thisObj2;
    private final int c1;
    private final int c2;
    private final int p2;
    private final int v1;
    private boolean hit;
    private double chance;
    
    public BattleCalculator(GameObject obj1, GameObject obj2){
        thisObj1 = obj1;
        c1 = thisObj1.getCelerity();
        v1 = thisObj1.getVitality();
        thisObj2 = obj2;
        c2 = thisObj2.getCelerity();
        p2 = thisObj2.getPuissance(); 
        
        calculateHitChance();
        calculateDamage();
        
    }
    private void calculateHitChance(){
        if(c1 > c2){
            hit = true;
        } else if(c1 < c2){
            chance = (c2 - c1)*0.12;
            hit = Math.random() > chance;
        } else {
            hit = Math.random() > 0.1;
        }
    }
    private  void calculateDamage(){
        if(hit){
            if(p2 > v1){
                thisObj1.changeCurrentVital(-(p2 - v1));
            } else { 
                thisObj1.changeCurrentVital(-1);
            }
        }
    }
}
