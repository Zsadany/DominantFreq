package com.dominantfreq.display;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dominantfreq.display.model.BulkSettingsPanel;
import com.dominantfreq.display.model.EcgItem;
import com.dominantfreq.display.model.handlers.ButtonHandlers;
import com.dominantfreq.display.model.styles.ButtonStyles;
import com.dominantfreq.export.EcgAnalysisToExcelExporter;
import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.dataaccess.EcgLoader;
import com.dominantfreq.service.bulkprocess.EcgBulkProcessingDaemon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

public class BulkDisplay extends Application {
	private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
	private static int itemsPerColumn = 8;
	private static int column = 2;
	private Stage stage;

	private String selectedDir;
	private List<EcgItem> items;
	private PageCreation callbacks;
	private LocalHandlers localHandlers;
	private LocalActions localActions;
	private Timeline uiUpdateCycle;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		this.stage = stage;
		stage.setTitle("ECGs");
		stage.setFullScreen(true);
		setScreenVariables();

		callbacks = new PageCreation();
		localHandlers = new LocalHandlers();
		localActions = new LocalActions();

		chooseDirectory(stage);
		items = loadItemsInDirectory();

		HBox pane = new HBox();
		BulkSettingsPanel bulkSettingsPanel = createSettingsPane();
		pane.getChildren().add(bulkSettingsPanel);
		AnchorPane paginatedPane = createPaginatedPane(bulkSettingsPanel);
		pane.getChildren().add(paginatedPane);

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setOnCloseRequest(localHandlers.closeWindow);
		stage.show();

		uiUpdateCycle = new Timeline(new KeyFrame(Duration.seconds(0.2), localHandlers.itemsUpdate));
		uiUpdateCycle.setCycleCount(Timeline.INDEFINITE);
		uiUpdateCycle.play();
	}

	private void setScreenVariables() {
		double screenX = Screen.getPrimary().getBounds().getMaxX();
		double screenY = Screen.getPrimary().getBounds().getMaxY();
		if (screenX < 1800)
			column = 1;
		if (screenY < 700)
			itemsPerColumn = 4;
		else if (screenY < 800)
			itemsPerColumn = 5;
		else if (screenY < 1080)
			itemsPerColumn = 6;
	}

	private void chooseDirectory(final Stage stage) {
		Button directoryChooserButton = new Button();
		directoryChooserButton.setText("Open Directory");
		directoryChooserButton.setOnAction(localHandlers.directoryChoice);
		while (selectedDir == null)
			directoryChooserButton.fireEvent(new ActionEvent());
	}

	private List<EcgItem> loadItemsInDirectory() {
		File folder = new File(selectedDir);
		List<String> ecgNames = EcgLoader.getEcgLoader().getDataNames(folder);
		List<EcgItem> items = new ArrayList<EcgItem>();
		for (String ecgName : ecgNames) {
			items.add(new EcgItem(EcgLoader.getEcgLoader().readEcgInfo(folder, ecgName)));
		}
		return items;
	}

	private BulkSettingsPanel createSettingsPane() {
		double screenY = Screen.getPrimary().getBounds().getMaxY();
		List<String> ecgNames = EcgLoader.getEcgLoader().getDataNames(new File(selectedDir));
		BulkSettingsPanel bulkSettingsPanel = new BulkSettingsPanel(ecgNames, false);
		Button buttonToProcessEmAll = createStylishButton("Process All ECGs", ButtonStyles.PROCESS_BUTTON_STYLE.toString());
		buttonToProcessEmAll.setOnAction(localHandlers.processAll);
		Button saveButton = createStylishButton("Save Results", ButtonStyles.SAVE_BUTTON_STYLE.toString());
		saveButton.setOnAction(localHandlers.save);
		Button closeButton = createStylishButton("Exit", ButtonStyles.CLOSE_BUTTON_STYLE.toString());
		closeButton.setOnAction(localHandlers.closeStage);
		Separator separator = new Separator();
		separator.setPrefHeight(10);
		bulkSettingsPanel.getChildren().add(buttonToProcessEmAll);
		bulkSettingsPanel.getChildren().add(separator);
		bulkSettingsPanel.getChildren().add(saveButton);
		Separator separator2 = new Separator();
		separator2.setPrefHeight(10);
		bulkSettingsPanel.getChildren().add(separator2);
		bulkSettingsPanel.getChildren().add(closeButton);
		bulkSettingsPanel.setPrefHeight(screenY);
		bulkSettingsPanel.setMinWidth(200);
		bulkSettingsPanel.autosize();
		return bulkSettingsPanel;
	}

	private Button createStylishButton(String name, String style) {
		Button button = new Button(name);
		button.setStyle(style);
		button.setOnMouseEntered(ButtonHandlers.buttonMouseEnteredHandler);
		button.setOnMouseExited(ButtonHandlers.buttonMouseExitedHandler);
		button.setEffect(new DropShadow());
		button.setOpacity(0.7);
		return button;
	}

	private AnchorPane createPaginatedPane(BulkSettingsPanel bulkSettingsPanel) {
		double screenX = Screen.getPrimary().getBounds().getMaxX();
		double screenY = Screen.getPrimary().getBounds().getMaxY();
		Pagination pagination = new Pagination(calcNumberOfPages());
		pagination.setPrefHeight(screenY);
		pagination.setPrefWidth(screenX - bulkSettingsPanel.getPrefWidth() - 1);
		pagination.setPageFactory(callbacks.pageFactory);

		AnchorPane anchor = new AnchorPane();
		anchor.setPrefHeight(screenY - 10);
		anchor.setPrefWidth(screenX - 250);
		AnchorPane.setTopAnchor(pagination, 10.0);
		AnchorPane.setRightAnchor(pagination, 10.0);
		AnchorPane.setBottomAnchor(pagination, 10.0);
		AnchorPane.setLeftAnchor(pagination, 10.0);
		anchor.getChildren().add(pagination);
		anchor.autosize();
		return anchor;
	}

	private int calcNumberOfPages() {
		int numberOfItems = items.size();
		int itemsOn1Page = itemsPerColumn * column;
		int extra = numberOfItems % itemsOn1Page > 0 ? 1 : 0;
		int numberOfPages = (numberOfItems / itemsOn1Page) + extra;
		return numberOfPages;
	}

	private final class PageCreation {
		private final Callback<Integer, Node> pageFactory = new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex);
			}
		};

		private final HBox createPage(final Integer pageIndex) {
			VBox vbox1 = new VBox();
			VBox vbox2 = new VBox();
			HBox hbox = new HBox();

			int numberOfItems = items.size();
			int itemsOn1Page = itemsPerColumn * column;
			int page = itemsOn1Page * pageIndex;
			for (int i = page; (i < page + itemsPerColumn) && (i < numberOfItems); i++) {
				VBox.setMargin(items.get(i), new Insets(7, 15, 7, 7));
				vbox1.getChildren().add(items.get(i));
			}
			for (int i = page + itemsPerColumn; (i < page + column * itemsPerColumn) && (i < numberOfItems); i++) {
				VBox.setMargin(items.get(i), new Insets(7, 7, 7, 15));
				vbox2.getChildren().add(items.get(i));
			}
			hbox.getChildren().add(vbox1);
			Separator separator = new Separator();
			separator.setOrientation(Orientation.VERTICAL);
			hbox.getChildren().add(separator);
			hbox.getChildren().add(vbox2);
			return hbox;
		}
	}

	private final class LocalHandlers {
		private final EventHandler<ActionEvent> closeStage = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				uiUpdateCycle.stop();
				EXECUTOR.shutdownNow();
				EcgBulkProcessingDaemon.getInstance().sendTerminationRequest();
				stage.close();
			}
		};
		private final EventHandler<WindowEvent> closeWindow = new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent we) {
				uiUpdateCycle.stop();
				EXECUTOR.shutdownNow();
				EcgBulkProcessingDaemon.getInstance().sendTerminationRequest();
			}
		};
		private final EventHandler<ActionEvent> itemsUpdate = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				List<EcgAnalysis> newResults = FXCollections.observableArrayList();
				newResults.addAll(EcgBulkProcessingDaemon.getInstance().pollResults());
				for (EcgAnalysis result : newResults) {
					if (items != null)
						for (EcgItem item : items)
							if (item.getName().equals(result.getName()))
								item.setAndShowResult(result);
				}
			}
		};
		private final EventHandler<ActionEvent> processAll = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				for (EcgItem item : items) {
					item.sendProcessingRequest();
				}
			}
		};
		private final EventHandler<ActionEvent> directoryChoice = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(stage);
				if (selectedDirectory != null)
					selectedDir = selectedDirectory.getAbsolutePath();
			}
		};
		private final EventHandler<ActionEvent> save = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				EXECUTOR.execute(localActions.saveAction);
			}
		};
	}

	private final class LocalActions {
		private final Runnable saveAction = new Runnable() {
			@Override
			public void run() {
				List<EcgAnalysis> toSave = new ArrayList<EcgAnalysis>();
				for (EcgItem item : items) {
					EcgAnalysis ecgAnalysis = item.getEcgAnalysis();
					if (ecgAnalysis != null)
						toSave.add(ecgAnalysis);
				}
				EcgAnalysisToExcelExporter.export(toSave);
			}
		};
	}

}
