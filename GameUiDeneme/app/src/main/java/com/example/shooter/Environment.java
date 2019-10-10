package com.example.shooter;

public class Environment {

    private float screenHeight;

    public Environment()
    {

    }

    public Environment(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Oyundaki ortamın öevresel faktörleri belirlenir
     * Ortamın yerçekimi ivmesi belirlenir
     */
    public float gravity = 1.569f;
    /**
     * Ortamdaki rüzgarın şiddeti ve yönü belirlenir
     */
    public float wind = 0;
    /**
     * Ortamın hava sürtünme katsayısı belirlenir
     */
    public float airFriction = 0;

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }
}
