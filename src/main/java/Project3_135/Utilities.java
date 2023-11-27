package Project3_135;

public interface Utilities {

    // Resource path
    static final String PATH                                     = "src/main/java/Project3_135/assets/";
    static final String PLAY_BUTTON_IMAGE_PATH                   = PATH + "playButton.png";
    static final String PLAY_HOVER_BUTTON_PATH                   = PATH + "play_hoverButton.png";
    static final String BACK_BUTTON_IMAGE_PATH                   = PATH + "backButton.png";
    static final String BACK_HOVER_BUTTON_PATH                   = PATH + "back_hoverButton.png";
    static final String MENU_BUTTON_IMAGE_PATH                   = PATH + "menuButton.png";
    static final String MENU_HOVER_BUTTON_PATH                   = PATH + "menu_hoverButton.png";
    static final String SETTING_BUTTON_IMAGE_PATH                = PATH + "settingButton.png";
    static final String SETTING_HOVER_BUTTON_PATH                = PATH + "setting_hoveButton.png";
    static final String CREDIT_BUTTON_IMAGE_PATH                 = PATH + "creditButton.png";
    static final String CREDIT_HOVER_BUTTON_PATH                 = PATH + "credit_hoverButton.png";
    static final String HOOK1_IMAGE_PATH                         = PATH + "hook1.png";
    static final String HOOK2_IMAGE_PATH                         = PATH + "hook2.png";
    static final String HOOK3_IMAGE_PATH                         = PATH + "hook3.png";
    static final String HOOK4_IMAGE_PATH                         = PATH + "hook4.png";
    static final String QUESTIONMARK_IMAGE_PATH                  = PATH + "questionmark.png";
    static final String DIAMOND_IMAGE_PATH                       = PATH + "diamond.png";
    static final String GOLDORE_SMALL_IMAGE_PATH                 = PATH + "goldore_small.png";
    static final String GOLDORE_LARGE_IMAGE_PATH                 = PATH + "goldore_large.png";
    static final String ROCK_SMALL_IMAGE_PATH                    = PATH + "rock_small.png";
    static final String ROCK_LARGE_IMAGE_PATH                    = PATH + "rock_large.png";
    static final String BACKGROUND1_IMAGE_PATH                   = PATH + "background1.png";
    static final String BACKGROUND2_IMAGE_PATH                   = PATH + "background2.png";
    static final String BACKGROUND3_IMAGE_PATH                   = PATH + "background3.png";
    static final String BACKGROUND4_IMAGE_PATH                   = PATH + "background4.png";
    static final String STONE_SETTING_BUTTON_IMAGE_PATH          = PATH + "stone_setting_button.png";
    static final String STONE_SETTING_BUTTON_HOVER_IMAGE_PATH    = PATH + "stone_setting_button_hover.png";
    static final String THEME_SOUND_PATH                         = PATH + "maintheme.wav";
    static final String ROCKHIT_SOUND_PATH                       = PATH + "rockhit.wav";
    static final String FINISH_SOUND_PATH                        = PATH + "finish.wav";
    static final String START_SOUND_PATH                         = PATH + "start.wav";
    static final String MAINMENU_BACKGROUND_PATH                 = PATH + "mainmenu_background.png";
    static final String SETTING_BACKGROUND_PATH                  = PATH + "mainmenu_background_dark.png";
    static final String SIGN_BACKGROUND_PATH                     = PATH + "setting_background.png";
    static final String CREDIT_BACKGROUND_PATH                   = PATH + "mainmenu_background_dark.png";
    static final String GAMEEND_BACKGROUND_PATH                  = PATH + "gamend_background.png";

    // Frame setting
    int FRAMEWIDTH = 1366;
    int FRAMEHEIGHT = 768;

    // Item setting (Minimum range, Maximum range, Score, Speed penalty
    String[] IMAGEFILES ={
            ROCK_SMALL_IMAGE_PATH,
            ROCK_LARGE_IMAGE_PATH,
            GOLDORE_SMALL_IMAGE_PATH,
            GOLDORE_LARGE_IMAGE_PATH,
            DIAMOND_IMAGE_PATH};
    double[] SPAWNCHANCE  = {30, 20, 20, 20, 5};
    int[] SPAWNCONDITION1 = {200, 600, 10, 15};
    int[] SPAWNCONDITION2 = {250, 650, 20, 10};
    int[] SPAWNCONDITION3 = {300, 600, 40, 30};
    int[] SPAWNCONDITION4 = {300, 620, 100, 12};
    int[] SPAWNCONDITION5 = {600, 620, 300, 15};

    // Game setting
    int ITEMAMOUNT = 30;
    int GAMETIME =1;
}
