package com.cafe.crm.models.Menu;

import java.util.Set;

/**
 * Created by Sasha ins on 17.04.2017.
 */
public class Menu {


    private Drinks drinks;
    private Hookah hookah;
    private Snacks snacks;

    public Menu() {
    }


    public Set<Drinks> getDrinks() {
        return drinks.getDrinks();
    }

    public Set<Snacks> getSnacks() {
        return snacks.getSnacks();
    }
    public Set<Hookah> getHookah() {
        return hookah.getHookah();
    }

}
