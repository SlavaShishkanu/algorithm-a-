package com.shishkanu.algorithm.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.config.Context;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlgorithmApplicatonUI extends Application {

    private static final int CONTROL_BUTOON_MIN_WIDTH = 100;

    private static Logger log = LoggerFactory.getLogger(AlgorithmApplicatonUI.class.getName());

    private UIContext uiContext;

    private Context context;

    private FieldDrawer fieldDrawer;

    private FieldManager fieldManager;

    private ImageView imageView;
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        context = Context.getInstance();
        
        uiContext = UIContext.getInstance();
        uiContext.init();
        
        fieldDrawer = uiContext.getFieldDrawer();
        fieldManager = context.getFieldManager();
        imageView = new ImageView();

        primaryStage.setTitle("Algorithm A*");
        primaryStage.setWidth(UIContext.getWindowWidth());
        primaryStage.setHeight(UIContext.getWindowHeight());

        final VBox rightPanel = new VBox();
        rightPanel.setPadding(new Insets(10, 10, 10, 10));
        rightPanel.setSpacing(10);

        drawSizeButtons(rightPanel);
        drawControlButtons(rightPanel);

        final HBox rootBox = new HBox();

        final ScrollPane scrollPane = drawImage();

        rootBox.getChildren().addAll(scrollPane, rightPanel);

        final Scene primaryScene = new Scene(rootBox);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    protected ScrollPane drawImage() {
        final Image image = SwingFXUtils.toFXImage(fieldDrawer.fieldToImage(fieldManager.getFieldDto()), null);

        imageView.setImage(image);
        final Pane imgContainer = new Pane(imageView);
        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imgContainer);

        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            double x = event.getX();
            double y = event.getY();
            log.debug("field clicked x={}, y={}, button={}", x, y, button);
            handleImageClick(x, y, button, uiContext);
            redrawImage();
        });
        imageView.setOnMouseDragged(event -> {
            MouseButton button = event.getButton();
            double x = event.getX();
            double y = event.getY();
            log.debug("field drag detected x={}, y={}, button={}", event.getX(), event.getY(), button);
            handleImageClick(x, y, button, uiContext);
            redrawImage();
        });
        return scrollPane;
    }

    protected void drawControlButtons(final VBox rightPanel) {
        final Button buttonSetStart = new Button("set start");
        buttonSetStart.setMinWidth(CONTROL_BUTOON_MIN_WIDTH);
        buttonSetStart.setOnAction(event -> {
            uiContext.setMode(AppMode.SET_START);
            log.debug("button SET_START clicked");
        });

        final Button buttonSetGoal = new Button("set goal");
        buttonSetGoal.setMinWidth(CONTROL_BUTOON_MIN_WIDTH);
        buttonSetGoal.setOnAction(event -> {
            uiContext.setMode(AppMode.SET_GOAL);
            log.debug("button SET_GOAL clicked");
        });

        final Button buttonSetWalls = new Button("add wall");
        buttonSetWalls.setMinWidth(CONTROL_BUTOON_MIN_WIDTH);
        buttonSetWalls.setOnAction(event -> {
            uiContext.setMode(AppMode.ADD_WALL);
            log.debug("button ADD_WALL clicked");
        });
        
        final Button buttonRemoveWall = new Button("remove wall");
        buttonRemoveWall.setMinWidth(CONTROL_BUTOON_MIN_WIDTH);
        buttonRemoveWall.setOnAction(event -> {
            uiContext.setMode(AppMode.REMOVE_WALL);
            log.debug("button REMOVE_WALL clicked");
        });

        final Button buttonClearWalls = new Button("clear walls");
        buttonClearWalls.setMinWidth(CONTROL_BUTOON_MIN_WIDTH);
        buttonClearWalls.setOnAction(event -> {
            uiContext.setMode(AppMode.ADD_WALL);
            log.debug("button CLEAR_WALL clicked");
            fieldManager.wallsClear();
            redrawImage();
        });
        
        final CheckBox boldPathcheckbox = new CheckBox("bold path");
        boldPathcheckbox.setSelected(context.isBoldPath());
        boldPathcheckbox.setOnAction(event -> {
            if (boldPathcheckbox.isSelected()) {
                context.setBoldPath(true);
            } else {
                context.setBoldPath(false);
            }
            fieldManager.updateField();
            redrawImage();
        });

        final CheckBox allowDiagonalMovementCheckbox = new CheckBox("diagonal movement");
        allowDiagonalMovementCheckbox.setSelected(context.isDiagonalMovementAllowed());
        allowDiagonalMovementCheckbox.setOnAction(event -> {
            if (allowDiagonalMovementCheckbox.isSelected()) {
                context.setDiagonalMovementAllowed(true);
            } else {
                context.setDiagonalMovementAllowed(false);
            }
            redrawImage();
        });
        
        rightPanel.getChildren().addAll(buttonSetStart, buttonSetGoal, buttonSetWalls, buttonRemoveWall,
                buttonClearWalls, boldPathcheckbox, allowDiagonalMovementCheckbox);
    }

    private void handleImageClick(final double x, final double y, MouseButton button, final UIContext uiContext2) {
        final int rectCoordinateX = (int) x / UIContext.getPixelSize();
        final int rectCoordinateY = (int) y / UIContext.getPixelSize();
        log.debug("rect coordinates X={}, Y={}", rectCoordinateX, rectCoordinateY);
        final Point point = new Point(rectCoordinateX, rectCoordinateY);

        
        if (uiContext2.getMode() == AppMode.SET_START && button == MouseButton.PRIMARY) {
            fieldManager.setStart(point);
            return;
        }
        
        if (uiContext2.getMode() == AppMode.SET_START && button == MouseButton.SECONDARY) {
            fieldManager.setGoal(point);
            return;
        }
        
        if (uiContext2.getMode() == AppMode.SET_GOAL) {
            fieldManager.setGoal(point);
        }
        
        if (uiContext2.getMode() == AppMode.ADD_WALL) {
            fieldManager.addWall(point);
        }
        
        if (uiContext2.getMode() == AppMode.REMOVE_WALL) {
            fieldManager.removeWall(point);
        }
    }

    protected void drawSizeButtons(final VBox rightPanel) {
        final Button btn1 = new Button("1");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                context.setObjectSize(1);
                log.debug("button 1 clicked size={}", context.getObjectSize());
                fieldManager.setObjectSize(1);
                redrawImage();
            }
        });

        final Button btn2 = new Button("2");
        btn2.setOnAction(event -> {
            context.setObjectSize(2);
            log.debug("button 2 clicked size={}", context.getObjectSize());
            redrawImage();
        });

        final Button btn3 = new Button("3");
        btn3.setOnAction(event -> {
            context.setObjectSize(3);
            log.debug("button 3 clicked, size={}", context.getObjectSize());
            redrawImage();
        });

        final HBox rectSizeButtonsBox = new HBox();
        rectSizeButtonsBox.setPadding(new Insets(5, 5, 5, 5));
        rectSizeButtonsBox.setSpacing(5);
        rectSizeButtonsBox.setMinHeight(50);
        rectSizeButtonsBox.getChildren().addAll(btn1, btn2, btn3);

        final Label sizeLabel = new Label("Size:");
        rightPanel.getChildren().addAll(sizeLabel, rectSizeButtonsBox);
    }

    private void redrawImage() {
        fieldManager.updateField();
        final Image image = SwingFXUtils.toFXImage(fieldDrawer.fieldToImage(fieldManager.getFieldDto()), null);
        imageView.setImage(image);
        
    }

}
