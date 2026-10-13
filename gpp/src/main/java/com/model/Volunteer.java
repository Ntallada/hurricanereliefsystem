package com.model;

import java.util.ArrayList;

public class Volunteer {
    private boolean backgroundCheck;
    private ArrayList<String> assignedSkills; //made it <String> not <skills> like on uml
    private ArrayList<String> certifications;
    
    /* i think we should add to make two available code can work
    private boolean available;
     */

    public viewMap() {
        //?? idk for viewmap
    }

    public claimRequest(ReliefRequest r) {
        //?? idk for claimrequest
    }

    public viewEtaToRequest(ReliefRequest r) {
        //?? idk for viewetatorequest
    }

    public updateInventory(String item, int quantity) {
        //?? idk for updateinventory
    }

    public getVictimStatus(Victim v) {
        //?? idk for getvictimstatus
    }

    public markUnavailable() {
        available = false;
    }

    public markAvailable() {
        available = true;
    }
    
}
