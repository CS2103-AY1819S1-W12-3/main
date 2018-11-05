package seedu.address.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NavigateToLearnPageEvent;
import seedu.address.commons.events.ui.ShowTriviaTestResultEvent;
import seedu.address.commons.events.ui.ShowTriviaTestViewEvent;
import seedu.address.logic.Logic;
import seedu.address.ui.home.Homepage;
import seedu.address.ui.test.TriviaTestPlaceholderPage;

/**
 * A Ui class that is responsible for storing the placeholders for all the pages that exist in the MainDisplay.
 */
public class MainDisplay extends UiPart<Region> {

    private static final String FXML = "MainDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Homepage homepage;
    private final TriviaTestPlaceholderPage triviaTestPlaceholderPage;
    private final double tabWidth = 90.0;

    private ArrayList<Tab> listOfTabs;
    private Tab currentTab;

    @FXML
    private StackPane mainDisplay;
    @FXML
    private JFXTabPane tabContainer;
    @FXML
    private Tab homeTab;
    @FXML
    private Tab settingsTab;
    @FXML
    private Tab customTab;
    @FXML
    private StackPane homepagePlaceholder;
    @FXML
    private StackPane triviaTestPlaceholder;

    public MainDisplay(Logic logic) {
        super(FXML);
        listOfTabs = new ArrayList<Tab>();

        tabContainer = new JFXTabPane();
        homeTab = new Tab();
        settingsTab = new Tab();
        customTab = new Tab();
        currentTab = homeTab;
        this.configureView();

        homepage = new Homepage(logic);
        homepagePlaceholder.getChildren().add(homepage.getRoot());

        triviaTestPlaceholderPage = new TriviaTestPlaceholderPage();
        triviaTestPlaceholder.getChildren().add(triviaTestPlaceholderPage.getRoot());

        bindNodesVisibilityProperty(mainDisplay);
        resetToOriginalState();
        registerAsAnEventHandler(this);
    }

    /**
     * Hides all the other pages except for the homepage. The default page should be the homepage.
     */
    public void resetToOriginalState() {
        mainDisplay.getChildren().forEach(node -> node.setVisible(false));
        homepagePlaceholder.setVisible(true);
    }

    /**
     * creates the view configuration of the tabs
     */
    private void configureView() {
        //tabContainer.setSide(Side.LEFT);
        tabContainer.setTabMinWidth(tabWidth);
        tabContainer.setTabMaxWidth(tabWidth);
        tabContainer.setTabMinHeight(tabWidth);
        tabContainer.setTabMaxHeight(tabWidth);
        tabContainer.setRotateGraphic(true);
        tabContainer.getStyleClass().add("root");

        //list of tabs configured
        createTab(homeTab, "Home", "file:/src/main/resources/images/tabIcons/home.png", homepagePlaceholder);
        //createTab(settingsTab, "Settings", "file:/src/main/resources/images/tabIcons/settings.png", settingsContainer);
        //createTab(customTab, "Custom", "file:/main/resources/images/tabIcons/test.png", testContainer);
    }

    /**
     * Configures a new tab
     */
    private void createTab(Tab tab, String title, String iconPath, AnchorPane containerPane) {
        double imageWidth = 40.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);

        BorderPane tabPane = new BorderPane();
        tabPane.setRotate(90.0);
        tabPane.setMaxWidth(tabWidth);
        tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText(title);
        tab.setGraphic(tabPane);

        if (containerPane != null) {
            try {
                Parent contentView = ;
                containerPane.getChildren().add(contentView);
                AnchorPane.setTopAnchor(contentView, 0.0);
                AnchorPane.setBottomAnchor(contentView, 0.0);
                AnchorPane.setRightAnchor(contentView, 0.0);
                AnchorPane.setLeftAnchor(contentView, 0.0);
                listOfTabs.add(tab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Change the given panel to the given UI region.
     */
    private void changeToScene(Node changeTo) {
        mainDisplay.getChildren().forEach(node -> {
            if (node.equals(changeTo)) {
                if (changeTo.equals(homepage.getRoot())) {
                    homepage.resetToOriginalState();

                }
                node.setVisible(true);
            } else {
                node.setVisible(false);
            }
        });
    }

    public void releaseResources() {
        homepage.releaseResources();
    }

    @Subscribe
    private void handleShowTriviaTestViewEvent(ShowTriviaTestViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeToScene(triviaTestPlaceholder);
    }

    @Subscribe
    private void handleShowTriviaTestResultPage(ShowTriviaTestResultEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeToScene(triviaTestPlaceholder);
    }

    @Subscribe
    private void handleNavigateToLearnPageEvent(NavigateToLearnPageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeToScene(homepagePlaceholder);
    }
}
