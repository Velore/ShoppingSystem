package ss;

import ss.controller.ViewController;
import ss.po.View;

/**
 * @author Velore
 * @date 2022/1/2
 **/
public class ShoppingSystem {

    public static void startup(){
        ViewController.mainView(new View());
    }

    public static void main(String[] args) {
        startup();
    }
}
